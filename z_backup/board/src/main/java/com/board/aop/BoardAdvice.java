package com.board.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BoardAdvice {

	/*
	 * @Before("within (com.board.controller.BoardController)") public void
	 * beforeAdvice() { System.out.println("Before Pointcut : BoardController "); }
	 * 
	 * @After("execution(* com.board.controller.BoardController.getView(..))")
	 * public void afterAdvice() { System.out.println("After Pointcut : getView"); }
	 * 
	 * @AfterThrowing(pointcut="execution(* com.board*..*.*(..))", throwing="e")
	 * public void afterThrowingAdvice(Exception e) {
	 * System.out.println("에러 발생 : "+e); }
	 */

	@Around("execution (* com.board..*.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint pjp) {

//long start = System.currentTimeMillis();

		System.out.println("Around Pointcut --> 실행된 클래스 : " + pjp.getTarget());
		System.out.println("Around Pointcut --> 전달된 파라미터 : " + Arrays.toString(pjp.getArgs()));

		Object result = null;

		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}

//long end = System.currentTimeMillis();
//System.out.println("--- Time : "+(end-start));

		return result;
	}

}