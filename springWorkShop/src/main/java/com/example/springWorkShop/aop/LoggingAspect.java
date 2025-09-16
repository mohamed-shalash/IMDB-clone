package com.example.springWorkShop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.springWorkShop.service.*.*(..))")
    public void serviceMethods() {}


    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("➡️ Entering: " + joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("✅ Returned from: " + joinPoint.getSignature() + " with value: " + result);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        System.out.println("❌ Exception in: " + joinPoint.getSignature() + " with message: " + ex.getMessage());
    }
}
