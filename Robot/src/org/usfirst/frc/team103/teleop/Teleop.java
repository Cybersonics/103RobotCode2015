package org.usfirst.frc.team103.teleop;

import org.usfirst.frc.team103.robot.ConfiguredRobot;
import org.usfirst.frc.team103.robot.ConfiguredRobot.Mapping;

import edu.wpi.first.wpilibj.PIDController;

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
			robot.drive.tankDrive(-robot.getAxisData(0) / mod, -robot.getAxisData(1) / mod);
			
		}
		robot.pid.disable();
	}
	
	
	public void doTowerControls(){
		if (Math.abs(robot.getAxisData(Mapping.Tower)) > 0.05) {
    		if (robot.pid.isEnable()) robot.pid.disable();
    		double mult = Math.max(0.0, Math.min(1.0, robot.getAxisData(2)) < 0 ? 10.0 * robot.pot.get() - 0.5 : -40.0 * robot.pot.get() + 22.0);
    		robot.tower.set(mult * robot.getAxisData(2));
    	} else {
    		boolean enable = true;
    		if (robot.getButtonData(Mapping.Pos1)){
    			robot.pid.setSetpoint(0.065);
    		}else if (robot.getButtonData(Mapping.Pos2)) {
        		robot.pid.setSetpoint(0.244);
        	}else if (robot.getButtonData(Mapping.Pos3)) {
        		robot.pid.setSetpoint(0.386);
        	}else if (robot.getButtonData(Mapping.Pos4)) {
        		robot.pid.setSetpoint(0.520);
        	}else{
        		enable = false;
        	}
        	if (enable && !robot.pid.onTarget()) robot.pid.enable();
        	if (robot.pid.onTarget() && robot.pid.isEnable()) robot.pid.disable(); 
    	}
	}
}
