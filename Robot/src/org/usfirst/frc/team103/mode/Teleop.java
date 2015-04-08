package org.usfirst.frc.team103.mode;

import org.usfirst.frc.team103.input.Mapping;
import org.usfirst.frc.team103.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Teleop {
	
	public Robot robot;
	
	public double slowMod1 = 1.5, slowMod2 = 1.5;
	
	public double lower = 0.05, upper = 0.7;
	public double buffer = 0.05;
	
	private double a;
	
	public Teleop(Robot r){
		robot = r;
		a = 1/buffer;
	}
	
	
	public void runTeleop(){
		
		while(robot.isOperatorControl() && robot.isEnabled()){
			doTower();
			doTail();
			
			move();
			
			robot.dt.putData();
			robot.tower.putData();
			
			Scheduler.getInstance().run();
			Timer.delay(0.005);
		}
		robot.tower.pid.disable();
	}
	
	private double curve(double in){
		return Math.pow(in, 3)/Math.abs(in);
	}
	
	private void move(){
		if(Mapping.SlowMod1.<Boolean>getValue() && Mapping.SlowMod2.<Boolean>getValue()){
			robot.dt.setLeft(-curve(Mapping.LeftDrive.<Double>getValue()));
			robot.dt.setRight(-curve(Mapping.RightDrive.<Double>getValue()));
			//robot.drive.tankDrive(Math.pow(-Mapping.LeftDrive.<Double>getValue(), 3)/Math.abs(Mapping.LeftDrive.<Double>getValue());, Math.pow(-Mapping.RightDrive.<Double>getValue(), 3)/Math.abs(Mapping.RightDrive.<Double>getValue()));
		}else{
			robot.dt.setRight(-Mapping.RightDrive.<Double>getValue());
			robot.dt.setLeft(-Mapping.LeftDrive.<Double>getValue());
			//robot.drive.tankDrive(-Mapping.LeftDrive.<Double>getValue(), -Mapping.RightDrive.<Double>getValue());
		}
		
		if(Mapping.HighGear.<Boolean>getValue()){
			robot.dt.setGear(true);
		}else if(Mapping.LowGear.<Boolean>getValue()){
			robot.dt.setGear(false);
		}
	}
	
	private void doTower(){
		if (Math.abs(Mapping.Tower.<Double>getValue()) > 0.05) {
    		if (robot.tower.pid.isEnable()) robot.tower.pid.disable();
    		double in = Math.pow(Mapping.Tower.<Double>getValue(), 3) / Math.abs(Mapping.Tower.<Double>getValue());
    		double mult = Math.max(0.0, Math.min(1.0, in) < 0 ? a * (robot.tower.getPos() - lower) : -a * (robot.tower.getPos() - upper));
    		robot.tower.set(0.15 * mult * in);
    	} else {
    		boolean enable = true;
    		if (Mapping.Pos1.<Boolean>getValue()){
    			robot.tower.goTo(1);
    		}else if (Mapping.Pos2.<Boolean>getValue()) {
        		robot.tower.goTo(2);
        	}else if (Mapping.Pos3.<Boolean>getValue()) {
        		robot.tower.goTo(3);
        	}else if (Mapping.Pos4.<Boolean>getValue()) {
        		robot.tower.goTo(4);
        	}else{
        		enable = false;
        	}
        	if (enable && !robot.tower.pid.onTarget()) robot.tower.pid.enable();
        	if (robot.tower.pid.onTarget() && robot.tower.pid.isEnable()) robot.tower.pid.disable(); 
    	}
		robot.tower.setGrabber(Mapping.Grabber.<Boolean>getValue());
	}
	
	private void doTail(){
		//robot.tail.set(curve(Mapping.Tail.<Double>getValue()));
	}
}
