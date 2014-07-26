/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

/**
 *
 * @author Luke
 */
public class ReplicatorNet implements IPacketHandler {

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
                x = din.readInt();
                y = din.readInt();
                z = din.readInt();
                e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
                e.replicateServer();
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
                
            case 8: // set energy (long)
                long val = din.readLong();
                x = din.readInt();
                y = din.readShort();
                z = din.readInt();
                e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
                if(e != null)
                    e.STORED_EU = (double)val;
//                if(packet.length == 19){
//                    long val = (((long)packet.data[1] << 56) +
//                    ((long)(packet.data[2] & 255) << 48) +
//                    ((long)(packet.data[3] & 255) << 40) +
//                    ((long)(packet.data[4] & 255) << 32) +
//                    ((long)(packet.data[5] & 255) << 24) +
//                    ((packet.data[6] & 255) << 16) +
//                    ((packet.data[7] & 255) <<  8) +
//                    ((packet.data[8] & 255) <<  0));
//                    x = ((packet.data[9]&0xFF  << 24) + (packet.data[10]&0xFF  << 16) + (packet.data[11]&0xFF  << 8) + (packet.data[12]&0xFF  << 0));
//                    y = ((packet.data[13]&0xFF  << 8) + (packet.data[14]&0xFF  << 0));
//                    z = ((packet.data[15]&0xFF  << 24) + (packet.data[16]&0xFF  << 16) + (packet.data[17]&0xFF  << 8) + (packet.data[18]&0xFF  << 0));
//                    e = (TEReplicator)(((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z));
//                    if(e != null)
//                        e.STORED_EU = (double)val;
//                }
//                System.out.println("setEnergy");
                break;
                
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
