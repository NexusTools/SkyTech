package com.nexustools.skytech;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.ChunkProviderEvent.InitNoiseField;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
//import net.minecraft.world.gen.ChunkProviderHell
//import net.minecraftforge.event.terraingen.

@Mod(modid = SkyTech.MODID, version = SkyTech.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels={"ReplicatorGUI"}, packetHandler = ReplicatorNet.class)
public class SkyTech {

    public static final String MODID = "skytech";
    public static final String VERSION = "1.0";

    Block BReplicator;
    Block BCommand;
    
    public GHandler handler;
    
    public SkyTech(){

    }
    
    @Instance("skytech")
    public static SkyTech instance = new SkyTech();
    
    private void DimensionManagerregisterProviderType(int i, Class<? extends WorldProvider> man, boolean b) {
        try {
            Field f = DimensionManager.class.getDeclaredField("providers");
            f.setAccessible(true);
            Hashtable<Integer, Class<? extends WorldProvider>> val = ((Hashtable<Integer, Class<? extends WorldProvider>>) f.get(null));
            val.remove(i);
            val.put(i, man);
            f.set(null, val);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

    }
    public static final int REPLICATOR_BLOCKID = 1102;
    
    
    public static final float res = 216f;
//    
//    @ForgeSubscribe
//    public void noise(InitNoiseField e){
////        e.
//        if(e.chunkProvider instanceof ChunkProviderHell){
//            System.out.println("NTHR " + e.sizeX + ", " + e.sizeY + ", " + e.sizeZ);
//            e.setResult(Event.Result.DENY);
//            e.noisefield = new double[e.sizeX * e.sizeY * e.sizeZ];
//            for(float x = e.posX; x < e.posX + e.sizeX; x++){
//                for(float z = e.posZ; z < e.posZ + e.sizeZ; z++){
//                    for(float y = e.posY; y < e.posY + e.sizeY; y++){
//                        e.noisefield[(int)((x-e.posX) + (y-e.posY) * e.sizeY + (z-e.posZ) * e.sizeZ)] = inst.noise(x/res, y/res, z/res);
//                    }
//                }
//            }
//        }
//    }
    
//    @ForgeSubscribe
//    public void chunk(ChunkProviderEvent.ReplaceBiomeBlocks e){
//        
//        if(e.chunkProvider instanceof ChunkProviderHell){
//            System.out.println("NATHR");
////            ChunkProviderHell h = (ChunkProviderHell)e.chunkProvider;
//            float chofx = e.chunkX * 16;
//            float chofz = e.chunkZ * 16;
//            for(int i = 0; i < e.blockArray.length; i++){
//                e.blockArray[i] = 0;
//            }
////            for(float x = 0; x < 16; x++){
////                for(float z = 0; z < 16; z++){
////                    for(float y = 0; y < 256; y++){
////                        float octive = 1f+inst.noise((x+chofx)/res, y/res, (z+chofz)/res);
////                        octive /= 2;
////                        if(true){
////                            e.blockArray[(int)(x) << 11 | (int)(y) << 7 | (int)(z)] = 0;
////                        }
////                    }
////                }
////            }
////            e.
//            e.setResult(Event.Result.DENY);
////            System.out.println("NTHR " + e.sizeX + ", " + e.sizeY + ", " + e.sizeZ);
////            e.setResult(Event.Result.DENY);
////            e.noisefield = new double[e.sizeX * e.sizeY * e.sizeZ];
////            for(float x = e.posX; x < e.posX + e.sizeX; x++){
////                for(float z = e.posZ; z < e.posZ + e.sizeZ; z++){
////                    for(float y = e.posY; y < e.posY + e.sizeY; y++){
////                        e.noisefield[(int)((x-e.posX) + (y-e.posY) * e.sizeY + (z-e.posZ) * e.sizeZ)] = inst.noise(x/res, y/res, z/res);
////                    }
////                }
////            }
//        }
//       // temporary hack (affects EVERYTHING D:)
////            float chofx = e.chunkX * 16;
////            float chofz = e.chunkZ * 16;
////            for(float x = 0; x < 16; x++){
////                for(float z = 0; z < 16; z++){
////                    for(float y = 0; y < 257; y++){
////                        float octive = 1f+inst.noise((x+chofx)/res, y/res, (z+chofz)/res);
////                        octive /= 2;
////                        if(octive < 0.84f){
////                            try{
////                            e.world.setBlockToAir((int)(x+chofx), (int)y, (int)(z+chofz));
////                            }catch(Throwable t){
////                            System.out.println("Error setting block to air! " + (int)(x+chofx) + ", " + (int)y + ", " + (int)(z+chofz) + "(" + x + ", " + y + ", " + z + ")");
////                            }
////                        }
////                    }
////                }
////            }
////        e.world.provider.dimensionId;
////        e
////        e.
////        if(e.world.provider.dimensionId == -1){ // nether
////            float chofx = e.chunkX * 16;
////            float chofz = e.chunkZ * 16;
////            for(float x = 0; x < 16; x++){
////                for(float z = 0; z < 16; z++){
////                    for(float y = 0; y < 257; y++){
////                        float octive = 1f+inst.noise((x+chofx)/res, y/res, (z+chofz)/res);
////                        octive /= 2;
////                        if(octive < 0.84f){
////                            try{
////                            e.world.setBlockToAir((int)(x+chofx), (int)y, (int)(z+chofz));
////                            }catch(Throwable t){
////                            System.out.println("Error setting block to air! " + (int)(x+chofx) + ", " + (int)y + ", " + (int)(z+chofz) + "(" + x + ", " + y + ", " + z + ")");
////                            }
////                        }
////                    }
////                }
////            }
////        }
//    }
//                
    
    
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        
//        MinecraftForge.
        BasicSkyblockScript.inject();
        DimensionManagerregisterProviderType(0, CP.class, true); // INSANE HACKS
        DimensionManagerregisterProviderType(-1, NETH.class, true);
        
        try {
//            Cache.validateCache(Minecraft.getMinecraft().mcDataDir);
//            Cache.validateCache(new File("/home/minecraft/skytech/")); // TEMPORARY SERVER HACK FIX
            Cache.validateCache(new File(System.getProperty("user.home")));
        } catch (IOException ex) {
            Logger.getLogger(SkyTech.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BReplicator = new BReplicator(REPLICATOR_BLOCKID);
        BCommand = new BCommand(REPLICATOR_BLOCKID+1);
        handler = new GHandler();
        
        GameRegistry.registerBlock(BCommand, "Command");
        LanguageRegistry.addName(BCommand, "Command");
        
        GameRegistry.registerBlock(BReplicator, "Replicator");
        LanguageRegistry.addName(BReplicator, "Replicator");
        
        GameRegistry.registerTileEntity(TEReplicator.class, "TEReplicator");
        
        NetworkRegistry.instance().registerGuiHandler(instance, handler);
   
        MinecraftForge.EVENT_BUS.register(instance);
        
    }

    
    boolean stillNeedsHackInjection = true;

}
