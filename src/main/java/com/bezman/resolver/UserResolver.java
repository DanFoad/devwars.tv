package com.bezman.resolver;

import com.bezman.annotation.AuthedUser;
import com.bezman.model.User;
import org.hibernate.Session;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Terence on 6/29/2015.
 */
public class UserResolver implements HandlerMethodArgumentResolver
{

    @Override
    public boolean supportsParameter(MethodParameter methodParameter)
    {
        System.out.println(methodParameter.getParameterType().getName());
        return methodParameter.getParameterType().equals(User.class) && methodParameter.getMethodAnnotation(AuthedUser.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception
    {
        ServletWebRequest webRequest = (ServletWebRequest) nativeWebRequest;

        return webRequest.getRequest().getAttribute("user");
    }
}
