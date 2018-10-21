package root;

import java.util.concurrent.Semaphore;
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
	
	public static void acquireSemaphore(Semaphore semaphore) {
		try {
			semaphore.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void releaseSemaphore(Semaphore semaphore) {
		semaphore.release();
	}
}
