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
import org.usfirst.frc103.Cyber103.commands.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class Lift extends Subsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    SpeedController liftController = RobotMap.liftLiftController;
    AnalogPotentiometer analogPot = RobotMap.liftAnalogPot;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    	setDefaultCommand(new LiftGeneric());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    
    public void tower() {
        double potv = Robot.lift.analogPot.get();
        double stick3v = Robot.oi.joystickOperator.getRawAxis(1);
        stick3v = Math.abs(stick3v) > 0.05 ? stick3v : 0.0;
        double mult = Math.max(0.0, Math.min(1.0, stick3v < 0 ? 10.0 * potv - 0.5 : -40.0 * potv + 22.0));
        Robot.lift.liftController.set(mult * stick3v);
    }
}

