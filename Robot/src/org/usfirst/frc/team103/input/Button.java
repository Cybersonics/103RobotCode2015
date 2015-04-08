package org.usfirst.frc.team103.input;

public class Button extends Input<Boolean> {

	public Button(int usbID, int buttonID) {
		super(usbID, buttonID);
	}

	@Override
	public Boolean getValue() {
		return joystick.getRawButton(input);
	}
}
