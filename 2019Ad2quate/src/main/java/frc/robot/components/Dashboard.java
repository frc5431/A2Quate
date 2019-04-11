package frc.robot.components;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.*;

import frc.robot.*;

public class Dashboard {
    
    public Dashboard(){
        final CameraServer cameraServer = CameraServer.getInstance();

        final  UsbCamera frontCamera = cameraServer.startAutomaticCapture("FrontCamera", 1);
        final  UsbCamera backCamera = cameraServer.startAutomaticCapture("BackCamera", 0);
    }
}