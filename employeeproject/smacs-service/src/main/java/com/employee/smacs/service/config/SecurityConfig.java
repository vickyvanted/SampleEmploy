
package com.employee.smacs.service.config;

import org.apache.camel.component.spring.security.SpringSecurityAccessPolicy;
import org.apache.camel.component.spring.security.SpringSecurityAuthorizationPolicy;
import org.apache.camel.spi.AuthorizationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * @author Linkeddots Development Team.
 *
 */
@Configuration
@ImportResource("classpath:config/security.xml")
public class SecurityConfig {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Create Policy for API Users
	 * 
	 * @param accessDecisionManager
	 * @param manager
	 * @return
	 */
	@Bean(name = "apiUserPolicy")
	public AuthorizationPolicy createLDUserPolicy(AccessDecisionManager accessDecisionManager,
			AuthenticationManager manager) {
		
		logger.debug("createLDUserPolicy() : Start : accessDecisionManager={}, manager={}", accessDecisionManager, manager);
		
		SpringSecurityAuthorizationPolicy policy = new SpringSecurityAuthorizationPolicy();
		policy.setAccessDecisionManager(accessDecisionManager);
		policy.setAuthenticationManager(manager);
		policy.setId("User");
		SpringSecurityAccessPolicy access = new SpringSecurityAccessPolicy("ROLE_USER");
		policy.setSpringSecurityAccessPolicy(access);
		
		logger.debug("createLDUserPolicy() : End : policy={}", policy);
		
		return policy;
	}
	
	/**
	 * Create Policy for Admin Users
	 * 
	 * @param accessDecisionManager
	 * @param manager
	 * @return
	 */
	@Bean(name = "managerUserPolicy")
	public AuthorizationPolicy createManagerUserPolicy(AccessDecisionManager accessDecisionManager,
			AuthenticationManager manager) {
		SpringSecurityAuthorizationPolicy policy = new SpringSecurityAuthorizationPolicy();
		policy.setAccessDecisionManager(accessDecisionManager);
		policy.setAuthenticationManager(manager);
		policy.setId("ManagerUser");
		SpringSecurityAccessPolicy access = new SpringSecurityAccessPolicy("ROLE_MANAGER");
		policy.setSpringSecurityAccessPolicy(access);
		return policy;
	}
	
	
	
	
	/**
	 * Create Policy for Admin Users
	 * 
	 * @param accessDecisionManager
	 * @param manager
	 * @return
	 */
	@Bean(name = "adminUserPolicy")
	public AuthorizationPolicy createAdminUserPolicy(AccessDecisionManager accessDecisionManager,
			AuthenticationManager manager) {
		SpringSecurityAuthorizationPolicy policy = new SpringSecurityAuthorizationPolicy();
		policy.setAccessDecisionManager(accessDecisionManager);
		policy.setAuthenticationManager(manager);
		policy.setId("AdminUser");
		SpringSecurityAccessPolicy access1 = new SpringSecurityAccessPolicy("ROLE_USER");
		policy.setSpringSecurityAccessPolicy(access1);
		SpringSecurityAccessPolicy access2 = new SpringSecurityAccessPolicy("ROLE_ADMIN");
		policy.setSpringSecurityAccessPolicy(access2);
		
		return policy;
	}

	/**
	 * Create Policy for Admin Users
	 * 
	 * @param accessDecisionManager
	 * @param manager
	 * @return
	 */
	@Bean(name = "superAdminUserPolicy")
	public AuthorizationPolicy createSuperAdminUserPolicy(AccessDecisionManager accessDecisionManager,
			AuthenticationManager manager) {
		SpringSecurityAuthorizationPolicy policy = new SpringSecurityAuthorizationPolicy();
		policy.setAccessDecisionManager(accessDecisionManager);
		policy.setAuthenticationManager(manager);
		policy.setId("SuperAdminUser");
		SpringSecurityAccessPolicy access = new SpringSecurityAccessPolicy("ROLE_SUPERADMIN");
		policy.setSpringSecurityAccessPolicy(access);
		return policy;
	}



	/**
	 * Create Policy for Configurator Users
	 * 
	 * @param accessDecisionManager
	 * @param manager
	 * @return
	 */
	@Bean(name = "apiConfiguratorPolicy")
	public AuthorizationPolicy createAPIConfiguratorPolicy(AccessDecisionManager accessDecisionManager,
			AuthenticationManager manager) {
		SpringSecurityAuthorizationPolicy policy = new SpringSecurityAuthorizationPolicy();
		policy.setAccessDecisionManager(accessDecisionManager);
		policy.setAuthenticationManager(manager);
		policy.setId("APIConfigurator");
		SpringSecurityAccessPolicy access = new SpringSecurityAccessPolicy("ROLE_CONFIGURATOR");
		policy.setSpringSecurityAccessPolicy(access);
		return policy;
	}

	/**
	 * Create Policy for Monitor Users
	 * 
	 * @param accessDecisionManager
	 * @param manager
	 * @return
	 */
	@Bean(name = "apiMonitorPolicy")
	public AuthorizationPolicy createAPIMonitorPolicy(AccessDecisionManager accessDecisionManager,
			AuthenticationManager manager) {
		SpringSecurityAuthorizationPolicy policy = new SpringSecurityAuthorizationPolicy();
		policy.setAccessDecisionManager(accessDecisionManager);
		policy.setAuthenticationManager(manager);
		policy.setId("APIMonitor");
		SpringSecurityAccessPolicy access = new SpringSecurityAccessPolicy("ROLE_APIMONITOR");
		policy.setSpringSecurityAccessPolicy(access);
		return policy;
	}
}
