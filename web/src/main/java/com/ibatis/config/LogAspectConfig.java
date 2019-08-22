package com.ibatis.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * AOP实现Controller层的Request和Response日志输出
 *
 * @author Malini | Date 2019-05-14
 */
@Configuration
@Aspect
public class LogAspectConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspectConfig.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Before("within(com.ibatis.controller.*)")
    public void before(JoinPoint joinPoint) throws JsonProcessingException {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        if (Objects.nonNull(sra)) {
            HttpServletRequest request = sra.getRequest();
            String methodType = request.getMethod();
            String uri = request.getRequestURI();
            String params = OBJECT_MAPPER.writeValueAsString(request.getParameterMap());
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            LOGGER.info("[请求:{}.{}],[URI:{},method:{}],[params:{}]", method.getDeclaringClass().getSimpleName(),
                    method.getName(), uri, methodType, params);
        }
    }

    @AfterReturning(value = "within(com.ibatis.controller.*)", returning = "obj")
    public void after(JoinPoint joinPoint, Object obj) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LOGGER.info("[响应:{}.{}],[Result:{}]", method.getDeclaringClass().getSimpleName(),
                method.getName(), OBJECT_MAPPER.writeValueAsString(obj));
    }
}
