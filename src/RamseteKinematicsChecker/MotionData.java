package RamseteKinematicsChecker;


public class MotionData {
	public double desiredHeading;
	public double desiredAngularVelocity;
	public double desiredVelocity;
	public MotionData(double desiredAngularVelocity, double desiredVelocity) {
		this.desiredHeading = desiredHeading;
		this.desiredAngularVelocity = desiredAngularVelocity;
		this.desiredVelocity = desiredVelocity;
	}
}
