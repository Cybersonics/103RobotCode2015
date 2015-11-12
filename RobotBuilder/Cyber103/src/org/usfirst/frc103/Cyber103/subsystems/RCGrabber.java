package org.usfirst.frc103.Cyber103.subsystems;

import org.usfirst.frc103.Cyber103.Robot;
import org.usfirst.frc103.Cyber103.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RCGrabber extends Subsystem{
	DoubleSolenoid rcSolenoid = RobotMap.rcSolenoid;
	
	public void initDefaultCommand(){
		
	}
	
	public void open(){
		Robot.rcGrabber.rcSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void close(){
		Robot.rcGrabber.rcSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
    public void stop(){
    	Robot.rcGrabber.rcSolenoid.set(DoubleSolenoid.Value.kOff);
    }
}
