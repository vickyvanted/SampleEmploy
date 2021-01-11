package com.employee.smacs.dao;

import java.util.List;

import com.employee.smacs.data.Author;
import com.employee.smacs.data.Book;
import com.employee.smacs.data.Employee;


public interface EmployeeDao {
	
	public List<Employee> findAll() throws Exception;
	
	
    public Employee findOne(long id) throws Exception;
	
	public Employee create(Employee employee) throws Exception;
	
	
    public void update(long id, Employee employee) throws Exception;
	
	
	public void delete(long id) throws Exception;
	
	

}

