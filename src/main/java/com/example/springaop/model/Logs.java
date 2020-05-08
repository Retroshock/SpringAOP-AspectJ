package com.example.springaop.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Logs {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer id;
	private String action;
	private Date date;
	private Integer userId;
	
	public Logs() {
		
	}
	
	public Logs(String action, Date date, Integer userId) {
		super();
		this.action = action;
		this.date = date;
		this.userId = userId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
}	
