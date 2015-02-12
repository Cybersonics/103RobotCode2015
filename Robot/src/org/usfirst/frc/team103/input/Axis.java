package org.usfirst.frc.team103.input;

public class Axis extends Input<Double> {
	
	public Axis(int usbID, int axisID){
		super(usbID, axisID);
	}

	@Override
	public Double getValue() {
		return joystick.getRawAxis(input);
	}
}
