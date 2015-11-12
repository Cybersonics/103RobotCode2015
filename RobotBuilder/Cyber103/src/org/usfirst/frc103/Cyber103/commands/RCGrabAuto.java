package org.usfirst.frc103.Cyber103.commands;

import org.usfirst.frc103.Cyber103.Robot;
import org.usfirst.frc103.Cyber103.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class RCGrabAuto extends Command{
	
	private enum State{
		WAIT, GRAB_BIN, LIFT_BIN, CLOSE_RCGRAB, RELEASE_BIN, LOWER_ARMS, DONE
	}
	
	private static final double LIFT_POS = 0.586, DOWN_POS = 0.100;
	
	private State state;
	private State nextState;
	private Timer waitTimer = new Timer();
	private double waitDuration;
	private boolean done;
	
	private void addWait(double duration, State next){
		state = State.WAIT;
		nextState = next;
		waitDuration = duration;
		waitTimer.reset();
		waitTimer.start();
	}
	

	@Override
	protected void initialize() {
		state = State.GRAB_BIN;
		done = false;
		
	}

	@Override
	protected void execute() {
		switch(state){
		case WAIT:
			if(waitTimer.hasPeriodPassed(waitDuration)) state = nextState;
			break;
		case GRAB_BIN:
			Robot.armGrabber.close();
			addWait(0.4, State.LIFT_BIN);
			break;
		case LIFT_BIN:
			double pos = RobotMap.liftAnalogPot.get();
			if(pos < LIFT_POS){
				RobotMap.liftLiftController.set(0.5);
			}else{
				RobotMap.liftLiftController.set(0.0);
				addWait(0.4, State.CLOSE_RCGRAB);
			}
			break;
		case CLOSE_RCGRAB:
			Robot.rcGrabber.close();
			addWait(0.4, State.RELEASE_BIN);
			break;
		case RELEASE_BIN:
			Robot.armGrabber.open();
			addWait(0.4, State.LOWER_ARMS);
			break;
		case LOWER_ARMS:
			double pos1 = RobotMap.liftAnalogPot.get();
			if(pos1 > DOWN_POS){
				RobotMap.liftLiftController.set(-0.5);
			}else{
				RobotMap.liftLiftController.set(0.0);
				addWait(0.0, State.DONE);
			}
			break;
		case DONE:
			done = true;
			break;
		}
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return done;
	}

	@Override
	protected void end() {
		Robot.rcGrabber.stop();
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
