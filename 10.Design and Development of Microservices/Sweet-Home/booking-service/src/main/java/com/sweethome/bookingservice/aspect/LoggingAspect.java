package com.sweethome.bookingservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


/**
 * Logging AOP used for debugging.
 * Note: Logging aspect is Disabled. Uncomment the @Around to enable AOP LoggingAspect.
 *
 */
@Aspect
@Component
public class LoggingAspect {

    // Uncomment to Enable Logging
    //@Around("execution(* com.sweethome.bookingservice..*(..))")
    public Object applyLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        System.out.println("In " + className + ", entering " + methodName);

        Object result = joinPoint.proceed();
        System.out.println("In " + className + ", exiting " + methodName);

        return result;
    }
}
