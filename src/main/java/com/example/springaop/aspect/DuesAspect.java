package com.example.springaop.aspect;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springaop.controller.MainController;
import com.example.springaop.model.Dues;
import com.example.springaop.model.Logs;

@Aspect
@Component
public class DuesAspect {
	
	@Autowired
	MainController mainController;
	
	@Before("execution(* com.example.springaop.controller.MainController.foo(..))")
	public void getDuesForId(JoinPoint joinPoint) {
		System.out.println("All dues have been requested");
	}

	@Before("execution(* com.example.springaop.controller.MainController.foo(..))")
	public void setDues(JoinPoint joinPoint) {
		Dues[] foo = (Dues[]) joinPoint.getArgs();
		if (foo.length > 0) { 
			Integer id = foo[0].getUserId();
			mainController.addLog(new Logs("Added due", new Date(), id));
			System.out.println("Added log for " + joinPoint.getArgs().toString());
		}
		
		
		System.out.println("Excute advice on Service add Method: " + joinPoint.getSignature().getName());
	}
}
