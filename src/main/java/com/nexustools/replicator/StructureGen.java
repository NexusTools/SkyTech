/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.replicator;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author Luke
 */

class RB {
    
    public int id, x, y, z;
    
    public int meta = 0, flags = 0;
    
    public boolean tileEntity = false;
    
    public NBTTagCompound nbt = null;
    
    public ItemStack[] inv;
    
    public RB(int id, int x, int y, int z){
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public RB setMeta(int meta){
        this.meta = meta;
        return this;
    }
    
    public RB setNBT(NBTTagCompound tag){
        tileEntity = true;
        nbt = tag;
        return this;
    }
    
    public RB setItems(ItemStack[] items){
        inv = items;
        return this;
    }
    
}

public class StructureGen {
    
    public static final ArrayList<RB> lobby = new ArrayList<RB>();
    public static final ArrayList<RB> skyblock = new ArrayList<RB>();
    
    static {
        NBTTagCompound cmd = new NBTTagCompound();
        cmd.setString("Command", "/say Hello");
        lobby.add(new RB(1103, 0,38,0).setNBT(cmd));
        lobby.add(new RB(Block.cobblestoneMossy.blockID, 0,37,0));
        
        for(int x = -16; x < 17; x++){
            for(int y = -16; y < 17; y++){
                lobby.add(new RB(Block.bedrock.blockID, x, 32, y));
                lobby.add(new RB(Block.obsidian.blockID, x, 33, y));
                lobby.add(new RB(Block.lavaStill.blockID, x, 34, y));
                lobby.add(new RB(Block.blockDiamond.blockID, x, 35, y));
                
                if(Math.abs(x) == Math.abs(y)){
                    lobby.add(new RB(Block.blockIron.blockID, x, 36, y));
                    if((16+x)%4 == 0||(16+y)%4==0){
                        if(!(x==0&&y==0))
                            lobby.add(new RB(Block.torchWood.blockID, x, 37, y));
                    }
                }else
                if(Math.abs(x)+Math.abs(y)==4){
                    lobby.add(new RB(Block.blockDiamond.blockID, x, 36, y));
                }else
                if(Math.abs(x)+Math.abs(y)==12){
                    lobby.add(new RB(Block.blockIron.blockID, x, 36, y));
                }else
                if(Math.abs(x)+Math.abs(y)==8){
                    lobby.add(new RB(Block.blockGold.blockID, x, 36, y));
                }else{
                    if((16+x)%4 == 0||(16+y)%4==0){
                        lobby.add(new RB(Block.blockIron.blockID, x, 36, y));
                        if((16+x)%4 == 0&&(16+y)%4==0){
                            lobby.add(new RB(Block.torchWood.blockID, x, 37, y));
                        }
                    }else
                        lobby.add(new RB(Block.stoneBrick.blockID, x, 36, y).setMeta(1));
                }
                if(x != y && ((Math.abs(y)==8&&x==0) || (Math.abs(x) == 8&&y==0)))
                    lobby.add(new RB(Block.torchWood.blockID, x, 37, y));
                lobby.add(new RB(Block.glass.blockID, x, 48, y));
                
            }
        }
        for(int x = -16; x < 17; x++){
            for(int y = 32; y < 49; y++){
                if((16+x)%4 == 0){
                    lobby.add(new RB(Block.blockIron.blockID, x, y, -16));
                    lobby.add(new RB(Block.blockIron.blockID, x, y, 16));
                }else{
                    lobby.add(new RB(Block.stoneBrick.blockID, x, y, -16).setMeta(1));
                    lobby.add(new RB(Block.stoneBrick.blockID, x, y, 16).setMeta(1));
                }
            }
        }
        for(int x = -16; x < 17; x++){
            for(int y = 32; y < 49; y++){
                if((16+x)%4 == 0){
                    lobby.add(new RB(Block.blockIron.blockID, -16, y, x));
                    lobby.add(new RB(Block.blockIron.blockID, 16, y, x));
                }else{
                    lobby.add(new RB(Block.stoneBrick.blockID, -16, y, x).setMeta(1));
                    lobby.add(new RB(Block.stoneBrick.blockID, 16, y, x).setMeta(1));
                }
            }
        }
        
        skyblock.add(new RB(Block.bedrock.blockID, 0, 60, 0));
        
        for(int x = -2; x < 4; x++){
            for(int y = -2; y < 1; y++){
                skyblock.add(new RB(Block.grass.blockID, x,64,y));
                skyblock.add(new RB(Block.dirt.blockID, x,63,y));
                skyblock.add(new RB(Block.sand.blockID, x,62,y));
                skyblock.add(new RB(Block.gravel.blockID, x,61,y));
                if(!(x==y&&y==0))
                    skyblock.add(new RB(Block.stone.blockID, x,60,y));
            }
        }
        for(int x = -2; x < 1; x++){
            for(int y = 0; y < 4; y++){
                skyblock.add(new RB(Block.grass.blockID, x,64,y));
                skyblock.add(new RB(Block.dirt.blockID, x,63,y));
                skyblock.add(new RB(Block.sand.blockID, x,62,y));
                skyblock.add(new RB(Block.gravel.blockID, x,61,y));
                if(!(x==y&&y==0))
                    skyblock.add(new RB(Block.stone.blockID, x,60,y));
            }
        }
        
        ItemStack[] nonvanilla = new ItemStack[2];
        
        try{
            nonvanilla[0] = new ItemStack(Item.itemsList[650], 1);//650:1
            nonvanilla[0].setItemDamage(1);
        }catch(Throwable t){}
        try{
            //15158:20 insulated copper wire
            nonvanilla[1] = new ItemStack(Item.itemsList[30184], 3); // 30184:0
            nonvanilla[1].setItemDamage(0);
        }catch(Throwable t){}
        
        /*
        
        1x replicator
        1x medium solar array
        3x insulated copper wire (why not lul)
        3x lava block
        3x ice block
        2x oak saplings (skyblock no longer nesisarily generates with a tree, as they are affected by biomes.)
        2x bone (bonemeal is a lot less efficient now, not sure about this one though)
        12x string (original skyblock had this)
        1x red mushroom (original skyblock had this)
        1x brown mushroom (original skyblock had this)
        1x watermelon slice (original skyblock had this)
        1x pumpkin seed (original skyblock had this)
        1x cactus (original skyblock had this)
        1x reed/sugarcane (original skyblock had this)
        
        */
        
        
        ItemStack[] items = new ItemStack[]{
            new ItemStack(Block.blocksList[SkyTech.REPLICATOR_BLOCKID], 1),
            new ItemStack(Block.lavaMoving, 3),
            new ItemStack(Block.ice, 4),
            new ItemStack(Block.sapling, 2),
            new ItemStack(Item.bone, 2),
            new ItemStack(Item.silk, 12),
            
            nonvanilla[0],
            nonvanilla[1],
            
            new ItemStack(Item.melon, 1),
            new ItemStack(Item.pumpkinSeeds, 1),
            new ItemStack(Block.cactus, 1),
            new ItemStack(Item.reed, 1)
            
        };
//        IC2.
        // 2 65 2
        skyblock.add(new RB(Block.chest.blockID, -1, 65, 2).setItems(items));
        
//        for(int x = -4; x < 5; x++){
//            for(int y = -4; y < 5; y++){
//                skyblock.add(new RB(Block.stone.blockID, x, 64, y));
//            }
//        }
    }
    
}
