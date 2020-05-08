package com.example.springaop.aspect;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springaop.controller.MainController;
import com.example.springaop.model.Dues;
import com.example.springaop.model.Logs;

@Aspect
@Component
public class UserAspect {
	
	@Autowired
	MainController mainController;
	
	@Before("execution(* com.example.springaop.controller.MainController.getAllUsers(..))")
	public void get(JoinPoint joinPoint) {
		Integer id = 1;
		mainController.addLog(new Logs("Opened all users", new Date(), id));
		System.out.println("Added log for " + joinPoint.getArgs().toString());
	}
	
	@Before("execution(* com.example.springaop.controller.MainController.getAllMaintenance(..))")
	public void getFoo(JoinPoint joinPoint) {
		mainController.addLog(new Logs("Got all maintenance", new Date(), 2));
		System.out.println("Added log for " + joinPoint.getArgs().toString());
	}

	@Before("execution(* com.example.springaop.service.UserService.set*(..))")
	public void getAllSetAdvices(JoinPoint joinPoint) {
		System.out.println("Excute advice on Service set Method: " + joinPoint.getSignature().getName());
	}
	
	@AfterReturning("execution(* com.example.springaop.controller.MainController.addDue(..))")
	public void setDues(JoinPoint joinPoint) {
		if (joinPoint.getArgs() instanceof Dues[]) {
			Dues[] foo = (Dues[]) joinPoint.getArgs();
			if (foo.length > 0) { 
				Integer id = foo[0].getUserId();
				mainController.addLog(new Logs("Added due", new Date(), id));
				System.out.println("Added log for " + joinPoint.getArgs().toString());
			}
			
		}
		System.out.println("Excute advice on Service add Method: " + joinPoint.getSignature().getName());
	}
}
