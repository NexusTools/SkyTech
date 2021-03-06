/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

import cpw.mods.fml.common.registry.LanguageRegistry;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 *
 * @author Luke
 */
public class ItemValueDatabase {

    static HashMap<Integer, String> reverse = new HashMap<Integer, String>();
    static HashMap<String, Integer> database = new HashMap<String, Integer>();
    static HashMap<Integer, Integer> rarities = new HashMap<Integer, Integer>();
    static HashMap<Integer, Double> values = new HashMap<Integer, Double>();
    static boolean needsScrape = true;

    public static LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap sortedMap = new LinkedHashMap();

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                Integer comp1 = (Integer)passedMap.get(key);
                Integer comp2 = (Integer) val;

                if (comp1.equals(comp2)) {
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put((Integer) key, (Integer) val);
                    break;
                }

            }

        }
        return sortedMap;
    }

    public static void checkScrape(String signaturemodifierhack) {
//  int uid = (id << 16) | (meta & 0xffff);
//                short id = (short) (uid >> 16);
//                short metadata = (short) (uid & 0xffff);
        if (needsScrape) {
            
            // extra safe bug fix thing
            reverse.clear();
            database.clear();
            rarities.clear();
            values.clear();
            // end
            
            
            needsScrape = false;
            InputStream in = null;
            try {
                in = Cache.getInputStream("commondb-tppi.txt");
            } catch (IOException ex) {
                Logger.getLogger(ItemValueDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(in == null) return;
            String st = "";
            int r = 0;
            byte[] b = new byte[1024];

            try {
                while ((r = in.read(b)) > -1) {
                    st += new String(b, 0, r);
                }
            } catch (IOException ex) {
                Logger.getLogger(ItemValueDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String[] spl = st.split("\n");
            int totes = Integer.parseInt(spl[0]);
            for(int i = 1; i < spl.length; i++){
//                String uln = spl[i];
//                System.out.println(uln);
                String[] data = spl[i].split("\\:"); // 1:0:549934136
                int iid = Integer.parseInt(data[0]);
                int oiid = iid;
                int meta = Integer.parseInt(data[1]);
                Item it = null;
                
                ItemStack is = null;
                
                try{
                   it = Item.itemsList[iid];
                   is = new ItemStack(it, 1);
                }catch(Throwable t){}
                
                iid = (iid << 16) | (meta & 0xffff);
                
                if(is != null){
                    is.setItemDamage(meta);
                    String str = it.getItemDisplayName(is).toLowerCase(); // haaaaax
                    if(str.contains("leaves")){
                        Block bl = Block.blocksList[oiid]; // please work
                        int sapid = bl.idDropped(meta, new Random(), Integer.MAX_VALUE);
                        int sapmet = bl.damageDropped(meta);
                        try{
                            ItemStack tmp = new ItemStack(Item.itemsList[sapid],1);
                            if(tmp != null){
                                tmp.setItemDamage(sapmet);
                                is = tmp;
                                it = Item.itemsList[sapid];
                                meta = sapmet;
                                str = it.getItemDisplayName(is).toLowerCase();
                                iid = (sapid << 16) | (meta & 0xffff);
                            }
                        }catch(Throwable t){System.out.println("SkyTech: Error processing tree leaves: " + str);}
                    }
                    database.put(str, iid);
                    reverse.put(iid, str);
                    int base = 0;
                    if(rarities.containsKey(iid)){
                        base = rarities.get(iid);
                    }
                    int count = Integer.parseInt(data[2].substring(0, data[2].indexOf('&')));
                    String nam = data[2].substring(1+data[2].indexOf('&'));
//                    rarities.put(it.itemID, base+Integer.parseInt(data[2]));
                    rarities.put(iid, base+count);
                }else{
                    String nam = data[2].substring(1+data[2].indexOf('&'));
                    database.put(nam, iid);
                    reverse.put(iid, nam);
                    int base = 0;
                    if(rarities.containsKey(iid)){
                        base = rarities.get(iid);
                    }
                    int count = Integer.parseInt(data[2].substring(0, data[2].indexOf('&')));
//                    rarities.put(it.itemID, base+Integer.parseInt(data[2]));
                    rarities.put(iid, base+count);
                }
            }
            
//            rarities = sortHashMapByValuesD(rarities);
//            
//            HashMap<String, Integer> tdat = new HashMap<String, Integer>();
//            HashMap<Integer, String> tdatr = new HashMap<Integer, String>();
//            
//            for(Integer i : rarities.keySet()){
//                tdat.put(reverse.get(i), i);
//                tdatr.put(i, reverse.get(i));
//            }
//            
//            reverse = tdatr;
//            database = tdat;
//            
//            double goldStandard = rarities.get(Block.oreGold.blockID);
            double stoneStandard = rarities.get((1 << 16) | (0 & 0xffff));
            double dia = rarities.get((Block.oreDiamond.blockID << 16) | (0 & 0xffff));
            double gol = rarities.get((Block.oreGold.blockID<< 16) | (0 & 0xffff));
            double dirtStandard = rarities.get((Block.dirt.blockID<< 16) | (0 & 0xffff));
            double bedStandard = rarities.get((Block.bedrock.blockID<< 16) | (0 & 0xffff));
            
//            System.out.println(stoneStandard);
//            System.out.println(stoneStandard/bedStandard);
//            double goldval = 0.1d; // 10% of MAX_EU
//            double gscalc = goldStandard / goldval;
            
            
            final int hack = (2272 << 16) | (5 & 0xffff);
//            System.out.println(goldStandard);
            
            for(String str : database.keySet()){
                int iid = database.get(str);
                short itid = (short) (iid >> 16);
                short metadata = (short) (iid & 0xffff);
                double rar = (double)rarities.get(database.get(str));
                int times = 0;
                while(rar > gol){ // haax
                    rar /= 2;
                    times ++;
                }
                if(times>1)
                rar *= times/2;
//                if(rar > dia){
//                    rar/=2;
//                }
                double bedperc = 100-(((rar)/bedStandard)*15000);
//                System.out.println((int)bedperc);
                double percval = rar / stoneStandard;
                percval *= 50000;
//                percval /= goldStandard;
//                percval *= 100;
//                if(percval>100)percval = 100;
                percval = Math.round(percval);
                percval = 100 - percval;
                
                percval = bedperc;
                
                if(percval < 1){
                    percval = 1;
                }
                
                String strl = str.toLowerCase();
                
                boolean hak2 = false;
                
                if(!strl.contains("berry")){
                    if(strl.contains("coal") || strl.equals("coal")){ // I like to be overzealous sometimes because I'm too lazy
                        System.out.println("valmod->coal[" + percval + "]");
                        percval = 2;
                    }else

                    if(strl.contains("copper") || strl.contains("tin") || strl.contains("certus")){
                        System.out.println("valmod->coppertincertus[" + percval + "]");
                        percval = 4;
                    }else

                    if(strl.contains("iron")){
//                        percval /= 2;
                        System.out.println("valmod->iron[" + percval + "]");
                        percval = 3;
                    }else
                    if(strl.contains("clay") || strl.equals("clay")){
                        System.out.println("valmod->clay[" + percval + "]");
                        percval = 3;
                    }else
                    if(strl.contains("redstone") || strl.equals("redstone")){
                        System.out.println("valmod->redstone[" + percval + "]");
                        percval /= 3;
                    }else
                    
                    if(strl.contains("gold") || strl.equals("gold")){
                        System.out.println("valmod->gold[" + percval + "]");
                        percval *= 6;
                    }else
                    
                    if(strl.contains("bauxi") || strl.equals("bauxite")){
                        System.out.println("valmod->bauxite[" + percval + "]");
                        hak2 = true;
                        percval /= 2.4f;
                    }else
                        System.out.println("valmod->unknown:" + itid + ":" + metadata + ":" + strl + "[" + percval + "]");
                    
                }
                
                if(!hak2 && hack == iid){
                    System.out.println("Using hacked modifier!!");
                    percval /= 2.4f;
                }
                
                values.put(database.get(str), percval/200d);
                
//                System.out.println(str + "-> " + rar + " (uses " + ((int)(percval)) + "% of EU to synergize)");
            }
//            System.exit(420);

        }
    }
}
//
//        if(needsScrape){
//            needsScrape = false;
//            int idx = 0;
//            for(Item i : Item.itemsList){
//                if(i != null){
//                    String st = i.getItemDisplayName(new ItemStack(i, 1)).toLowerCase(); // haaaaax
////                    String ul = i.getUnlocalizedName();
////                    String st = LanguageRegistry.instance().getStringLocalization(ul);
//////                    ItemStack i = null;
//////                    i.getDisplayName();
////                    if(st == null || st.length() < 2){
////                        StatCollector.translateToLocal(ul + ".name");
////                    }
//                    database.put(st, idx);
//                    System.out.println(st + " -> " + idx);
////                    i.getIte
////                    i.
//                }
//                idx++;
//            }
//        }
//    }
//}
