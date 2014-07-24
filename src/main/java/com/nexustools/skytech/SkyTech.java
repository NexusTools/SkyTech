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
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

@Mod(modid = SkyTech.MODID, version = SkyTech.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels={"ReplicatorGUI"}, packetHandler = ReplicatorNet.class)
public class SkyTech {

    public static final String MODID = "skytech";
    public static final String VERSION = "1.0";

    Block BReplicator;
    Block BCommand;
    
    public GHandler handler;
    
    public SkyTech(){
        DimensionManagerregisterProviderType(0, CP.class, true); // INSANE HACKS
    }
    
    @Instance("skytech")
    public static SkyTech instance = new SkyTech();
    
    private void DimensionManagerregisterProviderType(int i, Class<CP> man, boolean b) {
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
                
    @EventHandler
    public void init(FMLInitializationEvent event) {
        
//        MinecraftForge.
        BasicSkyblockScript.inject();
        try {
            Cache.validateCache(Minecraft.getMinecraft().mcDataDir);
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
