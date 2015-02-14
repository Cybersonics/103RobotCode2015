package org.usfirst.frc.team103.robot;

import org.usfirst.frc.team103.input.Axis;
import org.usfirst.frc.team103.input.Button;
import org.usfirst.frc.team103.input.Input;
import org.usfirst.frc.team103.mode.Teleop;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ConfiguredRobot extends SampleRobot{
	
	/*public CameraServer server;
	
	public RobotDrive drive;
	
	public PIDController pid;
	
	public AnalogPotentiometer pot;
	
	public CANTalon[] talons;
	
	public Talon tower;*/
	
	public CANTalon talon_left_back_cim, talon_left_front_cim, talon_right_front_cim, talon_right_back_cim;
	public Talon tower;
	public AnalogPotentiometer pot;
	public DoubleSolenoid s0, s1;
    public RobotDrive drive;
    public Joystick controller;
    public Joystick stick1;
    public Joystick stick2;
    public Joystick stick3;
    public JoystickButton bButton;
    public Compressor compressor;
    public CameraServer server;
    
    public PIDController pid;
	
	public ConfiguredRobot(){
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
    				if (ConfiguredRobot.this.isOperatorControl()) {
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
	
	public enum Mapping {
		LeftDrive(new Axis(0, 0)),
		RightDrive(new Axis(1, 0)),
		Tower(new Axis(2, 0)),
		LowGear(new Button(0, 2)),
		HighGear(new Button(0, 1)),
		Grabber(new Button(2, 3)), 
		SlowMod1(new Button(0, 1)),
		SlowMod2(new Button(1, 1));
		//Pos1, Pos2, Pos3, Pos4;
		
		public final Input input;
		private Mapping(Input in) {
			input = in;
		}
		public <T> T getValue() {
			return (T) input.getValue();
		}
	}
	
	public void operatorControl(){
		new Teleop(this).runTeleop();
	}
}
