package com.example.springaop.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springaop.aspect.Loggable;
import com.example.springaop.model.Dues;
import com.example.springaop.model.Logs;
import com.example.springaop.model.Maintenance;
import com.example.springaop.model.UserDB;
import com.example.springaop.repository.DuesRepository;
import com.example.springaop.repository.LogsRepository;
import com.example.springaop.repository.MaintenanceRepository;
import com.example.springaop.repository.UserRepository;

@Controller 
@Component
@RequestMapping(path="/demo") 
public class MainController {
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private MaintenanceRepository maintenanceRepository;
	
	@Autowired 
	private DuesRepository duesRepository;
	
	@Autowired 
	private LogsRepository logsRepository;

	@PostMapping(path="/add") 
	public UserDB addNewUser (@RequestBody UserDB user) {
		return userRepository.save(user);
	}

	@PostMapping(path="/delete") 
	public void deleteUser (@RequestBody UserDB user) {
		userRepository.delete(user);
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<UserDB> getAllUsers() {
		Iterable<UserDB> users = userRepository.findAll();
		
		ArrayList<UserDB> usersArray = new ArrayList<UserDB>();
		
		for(UserDB u : users) {
			usersArray.add(u);
		}
		
		System.out.println("Users: " + usersArray.size());
		
		addDue(new Dues(1, 3));
		
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
	
	@GetMapping(path="/allDues")
	public Dues addDue(Dues due) {
		return duesRepository.save(due);
	}
	
	public Iterable<Dues> getAllDuesOfUser(Integer id) {
		Iterable<Dues> dues = duesRepository.findAllById(new ArrayList<Integer>(id));
		return dues;
	}
	
 	public Iterable<Logs> getAllLogs() {
		return logsRepository.findAll();
 	}
 	
 	public Logs addLog(Logs log) {
 		return logsRepository.save(log);
 	}
	
}
