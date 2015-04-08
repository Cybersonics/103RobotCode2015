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

public class Robot extends SampleRobot{
	
	public DriveTrain dt;
	public Tower tower;
	public Tail tail;
	
    //public RobotDrive drive;
    public Compressor compressor;
    public CameraServer server;
    
    public Teleop t;
	
    public SendableChooser a;
    Command autonCommand;
    
	public Robot(){
		try{
			dt = new DriveTrain();
			tower = new Tower();
    		
    		//drive = new RobotDrive(dt.leftBack, dt.rightBack);
    		//drive.setSafetyEnabled(true);
    		//drive.setExpiration(0.1);
    		
    		compressor = new Compressor();
    		compressor.setClosedLoopControl(true);
    		SmartDashboard.putBoolean("pcm", compressor.getClosedLoopControl());

    		t = new Teleop(this);
    		a = new SendableChooser();
    		a.addDefault("Do Nothing", new DoNothing());
    		a.addObject("Drive Forward", new DriveForward());
    		a.addObject("Forward Pick Up", new ForwardPU());
    		a.addObject("Tote Then Turn Right" , new ToteTR());
    		a.addObject("Recycling Can and Tote", new RC());
    		a.addObject("Tail (Unfinished)", new TailAuto());
    		
    		
        	//server = CameraServer.getInstance();
            //server.setQuality(50);
            //server.startAutomaticCapture("cam0");
            
            new Auton(this);
        }catch(Exception e){
        	SmartDashboard.putString("Startup Errors", e.getMessage());
        }
        //SmartDashboard.putNumber("right encoder", dt.rightBack.getEncPosition());
		//SmartDashboard.putNumber("left encoder", dt.leftBack.getEncPosition());
	}
	@Override
	public void operatorControl(){
		 t.runTeleop();
		 if (autonCommand != null) autonCommand.cancel();
	}
	@Override
	public void autonomous(){
		autonCommand = (Command) a.getSelected();
		autonCommand.start();
	}
	
	@Override
	public void test() {
	}
}
