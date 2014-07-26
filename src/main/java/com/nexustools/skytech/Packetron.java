/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 *
 * @author Luke
 */
public class Packetron {
    public static Packet250CustomPayload generatePacket(int opcode, int ... data){
//        Packet250CustomPayload pl = new Packet250CustomPayload();
//        pl.channel = "ReplicatorGUI";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(out);
        out.write(opcode);
        
        try{
            for(int i : data)dout.writeInt(i);
            out.flush();
            out.close();
        }catch(Throwable t){}
        
//        pl.data = out.toByteArray();
//        pl.length = data.length;
        
        return new Packet250CustomPayload("ReplicatorGUI", out.toByteArray());
    }
}
