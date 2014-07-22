/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.replicator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Luke
 */
public class Cache {
    
    static final String[] cachedEntries = new String[]{"replicator.png", "commondb.txt"};
    
    static String cachedir = "./";
    
    public static void validateCache(File basecachedir) throws IOException{
        File dir = new File(basecachedir.toString() + "/.repcache/");
        String dirs = dir.toString() + "/";
        cachedir = dirs;
        if(!dir.exists()){
            dir.mkdirs();
        }
        for(String s : cachedEntries){
            if(!new File(dirs + s).exists()){
                validate(dirs, s);
            }
        }
    }
    
    private static void validate(String dir, String filename)throws IOException {
        InputStream remote = new URL("http://mc.nexustools.net/media/" + filename).openConnection().getInputStream();
        if(new File(dir + filename + ".temporary").exists())new File(dir + filename + ".temporary").delete();
        FileOutputStream local = new FileOutputStream(dir + filename + ".temporary");
        int r = 0;
        byte[] b = new byte[8129];
        
        while((r=remote.read(b))>-1){
            local.write(b,0,r);
        }
        
        local.flush();
        local.close();
        remote.close();
        
        new File(dir + filename + ".temporary").renameTo(new File((dir + filename)));
        
    }
    
    public static InputStream getInputStream(String entry) throws IOException {
        return new FileInputStream(cachedir + entry);
    }
    
}
