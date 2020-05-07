package com.example.springaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class UserAspect {
	
	@Before("execution(* com.example.springaop.service.UserService.getCustomerById(..))")
	public void get() {
		System.out.println("Execute advice on getCustomerByIdAdvice()");
	}

	@Before("execution(* com.example.springaop.service.UserService.set*(..))")
	public void getAllSetAdvices(JoinPoint joinPoint) {
		System.out.println("Excute advice on Service set Method: " + joinPoint.getSignature().getName());
	}
}
