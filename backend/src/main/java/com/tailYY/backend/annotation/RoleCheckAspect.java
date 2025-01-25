package com.tailYY.backend.annotation;

import com.tailYY.backend.model.User;
import com.tailYY.backend.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author daken 2025/1/25
 **/
@Aspect
@Component
public class RoleCheckAspect {

    @Resource
    private UserService userService;

    @Around("@annotation(requireRole)") // 拦截所有使用了@RequireRole注解的方法
    public Object checkRole(ProceedingJoinPoint joinPoint, RequireRole requireRole) throws Throwable {
        String currentUserRole = getCurrentUserRole();

        if (requireRole.value().equals(currentUserRole)) {
            return joinPoint.proceed(); // 如果角色匹配，则继续执行原方法
        } else {
            throw new SecurityException("Access denied: insufficient role");
        }
    }

    private String getCurrentUserRole() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User loginUser = userService.getLoginUser(request);
        return loginUser.getUserrole();
    }
}
