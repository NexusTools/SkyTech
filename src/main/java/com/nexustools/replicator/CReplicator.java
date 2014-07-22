/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.replicator;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 *
 * @author Luke
 */
public class CReplicator extends Container {

//    this.

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges(); //To change body of generated methods, choose Tools | Templates.
    }
    
    static boolean n = true;
    
//    this.
    
//    this.

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        System.out.println("transferStackInSlot");
        return null;
    }
    
//    this.on

    int count = 0;
//    
//    @Override
//    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
//        count = 0;
//        return super.transferStackInSlot(par1EntityPlayer, par2); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer) {
//        if(count>4){
//            return;
//        }
//        count++;
//        super.retrySlotClick(par1, par2, par3, par4EntityPlayer); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer) {
//        return super.slotClick(par1, par2, par3, par4EntityPlayer); //To change body of generated methods, choose Tools | Templates.
//    }
    
    int CIIDX = 0;
    
//    this.
    
    public void setItemIndex(int rawid){
////        removeCraftingFromCrafters(null); // fix right here
                            int index =  (rawid >> 16);
            int meta =  (rawid & 0xffff);
        System.out.println("Updating item index to " + index);

        CIIDX = index;
        Slot s = (Slot) inventorySlots.get(0);
        ItemStack is = null;
        try {
            is = new ItemStack(Block.blocksList[CIIDX], 1);
            is.setItemDamage(meta);
            s.inventory.setInventorySlotContents(0, is);// System.out.println(" SUCCESS");
            s.onSlotChanged();
        } catch (Throwable i) {
        }

        if (is != null) {
                                try {
                                    Packet250CustomPayload cust = new Packet250CustomPayload();
                                    cust.channel = "ReplicatorGUI";
                                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                                    DataOutputStream dout = new DataOutputStream(out);
                                    dout.write(1);
                                    dout.writeInt(rawid);
                                    dout.write(is.stackSize);
                                    dout.writeInt(ent.xCoord);
                                    dout.writeShort(ent.yCoord);
                                    dout.writeInt(ent.zCoord);
                                    cust.data = out.toByteArray();
                                    cust.length = cust.data.length;
//            cust.length = 14;
//            cust.data = new byte[14]; // 4 + 8 (x,z) + 2 (y)
//            cust.data[0] = (char) 1; // char is unsigned
//            cust.data[1] = (byte) (char) ((is.itemID >>> 8) & 0xFF);
//            cust.data[2] = (byte) (char) ((is.itemID >>> 0) & 0xFF);
//            cust.data[3] = (byte) is.stackSize;
//            cust.data[4] = (byte) (char) ((ent.xCoord >>> 24) & 0xFF);
//            cust.data[5] = (byte) (char) ((ent.xCoord >>> 16) & 0xFF);
//            cust.data[6] = (byte) (char) ((ent.xCoord >>> 8) & 0xFF);
//            cust.data[7] = (byte) (char) ((ent.xCoord >>> 0) & 0xFF);
//            cust.data[8] = (byte) (char) ((ent.yCoord >>> 8) & 0xFF);
//            cust.data[9] = (byte) (char) ((ent.yCoord >>> 0) & 0xFF);
//            cust.data[10] = (byte) (char) ((ent.zCoord >>> 24) & 0xFF);
//            cust.data[11] = (byte) (char) ((ent.zCoord >>> 16) & 0xFF);
//            cust.data[12] = (byte) (char) ((ent.zCoord >>> 8) & 0xFF);
//            cust.data[13] = (byte) (char) ((ent.zCoord >>> 0) & 0xFF);
                                    
                                    NetUtil.sendPacket("ReplicatorGUI", cust.data, play.player);
                                    
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(CReplicator.class.getName()).log(Level.SEVERE, null, ex);
//            }
                                }
//                    s.putStack(new ItemStack(Item.itemsList[(int)(Math.random()*32f)],1));
                                catch (IOException ex) {
                                    Logger.getLogger(CReplicator.class.getName()).log(Level.SEVERE, null, ex);
                                }

        }


    }
    
    public int getItemIndex(){
        return CIIDX;
    }
    
//    this.

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer); //To change body of generated methods, choose Tools | Templates.
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
//                // We are on the server side.
//                EntityPlayerMP player = (EntityPlayerMP) play.player;
//                PacketDispatcher.sendPacketToPlayer(cust, (Player)player);
            removeCraftingFromCrafters((EntityPlayerMP) par1EntityPlayer);
        } else if (side == Side.CLIENT) {
//            removeCraftingFromCrafters((EntityClientPlayerMP) par1EntityPlayer);
                // We are on the client side.
        } else {
                // We have an errornous state!
        }
    }
    
    public InventoryPlayer play;
    public TEReplicator ent;
    
    public CReplicator(final InventoryPlayer play, final TEReplicator ent){
        this.play = play;
        this.ent = ent;
        this.addSlotToContainer(new Slot(ent, 0, 56, 53){
        
            @Override
            public void putStack(ItemStack par1ItemStack) {
//                try{
//                    throw new Throwable("Stack");
//                }catch(Throwable t){t.printStackTrace();}
                System.out.println("(SL)putStack->" + (par1ItemStack==null?"null":par1ItemStack.getUnlocalizedName()));
                super.putStack(par1ItemStack); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
                System.out.println("(SL)canTakeStack->" + (par1EntityPlayer==null?"null":par1EntityPlayer.getDisplayName()));
                return super.canTakeStack(par1EntityPlayer) && ent.enoughEnergyToSynergize(); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onSlotChange(ItemStack par1ItemStack, ItemStack par2ItemStack) {
                System.out.println("(SL)onSlotChange->" + (par1ItemStack==null?"null":par1ItemStack.getUnlocalizedName()) + ", " + (par2ItemStack==null?"null":par2ItemStack.getUnlocalizedName()));
                super.onSlotChange(par1ItemStack, par2ItemStack); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
                System.out.println("(SL)onPickupFromSlot->" + (par1EntityPlayer==null?"null":par1EntityPlayer.getDisplayName()) + ", " + (par2ItemStack==null?"null":par2ItemStack.getUnlocalizedName()));
                if(ent.enoughEnergyToSynergize())super.onPickupFromSlot(par1EntityPlayer, par2ItemStack); //To change body of generated methods, choose Tools | Templates.
            }
            
//            this

            @Override
            public ItemStack decrStackSize(int par1) {
                ItemStack us = super.decrStackSize(par1);
                return us == null ? us : us.copy(); //To change body of generated methods, choose Tools | Templates.
            }
//.
        
        });
        
        
        for (int i = 0; i < 3; i++)
        {
            for (int k = 0; k < 9; k++)
            {
            	this.addSlotToContainer(new Slot(play, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
        	this.addSlotToContainer(new Slot(play, j, 8 + j * 18, 142));
        }
        
//        this.
        if (n) {
            n = false;
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    while (true) {
//                       
//                        Slot s = (Slot) inventorySlots.get(0);
////                    System.out.print("SETSLOT");
//                        ItemStack is = null;
//                        try {
//                            is = new ItemStack(Item.itemsList[(int) (Math.random() * 256f)], 1);
//                            s.inventory.setInventorySlotContents(0, is);// System.out.println(" SUCCESS");
////                    s.inventory.onInventoryChanged();
//                            s.onSlotChanged();
////                            detectAndSendChanges();
////                            ent.setInventorySlotContents(0, is);
////                            ent.onInventoryChanged();
//                        } catch (Throwable i) {
////                        System.out.println(" FAIL");
//                        }
//                        
//                        if(is != null){
//                            Packet250CustomPayload cust = new Packet250CustomPayload();
//                            cust.channel = "ReplicatorGUI";
//                            cust.length = 14;
//                            cust.data = new byte[14]; // 4 + 8 (x,z) + 2 (y)
//                            cust.data[0] = (char)1; // char is unsigned
//                            cust.data[1] = (byte)(char)((is.itemID>>>  8) & 0xFF);
//                            cust.data[2] = (byte)(char)((is.itemID>>>  0) & 0xFF);
//                            cust.data[3] = (byte)is.stackSize;
//                            cust.data[4] = (byte)(char)((ent.xCoord>>> 24) & 0xFF);
//                            cust.data[5] = (byte)(char)((ent.xCoord>>> 16) & 0xFF);
//                            cust.data[6] = (byte)(char)((ent.xCoord>>>  8) & 0xFF);
//                            cust.data[7] = (byte)(char)((ent.xCoord>>>  0) & 0xFF);
//                            cust.data[8] = (byte)(char)((ent.yCoord>>>  8) & 0xFF);
//                            cust.data[9] = (byte)(char)((ent.yCoord>>>  0) & 0xFF);
//                            cust.data[10] = (byte)(char)((ent.zCoord>>> 24) & 0xFF);
//                            cust.data[11] = (byte)(char)((ent.zCoord>>> 16) & 0xFF);
//                            cust.data[12] = (byte)(char)((ent.zCoord>>>  8) & 0xFF);
//                            cust.data[13] = (byte)(char)((ent.zCoord>>>  0) & 0xFF);
//                            
//                            NetUtil.sendPacket("ReplicatorGUI", cust.data, play.player);
////                            
////                            
////                            System.out.println("Writing " + is.itemID + " as " + cust.data[1] + ", " + cust.data[2]+ " -> converted back:" + (short)(((int)(cust.data[1]&0xFF) << 8) + ((int)(cust.data[2]&0xFF) << 0)));
////                            
////                            /*/
////                                out.write((v >>> 8) & 0xFF);
////                                out.write((v >>> 0) & 0xFF);
////                            /*/
////                            
////                            
////                            
////                            Side side = FMLCommonHandler.instance().getEffectiveSide();
////                            if (side == Side.SERVER) {
////                                    // We are on the server side.
////                                    EntityPlayerMP player = (EntityPlayerMP) play.player;
////                                    PacketDispatcher.sendPacketToPlayer(cust, (Player)player);
////                            } else if (side == Side.CLIENT) {
////                                    EntityClientPlayerMP player = (EntityClientPlayerMP) play.player;
////                                    player.sendQueue.addToSendQueue(cust);
////                                    // We are on the client side.
////                            } else {
////                                    // We have an errornous state!
////                            }
//                            
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException ex) {
//                                Logger.getLogger(CReplicator.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//
//                        }
//                        
//                        
//
////                    s.putStack(new ItemStack(Item.itemsList[(int)(Math.random()*32f)],1));
//                    }
//                }
//            }).start();
        }
    }
    
    int rarity = 1000000;
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
//        return true && ent.enoughEnergyToSynergize(); // haax
        return true;
    }
    
}
