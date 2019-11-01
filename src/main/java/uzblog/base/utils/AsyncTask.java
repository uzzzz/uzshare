package uzblog.base.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {

	@Bean
	public TaskExecutor getTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(1000);
		taskExecutor.setCorePoolSize(100);
		taskExecutor.setQueueCapacity(2000);
		return taskExecutor;
	}
}
