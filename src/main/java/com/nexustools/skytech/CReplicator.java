/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

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

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        System.out.println("transferStackInSlot");
        return null;
    }
    
    // weird server bug fix
    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        if (this.crafters.contains(par1ICrafting))
        {
            throw new IllegalArgumentException("Listener already listening");
        }
        else
        {
            this.crafters.add(par1ICrafting);
            par1ICrafting.sendContainerAndContentsToPlayer(this, this.getInventory());
            this.detectAndSendChanges();
        }
    }
    @Override
    public void removeCraftingFromCrafters(ICrafting par1ICrafting)
    {
        this.crafters.remove(par1ICrafting);
    }

    
//    
//    int count = 0;
//
//    int CIIDX = 0;
//    
//    public void setItemIndex(int rawid){
//        int index =  (rawid >> 16);
//        int meta =  (rawid & 0xffff);
//        System.out.println("Updating item index to " + index);
//
//        CIIDX = index;
//        Slot s = (Slot) inventorySlots.get(0);
//        ItemStack is = null;
//        try {
//            is = new ItemStack(Block.blocksList[CIIDX], 1);
//            is.setItemDamage(meta);
//            s.inventory.setInventorySlotContents(0, is);// System.out.println(" SUCCESS");
//            s.onSlotChanged();
//        } catch (Throwable i) {
//        }
//
//        if (is != null) {
//            try {
//                Packet250CustomPayload cust = new Packet250CustomPayload();
//                cust.channel = "ReplicatorGUI";
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                DataOutputStream dout = new DataOutputStream(out);
//                dout.write(1);
//                dout.writeInt(rawid);
//                dout.write(is.stackSize);
//                dout.writeInt(ent.xCoord);
//                dout.writeShort(ent.yCoord);
//                dout.writeInt(ent.zCoord);
//                cust.data = out.toByteArray();
//                cust.length = cust.data.length;
//                NetUtil.sendPacket("ReplicatorGUI", cust.data, play.player);
//            }catch (IOException ex) {
//                Logger.getLogger(CReplicator.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//    
//    public int getItemIndex(){
//        return CIIDX;
//    }
//    
    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
            removeCraftingFromCrafters((EntityPlayerMP) par1EntityPlayer);
        }
    }
    
    public InventoryPlayer play;
    public TEReplicator ent;
    
//    this.
    
    public CReplicator(final InventoryPlayer play, final TEReplicator ent){
        this.play = play;
        this.ent = ent;
        addSlotToContainer(new Slot(ent, 0, 96, 53));
        addSlotToContainer(new Slot(ent, 1, 56, 53));
//        this.addSlotToContainer(new Slot(ent, 0, 56, 53){
//        
//            @Override
//            public void putStack(ItemStack par1ItemStack) {
//                
//                System.out.println("(SL)putStack->" + (par1ItemStack==null?"null":par1ItemStack.getUnlocalizedName()));
//                super.putStack(par1ItemStack); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
//                System.out.println("(SL)canTakeStack->" + (par1EntityPlayer==null?"null":par1EntityPlayer.getDisplayName()));
//                return super.canTakeStack(par1EntityPlayer) && ent.enoughEnergyToSynergize(); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void onSlotChange(ItemStack par1ItemStack, ItemStack par2ItemStack) {
//                System.out.println("(SL)onSlotChange->" + (par1ItemStack==null?"null":par1ItemStack.getUnlocalizedName()) + ", " + (par2ItemStack==null?"null":par2ItemStack.getUnlocalizedName()));
//                super.onSlotChange(par1ItemStack, par2ItemStack); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
//                System.out.println("(SL)onPickupFromSlot->" + (par1EntityPlayer==null?"null":par1EntityPlayer.getDisplayName()) + ", " + (par2ItemStack==null?"null":par2ItemStack.getUnlocalizedName()));
//                if(ent.enoughEnergyToSynergize())super.onPickupFromSlot(par1EntityPlayer, par2ItemStack); //To change body of generated methods, choose Tools | Templates.
//            }
//            
////            this
//
//            @Override
//            public ItemStack decrStackSize(int par1) {
//                ItemStack us = super.decrStackSize(par1);
//                return us == null ? us : us.copy(); //To change body of generated methods, choose Tools | Templates.
//            }
////.
//        
//        });
        
        
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
        
    }
    
    int rarity = 1000000;
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
//        return true && ent.enoughEnergyToSynergize(); // haax
        return true;
    }

    void setOutput(ItemStack is) {
        Slot s = (Slot) inventorySlots.get(0);
        s.inventory.setInventorySlotContents(0, is);// System.out.println(" SUCCESS");
        s.onSlotChanged();
    }
    
}
