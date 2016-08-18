/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academic_util;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Path2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ankit
 */
public class transWave {
    
    double L,C; //inductance & capacitance per meter
    double beta, Zo, omega;
    complex tauL,Zl;
    
    double phi, modTauL, Vplus;
    double Vmax,Vmin;
    
    double width;
    doubleHash envelop;
    
    public transWave(double l, double c, double omega,double Vplus,complex Zl,double w){
        this.L=l;this.C=c;this.omega=omega;
        beta = omega*Math.sqrt(L*C);
        this.Zl = Zl;
        Zo=Math.sqrt(L/C);
        tauL = (Zl.sub(new complex(Zo, 0))).divide((Zl.add(new complex(Zo, 0))));
        phi = tauL.getAngle();
        modTauL= tauL.getMag();
        this.Vplus=Vplus;
        Vmax=Vplus*(1+modTauL);Vmin=Vplus*(1-modTauL);this.width=w;envelop=new doubleHash();
        controller cooo =new controller();
    }
    
    public void analsis(){
        System.out.print("Zo: "+Zo);
        System.out.println("   Zl: "+Zl.real+"  "+Zl.imag+" angle:\t"+Math.toDegrees(Zl.getAngle()));
        System.out.println("TauL: "+"r:"+tauL.real+"i:"+tauL.imag+"\t"+tauL.getMag()+"\t"+Math.toDegrees(tauL.getAngle()));
        System.out.println("Lembda: "+2*Math.PI/beta);
        System.out.println("Max Value: "+Vmax);
        System.out.println("Min Value: "+Vmin);
        System.out.println("Max");
        for(int i=-5; i<5; i++){
            System.out.print((phi+2*i*Math.PI)/(2*beta)+"  ");
            if(i==0)
                System.out.println();
        }
        System.out.println("\nMin");
        for(int i=-5; i<5; i++){
            System.out.print((phi+(2*i+1)*Math.PI)/(2*beta)+"  ");
            if(i==0)
                System.out.println();
        }
    }
    
    public double getY(double x,double t){
//        double Vprog = Vplus*(1-modTauL)*Math.cos(omega*t-beta*x);double Vstand = 2*Vplus*modTauL*Math.cos(beta*x+phi)*Math.cos(omega*t+phi);
        double forward = Vplus*Math.cos(beta*x+omega*t);
        double backward = Vplus*modTauL*Math.cos(phi-beta*x+omega*t);
        return forward+backward;
    }
    
    public double getForwardY(double x, double t){
        double forward = Vplus*Math.cos(beta*x+omega*t);
        return forward;
    }
    public double getBackwardY(double x, double t){
        double backward = Vplus*modTauL*Math.cos(phi-beta*x+omega*t);
        return backward;
    }
    
    public Path2D getPath(double tot){
        Path2D p=new Path2D.Double();p.moveTo(width, 0);
        
        for(double i=0; i<width; i+=1){
            double y =(getY(i, tot));
            p.lineTo(width-i, -(y));
            if(!envelop.containsKey(width-i)){
                envelop.put(width-i, -Math.abs(y));
            }
            else{
                if(Math.abs(envelop.get(width-i))<(y)){
                    envelop.update(width-i, -Math.abs(y));
                }
            }
        }
        return  p;
    }
    
    public Path2D getForwardPath(double tot){
        Path2D p=new Path2D.Double();p.moveTo(width, 0);
        
        for(int i=0; i<width; i++)
            p.lineTo(width-i, -(getForwardY(i, tot)));
        
        return  p;
    }
    
    public Path2D getBackwardPath(double tot){
        Path2D p=new Path2D.Double();p.moveTo(width, 0);
        
        for(int i=0; i<width; i++)
            p.lineTo(width-i, -(getBackwardY(i, tot)));
        
        return  p;
    }
    
    public Path2D getEnvelop(){
        Path2D enve=new Path2D.Double();enve.moveTo(width, 0);
        for(int i=0; i<=width; i++){
            if(envelop.containsKey(width-i))
                enve.lineTo(width-i, envelop.get(width-i));
        }
        return enve;
        
    }
    
    public class controller extends JFrame{
        
        JPanel p;
    
        //variables to get from user 
        /*
        
        freqency;
        Vplus;
        Zl;
        L C
        Zo;
        Lembda
        
        */
        public controller(){
            super("Control Panel");this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            p = new JPanel(new GridLayout(0,2,10,10));
            addComponents();
            
            this.getContentPane().add(p);
            pack();this.setVisible(true);this.setLocation(500,500);
        }
    
        public void addComponents(){
            label Lfreq = new label("Frequency");
            label LVplus  = new label("V+");
            label LZl  = new label("Load Impedence");
            label LL = new label("Inductance");
            label LC =new label("Capacitance");
            label LZo = new label("Characteristic Impedence");
            label Llembda = new label("Wavelength");
            
            JTextField Tfreq =new JTextField();
            Tfreq.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c=e.getKeyChar();
                    if(c==KeyEvent.VK_ENTER && Tfreq.getText().length()>0){
                        omega = 2*Math.PI*Double.parseDouble(Tfreq.getText());
                    }
                    else if(!(Character.isDigit(c)|| c=='.' || c==KeyEvent.VK_DELETE||c==KeyEvent.VK_BACK_SPACE||c==KeyEvent.VK_ENTER)){
                        Toolkit.getDefaultToolkit().beep();
                        e.consume();
                    }
                }
                @Override
                public void keyPressed(KeyEvent e) {}

                @Override
                public void keyReleased(KeyEvent e) {}
            });
            
            JTextField TVplus = new JTextField();
            TVplus.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c=e.getKeyChar();
                    if(c==KeyEvent.VK_ENTER && TVplus.getText().length()>0){
                        if(Vplus>Double.parseDouble(TVplus.getText()))
                            envelop.clear();
                        Vplus = Double.parseDouble(TVplus.getText());
                    }
                    else if(!(Character.isDigit(c)|| c=='.' || c==KeyEvent.VK_DELETE||c==KeyEvent.VK_BACK_SPACE||c==KeyEvent.VK_ENTER)){
                        Toolkit.getDefaultToolkit().beep();
                        e.consume();
                    }
                }
                @Override
                public void keyPressed(KeyEvent e) {}

                @Override
                public void keyReleased(KeyEvent e) {}
            });
            
            
            JTextField TZl = new JTextField();
            TZl.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c=e.getKeyChar();
                    if(c==KeyEvent.VK_ENTER && TZl.getText().length()>0){
                        try{
                            String txt = TZl.getText();double r=0,imag=0;
                            int indexPos=txt.indexOf('+');
                            int indexNeg=txt.indexOf('-');
                            if(indexPos == -1 && indexNeg == -1){
                                r = Double.parseDouble(txt);imag=0;
                            }
                            else if(indexPos!=-1 || indexNeg !=-1){
                                int indexI = txt.indexOf('i');
                                if(indexPos != -1){
                                    r = Double.parseDouble(txt.substring(0,indexPos));
                                    if(indexI == txt.length()-1)
                                        imag = Double.parseDouble(txt.substring(indexPos+1,txt.length()-1));        
                                    else
                                        imag = Double.parseDouble(txt.substring(indexI+1));
                                }else if(indexNeg != -1){
                                    r = Double.parseDouble(txt.substring(0,indexNeg));
                                    if(indexI == txt.length()-1)
                                        imag = Double.parseDouble(txt.substring(indexNeg+1,txt.length()-1));        
                                    else
                                        imag = Double.parseDouble(txt.substring(indexI+1));
                                }
                            }
                            System.out.println(r+"\t"+imag);
                            envelop.clear();
                            Zl = new complex(r, imag);
                            tauL = (Zl.sub(new complex(Zo, 0))).divide((Zl.add(new complex(Zo, 0))));
                            phi = tauL.getAngle();
                            modTauL= tauL.getMag();
                        }catch(Exception exp){
                            System.out.println("Error...Wrong Input \n"+TZl.getText()+"\n");
                            exp.printStackTrace();
                        }
                    }
                    else if(!(Character.isDigit(c)|| c=='.'||c=='+'|| c=='i'||c=='-' || c==KeyEvent.VK_DELETE||c==KeyEvent.VK_BACK_SPACE||c==KeyEvent.VK_ENTER)){
                        Toolkit.getDefaultToolkit().beep();
                        e.consume();
                    }
                }
                @Override
                public void keyPressed(KeyEvent e) {}

                @Override
                public void keyReleased(KeyEvent e) {}
            });
            
            
            p.add(Lfreq);p.add(Tfreq);p.add(LVplus);p.add(TVplus);
            p.add(LZl); p.add(TZl);
        }
    }
    
    public class label extends JLabel{
        public label(String s){
            super(s);
            this.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
            this.setHorizontalAlignment(JLabel.CENTER);
        }
    }
    
    
}
