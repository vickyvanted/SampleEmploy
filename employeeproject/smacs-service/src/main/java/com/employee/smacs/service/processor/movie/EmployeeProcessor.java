package com.employee.smacs.service.processor.movie;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.employee.smacs.dao.EmployeeDao;
import com.employee.smacs.data.Author;
import com.employee.smacs.data.Book;
import com.employee.smacs.data.Employee;
import com.employee.smacs.service.model.ErrorMessage;

@Component("employeeProcessor")
public class EmployeeProcessor extends CoreProcessor {
	
	private final static Logger logger = LoggerFactory.getLogger(EmployeeProcessor.class);
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private ObjectMapper mapper;
	

	public void doProcess(Exchange exchange) throws Exception {
		
		logger.debug("process() : Start.");
		
		
		String method =  (String) exchange.getIn().getHeader("method");
		logger.debug("process() : method={}", method);
		
		
		
		HashMap<String, Object> outputValues = new HashMap<>();
		
		try {
			if ("findAll".equals(method)) {
				exchange.getOut().setBody( employeeDao.findAll() );
				
			} 
			
			else if ("create".equals(method)) {				
				@SuppressWarnings("unchecked")
				Map<String, Object> inputValues = (HashMap<String, Object>) exchange.getIn().getBody();
				Employee employee = mapper.convertValue(inputValues, Employee.class);
				exchange.getOut().setBody( employeeDao.create(employee) );
			}
			else if ("findOne".equals(method)) {
				Long id =  exchange.getIn().getHeader("Id", Long.class);
				Employee employee = employeeDao.findOne(id);
				
				if (employee != null) {
					exchange.getOut().setBody( employee );
				} else {
					exchange.getOut().setBody( outputValues );
				}
			} 
			
			else if ("update".equals(method)) {
				@SuppressWarnings("unchecked")
				Map<String, Object> inputValues = (HashMap<String, Object>) exchange.getIn().getBody();
				HashMap<String,String> hm=new HashMap<String,String>();  
				Long id = exchange.getIn().getHeader("Id", Long.class);
				Employee employee = mapper.convertValue(inputValues, Employee.class);
				employeeDao.update(id, employee);
				  
				} 
			else if ("delete".equals(method)) {
				Long id = exchange.getIn().getHeader("Id", Long.class);
				employeeDao.delete(id);
			}
			
			
			exchange.getOut().setHeader( "STATUS", "SUCCESS" );
		} catch (Exception ex) {
			
			logger.error("process() : error={}" +  ex);
			
			ex.printStackTrace();
			
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setErrorCode("ERROR_TAG_PROCESSOR");
			errorMessage.setErrorMessage(ex.getMessage());
			
			exchange.getOut().setHeader("STATUS", "FAILURE");
			exchange.getOut().setBody(errorMessage);
		}
		
		
		
		
		
		
		
		
		
		
		
		logger.debug("process() : End.");
	}
	
	
	

	

	
}

