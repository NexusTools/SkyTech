/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nexustools.replicator;

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
        System.out.println("COMPARETO");
        Trip t = (Trip)o;
        return (t.x == x && t.y == y && t.z == z)?0:1;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("EQUALS");
        Trip t = (Trip)o;
        return (t.x == x && t.y == y && t.z == z);
    }
    
    @Override
    public int hashCode() {
        return x+y+z%Integer.MAX_VALUE-Integer.MAX_VALUE/4+x&y&z+(x/z)+(x/y)+(y/z)+(z/x)+(y/x); // haaaaaax
    }
    
}