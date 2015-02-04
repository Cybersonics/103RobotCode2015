
package org.usfirst.frc.team103.robot;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

public class Robot extends SampleRobot {
	CANTalon talon0, talon1, talon2, talon3;
	Talon tower;
	DoubleSolenoid s0, s1;
    RobotDrive drive;
    Joystick controller;
    Compressor compressor;
    CameraServer server;

    public Robot() {
    	talon0 = new CANTalon(0);
    	talon1 = new CANTalon(1);
    	talon2 = new CANTalon(2);
    	talon3 = new CANTalon(3);
    	talon0.changeControlMode(ControlMode.PercentVbus);
    	talon1.changeControlMode(ControlMode.Follower);
    	talon2.changeControlMode(ControlMode.Follower);
    	talon3.changeControlMode(ControlMode.PercentVbus);
    	talon1.set(0);
    	talon2.set(3);
    	
    	s0 = new DoubleSolenoid(0,1);
    	s1 = new DoubleSolenoid(2,3);
    	
        drive = new RobotDrive(talon0, talon3);
        drive.setSafetyEnabled(true);
        drive.setExpiration(0.1);

        tower = new Talon(0);
        tower.setSafetyEnabled(true);
        tower.setExpiration(0.1);
        
        controller = new Joystick(0);
        
        compressor.start();
        SmartDashboard.putBoolean("pcm", compressor.getClosedLoopControl());
        
        server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam0");
    }

    public void operatorControl() {
    	talon0.enableControl();
    	talon1.enableControl();
    	talon2.enableControl();
    	talon3.enableControl();
        while (isOperatorControl() && isEnabled()) {
        	double slowMod = controller.getRawButton(6) ? 1.5 : 1.0;
            drive.tankDrive(-controller.getRawAxis(1) / slowMod, -controller.getRawAxis(5) / slowMod, true);
            tower.set(controller.getRawAxis(3) - controller.getRawAxis(2));
            Timer.delay(0.005);
        }
    }
}
