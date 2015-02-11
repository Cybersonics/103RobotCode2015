
package org.usfirst.frc.team103.robot;


import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

public class Robot extends SampleRobot {
	CANTalon talon_left_back_cim, talon_left_front_cim, talon_right_front_cim, talon_right_back_cim;
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
    
    PIDController pid;

    public Robot() {
    	try{
    		talon_left_back_cim = new CANTalon(4); //left back sim -- encoder
    		talon_left_front_cim = new CANTalon(1); //left front
    		talon_right_front_cim = new CANTalon(2);// right front
    		talon_right_back_cim = new CANTalon(3); //right back cim -- encoder
    		
    		talon_left_back_cim.changeControlMode(ControlMode.PercentVbus);
    		talon_left_front_cim.changeControlMode(ControlMode.Follower);
    		talon_right_front_cim.changeControlMode(ControlMode.Follower);
    		talon_right_back_cim.changeControlMode(ControlMode.PercentVbus);
    		talon_left_front_cim.set(talon_left_back_cim.getDeviceID());
    		talon_right_front_cim.set(talon_right_back_cim.getDeviceID());
    		
    		s0 = new DoubleSolenoid(0,1);
    		s1 = new DoubleSolenoid(2,3);
    		
    		drive = new RobotDrive(talon_left_back_cim, talon_right_back_cim);
    		drive.setSafetyEnabled(true);
    		drive.setExpiration(0.1);
    		
    		tower = new Talon(0);
    		tower.setSafetyEnabled(true);
    		tower.setExpiration(0.1);
    		
    		pot = new AnalogPotentiometer(0);
    		
    		//controller = new Joystick(3);
    		stick1 = new Joystick(0);
    		stick2 = new Joystick(1);
    		stick3 = new Joystick(2);
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
    		
    		pid = new PIDController(1.0, 1.0, 1.0, pot, tower, 0.005);
    		pid.setInputRange(0.0, 1.0);
    		pid.setOutputRange(-1.0, 1.0);
    		pid.setPercentTolerance(1.0);
    		
    		compressor = new Compressor();
    		compressor.setClosedLoopControl(true);
    		SmartDashboard.putBoolean("pcm", compressor.getClosedLoopControl());

        	LiveWindow.setEnabled(true);
        	LiveWindow.addSensor("tower", "tower", tower);
        	LiveWindow.addSensor("pot", "pot", pot);
        	LiveWindow.addSensor("pid", "pid", pid);
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
    
    public void test() {
    	operatorControl();
    }

    public void operatorControl() {
        Scheduler.getInstance().enable();
    	talon_left_back_cim.enableControl();
    	talon_left_front_cim.enableControl();
    	talon_right_front_cim.enableControl();
    	talon_right_back_cim.enableControl();
        talon_left_back_cim.setPosition(0.00);
        talon_right_back_cim.setPosition(0.00);
        while (/*isOperatorControl() && */isEnabled()) {
        	if (Math.abs(stick3.getRawAxis(1)) > 0.05) {
        		if (pid.isEnable()) pid.disable();
        	} else {
        		boolean enable = false;
        		boolean pidSafeDelay = false;
            	/*if (stick3.getRawButton(6)) { pid.setSetpoint(0.222); enable = true; if(pot.get() > 0.4){pidSafeDelay = true;}}
            		if(pot.get() > 0.4 && pidSafeDelay == true) { pid.setSetpoint(0.065); pidSafeDelay = false;} */
            	if (stick3.getRawButton(6)) { pid.setSetpoint(0.244); enable = true; }
            	if (stick3.getRawButton(7)) { pid.setSetpoint(0.386); enable = true; }
            	if (stick3.getRawButton(11)) { pid.setSetpoint(0.520); enable = true; }
            	if (enable && !pid.onTarget()) pid.enable();
            	if (pid.onTarget() && pid.isEnable()) pid.disable(); 
        	}
    		SmartDashboard.putNumber("right encoder", -talon_right_back_cim.getEncPosition());
    		SmartDashboard.putNumber("left encoder", talon_left_back_cim.getEncPosition());
        	LiveWindow.run();
        	SmartDashboard.putData("tower", tower);
        	SmartDashboard.putNumber("level", pid.getSetpoint());
        	SmartDashboard.putBoolean("on target", pid.onTarget());
        	SmartDashboard.putData("pid", pid);
        	SmartDashboard.putNumber("string pot", pot.get());
        	if(stick1.getRawButton(3)){
        		s1.set(DoubleSolenoid.Value.kForward);
        	}else if(stick1.getRawButton(2)){
        		s1.set(DoubleSolenoid.Value.kReverse);
        	}
        	//double slowMod = controller.getRawButton(6) ? 1.5 : 1.0;
        	double slowMod1 = stick1.getRawButton(1) ? 1.5 : 1.0;
        	double slowMod2 = stick2.getRawButton(1) ? 1.5 : 1.0;
            //drive.tankDrive(-controller.getRawAxis(1) / slowMod, -controller.getRawAxis(5) / slowMod, true);
            //tower.set(controller.getRawAxis(3) - controller.getRawAxis(2));
            drive.tankDrive(-stick1.getRawAxis(1) / slowMod1 / slowMod2, -stick2.getRawAxis(1) / slowMod1 / slowMod2, true);
            double potv = pot.get();
            double stick3v = stick3.getRawAxis(1);
            stick3v = Math.abs(stick3v) > 0.05 ? stick3v : 0.0;
            double mult = Math.max(0.0, Math.min(1.0, stick3v < 0 ? 10.0 * potv - 0.5 : -40.0 * potv + 22.0));
            SmartDashboard.putNumber("mult", mult);
            tower.set(mult * stick3v);
            /*if(pot.get() > 0.06 && pot.get() < 0.5){
            	tower.set(stick3.getRawAxis(1));
            }else if(pot.get() < 0.06){
            	tower.set(stick3.getRawAxis(1) < 0 ? stick3.getRawAxis(1) * (pot.get()-0.05) : stick3.getRawAxis(1));
            }else{
            	tower.set(stick3.getRawAxis(1) > 0 ? 0 : stick3.getRawAxis(1));
            }*/
        	Scheduler.getInstance().run();
        	Timer.delay(0.005);
            /*if(controller.getRawButton(3)){
            	s0.set(DoubleSolenoid.Value.kForward);
            }
            if(controller.getRawButton(2)){
            	s0.set(DoubleSolenoid.Value.kReverse);
            }*/
        }
        pid.disable();
    }
    
    public void autonomous(){
    	talon_left_back_cim.enableControl();
        talon_left_front_cim.enableControl();
        talon_left_back_cim.changeControlMode(CANTalon.ControlMode.PercentVbus);
        talon_left_back_cim.setSafetyEnabled(false);
        talon_left_front_cim.changeControlMode(CANTalon.ControlMode.PercentVbus);
        talon_left_front_cim.setSafetyEnabled(false);
        talon_left_back_cim.setPosition(0.00);
        talon_right_back_cim.setPosition(0.00);
        
        while(isAutonomous() && isEnabled()){
//    		SmartDashboard.putNumber("Auton Encoder", talon_left_front_cim.getEncPosition());
//        	if (Math.abs(talon_left_front_cim.getEncPosition()) < 1000.0 && Math.abs(talon_left_front_cim.getEncPosition()) < 1000.0){
//        		talon_left_back_cim.set(0.5);
//        		talon_left_front_cim.set(0.5);
//        	}
//        	SmartDashboard.putNumber("throttle", talon_left_back_cim.get());
//        	SmartDashboard.putNumber("throttle1", talon_left_front_cim.get());
        	SmartDashboard.putNumber("left encoder", talon_left_back_cim.getEncPosition());
        	SmartDashboard.putNumber("right encoder ", (-1)*talon_right_back_cim.getEncPosition());
        	
        	/*
        	 * Determine distance per encoder tick -- interpret encoder to inches
        	 */
        	
        }
        talon_left_back_cim.disableControl();
        talon_left_front_cim.disableControl();
        talon_right_front_cim.disableControl();
        talon_right_back_cim.disableControl();
    }
}