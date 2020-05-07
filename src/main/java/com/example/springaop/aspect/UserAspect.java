package com.example.springaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {
	
	@Before("execution(* com.example.springaop.*.*.*(..))")
	public void get(JoinPoint joinPoint) {
		System.out.println("Execute advice on getAllUsers" + joinPoint.getSignature().getName());
	}

	@Before("execution(* com.example.springaop.service.UserService.set*(..))")
	public void getAllSetAdvices(JoinPoint joinPoint) {
		System.out.println("Excute advice on Service set Method: " + joinPoint.getSignature().getName());
	}
}
