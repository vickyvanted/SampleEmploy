package com.employee.smacs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employee.smacs.data.Employee;


@Repository("employeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	

	
	
}
