package com.almundo.callcenter;

import java.util.concurrent.Executor;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Permite la configuracion del proyecto spring boot habilitando la
 * configuracion asincronica para las operaciones de los servicios.
 * 
 * @author stilaguy
 *
 */
@Configuration
@EnableAsync
@EnableAutoConfiguration
public class CallCenterConfig extends AsyncConfigurerSupport {

	@Bean
	@Override
	public Executor getAsyncExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("ASYNC-");
		executor.initialize();
		return executor;
	}

}
