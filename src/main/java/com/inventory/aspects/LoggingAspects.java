
package com.inventory.aspects;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspects {
 
	private static Logger logger=Logger.getLogger(LoggingAspects.class.getSimpleName());
	//preceeding * mean match any method public private protected
	
	@Pointcut("execution(* com.inventory.controllers..*(..))")
	private void generalPointCut(){}
	// we also have bean property
	//bean(*Manager Match all methods defined in beans whose name ends with ‘Manager’.)
	@Pointcut("within(com.inventory.beans..*)")
	private void withinSpecificPackage() {}
	
	@Before("generalPointCut()")
	public void logDetail(JoinPoint joinPoint){
		logger.log(Level.INFO, joinPoint.getThis().toString()+"-------"+joinPoint.getClass().getSimpleName());
	}
	
	
}
