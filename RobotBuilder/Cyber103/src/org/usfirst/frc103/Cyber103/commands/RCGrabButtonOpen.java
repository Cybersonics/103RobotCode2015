package org.usfirst.frc103.Cyber103.commands;

import org.usfirst.frc103.Cyber103.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RCGrabButtonOpen extends Command{
	
	public static boolean done = true;
	
	public RCGrabButtonOpen(){
		requires(Robot.rcGrabber);
	}

	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	protected void execute() {
		Robot.rcGrabber.open();
		done = false;
	}

	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return done;
	}

	protected void end() {
		// TODO Auto-generated method stub
		Robot.rcGrabber.stop();
		
	}

	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
		
	}

}
