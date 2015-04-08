package org.usfirst.frc.team103.robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Tower {
	private Talon motor;
	private AnalogPotentiometer pot;
	public PIDController pid;
	private DoubleSolenoid grabber;
	
	public Tower(){
		motor = new Talon(4);
		motor.setSafetyEnabled(true);
		motor.setExpiration(0.1);
		
		pot = new AnalogPotentiometer(0);
		
		pid = new PIDController(0.65, 0.96, 0.8, pot, motor, 0.005);
		pid.setInputRange(0.0, 1.0);
		pid.setOutputRange(-1.0, 1.0);
		pid.setPercentTolerance(1.0);
		
		grabber = new DoubleSolenoid(0,1);
	}
	
	public void setGrabber(boolean b){//true is open, false is closed
		if(b){
			grabber.set(DoubleSolenoid.Value.kForward);
		}else{
			grabber.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
	public void goTo(int level){
		pid.setSetpoint(0.1502*level - 0.051);
	}
	
	public void set(double speed){
		motor.set(speed);
	}
	
	public double getPos(){
		return pot.get();
	}
	
	public void putData(){
		SmartDashboard.putData("tower", motor);
    	SmartDashboard.putNumber("level", pid.getSetpoint());
    	SmartDashboard.putBoolean("on target", pid.onTarget());
    	SmartDashboard.putData("pid", pid);
    	SmartDashboard.putNumber("string pot", pot.get());
	}
}
