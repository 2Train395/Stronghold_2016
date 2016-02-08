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
import edu.wpi.first.wpilibj.Timer;

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
	final double DRIVE_FACTOR = 0.5;					//----TEST
	final double ROTATE_FACTOR = 0.;					//----TEST
	final int frontLeftChannel	= 4;
	final int rearLeftChannel	= 3;
	final int frontRightChannel	= 1;
	final int rearRightChannel	= 2;
	boolean twistZ = true;
	boolean xbRoller = true;
	//----CAN CHANGE
	
	// JOYSTICKS
	Joystick driveStick;
	Joystick xboxController;
	final int driveStickChannel = 1;
	final int XBOX_CONTROLLER_CHANNEL = 2;
	final int ROLLER_INXB = 6;
	final int ROLLER_OUTXB = 5;
	final int ROLLER_INJS = 1;
	final int ROLLER_OUTJS = 2;
	final int ARM_UP = 4;
	final int ARM_DOWN = 1;

	// ROLLER
	Talon roller;
	final int ROLLER_CHANNEL = 5;
	final double ROLLER_SPEED = 0.75;					//----TEST

	// ARM
	Talon right_arm;
	Talon left_arm;
	final int LEFT_ARM_CHANNEL = 6;
	final int RIGHT_ARM_CHANNEL = 7;
		
	// AUTONOMOUS
	double autonMoveSpeed;
	double autonRotateSpeed;
	int autonStage = 1;
	final int AUTON_MODE = 1;
	Timer autonTimer;
	final double STOP_TIME = 1.00;
	final double MOVE_TIME = 2.15;				// TEST BEFORE USING!!!
	final double RELEASE_TIME = 1.0;
	
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
		
		// ARM
		left_arm = new Talon(LEFT_ARM_CHANNEL);
		right_arm = new Talon(RIGHT_ARM_CHANNEL);
		
    }
    
    public void autonomousPeriodic() {
        
	    if (AUTON_MODE == 1){
	    	if(autonStage==1){
	        	
	   		autonTimer.reset();
	   		autonTimer.start();
	    	
	   		while(autonTimer.get() < MOVE_TIME){	
	    		
	   			autonMoveSpeed = 0.9;
	    		autonRotateSpeed = 0.0;
	    		robotDrive.arcadeDrive(autonMoveSpeed, autonRotateSpeed);
	    	}
	    	
	   		autonStage = 2;
	    	autonTimer.stop();
	   	}
	    else if(autonStage == 2){
	    		
	   		autonTimer.reset();
    		autonTimer.start();
	    	
    		while(autonTimer.get() < RELEASE_TIME){	
	    		
    			robotDrive.arcadeDrive(0.0 , 0.0);
	    		roller.set(-ROLLER_SPEED);
	    	}
	   		
    		autonStage = 3;
	   		autonTimer.stop();
	    }
	    else if(autonStage == 3){
	    		
	    	autonTimer.reset();
	   		autonTimer.start();
	    	
	   		while(autonTimer.get() < MOVE_TIME){	
	    	
	   			autonMoveSpeed = -0.9;
	    		autonRotateSpeed = 0.0;
	    		robotDrive.arcadeDrive(autonMoveSpeed, autonRotateSpeed);
	    	}
	    	
	    	autonStage = 4;
	    	autonTimer.stop();
	    	
	   	}
	   	
	    else if(autonStage == 4){
	    	
	    	autonTimer.reset();
	    	autonTimer.start();
	    	
	    		robotDrive.arcadeDrive(0.0 , 0.0);
	    	
	    	autonTimer.stop();
	    	
	    	}
	    }
	    
	    if (AUTON_MODE == 2){

	    	if(autonStage==1){
	        	
	   		autonTimer.reset();
	   		autonTimer.start();
	    	
	   		while(autonTimer.get() < MOVE_TIME){	
	    		
	   			autonMoveSpeed = 0.9;
	    		autonRotateSpeed = 0.0;
	    		robotDrive.arcadeDrive(autonMoveSpeed, autonRotateSpeed);
	    	}
	    	
	   		autonStage = 2;
	    	autonTimer.stop();
	   	}
	    else if(autonStage == 2){
	    		
	   		autonTimer.reset();
    		autonTimer.start();
	    	
    		while(autonTimer.get() < RELEASE_TIME){	
	    		
    			robotDrive.arcadeDrive(0.0 , 0.0);
	    		roller.set(-ROLLER_SPEED);
	    	}
	   		
    		autonStage = 3;
	   		autonTimer.stop();
	    }
	    else if(autonStage == 3){
	    		
	    	autonTimer.reset();
	   		autonTimer.start();
	    	
	   		while(autonTimer.get() < MOVE_TIME){	
	    	
	   			autonMoveSpeed = -0.9;
	    		autonRotateSpeed = 0.0;
	    		robotDrive.arcadeDrive(autonMoveSpeed, autonRotateSpeed);
	    	}
	    	
	    	autonStage = 4;
	    	autonTimer.stop();
	    	
	   	}
	   	
	    else if(autonStage == 4){
	    	
	    	autonTimer.reset();
	    	autonTimer.start();
	    	
	    		robotDrive.arcadeDrive(0.0 , 0.0);
	    	
	    	autonTimer.stop();
	    	
	    	}
	    }

	    if (AUTON_MODE == 3){
	    	
	    	if(autonStage==1){
	        	
	   		autonTimer.reset();
	   		autonTimer.start();
	    	
	   		while(autonTimer.get() < MOVE_TIME){	
	    		
	   			autonMoveSpeed = 0.9;
	    		autonRotateSpeed = 0.0;
	    		robotDrive.arcadeDrive(autonMoveSpeed, autonRotateSpeed);
	    	}
	    	
	   		autonStage = 2;
	    	autonTimer.stop();
	   	}
	    else if(autonStage == 2){
	    		
	   		autonTimer.reset();
    		autonTimer.start();
	    	
    		while(autonTimer.get() < RELEASE_TIME){	
	    		
    			robotDrive.arcadeDrive(0.0 , 0.0);
	    		roller.set(-ROLLER_SPEED);
	    	}
	   		
    		autonStage = 3;
	   		autonTimer.stop();
	    }
	    else if(autonStage == 3){
	    		
	    	autonTimer.reset();
	   		autonTimer.start();
	    	
	   		while(autonTimer.get() < MOVE_TIME){	
	    	
	   			autonMoveSpeed = -0.9;
	    		autonRotateSpeed = 0.0;
	    		robotDrive.arcadeDrive(autonMoveSpeed, autonRotateSpeed);
	    	}
	    	
	    	autonStage = 4;
	    	autonTimer.stop();
	    	
	   	}
	   	
	    else if(autonStage == 4){
	    	
	    	autonTimer.reset();
	    	autonTimer.start();
	    	
	    		robotDrive.arcadeDrive(0.0 , 0.0);
	    	
	    	autonTimer.stop();
	    	
	    	}
	    }
	}

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    	manualDrive();
    	rollerControl();
    	armControl();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    public void manualDrive(){
    	
    	double rotateValue;
    	double driveValue = driveStick.getY();
    	if(twistZ) {
    		
    		rotateValue = driveStick.getZ();    	}
    	else {
    		
    		rotateValue = driveStick.getX();
    	}
  	
    	//driveValue *= DRIVE_FACTOR;
    	rotateValue *= ROTATE_FACTOR;
    	robotDrive.arcadeDrive(driveValue , rotateValue);
    }
    
    public void rollerControl(){	
    	
    	if((xboxController.getRawButton(ROLLER_INXB) && xbRoller) || (driveStick.getRawButton(ROLLER_INJS) && !xbRoller)){	
    		
    		roller.set(ROLLER_SPEED);
    	}
    	else if((xboxController.getRawButton(ROLLER_OUTXB) && xbRoller) || (driveStick.getRawButton(ROLLER_OUTJS) && !xbRoller)){
    	
    		roller.set(-ROLLER_SPEED);
    	}
    	else{
			
    		roller.set(0.0);
		}
    	
    }
    
    public void armControl() {
    	
    	if (xboxController.getRawButton(ARM_UP)) {
    		
    		right_arm.set(1.0);
    		left_arm.set(1.0);
    	}
    	else if (xboxController.getRawButton(ARM_DOWN)) {
    		
    		right_arm.set(-1.0);
    		left_arm.set(-1.0);
    	}
    	else {
    		
    		right_arm.set(0.0);
    		left_arm.set(0.0);
    	}
    }
}
