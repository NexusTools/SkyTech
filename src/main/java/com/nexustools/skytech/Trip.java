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
        int result = (int) (x ^ (x >>> 32)); // internet magic
        result = 31 * result + (int) (y ^ (y >>> 32));
        result = 31 * result + (int) (z ^ (z >>> 32));
        return result;
//        return x+y+z%Integer.MAX_VALUE-Integer.MAX_VALUE/4+x&y&z+(x/(1+Math.abs(z)))+(x/(1+Math.abs(y)))+(y/(1+Math.abs(z)))+(z/(1+Math.abs(x)))+(y/(1+Math.abs(x))); // haaaaaax
    }
    
}