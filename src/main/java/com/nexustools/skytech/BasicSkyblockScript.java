/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

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
    static void inject() {
        
        t = new WorldType(13, "Skyblock"){
            
            @Override
            public int getSpawnFuzz() {
                return 1;
            }
            @Override
            public IChunkProvider getChunkGenerator(final World world, String generatorOptions) {
                
                return new ChunkProviderGenerate(world, world.getSeed(), false){
                    @Override
                    public Chunk provideChunk(int par1, int par2) {
                        short[] blocks = new short[16*16*256];
                        byte[] metas = new byte[16*16*256];

                        Chunk ret = new Chunk(world, blocks, metas, par1, par2);

                        ret.generateSkylightMap();

                        return ret;
                    }
                };
            }
        };
    }
    
}
