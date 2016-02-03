//  ___ _______        _       _____   ____  ____   ____ _______ _____ _____  _____ 
// |__ \__   __|      (_)     |  __ \ / __ \|  _ \ / __ \__   __|_   _/ ____|/ ____|
//    ) | | |_ __ __ _ _ _ __ | |__) | |  | | |_) | |  | | | |    | || |    | (___  
//   / /  | | '__/ _` | | '_ \|  _  /| |  | |  _ <| |  | | | |    | || |     \___ \ 
//  / /_  | | | | (_| | | | | | | \ \| |__| | |_) | |__| | | |   _| || |____ ____) |
// |____| |_|_|  \__,_|_|_| |_|_|  \_\\____/|____/ \____/  |_|  |_____\_____|_____/ 
//

package org.usfirst.frc.team395.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive; 
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
   
	// DRIVE
	RobotDrive robotDrive;
	final double DRIVE_FACTOR = 0.5;
	final double ROTATE_FACTOR = 0.25;
	final int frontLeftChannel	= 4;
	final int rearLeftChannel	= 3;
	final int frontRightChannel	= 1;
	final int rearRightChannel	= 2;
	boolean twistZ = false;
	
	// JOYSTICKS
	Joystick driveStick;
	Joystick xboxController;
	final int driveStickChannel = 1;
	final int XBOX_CONTROLLER_CHANNEL = 2;
	final int ROLLER_IN = 6;
	final int ROLLER_OUT = 5;
	
	// ROLLER
	Talon roller;
	final int ROLLER_CHANNEL = 1;
	final double ROLLER_SPEED = 1.0;
	
    public void robotInit() {
    
    //DRIVE
    robotDrive = new RobotDrive(frontLeftChannel, rearLeftChannel, frontRightChannel, rearRightChannel);
    robotDrive.setExpiration(0.1); 
	robotDrive.setSafetyEnabled(true); 
	robotDrive.setInvertedMotor(MotorType.kFrontRight, true);
	robotDrive.setInvertedMotor(MotorType.kRearLeft, true);
	robotDrive.setInvertedMotor(MotorType.kRearRight, true);
	robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);
    
	// JOYSTICK
	driveStick = new Joystick(driveStickChannel);
	xboxController = new Joystick(XBOX_CONTROLLER_CHANNEL);
	
	// ROLLER
	roller = new Talon(ROLLER_CHANNEL);

    }
    
    public void autonomousInit() {
  
    }

    /**1
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        rollerControl();
    	manualDrive(twistZ);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    public void manualDrive(boolean twistTypeZ){
    	double rotateValue;
    	double driveValue = driveStick.getY();
    	if(twistTypeZ) {
    		
    		rotateValue = driveStick.getZ();
    		
    	}
    	else {
    		
    		rotateValue = driveStick.getX();
    	
    	}
  	
    	driveValue *= DRIVE_FACTOR;
    	rotateValue *= ROTATE_FACTOR;
    	robotDrive.arcadeDrive(driveValue, rotateValue);
    }
    
    public void rollerControl(){	
    	if(xboxController.getRawButton(ROLLER_IN)){	
    		roller.set(ROLLER_SPEED);
    	}
    	else if(xboxController.getRawButton(ROLLER_OUT)){
    		roller.set(-ROLLER_SPEED);
    	}
    	else{
			roller.set(0.0);
		}
    	
    }
}
