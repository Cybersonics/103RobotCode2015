package org.usfirst.frc.team103.mode;

import org.usfirst.frc.team103.robot.ConfiguredRobot;
import org.usfirst.frc.team103.robot.ConfiguredRobot.Mapping;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	
	public ConfiguredRobot robot;
	
	public double slowMod1 = 1.5, slowMod2 = 1.5;
	
	public Teleop(ConfiguredRobot r){
		robot = r;
	}
	
	
	public void runTeleop(){
		while(robot.isOperatorControl() && robot.isEnabled()){
			doTowerControls();
			double mod = (Mapping.SlowMod1.<Boolean>getValue() ? slowMod1 : 0.0) * (Mapping.SlowMod2.<Boolean>getValue() ? slowMod2 : 0.0);
			robot.drive.tankDrive(-Mapping.LeftDrive.<Double>getValue() / mod, -Mapping.RightDrive.<Double>getValue() / mod);
			if(Mapping.Grabber.<Boolean>getValue()){
				robot.s0.set(DoubleSolenoid.Value.kForward);
			}else{
				robot.s0.set(DoubleSolenoid.Value.kReverse);
			}
			
			
			SmartDashboard.putNumber("right encoder", -robot.talon_right_back_cim.getEncPosition());
    		SmartDashboard.putNumber("left encoder", robot.talon_left_back_cim.getEncPosition());
        	LiveWindow.run();
        	SmartDashboard.putData("tower", robot.tower);
        	SmartDashboard.putNumber("level", robot.pid.getSetpoint());
        	SmartDashboard.putBoolean("on target", robot.pid.onTarget());
        	SmartDashboard.putData("pid", robot.pid);
        	SmartDashboard.putNumber("string pot", robot.pot.get());
			Scheduler.getInstance().run();
			Timer.delay(0.005);
		}
		robot.pid.disable();
	}
	
	
	public void doTowerControls(){
		if (Math.abs(Mapping.Tower.<Double>getValue()) > 0.05) {
    		if (robot.pid.isEnable()) robot.pid.disable();
    		double mult = Math.max(0.0, Math.min(1.0, Mapping.Tower.<Double>getValue()) < 0 ? 10.0 * robot.pot.get() - 0.5 : -40.0 * robot.pot.get() + 22.0);
    		robot.tower.set(mult * Mapping.Tower.<Double>getValue());
    	} else {
    		boolean enable = true;
    		if (Mapping.Pos1.<Boolean>getValue()){
    			robot.pid.setSetpoint(0.065);
    		}else if (Mapping.Pos2.<Boolean>getValue()) {
        		robot.pid.setSetpoint(0.244);
        	}else if (Mapping.Pos3.<Boolean>getValue()) {
        		robot.pid.setSetpoint(0.386);
        	}else if (Mapping.Pos4.<Boolean>getValue()) {
        		robot.pid.setSetpoint(0.520);
        	}else{
        		enable = false;
        	}
        	if (enable && !robot.pid.onTarget()) robot.pid.enable();
        	if (robot.pid.onTarget() && robot.pid.isEnable()) robot.pid.disable(); 
    	}
	}
}
