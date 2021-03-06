package todo;

public class Time extends Thread {
	
	private SharedData data;

	public Time(SharedData data) {
		this.data = data;
	}
	
	public void run() {
		while (true) {
			long t = System.currentTimeMillis();
			data.addSecond();
			t += 1000;
			long diff = t - System.currentTimeMillis();
			try {
				Thread.sleep(diff);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
