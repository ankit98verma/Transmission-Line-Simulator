/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academic_util;

/**
 *
 * @author Ankit
 */
public class complex {
    
    double real, imag;
    
    complex(double r, double i){
        real=r;imag=i;
    }
    complex(){
        real=0;imag=0;
    }
    
    public complex add(complex b){
        return new complex(this.real+b.real,this.imag+b.imag);
    }
    public complex sub(complex b){
        return new complex(this.real-b.real,this.imag-b.imag);
    }
    
    public complex multiply(complex b){
        return new complex(this.real*b.real - this.imag*b.imag, this.real*b.imag + this.imag*b.real);
    }
    
    public double getMag(){
        return Math.sqrt(real*real+imag*imag);
    }
    
    public complex divide(complex b){
        complex upper = this.multiply(b.getConjugate());
        complex lower = b.multiply(b.getConjugate());
        return new complex(upper.real/lower.getMag(), upper.imag/lower.getMag());
    }
    
    public complex getConjugate(){
        return new complex(real, -imag);
    }
    public double getAngle(){
        double ang=0;
        if(real!=0){
            //System.out.println("1");
            ang = Math.atan(Math.abs(imag/real));
            if(real > 0){
                //System.out.println("1.1");
                if(imag >= 0){
//                    System.out.println("1.1.1");
                    return ang;
                }
                else{
//                    System.out.println("1.1.2");
                    return -ang;
                }
            }
            else if(real < 0){
//                System.out.println("1.2");
                if(imag >= 0){
//                    System.out.println("1.2.1");
                    return Math.PI-ang;
                }
                else{
//                    System.out.println("1.2.2");
                    return ang-Math.PI;
                }
            }
            
            return ang;     
        }
        else if(imag>0)
            return Math.PI;
        else if(imag<0)
            return -Math.PI;
        return 0;
    }
}
