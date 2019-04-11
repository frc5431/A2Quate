package frc.robot.components;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Titan;
import frc.robot.components.*;

public class Teleop {
    private Titan.Xbox driver;
    private Titan.LogitechExtreme3D operator;


    public Teleop(){
        driver = new Titan.Xbox(Constants.DRIVER_JOYSTICK_ID);
        driver.setDeadzone(Constants.DRIVER_JOYSTICK_DEADZONE);

        operator = new Titan.LogitechExtreme3D(Constants.OPERATOR_JOYSTICK_ID);
        operator.setDeadzone(Constants.OPERATOR_JOYSTICK_DEADZONE);

    }

    public void periodic(final Robot robot){

        double leftY = driver.getRawAxis(Titan.Xbox.Axis.LEFT_Y);
        double leftX = driver.getRawAxis(Titan.Xbox.Axis.LEFT_X);

        double drivespeed = 0.5;
        
        robot.getDrivebase().drive(leftY - leftX*drivespeed, leftY + leftX*drivespeed);
		//driveBase.tankDrive(leftY, rightY);
		
		if (operator.getRawButton(Titan.LogitechExtreme3D.Button.TRIGGER)) {
            robot.getIntake().intake(0.75);
		} else if (operator.getRawButton(Titan.LogitechExtreme3D.Button.TEN) || operator.getRawButton(Titan.LogitechExtreme3D.Button.NINE)) {
			robot.getIntake().intake(-1);
		} else {
			robot.getIntake().intake(0);
		}
		
        
        robot.getArm().arm(operator.getRawAxis(Titan.LogitechExtreme3D.Axis.Y)/2);
		
		double throttle = 1-((operator.getRawAxis(Titan.LogitechExtreme3D.Axis.SLIDER)+1)/2);
		
		if (operator.getPOV() == 0) {
            robot.getElevator().elevator(throttle);
			// hat is being pushed forward, extend the elevator
		} else if (operator.getPOV() == 180) {
            robot.getElevator().elevator(-throttle);
			// hat is being pushed backward, retract the elevator
		} else {
            robot.getElevator().elevator(0);
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
    public Titan.Xbox getDriver(){
        return driver;
    }
}