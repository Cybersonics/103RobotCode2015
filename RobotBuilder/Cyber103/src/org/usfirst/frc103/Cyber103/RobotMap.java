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
    public static SpeedController driveTrainLeftFrontController;
    public static SpeedController driveTrainLeftBackController;
    public static SpeedController driveTrainRightFrontController;
    public static SpeedController driveTrainRightBackController;
    public static RobotDrive driveTrainRobotDrive;
    public static Encoder driveTrainDriveEncoderLeft;
    public static Encoder driveTrainDriveEncoderRight;
    public static DoubleSolenoid driveTrainGearShift;
    public static DoubleSolenoid armGrabberArmSolenoid;
    public static SpeedController liftLiftController;
    public static AnalogPotentiometer liftAnalogPot;
    public static PIDController liftPID;
    public static Compressor compressorSystemCompressorSubsystem;
    public static SpeedController tailTailController;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public static DoubleSolenoid rcSolenoid;

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainLeftFrontController = new Talon(2);
        LiveWindow.addActuator("DriveTrain", "LeftFrontController", (Talon) driveTrainLeftFrontController);
        
        driveTrainLeftBackController = new Talon(3);
        LiveWindow.addActuator("DriveTrain", "LeftBackController", (Talon) driveTrainLeftBackController);
        
        driveTrainRightFrontController = new Talon(0);
        LiveWindow.addActuator("DriveTrain", "RightFrontController", (Talon) driveTrainRightFrontController);
        
        driveTrainRightBackController = new Talon(1);
        LiveWindow.addActuator("DriveTrain", "RightBackController", (Talon) driveTrainRightBackController);
        
        driveTrainRobotDrive = new RobotDrive(driveTrainLeftFrontController, driveTrainLeftBackController,
              driveTrainRightFrontController, driveTrainRightBackController);
        
        driveTrainRobotDrive.setSafetyEnabled(true);
        driveTrainRobotDrive.setExpiration(0.1);
        driveTrainRobotDrive.setSensitivity(0.5);
        driveTrainRobotDrive.setMaxOutput(1.0);
        driveTrainRobotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        driveTrainRobotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        driveTrainRobotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveTrainRobotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        driveTrainDriveEncoderLeft = new Encoder(2, 3, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveTrain", "DriveEncoderLeft", driveTrainDriveEncoderLeft);
        driveTrainDriveEncoderLeft.setDistancePerPulse(1.0);
        driveTrainDriveEncoderLeft.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainDriveEncoderRight = new Encoder(0, 1, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveTrain", "DriveEncoderRight", driveTrainDriveEncoderRight);
        driveTrainDriveEncoderRight.setDistancePerPulse(1.0);
        driveTrainDriveEncoderRight.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainGearShift = new DoubleSolenoid(0, 2, 3);      
        LiveWindow.addActuator("DriveTrain", "GearShift", driveTrainGearShift);
        
        armGrabberArmSolenoid = new DoubleSolenoid(0, 0, 1);      
        LiveWindow.addActuator("ArmGrabber", "ArmSolenoid", armGrabberArmSolenoid);
        
        liftLiftController = new Talon(4);
        LiveWindow.addActuator("Lift", "LiftController", (Talon) liftLiftController);
        
        liftAnalogPot = new AnalogPotentiometer(0, 1.0, 0.0);
        LiveWindow.addSensor("Lift", "AnalogPot", liftAnalogPot);
        
        liftPID = new PIDController(0.65, 0.96, 0.8, 1.0, liftAnalogPot, liftLiftController, 0.005);
        LiveWindow.addActuator("Lift", "PID", liftPID);
        liftPID.setContinuous(false); liftPID.setAbsoluteTolerance(0.2); 
        liftPID.setOutputRange(-1.0, 1.0);        

        compressorSystemCompressorSubsystem = new Compressor(0);
        
        
        tailTailController = new Talon(5);
        LiveWindow.addActuator("Tail", "TailController", (Talon) tailTailController);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        rcSolenoid = new DoubleSolenoid(0, 2);
    }
}
