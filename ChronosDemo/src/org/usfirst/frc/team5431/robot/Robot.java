/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import org.usfirst.frc.team5431.robot.Titan.Command;
import org.usfirst.frc.team5431.robot.subsystems.ExampleSubsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private Titan.Xbox driverXbox;
	private WPI_TalonSRX frontLeft, frontRight, rearLeft, rearRight,shootLeft,shootRight,intake;
	private DifferentialDrive driveBase;
	//private SpeedControllerGroup frontLeftGroup, frontRightGroup, rearLeftGroup, rearRightGroup;
	

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void robotInit() {
	/**
	 * initializing all the variables in the project
	 */
	driverXbox = new Titan.Xbox(0);
	
	driverXbox.setDeadzone(0.15);
	
	frontLeft = new WPI_TalonSRX(2);	//frontLeft.setInverted(true);
	frontRight = new WPI_TalonSRX(8);	//frontRight.setInverted(true);
	rearLeft = new WPI_TalonSRX(3);		//rearLeft.setInverted(true);
	rearRight = new WPI_TalonSRX(7);	//rearRight.setInverted(true);
	shootLeft = new WPI_TalonSRX(4);	//rearRight.setInverted(true);
	shootRight = new WPI_TalonSRX(6); 
	intake = new WPI_TalonSRX(5); 
	
}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
