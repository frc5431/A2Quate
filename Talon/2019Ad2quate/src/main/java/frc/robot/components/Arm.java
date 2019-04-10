package frc.robot.components;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Arm {
    private final WPI_TalonSRX leftArm, rightArm;

    public Arm(){
        leftArm = new WPI_TalonSRX(Constants.LEFT_ARM_ID);
        leftArm.setInverted(Constants.LEFT_ARM_INVERTED);

        rightArm = new WPI_TalonSRX(Constants.RIGHT_ARM_ID);
        rightArm.setInverted(Constants.RIGHT_ARM_INVERTED);
    }

    public void arm(final double val){
        leftArm.set(val);
        rightArm.set(val);
    }
}