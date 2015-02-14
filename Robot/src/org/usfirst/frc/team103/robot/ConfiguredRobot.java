package org.usfirst.frc.team103.robot;

import org.usfirst.frc.team103.input.Axis;
import org.usfirst.frc.team103.input.Button;
import org.usfirst.frc.team103.input.Input;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;

public class ConfiguredRobot extends SampleRobot{
	
	public CameraServer server;
	
	public RobotDrive drive;
	
	public PIDController pid;
	
	public AnalogPotentiometer pot;
	
	public CANTalon[] talons;
	
	public Talon tower;
	
	public ConfiguredRobot(){
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
}
