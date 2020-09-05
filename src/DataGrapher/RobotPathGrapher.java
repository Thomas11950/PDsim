package DataGrapher;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JApplet;

public class RobotPathGrapher extends JApplet{
	ArrayList<Double> currentXs;
	ArrayList<Double> currentYs;
	ArrayList<Double> targetXs;
	ArrayList<Double> targetYs;
	Calendar c;
	long startTime;
	double graphFactor = 7;
	final double timeElapsedToGraph=5000;
	int yTranslate = 350;
	public void init() {
		c = Calendar.getInstance();
		currentXs = new ArrayList<Double>();
		currentYs = new ArrayList<Double>();
		targetXs = new ArrayList<Double>();
		targetYs = new ArrayList<Double>();
		File fileContainingPoints = new File("C:\\primitive data\\pointformatter\\PointFormatterFF\\src\\RamseteMotionData.txt");
        Scanner scnr;
        try {
            scnr = new Scanner(fileContainingPoints);
        }
        catch(FileNotFoundException e){
            return;
        }
        while(scnr.hasNextLine()){
        	String pointInStringFormat = scnr.nextLine();
        	double targetX = identifyNumberInString(pointInStringFormat, "desired X: ");
        	double targetY = identifyNumberInString(pointInStringFormat, "desired Y: ");
        	double currentX = identifyNumberInString(pointInStringFormat, "current X: ");
        	double currentY = identifyNumberInString(pointInStringFormat, "current Y: ");
        	targetXs.add(targetX);
        	targetYs.add(targetY);
        	currentXs.add(currentX);
        	currentYs.add(currentY);
        }
        startTime = c.getTime().getTime();
	}
    public double identifyNumberInString(String pointInStringFormat, String TAG) {
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
	public void paint(Graphics g) {
		super.paint(g);
		long currentTime = c.getTime().getTime();
		double deltaTime = currentTime - startTime;
		for(int i = 0; i < (currentXs.size() -1);i++) {
			System.out.println("targetX: " + targetXs.get(i) + "targetY: " + targetYs.get(i));
			g.setColor(Color.green);
			g.drawLine((int)(currentXs.get(i)*graphFactor), (int)(currentYs.get(i)*graphFactor)+yTranslate, (int)(currentXs.get(i+1)*graphFactor), (int)(currentYs.get(i+1)*graphFactor)+yTranslate);
			g.setColor(Color.blue);
			g.drawLine((int)(targetXs.get(i)*graphFactor), (int)(targetYs.get(i)*graphFactor)+yTranslate, (int)(targetXs.get(i+1)*graphFactor), (int)(targetYs.get(i+1)*graphFactor)+yTranslate);
		}
	}
}
