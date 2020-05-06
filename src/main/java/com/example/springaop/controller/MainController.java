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

import com.example.springaop.model.UserDB;
import com.example.springaop.repository.UserRepository;

@Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
	@Autowired // This means to get the bean called userRepository which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;

	@PostMapping(path="/add") 
	public UserDB addNewUser (@RequestBody UserDB user) {
		return userRepository.save(user);
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<UserDB> getAllUsers() {
		// This returns a JSON or XML with the users
		Iterable<UserDB> users = userRepository.findAll();
		
		ArrayList<UserDB> usersArray = new ArrayList<UserDB>();
		
		for(UserDB u : users) {
			usersArray.add(u);
		}
		
		System.out.println("Users: " + usersArray.size());
		
		return users;
	}
}