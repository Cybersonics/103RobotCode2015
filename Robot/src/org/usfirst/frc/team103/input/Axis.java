package org.usfirst.frc.team103.input;

public class Axis extends Input<Double> {
	
	protected boolean inv = false;
	
	public Axis(int usbID, int axisID, boolean inv){
		super(usbID, axisID);
		this.inv = inv;
	}

	public double invert(double d){
		return d * (inv ? 1 : -1);
	}
	
	@Override
	public Double getValue() {
		return  joystick.getRawAxis(input);
	}
}
