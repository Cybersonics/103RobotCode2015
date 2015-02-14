package org.usfirst.frc.team103.input;

import org.usfirst.frc.team103.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ToggleButton extends Button {

	JoystickButton bButton;
	boolean toggle;
	
	public ToggleButton(int usbID, int rawInputID) {
		super(usbID, rawInputID);
		bButton = new JoystickButton(joystick, rawInputID);
		bButton.whenPressed(new Command() {
			@Override
			protected boolean isFinished() { return true; }
			@Override
			protected void interrupted() { }
			@Override
			protected void initialize() {
				SmartDashboard.putBoolean("open state", toggle);
				toggle = !toggle;
			}
			@Override
			protected void execute() { }
			@Override
			protected void end() { }
		});
	}

	@Override
	public Boolean getValue() {
		return toggle;
	}

}
