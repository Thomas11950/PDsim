package PathPlanner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.Timer;


public class DrawingEngine extends JApplet{
	Path pathToDraw;
	public double maxAccel = 50;
	public double maxAngularAccel= 5;
	public double scaleFactor = 5;
	ArrayList<ArrayList<Point>> arcs;
	boolean[] overAccel;
	int xTranslate =5;
	int yTranslate =600;
	public double kV = 0.174;
	public double kA = 0.0325;
	public double Vmax = 11;
	public void init() {
		double totalTime = 0;
		pathToDraw = new Path("C:\\primitive data\\pointformatter\\PathPlanner\\src\\points11.txt");
		arcs = new ArrayList<ArrayList<Point>>();
		overAccel = new boolean[pathToDraw.path.size()-1];
		Double[] angles = new Double[pathToDraw.path.size()-1];
		for(int i = 0; i< angles.length; i++) {
			angles[i] = Math.atan2(pathToDraw.path.get(i+1).Y - pathToDraw.path.get(i).Y, pathToDraw.path.get(i+1).X - pathToDraw.path.get(i).X);
		}
		double[] prevIntercept = new double[2];
		prevIntercept[0] = pathToDraw.path.get(0).X;
		prevIntercept[1] = pathToDraw.path.get(0).Y;
		for(int i = 0; i < pathToDraw.path.size()-2;i++) {
			if(Math.abs(MathFunctions.angleFormatting(angles[i]-angles[i+1])) > Math.toRadians(0.1)) {
				simulateArc arc;
				if(pathToDraw.path.get(i+1).angularAccel==null) {
					 arc = new simulateArc(pathToDraw.path.get(i).X, pathToDraw.path.get(i).Y, pathToDraw.path.get(i+1).powerInitial, pathToDraw.path.get(i+1).powerFinal, MathFunctions.angleFormatting(angles[i+1]-angles[i]),angles[i],maxAngularAccel);
				}
				else {
					 arc = new simulateArc(pathToDraw.path.get(i).X, pathToDraw.path.get(i).Y, pathToDraw.path.get(i+1).powerInitial, pathToDraw.path.get(i+1).powerFinal, MathFunctions.angleFormatting(angles[i+1]-angles[i]),angles[i],pathToDraw.path.get(i+1).angularAccel);
				}
				arc.calculateArc();
				double[] intercept = new double[2];
				double arcEndPointX = arc.arc.get(arc.arc.size()-1).X;
				double arcEndPointY = arc.arc.get(arc.arc.size()-1).Y;
				if(pathToDraw.path.get(i+1).X != pathToDraw.path.get(i).X && pathToDraw.path.get(i+1).X != pathToDraw.path.get(i+2).X) {
					double slopeLine1 = 1.0*(pathToDraw.path.get(i+1).Y-pathToDraw.path.get(i).Y)/(pathToDraw.path.get(i+1).X - pathToDraw.path.get(i).X);
					double interceptLine1 = arcEndPointY - slopeLine1 * arcEndPointX;
					double slopeLine2 = 1.0*(pathToDraw.path.get(i+2).Y-pathToDraw.path.get(i+1).Y)/(pathToDraw.path.get(i+2).X - pathToDraw.path.get(i+1).X);
					double interceptLine2 = pathToDraw.path.get(i+1).Y - slopeLine2 * pathToDraw.path.get(i+1).X;
					intercept = MathFunctions.findIntercept(slopeLine1, interceptLine1, slopeLine2, interceptLine2);
				}
				else if(pathToDraw.path.get(i+1).X == pathToDraw.path.get(i).X){
					intercept = new double[2];
					intercept[0] = arcEndPointX;
					double slopeLine2 = (pathToDraw.path.get(i+2).Y-pathToDraw.path.get(i+1).Y)/(pathToDraw.path.get(i+2).X - pathToDraw.path.get(i+1).X);
					double interceptLine2 = pathToDraw.path.get(i+1).Y - slopeLine2 * pathToDraw.path.get(i+1).X;
					intercept[1] = slopeLine2 * intercept[0] + interceptLine2;
				}
				else if(pathToDraw.path.get(i+1).X == pathToDraw.path.get(i+2).X){
					intercept = new double[2];
					double slopeLine1 = (pathToDraw.path.get(i+1).Y-pathToDraw.path.get(i).Y)/(pathToDraw.path.get(i+1).X - pathToDraw.path.get(i).X);
					double interceptLine1 = arcEndPointY - slopeLine1 * arcEndPointX;
					intercept[0] = pathToDraw.path.get(i+1).X;
					intercept[1] = slopeLine1 * intercept[0] + interceptLine1;
				}
				double translateX = intercept[0] - arcEndPointX;
				double translateY = intercept[1] - arcEndPointY;
				for(Point p: arc.arc) {
					p.X += translateX;
					p.Y += translateY;
				}
				double lineEndX = arc.arc.get(0).X;
				double lineEndY = arc.arc.get(0).Y;
				double lineLength = Math.hypot(lineEndX - prevIntercept[0],lineEndY-prevIntercept[1]);
				double lineAccel = (Math.pow(pathToDraw.path.get(i+1).powerInitial,2) - Math.pow(pathToDraw.path.get(i).powerFinal,2))/(2*lineLength);
				if(pathToDraw.path.get(i+1).powerInitial > pathToDraw.path.get(i).powerFinal) {
					double timeExponentialGrowth = -kA/kV*Math.log((Vmax-kV*pathToDraw.path.get(i+1).powerInitial)/(Vmax-kV*pathToDraw.path.get(i).powerFinal));
					System.out.println("timeexpotentialGRowth" + timeExponentialGrowth);
					double distanceExponentialGrowth = -kA/kV * pathToDraw.path.get(i).powerFinal * Math.pow(Math.E, -timeExponentialGrowth * kV/kA) + Vmax/kV*timeExponentialGrowth + Vmax*kA/Math.pow(kV,2)*Math.pow(Math.E, -timeExponentialGrowth*kV/kA) + kA/kV*pathToDraw.path.get(i).powerFinal - Vmax*kA/Math.pow(kV, 2);
					overAccel[i] = distanceExponentialGrowth>lineLength;
					double lengthRemaining = lineLength-distanceExponentialGrowth;
					totalTime += timeExponentialGrowth + lengthRemaining / pathToDraw.path.get(i+1).powerInitial;
				}
				else {
					overAccel[i] = Math.abs(lineAccel) > maxAccel;
					totalTime += (lineLength/((pathToDraw.path.get(i).powerFinal + pathToDraw.path.get(i+1).powerInitial)/2));
				}
				totalTime += 2*arc.turnTime(arc.deltaHeading, arc.maxAngularAccel);
				prevIntercept = intercept;
				arcs.add(i, arc.arc);
			}
			else {
				double lineEndX = pathToDraw.path.get(i+1).X;
				double lineEndY = pathToDraw.path.get(i+1).Y;
				double lineLength = Math.hypot(lineEndX - prevIntercept[0],lineEndY-prevIntercept[1]);
				double lineAccel = (Math.pow(pathToDraw.path.get(i+1).powerInitial,2) - Math.pow(pathToDraw.path.get(i).powerFinal,2))/(2*lineLength);
				overAccel[i] = Math.abs(lineAccel) > maxAccel;
				prevIntercept[0] = lineEndX;
				prevIntercept[1] = lineEndY;
				arcs.add(null);
				totalTime += (lineLength/((pathToDraw.path.get(i).powerFinal + pathToDraw.path.get(i+1).powerInitial)/2));
			}
		}
		double lineEndX = pathToDraw.path.get(pathToDraw.path.size()-1).X;
		double lineEndY = pathToDraw.path.get(pathToDraw.path.size()-1).Y;
		double lineLength = Math.hypot(lineEndX - prevIntercept[0],lineEndY-prevIntercept[1]);
		double lineAccel = (Math.pow(pathToDraw.path.get(pathToDraw.path.size()-1).powerInitial,2) - Math.pow(pathToDraw.path.get(pathToDraw.path.size()-2).powerFinal,2))/(2*lineLength);
		overAccel[overAccel.length-1] = Math.abs(lineAccel) > maxAccel;
		prevIntercept[0] = lineEndX;
		prevIntercept[1] = lineEndY;
		arcs.add(null);
		totalTime += (lineLength/((pathToDraw.path.get(pathToDraw.path.size()-2).powerFinal + pathToDraw.path.get(pathToDraw.path.size()-1).powerInitial)/2));
		System.out.println("totalTime: "+totalTime);
	}
	public void paint(Graphics g) {
		super.paint(g);
		for(int i = 0; i < pathToDraw.path.size()-1; i++) {
			if(!overAccel[i]) {
				g.setColor(Color.black);
			}
			else {
				g.setColor(Color.red);
			}
			g.fillOval((int)(pathToDraw.path.get(i).X*scaleFactor)+xTranslate-5, -(int)(pathToDraw.path.get(i).Y*scaleFactor)+yTranslate-5, 10, 10);
			g.drawLine((int)(pathToDraw.path.get(i).X * scaleFactor)+xTranslate,-(int)( pathToDraw.path.get(i).Y * scaleFactor)+yTranslate,(int)( pathToDraw.path.get(i+1).X * scaleFactor)+xTranslate,-(int) (pathToDraw.path.get(i+1).Y*scaleFactor)+yTranslate);
		}
		for(ArrayList<Point> arc: arcs) {
			if(arc!=null) {
			for(int i = 0; i < arc.size()-1; i++) {
				g.setColor(Color.green);
				g.drawLine((int)(arc.get(i).X*scaleFactor)+xTranslate,-(int) (arc.get(i).Y*scaleFactor)+yTranslate, (int)(arc.get(i+1).X*scaleFactor)+xTranslate, -(int)(arc.get(i+1).Y*scaleFactor)+yTranslate);
			}
			}
		}
	}
}
