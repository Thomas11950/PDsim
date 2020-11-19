package DataGrapher;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PointFormatterFF {

	public static void main(String[] args) {
		System.out.println("herro");
		// TODO Auto-generated method stub
		File fileContainingPoints = new File("C:\\engineering\\robotics\\feedforwarddatageneric.txt");
        Scanner scnr;
        try {
            scnr = new Scanner(fileContainingPoints);
        }
        catch(FileNotFoundException e){
            return;
        }
        while(scnr.hasNextLine()){
		String pointInStringFormat = scnr.nextLine();
        int powerPosition = pointInStringFormat.indexOf("Power: ");
        String powerString = pointInStringFormat.substring(powerPosition + 7);
        for(int i = 0; i < powerString.length(); i++){
            if(powerString.substring(i,i+1).equals(",")){
                powerString = powerString.substring(0,i);
                i=powerString.length();
            }
        }
        double power = Double.parseDouble(powerString);
        int veloPosition = pointInStringFormat.indexOf("Velo: ");
        String veloString = pointInStringFormat.substring(veloPosition + 6);
        for(int i = 0; i < veloString.length(); i++){
            if(veloString.substring(i,i+1).equals(",")){
                veloString = veloString.substring(0,i);
                i=veloString.length();
            }
        }
        double velo = Double.parseDouble(veloString);
        double voltage = identifyNumberInString(pointInStringFormat, "Voltage: ");
        double accel = identifyNumberInString(pointInStringFormat, "Accel: ");
        /*int accelPosition = pointInStringFormat.indexOf("Accel: ");
        String accelString = pointInStringFormat.substring(accelPosition + 7);
        for(int i = 0; i < accelString.length(); i++){
            if(accelString.substring(i,i+1).equals(",")){
                accelString = accelString.substring(0,i);
                i=accelString.length();
            }
        }
        double accel = Double.parseDouble(accelString);*/
        /*int timePosition = pointInStringFormat.indexOf("Time: ");
        String timeString = pointInStringFormat.substring(timePosition + 6);
        for(int i = 0; i < timeString.length(); i++){
            if(timeString.substring(i,i+1).equals(",")){
                timeString = timeString.substring(0,i);
                i=timeString.length();
            }
        }
        double time = Double.parseDouble(timeString);*/
        //System.out.println("("+(time)+","+velo+")");
        System.out.println(voltage);
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
