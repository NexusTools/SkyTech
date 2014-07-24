/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;

/**
 *
 * @author Luke
 */
public class CP extends WorldProvider {
    
    @Override
    public String getDimensionName() {
        return "Skyblock";
    }
    
    boolean hak = true;
    
    @Override
    public ChunkCoordinates getSpawnPoint() {
        if(hak){
            hak = false;
            for(RB rb : StructureGen.lobby){
                this.worldObj.setBlock(rb.x, rb.y, rb.z, rb.id, rb.meta, rb.flags);
                
                if(rb.tileEntity){
//                    this.worldObj.setBlockMetadataWithNotify(rb.x, rb.y, rb.z, rb.meta, rb.flags);
                    TileEntity e = this.worldObj.getBlockTileEntity(rb.x, rb.y, rb.z);
//                    this.worldObj
                    if(rb.nbt != null && e != null)
                        e.writeToNBT(rb.nbt);
                }
//                this.worldObj.setN
            }
        }
//        try{throw new Throwable("stack");}catch(Throwable t){t.printStackTrace();}
        return new ChunkCoordinates(0,40,-3);
    }

    @Override
    public ChunkCoordinates getEntrancePortalLocation() {
        return super.getEntrancePortalLocation();
    }

    @Override
    public int getRespawnDimension(EntityPlayerMP player) {
        return super.getRespawnDimension(player);
    }

    @Override
    public double getHorizon() {
        return 0.0D;
    }

    @Override
    public int getActualHeight() {
        return super.getActualHeight();
    }

    @Override
    public float getCloudHeight() {
        return 16F;
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