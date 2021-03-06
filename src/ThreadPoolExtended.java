import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolExtended {
	private static Logger logger = LoggerFactory.getLogger(Task.class);

	public static void main(String[] args) {
		// 1. 创建一个只允许3个 worker【工作线程】的线程池
		final ExecutorService tPool = Executors.newFixedThreadPool(3);

		// //////////////////////2. 注册关闭线程池///////////////////
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.debug("程序结束，线程池开始关闭");

				// 注意: 线程池的"安详"的关闭，要以下两个步骤
				tPool.shutdown();// 停止接受新任务，但是老任务不停止
				try {
					tPool.awaitTermination(1, TimeUnit.MINUTES);
				} catch (InterruptedException e) {
				}
			}
		});
		// ///////////////////////////////////////////////////

		// 3. 把10个任务发给着3个worker去做
		for (int i = 0; i < 10; i++) {
			tPool.submit(new Task(i));
		}

		// 4. 用户输入exit的时候，程序退出
		Scanner sc = new Scanner(System.in);
		while (true) {
			if (sc.next().equals("exit")) {
				System.exit(0);
			}
		}
	}
}

class Task implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(Task.class);
	private int id;

	public Task(int id) {
		this.id = id;
	}

	public void run() {
		logger.debug("线程:" + Thread.currentThread().getName() + " 在处理任务:" + id);
	}
}
