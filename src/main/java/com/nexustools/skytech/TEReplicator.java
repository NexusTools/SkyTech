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
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.info.Info;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;

/**
 *
 * @author Luke
 */
public class TEReplicator extends TileEntity implements IInventory, ISidedInventory, IEnergySink {
    
    public CopyOnWriteArrayList<EntityPlayer> watchedBy = new CopyOnWriteArrayList<EntityPlayer>();
    
    @Override
    public int getSizeInventory() {
        return 64;
    }

    ItemStack output = null;
    ItemStack search = null;
    
    @Override
    public ItemStack getStackInSlot(int i) {
        switch(i){
            case 0: return output;
            case 1: return search;
            default: return null;
        }
    }
    
    @Override
    public ItemStack decrStackSize(int i, int j) {
        if(i == 0){
            if(output == null) return null;
            ItemStack temp = output.copy();
            output = null;
            return temp;
        }else return null;
//        if(!this.enoughEnergyToSynergize()) return null;
//        if(i != 0) return null;
//        System.out.println("decrStackSize");
////        inv = null;
//        ItemStack tmp = realinv;
//        
//        if(tmp != null){
//            if(this.enoughEnergyToSynergize()){
//                System.out.println("STORED_EU: " + STORED_EU + ", cost= " + this.cost + ", POST_STORED_EU=" + (STORED_EU-cost));
//                STORED_EU -= this.cost;
//                sendEnPacket();
//            }
//            tmp.stackSize = 1;
//        }else return null;
//        return tmp.copy(); //haaaaax
//        return null; // stub?
    }
    
//    this.

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return null; // stub?
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        System.out.println("setInventorySlotContents");
//        int uid = -1;
//        //iid = (iid << 16) | (meta & 0xffff);
//        if(itemstack != null){
//            //itemstack.itemID
//            uid = (itemstack.itemID << 16) | (itemstack.getItemDamage() & 0xffff);
//            this.cost = (int) (MAX_EU*ItemNameDatabase.values.get(uid));
//        }
//        if(i==0 && itemstack != null){inv = itemstack;inv.stackSize = 1;}
//        if(this.enoughEnergyToSynergize())realinv = inv;
    }
    
    public void setOutputSlotContents(ItemStack is){
        output = is;
        sync();
    }
    
    public void setSearchSlotContentsClient(ItemStack is, int uuid){
        System.out.println("setSearchSlotContentsClient");
        search = is;
        cost = (int) (MAX_EU*ItemNameDatabase.values.get(uuid));
        sync(uuid);
    }
    
    void setSearchSlotContentsServer(int uuid) {
        System.out.println("setSearchSlotContentsServer");
        int id =  (uuid >> 16);
        int meta =  (uuid & 0xffff);
        search = new ItemStack(Item.itemsList[id],1);
        search.setItemDamage(meta);
        cost = (int) (MAX_EU*ItemNameDatabase.values.get(uuid));
    }
    
    public void replicateServer(){
        // tell CReplicator
        CReplicator c = SkyTech.instance.handler.containers.get(new Trip(xCoord, yCoord, zCoord));
        ItemStack nu = this.search.copy();
        if(output != null && output.itemID == nu.itemID && output.getItemDamage() == nu.getItemDamage()){
            nu.stackSize += output.stackSize;
        }
        c.setOutput(nu);
        // tell TEReplicator (this)
        this.output = nu;
        // tell client
        int uuid = (nu.itemID << 16) | (nu.getItemDamage() & 0xffff);
        Packet250CustomPayload pack = Packetron.generatePacket(13, uuid, nu.stackSize, xCoord, yCoord, zCoord);
        for(EntityPlayer e : watchedBy){
            if(e != null){
                EntityPlayerMP mplayer = (EntityPlayerMP) e;
                PacketDispatcher.sendPacketToPlayer(pack, (Player) mplayer);
            }
        }
        
    }
    
    public void replicateClient(){ // called by the client
        if(STORED_EU>cost){
            if(search == null) return;
            PacketDispatcher.sendPacketToServer(Packetron.generatePacket(14, xCoord, yCoord, zCoord));
//            if(output == null){
//                STORED_EU -= cost;
////                output = search.copy();
////                sync();
//            }else{
//                if(output.itemID == search.itemID){
//                    if(output.getItemDamage() == search.getItemDamage()){
//                        STORED_EU -= cost;
////                        output.stackSize++; // work? D:
////                        sync();
//                    }
//                }
//            }
        }
    }
    
    public void sync(int ... data){
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if(side.isClient() && data.length>0){
            PacketDispatcher.sendPacketToServer(Packetron.generatePacket(12, data[0], xCoord, yCoord, zCoord));
        }
    }

    @Override
    public String getInvName() {
        return "Replicator";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return true;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        System.out.println("isItemValidForSlot");
        return false; // stub
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        return null; // stub
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        System.out.println("canInsertItem");
        return false; // stub
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        System.out.println("canExtractItem");
        if(i == 0)return true; // stub
        else return false;
    }

    /// POWER STUFF ///
    public static final double MAX_EU = 4500000d;
    public double STORED_EU = 4490000d;

    @Override
    public double demandedEnergyUnits() {
        return MAX_EU - STORED_EU;
    }

    boolean addedToEnet;

    /**
     * Forward for the base TileEntity's updateEntity(), used for creating the
     * energy net link. Either updateEntity or onLoaded have to be used.
     */
    @Override
    public void updateEntity() {
        if (!addedToEnet) {
            onLoaded();
        }
        this.onInventoryChanged();
    }

    /**
     * Notification that the base TileEntity finished loaded, for advanced uses.
     * Either updateEntity or onLoaded have to be used.
     */
    public void onLoaded() {
        if (!addedToEnet
                && !FMLCommonHandler.instance().getEffectiveSide().isClient()
                && Info.isIc2Available()) {

            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));

            addedToEnet = true;
        }
    }

    /**
     * Forward for the base TileEntity's invalidate(), used for destroying the
     * energy net link. Both invalidate and onChunkUnload have to be used.
     */
    @Override
    public void invalidate() {
        super.invalidate();

        onChunkUnload();
    }

    /**
     * Forward for the base TileEntity's onChunkUnload(), used for destroying
     * the energy net link. Both invalidate and onChunkUnload have to be used.
     */
    @Override
    public void onChunkUnload() {
        if (addedToEnet
                && Info.isIc2Available()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));

            addedToEnet = false;
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        NBTTagCompound data = tag.getCompoundTag("TEReplicator");

        STORED_EU = data.getDouble("energy");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag) {
        try {
            super.writeToNBT(tag);
        } catch (RuntimeException e) {
// happens if this is a delegate, ignore
        }

        NBTTagCompound data = new NBTTagCompound();

        data.setDouble("energy", STORED_EU);

        tag.setTag("TEReplicator", data);
    }
    
//    this.on
    
    int pt = 0;
    
    public void sendEnPacket(){
        if(this.watchedBy.size() == 0) return;
        byte[] data = new byte[19];
        data[0] = 8; //case 8: // set energy (long)
        long l = (long)STORED_EU;
        data[1] = (byte)(l >>> 56);
        data[2] = (byte)(l >>> 48);
        data[3] = (byte)(l >>> 40);
        data[4] = (byte)(l >>> 32);
        data[5] = (byte)(l >>> 24);
        data[6] = (byte)(l >>> 16);
        data[7] = (byte)(l >>>  8);
        data[8] = (byte)(l >>>  0);
        data[9] = (byte) (char) ((this.xCoord >>> 24) & 0xFF);
        data[10] = (byte) (char) ((this.xCoord >>> 16) & 0xFF);
        data[11] = (byte) (char) ((this.xCoord >>> 8) & 0xFF);
        data[12] = (byte) (char) ((this.xCoord >>> 0) & 0xFF);
        data[13] = (byte) (char) ((this.yCoord >>> 8) & 0xFF);
        data[14] = (byte) (char) ((this.yCoord >>> 0) & 0xFF);
        data[15] = (byte) (char) ((this.zCoord >>> 24) & 0xFF);
        data[16] = (byte) (char) ((this.zCoord >>> 16) & 0xFF);
        data[17] = (byte) (char) ((this.zCoord >>> 8) & 0xFF);
        data[18] = (byte)(char)((this.zCoord>>>  0) & 0xFF);
        
        
//        NetUtil.sendPacket("ReplicatorGUI", cust.data, play.player);
        
        for(EntityPlayer e : this.watchedBy){
            if(e != null)
                NetUtil.sendPacket("ReplicatorGUI", data, e);
        }
//        this
        
    }
    
    boolean realinvswch = false;

    @Override
    public double injectEnergyUnits(ForgeDirection directionFrom, double amount) {
        if(pt>8 || amount > 512){
            sendEnPacket();
            pt = 0;
        }
        if (amount + STORED_EU > MAX_EU) {
            System.out.println("MAXING OUT");
            double rem = MAX_EU - STORED_EU;
            STORED_EU = MAX_EU;
            if(amount-rem>0)pt++;
            return amount - rem;
        } else {
            STORED_EU += amount;
            pt++;
            return 0;
        }
    }

    @Override
    public int getMaxSafeInput() { // not exactly sure what for
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        System.out.println("acceptsEnergyFrom");
        return true; // haaax
    }

    public int cost = 1000000;
    
    public boolean enoughEnergyToReplicate() {
        return cost < STORED_EU;
    }

}
