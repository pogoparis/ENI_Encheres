package fr.eni.encheres.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class EncheresSchedulingConfig implements SchedulingConfigurer{

	@Scheduled(fixedRate = 5000)
	public void verifierEncheresTerminees() {
		
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setTaskScheduler(TaskScheduler());
	}

	@Bean
	public TaskScheduler TaskScheduler() {
		 ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
	        taskScheduler.setPoolSize(5); // Définir la taille du pool de threads
	        return taskScheduler;
	}
	
}
