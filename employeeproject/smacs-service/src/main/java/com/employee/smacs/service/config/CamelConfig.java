
package com.employee.smacs.service.config;

import java.io.IOException;

import org.apache.camel.CamelContext;
import org.apache.camel.component.jasypt.JasyptPropertiesParser;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.spring31.properties.EncryptablePropertyPlaceholderConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@ComponentScan( value = { "com.employee.smacs.service.routes", 
	"com.employee.smacs.service.auth",
	"com.employee.smacs.service.processor", 
	"com.employee.smacs.service.model",
	"com.employee.smacs.data",
	"com.employee.smacs.dao"}, lazyInit=true)
@EnableAsync
@EnableJpaRepositories(basePackages = "com.employee.smacs.repository")
public class CamelConfig extends CamelConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(CamelConfig.class);

	private static String defaultPassword = "APP_ENCRYPTION_PASSWORD";

	/**
	 * @param config
	 * @return
	 */
	@Bean(name = "configurationEncryptor")
	public static StandardPBEStringEncryptor configurationEncryptor(EnvironmentStringPBEConfig config) {
		StandardPBEStringEncryptor configurationEncryptor = new StandardPBEStringEncryptor();
		configurationEncryptor.setConfig(config);
		return configurationEncryptor;
	}
	
	/**
	 * @return
	 */
	@Bean(name = "environmentVariablesConfiguration")
	public static EnvironmentStringPBEConfig environmentVariablesConfiguration() {
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setPassword(defaultPassword);
		return config;
	}

	/**
	 * @param configurationEncryptor
	 * @return
	 * @throws IOException
	 */
	@Bean
	public static EncryptablePropertyPlaceholderConfigurer propertySourcesPlaceholderConfigurer(
			StandardPBEStringEncryptor configurationEncryptor) throws IOException {
		EncryptablePropertyPlaceholderConfigurer propertySource = new EncryptablePropertyPlaceholderConfigurer(
				configurationEncryptor);
		
		logger.debug("CONFIG_FILE={}", System.getProperty("CONFIG_FILE"));
		FileSystemResource resouce = new FileSystemResource(System.getProperty("CONFIG_FILE"));
		propertySource.setLocation(resouce);
		return propertySource;
	}

	@Value("${logging.trace.enabled: true}")
	private Boolean tracingEnabled;

	@Bean
	public ObjectMapper objectMapper() {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.camel.spring.javaconfig.CamelConfiguration#setupCamelContext(
	 * org.apache.camel.CamelContext)
	 */
	@Override
	protected void setupCamelContext(CamelContext context) throws Exception {

		logger.debug("setupCamelContext() : Start.");
		// see if trace logging is turned on
		if (tracingEnabled) {
			context.setTracing(true);
		}
		
		JasyptPropertiesParser jasypt = new JasyptPropertiesParser();
		jasypt.setPassword(defaultPassword);

		PropertiesComponent pc = new PropertiesComponent();
		pc.setPropertiesParser(jasypt);
		pc.setLocation("file://" + System.getProperty("CONFIG_FILE"));
		logger.debug("PropertiesComponent():{}",pc);
		context.addComponent("properties", pc);
		logger.debug("context(): {}",context);
		super.setupCamelContext(context);
	}
}
