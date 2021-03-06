package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class SpinController extends PeriodicThread {
	
	private AbstractWashingMachine machine;
	private int mode = SpinEvent.SPIN_OFF;
	private int spinDirection, counter;

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed));
		machine = mach;
		spinDirection = AbstractWashingMachine.SPIN_LEFT;
	}

	public void perform() {
		SpinEvent event = (SpinEvent) mailbox.tryFetch();
		
		// Lets handle our event, if such exists
		if(event != null) {
			mode = event.getMode();
			
			switch (event.getMode()) {
			case SpinEvent.SPIN_OFF: 
				System.out.println("Spin off");
				machine.setSpin(AbstractWashingMachine.SPIN_OFF);
				break;
				
			case SpinEvent.SPIN_SLOW:
				//System.out.println("Spin slow LEFT");
				spinDirection = AbstractWashingMachine.SPIN_LEFT;
				machine.setSpin(spinDirection);
				counter = 0;
				break;
				
			case SpinEvent.SPIN_FAST: 
				System.out.println("Spin fast");
				machine.setSpin(AbstractWashingMachine.SPIN_FAST);
				break;
			}
		}
		
		// Change direction every minute when slow spinning
		if(mode == SpinEvent.SPIN_SLOW && counter++ % 60 == 0) {
			
			if(spinDirection == AbstractWashingMachine.SPIN_LEFT) {
				//System.out.println("Spin slow RIGHT");
				spinDirection = AbstractWashingMachine.SPIN_RIGHT;
				machine.setSpin(spinDirection);
			} else {
				//System.out.println("Spin slow LEFT");
				spinDirection = AbstractWashingMachine.SPIN_LEFT;
				machine.setSpin(spinDirection);
			}
		}
	}
}
