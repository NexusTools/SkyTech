/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;

/**
 *
 * @author Luke
 */
public class CPNETH extends ChunkProviderHell {

    World worldObj;
    Random hellRNG;
    MapGenBase netherCaveGenerator = new MapGenCavesHell();
    
    public CPNETH(World w, long seed) {
        super(w, seed);
        worldObj = w;
        hellRNG = new Random(seed);
    }
    
//    Rand
    
    InstancedSimplex inst = new InstancedSimplex();

    public static final float res = 166f;
    
    @Override
    public Chunk provideChunk(int par1, int par2)
    {
        this.hellRNG.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        byte[] abyte = new byte[32768];
        this.generateNetherTerrain(par1, par2, abyte);
        this.replaceBlocksForBiome(par1, par2, abyte);
        this.netherCaveGenerator.generate(this, this.worldObj, par1, par2, abyte);
        this.genNetherBridge.generate(this, this.worldObj, par1, par2, abyte);
        float xt = 0;
        float zt = 0;
        float noise = 0;
//        System.out.println("WAAAAAHHAHHAHAHHAHA");
        for(int x = 0; x < 16; x++){
            for(int z = 0; z < 16; z++){
                for(int y = 0; y < 128; y++){
                    xt = ((float)(x+par1*16))/res;
                    zt = ((float)(z+par2*16))/res;
                    noise = inst.noise(xt, ((float)y)/res, zt);
                    noise += 1f;
                    noise /= 2f;
                    if(noise < 0.89f){
                        //(int)(x) << 11 | (int)(y) << 7 | (int)(z)
//                        abyte[x << 11 | y << 7 | z] = 0;
                        abyte[x << 11 | z << 7 | y] = 0;
                    }
                }
            }
        }
//        abyte = new byte[32768];
        Chunk chunk = new Chunk(this.worldObj, abyte, par1, par2);
        BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, par1 * 16, par2 * 16, 16, 16);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
        {
            abyte1[k] = (byte)abiomegenbase[k].biomeID;
        }

        chunk.resetRelightChecks();
        return chunk;
    }
    
}
