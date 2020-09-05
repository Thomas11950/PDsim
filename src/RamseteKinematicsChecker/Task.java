package RamseteKinematicsChecker;


public class Task {
	public double timeTaken;
	public double velocity;
	public double acceleration;
	public double angularVelo;
	public double angularAccel;
	public Task(double timeTaken, double velocity, double angularVelo, double acceleration, double angularAccel) {
		this.timeTaken = timeTaken;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.angularVelo = angularVelo;
		this.angularAccel = angularAccel;
	}
	public double getDesiredVelocity(double timeStamp) {
		return velocity + acceleration*timeStamp;
	}
	public double getDesiredAngularVelocity(double timeStamp) {
		return angularVelo + angularAccel * timeStamp;
	}
}
