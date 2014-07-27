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
import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 *
 * @author Luke
 */
public class BCommand extends BlockCommandBlock {
    
    HashMap<String, Point> occupancies = new HashMap<String, Point>();

    public BCommand(int par1) {
        super(par1);
//        loaddb();
        setTextureName("command_block");
    }

    @Override
    public boolean onBlockActivated(final World w, int par2, int par3, int par4, final EntityPlayer p, int par6, float par7, float par8, float par9) {
        final Side side = FMLCommonHandler.instance().getEffectiveSide();
        boolean client = side.isClient();
        if(!client)return true;
        PacketDispatcher.sendPacketToServer(Packetron.generatePacket(17));
//        System.out.println("BCommand.onBlockActivated  " + (client?"this is the client, so returning now":"this is the server, continuing"));
//        if(client) return true; //HAAAX
        
        
        return true;
    }


   
    
}
