import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddShutdownHookSample {
	private static Logger logger = LoggerFactory.getLogger(AddShutdownHookSample.class);


	public static void main(String[] args) {
		//用以下代码注册在程序结束的时候要执行的任务
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.debug("在虚拟机关闭前，要运行一下这段代码");
			}
		});
		logger.debug("注册了关闭挂钩代码");
		
		logger.debug("程序最后一句话");
		System.exit(0);
		logger.debug("这句话因为虚拟机在System.exit已经进入关闭进程，所以显示不出来");
	}
}