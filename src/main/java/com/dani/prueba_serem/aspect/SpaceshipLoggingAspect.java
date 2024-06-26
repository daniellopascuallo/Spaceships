package com.dani.prueba_serem.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpaceshipLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(SpaceshipLoggingAspect.class);

    @After("execution(* com.dani.prueba_serem.SpaceshipController.getSpaceshipById(..)) && args(id)")
    public void logNegativeId(JoinPoint joinPoint, Long id) {

        if (id < 0) {
            logger.warn("Requested id is negative: {}", id);
        }
    }
}
