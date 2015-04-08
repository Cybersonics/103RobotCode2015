package org.usfirst.frc.team103.input;

public class Deadzone extends Axis {
	
	private static final double deadzone = 0.05;

	public Deadzone(int usbID, int axisID, boolean inv) {
		super(usbID, axisID, inv);
	}
	
	@Override
	public Double getValue() {
		return invert(Math.abs(joystick.getRawAxis(input)) > deadzone ? (joystick.getRawAxis(input) - deadzone) : 0.0);
	}
}
