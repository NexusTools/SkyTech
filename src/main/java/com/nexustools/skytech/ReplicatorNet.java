/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 *
 * @author Luke
 */
public class ReplicatorNet implements IPacketHandler {
       
    HashMap<String, Point> occupancies = new HashMap<String, Point>();
    
    boolean needsLoad = true;
    
    private void savedb() {
        try{
            File db = new File(Cache.cachedir + "skyplayers.db");
            FileOutputStream out = new FileOutputStream(db);
            DataOutputStream dout = new DataOutputStream(out);
            dout.writeInt(occupancies.size());
            for(String s : occupancies.keySet()){
                out.write(s.length());
                out.write(s.getBytes());
                Point p = occupancies.get(s);
                dout.writeDouble(p.getX());
                dout.writeDouble(p.getY());
            }
        }catch(Throwable t){}
    }

    private void loaddb() {
        try{
            File db = new File(Cache.cachedir + "skyplayers.db");
            if(!db.exists())return;
            FileInputStream in = new FileInputStream(db);
            DataInputStream din = new DataInputStream(in);
            int len = din.readInt();
            for(int i = 0; i < len; i++){
                String str = "";
                int strl = in.read();
                for(int s = 0; s < strl; s++)str+=(char)in.read();
                double x = din.readDouble();
                double y = din.readDouble();
                occupancies.put(str, new Point((int)x, (int)y));
            }
        }catch(Throwable t){}
    }
    
    private Point getNextAvailableProperty() {
        Point low = new Point(0, 1);
        boolean unsafe = true;
        
        while(unsafe){
            unsafe = false;
            for(String s : occupancies.keySet()){
                Point p = occupancies.get(s);
                if(p.x==low.x&&p.y==low.y){
                    unsafe = true;
                    if(low.x==low.y){
                        if(low.x>=0)low.x++;else low.x--;
                    }else if(Math.abs(low.x)>Math.abs(low.y)){
                        if(low.y>=0)low.y++;else low.y--;
                    }else{
                        if(low.x>=0)low.x++;else low.x--;
                    }
                }
            }
        }
        return low;
    }
    
    public void activateSkyblock(final EntityPlayer p){
        final World w = p.worldObj;
        String str = p.getEntityName();
        if(!occupancies.containsKey(str)){
            Point poi = getNextAvailableProperty();
            occupancies.put(str, poi);
            savedb();
        }
        
        final Point poi = occupancies.get(str);
        
        RB chck = StructureGen.skyblock.get(0); // bedrock
//        
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                for (Item i : Item.itemsList) {
//                    try{
//                        if (i != null) {
//                            String iname = i.getItemDisplayName(new ItemStack(i, 1)).toLowerCase();
//                            System.out.println("[ITM] " + i.itemID + ":0 " + iname);
//                            ItemStack stk;
//                            for (int inc = 0; inc < 255; inc++) {
//                                stk = new ItemStack(i, 1);
//                                stk.setItemDamage(inc);
//                                String sname = i.getItemDisplayName(stk).toLowerCase();
//                                if (sname != null && !sname.equals(iname)) {
//                                    System.out.println("[ITM] " + i.itemID + ":" + inc + " " + sname);
//                                    iname = sname;
//                                }
//                            }
//                        }
//                    }catch(Throwable t){}
//                }
//            }
//        }).start();
        
//        for(Block i : Block.blocksList){
//            if(i != null){
////                i.getBlock
////                Item i = new
//                String iname = i.getItemDisplayName(new ItemStack(i, 1)).toLowerCase();
//                System.out.println("[ITM] " + i.itemID + ":0 " + iname);
//                ItemStack stk;
//                for(int inc = 0; inc < 255; inc++){
//                    stk = new ItemStack(i, 1);
//                    stk.setItemDamage(inc);
//                    String sname = i.getItemDisplayName(stk).toLowerCase();
//                    if(sname != null && !sname.equals(iname)){
//                        System.out.println("[ITM] " + i.itemID + ":"+inc+" " + sname);
//                        iname = sname;
//                    }
//                }
//            }
//        }
        //add(new RB(Block.bedrock.blockID, 0, 60, 0));
        if(w.getBlockId(chck.x+poi.x*512, chck.y, chck.z + poi.y*512) != chck.id) // haaaax
            for (final RB b : StructureGen.skyblock) {
                w.setBlock(b.x + poi.x * 512, b.y, b.z + poi.y * 512, b.id, b.meta, b.flags);
                if(b.inv != null){
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Thread.sleep(10000);
                                TileEntityChest t = (TileEntityChest)w.getBlockTileEntity(b.x + poi.x * 512, b.y, b.z + poi.y * 512);
                                for(int i = 0; i < b.inv.length; i++){
                                    try{
                                        if(b.inv[i].itemID == 650){ // TEMPORARY FTB FIX (no idea why this is happening, I think something is weirdly cached
                                            b.inv[i].setItemDamage(0);
                                        }
                                        t.setInventorySlotContents(i, b.inv[i]);
                                    }catch(Throwable at){}
                                }
                                ChatMessageComponent ct = new ChatMessageComponent();
                                ct.addText("You have been apportioned a few items to get you started, check the nearby chest!");
                                p.sendChatToPlayer(ct);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(BCommand.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }).start(); // INSANE HACKS, but minecraft REALLY doesnt like getting the tile entity right after setting the block id...

                }
            }
        
        p.setSpawnChunk(new ChunkCoordinates(poi.x*512, 68, poi.y*512), true);
        
        p.setLocationAndAngles(poi.x*512, 68, poi.y*512, 0, 0);
        PacketDispatcher.sendPacketToPlayer(Packetron.generatePacket(16, poi.x*512, 68, poi.y*512), (Player)p);
    }

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
//        System.out.print("PACKET250CUSTOMPAYLOAD!!!!["+packet.data[0]+"] -> ");
        int x, y, z;
        TEReplicator e;
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(packet.data));
        try{
        switch(din.read()){
            
            case 12:{ // setSearchItem
                //data[0], xCoord, yCoord, zCoord
                int uuid = din.readInt();
                x = din.readInt();
                y = din.readInt();
                z = din.readInt();
                e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
                e.setSearchSlotContentsServer(uuid);
                break;
            }
            
            case 13:{//setOutputItem
                System.out.println("setOutputItem");
                int uuid = din.readInt();
                int stacks = din.readInt();
                x = din.readInt();
                y = din.readInt();
                z = din.readInt();
                e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
                int id =  (uuid >> 16);
                int meta =  (uuid & 0xffff);
                ItemStack is = new ItemStack(Item.itemsList[id], stacks);
                is.setItemDamage(meta);
                e.output = is;
                break;
            }
            
            case 14:{ // activate
                System.out.println("Activate");
                x = din.readInt();
                y = din.readInt();
                z = din.readInt();
                int uuid = din.readInt();
                e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
                e.replicateServer(uuid);
                break;
            }
            
            case 15:{
//                System.out.println("receiveEnPacket");
                int eu = din.readInt();
                x = din.readInt();
                y = din.readInt();
                z = din.readInt();
                e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
                e.STORED_EU = (double)eu;
                break;
            }
            
            case 16:{ // CBTeleport
                x = din.readInt();
                y = din.readInt();
                z = din.readInt();
                
                ((EntityPlayer)player).setLocationAndAngles(x, y, z, 0, 0);
                break;
            }
            
            case 17:{// requestSkyblockActivate
                if(needsLoad){
                    needsLoad = false;
                    this.loaddb();
                }
                activateSkyblock(((EntityPlayer)player));
                break;
            }
            
            
            
            //@Deprecated
            case 1: //if(packet.length == 14){ 
                try{
//                    System.out.println("UIItemUpdate");
//                int rawid = (short)((packet.data[1]&0xFF << 8) + (packet.data[2]&0xFF << 0));
                    /*
                    te(1);
                                    dout.writeInt(rawid);
                                    dout.write(is.stackSize);
                                    dout.writeInt(ent.xCoord);
                                    dout.writeShort(ent.yCoord);
                                    dout.writeInt(ent.zCoord);
                    */
                    int rawid = din.readInt();
                    int index =  (rawid >> 16);
                    int meta =  (rawid & 0xffff);
                    int stack = din.read();
                    ItemStack is = new ItemStack(Item.itemsList[index],stack);
                    is.setItemDamage(meta);
//                    System.out.println(is.getUnlocalizedName());
                    x = din.readInt();
                    y = din.readShort();
                    z = din.readInt();
    //                x = ((packet.data[4]&0xFF  << 24) + (packet.data[5]&0xFF  << 16) + (packet.data[6]&0xFF  << 8) + (packet.data[7]&0xFF  << 0));
    //                y = ((packet.data[8]&0xFF  << 8) + (packet.data[9]&0xFF  << 0));
    //                z = ((packet.data[10]&0xFF  << 24) + (packet.data[11]&0xFF  << 16) + (packet.data[12]&0xFF  << 8) + (packet.data[13]&0xFF  << 0));
                    e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
                    if(e != null)
                        e.setInventorySlotContents(0, is);
                }catch(Throwable t){t.printStackTrace();}
//            } break;
                break;
            
            case 2: // small energy update plus
                
                break;
                
            case 3: // small energy update minus
                
                break;
                
            case 4: // medium energy update plus
                
                break;
                
            case 5: // medium energy update minus
                
                break;
                
            case 6: // large energy update plus
                
                break;
                
            case 7: // large energy update minus
                
                break;
                
//            case 8: // set energy (long)
//                long val = din.readLong();
//                x = din.readInt();
//                y = din.readShort();
//                z = din.readInt();
//                e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
//                if(e != null)
//                    e.STORED_EU = (double)val;
////                if(packet.length == 19){
////                    long val = (((long)packet.data[1] << 56) +
////                    ((long)(packet.data[2] & 255) << 48) +
////                    ((long)(packet.data[3] & 255) << 40) +
////                    ((long)(packet.data[4] & 255) << 32) +
////                    ((long)(packet.data[5] & 255) << 24) +
////                    ((packet.data[6] & 255) << 16) +
////                    ((packet.data[7] & 255) <<  8) +
////                    ((packet.data[8] & 255) <<  0));
////                    x = ((packet.data[9]&0xFF  << 24) + (packet.data[10]&0xFF  << 16) + (packet.data[11]&0xFF  << 8) + (packet.data[12]&0xFF  << 0));
////                    y = ((packet.data[13]&0xFF  << 8) + (packet.data[14]&0xFF  << 0));
////                    z = ((packet.data[15]&0xFF  << 24) + (packet.data[16]&0xFF  << 16) + (packet.data[17]&0xFF  << 8) + (packet.data[18]&0xFF  << 0));
////                    e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
////                    if(e != null)
////                        e.STORED_EU = (double)val;
////                }
////                System.out.println("setEnergy");
//                break;
                
            //@Deprecated
//            case 9: // back item id
//                System.out.println("backItem");
//                x = ((packet.data[1]&0xFF  << 24) + (packet.data[2]&0xFF  << 16) + (packet.data[3]&0xFF  << 8) + (packet.data[4]&0xFF  << 0));
//                y = ((packet.data[5]&0xFF  << 8) + (packet.data[6]&0xFF  << 0));
//                z = ((packet.data[7]&0xFF  << 24) + (packet.data[8]&0xFF  << 16) + (packet.data[9]&0xFF  << 8) + (packet.data[10]&0xFF  << 0));
//                e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
//                if(e != null){
//                    Trip tri = new Trip(x,y,z);
//                    CReplicator contain = SkyTech.instance.handler.containers.get(tri);
//                    if(contain != null){
//                        contain.setItemIndex(Math.abs(contain.getItemIndex()-1));
//                    }else{
//                        System.out.println("CONTAIN NULL");
//                        for(Trip t : SkyTech.instance.handler.containers.keySet()){
//                            System.out.println(t.x + " " + t.y + " " + t.z + "("+tri.x + " " + tri.y + " " + tri.z+")");
//                        }
//                    }
////                    e.
////                    e.blockType.cre
//                }System.out.println("->backItem");
//                break;
            
                //@Deprecated
//            case 10: // forward item id
//                System.out.println("forwardItem");
//                x = ((packet.data[1]&0xFF  << 24) + (packet.data[2]&0xFF  << 16) + (packet.data[3]&0xFF  << 8) + (packet.data[4]&0xFF  << 0));
//                y = ((packet.data[5]&0xFF  << 8) + (packet.data[6]&0xFF  << 0));
//                z = ((packet.data[7]&0xFF  << 24) + (packet.data[8]&0xFF  << 16) + (packet.data[9]&0xFF  << 8) + (packet.data[10]&0xFF  << 0));
//                e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
//                if(e != null){
//                    Trip tri = new Trip(x,y,z);
//                    CReplicator contain = SkyTech.instance.handler.containers.get(tri);
//                    if(contain != null){
//                        contain.setItemIndex(Math.abs(contain.getItemIndex()+1));
//                    }else{
//                        System.out.println("CONTAIN NULL");
//                        for(Trip t : SkyTech.instance.handler.containers.keySet()){
//                            System.out.println(t.x + " " + t.y + " " + t.z + "("+tri.x + " " + tri.y + " " + tri.z+")");
//                        }
//                    }
////                    e.
////                    e.blockType.cre
//                }System.out.println("->forwardItem");
//                break;
                
                //@Deprecated
//            case 11: // set item
//                x = din.readInt();
//                y = din.readShort();
//                z = din.readInt();
//                int rawid = din.readInt();
////                x = ((packet.data[1]&0xFF  << 24) + (packet.data[2]&0xFF  << 16) + (packet.data[3]&0xFF  << 8) + (packet.data[4]&0xFF  << 0));
////                y = ((packet.data[5]&0xFF  << 8) + (packet.data[6]&0xFF  << 0));
////                z = ((packet.data[7]&0xFF  << 24) + (packet.data[8]&0xFF  << 16) + (packet.data[9]&0xFF  << 8) + (packet.data[10]&0xFF  << 0));
////                int id = ((packet.data[11]&0xFF  << 8) + (packet.data[12]&0xFF  << 0));
////                short id = (short)((packet.data[11]&0xFF << 8) + (packet.data[12]&0xFF << 0));
//                // THIS IS REDICULOUS WHY DOESNT THE ABOVE WORK, OH WELL INSANE HAX TIME
////                String hex0 = Integer.toHexString(packet.data[11]&0xFF);
////                if(hex0.length()==1)hex0 = "0" + hex0;
////                String hex1 = Integer.toHexString(packet.data[12]&0xFF);
////                if(hex1.length()==1)hex1 = "0" + hex1;
////                
////                int id = Integer.parseInt("00"+hex0+hex1, 16);
////                System.out.println(id + " -> 0x"+"00"+hex0+hex1);
////                
////                hex0 = Integer.toHexString(packet.data[1]&0xFF);
////                if(hex0.length()==1)hex0 = "0" + hex0;
////                hex1 = Integer.toHexString(packet.data[2]&0xFF);
////                if(hex1.length()==1)hex1 = "0" + hex1;
////                String hex2 = Integer.toHexString(packet.data[3]&0xFF);
////                if(hex2.length()==1)hex2 = "0" + hex2;
////                String hex3 = Integer.toHexString(packet.data[4]&0xFF);
////                if(hex3.length()==1)hex3 = "0" + hex3;
////                
////                x = Integer.parseInt(hex0+hex1+hex2+hex3,16);
////                
////                hex0 = Integer.toHexString(packet.data[5]&0xFF);
////                if(hex0.length()==1)hex0 = "0" + hex0;
////                hex1 = Integer.toHexString(packet.data[6]&0xFF);
////                if(hex1.length()==1)hex1 = "0" + hex1;
////
////                
////                y = Integer.parseInt("00"+hex0+hex1,16);
////                
////                hex0 = Integer.toHexString(packet.data[7]&0xFF);
////                if(hex0.length()==1)hex0 = "0" + hex0;
////                hex1 = Integer.toHexString(packet.data[8]&0xFF);
////                if(hex1.length()==1)hex1 = "0" + hex1;
////                hex2 = Integer.toHexString(packet.data[9]&0xFF);
////                if(hex2.length()==1)hex2 = "0" + hex2;
////                hex3 = Integer.toHexString(packet.data[10]&0xFF);
////                if(hex3.length()==1)hex3 = "0" + hex3;
////                
////                z = Integer.parseInt(hex0+hex1+hex2+hex3,16);
////                
////                
////                //END INSANE HAX, SERIOUSLY WTF
//                
//                e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
//                if(e != null){
//                    Trip tri = new Trip(x,y,z);
//                    CReplicator contain = SkyTech.instance.handler.containers.get(tri);
//                    if(contain != null){
//                        contain.setItemIndex(rawid);
//                    }else{
//                        System.out.println("CONTAIN NULL");
//                        for(Trip t : SkyTech.instance.handler.containers.keySet()){
//                            System.out.println(t.x + " " + t.y + " " + t.z + "("+tri.x + " " + tri.y + " " + tri.z+")");
//                        }
//                    }
////                    e.
////                    e.blockType.cre
//                }else{System.out.println("TEReplicator["+x+","+y+","+z+"] == NULL!");}System.out.println("->setItem " + rawid + "{0x"+Integer.toHexString(packet.data[11]&0xFF)+",0x"+Integer.toHexString(packet.data[12]&0xFF)+"}");
//                break;
                
            default: System.out.println("ERR: Unknown opcode: " + packet.data[0]);break;
        }
//        System.out.println();
        }catch(Throwable t){}
    }
    
}
