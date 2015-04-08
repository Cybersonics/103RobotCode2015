package org.usfirst.frc.team103.robot;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.Talon;

public class Tail {
	private Talon motor;
	/*public Tail(){
		motor = new Talon(5);
		motor.setSafetyEnabled(true);
		motor.setExpiration(0.1);
	}
	
	public void set(double speed){
		motor.set(speed);
	}
	
	public void moveFor(double speed, long time){
		motor.set(speed);
		try{
			TimeUnit.SECONDS.sleep(time);
		}catch(InterruptedException e){}
	}*/
}
