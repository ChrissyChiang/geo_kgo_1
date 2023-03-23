package gov.kcg.kgo.util;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 取得springBean.
 * 
 * @author Richard
 */
@Configuration
public class SpringUtil implements ApplicationContextAware {
	/** The ac. */
// extends ApplicationObjectSupport{
	private static ApplicationContext ac = null;

	/** The spring config tool. */
	private static SpringUtil springConfigTool = null;

	/**
	 * Inits the.
	 *
	 * @return the spring util
	 */
	public synchronized static SpringUtil init() {
		if (springConfigTool == null) {
			springConfigTool = new SpringUtil();
		}
		return springConfigTool;
	}

	/**
	 * Sets the application context.
	 *
	 * @param applicationContext the new application context
	 * @throws BeansException the beans exception
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ac = applicationContext;
	}

	/**
	 * Gets the bean.
	 *
	 * @param clazz the clazz
	 * @return the bean
	 */
	public synchronized static Object getBean(String beanName) {
		return ac.getBean(beanName);
	}

	/**
	 * Gets the bean.
	 *
	 * @param clazz the clazz
	 * @return the bean
	 */
	public synchronized static <T> T getBean(Class<T> clazz) {
		return ac.getBean(clazz);
	}

	/**
	 * Gets the bean.
	 *
	 * @param clazz the clazz
	 * @return the bean
	 */
	public synchronized static <T> T getBean(String beanName, Class<T> clazz) {
		return ac.getBean(beanName, clazz);
	}

	/**
	 * Gets the dao.
	 *
	 * @param <T>   the generic type
	 * @param clazz the clazz
	 * @return the dao
	 */
	public synchronized static <T> T getDao(Class<T> clazz) {
		return ac.getBean(clazz);
	}

	/**
	 * Gets the property.
	 *
	 * @param key the key
	 * @return the property
	 */
	public synchronized static String getProperty(String key) {
		return ac.getEnvironment().getProperty(key);
	}

	/**
	 * Gets the property.
	 *
	 * @param <T>   the generic type
	 * @param key   the key
	 * @param clazz the clazz
	 * @return the property
	 */
	public synchronized static <T> T getProperty(String key, Class<T> clazz) {
		return ac.getEnvironment().getProperty(key, clazz);
	}

	/**
	 * Gets the resource path.
	 *
	 * @param resource the resource
	 * @return the resource path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public synchronized static String getResourcePath(String resource) throws IOException {
		return ac.getResource(resource).getURL().getPath();
	}
}