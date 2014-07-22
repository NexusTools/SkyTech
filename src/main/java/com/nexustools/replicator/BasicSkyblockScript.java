/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.replicator;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.common.DimensionManager;

/**
 *
 * @author Luke
 */



public class BasicSkyblockScript {

    static WorldType t = null;
        
    public float res = 256f;
    
    float trg = 0.5f;
    static void inject() {
//        DimensionManager.reg
        t = new WorldType(13, "Skyblock"){
//            this.on
//            this.ge
//            this.
//            this.

//            private void DimensionManagerregisterProviderType(int i, Class<CP> man, boolean b) {
//                try {
//                    Field f = DimensionManager.class.getClass().getDeclaredField("providers");
//                    f.setAccessible(true);  
//                    ((Hashtable<Integer, Class<? extends WorldProvider>>)f.get(null)).put(i, man);
////            if(f.isAccessible()){  
////                result = (String) f.get(null);  
////            }else {  
////                return "";  
////            }  
//                } catch (NoSuchFieldException ex) {
//                    Logger.getLogger(BasicSkyblockScript.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (SecurityException ex) {
//                    Logger.getLogger(BasicSkyblockScript.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IllegalArgumentException ex) {
//                    Logger.getLogger(BasicSkyblockScript.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IllegalAccessException ex) {
//                    Logger.getLogger(BasicSkyblockScript.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//            }
//            
//            @Override
//            public void onGUICreateWorldPress() {
//                DimensionManagerregisterProviderType(0, CP.class, true); // INSANE HACKS
//                super.onGUICreateWorldPress(); //To change body of generated methods, choose Tools | Templates.
//            }

            @Override
            public int getSpawnFuzz() {
                return 1;
//                return super.getSpawnFuzz(); //To change body of generated methods, choose Tools | Templates.
            }
            
//            this.get
            

//            WorldChunkManager wcm = null;
//            this

            @Override
            public IChunkProvider getChunkGenerator(final World world, String generatorOptions) {
//                this.on
//                this.getSpawnFuzz()
                return new ChunkProviderGenerate(world, world.getSeed(), false){
//                    this
//                    this.
                    

                    @Override
                    public Chunk provideChunk(int par1, int par2) {
                        short[] blocks = new short[16*16*256];
                        byte[] metas = new byte[16*16*256];
                        int v = 0;
                        int b = 0;
                        short topblock = 0;
                        short filler = 0;
                        float nlx = 0;
                        float nlz = 0;

                        try{

                        }catch(Throwable e){System.out.println("OMFG SIMPLEXPROVIDER ENCOUNTERED AN ERROR! WTF IS THIS?");e.printStackTrace();}

                        Chunk ret = new Chunk(world, blocks, metas, par1, par2);

                        ret.generateSkylightMap();

                        return ret;
                    }
                };
            }

        
        };
    }
    
}
