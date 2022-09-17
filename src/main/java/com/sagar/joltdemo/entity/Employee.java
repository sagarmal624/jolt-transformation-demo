package com.sagar.joltdemo.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class Employee {
	@Id
	private String id;
	private String firstName;
	private String lastName;
	@OneToOne(cascade = CascadeType.ALL)
	private Contact contact;
	private String fullName;
	private Integer salary;
	private String doj;
	private String source;
}