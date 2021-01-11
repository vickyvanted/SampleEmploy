package com.employee.smacs.service.processor.movie;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.employee.smacs.data.UserProfileData;


@Component("coreProcessor")
public abstract class CoreProcessor implements Processor {
	
	private final static Logger logger = LoggerFactory.getLogger(CoreProcessor.class);
	
	protected static Properties configProperties = null;
	
	public void process(Exchange exchange) throws Exception {
		
		logger.debug("Core process() : Start.");
		String token = (String) exchange.getIn().getHeader( "RefereshAuthorization");
		
		if(!StringUtils.isEmpty(token)){
			exchange.getOut().setHeader( "RefereshAuthorization", token );
			logger.debug("RefereshAuthorization:"+token);
		}
		
		doProcess(exchange);
		logger.debug("Core process() : End.");
	}
	
	
	public boolean centralCacheProcess (Exchange exchange,String cc_IdS) throws Exception {
		
		configProperties = new Properties();
		configProperties.load(new FileInputStream(new File(System.getProperty("CONFIG_FILE"))));
		boolean centralCacheEnable = Boolean.parseBoolean(configProperties.getProperty("smacs.centralcache.enabled"));
		boolean isNoChange = false;
		
		if(centralCacheEnable) {
			String cc_type = (String) exchange.getIn().getHeader("CC-Type");
			String cc_Id = (String) exchange.getIn().getHeader("CC-Id");
			String cc_status = "CHANGE";
			
			exchange.getOut().setHeader( "CC-Id", cc_IdS);
			exchange.getOut().setHeader( "CC-Status",cc_status);
			if(cc_IdS.equals(cc_Id) && !cc_type.equals("NEWCALL")) {
				cc_status = "NOCHANGE";
				exchange.getOut().setHeader( "CC-Status",cc_status);
				exchange.getOut().setBody(cc_IdS);
				exchange.getOut().setHeader( "STATUS", "SUCCESS" );
				isNoChange = true;
			}
		}
		
		
		return isNoChange;
	
	}
	
	
//public UserProfileData getUserProfileData (Exchange exchange) throws Exception {
//		String UserRole = (String) exchange.getIn().getHeader( "User-UserRole");
//		String ProjectId = (String) exchange.getIn().getHeader( "User-ProjectId");
//		String uId = (String) exchange.getIn().getHeader( "User-uId");
//		String isI3mUser = (String) exchange.getIn().getHeader( "User-isI3mUser");
//		String vendorLogIn = (String) exchange.getIn().getHeader( "User-vendorLogIn");
//		String vendorCode = (String) exchange.getIn().getHeader( "User-vendorCode");
//		UserProfileData usd = new UserProfileData();
//		
//		usd.setUId(uId);
//		usd.setUserRole(UserRole);
//		usd.setProjects(ProjectId);
//		usd.setIsI3mUser(isI3mUser);
//		usd.setVendorLogIn(vendorLogIn);
//		usd.setVendorCode(vendorCode);
//		return usd;
//	}
	
	public abstract void doProcess(Exchange exchange) throws Exception;
	
}

