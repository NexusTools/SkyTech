/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.chunk.IChunkProvider;

/**
 *
 * @author Luke
 */
public class NETH extends WorldProviderHell {

    @Override
    public IChunkProvider createChunkGenerator() {
        return new CPNETH(this.worldObj, this.worldObj.getSeed());
    }
    
}
