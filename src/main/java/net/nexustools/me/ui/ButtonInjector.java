/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.me.ui;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

/**
 *
 * @author Luke
 */
public class ButtonInjector {
    
    HashMap<String, ArrayList<GuiButton>> buttons = new HashMap<String, ArrayList<GuiButton>>();
    
    public void injectButton(Class<?> page, GuiButton button){
        String className = page.getCanonicalName();
        if(!buttons.containsKey(className)){
            buttons.put(className, new ArrayList<GuiButton>());
        }
        buttons.get(className).add(button);
    }
    
    public void onTickInGUI(Gui g){
        String className = g.getClass().getCanonicalName();
        if(buttons.containsKey(className)){
            
        }
    }
    
}
