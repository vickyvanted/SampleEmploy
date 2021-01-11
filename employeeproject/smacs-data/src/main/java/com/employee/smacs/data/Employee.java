package com.employee.smacs.data;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Employ")
@Cacheable(false)
public class Employee implements Serializable {
	
	private static final long serialVersionUID = -8678957894521953215L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "employeeId")
	private long employeeId;
	
	@Column(name = "employeefirstName")
	private String employeefirstName;
	
	@Column(name = "employeeLastName")
	private String employeeLastName;
	
	@Column(name = "emailId")
	private String emailId;
   
	@Column(name = "age")
	private String age;

	@Column(name = "gender")
	private String gender;
	
	@Column(name = "address")
	private String address;
	
}
