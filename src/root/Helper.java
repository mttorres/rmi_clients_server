package root;

import java.util.concurrent.TimeUnit;

public class Helper {
	private Helper() {}
	
	public static void sleepSeconds(int time) {
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
