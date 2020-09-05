package DataGrapher;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JApplet;
import javax.swing.JPanel;

public class PIDgrapher extends JApplet implements KeyListener {
	ArrayList<Double> times;
	ArrayList<Double> velos;
	ArrayList<Double> requestedVs;
	public int xTranslate = 0;
	public int yTranslate = -200;
	double scaleFactor = 100;
	public void init() {
		times = new ArrayList<Double>();
		velos = new ArrayList<Double>();
		requestedVs = new ArrayList<Double>();
		// TODO Auto-generated method stub
		File fileContainingPoints = new File("C:\\primitive data\\pointformatter\\PointFormatterFF\\src\\PIDpoints.txt");
        Scanner scnr;
        try {
            scnr = new Scanner(fileContainingPoints);
        }
        catch(FileNotFoundException e){
            return;
        }
        while(scnr.hasNextLine()){
		String pointInStringFormat = scnr.nextLine();
        /*int powerPosition = pointInStringFormat.indexOf("Power: ");
        String powerString = pointInStringFormat.substring(powerPosition + 7);
        for(int i = 0; i < powerString.length(); i++){
            if(powerString.substring(i,i+1).equals(",")){
                powerString = powerString.substring(0,i);
                i=powerString.length();
            }
        }
        double power = Double.parseDouble(powerString);*/
        int veloPosition = pointInStringFormat.indexOf("Velo: ");
        String veloString = pointInStringFormat.substring(veloPosition + 6);
        for(int i = 0; i < veloString.length(); i++){
            if(veloString.substring(i,i+1).equals(",")){
                veloString = veloString.substring(0,i);
                i=veloString.length();
            }
        }
        double velo = Double.parseDouble(veloString);
        
        /*int accelPosition = pointInStringFormat.indexOf("Accel: ");
        String accelString = pointInStringFormat.substring(accelPosition + 7);
        for(int i = 0; i < accelString.length(); i++){
            if(accelString.substring(i,i+1).equals(",")){
                accelString = accelString.substring(0,i);
                i=accelString.length();
            }
        }
        double accel = Double.parseDouble(accelString);*/
        int timePosition = pointInStringFormat.indexOf("Time: ");
        String timeString = pointInStringFormat.substring(timePosition + 6);
        for(int i = 0; i < timeString.length(); i++){
            if(timeString.substring(i,i+1).equals(",")){
                timeString = timeString.substring(0,i);
                i=timeString.length();
            }
        }
        double time = Double.parseDouble(timeString);
        int requestedVPosition = pointInStringFormat.indexOf("RequestedV: ");
        String requestedVString = pointInStringFormat.substring(requestedVPosition + 12);
        for(int i = 0; i < requestedVString.length(); i++){
            if(requestedVString.substring(i,i+1).equals(",")){
                requestedVString = requestedVString.substring(0,i);
                i=requestedVString.length();
            }
        }
        double requestedV = Double.parseDouble(requestedVString);
        //System.out.println("("+(time)+","+velo+")");
        times.add(time);
        velos.add(velo);
        requestedVs.add(requestedV);
        }
        this.addKeyListener(this);
       
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.drawLine(0, 300, 100, 300);
		for(int i = 0; i < times.size() -1;i++) {
			g.setColor(Color.green);
			g.drawLine((int)(times.get(i)*scaleFactor)+xTranslate, -(int)(velos.get(i)*5.0)+500+yTranslate, (int)(times.get(i+1)*scaleFactor)+xTranslate, -(int)(velos.get(i+1)*5.0)+500+yTranslate);
			g.setColor(Color.blue);
			g.drawLine((int)(times.get(i)*scaleFactor)+xTranslate, -(int)(requestedVs.get(i)*5.0)+500 + yTranslate, (int)(times.get(i+1)*scaleFactor)+xTranslate, -(int)(requestedVs.get(i+1)*5.0)+500+yTranslate);
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getKeyCode());
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			xTranslate+=scaleFactor;
			repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			scaleFactor = scaleFactor * 1.5;
			repaint();
		}
		if(e.getKeyCode() == KeyEvent.VK_O) {
			scaleFactor = scaleFactor /1.5;
			repaint();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
