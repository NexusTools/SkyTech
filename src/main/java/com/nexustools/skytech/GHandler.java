/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

import cpw.mods.fml.common.network.IGuiHandler;
import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 *
 * @author Luke
 */



public class GHandler implements IGuiHandler{

    
    /*
            @Override
        public Object getServerGuiElement(int id, EntityPlayer player, World world,
                        int x, int y, int z) {
                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
                if(tileEntity instanceof TileEntityTiny){
                        return new ContainerTiny(player.inventory, (TileEntityTiny) tileEntity);
                }
                return null;
        }

        //returns an instance of the Gui you made earlier
        @Override
        public Object getClientGuiElement(int id, EntityPlayer player, World world,
                        int x, int y, int z) {
                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
                if(tileEntity instanceof TileEntityTiny){
                        return new GuiTiny(player.inventory, (TileEntityTiny) tileEntity);
                }
                return null;

        }
    */
    
    HashMap<Trip, CReplicator> containers = new HashMap<Trip, CReplicator>();
//    HashMap<Trip, GReplicator> guis = new HashMap<Trip, GReplicator>();
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//        System.out.println("getServerGuiElement(" + ID + ")");
        TileEntity ent = world.getBlockTileEntity(x, y, z);
        
        if(ent instanceof TEReplicator){
            Trip t = new Trip(x,y,z);
            if(containers.containsKey(t)){
                return containers.get(t);
            }else{
                CReplicator tr = new CReplicator(player.inventory, (TEReplicator)ent);
                containers.put(t, tr);
                return tr;
            }
//            return new CReplicator(player.inventory, (TEReplicator)ent);
        } else System.out.println("--- TileEntity is not TEReplicator!");
        
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//        System.out.println("getClientGuiElement(" + ID + ")");
        TileEntity ent = world.getBlockTileEntity(x, y, z);
        
//        if(ent instanceof TEReplicator){
//            Trip t = new Trip(x,y,z);
//            if(guis.containsKey(t)){
//                return guis.get(t);
//            }else{
//                GReplicator tr = new GReplicator(player.inventory, (TEReplicator)ent);
//                guis.put(t, tr);
//                return tr;
//            }
//        } else System.out.println("--- TileEntity is not TEReplicator!");
//        
        return new GReplicator(player.inventory, (TEReplicator)ent);
    }
    
}
