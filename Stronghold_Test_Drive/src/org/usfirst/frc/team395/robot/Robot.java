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
	
	//XBOX
	Joystick xboxController;
	final int XBOX_CONTROLLER_CHANNEL = 2;
	final int ROLLER_IN_AXIS = 2;
	final int ROLLER_OUT_AXIS = 3;
	
	//ROLLER
	Talon leftRoller;
	Talon rightRoller;
	final int LEFT_ROLLER_CHANNEL = 5;
	final int RIGHT_ROLLER_CHANNEL = 6;
	
	//AUTON
	int autonStage = 1;
	Timer autonTimer;
	boolean sequenceComplete;

    public void robotInit() {
    
    	//DRIVE
    	robotDrive = new RobotDrive(frontLeftChannel, rearLeftChannel, frontRightChannel, rearRightChannel);
    	robotDrive.setExpiration(0.1); 
    	robotDrive.setSafetyEnabled(true); 
    
    	//ROLLER
    	leftRoller = new Talon(LEFT_ROLLER_CHANNEL);
    	rightRoller = new Talon(RIGHT_ROLLER_CHANNEL);
    	
    	//JOYSTICK & XBOX
    	driveStick = new Joystick(DRIVE_STICK_CHANNEL);
    	xboxController = new Joystick(XBOX_CONTROLLER_CHANNEL);
	
    	//AUTON
    	autonTimer = new Timer();
    	sequenceComplete = false;
    }

    /**
     * This function is called periodically during autonomous
     */
    
    public void autonomousPeriodic() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    	manualDrive();
    	
    	if(xboxController.getRawAxis(ROLLER_IN_AXIS) > 0.5){
    		rightRoller.set(1.0);
    		leftRoller.set(1.0);
    	}
    	else{
    		rightRoller.set(0.0);
    		leftRoller.set(0.0);
    	}
    	
    	if(xboxController.getRawAxis(ROLLER_OUT_AXIS) > 0.5){
    		rightRoller.set(-1.0);
    		leftRoller.set(-1.0);
    	}
    	else{
    		rightRoller.set(0.0);
    		leftRoller.set(0.0);
    	}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    public void manualDrive(){
    	
    	robotDrive.arcadeDrive(driveStick);
    
    }
}
