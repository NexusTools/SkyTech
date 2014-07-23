/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.replicator;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;

/**
 *
 * @author Luke
 */
public class BCommand extends BlockCommandBlock {
    
    HashMap<String, Point> occupancies = new HashMap<String, Point>();

    public BCommand(int par1) {
        super(par1);
        loaddb();
        setTextureName("command_block");
    }

    @Override
    public boolean onBlockActivated(final World w, int par2, int par3, int par4, final EntityPlayer p, int par6, float par7, float par8, float par9) {
        final Side side = FMLCommonHandler.instance().getEffectiveSide();
        String str = p.getEntityName();
        if(!occupancies.containsKey(str)){
            Point poi = getNextAvailableProperty();
            occupancies.put(str, poi);
            savedb();
        }
        
        final Point poi = occupancies.get(str);
        
        RB chck = StructureGen.skyblock.get(0); // bedrock
        
        if(w.getBlockId(chck.x+poi.x*512, chck.y, chck.z + poi.y*512) != 15111) // haaaax
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
                                    t.setInventorySlotContents(i, b.inv[i]);
                                }
                                ChatMessageComponent ct = new ChatMessageComponent();
                                ct.addText("You have been apportioned a few items to get you started, check the nearby chest!");
                                if(side.isServer())p.sendChatToPlayer(ct);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(BCommand.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }).start(); // INSANE HACKS, but minecraft REALLY doesnt like getting the tile entity right after setting the block id...

                }
            }
//        w.upd
        p.setLocationAndAngles(poi.x*512, 67, poi.y*512, 0, 0);
//        par5EntityPlayer.setL
        return true;
//        System.exit(0);-
//        return false;
//        return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9); //To change body of generated methods, choose Tools | Templates.
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
    
}
