package com.employee.smacs.dao.impl;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;

import com.employee.smacs.data.Author;
import com.employee.smacs.data.Book;
import com.employee.smacs.data.Employee;
import com.employee.smacs.dao.EmployeeDao;
import com.employee.smacs.repository.AuthorRepository;
import com.employee.smacs.repository.BookRepository;
import com.employee.smacs.repository.EmployeeRepository;


import lombok.extern.slf4j.Slf4j;

@Component("employeeDao")
@Slf4j
public class EmployeeDaoImpl implements EmployeeDao {
	
	@Autowired
	private EmployeeRepository repository;
	
	
	@Autowired
	private JpaTransactionManager transactionManager;
	

	@Override 
	public List<Employee> findAll() throws Exception {
		return  repository.findAll();
    }
	
	
	
	public Employee create(Employee employee) throws Exception {
        return repository.save(employee);
    }
	

	public Employee findOne(long id) throws Exception {
		return repository.findOne(id);
    }
	
	public void update(long id, Employee employee) throws Exception {
		
		repository.save(employee);
    }
	
	public void delete(long id) throws Exception {
		
        repository.delete(id);
    }

	
	
	
	

	
}
