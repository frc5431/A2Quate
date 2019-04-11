package frc.robot.components;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Drivebase {
    private final WPI_VictorSPX frontLeft, frontRight, backLeft, backRight;

    public Drivebase(){
        frontLeft = new WPI_VictorSPX(Constants.FRONT_LEFT_ID);
        frontLeft.setInverted(Constants.FRONT_LEFT_INVERTED);

        frontRight = new WPI_VictorSPX(Constants.FRONT_RIGHT_ID);
        frontRight.setInverted(Constants.FRONT_RIGHT_INVERTED);

        backLeft = new WPI_VictorSPX(Constants.BACK_LEFT_ID);
        backLeft.setInverted(Constants.BACK_LEFT_INVERTED);

        backRight = new WPI_VictorSPX(Constants.BACK_RIGHT_ID);
        backRight.setInverted(Constants.BACK_RIGHT_INVERTED);
    }

    public void driveLeft(final double val){
        frontLeft.set(val);
        backLeft.set(val); 
    }

    public void driveRight(final double val){
        frontRight.set(val);
        backRight.set(val);
    }

    public void drive(final double left, final double right){
        driveLeft(left);
        driveRight(right);
    }


}