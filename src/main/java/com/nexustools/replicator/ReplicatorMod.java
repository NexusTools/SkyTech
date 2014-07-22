package com.nexustools.replicator;

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

@Mod(modid = ReplicatorMod.MODID, version = ReplicatorMod.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels={"ReplicatorGUI"}, packetHandler = ReplicatorNet.class)
public class ReplicatorMod {

    public static final String MODID = "replicatormod";
    public static final String VERSION = "1.0";

    Block BReplicator;
    
    public GHandler handler;
    
    public ReplicatorMod(){
        DimensionManagerregisterProviderType(0, CP.class, true); // INSANE HACKS
    }
    
    @Instance("replicatormod")
    public static ReplicatorMod instance = new ReplicatorMod();
    
    //                for(int i = 0; i < 60*3;i++){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(AMForgeConnector.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    System.out.println("Slept " + i + "/" + 60*5);
//                }
//                for(Integer i : totes.keySet()){
//                    short id = (short) (i >> 16);
//                    short metadata = (short) (i & 0xffff);
//                    int cnt = totes.get(i).val;
//                    String unl = Block.blocksList[id].getUnlocalizedName();
//                    if(unl.contains("ore") || id < 4 || id == 12 || id == 13 || id == 24)
//                        System.out.println("0x"+Integer.toHexString(i).toUpperCase() + "["+id+":"+metadata+"]["+unl+"]["+Block.blocksList[id].getLocalizedName()+"]: " + cnt);
//                }
    
    
//    @EventHandler
//    public void join(EntityJoinWorldEvent event){
//        event.entity.setLocationAndAngles(0,128,0,0,0);
//    }
    
                private void DimensionManagerregisterProviderType(int i, Class<CP> man, boolean b) {
                try {
//                    System.exit(0)
                    Field f = DimensionManager.class.getDeclaredField("providers");
                    f.setAccessible(true);
                    Hashtable<Integer, Class<? extends WorldProvider>> val = ((Hashtable<Integer, Class<? extends WorldProvider>>)f.get(null));
                    val.remove(i);
                    val.put(i, man);
                    f.set(null, val);
//                    System.exit(0);
//            if(f.isAccessible()){  
//                result = (String) f.get(null);  
//            }else {  
//                return "";  
//            }  
                } catch (NoSuchFieldException ex) {
                    ex.printStackTrace();
//                    Logger.getLogger(BasicSkyblockScript.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {ex.printStackTrace();
//                    Logger.getLogger(BasicSkyblockScript.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {ex.printStackTrace();
//                    Logger.getLogger(BasicSkyblockScript.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {ex.printStackTrace();
//                    Logger.getLogger(BasicSkyblockScript.class.getName()).log(Level.SEVERE, null, ex);
                }
//                System.exit(0);
                
            }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        
//        MinecraftForge.
        BasicSkyblockScript.inject();
        try {
            Cache.validateCache(Minecraft.getMinecraft().mcDataDir);
        } catch (IOException ex) {
            Logger.getLogger(ReplicatorMod.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BReplicator = new BReplicator(1102, false);
        handler = new GHandler();
        
        GameRegistry.registerBlock(BReplicator, "Replicator");
        LanguageRegistry.addName(BReplicator, "Replicator");
        
        GameRegistry.registerTileEntity(TEReplicator.class, "TEReplicator");
        
        
        NetworkRegistry.instance().registerGuiHandler(instance, handler);
   
        MinecraftForge.EVENT_BUS.register(instance);
        
    }

    
    boolean stillNeedsHackInjection = true;
//    
////    
//    @ForgeSubscribe
//    public void chunk(PopulateChunkEvent e){
//        System.out.println("CHANK");
//        if(stillNeedsHackInjection){
//            final World pass = e.world;
//            stillNeedsHackInjection = false;
//                new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(60000);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(AMForgeConnector.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                        while(true){
//                            System.out.println("Trying spawn again in 3 seconds...");
//                            try{
//                                Thread.sleep(3000);
//                                AsteroidUtil.spawnAsteroid(0, 0, pass);
//                            }catch(Throwable t){t.printStackTrace();}
//                        }
//
//                }
//            }).start();
//        }
////        int count = 0;
////        HashMap<Integer, AtomicInteger> counts = new HashMap<Integer, AtomicInteger>();
////        for(int x = 0; x < 16; x++){
////            for(int y = 0; y < 255; y++){
////                for(int z = 0; z < 16; z++){
////                    int id = e.world.getBlockId(e.chunkX*16+x, y, e.chunkZ*16+z);
////                    if(id != 0){
////                        int meta = e.world.getBlockMetadata(e.chunkX*16+x, y, e.chunkZ*16+z);
////                        int uid = (id << 16) | (meta & 0xffff);
////                        if(!counts.containsKey(uid))counts.put(uid, new AtomicInteger(0));
////                        counts.get(uid).incrementAndGet();
////                        count++;
////                    }
////                }
////            }
////        }
////        for(Integer i : counts.keySet()){
////            short id = (short) (i >> 16);
////            short metadata = (short) (i & 0xffff);
////            int cnt = counts.get(i).get();
////            if(!totes.containsKey(i))totes.put(i,new RefInt());
////            totes.get(i).val += cnt;
////            System.out.println("0x"+Integer.toHexString(i).toUpperCase() + "["+id+":"+metadata+"]["+Block.blocksList[id].getUnlocalizedName()+"]: " + cnt);
////        }
////        System.out.println(count);
//    }
//
//    @Override
//    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
////
////    @Override
////    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
//////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
////    }
//
//    @Override
//    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void connectionClosed(INetworkManager manager) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
}
