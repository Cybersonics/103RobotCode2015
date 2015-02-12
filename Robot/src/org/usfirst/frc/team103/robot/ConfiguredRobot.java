package org.usfirst.frc.team103.robot;

import org.usfirst.frc.team103.input.Axis;
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
	
	/* MAPPING STRUCTURE:
	 * ex. mappings[0] = {1, 5}
	 * sets mapping 0 to stick 1, button/axis 5
	 * 
	 * MAPPINGS:
	 * 0 is left drive (axis)
	 * 1 is right drive (axis)
	 * 2 is tower (axis)
	 * 3 is low gear (button)
	 * 4 is high gear (button)
	 * 5 is grabber (button)
	 * 6 is slow mod (button)
	 * 7 is slow mod (button)
	 * 8 is pos 1 (button)
	 * 9 is pos 2 (button)
	 * 10 is pos 3 (button)
	 * 11 is pos 4 (button)
	 */
	
	public RobotDrive drive;
	
	public PIDController pid;
	
	public AnalogPotentiometer pot;
	
	public CANTalon[] talons;
	
	public Talon tower;
	
	public ConfiguredRobot(){
		Mapping.LeftDrive.<Double>getValue();
	}
	
	public enum Mapping {
		LeftDrive(new Axis(0, 0)),
		RightDrive(new Axis(1, 0)),
		Tower, LowGear, HighGear, Grabber, SlowMod1, SlowMod2, Pos1, Pos2, Pos3, Pos4;
		
		public final Input input;
		private Mapping(Input in) {
			input = in;
		}
		public <T> T getValue() {
			return (T) input.getValue();
		}
	}
}
