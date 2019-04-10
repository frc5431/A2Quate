package frc.robot.components;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator {
    private final WPI_TalonSRX leftElevator, rightElevator;

    public Elevator(){
        leftElevator = new WPI_TalonSRX(Constants.LEFT_ELEVATOR_ID);
        leftElevator.setInverted(Constants.LEFT_ELEVATOR_INVERTED);

        rightElevator = new WPI_TalonSRX(Constants.RIGHT_ELEVATOR_ID);
        rightElevator.setInverted(Constants.RIGHT_ELEVATOR_INVERTED);
    }

    public void elevator(final double val){
        leftElevator.set(val);
        rightElevator.set(val);
    }
}

