
package org.usfirst.frc.team103.robot;


import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

public class Robot extends SampleRobot {
	CANTalon talon0, talon1, talon2, talon3;
	Talon tower;
	AnalogPotentiometer pot;
	DoubleSolenoid s0, s1;
    RobotDrive drive;
    Joystick controller;
    Joystick stick1;
    Joystick stick2;
    Joystick stick3;
    JoystickButton bButton;
    Compressor compressor;
    CameraServer server;

    public Robot() {
    	try{
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
    		
    		pot = new AnalogPotentiometer(0);
    		
    		//controller = new Joystick(0);
    		stick1 = new Joystick(0);
    		stick2 = new Joystick(1);
    		stick3 = new Joystick(2);
    		//bButton = new JoystickButton(controller, 2);
    		bButton = new JoystickButton(stick3, 2);
    		bButton.whenPressed(new Command() {
    			private boolean isOpen = true;
    			@Override
    			protected boolean isFinished() { return true; }
    			@Override
    			protected void interrupted() { }
    			@Override
    			protected void initialize() {
    				SmartDashboard.putBoolean("open state", isOpen);
    				if (Robot.this.isOperatorControl()) {
    					s0.set((isOpen = !isOpen) ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    				}
    			}
    			@Override
    			protected void execute() { }
    			@Override
    			protected void end() { }
    		});	
    		compressor = new Compressor();
    		compressor.setClosedLoopControl(true);
    		SmartDashboard.putBoolean("pcm", compressor.getClosedLoopControl());
    	}catch(Exception e){
    		SmartDashboard.putString("Startup Errors", e.getMessage());
    	}
        try{
        	server = CameraServer.getInstance();
            server.setQuality(50);
            server.startAutomaticCapture("cam0");
        }catch(Exception e){
        	SmartDashboard.putString("Startup Errors", "cam 0 failed to start");
        }
    }

    public void operatorControl() {
        Scheduler.getInstance().enable();
    	talon0.enableControl();
    	talon1.enableControl();
    	talon2.enableControl();
    	talon3.enableControl();
        while (isOperatorControl() && isEnabled()) {
        	SmartDashboard.putNumber("string pot", pot.get());
        	//double slowMod = controller.getRawButton(6) ? 1.5 : 1.0;
        	double slowMod = stick1.getRawButton(1) ? 1.5 : 1.0;
            //drive.tankDrive(-controller.getRawAxis(1) / slowMod, -controller.getRawAxis(5) / slowMod, true);
            //tower.set(controller.getRawAxis(3) - controller.getRawAxis(2));
            drive.tankDrive(-stick1.getRawAxis(1) / slowMod, -stick2.getRawAxis(1) / slowMod, true);
            tower.set(stick3.getRawAxis(1));
        	Scheduler.getInstance().run();
            Timer.delay(0.005);
            /*if(controller.getRawButton(3)){
            	s0.set(DoubleSolenoid.Value.kForward);
            }
            if(controller.getRawButton(2)){
            	s0.set(DoubleSolenoid.Value.kReverse);
            }*/
        }
    }
    
    public void autonomous(){
    	talon0.enableControl();
        talon1.enableControl();
        talon0.changeControlMode(CANTalon.ControlMode.PercentVbus);
        talon0.setSafetyEnabled(false);
        talon1.changeControlMode(CANTalon.ControlMode.PercentVbus);
        talon1.setSafetyEnabled(false);
        while(isAutonomous() && isEnabled()){
    		SmartDashboard.putNumber("Auton Encoder", talon1.getEncPosition());
        	if (Math.abs(talon1.getEncPosition()) < 1000.0 && Math.abs(talon1.getEncPosition()) < 1000.0){
        		talon0.set(0.5);
        		talon1.set(0.5);
        	}
        	SmartDashboard.putNumber("throttle", talon0.get());
        	SmartDashboard.putNumber("throttle1", talon1.get());
        }
        talon0.disableControl();
        talon1.disableControl();
    }
}
