
package academic_util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Ankit
 */
public class analog_Exp5 {
    private double Vin=1;
    public void sourceFollower(){
        try{
            BufferedReader br =new BufferedReader(new FileReader(new File("sourceFollower.txt")));
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
            System.out.println(i);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Cant Initialize BufferedReader");
        }
        
    }
}
