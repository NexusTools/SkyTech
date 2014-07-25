/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.skytech;

/**
 *
 * @author Luke
 */
public class Trip implements Comparable {

    int x, y, z;
    
    public Trip(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    
    @Override
    public int compareTo(Object o) {
        Trip t = (Trip)o;
        return (t.x == x && t.y == y && t.z == z)?0:1;
    }

    @Override
    public boolean equals(Object o) {
        Trip t = (Trip)o;
        return (t.x == x && t.y == y && t.z == z);
    }
    
    @Override
    public int hashCode() {
        return x+y+z%Integer.MAX_VALUE-Integer.MAX_VALUE/4+x&y&z+(x/(1+z))+(x/(1+y))+(y/(1+z))+(z/(1+x))+(y/(1+x)); // haaaaaax
    }
    
}