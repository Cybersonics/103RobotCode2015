package org.usfirst.frc.team103.mode;

import org.usfirst.frc.team103.robot.Robot;
import org.usfirst.frc.team103.robot.Tail;

import edu.wpi.first.wpilibj.Talon;

public class Auton {
	public static Robot robot;
	public Auton(Robot r){
		robot = r;
	}
	public static void driveTo(int left, int right, double speed){
		robot.dt.goTo(left, right, speed);
	}
	public static void driveForward(int distance, double speed){
		robot.dt.goTo(distance, distance, speed);
	}
	public static void driveLeft(int howMuchDoYouWantToTurnLeft, double speed){
		robot.dt.goTo(-howMuchDoYouWantToTurnLeft, howMuchDoYouWantToTurnLeft, speed);
	}
	public static void driveRight(int howMuchDoYouWantToTurnRight, double speed){
		robot.dt.goTo(howMuchDoYouWantToTurnRight, -howMuchDoYouWantToTurnRight, speed);
	}
	public static void tailTo(double speed, long time){
		//robot.tail.moveFor(speed, time);
	}
	public static void setGrabber(boolean b){
		robot.tower.setGrabber(b);
	}
	public static void towerTo(int level){
		robot.tower.goTo(level);
	}
}
