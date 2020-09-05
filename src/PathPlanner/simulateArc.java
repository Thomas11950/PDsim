package PathPlanner;
import java.time.LocalTime;
import java.util.ArrayList;

public class simulateArc {
	public double beginVelocity;
	public double endVelocity;
	public double maxAngularAccel;//rad/s/s
	public double deltaHeading;//radians
	public ArrayList<Point> arc = new ArrayList<Point>();
	double prevTime;
	double currentRotationalVelocity = 0;
	double heading;
	double X;
	double Y;
	boolean calculateArcFirstIteration = true;
	double startTime;
	public simulateArc(double X, double Y, double beginVelocity, double endVelocity, double deltaHeading, double beginHeading, double maxAngularAccel) {
		this.beginVelocity = beginVelocity;
		this.endVelocity = endVelocity;
		this.deltaHeading = deltaHeading;
		heading = beginHeading;
		this.maxAngularAccel = maxAngularAccel;
		this.X = X;
		this.Y = Y;
	}
	public double turnTime(double deltaHeading, double maxAngularAccel) {
		return Math.sqrt(Math.abs(deltaHeading/maxAngularAccel));
	}
	public int getTimeMilliseconds() {
		String time = java.time.LocalTime.now().toString();
		if(time.length() == 8) {
			time += ".000";
		}
		String minutes = time.substring(time.length()-9,time.length()-7);
	    String timeEdited = time.substring(time.length()-6,time.length()-4)+time.substring(time.length()-3,time.length()); 
	    return Integer.parseInt(timeEdited) + Integer.parseInt(minutes) * 60000;
	}
	public void calculateArc() {
		while(prevTime - startTime < 2*turnTime(deltaHeading, maxAngularAccel)) {
		if(calculateArcFirstIteration) {
			if(deltaHeading < 0) {
				maxAngularAccel = -maxAngularAccel;
			}
			prevTime = 0;
			startTime = prevTime;
			calculateArcFirstIteration = false;
		}
		double accel = (endVelocity - beginVelocity)/(2*turnTime(deltaHeading, maxAngularAccel));
		double currentTime = prevTime + 0.001;
		//System.out.println("currentTime: "+currentTime+", startTime: "+startTime + ", turnTime: "+turnTime(deltaHeading,maxAngularAccel));
		double deltaTime = currentTime - prevTime;
		if(prevTime > turnTime(deltaHeading,maxAngularAccel)) {
			currentRotationalVelocity += deltaTime * -maxAngularAccel;
		}
		else {
			currentRotationalVelocity += deltaTime * maxAngularAccel;
		}
		heading += deltaTime * currentRotationalVelocity;/*
		double headingToEnter;
		if((heading+"").substring((heading+"").length()-3,(heading+"").length()-2).equalsIgnoreCase("E")){
			String headingString = heading+"";
			headingString = headingString.substring(0,headingString.length()-3);
			headingToEnter = Double.parseDouble(headingString);
		}
		else {
			headingToEnter = heading;
		}*/
		double prevX = X;
		double prevY = Y;
		X += ((currentTime-startTime)*accel + beginVelocity)*deltaTime*Math.cos(heading);
		Y += ((currentTime-startTime)*accel + beginVelocity)*deltaTime*Math.sin(heading);
		arc.add(new Point(X,Y,startTime));
		//System.out.println("heading: " + heading + ", change X: "+ (X-prevX)+", change Y: " + (Y-prevY) + ", cos(heading): " + Math.cos(heading));
		prevTime = currentTime;
		}
	}
}
