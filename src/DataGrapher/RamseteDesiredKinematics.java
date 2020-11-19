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

public class RamseteDesiredKinematics extends JApplet {
	ArrayList<Double> times;
	ArrayList<Double> headings;
	ArrayList<Double> currentHeadings;
	ArrayList<Double> derivatives;
	ArrayList<Double> desiredAngularVelos;
	ArrayList<Double> currentVelos;
	ArrayList<Double> veloCommands;
	ArrayList<Double> desiredVelos;
	ArrayList<Double> lateralVelos;
	ArrayList<Double> angularVeloCommands;
	public int xTranslate = 0;
	public int yTranslate = -150;
	double scaleFactor = 100;
	double yScaleFactor = 50;
	double yScaleFactor2 = 4;
	double yScaleFactor3 = 10;
	public void init() {
		times = new ArrayList<Double>();
		headings = new ArrayList<Double>();
		currentHeadings = new ArrayList<Double>();
		derivatives = new ArrayList<Double>();
		currentVelos = new ArrayList<Double>();
		veloCommands = new ArrayList<Double>();
		desiredVelos = new ArrayList<Double>();
		lateralVelos = new ArrayList<Double>();
		angularVeloCommands = new ArrayList<Double>();
		// TODO Auto-generated method stub
		File fileContainingPoints = new File("C:\\engineering\\robotics\\ramsetedesiredkinematics.txt");
        Scanner scnr;
        try {
            scnr = new Scanner(fileContainingPoints);
        }
        catch(FileNotFoundException e){
            return;
        }
        while(scnr.hasNextLine()){
        	String pointInStringFormat = scnr.nextLine();
        	double time = identifyNumberInString(pointInStringFormat, "Time: ");
        	double heading = identifyNumberInString(pointInStringFormat, "Heading: ");
        	double currentHeading = identifyNumberInString(pointInStringFormat, "ActualHeading: ");
        	double currentVelo = identifyNumberInString(pointInStringFormat, "CurrentVelo: ");
        	double veloCommand = identifyNumberInString(pointInStringFormat, "VeloCommand: ");
        	double desiredVelo = identifyNumberInString(pointInStringFormat, "DesiredVelo: ");
        	double lateralVelo = identifyNumberInString(pointInStringFormat, "LateralVelo: ");
        	double angularVeloCommand = identifyNumberInString(pointInStringFormat, "AngularVeloCommand: ");
        	times.add(time);
        	headings.add(heading);
        	currentHeadings.add(currentHeading);
        	currentVelos.add(currentVelo);
        	veloCommands.add(veloCommand);
        	desiredVelos.add(desiredVelo);
        	lateralVelos.add(lateralVelo);
        	angularVeloCommands.add(angularVeloCommand);
        }
        for(int i = 0; i < times.size()-1; i++) {
        	double derivative = (headings.get(i+1)-headings.get(i))/(times.get(i+1)-times.get(i));
        	derivatives.add(derivative);
        }
       
	}
	public void paint(Graphics g) {
		super.paint(g);
		for(int i = 0; i < times.size() -1;i++) {
			g.setColor(Color.green);
			g.drawLine((int)(times.get(i)*scaleFactor)+xTranslate, -(int)(headings.get(i)*yScaleFactor)+500+yTranslate, (int)(times.get(i+1)*scaleFactor)+xTranslate, -(int)(headings.get(i+1)*yScaleFactor)+500+yTranslate);		
			g.setColor(Color.black);
			g.drawLine((int)(times.get(i)*scaleFactor)+xTranslate, -(int)(currentHeadings.get(i)*yScaleFactor+4*Math.PI*yScaleFactor)+500+yTranslate, (int)(times.get(i+1)*scaleFactor)+xTranslate, -(int)(currentHeadings.get(i+1)*yScaleFactor+4*Math.PI*yScaleFactor)+500+yTranslate);		
			g.setColor(Color.blue);
			g.drawLine((int)(times.get(i)*scaleFactor)+xTranslate, -(int)(angularVeloCommands.get(i)*yScaleFactor)+500+yTranslate, (int)(times.get(i+1)*scaleFactor)+xTranslate, -(int)(angularVeloCommands.get(i+1)*yScaleFactor)+500+yTranslate);		
			
			/*if(i < times.size()-2) {
				g.setColor(Color.red);
				g.drawLine((int)(times.get(i)*scaleFactor)+xTranslate, -(int)(derivatives.get(i)*yScaleFactor)+500+yTranslate, (int)(times.get(i+1)*scaleFactor)+xTranslate, -(int)(derivatives.get(i+1)*yScaleFactor)+500+yTranslate);		
			}
			g.setColor(Color.blue);
			g.drawLine((int)(times.get(i)*scaleFactor)+xTranslate, -(int)(desiredAngularVelos.get(i)*yScaleFactor)+500+yTranslate, (int)(times.get(i+1)*scaleFactor)+xTranslate, -(int)(desiredAngularVelos.get(i+1)*yScaleFactor)+500+yTranslate);		
*/
			g.setColor(Color.DARK_GRAY);
			g.drawLine((int)(times.get(i)*scaleFactor)+xTranslate, -(int)(currentVelos.get(i)*yScaleFactor2)+500+yTranslate, (int)(times.get(i+1)*scaleFactor)+xTranslate, -(int)(currentVelos.get(i+1)*yScaleFactor2)+500+yTranslate);	
			g.setColor(Color.yellow);
			g.drawLine((int)(times.get(i)*scaleFactor)+xTranslate, -(int)(veloCommands.get(i)*yScaleFactor2)+500+yTranslate, (int)(times.get(i+1)*scaleFactor)+xTranslate, -(int)(veloCommands.get(i+1)*yScaleFactor2)+500+yTranslate);	
			g.setColor(Color.CYAN);
			g.drawLine((int)(times.get(i)*scaleFactor)+xTranslate, -(int)(desiredVelos.get(i)*yScaleFactor2)+500+yTranslate, (int)(times.get(i+1)*scaleFactor)+xTranslate, -(int)(desiredVelos.get(i+1)*yScaleFactor2)+500+yTranslate);	
			g.setColor(Color.pink);
			g.drawLine((int)(times.get(i)*scaleFactor)+xTranslate, -(int)(lateralVelos.get(i)*yScaleFactor2)+500+yTranslate, (int)(times.get(i+1)*scaleFactor)+xTranslate, -(int)(lateralVelos.get(i+1)*yScaleFactor2)+500+yTranslate);	
		}
	}
	
	
	public static double identifyNumberInString(String pointInStringFormat, String TAG) {
    	int tagLength = TAG.length();
    	int requestedNumberPosition = pointInStringFormat.indexOf(TAG);
        String requestedNumberString = pointInStringFormat.substring(requestedNumberPosition + tagLength);
        for(int i = 0; i < requestedNumberString.length(); i++){
            if(requestedNumberString.substring(i,i+1).equals(",")){
                requestedNumberString = requestedNumberString.substring(0,i);
                i=requestedNumberString.length();
            }
        }
        return Double.parseDouble(requestedNumberString);
    }
}
