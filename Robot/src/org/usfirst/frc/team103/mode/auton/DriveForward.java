package org.usfirst.frc.team103.mode.auton;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team103.robot.Robot;

public class DriveForward extends Command{
	private Encoder leftEnc;
	private Encoder rightEnc;
	@Override
	protected void initialize() {
		leftEnc = Robot.dt.leftEnc;
		rightEnc = Robot.dt.rightEnc;
		leftEnc.reset();
		rightEnc.reset();
		leftEnc.setDistancePerPulse(1);
		rightEnc.setDistancePerPulse(1);
		SmartDashboard.putString("plz", "plz");
		
	}

	@Override
	protected void execute() {
    		//Robot.dt.goTo(501, 501, 0.4);
    		Robot.dt.leftBack.set(-0.4);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		leftEnc.reset();
		rightEnc.reset();
		Robot.dt.goTo(0,0,0);
	}

}
