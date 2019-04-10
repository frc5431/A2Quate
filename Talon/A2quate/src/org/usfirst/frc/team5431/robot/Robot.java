/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5431.robot;

import org.usfirst.frc.team5431.robot.Titan.LogitechExtreme3D;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CameraServer;
//import edu.wpi.first.wpilibj.Compressor;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
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
	private Titan.Xbox driver;
	private LogitechExtreme3D operator;
	// private DoubleSolenoid intakePiston;
	// private Compressor compress;
	private SpeedControllerGroup left, right, intake, arm, elevator;
	private DifferentialDrive driveBase;
	private AnalogInput pot;
	private PIDController pid;
	private boolean reachedTarget;

	@Override
	public void robotInit() {
		// compress = new Compressor(0);
		// compress.setClosedLoopControl(true);
		// intakePiston = new DoubleSolenoid(0, 1);
		boolean reachedTarget = false;
		pid = new PIDController(0.5, 0, 0, 0, 
				new PIDSource() {

					@Override
					public void setPIDSourceType(PIDSourceType pidSource) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public PIDSourceType getPIDSourceType() {
						// TODO Auto-generated method stub
						return PIDSourceType.kDisplacement;
					}

					@Override
					public double pidGet() {
						// TODO Auto-generated method stub
						return pot.getAverageVoltage();
					}
			
			
		}, new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				// TODO Auto-generated method stub
				if (!reachedTarget) {
					SmartDashboard.putNumber("pid",output);
					arm.set(-output);
				}
			}});
		
		driver = new Titan.Xbox(0);
		operator = new Titan.LogitechExtreme3D(1);
		driver.setDeadzone(.15);
		operator.setDeadzone(.15);

		frontLeft = new WPI_VictorSPX(9);
		frontRight = new WPI_VictorSPX(4);

		backLeft = new WPI_VictorSPX(7);
		backRight = new WPI_VictorSPX(2);

		leftIntake = new WPI_TalonSRX(10);
		rightIntake = new WPI_TalonSRX(5);
		rightIntake.setInverted(true);

		leftArm = new WPI_TalonSRX(6);
		rightArm = new WPI_TalonSRX(1);
		rightArm.setInverted(true);

		leftElevator = new WPI_TalonSRX(3);
		rightElevator = new WPI_TalonSRX(8);

		left = new SpeedControllerGroup(frontLeft, backLeft);
		right = new SpeedControllerGroup(frontRight, backRight);

		intake = new SpeedControllerGroup(leftIntake, rightIntake);
		arm = new SpeedControllerGroup(leftArm, rightArm);
		elevator = new SpeedControllerGroup(leftElevator, rightElevator);
		driveBase = new DifferentialDrive(left, right);
		
		pot = new AnalogInput(1);
		
		CameraServer.getInstance().startAutomaticCapture("FrontCamera", 1);
		CameraServer.getInstance().startAutomaticCapture("BackCamera", 0);

		pid.setSetpoint(2.5);
		pid.setEnabled(true);
	}
	
	@Override
	public void teleopPeriodic() {
		
		double leftY = driver.getRawAxis(Titan.Xbox.Axis.LEFT_Y);
		double leftX = driver.getRawAxis(Titan.Xbox.Axis.LEFT_X);
		
 		double drivespeed = 0.5;
		
 		SmartDashboard.putNumber("pot",pot.getAverageVoltage());
 		
		driveBase.tankDrive(leftY - leftX*drivespeed, leftY + leftX*drivespeed);
		//driveBase.tankDrive(leftY, rightY);
		
		if (operator.getRawButton(Titan.LogitechExtreme3D.Button.TRIGGER)) {
			intake.set(0.75);
		} else if (operator.getRawButton(Titan.LogitechExtreme3D.Button.TEN) || operator.getRawButton(Titan.LogitechExtreme3D.Button.NINE)) {
			intake.set(-1);
		} else {
			intake.set(0);	
		}
		
		arm.set(operator.getRawAxis(Titan.LogitechExtreme3D.Axis.Y)/2);
		
		double throttle = 1-((operator.getRawAxis(Titan.LogitechExtreme3D.Axis.SLIDER)+1)/2);
		
		if (operator.getPOV() == 0) {
			elevator.set(throttle);
			// hat is being pushed forward, extend the elevator
		} else if (operator.getPOV() == 180) {
			elevator.set(-throttle);
			// hat is being pushed backward, retract the elevator
		} else {
			elevator.set(0);
			// hat is in it's default position, stop the elevator motors
		}

		// if(pistonOpen)
		// {
		// intakePiston.set(DoubleSolenoid.Value.kForward);
		// }
		// else if(pistonClose)
		// {
		// intakePiston.set(DoubleSolenoid.Value.kReverse);
		// }

		// compress.setClosedLoopControl(false);
	}

	
	@Override
	public void autonomousInit() {
	pid.setSetpoint(2.5);
	pid.setEnabled(true);
	}
	
	@Override
	public void autonomousPeriodic() {	
	SmartDashboard.putNumber("pot",pot.getAverageVoltage());
	}	
}