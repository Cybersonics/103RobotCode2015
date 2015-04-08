package org.usfirst.frc.team103.input;

public enum Mapping {
	LeftDrive(new Axis(1, 1, true)),
	RightDrive(new Axis(0, 1, true)),
	
	Tower(new Deadzone(2, 5, false)),
	
	Tail(new Axis(2, 2, false)),
	
	LowGear(new Button(1, 2)),
	HighGear(new Button(1, 3)),
	
	Grabber(new ToggleButton(2, 6)),
	
	SlowMod1(new Button(1, 1)),
	SlowMod2(new Button(0, 1)),
	
	Pos1(new Button(2, 1)),
	Pos2(new Button(2, 2)),
	Pos3(new Button(2, 3)),
	Pos4(new Button(2, 4));
	
	public final Input input;
	private Mapping(Input in) {
		input = in;
	}
	public <T> T getValue() {
		return (T) input.getValue();
	}
}
