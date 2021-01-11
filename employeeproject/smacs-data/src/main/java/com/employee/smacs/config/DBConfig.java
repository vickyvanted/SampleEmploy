
package com.employee.smacs.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class DBConfig {
		
	// Database connection Details
	@Value("${hibernate.dialect:org.hibernate.dialect.SQLServer2012Dialect}")
	private String dbDialect;

	@Value("${hibernate.connection.driver_class:com.microsoft.sqlserver.jdbc.SQLServerDriver}")
	private String dbDriverClass;

	@Value("${HIBERNATE_CONNECTION_URL}")
	private String dbUrl;

	@Value("${HIBERNATE_CONNECTION_USERNAME}")
	private String dbUsername;

	@Value("${HIBERNATE_CONNECTION_PASSWORD}")
	private String dbPassword;
	
	@Value("${hibernate.show_sql:false}")
	private Boolean showSql;
	
	@Value("${hibernate.format_sql:false}")
	private Boolean formatSql;
	
	@Value("${hibernate.hbm2ddl.auto:update}")
	private String autoCreate;
	
	// c3p0 conf
	@Value("${hibernate.c3p0.max_size:100}")
	private String c3p0MaxSize;
	
	@Value("${hibernate.c3p0.min_size:7}")
	private String c3p0MinSize;
	
	@Value("${hibernate.c3p0.acquire_increment:1}")
	private String c3p0AcquireIncrement;
	
	@Value("${hibernate.c3p0.idle_test_period:300}")
	private String c3p0IdleTestPeriod;
	
	@Value("${hibernate.c3p0.max_statements:50}")
	private String c3p0MaxStatements;
	
	@Value("${hibernate.c3p0.timeout:100}")
	private String c3p0Timeout;
	
	@Value("${hibernate.c3p0.initialPoolSize:1}")
	private String initialPoolSize;
	
	@Bean(name="entityManagerFactory")
	public EntityManagerFactory getEntityManagerFactory() {
		
		log.debug("getEntityManagerFactory() : Start.");
		
		Map<String, Object> properties = new HashMap<>();
		
		properties.put("hibernate.dialect", dbDialect);
		properties.put("hibernate.connection.driver_class", dbDriverClass);
		properties.put("hibernate.connection.url", dbUrl);
		properties.put("hibernate.connection.username", dbUsername);
		properties.put("hibernate.connection.password", dbPassword);
		properties.put("hibernate.show_sql", showSql);
		properties.put("hibernate.format_sql", formatSql);
		properties.put("hibernate.hbm2ddl.auto", autoCreate);
		// properties.put("hibernate.connection.pool_size", 1);
		
		// c3p0
		properties.put("hibernate.c3p0.initialPoolSize", initialPoolSize);
		properties.put("hibernate.c3p0.max_size", c3p0MaxSize);
		properties.put("hibernate.c3p0.min_size", c3p0MinSize);
		properties.put("hibernate.c3p0.acquire_increment", c3p0AcquireIncrement);
		properties.put("hibernate.c3p0.idle_test_period", c3p0IdleTestPeriod);
		properties.put("hibernate.c3p0.max_statements", c3p0MaxStatements);
		properties.put("hibernate.c3p0.timeout", c3p0Timeout);
		properties.put("hibernate.proc.param_null_passing", true);

//		log.info("dbDialect", dbDialect);
//		log.info("dbDriverClass", dbDriverClass);
//		log.info("dbUrl", dbUrl);
//		log.info("dbUsername", dbUsername);
//		log.info("showSql", showSql);
//		log.info("formatSql", formatSql);
//		log.info("autoCreate", autoCreate);
		
//		spring.jpa.properties.hibernate.proc.param_null_passing
		
//		log.info("c3p0MaxSize", c3p0MaxSize);
//		log.info("c3p0MinSize", c3p0MinSize);
//		log.info("c3p0AcquireIncrement", c3p0AcquireIncrement);
//		log.info("c3p0IdleTestPeriod", c3p0IdleTestPeriod);
//		log.info("c3p0MaxStatements", c3p0MaxStatements);
//		log.info("c3p0Timeout", c3p0Timeout);

		properties.put("hibernate.c3p0.unreturnedConnectionTimeout", 400);
		properties.put("hibernate.c3p0.debugUnreturnedConnectionStackTraces", true);
		
		// properties.put("hibernate.cache.use_query_cache", true);
		
		// Enable secondary level cache and factory_class
		// properties.put("hibernate.cache.use_second_level_cache", true);
		// properties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		
		// provider_class removed in latest hibernate version
		// properties.put("hibernate.connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
				
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("presence-management", properties);

		log.debug("getEntityManagerFactory() : End : entityManagerFactory={}", entityManagerFactory);
		return entityManagerFactory;
	}

	@Bean(name="transactionManager")
	public JpaTransactionManager getJpaTransactionManager() {
		log.debug("getJpaTransactionManager() : Start.");
		
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(getEntityManagerFactory());
		
		log.debug("getJpaTransactionManager() : End : jpaTransactionManager={}", jpaTransactionManager);
		
		return jpaTransactionManager;
	}
}
