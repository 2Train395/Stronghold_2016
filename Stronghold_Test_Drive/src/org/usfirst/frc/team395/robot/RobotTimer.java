package org.usfirst.frc.team395.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotTimer extends TimerTask {
	
	private final Joystick JOYSTICK; //Joystick to gather input from
	private final int TOGGLE; //Button on joystick to be used for toggling timer
	private final int RESET; //Button on joystick to be used for resetting timer
	private boolean running; //Whether or not the timer is running
	
	private int mode; //0 for autonomous, 1 for teleop
	private int beginTime; //The amount of seconds the mode specifies to begin with
	private long startTime; //System time in milliseconds at start
	private int elapsedTime; //Amount of time passed since startTime in seconds
	private int remainingTime; //Amount of time left in seconds (is displayed on dashboard)
	
	public RobotTimer(Joystick joystick, int toggleButton, int resetButton) {
		
		JOYSTICK = joystick;
		TOGGLE = toggleButton;
		RESET = resetButton;
		new Timer().schedule(this, 0, 1);
	}
	
	/**
	 * Gets the current mode.
	 * @return
	 */
	public int getMode() {
		
		return this.mode;
	}
	
	/**
	 * Sets the current mode.
	 * @param mode 0 for autonomous and 1 for teleop
	 * @throws IndexOutOfBoundsException If the mode is not 0 or 1
	 */
	public void setMode(int mode) {
		
		if (mode < 0 || mode > 1) {
			
			throw new IndexOutOfBoundsException("Mode must be set to 0 or 1");
		}
		else {
			
			this.mode = mode;
			beginTime = mode == 0 ? 15 : 135;
			remainingTime = beginTime;
			startTime = System.currentTimeMillis();
		}
	}
	
	private String formatTime() {
		
		remainingTime = (beginTime - elapsedTime) <= 0 ? 0 : beginTime - elapsedTime;
		String min = String.valueOf(Math.round(remainingTime / 60));
		String sec = String.valueOf(Math.round(((remainingTime / 60F) % 1) * 60));
		return (Integer.valueOf(min) <= 9 ? "0" : "") + min + ":" + (Integer.valueOf(sec) <= 9 ? "0" : "") + sec;
	}

	@Override
	public void run() {
		
		if (JOYSTICK.getRawButton(TOGGLE)) {
			
			running = !running;
		}
		
		if (JOYSTICK.getRawButton(RESET)) {
			
			startTime = mode == 0 ? 15 : 135;
		}
		
		if (running) {
			
			elapsedTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
			SmartDashboard.putString("Time Remaining", formatTime());
		}
	}
}
