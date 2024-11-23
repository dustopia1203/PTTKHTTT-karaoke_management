package com.dustopia.karaoke.common.aop;

import com.dustopia.karaoke.model.Manager;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Aspect
@Component
public class AuthAspect {

    @Around("@annotation(Auth)")
    public Object auth(final ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return new ModelAndView("redirect:/login");
        }
        HttpSession session = attributes.getRequest().getSession(false);
        if (session == null) {
            return new ModelAndView("redirect:/login");
        }
        Manager manager = (Manager) session.getAttribute("manager");
        if (Objects.isNull(manager)) {
            return new ModelAndView("redirect:/login");
        }
        return joinPoint.proceed();
    }

}
