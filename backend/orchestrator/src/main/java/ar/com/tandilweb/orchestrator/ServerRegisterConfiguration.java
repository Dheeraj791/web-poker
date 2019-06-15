package ar.com.tandilweb.orchestrator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ServerRegisterConfiguration {
	
	@Value("${act.orchestrator.corePoolSize}")
	private int corePoolSize;
	
	@Value("${act.orchestrator.maxPoolSize}")
	private int maxPoolSize;

	@Bean(value = "taskExecutor")
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setThreadNamePrefix("RoomRegisterService");
		executor.initialize();
		return executor;
	}

}
