//
//   _______ ______________                       ___ _______    ______     _________    ______ ______________ ______________   ________  ________
//  /       \              \                    _(  _)       \  /      \   /         \  /      \              \              \ /        \/        \
// |    _    \_____    _____\             __   (___)   ___    \/        \ |   ____    \/        \_____    _____\_____    _____/    _____/   ______/
//  \__/ \    |    |  |                  (__)      |  /   \   |   ____   \|  |    \   /   ____   \    |  |           |  |    /    /    /   /
//      /    |     |  |__ ____ ___  ___ __ __ ____ |  \___/   /  /    \   \  |____/  /   /    \   \   |  |           |      /    /     \   \_____
//     /    /      |  |  '   /    ``   /  /  '_   \|         /  |      |   |  ____  |   |      |   |  |  |           |  |  |    |       \        \
//    /    /       |  |   __/   __    /  /  |  \   |   ___   \   \____/   /  |    \  \   \____/   /   |  |           |  |   \    \       \____    \
//   /    /________|  |  |  |  (__|   |  |  |  |   |  |   \   \          /|  |____/   \          /    |  |___________|  |____\    \_____ _____|   |
//  |              |  |  |  |         |  |  |  |   |  |    \   \        / |           /\        /     |  |                    \         \         /
//  |______________|__|__|___\_____/\_|__|__|__|___|__|_____\___\______/___\_________/__\______/______|__|_____________________\________/________/
//  /                                                                                                                                            \
// |      --      --      --      --      --      --      --      --      --      --      --      --      --      --      --      --      --      |
//  \____________________________________________________________________________________________________________________________________________/
//   /      \      /      \                 /      \      /      \                  /      \      /      \                 /      \      /      \
//  /    ____\____/____    \               /    ____\____/____    \                /    ____\____/____    \               /    ____\____/____    \
// |    ()____________()    |             |    ()____________()    |              |    ()____________()    |             |    ()____________()    |
//  \        /    \        /               \        /    \        /                \        /    \        /               \        /    \        /
//   \______/      \______/                 \______/      \______/                  \______/      \______/                 \______/      \______/
//

package org.usfirst.frc.team395.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive; 
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;

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
	double ARM_SPEED = 0.6; 
	final double DRIVE_FACTOR = 0.5;					//----TEST
	final double ROTATE_FACTOR = 0.85;					//----TEST
	
	final int frontLeftChannel	= 4;
	final int rearLeftChannel	= 3;
	final int frontRightChannel	= 1;
	final int rearRightChannel	= 2;
	
	// JOYSTICKS
	Joystick driveStick;
	Joystick xboxController;
	final int driveStickChannel = 1;
	final int XBOX_CONTROLLER_CHANNEL = 2;

	final int ROLLER_IN = 6;
	final int ROLLER_OUT = 5;
	final int ARM_UP = 1;
	final int ARM_DOWN = 4;
	
	final int WINCH_UP = 3;
	final int WINCH_DOWN = 2;
	
	// ROLLER
	Talon roller;
	final int ROLLER_CHANNEL = 5;
	final double ROLLER_SPEED = 0.75;					//----TEST

	// ARM
	Talon RIGHT_ARM;
	Talon LEFT_ARM;
	final int LEFT_ARM_CHANNEL = 6;
	final int RIGHT_ARM_CHANNEL = 7;
	double REVERSE_ARM = 1;
		
	// AUTONOMOUS
	double autonMove;
	double autonRotate;
	int autonStage = 1;
	Timer autonTimer;
	final double STOP_TIME = 1.00;
	final double MOVE_TIME = 5.00;				// TEST BEFORE USING!!!
	final double RELEASE_TIME = 3.00;
	final int AUTON_MODE = 4;
	boolean sequenceComplete;
	    
	//ANALOG 
	AnalogGyro gyro;
	final int GYRO_CHANNEL =  0;
	final double GYRO_SENSITIVITY = 0.007;
	final int TEMP_CHANNEL = 1;
	final double GYRO_CORRECTION = 0.05;

	AnalogInput ultra;
	//CONSTANTS
	final int ULTRASONIC_CHANNEL = 1;
    final static double SONAR_INCHES_PER_VOLT = 102.4;      // 512/(Vcc = 5V)
    final static double SONAR_OFFSET = 0.0;                 // Calibration
    final static double SAMPLE_TIME = 0.01;                 // Seconds
    final static int DESIRED_SAMPLE_SIZE = 50;              // # of samples
    // VARIABLES
    double[] sonarSamples;
    int sampleCount;
    double distanceSum;
    double distance;
    boolean sonarSampling;

	//DIGITAL
	DigitalInput topLimitSwitch;
	DigitalInput bottomLimitSwitch;
	final int TOP_LIMIT_SWITCH_CHANNEL = 1;
	final int BOTTOM_LIMIT_SWITCH_CHANNEL = 2;
		
	//PID
	PIDController gyroPID;
	final double ROTATE_PID_GAIN_P = 0.3; 	//0.01 (?)
	final double ROTATE_PID_GAIN_I = 0.0000;   //why is the integral constant 0? (0.001)?
	final double ROTATE_PID_GAIN_D = 0.0000;	//0.00 (?)
	RotatePIDOutput PIDOutput;
	
	// WINCH 
	Talon winch;
	final int WINCH_CHANNEL = 9;
	final double WINCH_SPEED = 1.0;
	
	//TIMER
	RobotTimer robotTimer;
	Timer sonarTimer;
	final int TIMER_TOGGLE = 11;
	final int TIMER_RESET = 12;
	
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
		LEFT_ARM = new Talon(LEFT_ARM_CHANNEL);
		RIGHT_ARM = new Talon(RIGHT_ARM_CHANNEL);
		
		//ANALOG
		gyro = new AnalogGyro(GYRO_CHANNEL);
		gyro.setSensitivity(GYRO_SENSITIVITY);
		gyro.calibrate();
		
		//DIGITAL
		topLimitSwitch = new DigitalInput(TOP_LIMIT_SWITCH_CHANNEL);
		bottomLimitSwitch = new DigitalInput(BOTTOM_LIMIT_SWITCH_CHANNEL);
		
		ultra = new AnalogInput(ULTRASONIC_CHANNEL);
        sonarSamples = new double[DESIRED_SAMPLE_SIZE];
        sampleCount = 0;
        distanceSum = 0;
        distance = 0.0;
        sonarSampling = false;
		//ultra.setEnabled(true);
		//ultra.setAutomaticMode(true);
		
		//DASHBOARD
		SmartDashboard.putNumber("gyro", gyro.getAngle());
		SmartDashboard.putNumber("voltage", ultra.getVoltage());
    	SmartDashboard.putBoolean("LimitSwitchTop", topLimitSwitch.get());
    	SmartDashboard.putBoolean("BottomLimitSwitch", bottomLimitSwitch.get());
    	SmartDashboard.putNumber("distance", distance);
    	
		//PID
		PIDOutput = new RotatePIDOutput(robotDrive);
		gyroPID = new PIDController(ROTATE_PID_GAIN_P, ROTATE_PID_GAIN_I, ROTATE_PID_GAIN_D, gyro ,PIDOutput);
		gyroPID.disable();
		
		//AUTON
		autonTimer = new Timer();
		sequenceComplete = false;

		//WINCH
		winch = new Talon(WINCH_CHANNEL);
		
		//TIMER
		robotTimer = new RobotTimer(driveStick, TIMER_TOGGLE, TIMER_RESET);
		sonarTimer = new Timer();

	}
	
	@SuppressWarnings("unused")
	public void autonomousPeriodic() {

		if (robotTimer.getMode() != 0) robotTimer.setMode(0);
		
		if (AUTON_MODE == 1){
			if(autonStage==1){
				
				autonTimer.reset();
				autonTimer.start();
	
				gyro.reset();
				
				while(autonTimer.get() < MOVE_TIME){	
					double angle = gyro.getAngle();
					autonMove = 0.5;			//MAGIC NUMBER, use constants
					//autonRotate = 0.0;
					robotDrive.arcadeDrive(autonMove, -angle * GYRO_CORRECTION);
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
				gyro.reset();
				
				while(autonTimer.get() < MOVE_TIME){	
					
					double angle = gyro.getAngle();
					autonMove = -0.9;			//MAGIC NUMBER, use constants
					//autonRotate = 0.0;
					robotDrive.arcadeDrive(autonMove, -angle * GYRO_CORRECTION);
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
			gyro.reset();
				
			while(autonTimer.get() < MOVE_TIME){
				SmartDashboard.putNumber("gyro", gyro.getAngle());		   			
				double angle = gyro.getAngle();
				autonMove = 1.0;
				//autonRotate = 0.0;
				robotDrive.arcadeDrive(-autonMove, -angle * GYRO_CORRECTION);
			}
		
			autonTimer.stop();
			autonStage = 2;
		
			}
			else if(autonStage == 2){
					
				autonTimer.reset();
				autonTimer.start();
				
				while(autonTimer.get() < RELEASE_TIME){	
					robotDrive.arcadeDrive(0.0 , 0.0);
					roller.set(ROLLER_SPEED);
				}
			
				autonTimer.stop();
				autonStage = 3;  
			}
			else if(autonStage == 3){
					
				autonTimer.reset();
				autonTimer.start();

				gyro.reset();
				gyroPID.disable();
				
				gyroPID.setSetpoint(-90);
				gyroPID.enable();
				while(autonTimer.get() < 15){
					SmartDashboard.putNumber("gyro", gyro.getAngle());	    		
					//roller.set(0.0);
					
					//double angle = gyro.getAngle();
					//autonMove = 0.5;
					//autonRotate = 0.0;
					//robotDrive.arcadeDrive(autonMove, -angle * GYRO_CORRECTION);
				}
				
				gyroPID.disable();
				
				autonTimer.stop();	    	
				autonStage = 4;
				
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
					autonMove = -0.70;		//MAGIC NUMBER, use constants
					autonRotate = 0.0;
					robotDrive.arcadeDrive(autonMove, autonRotate);
				}
				
				autonStage = 2;
				autonTimer.stop();
			}
			else if(autonStage == 2){
				autonTimer.reset();
				autonTimer.start();
				
				while(autonTimer.get() < RELEASE_TIME){	
					robotDrive.arcadeDrive(0.0 , 0.0);
					roller.set(ROLLER_SPEED);
				}
				
				autonStage = 3;
				autonTimer.stop();
			}
			else if(autonStage == 3){	
				autonTimer.reset();
				autonTimer.start();
				
				while(autonTimer.get() < MOVE_TIME){	
					roller.set(0.0);
					autonMove = 0.70;		//MAGIC NUMBER, use constants
					autonRotate = 0.0;
					robotDrive.arcadeDrive(autonMove, autonRotate);
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
		/**else if(AUTON_MODE == 4){
			double initialAngle;
			if(autonStage == 1){
				autonTimer.reset();
				autonTimer.start();
				gyro.reset();
				gyroPID.reset();
				initialAngle = gyro.getAngle();
				gyroPID.setSetpoint(initialAngle + 45);
				gyroPID.setPID(ROTATE_PID_GAIN_P, ROTATE_PID_GAIN_I, ROTATE_PID_GAIN_D);
				gyroPID.enable();
				autonStage++;
			}
			else if(autonStage == 2){
				robotDrive.arcadeDrive(0, gyroPID.get());
				if(gyro.getAngle == initialAngle)
			}
		}*/
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
   // 	randomDashboard();
		if (robotTimer.getMode() != 1) robotTimer.setMode(1);
		manualDrive();
		rollerControl();
		armControl();
		winchControl();
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
	
	}

	public void manualDrive(){

		//CONSIDER: logarithmic scale for more precise controls.
		
		double rotateValue = driveStick.getZ();;
		
		double driveValue = driveStick.getY();	
			
		robotDrive.arcadeDrive(driveValue , rotateValue * ROTATE_FACTOR);

		SmartDashboard.putNumber("gyro", gyro.getAngle());
		SmartDashboard.putNumber("voltage", ultra.getVoltage());
		SmartDashboard.putNumber("distance", distance);
		
	//	driveValue *= DRIVE_FACTOR;
		rotateValue *= ROTATE_FACTOR;
		robotDrive.arcadeDrive(driveValue, rotateValue);
		
	}
	
	public void rollerControl(){	

		if(xboxController.getRawButton(ROLLER_IN)){	
			roller.set(0.8);
		}
		else if(xboxController.getRawButton(ROLLER_OUT)){
			roller.set(-1.0);
		}
		else{
			roller.set(0.0);
		}
		
	}
	
    public void armControl() {
    	       	
    	SmartDashboard.putBoolean("LimitSwitchTop", topLimitSwitch.get());
    	SmartDashboard.putBoolean("BottomLimitSwitch", bottomLimitSwitch.get());
    	if (xboxController.getRawButton(ARM_UP) && bottomLimitSwitch.get()) {	
    		
    		LEFT_ARM.set(-ARM_SPEED * REVERSE_ARM);
    		RIGHT_ARM.set(ARM_SPEED * REVERSE_ARM);

    	}
    	
    	else if (xboxController.getRawButton(ARM_DOWN) && topLimitSwitch.get()) {
    		
    		LEFT_ARM.set(ARM_SPEED * REVERSE_ARM);
    		RIGHT_ARM.set(-ARM_SPEED * REVERSE_ARM);

    		
    	}
    	else{

    		RIGHT_ARM.set(0.0);
    		LEFT_ARM.set(0.0);
    		
    	}
	
    }
    public void winchControl(){
    	
    	if (xboxController.getRawButton(WINCH_UP)){
    		winch.set(-WINCH_SPEED);
    	}
    	
    	// IMPORTANT: DO NOT REVERSE DURING COMPETITION / WHEN RACHETIS ENGAGED!!!!
    	else if (xboxController.getRawButton(WINCH_DOWN)){
    		winch.set(WINCH_SPEED);
    	}
    	
    	else {
    		winch.set(0.0);
    	}
    }
 /*   public void randomDashboard(){
		DRIVE_FACTOR = SmartDashboard.getNumber("Drive Factor");
		ROTATE_FACTOR = SmartDashboard.getNumber("Rotate Factor");
		ARM_SPEED = SmartDashboard.getNumber("Arm Speed");
		REVERSE_ARM = SmartDashboard.getNumber("Reverse Arm");
		if(REVERSE_ARM != -1 || REVERSE_ARM != 1){
			REVERSE_ARM = 1;
		}
		SmartDashboard.putNumber("Drive Factor", DRIVE_FACTOR);
		SmartDashboard.putNumber("Rotate Factor", ROTATE_FACTOR);
		SmartDashboard.putNumber("Arm Speed", ARM_SPEED);
		SmartDashboard.putNumber("Reverse Arm", REVERSE_ARM);
	}*/
    public void computeDistance() {
        
        if (!sonarSampling){
            sonarSampling = true;
            sonarTimer.reset();
            sonarTimer.start();
        }
        else {
            
            if (sonarTimer.get() > SAMPLE_TIME) {
                
                sonarSampling = false;
                sonarTimer.stop();
                
                if(sampleCount < sonarSamples.length){
                    
                    sonarSamples[sampleCount++] = ultra.getVoltage();
                    
                }
                else {
                    
                    distanceSum = 0.0;
                    
                    for(int i = 0; i < sampleCount; i++) {
                        distanceSum += sonarSamples[i];
                    }
                    
                    distance = (distanceSum / ((double)sampleCount)) 
                                    * SONAR_INCHES_PER_VOLT;
                    
                    sampleCount = 0;
                }
            }
                
        }
        
    
    }

}

