package org.usfirst.frc.team395.robot;


import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;

public class RotatePIDOutput implements PIDOutput {

	private RobotDrive m_robotDrive;

	public RotatePIDOutput(RobotDrive roboDrive){
		
		m_robotDrive = roboDrive;
	}
	
	public void pidWrite(double output){
		m_robotDrive.arcadeDrive( 0.0, output); //TEST THE PID
	}
}
