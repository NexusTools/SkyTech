/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.replicator;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldProvider;

/**
 *
 * @author Luke
 */
public class CP extends WorldProvider {
    
//    HashMap<
    
    {
//        System.exit(0);
    }

    @Override
    public String getDimensionName() {
        return "Skyblock";
    }
    
//    this

    @Override
    public ChunkCoordinates getSpawnPoint() {
        return new ChunkCoordinates(0,128,0);
//        return super.getSpawnPoint(); //To change body of generated methods, choose Tools | Templates.
    }
    
//    this.

    @Override
    public ChunkCoordinates getEntrancePortalLocation() {
        return super.getEntrancePortalLocation(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getRespawnDimension(EntityPlayerMP player) {
        return super.getRespawnDimension(player); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getHorizon() {
        return 0.0D; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getActualHeight() {
        return super.getActualHeight(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getCloudHeight() {
        return 200F; //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public int getAverageGroundLevel() {
        return 1;
    }
    
    @Override
    public ChunkCoordinates getRandomizedSpawnPoint() {
        return getSpawnPoint();
    }
}