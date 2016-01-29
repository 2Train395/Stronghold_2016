//  ___ _______        _       _____   ____  ____   ____ _______ _____ _____  _____ 
// |__ \__   __|      (_)     |  __ \ / __ \|  _ \ / __ \__   __|_   _/ ____|/ ____|
//    ) | | |_ __ __ _ _ _ __ | |__) | |  | | |_) | |  | | | |    | || |    | (___  
//   / /  | | '__/ _` | | '_ \|  _  /| |  | |  _ <| |  | | | |    | || |     \___ \ 
//  / /_  | | | | (_| | | | | | | \ \| |__| | |_) | |__| | | |   _| || |____ ____) |
// |____| |_|_|  \__,_|_|_| |_|_|  \_\\____/|____/ \____/  |_|  |_____\_____|_____/ 
//

package org.usfirst.frc.team395.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive; 
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
   
	//DRIVE
	RobotDrive robotDrive;
	final double DRIVE_FACTOR = 0.5;
	final int frontLeftChannel	= 1;
	final int rearLeftChannel	= 2;
	final int frontRightChannel	= 3;
	final int rearRightChannel	= 4;
	
	//JOYSTICKS
	Joystick driveStick;
	final int DRIVE_STICK_CHANNEL = 1;
	double twist;
	double forwardBack;
    final static double TURN_TOLERANCE_PERCENT = 0.1;
    final static double DRIVE_TOLERANCE_PRECENT = 0.25;
	
	//XBOX
	Joystick xboxController;
	final int XBOX_CONTROLLER_CHANNEL = 2;
	final int ROLLER_IN_AXIS = 2;
	final int ROLLER_OUT_AXIS = 3;
	
	//ROLLER
	Talon frontRoller;
	final int FRONT_ROLLER_CHANNEL = 5;
	
	//AUTON
	final int AUTON_MODE = 1; // 1 == LOW BAR
	int autonStage = 1; // Stages in auton meaning different actions in different stages
	Timer autonTimer; //Used to calculate time in auton
	boolean sequenceComplete; // boolean = true or false

    public void robotInit() {
    
    	//DRIVE
    	robotDrive = new RobotDrive(frontLeftChannel, rearLeftChannel, frontRightChannel, rearRightChannel);
    	robotDrive.setInvertedMotor(MotorType.kFrontRight, true);
    	robotDrive.setInvertedMotor(MotorType.kRearLeft, true);
    	robotDrive.setInvertedMotor(MotorType.kRearRight, true);
    	robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);
    	robotDrive.setExpiration(0.1); 
    	robotDrive.setSafetyEnabled(true); 
    
    	//ROLLER
    	frontRoller = new Talon(FRONT_ROLLER_CHANNEL);
    	
    	//JOYSTICK & XBOX
    	driveStick = new Joystick(DRIVE_STICK_CHANNEL);
    	xboxController = new Joystick(XBOX_CONTROLLER_CHANNEL);
    	twist = 0.0;
    	
    	//AUTON
    	autonTimer = new Timer();
    	sequenceComplete = false;
    }

    /**
     * This function is called periodically during autonomous
     */
    
    public void autonomousPeriodic() {
    	/**
     * This first mode tells the robot to move forward under 
     * the low bar to then releasing the ball and finally 
     * moving backwards to start teleop at the neutral zone.  	
     */
    	if (AUTON_MODE == 1){
    		
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    	manualDrive();
    	
    	if(xboxController.getRawAxis(ROLLER_IN_AXIS) > 0.5){
    		frontRoller.set(1.0);
    	}
    	else{
    		frontRoller.set(0.0);
    	}
    	
    	if(xboxController.getRawAxis(ROLLER_OUT_AXIS) > 0.5){
    		frontRoller.set(-1.0);
    	}
    	else{
    		frontRoller.set(0.0);
    	}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    public void manualDrive(){
    	
    	double desiredTwist = driveStick.getRawAxis(3);// Extreme 3D Pro Z-Axis

        double error = desiredTwist - twist;

        twist += error * TURN_TOLERANCE_PERCENT;
        
        // Forward / Back acceleration
        
        double desiredFB = driveStick.getRawAxis(2); // Extreme 3D Pro Y-Axis

        double fBerror = desiredFB - forwardBack;
        
        forwardBack += fBerror * DRIVE_TOLERANCE_PRECENT;
        
        robotDrive.arcadeDrive(forwardBack, twist);
    
    }
}
