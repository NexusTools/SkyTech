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
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 *
 * @author Luke
 */
public class NetUtil {
    public static void sendPacket(String chan, byte[] pack, EntityPlayer player) {
        if(pack == null || chan == null || player == null){
            System.out.println("ERR: NULLNESS: " + (chan==null?"true, ":"false, ") + (pack==null?"true, ":"false, ") + (player==null?"true":"false"));
            return;
        }else{
//            System.out.println("[NU]sendPacket(" + chan + ", byte[" + pack.length + "], " + player.getDisplayName() + ")");
        }
        Packet250CustomPayload cust = new Packet250CustomPayload();
        cust.channel = chan;
        cust.length = pack.length;
        cust.data = pack;
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
            // We are on the server side.
            EntityPlayerMP mplayer = (EntityPlayerMP) player;
            PacketDispatcher.sendPacketToPlayer(cust, (Player) mplayer);
        } else if (side == Side.CLIENT) {
            EntityClientPlayerMP mplayer = (EntityClientPlayerMP) player;
//            if(cust.channel == null) System.exit(420);
            mplayer.sendQueue.addToSendQueue(cust);
            // We are on the client side.
        } else {
            // We have an errornous state!
        }
    }
}
