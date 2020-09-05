package PathPlanner;
import java.time.LocalTime;
import java.util.ArrayList;

public class simulateArcCalc {
	public double beginVelocity;
	public double endVelocity;
	public double maxAngularAccel;//rad/s/s
	public double deltaHeading;//radians
	public ArrayList<Point> arc = new ArrayList<Point>();
	int prevTime;
	double currentRotationalVelocity = 0;
	double heading;
	double X;
	double Y;
	boolean calculateArcFirstIteration = true;
	int startTime;
	public simulateArcCalc(double X, double Y, double beginVelocity, double endVelocity, double deltaHeading, double beginHeading, double maxAngularAccel) {
		this.beginVelocity = beginVelocity;
		this.endVelocity = endVelocity;
		this.deltaHeading = deltaHeading;
		heading = beginHeading;
		this.maxAngularAccel = maxAngularAccel;
	}
	
	public void calculateArc() {
		double previousAngleReading = heading;
		double portVelocity=0;
        double starboardVelocity=0;
        double lateralVelocity =0;
        double prevW = 0;
        double localYVelocity = endVelocity / 2;
        double prevLocalYVelo = 0;
        double angularAccel = maxAngularAccel;
        double Tg = Math.sqrt(deltaHeading/angularAccel);
        double Twy = Tg;
        double accel = (endVelocity - beginVelocity)/Tg;
        double localY = (Math.pow(localYVelocity,2) - Math.pow(prevLocalYVelo, 2))/(2*Tg);
        System.out.println("Tg: " + Tg + ", Twy: " + Twy);
		long ClocalYf = OdoMath.C(0.56419*(Twy * prevW + Tg * angularAccel*Tg)/(Twy*Math.sqrt(angularAccel)));
        long Ci = OdoMath.C(0.56419*(prevW)/(Math.sqrt(angularAccel)));
        long SlocalYf = OdoMath.S(0.56419*(Twy * prevW + Tg * angularAccel*Tg)/(Twy*Math.sqrt(angularAccel)));
        long Si = OdoMath.S(0.56419*(prevW)/(Math.sqrt(angularAccel)));
        double globalYchangeForLocalYChange = 1.77245*Twy/(Tg * Math.sqrt(angularAccel))*((Math.cos(0.5*Math.pow(prevW,2)/angularAccel-previousAngleReading)*ClocalYf + Math.sin(0.5*Math.pow(prevW,2)/angularAccel-previousAngleReading) * SlocalYf) -(Math.cos(0.5*Math.pow(prevW,2)/angularAccel-previousAngleReading)*Ci + Math.sin(0.5*Math.pow(prevW,2)/angularAccel-previousAngleReading) * Si));
        double globalXchangeForLocalYChange = 1.77245*Twy/(Tg * Math.sqrt(angularAccel))*((Math.cos(0.5*Math.pow(prevW,2)/angularAccel-previousAngleReading)*SlocalYf - Math.sin(0.5*Math.pow(prevW,2)/angularAccel-previousAngleReading) * ClocalYf) -(Math.cos(0.5*Math.pow(prevW,2)/angularAccel-previousAngleReading)*Si - Math.sin(0.5*Math.pow(prevW,2)/angularAccel-previousAngleReading) * Ci));
        arc.add(new Point((int)globalXchangeForLocalYChange, (int)globalYchangeForLocalYChange));        
	}
}
