/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academic_util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 *
 * @author Ankit
 */
public class analog_Exp1 {
    
    private double Vin=.04;
    public void withCE(){
        try{
            BufferedReader br =new BufferedReader(new FileReader(new File("freq_with_CE.txt")));
            String s = "";int i=0;
            while((s=br.readLine())!=null){
                String freq = s.substring(0, s.indexOf(','));
                String Vo=s.substring(s.indexOf(',')+1);
                double vo=Double.parseDouble(Vo);
                double Av = 20*Math.log10(vo/Vin);
                Av = Math.round(Av*100.0)/100.0;i++;
                System.out.println(freq+"\t"+vo+"\t"+Av);
                System.out.println("------------------------------------------------");
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Cant Initialize BufferedReader");
        }
    }
    public void withoutCE(){
        try{
            BufferedReader br =new BufferedReader(new FileReader(new File("freq_without_CE.txt")));
            String s = "";int i=0;
            while((s=br.readLine())!=null){
                String freq = s.substring(0, s.indexOf(','));
                String Vo=s.substring(s.indexOf(',')+1);
                double vo=Double.parseDouble(Vo);
                double Av = 20*Math.log10(vo/Vin);
                Av = Math.round(Av*100.0)/100.0;i++;
                System.out.println(freq+"\t"+vo+"\t"+Av);
                System.out.println("------------------------------------------------");
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Cant Initialize BufferedReader");
        }
    }
}
