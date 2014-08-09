/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Luke
 */
public class GReplicator extends GuiContainer {

    TEReplicator ent;
    EntityPlayer ply;
    
//    /*
//                itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.getTextureManager(), itemstack, i, j);
//            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.getTextureManager(), itemstack, i, j, s);
//    */
//    
//    private void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str)
//    {
//        RenderHelper.enableGUIStandardItemLighting();
//        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
//        this.zLevel = 200.0F;
//        itemRenderer.zLevel = 200.0F;
//        FontRenderer font = null;
//        if (par1ItemStack != null) font = par1ItemStack.getItem().getFontRenderer(par1ItemStack);
//        if (font == null) font = fontRenderer;
//        itemRenderer.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3);
//        itemRenderer.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3 - (true ? 0 : 8), par4Str);
//        this.zLevel = 0.0F;
//        itemRenderer.zLevel = 0.0F;
//    }
    
    public GReplicator(InventoryPlayer play, TEReplicator ent){
        super(new CReplicator(play, ent));
        this.ent = ent;
        this.ply = play.player;
        ItemValueDatabase.checkScrape(null);
    }
    
    int lmx = 0;
    int lmy = 0;
//    
//    void setItemID(int id){
//        try {
//            Packet250CustomPayload pl = new Packet250CustomPayload();
//            pl.channel = "ReplicatorGUI";
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            DataOutputStream dout = new DataOutputStream(out);
//            dout.write(11);
//            dout.writeInt(ent.xCoord);
//            dout.writeShort(ent.yCoord);
//            dout.writeInt(ent.zCoord);
//            dout.writeInt(id);
//            pl.data = out.toByteArray();
//            pl.length = pl.data.length;
////        pl.length = 13;
////
////        pl.data = new byte[13];
////        pl.data[0] = 11;
////        pl.data[1] = (byte) (char) ((ent.xCoord >>> 24) & 0xFF);
////        pl.data[2] = (byte) (char) ((ent.xCoord >>> 16) & 0xFF);
////        pl.data[3] = (byte) (char) ((ent.xCoord >>> 8) & 0xFF);
////        pl.data[4] = (byte) (char) ((ent.xCoord >>> 0) & 0xFF);
////        pl.data[5] = (byte) (char) ((ent.yCoord >>> 8) & 0xFF);
////        pl.data[6] = (byte) (char) ((ent.yCoord >>> 0) & 0xFF);
////        pl.data[7] = (byte) (char) ((ent.zCoord >>> 24) & 0xFF);
////        pl.data[8] = (byte) (char) ((ent.zCoord >>> 16) & 0xFF);
////        pl.data[9] = (byte) (char) ((ent.zCoord >>> 8) & 0xFF);
////        pl.data[10] = (byte) (char) ((ent.zCoord >>> 0) & 0xFF);
////        pl.data[11] = (byte) (char) ((id >>> 8) & 0xFF);
////        pl.data[12] = (byte) (char) ((id >>> 0) & 0xFF);
//            PacketDispatcher.sendPacketToServer(pl); 
//        } catch (IOException ex) {
//            Logger.getLogger(GReplicator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    void moveItemID(int amt){
//        Packet250CustomPayload pl = new Packet250CustomPayload();
//        pl.channel = "ReplicatorGUI";
//        pl.length = 11;
////        Block.blockClay.getPickBlock(null, null, lmx, lmy, bt)
////        Block.blockClay.id
//        switch(amt){
//            case -1:
//                pl.data = new byte[11];
//                pl.data[0] = 9;
//                pl.data[1] = (byte) (char) ((ent.xCoord >>> 24) & 0xFF);
//                pl.data[2] = (byte) (char) ((ent.xCoord >>> 16) & 0xFF);
//                pl.data[3] = (byte) (char) ((ent.xCoord >>> 8) & 0xFF);
//                pl.data[4] = (byte) (char) ((ent.xCoord >>> 0) & 0xFF);
//                pl.data[5] = (byte) (char) ((ent.yCoord >>> 8) & 0xFF);
//                pl.data[6] = (byte) (char) ((ent.yCoord >>> 0) & 0xFF);
//                pl.data[7] = (byte) (char) ((ent.zCoord >>> 24) & 0xFF);
//                pl.data[8] = (byte) (char) ((ent.zCoord >>> 16) & 0xFF);
//                pl.data[9] = (byte) (char) ((ent.zCoord >>> 8) & 0xFF);
//                pl.data[10] = (byte) (char) ((ent.zCoord >>> 0) & 0xFF);
//                break;
//            case 1:
//                pl.data = new byte[11];//
//                pl.data[0] = 10;
//                pl.data[1] = (byte) (char) ((ent.xCoord >>> 24) & 0xFF);
//                pl.data[2] = (byte) (char) ((ent.xCoord >>> 16) & 0xFF);
//                pl.data[3] = (byte) (char) ((ent.xCoord >>> 8) & 0xFF);
//                pl.data[4] = (byte) (char) ((ent.xCoord >>> 0) & 0xFF);
//                pl.data[5] = (byte) (char) ((ent.yCoord >>> 8) & 0xFF);
//                pl.data[6] = (byte) (char) ((ent.yCoord >>> 0) & 0xFF);
//                pl.data[7] = (byte) (char) ((ent.zCoord >>> 24) & 0xFF);
//                pl.data[8] = (byte) (char) ((ent.zCoord >>> 16) & 0xFF);
//                pl.data[9] = (byte) (char) ((ent.zCoord >>> 8) & 0xFF);
//                pl.data[10] = (byte) (char) ((ent.zCoord >>> 0) & 0xFF);
//                break;
//                
//                
//            default: return;
//        }
//        
//        PacketDispatcher.sendPacketToServer(pl); 
//    }
    
//    this.mou
    
    final static int BTN_X=116,BTN_Y=54,  BTN_W=55,BTN_H=14;
    
    @Override
    protected void mouseClicked(int x, int y, int par3) {
        super.mouseClicked(x, y, par3); //To change body of generated methods, choose Tools | Templates.
        lmx = x-xOffs;
        lmy = y-yOffs;
//        System.out.println("MPRESS " + x + ", " + y);
        
        //Args: left, top, width, height, pointX, pointY.     Note:  left,top are local to the Gui   pointX,pointY are local to the screen
        boolean b = this.isPointInRegion(BTN_X, BTN_Y, BTN_W, BTN_H, x, y);
        
        if(b)ent.replicateClient();

    }

    @Override
    public void onGuiClosed() {
        // hackish but I cant do something better right now
        ent.watchedBy.clear();
        super.onGuiClosed();
    }
    
    static int texid = 0;
    boolean loaded = false;
   
    boolean typing = true;
    String tstring = "";
    String suggest = "";
    String suggestcar = "";
    
    void setSearchItem(int uuid){
        
        int id =  (uuid >> 16);
        int meta =  (uuid & 0xffff);
        
        if(id>0){
            try{
                ItemStack is = new ItemStack(Item.itemsList[id], 1);
                is.setItemDamage(meta);
                this.ent.setSearchSlotContentsClient(is, uuid);
            }catch(Throwable t){}
        }
        
    }

    @Override
    protected void keyTyped(char par1, int par2) { // really needs to be cleaned up
//        System.out.println(par1);
//        System.out.println(par2);
        if(par2 == 1) {super.keyTyped(par1, par2);return;}//return super.keyTyped(par1, par2); // I MISS C
        boolean strc = false;
        switch(par2){
            case 28: /*typing = !typing;*/break;
            case 14: if(tstring.length()>0){tstring = tstring.substring(0,tstring.length()-1);strc=true;}break;
            case 1: if(typing)super.keyTyped(par1, par2);break; // escape
            default: if(typing) {tstring += par1;strc = true;} break;
        }
        if(!typing)super.keyTyped(par1, par2);
        int rawid = -1;
        int id = -1;
        int meta = -1;
        if(strc){
            int smallest = Integer.MAX_VALUE;
            String smst = "";
            for(String s : ItemValueDatabase.database.keySet()){
                int c = StringDist.compute(s,tstring);
                if(s.startsWith(tstring) || s.equals(tstring)){
                    smallest = Integer.MIN_VALUE;
                    smst = s;
                }
                if(s.contains(tstring) && smallest > Integer.MIN_VALUE){
                    smallest = Integer.MIN_VALUE+1;
                    smst = s;
                }
                if(smallest>c){
                    smallest = c;
                    smst = s;
                }
            }
            
            try{
                rawid = ItemValueDatabase.database.get(smst);
                id =  (rawid >> 16);
                meta =  (rawid & 0xffff);
    //            System.out.println(smst + "-> " + rawid);
                if(smst.length()>1){
                    setSearchItem(rawid);
                    ent.cost = (int) (ItemValueDatabase.values.get(rawid) * TEReplicator.MAX_EU);
                }
            }catch(Throwable t){
                System.out.println("Error getting search item: " + (smst==null?"null":smst));
            }
        }
        
        if(typing && id > 0){
//            suggest = ent.inv == null ? "" : ent.inv.getItem().getItemDisplayName(ent.inv);// haax
            Item itm = Item.itemsList[id];
            if(itm != null){
                suggest = itm.getItemDisplayName(new ItemStack(itm, 1));
                int len = tstring.length();
                char prec = '0';
    //            if(carrot)len++;
                if(len<suggest.length())
                    suggest = suggest.substring(len);
                else suggest = "";

                suggest = suggest.toLowerCase();
                if(suggest.length()>1){
                    suggestcar = suggest.substring(1);
                }else suggestcar = "";

                carrotc = suggest.length()>0?suggest.charAt(0):'|';
            }
        }
        
    }
    
    void drawTexturedRect(int x, int y, int texx, int texy, int w, int h){
        drawTexturedModalRect(x,y,texx,texy,w,h);
    }
    int incr = 0;
    int swch = 0;
    char carrotc = '|';
    boolean carrot = false;
    double euv = 0;
    long lblink = 0L;
    long rt = 0L;
    long req = 0L;
    int bt = 32;
    
    int xOffs = 0, yOffs = 0;
    
    boolean mouseDownOnButton = false;
    
    static final char colorchar = (char)0xA7;
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int var1, int var2)
    {
        if(!loaded)try {
            loaded = true;
            lblink = System.currentTimeMillis();
            texid = ConvolutedTextureSystemBypass.loadTexture(ImageIO.read(Cache.getInputStream("replicator.png")));
        } catch (IOException ex) {
            Logger.getLogger(GReplicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(swch++>=bt){
            rt = System.currentTimeMillis();
            carrot = ! carrot && typing;
            if(rt - lblink < 500) bt *= 2; else bt /= 2;
            lblink = rt;
            swch = 0;
        }
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texid);

        xOffs = (width - xSize) / 2;
        yOffs = (height - ySize) / 2;

        drawTexturedModalRect(xOffs, yOffs, 0, 0, xSize, ySize);

        int i1 = 0;
        euv = ent.STORED_EU;
        req = ent.cost;
        incr = (int) ((euv*6d)/ TEReplicator.MAX_EU);
        drawTexturedRect(xOffs+150, yOffs+8, 192,0,15,16);
        drawTexturedRect(xOffs+8, yOffs+7, 176,incr*16,16,16); // 176 97
        if(ent.enoughEnergyToReplicate()){
            drawTexturedRect(xOffs+116, yOffs+54, 176,97,55,14); // 97 -> 111
        }
//        if(ent.enoughEnergyToSynergize()){
//            drawTexturedRect(x+56, y+53, 192,32,16,16);
//        }else
//            drawTexturedRect(x+56, y+53, 192,16,16,16);

        mc.fontRenderer.drawString(String.valueOf((long)euv)+"EU", xOffs+26, yOffs+12, 0);
        mc.fontRenderer.drawString(String.valueOf((long)req)+"EU", xOffs+92, yOffs+12, 0);

        if(carrot)mc.fontRenderer.drawString(tstring +colorchar+"7"+ carrotc + colorchar+"8" + suggestcar, xOffs+35, yOffs+40, 0);
        else mc.fontRenderer.drawString(tstring + colorchar+"8" + suggest, xOffs+35, yOffs+40, 0);
            
    }
    
}
