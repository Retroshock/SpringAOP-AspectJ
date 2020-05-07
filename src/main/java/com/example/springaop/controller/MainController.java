package com.example.springaop.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springaop.model.Maintenance;
import com.example.springaop.model.UserDB;
import com.example.springaop.repository.MaintenanceRepository;
import com.example.springaop.repository.UserRepository;

@Controller 
@RequestMapping(path="/demo") 
public class MainController {
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private MaintenanceRepository maintenanceRepository;

	@PostMapping(path="/add") 
	public UserDB addNewUser (@RequestBody UserDB user) {
		return userRepository.save(user);
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<UserDB> getAllUsers() {
		Iterable<UserDB> users = userRepository.findAll();
		
		ArrayList<UserDB> usersArray = new ArrayList<UserDB>();
		
		for(UserDB u : users) {
			usersArray.add(u);
		}
		
		System.out.println("Users: " + usersArray.size());
		
		return users;
	}
	
	@GetMapping(path="/allMaintenance")
	public Iterable<Maintenance> getAllMaintenance() {
		Iterable<Maintenance> maintenance = maintenanceRepository.findAll();
		return maintenance;
	}
	
	@Autowired
	public MainController() {
		
	}
}