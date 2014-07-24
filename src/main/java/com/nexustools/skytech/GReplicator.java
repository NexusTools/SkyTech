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
        ItemNameDatabase.checkScrape();
//        this.i
//        this.drawSlotInventory(null);
//        this.c
        
    }
    
    int lmx = 0;
    int lmy = 0;
    
    void setItemID(int id){
        try {
            Packet250CustomPayload pl = new Packet250CustomPayload();
            pl.channel = "ReplicatorGUI";
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(out);
            dout.write(11);
            dout.writeInt(ent.xCoord);
            dout.writeShort(ent.yCoord);
            dout.writeInt(ent.zCoord);
            dout.writeInt(id);
            pl.data = out.toByteArray();
            pl.length = pl.data.length;
//        pl.length = 13;
//
//        pl.data = new byte[13];
//        pl.data[0] = 11;
//        pl.data[1] = (byte) (char) ((ent.xCoord >>> 24) & 0xFF);
//        pl.data[2] = (byte) (char) ((ent.xCoord >>> 16) & 0xFF);
//        pl.data[3] = (byte) (char) ((ent.xCoord >>> 8) & 0xFF);
//        pl.data[4] = (byte) (char) ((ent.xCoord >>> 0) & 0xFF);
//        pl.data[5] = (byte) (char) ((ent.yCoord >>> 8) & 0xFF);
//        pl.data[6] = (byte) (char) ((ent.yCoord >>> 0) & 0xFF);
//        pl.data[7] = (byte) (char) ((ent.zCoord >>> 24) & 0xFF);
//        pl.data[8] = (byte) (char) ((ent.zCoord >>> 16) & 0xFF);
//        pl.data[9] = (byte) (char) ((ent.zCoord >>> 8) & 0xFF);
//        pl.data[10] = (byte) (char) ((ent.zCoord >>> 0) & 0xFF);
//        pl.data[11] = (byte) (char) ((id >>> 8) & 0xFF);
//        pl.data[12] = (byte) (char) ((id >>> 0) & 0xFF);
            PacketDispatcher.sendPacketToServer(pl); 
        } catch (IOException ex) {
            Logger.getLogger(GReplicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void moveItemID(int amt){
        Packet250CustomPayload pl = new Packet250CustomPayload();
        pl.channel = "ReplicatorGUI";
        pl.length = 11;
//        Block.blockClay.getPickBlock(null, null, lmx, lmy, bt)
//        Block.blockClay.id
        switch(amt){
            case -1:
                pl.data = new byte[11];
                pl.data[0] = 9;
                pl.data[1] = (byte) (char) ((ent.xCoord >>> 24) & 0xFF);
                pl.data[2] = (byte) (char) ((ent.xCoord >>> 16) & 0xFF);
                pl.data[3] = (byte) (char) ((ent.xCoord >>> 8) & 0xFF);
                pl.data[4] = (byte) (char) ((ent.xCoord >>> 0) & 0xFF);
                pl.data[5] = (byte) (char) ((ent.yCoord >>> 8) & 0xFF);
                pl.data[6] = (byte) (char) ((ent.yCoord >>> 0) & 0xFF);
                pl.data[7] = (byte) (char) ((ent.zCoord >>> 24) & 0xFF);
                pl.data[8] = (byte) (char) ((ent.zCoord >>> 16) & 0xFF);
                pl.data[9] = (byte) (char) ((ent.zCoord >>> 8) & 0xFF);
                pl.data[10] = (byte) (char) ((ent.zCoord >>> 0) & 0xFF);
                break;
            case 1:
                pl.data = new byte[11];//
                pl.data[0] = 10;
                pl.data[1] = (byte) (char) ((ent.xCoord >>> 24) & 0xFF);
                pl.data[2] = (byte) (char) ((ent.xCoord >>> 16) & 0xFF);
                pl.data[3] = (byte) (char) ((ent.xCoord >>> 8) & 0xFF);
                pl.data[4] = (byte) (char) ((ent.xCoord >>> 0) & 0xFF);
                pl.data[5] = (byte) (char) ((ent.yCoord >>> 8) & 0xFF);
                pl.data[6] = (byte) (char) ((ent.yCoord >>> 0) & 0xFF);
                pl.data[7] = (byte) (char) ((ent.zCoord >>> 24) & 0xFF);
                pl.data[8] = (byte) (char) ((ent.zCoord >>> 16) & 0xFF);
                pl.data[9] = (byte) (char) ((ent.zCoord >>> 8) & 0xFF);
                pl.data[10] = (byte) (char) ((ent.zCoord >>> 0) & 0xFF);
                break;
                
                
            default: return;
        }
        
        PacketDispatcher.sendPacketToServer(pl); 
    }
    
    @Override
    protected void mouseClicked(int x, int y, int par3) {
        super.mouseClicked(x, y, par3); //To change body of generated methods, choose Tools | Templates.
        lmx = x;
        lmy = y;
        System.out.println("MPRESS " + x + ", " + y);
        
        /*
            162 93    173 101   (back)
            203 93    214 101   (forward)
        */
        
        if(lmx > 162 && lmx < 173 && lmy > 93 && lmy < 101){ // back
            moveItemID(-1);
        }else if (lmx > 203 && lmx < 214 && lmy > 93 && lmy < 101){ // forward
            moveItemID(1);
        }
        
//        if(ent.realinvswch)ent.realinv = null;

    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed(); //To change body of generated methods, choose Tools | Templates.
//        System.exit(420);
        // hackish but I cant do something better right now
        for(int i = 0; i < ent.watchedBy.size(); i++){
//            if(ent.watchedBy.get(i)==null){
//                ent.watchedBy.remove(i);
//            }else
            if(ent.watchedBy.get(i).getDisplayName().equals(ply.getDisplayName())){
//                ent.watchedBy.remove(i);//not working
//                break;
            }
        }
    }
    
    //assets\replicatormod\textures\gui
//    ResourceLocation largeFurnace = new ResourceLocation(ReplicatorMod.MODID, "textures/gui/replicator.png");
    static int texid = 0;
    boolean loaded = false;
//    private ResourceLocation largeFurnace = new ResourceLocation("ReplicatorMod:com/nexustools/replicator/gui.png");
   
    
    boolean typing = true;
    String tstring = "";
    String suggest = "";
    String suggestcar = "";

    @Override
    protected void keyTyped(char par1, int par2) {
        System.out.println(par1);
        System.out.println(par2);
        boolean strc = false;
        switch(par2){
            case 28: typing = !typing;
            case 14: if(tstring.length()>0){tstring = tstring.substring(0,tstring.length()-1);strc=true;}break;
            case 1: if(typing)super.keyTyped(par1, par2); // escape
            default: if(typing) {tstring += par1;strc = true;} break;
        }
        if(!typing)super.keyTyped(par1, par2); //To change body of generated methods, choose Tools | Templates.
        int rawid = -1;
        int id = -1;
        int meta = -1;
        if(strc){
            int smallest = Integer.MAX_VALUE;
            String smst = "";
            for(String s : ItemNameDatabase.database.keySet()){
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
            rawid = ItemNameDatabase.database.get(smst);
            id =  (rawid >> 16);
            meta =  (rawid & 0xffff);
            System.out.println(smst + "-> " + rawid);
            if(smst.length()>1){
                setItemID(rawid);
                ent.cost = (int) (ItemNameDatabase.values.get(rawid) * TEReplicator.MAX_EU);
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
//	    mc.renderEngine.func_110577_a(largeFurnace);
            
//            mc.renderEngine.bindTexture(largeFurnace);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texid);
//            TextureObject to = (TextureObject)mc.renderEngine.mapTextureObjects.get(par1ResourceLocation);
	    int x = (width - xSize) / 2;
	    int y = (height - ySize) / 2;
//            ent.inv.
            
//            System.out.println(x + ", " + y);
	    drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	   
//	   
//	    if(ent.isBurning())
//	    {
//		    int l = ent.getBurnTimeRemainingScaled(12);
//		    drawTexturedModalRect(j + 82, (k + 71) - l, 176, 12 - l, 14, l + 2);
//	    }
//	    int i1 = emt.getCookProgressScaled(24, 0);
//	    int j1 = ent.getCookProgressScaled(24, 1);
            int i1 = 0;
            euv = ent.STORED_EU;
            req = ent.cost;
            incr = (int) ((euv*6d)/ TEReplicator.MAX_EU);
            int j1 = 1;// Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
            
            drawTexturedRect(x+150, y+8, 192,0,15,16);
            
            drawTexturedRect(x+8, y+7, 176,incr*16,16,16);
            
            if(ent.enoughEnergyToSynergize()){
                drawTexturedRect(x+56, y+53, 192,32,16,16);
            }else
                drawTexturedRect(x+56, y+53, 192,16,16,16);
            //Draws the specified string. Args: string, x, y, color
            mc.fontRenderer.drawString(String.valueOf((long)euv)+"EU", x+26, y+12, 0);
            mc.fontRenderer.drawString(String.valueOf((long)req)+"EU", x+92, y+12, 0);
//            drawItemStack(ent.inv, 100,100, "");
//            ItemStack s = ent.inv;
//            if(s != null)
            
            
            
//            carrotc = '|';
            
            
                if(carrot)mc.fontRenderer.drawString(tstring +colorchar+"7"+ carrotc + colorchar+"8" + suggestcar, x+35, y+40, 0);
                else mc.fontRenderer.drawString(tstring + colorchar+"8" + suggest, x+35, y+40, 0);
//            incr++;
//            incr%=6;
//            drawTexturedModalRect
//	    drawTexturedModalRect(j + 77, k + 34, 176, 14, j1 + 1, 16);
//	    drawTexturedModalRect(j + 77, k + 15, 176, 14, i1 + 1, 16);
    }
    
}
