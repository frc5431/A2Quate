/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5431.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
	private WPI_TalonSRX leftIntake, rightIntake, leftArm, rightArm, leftElevator, rightElevator; 
	private Titan.Xbox driverXbox, driver2Xbox;
	private DoubleSolenoid intakePiston;
	private Compressor compress;
	private SpeedControllerGroup left, right, intake, arm, elevator;
	private DifferentialDrive driveBase;
  
	
	@Override
	public void robotInit() {
      
		compress = new Compressor(0);
		compress.setClosedLoopControl(true);
		intakePiston = new DoubleSolenoid(0, 1);

		driverXbox = new Titan.Xbox(0);
		
		driverXbox.setDeadzone(.15);
      
        driver2Xbox = new Titan.Xbox(1);
		
		driver2Xbox.setDeadzone(.15);
		
		frontLeft = new WPI_VictorSPX(9);
		frontRight = new WPI_VictorSPX(4);
      
		backLeft = new WPI_VictorSPX(7);
		backRight = new WPI_VictorSPX(2);
      
		leftIntake = new WPI_TalonSRX(10);
		rightIntake = new WPI_TalonSRX(5); rightIntake.setInverted(true);
      
		leftArm = new WPI_TalonSRX(6);
		rightArm = new WPI_TalonSRX(1); rightArm.setInverted(true);
      
     	leftElevator = new WPI_TalonSRX(3);
		rightElevator = new WPI_TalonSRX(8); rightArm.setInverted(true);
      
		left = new SpeedControllerGroup(frontLeft, backLeft);
		right = new SpeedControllerGroup(frontRight, backRight);
      
		intake = new SpeedControllerGroup(leftIntake, rightIntake);
		arm = new SpeedControllerGroup(leftArm,rightArm);
		elevator = new SpeedControllerGroup(leftElevator,rightElevator);
		driveBase = new DifferentialDrive(left, right);
      //	int timer = 0;
		
	}

	@Override
	public void teleopPeriodic() {
		double leftY = driverXbox.getRawAxis(Titan.Xbox.Axis.LEFT_Y); 
		double leftX = driverXbox.getRawAxis(Titan.Xbox.Axis.LEFT_X);
      
		//double rightY = driverXbox.getRawAxis(Titan.Xbox.Axis.RIGHT_Y); 
		//double rightX = driverXbox.getRawAxis(Titan.Xbox.Axis.RIGHT_X);
      
		double doubleIntake1 = driverXbox.getRawAxis(Titan.Xbox.Axis.TRIGGER_RIGHT);
		double doubleOutake1 = driverXbox.getRawAxis(Titan.Xbox.Axis.TRIGGER_LEFT);
    	double doubleIntake2 = driver2Xbox.getRawAxis(Titan.Xbox.Axis.TRIGGER_RIGHT);
		double doubleOutake2 = driver2Xbox.getRawAxis(Titan.Xbox.Axis.TRIGGER_LEFT);
      
		boolean rightBump1 = driverXbox.getRawButton(Titan.Xbox.Button.BUMPER_R);
		boolean leftBump1 = driverXbox.getRawButton(Titan.Xbox.Button.BUMPER_L);
      
     	boolean rightBump2 = driver2Xbox.getRawButton(Titan.Xbox.Button.BUMPER_R);
		boolean leftBump2 = driver2Xbox.getRawButton(Titan.Xbox.Button.BUMPER_L);
      
		boolean elevatorup1 = driverXbox.getRawButton(Titan.Xbox.Button.X);
		boolean elevatordown1 = driverXbox.getRawButton(Titan.Xbox.Button.B);
      
		boolean elevatorup2 = driver2Xbox.getRawButton(Titan.Xbox.Button.X);
		boolean elevatordown2 = driver2Xbox.getRawButton(Titan.Xbox.Button.B);

		boolean pistonClose = driverXbox.getRawButton(Titan.Xbox.Button.A);
		boolean pistonOpen = driverXbox.getRawButton(Titan.Xbox.Button.Y);
		
		boolean pistonClose2 = driver2Xbox.getRawButton(Titan.Xbox.Button.A);
		boolean pistonOpen2 = driver2Xbox.getRawButton(Titan.Xbox.Button.Y);
		
		double drivespeed = 0.5;
      
        driveBase.tankDrive(leftY - leftX*drivespeed, leftY + leftX*drivespeed);
      
		//leftIntake.set(doubleIntake);
		//leftArm.set(doubleIntake);
		//rightArm.set(1);
		
		double intakespeed = 1;
      
		if(doubleIntake1 > 0.15) {
			intake.set(doubleIntake1*intakespeed);
		}
		else if(doubleOutake1 > 0.15) {
			
			intake.set(-doubleOutake1*intakespeed);
		}
		else {
			intake.set(0);
		}
		
		if(doubleIntake2 > 0.15) {
			intake.set(doubleIntake2*intakespeed);
		}
		else if( doubleOutake2 > 0.15) {
			intake.set(-doubleOutake2*intakespeed);
		}
		else {
			
		}
	
		double armspeed = 0.35;
      
      	
		
		 if(rightBump1 || rightBump2)
		{
			arm.set(armspeed);
		}
		else if(leftBump1 || leftBump2)
		{
			arm.set(-armspeed);
		}
		else
		{
			arm.set(0);
		} 
      
      	double elevatorspeed = .5;
      
	 	if(elevatorup1 || elevatorup2)
		{
			elevator.set(-elevatorspeed);
		}
		else if(elevatordown1 || elevatordown2)
		{
			elevator.set(elevatorspeed);
		}
		else
		{
			elevator.set(0);
		} 
	 	
	 	
	 	 if(pistonOpen || pistonOpen2)
	 	{
	 		intakePiston.set(DoubleSolenoid.Value.kForward);
	 	}
	 	else if(pistonClose || pistonClose2) 
	 	{
	 		intakePiston.set(DoubleSolenoid.Value.kReverse);
	 	}
	 	
        
		//compress.setClosedLoopControl(false);
	}
	
		
	
	
	

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}