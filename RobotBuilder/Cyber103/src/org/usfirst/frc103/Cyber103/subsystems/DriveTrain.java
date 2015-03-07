// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc103.Cyber103.subsystems;

import org.usfirst.frc103.Cyber103.Robot;
import org.usfirst.frc103.Cyber103.RobotMap;
import org.usfirst.frc103.Cyber103.commands.TankDrive;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class DriveTrain extends PIDSubsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    SpeedController leftFrontController = RobotMap.driveTrainLeftFrontController;
    SpeedController leftBackController = RobotMap.driveTrainLeftBackController;
    SpeedController rightFrontController = RobotMap.driveTrainRightFrontController;
    SpeedController rightBackController = RobotMap.driveTrainRightBackController;
    RobotDrive robotDrive = RobotMap.driveTrainRobotDrive;
    Encoder driveEncoderLeft = RobotMap.driveTrainDriveEncoderLeft;
    Encoder driveEncoderRight = RobotMap.driveTrainDriveEncoderRight;
    DoubleSolenoid gearShift = RobotMap.driveTrainGearShift;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    // Initialize your subsystem here
    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
        super("DriveTrain", 1.0, 0.0, 0.0);
        setAbsoluteTolerance(0.2);
        getPIDController().setContinuous(false);
        LiveWindow.addActuator("DriveTrain", "PIDSubsystem Controller", getPIDController());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
        

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new TankDrive());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE
        return driveEncoderLeft.pidGet();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
        leftFrontController.pidWrite(output);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
    }
    
    public void tankDriveCommandInit(){
    	driveEncoderLeft.reset();
    	driveEncoderRight.reset();
    }
    
    public void tankDriveCommand(Joystick left, Joystick right){
    	//SmartDashboard.putNumber("EncoderLeftTele", driveEncoderLeft.getDistance());
    	robotDrive.tankDrive(left, right);
    	//SmartDashboard.putNumber("POV Thing", Robot.oi.joystickOperator.getAxis(AxisType.kX));
    	//SmartDashboard.putNumber("Joystick", Robot.oi.joystickOperator.getAxis(AxisType.kY));
    	//SmartDashboard.putNumber("EPOT", Robot.lift.analogPot.get());
    	if (Robot.oi.getJoystickOperator().getRawAxis(5) < 0.09 && Robot.oi.getJoystickOperator().getRawAxis(5) > -0.09) {
            RobotMap.liftLiftController.set(0);
         } else {
        	 double in = -(Robot.oi.getJoystickOperator().getRawAxis(5));
        	 in = Math.pow(in, 3) / Math.abs(in);
        	 double lower = 0.06, upper = 0.57;
        	 double buffer = 0.05;
        	 double a = 1/buffer;
        	 double mult = Math.max(0.0, Math.min(1.0, in) < 0 ? a * (RobotMap.liftAnalogPot.get() - lower) : -a * (RobotMap.liftAnalogPot.get() - upper));
             RobotMap.liftLiftController.set(in * mult);
         }
    }
    
    public void shiftGears(){
    	/*if(Robot.gearShift == true){
    		Robot.driveTrain.gearShift.set(DoubleSolenoid.Value.kForward);
    		Robot.gearShift = false;
    	}else{
    		Robot.driveTrain.gearShift.set(DoubleSolenoid.Value.kReverse);
    		Robot.gearShift = true;
    	}*/
    }
    public void forwardLiftAutoComInit(){
    	driveEncoderLeft.reset();
    	driveEncoderRight.reset();
    	driveEncoderLeft.setDistancePerPulse(1);
    	driveEncoderRight.setDistancePerPulse(1);
    }
    
    public void forwardLiftAutoCom(){
    	//SmartDashboard.putNumber("EncoderLeft", driveEncoderLeft.getDistance());
    	//SmartDashboard.putNumber("EPOT", Robot.lift.analogPot.get());
    	if(driveEncoderLeft.getDistance() <= 1300.50){
    		if(Robot.armGrabOpenBool == 0){
    			Robot.armGrabber.close();
    			Robot.armGrabOpenBool = 1;
        		try{
            		Thread.sleep(1000);
            	}catch(InterruptedException ex){
            			Thread.currentThread().interrupt();
            	}
    		}
    		Robot.lift.autoUpPoint();
    		robotDrive.drive(-0.4, 0);
    	} 
    	
    	if(driveEncoderLeft.getDistance() > 1300.00){
    		robotDrive.drive(0, 0);
    		
    		Robot.lift.autoDownPoint();

    		try{
    		Thread.sleep(1500);
    		}catch(InterruptedException ex){
    			Thread.currentThread().interrupt();
    		}
    		
   			if(Robot.armGrabOpenBool == 1){
    			Robot.armGrabber.open();
    			Robot.armGrabOpenBool = 3;
    		}
    		if(Robot.towerUpBool == 3 && Robot.armGrabOpenBool == 3){
    			Robot.doneForwardLiftAuto = true;
    		}
    	}
    	robotDrive.drive(-0.4, 0);
    }
    
    public boolean forwardLiftAutoComFinished(){
    	return Robot.doneForwardAuto;
    }
    
    public void fowardAutoComStop(){
    	robotDrive.drive(0, 0);
    }
    
    
    public void toteThenLeftAutoComInit(){
    	driveEncoderLeft.reset();
    	driveEncoderRight.reset();
    	driveEncoderLeft.setDistancePerPulse(1);
    	driveEncoderRight.setDistancePerPulse(1);
    }
    
    public void toteThenLeftAutoCom(){
    	//SmartDashboard.putNumber("EncoderRight", driveEncoderRight.getDistance());
    	//SmartDashboard.putNumber("EPOT", Robot.lift.analogPot.get());
    	if(driveEncoderRight.getDistance() <= 500.0){
    		 
    		if(Robot.armGrabOpenBool == 0){
    			Robot.armGrabber.close();
    			Robot.armGrabOpenBool = 1;
        		try{
            		Thread.sleep(1000);
            	}catch(InterruptedException ex){
            			Thread.currentThread().interrupt();
            	}
    		}
    		Robot.lift.autoUpPoint();
    		robotDrive.drive(-0.5, 0);
    	}	
    	
    	if(driveEncoderRight.getDistance() >= 500.0 && driveEncoderRight.getDistance() <= 700.0){
    		robotDrive.drive(0, 0);
    		rightFrontController.set(0.1);
    		rightBackController.set(0.1);
    	}
    	
    	if(driveEncoderRight.getDistance() >= 700.0 && driveEncoderRight.getDistance() <= 800.0){
    		rightFrontController.set(0);
    		rightBackController.set(0);
    		robotDrive.drive(-0.5, 0);
   		}
  
    	if(driveEncoderRight.getDistance() > 799.00){
    		robotDrive.drive(0, 0);
    		
    		Robot.lift.autoDownPoint();
   			//Robot.towerUpBool = 3;
    		try{
    		Thread.sleep(1500);
    		}catch(InterruptedException ex){
    			Thread.currentThread().interrupt();
    		}
    		
   			if(Robot.armGrabOpenBool == 1){
    			Robot.armGrabber.open();
    			Robot.armGrabOpenBool = 3;
    		}
    		if(Robot.towerUpBool == 3 && Robot.armGrabOpenBool == 3){
    			Robot.doneToteLeftAuto = true;
    		}
    	}
    }
    
    public boolean toteThenLeftAutoComFinished(){
    	return Robot.doneToteLeftAuto;
    }
    
    public void toteThenLeftAutoComStop(){
    	robotDrive.drive(0, 0);
    	Robot.lift.towerStop();
    	Robot.armGrabber.stop();
    }
    
    public void toteThenRightAutoComInit(){
    	driveEncoderLeft.reset();
    	driveEncoderRight.reset();
    	driveEncoderLeft.setDistancePerPulse(1);
    	driveEncoderRight.setDistancePerPulse(1);
    }
    
    public void toteThenRightAutoCom(){
    	int mod = -85;
    	int driveMod = 50;
    	//SmartDashboard.putNumber("EncoderLeft", driveEncoderLeft.getDistance());
    	//SmartDashboard.putNumber("EPOT", Robot.lift.analogPot.get());
    	if(driveEncoderLeft.getDistance() <= 571.44+driveMod){
    		 
    		if(Robot.armGrabOpenBool == 0){
    			Robot.armGrabber.close();
    			Robot.armGrabOpenBool = 1;
        		try{
            		Thread.sleep(1000);
            	}catch(InterruptedException ex){
            			Thread.currentThread().interrupt();
            	}
    		}
    		Robot.lift.autoUpPoint();
    		robotDrive.drive(-0.2, 0);
    	}
    	
    	if(driveEncoderLeft.getDistance() >= 571.44+driveMod && driveEncoderLeft.getDistance() <= 1081.44+mod+driveMod){
    		leftFrontController.set(0.2);
    		leftBackController.set(0.2);
    	}
    	
    	
    	if(driveEncoderLeft.getDistance() >= 1081.44+mod+driveMod && driveEncoderLeft.getDistance() <= 1724.31+driveMod){
    		leftFrontController.set(0.0);
    		leftBackController.set(0.0);
    		robotDrive.drive(-0.2, 0);
    	}
    	
    	if(driveEncoderLeft.getDistance() >= 1724.31+driveMod && driveEncoderLeft.getDistance() <= 2234.31+mod+driveMod){
    		robotDrive.drive(0, 0);
    		leftFrontController.set(0.2);
    		leftBackController.set(0.2);
    	}
    	
    	if(driveEncoderLeft.getDistance() >= 2234.31+mod+driveMod && driveEncoderLeft.getDistance() <= 2805.75){
    		leftFrontController.set(0);
    		leftBackController.set(0);
    		robotDrive.drive(-0.2, 0);
   		}
  
    	if(driveEncoderLeft.getDistance() > 2805.00){
    		robotDrive.drive(0, 0);
    		
    		Robot.lift.autoDownPoint();
   			//Robot.towerUpBool = 3;
    		try{
    		Thread.sleep(1500);
    		}catch(InterruptedException ex){
    			Thread.currentThread().interrupt();
    		}
    		
   			if(Robot.armGrabOpenBool == 1){
    			Robot.armGrabber.open();
    			Robot.armGrabOpenBool = 3;
    		}
    		if(Robot.towerUpBool == 3 && Robot.armGrabOpenBool == 3){
    			Robot.doneToteRightAuto = true;
    		}
    	}
    }
    
    public boolean toteThenRightAutoComFinished(){
    	return Robot.doneToteRightAuto;
    }
    
    public void toteThenRightAutoComStop(){
    	robotDrive.drive(0, 0);
    	Robot.lift.towerStop();
    	Robot.armGrabber.stop();
    }
    
    public void toteThenTForwardInitCom(){
    	driveEncoderRight.reset();
    	driveEncoderLeft.reset();
    	driveEncoderLeft.setDistancePerPulse(1);
    	driveEncoderRight.setDistancePerPulse(1);
    }
    
    public void toteThenTForwardCom(){
    	//SmartDashboard.putNumber("EncoderLeft", driveEncoderLeft.getDistance());
    	//SmartDashboard.putNumber("EPOT", Robot.lift.analogPot.get());
    	if(driveEncoderLeft.getDistance() <= 200.00){
    		if(Robot.armGrabOpenBool == 0){
    			Robot.armGrabber.close();
    			Robot.armGrabOpenBool = 1;
        		try{
            		Thread.sleep(1000);
            	}catch(InterruptedException ex){
            			Thread.currentThread().interrupt();
            	}
    		}
    		Robot.lift.autoUpPoint();
    		leftFrontController.set(0.3);
    		rightFrontController.set(0.3);
    		leftBackController.set(0.3);
    		rightBackController.set(0.3);
    	} 
    	if(driveEncoderLeft.getDistance() >= 200.00 && driveEncoderLeft.getDistance() <= 1500.50){	
    		leftFrontController.set(0);
    		rightFrontController.set(0);
    		leftBackController.set(0);
    		rightBackController.set(0);
    		robotDrive.drive(-0.4, 0);
    	}
    	
    	if(driveEncoderLeft.getDistance() > 1300.00){
    		robotDrive.drive(0, 0);
    		
    		Robot.lift.autoDownPoint();

    		try{
    		Thread.sleep(1500);
    		}catch(InterruptedException ex){
    			Thread.currentThread().interrupt();
    		}
    		
   			if(Robot.armGrabOpenBool == 1){
    			Robot.armGrabber.open();
    			Robot.armGrabOpenBool = 3;
    		}
    		if(Robot.towerUpBool == 3 && Robot.armGrabOpenBool == 3){
    			Robot.doneToteTForwardAuto = true;
    		}
    	}
    }
    
    public boolean toteThenTForwardComFinished(){
    	return Robot.doneToteTForwardAuto;
    }
    
    public void toteThenTForwardComStop(){
    	robotDrive.drive(0, 0);
    	Robot.armGrabber.stop();
    	Robot.lift.towerStop();
    }
    
    public void forwardAutoComInit(){
    	driveEncoderLeft.reset();
    	driveEncoderRight.reset();
    	driveEncoderLeft.setDistancePerPulse(1);
    	driveEncoderRight.setDistancePerPulse(1);
    }
    
    public void forwardAutoCom(){
    	if(driveEncoderLeft.getDistance() <= 500.56){
    		robotDrive.drive(-0.4, 0);
    	}
    	if(driveEncoderLeft.getDistance() > 500){
    		robotDrive.drive(0, 0);
    		Robot.doneForwardAuto = true;
    	}
    }
    
    public void forwardAutoComStop(){
    	robotDrive.drive(0, 0);
    }
    
    public void rcInitCom(){
    	driveEncoderLeft.reset();
    	driveEncoderRight.setDistancePerPulse(1);
    	driveEncoderRight.reset();
    	driveEncoderLeft.setDistancePerPulse(1);
    }
    
    public void rcCom(){
    	boolean thing = false;
    	//SmartDashboard.putNumber("EncoderLeft", driveEncoderLeft.getDistance());
    	//SmartDashboard.putNumber("EPOT", Robot.lift.analogPot.get());
    	if(driveEncoderLeft.getDistance() <= 142.86){
    		if(Robot.armGrabOpenBool == 0){
    			Robot.armGrabber.close();
    			Robot.armGrabOpenBool = 1;
    		}
    		if(Robot.armGrabOpenBool == 1){
	    		//if(Robot.towerUpBool == 0){
	    			Robot.lift.autoUpRCPoint();
	    			//Robot.towerUpBool = 1;
	    			//wait
	    		//}
	    			thing = true;
    		}
    		if(thing == true){
	    		robotDrive.drive(-0.4, 0);
    		}
    	}
    	if(driveEncoderLeft.getDistance() >= 142.86 && driveEncoderLeft.getDistance() <= 652.86){
    		if(Robot.towerUpBool == 1){
    			Robot.lift.autoDownPoint();
    			if(RobotMap.liftAnalogPot.get() < 400){
    				Robot.armGrabber.open();
    			}
    			
    			//wait
    			Robot.armGrabber.close();
    			Robot.lift.autoUpPoint();
    		}
    		leftFrontController.set(0.3);
    		rightFrontController.set(-0.3);
    		leftBackController.set(0.3);
    		rightBackController.set(-0.3);
    	}
    	
    	if(driveEncoderLeft.getDistance() >= 652.86 && driveEncoderLeft.getDistance() <= 1224.3){	
    		leftFrontController.set(0);
    		rightFrontController.set(0);
    		leftBackController.set(0);
    		rightBackController.set(0);
    		robotDrive.drive(-0.4, 0);
    	}
    	
    	if(driveEncoderLeft.getDistance() > 1224.00){
    		robotDrive.drive(0, 0);
    		
    		Robot.lift.autoDownPoint();

    		try{
    		Thread.sleep(1500);
    		}catch(InterruptedException ex){
    			Thread.currentThread().interrupt();
    		}
    		
   			if(Robot.armGrabOpenBool == 1){
    			Robot.armGrabber.open();
    			Robot.armGrabOpenBool = 3;
    		}
    		if(Robot.towerUpBool == 3 && Robot.armGrabOpenBool == 3){
    			Robot.doneRCAuto = true;
    		}
    	}
    }
    
    public void rcComStop(){
    	robotDrive.drive(0, 0);
    }
}
