package gov.kcg.kgo.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpSessionListenerConfig {
	/**
	 * Self4j Logger
	 */
	private static transient Logger LOGGER = LoggerFactory.getLogger(HttpSessionListenerConfig.class);

	private static int count = 0;// 記錄session的數量

	@Bean
	public HttpSessionListener httpSessionListener() {
		return new HttpSessionListener() {
			/**
			 * associate a PythonInterpreter to each newly created session
			 */
			@Override
			public void sessionCreated(HttpSessionEvent event) {
				count++;
				event.getSession().getServletContext().setAttribute("count", count);
				LOGGER.info("======= [" + event.getSession().getId() + "]session count : " + count + " ==========");
			}

			/**
			 * clean the PythonInterpreter associated to the destroyed session
			 */
			@Override
			public void sessionDestroyed(HttpSessionEvent event) {
				count--;
				event.getSession().getServletContext().setAttribute("count", count);
				LOGGER.info("======= [" + event.getSession().getId() + "]session count : " + count + " ==========");
			}
		};
	}
}