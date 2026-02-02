package com.hoit.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class SpringAspect {
	private Logger log = LoggerFactory.getLogger(SpringAspect.class);
	
	@Pointcut("execution(* com.hoit.*.controller..*.*(..))")
	public void modelPointCuts() {}
	
	@Before("modelPointCuts()")
	public void modelPrint(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		
		for(Object arg : args) {
			log.info("parameter type [{}]", arg.getClass().getSimpleName());
			log.info("parameter value [{}]", arg);
		}
	}
	
	@AfterReturning(value = "modelPointCuts()", returning = "returnObj")
	public void afterLog(JoinPoint joinPoint, Object returnObj) {
		Method method = getMethod(joinPoint);
		log.info("========== methods name [{}] ==========", method.getName());
		if(returnObj != null) {
			log.info("return type [{}]", returnObj.getClass().getSimpleName());
		}
		log.info("return value [{}]", returnObj);
	}
	
	private Method getMethod(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getMethod();
	}
}
