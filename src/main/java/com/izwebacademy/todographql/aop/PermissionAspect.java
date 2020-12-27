package com.izwebacademy.todographql.aop;

import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.models.JwtUser;
import com.izwebacademy.todographql.models.Permission;
import com.izwebacademy.todographql.models.User;
import com.izwebacademy.todographql.repositories.UserRepository;
import com.izwebacademy.todographql.utils.EntityException;
import com.izwebacademy.todographql.utils.GenericException;
import com.izwebacademy.todographql.utils.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Before("@annotation(com.izwebacademy.todographql.annotations.PermissionMetaData) && execution(public * *(..))")
    public void restrict(final JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PermissionMetaData annotation = signature.getMethod().getAnnotation(PermissionMetaData.class);

        JwtUser jwtUser = jwtUtil.getJwtUser();

        String username = jwtUser.getUsername();

        Optional<User> dbUser = userRepository.findByUsernameAndActiveTrue(username);

        if (!dbUser.isPresent()) {
            throw new EntityException("User not found", username);
        }

        User user = dbUser.get();

        List<String> permissions = user.getPermissions().stream().map(Permission::getName).collect(Collectors.toList());

        String permission = annotation.permissionName();

        if (!permissions.contains(permission)) {
            throw new GenericException("Access Denied", permission);
        }

    }




}
