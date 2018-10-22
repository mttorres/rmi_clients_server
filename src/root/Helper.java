package root;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Helper {
	public static int NUMBER_OF_FILES = 3;
	
	private Helper() {}
	
	public static void sleepSeconds(int time) {
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void acquireWritePermission(Semaphore semaphore) {
		

		acquirePermission(semaphore, NUMBER_OF_FILES);
	}
	
	public static void acquireReadPermission(Semaphore semaphore) {
		acquirePermission(semaphore, 1);
	}
	
	public static void releaseWritePermission(Semaphore semaphore) {
		semaphore.release(NUMBER_OF_FILES);
	}
	
	public static void releaseReadPermission(Semaphore semaphore) {
		semaphore.release();
	}
	
	private static void acquirePermission(Semaphore semaphore, int permits) {
		try {
			semaphore.acquire(permits);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	
}
