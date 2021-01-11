
package com.employee.smacs.service;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.camel.spring.javaconfig.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

import com.employee.smacs.config.CacheConfig;
import com.employee.smacs.config.DBConfig;
import com.employee.smacs.service.config.AsyncConfig;
import com.employee.smacs.service.config.CamelConfig;
import com.employee.smacs.service.config.JPAConfig;
import com.employee.smacs.service.config.SecurityConfig;

@Configuration
@ComponentScan
public class Server extends CamelConfiguration {

	private final static Logger log = LoggerFactory.getLogger(Server.class);

	private static Main main;

	public static Server runner;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static final void main(String[] args) throws Exception {

		log.debug("main() : Start SMACS Service Server.");
		runner = new Server();
		runner.boot();
	}

	/**
	 * 
	 */
	private void boot() {
		try {
			AbstractApplicationContext appContext = 
				new AnnotationConfigApplicationContext(
					DBConfig.class,
					JPAConfig.class, 
					CamelConfig.class,
					SecurityConfig.class,
					
					AsyncConfig.class,
					CacheConfig.class //Enable or disable caching
					);

			main = new Main();
			main.setApplicationContext(appContext);
			// run until you terminate the JVM
			log.debug("Started Camel API.\n");
			main.run();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 */
	public static void shutdown() {

		if (main != null && main.isStarted() && main.isStoppingOrStopped() == false) {
			try {

				Thread stop = null;

				// stop this route using a thread that will stop
				// this route gracefully while we are still running
				if (stop == null) {
					stop = new Thread() {
						@Override
						public void run() {
							try {
								main.shutdown();
							} catch (Exception e) {
								// ignore
							}
						}
					};
				}
				// start the thread that stops this route
				stop.start();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}
