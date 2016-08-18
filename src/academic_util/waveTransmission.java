/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academic_util;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Ankit
 */
public class waveTransmission extends JFrame{
    
    double t = 16, tot=0;boolean isAni = false;
    
    private final static Toolkit kit=Toolkit.getDefaultToolkit();
    private final static Dimension srcDims=kit.getScreenSize();
    public final int w=srcDims.width, h=srcDims.height-300;
    
    BufferedImage buffg;
    Graphics2D g2d;
    JPanel p;   //panel to show the img;
    
    transWave trans;
    double mouseX,mouseY;
    
    waveTransmission(){
        super("Wave of Transmission Line");this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(w,h);this.setUndecorated(true);
        buffg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB); //defining the image to draw on;
        g2d = (Graphics2D) buffg.getGraphics(); //getGraphics() return Graphics Object not Graphics2D so type Casting it
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        trans = new transWave(1,0.00001,2*Math.PI*0.5,100,new complex(1500,0),w);
        trans.analsis();
        
        p = new JPanel(){
            @Override
            public void paint(Graphics g){
                //it is better to give a funciton which draw image;
                draw(g2d);
                g.drawImage(buffg, 0,0,null); //drawImage(image, location x, y, refference);
            }
            @Override
            public void update(Graphics g){
                draw(g2d);
                g.drawImage(buffg, 0,0,null); //drawImage(image, location x, y, refference);
            }
        }; //PUT Braces to change/override any function of jpanel(()
        
        Thread th = new Thread(){       //thread is made
            @Override
            public void run(){
                while(true){
                    if(isAni){
                        try {
                            Thread.sleep((long) t);     //sleep for 16 ms
                            tot+=t/1000;
                            p.repaint();    //get the total time escaped and then repaint the panel;
                        } catch (InterruptedException ex) { 
                            System.out.println();
                        }
                    }
                }
            }
        };
        
        th.start();
        addListeners();
        this.setContentPane(p);this.setVisible(true);
        //this.setLocationRelativeTo(null);
//        g2d.translate(0, h/2);
    }
    
    public void draw(Graphics2D g2d){  //draw the Wave
        //clear the image and set the color.
        g2d.clearRect(0, 0, w, h);
        g2d.setColor(Color.white);
        g2d.drawString(w-mouseX+"  "+-mouseY, 10 ,p.getHeight()/2-50);
        g2d.translate(0, h/2);
//        g2d.scale(1, 2.1);
        g2d.drawLine(0, 0, w, 0);   //draw the Coordinate axis
        g2d.drawLine(0,-h/2,0,h/2);
        
        g2d.draw(trans.getPath(tot));
        g2d.setColor(Color.blue);
        g2d.draw(trans.getForwardPath(tot));
        
        g2d.setColor(Color.red);
        g2d.draw(trans.getEnvelop());
        
        g2d.setColor(Color.GREEN);
        g2d.draw(trans.getBackwardPath(tot));
        
        g2d.setColor(Color.red);
        g2d.fill(new Ellipse2D.Double(mouseX-5,mouseY-5,10,10));
        
//        g2d.scale(1/2.1, 1/2.1);
        g2d.translate(0, -h/2);
        
        
    }
    
    public void addListeners(){
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX=e.getX();mouseY = Math.round(trans.envelop.get(mouseX)*1000.0)/1000.0;
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseX=e.getX();mouseY = Math.round(trans.envelop.get(mouseX)*1000.0)/1000.0;
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==' '){
                    isAni=!isAni;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    
}
