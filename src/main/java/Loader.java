/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;

import net.minecraft.client.Minecraft;

/**
 *
 * @author Luke
 */
public class Loader {
public static void addLibraryPath(String pathToAdd) throws Exception {
        final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
        usrPathsField.setAccessible(true);

        //get array of paths
        final String[] paths = (String[]) usrPathsField.get(null);

        //check if the path to add is already present
        for (String path : paths) {
            if (path.equals(pathToAdd)) {
                return;
            }
        }

        //add the new path
        final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
        newPaths[newPaths.length - 1] = pathToAdd;
        usrPathsField.set(null, newPaths);
    }
            
    public static void main(String[] args) {
        try {
                System.setProperty("java.library.path", "C:\\SkyTech\\build\\natives\\");
                addLibraryPath("C:\\SkyTech\\build\\natives\\");
                Field path = ClassLoader.class.getDeclaredField("sys_paths");
                path.setAccessible(true);
                path.set(null, null);
            } catch (Throwable r) {
            }
        net.minecraft.launchwrapper.Launch.main(new String[]{"--version","1.7.2","--tweakClass","cpw.mods.fml.common.launcher.FMLTweaker","--accessToken","FML"});
        
    }

}
