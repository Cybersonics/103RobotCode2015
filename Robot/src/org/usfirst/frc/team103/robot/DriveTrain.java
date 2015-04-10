package org.usfirst.frc.team103.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {
	public Talon leftBack, leftFront, rightFront, rightBack;
	private DoubleSolenoid gear;
	public Encoder leftEnc, rightEnc;
	private static double encPerIn = 39.75;
	
	public DriveTrain(){
		try{
			leftBack = new Talon(3); //left back sim -- encoder
    		leftFront = new Talon(2); //left front
    		rightFront = new Talon(0);// right front
    		rightBack = new Talon(1); //right back cim -- encoder
    		
    		leftEnc = new Encoder(2, 3, false);
    		rightEnc = new Encoder(0, 1, true);
    		
    		gear = new DoubleSolenoid(2,3);
		}catch(Exception e){}
	}
	
	public void disable(){
		leftBack.disable();
		rightBack.disable();
		rightFront.disable();
		leftFront.disable();
	}
	
	public void setLeft(double speed){
		leftBack.set(speed);
		leftFront.set(speed);
	}
	
	public void setRight(double speed){
		rightBack.set(-speed);
		leftFront.set(-speed);
	}
	
	public void setGear(boolean b){//true is high, false is low
		if(b){
			gear.set(DoubleSolenoid.Value.kForward);
		}else{
			gear.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
	public void putData(){
		SmartDashboard.putNumber("left encoder", leftEnc.get());
		SmartDashboard.putNumber("right encoder", rightEnc.get());
	}
	
	public int leftEnc(){
		return leftEnc.get();
	}
	
	public int rightEnc(){
		return rightEnc.get();
	}
	
	public void goTo(int left, int right, double speed){// go to in encoder ticks
		while(Math.abs(leftEnc.get() - left) > 1 && Math.abs(rightEnc.get() - right) > 1){
			if(Math.abs(leftEnc.get() - left) <= 1){
			}else if(leftEnc.get() > left){
				setLeft(speed);
			}else{
				setLeft(-speed);
			}
			if(Math.abs(rightEnc.get() - right) <= 1){
			}else if(rightEnc.get() > left){
				setRight(speed);
			}else{
				setRight(-speed);
			}
		}
		setRight(0);
		setLeft(0);
	}
	
	public void goToIn(double left, double right, double speed){// go to in inches
		goTo((int) (left*encPerIn), (int) (right*encPerIn), speed);
	}
}
