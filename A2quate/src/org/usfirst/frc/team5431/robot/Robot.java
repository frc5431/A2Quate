/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5431.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private WPI_VictorSPX frontLeft, frontRight, backLeft, backRight;
	private WPI_TalonSRX leftIntake, rightIntake, leftArm, rightArm; 
	private Titan.Xbox driverXbox;
	private SpeedControllerGroup left, right, intake, arm;
	private DifferentialDrive driveBase;
	
	@Override
	public void robotInit() {
		
		driverXbox = new Titan.Xbox(0);
		
		driverXbox.setDeadzone(.15);
		
		frontLeft = new WPI_VictorSPX(9);
		frontRight = new WPI_VictorSPX(4);
		backLeft = new WPI_VictorSPX(7);
		backRight = new WPI_VictorSPX(2);
		leftIntake = new WPI_TalonSRX(10);
		rightIntake = new WPI_TalonSRX(5); rightIntake.setInverted(true);
		leftArm = new WPI_TalonSRX(6);
		rightArm = new WPI_TalonSRX(1); rightArm.setInverted(true);
		left = new SpeedControllerGroup(frontLeft, backLeft);
		right = new SpeedControllerGroup(frontRight, backRight);
		intake = new SpeedControllerGroup(leftIntake, rightIntake);
		arm = new SpeedControllerGroup(leftArm,rightArm);
		driveBase = new DifferentialDrive(left, right);
		
	}

	@Override
	public void teleopPeriodic() {
		double leftY = driverXbox.getRawAxis(Titan.Xbox.Axis.LEFT_Y); 
		double leftX = driverXbox.getRawAxis(Titan.Xbox.Axis.LEFT_X);
		//double rightY = driverXbox.getRawAxis(Titan.Xbox.Axis.RIGHT_Y); 
		//double rightX = driverXbox.getRawAxis(Titan.Xbox.Axis.RIGHT_X);
		double doubleIntake = driverXbox.getRawAxis(Titan.Xbox.Axis.TRIGGER_RIGHT);
		double doubleOutake = driverXbox.getRawAxis(Titan.Xbox.Axis.TRIGGER_LEFT);
		boolean rightBump = driverXbox.getRawButton(Titan.Xbox.Button.BUMPER_R);
		boolean leftBump = driverXbox.getRawButton(Titan.Xbox.Button.BUMPER_L);
		driveBase.tankDrive(leftY - leftX*.5, leftY + leftX*.5);
		//leftIntake.set(doubleIntake);
		//leftArm.set(doubleIntake);
		//rightArm.set(1);
		
		
		if(doubleIntake > 0.15) {
			intake.set(doubleIntake);
		}
		else if(doubleOutake > 0.15) {
			
			intake.set(-doubleOutake);
		}
		else {
			intake.set(0);
		}
	
		
		
		 if(!leftBump && rightBump)
		{
			arm.set(0.60);
		}
		else if(!rightBump && leftBump)
		{
			arm.set(-0.60);
		}
		else
		{
			arm.set(0);
		} 
		 
	}
		
	
	
	

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
