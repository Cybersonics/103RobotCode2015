package org.usfirst.frc.team103.input;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Joystick;

public abstract class Input<T> {
	protected final Joystick joystick;
	public final int usb, input;
	protected Input(int usbID, int rawInputID){
		usb = usbID;
		input = rawInputID;
		joystick = getJoystick(usb);
	}
	
	public abstract T getValue();
	
	private static HashMap<Integer, Joystick> cache = new HashMap<>();
	
	public static Joystick getJoystick(int usbID){
		if(!cache.containsKey(usbID)){
			cache.put(usbID, new Joystick(usbID));
		}
		return cache.get(usbID);
	}
}
