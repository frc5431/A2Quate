package frc.robot.components;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake {
    private final WPI_TalonSRX leftIntake, rightIntake;

    public Intake(){
        leftIntake = new WPI_TalonSRX(Constants.LEFT_INTAKE_ID);
        leftIntake.setInverted(Constants.LEFT_INTAKE_INVERTED);

        rightIntake = new WPI_TalonSRX(Constants.RIGHT_INTAKE_ID);
        rightIntake.setInverted(Constants.RIGHT_INTAKE_INVERTED);
    }

    public void intake(final double val){
        leftIntake.set(val);
        rightIntake.set(val);
    }
}