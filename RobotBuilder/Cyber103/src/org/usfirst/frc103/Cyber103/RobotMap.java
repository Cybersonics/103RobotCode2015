// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc103.Cyber103;
    
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType; import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.util.Vector;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Encoder driveTrainQuadratureEncoder2;
    public static Encoder driveTrainQuadratureEncoder1;
    public static DoubleSolenoid driveTrainDoubleSolenoid1;
    public static SpeedController driveTrainSpeedController1;
    public static SpeedController driveTrainSpeedController2;
    public static SpeedController driveTrainSpeedController3;
    public static SpeedController driveTrainSpeedController4;
    public static RobotDrive driveTrainRobotDrive41;
    public static SpeedController liftSpeedController5;
    public static AnalogPotentiometer liftStringPot;
    public static DoubleSolenoid armGripperDoubleSolenoid1;
    public static DoubleSolenoid armGripperDoubleSolenoid2;
    public static Compressor compressorCompressor1;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainQuadratureEncoder2 = new Encoder(2, 3, false, EncodingType.k4X);
        LiveWindow.addSensor("Drive Train", "Quadrature Encoder 2", driveTrainQuadratureEncoder2);
        driveTrainQuadratureEncoder2.setDistancePerPulse(1.0);
        driveTrainQuadratureEncoder2.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainQuadratureEncoder1 = new Encoder(0, 1, false, EncodingType.k4X);
        LiveWindow.addSensor("Drive Train", "Quadrature Encoder 1", driveTrainQuadratureEncoder1);
        driveTrainQuadratureEncoder1.setDistancePerPulse(1.0);
        driveTrainQuadratureEncoder1.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainDoubleSolenoid1 = new DoubleSolenoid(0, 4, 5);      
        LiveWindow.addActuator("Drive Train", "Double Solenoid 1", driveTrainDoubleSolenoid1);
        
        driveTrainSpeedController1 = new Talon(1);
        LiveWindow.addActuator("Drive Train", "Speed Controller 1", (Talon) driveTrainSpeedController1);
        
        driveTrainSpeedController2 = new Talon(2);
        LiveWindow.addActuator("Drive Train", "Speed Controller 2", (Talon) driveTrainSpeedController2);
        
        driveTrainSpeedController3 = new Talon(3);
        LiveWindow.addActuator("Drive Train", "Speed Controller 3", (Talon) driveTrainSpeedController3);
        
        driveTrainSpeedController4 = new Talon(4);
        LiveWindow.addActuator("Drive Train", "Speed Controller 4", (Talon) driveTrainSpeedController4);
        
        driveTrainRobotDrive41 = new RobotDrive(driveTrainSpeedController1, driveTrainSpeedController2,
              driveTrainSpeedController3, driveTrainSpeedController4);
        
        driveTrainRobotDrive41.setSafetyEnabled(true);
        driveTrainRobotDrive41.setExpiration(0.1);
        driveTrainRobotDrive41.setSensitivity(0.5);
        driveTrainRobotDrive41.setMaxOutput(1.0);

        liftSpeedController5 = new Talon(0);
        LiveWindow.addActuator("Lift", "Speed Controller 5", (Talon) liftSpeedController5);
        
        liftStringPot = new AnalogPotentiometer(1, 1.0, 0.0);
        LiveWindow.addSensor("Lift", "String Pot", liftStringPot);
        
        armGripperDoubleSolenoid1 = new DoubleSolenoid(0, 0, 1);      
        LiveWindow.addActuator("Arm Gripper", "Double Solenoid 1", armGripperDoubleSolenoid1);
        
        armGripperDoubleSolenoid2 = new DoubleSolenoid(0, 2, 3);      
        LiveWindow.addActuator("Arm Gripper", "Double Solenoid 2", armGripperDoubleSolenoid2);
        
        compressorCompressor1 = new Compressor(0);
        
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}