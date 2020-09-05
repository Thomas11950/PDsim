package RamseteKinematicsChecker;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JApplet;

public class RamseteKinematicsChecker extends JApplet{
	ArrayList<Task> tasks = new ArrayList<Task>();
	TimeBasedTaskList taskList;
	public double X=0; 
	public double Y=0;
	public double heading = 0;
	public double scaleFactor=10;
	public int yTranslate = 400;
	public void init() {
		tasks = new ArrayList<Task>();
		File fileContainingPoints = new File("C:\\primitive data\\pointformatter\\RamseteKinematicsChecker\\src\\RamseteMotionData.txt");
        Scanner scnr;
        try {
            scnr = new Scanner(fileContainingPoints);
        }
        catch(FileNotFoundException e){
        	System.out.println("file not found");
            return;
        }
        while(scnr.hasNextLine()) {
        	String line = scnr.nextLine();
        	System.out.println(line);
        	double timeTaken = identifyNumberInString(line, "timeTaken: ");
        	double velo = identifyNumberInString(line, "Velo: ");
        	double accel = identifyNumberInString(line, "Accel: ");
        	double angularVelo = identifyNumberInString(line, "AngularVelo: ");
        	double angularAccel = identifyNumberInString(line, "AngularAccel: ");
        	tasks.add(new Task(timeTaken, velo, angularVelo, accel, angularAccel));
        }
        taskList = new TimeBasedTaskList(tasks);
        System.out.println("init done");
	}
	public void paint(Graphics g) {
		super.paint(g);
		double currentTime = 0;
		double timeIncrement = 0.001;
		double prevX =0;
		double prevY = 0;
		X = prevX;
		Y = prevY;
		heading = 0;
		if(taskList!=null) {
			System.out.println(taskList.getTotalTime());
			while(currentTime<taskList.getTotalTime()) {
				MotionData data = taskList.getMotionData(currentTime);
				heading+=(data.desiredAngularVelocity) * timeIncrement;
				X+=data.desiredVelocity*timeIncrement*Math.cos(heading);
				Y+=data.desiredVelocity*timeIncrement*Math.sin(heading);
				System.out.println("heading: "+heading+", desiredAngularVelo: "+data.desiredAngularVelocity+", X: " +X+", Y: "+ Y);
				g.drawLine((int)(prevX*scaleFactor),(int) (prevY*scaleFactor)+yTranslate, (int)(X*scaleFactor), (int)(Y*scaleFactor)+yTranslate);
				prevX = X;
				prevY = Y;
				currentTime += timeIncrement;
			}
		}
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
}
