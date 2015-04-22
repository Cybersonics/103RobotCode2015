package org.usfirst.frc103.Cyber103.commands;

import org.usfirst.frc103.Cyber103.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RCGrabButtonOpen extends Command{

	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	protected void execute() {
		Robot.rcGrabber.open();
		
	}

	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	protected void end() {
		// TODO Auto-generated method stub
		
	}

	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
