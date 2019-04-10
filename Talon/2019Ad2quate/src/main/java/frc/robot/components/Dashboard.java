package frc.robot.components;

import frc.robot.*;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Dashboard {
    
  public Dashboard() {
       final CameraServer cameraServer = CameraServer.getInstance();

       final UsbCamera FrontCam = cameraServer.startAutomaticCapture("FrontCam", 1);
       final UsbCamera BackCam = cameraServer.startAutomaticCapture("BackCam", 0);
  }
}
