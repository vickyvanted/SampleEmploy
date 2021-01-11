package com.employee.smacs.service.routes;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmployeeServiceRoute extends AbstractRoute {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void configure() throws Exception {

		logger.debug("configure() : Started.");

		super.configure();
		
		//Create a Employee
		rest("/employee").post().description("Create a employee.")
			.route()
				.routeId("Createemployee")
					.setHeader("method", simple("create"))
					.to("employeeProcessor")
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201)).endRest();
		
		
		//Get all employees
		rest("/employee").get().description("Get all employees.")
			.route()
				.routeId("Getemployees")
				.description("Get all employees.")
				.setHeader("method", simple("findAll"))
				.setHeader("Cache-Control",constant("private, max-age=0,no-store"))
				.to("employeeProcessor")
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200)).endRest();
		
		
		// Get Single employees
		rest("/employee/{employeeId}").get().description("Returns employee details for a specific employee id.")
			.route()
				.routeId("GetemployeeById")
					.setHeader("Id", simple("${header['employeeId']}"))
					.setHeader("Cache-Control",constant("private, max-age=0,no-store"))
					.setHeader("method", simple("findOne"))
					.to("employeeProcessor")
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200)).endRest();

		// update a employee
		rest("/employee/{employeeId}").put().description("Update the employee details.")
			.route()
				.routeId("Updateemployee")
					.setHeader("Id", simple("${header['employeeId']}"))
					.setHeader("method", simple("update"))
					.to("employeeProcessor")
					.setBody(simple(""))
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204)).endRest();
		
		
		
		//Delete a specified employe
		rest("/employee/{employeeId}").delete().description("Delete a specified employee.")
			.route()
				.routeId("Deleteemployee")
					.setHeader("Id", simple("${header['employeeId']}"))
					.setHeader("method", simple("delete"))
					.to("employeeProcessor")
					.setBody(simple(""))
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204)).endRest();
		
	
		
	}


}
