package com.example.springaop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dues {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer userId;
	private Integer numberOfDues;
	
	public Dues() {
		
	}
	
	public Dues(Integer userId, Integer numberOfDues) {
		super();
		this.userId = userId;
		this.numberOfDues = numberOfDues;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getNumberOfDues() {
		return numberOfDues;
	}
	public void setNumberOfDues(Integer numberOfDues) {
		this.numberOfDues = numberOfDues;
	}
	
}
