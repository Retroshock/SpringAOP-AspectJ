package com.example.springaop.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springaop.service.UserLoggableService;
import com.example.springaop.service.UserService;

@RestController
public class WebController {
	ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
	UserLoggableService loggableService = context.getBean(UserLoggableService.class);
	UserService service = context.getBean(UserService.class);

	@RequestMapping("/aspectnormal")
	public String normalAspect() {
		String result = "";

		result += service.getUserById("1").getFirstName() + "<br/>";
		result += service.setUserFirstName("John", "1") + "<br/>";
		result += service.setUserLastName("Lennon", "2");

		return result;
	}

}