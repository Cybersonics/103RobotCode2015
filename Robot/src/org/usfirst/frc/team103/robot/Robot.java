package org.usfirst.frc.team103.robot;

import org.usfirst.frc.team103.mode.Auton;
import org.usfirst.frc.team103.mode.Teleop;
import org.usfirst.frc.team103.mode.auton.*;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends SampleRobot{
	
	public static DriveTrain dt;
	public static Tower tower;
	public static Tail tail;
	
    //public RobotDrive drive;
    public static Compressor compressor;
    public static CameraServer server;
    
    public static Teleop t;
	
    public static SendableChooser a;
    public static Command autonCommand;
    
	public Robot(){

        //SmartDashboard.putNumber("right encoder", dt.rightBack.getEncPosition());
		//SmartDashboard.putNumber("left encoder", dt.leftBack.getEncPosition());
	}
	@Override
	protected void robotInit(){
		try{
    		
			dt = new DriveTrain();
			tower = new Tower();
    		
    		//drive = new RobotDrive(dt.leftBack, dt.rightBack);
    		//drive.setSafetyEnabled(true);
    		//drive.setExpiration(0.1);
    		
    		compressor = new Compressor();
    		compressor.setClosedLoopControl(true);
    		SmartDashboard.putBoolean("pcm", compressor.getClosedLoopControl());
    	
    		a = new SendableChooser();
    		a.addDefault("Do Nothing", new DoNothing());
    		a.addObject("Drive Forward", new DriveForward());
    		a.addObject("Forward Pick Up", new ForwardPU());
    		a.addObject("Tote Then Turn Right" , new ToteTR());
    		a.addObject("Recycling Can and Tote", new RC());
    		a.addObject("Tail (Unfinished)", new TailAuto());
    		SmartDashboard.putData("Autonomous Chooser", a);
    		autonCommand = (Command) a.getSelected();
    		autonCommand.start();
    		
    		t = new Teleop(this);
    		
    		
        	//server = CameraServer.getInstance();
            //server.setQuality(50);
            //server.startAutomaticCapture("cam0");
            
            new Auton(this);
        }catch(Exception e){
        	SmartDashboard.putString("Startup Errors", e.getMessage());
        }
	}
	@Override
	public void operatorControl(){
		 t.runTeleop();
		 if (autonCommand != null) autonCommand.cancel();
	}
	@Override
	public void autonomous(){
		Scheduler.getInstance().run();
	}
	
	@Override
	public void test() {
	}
}
