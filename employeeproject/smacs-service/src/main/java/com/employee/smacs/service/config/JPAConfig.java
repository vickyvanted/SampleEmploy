package com.employee.smacs.service.config;

import javax.persistence.EntityManagerFactory;

import org.apache.camel.component.jpa.JpaComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;


@Configuration
public class JPAConfig {
		
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private JpaTransactionManager jpaTransactionManager;
	
	private static final Logger logger = LoggerFactory.getLogger(CamelConfig.class);

	@Bean(name="jpa")
	public JpaComponent getJPAComponent() {
		logger.debug("getJPAComponent() : Start.");
		
		JpaComponent jpaComponent = new JpaComponent();
		jpaComponent.setEntityManagerFactory(entityManagerFactory);
		jpaComponent.setTransactionManager(jpaTransactionManager);
		
		logger.debug("getJPAComponent() : End : jpaComponent={}", jpaComponent);
	
		return jpaComponent;
	}

}
