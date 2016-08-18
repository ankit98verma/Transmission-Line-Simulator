/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academic_util;

import java.util.Hashtable;

/**
 *
 * @author Ankit
 */
public class doubleHash extends Hashtable{
    
    
    public boolean contains(double s){
        return this.contains(new Double(s));
    } 
    public boolean containsKey(double key){
        return this.containsKey(new Double(key));
    }
    public boolean containsValue(double key){
        return this.containsValue(new Double(key));
    }
    
    public double get(double key){
        double v=(double) this.get(new Double(key));
        return v;
    }
    
    public void put(double key, double value){
        this.put(new Double(key), new Double(value));
    }
    
    public double remove(double key){
        return (double)this.remove(new Double(key));
    }
    
    public double update(double key, double value){
        remove(key);
        put(key, value);
        return get(key);
    }
    
}
