package com.example.registration;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.constant.Constant;
import com.example.constant.MessageConstant;
import com.example.constant.ReturnCodeConstant;
import com.example.registration.exception.InvalidCredentialException;
import com.example.registration.model.ReturnObject;
import com.example.utility.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class JwtAuthorizeInterceptor implements HandlerInterceptor{

	private Logger logger = LogManager.getLogger(this.getClass());
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		logger.info("Start JwtAuthorizationFilter ::doFilter::" + request.getMethod());
		String path = request.getRequestURI().substring(request.getContextPath().length());
		if(!request.getMethod().equalsIgnoreCase("OPTIONS") 
				&& (!isExceptPathAuthen(path)) )
		{
	        try {
	        	ThreadContext.put(Constant.CLIENT_IP_ADDRESS, request.getRemoteAddr());
	            String jwt = resolveToken(request);
	            logger.info("Token: "+ jwt);
	            if (StringUtils.isNotEmpty(jwt)) {
	            	ThreadContext.put(Constant.AUTH_TOKEN, jwt);
	            	JwtTokenUtil validificationToken = new JwtTokenUtil();
	            	ReturnObject returnObject = validificationToken.validificationToken(jwt, request.getRemoteAddr(),true);
	            	if(StringUtils.equals(returnObject.getCode(), ReturnCodeConstant.SUCCESS)) {
	            		return true;
	            	}
	            }else {
	            	throw new InvalidCredentialException("Invalid Token");
	            }
	            
	        } catch (InvalidCredentialException eje) {
	            logger.info("Security exception - {}",  eje.getMessage());
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            
	        } catch (Exception e) {
	        	logger.info("Internal exception - {}",  e.getMessage());
	        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	           
			}
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json");
	        
	        ObjectMapper mapper = new ObjectMapper();
	        ModelMap modelMap = new ModelMap();
	        modelMap.put("code", ReturnCodeConstant.UNAUTHORIZE_ERROR);
	        modelMap.put("description", MessageConstant.UNAUTHORIZE_ERROR);
	        modelMap.put("result", "");
	        PrintWriter writer = response.getWriter();
	        writer.print(mapper.writeValueAsString(modelMap));
	        writer.flush();
	        return false;
		} 
	 	return true;
	}
	
	private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt;
        }
        return null;
    }
	
	public boolean isExceptPathAuthen(String path) {
		
		boolean result = false;
		if(path.contains("/register") || path.contains("/generateToken")) {
			result = true;
		}
		return result;
	}
	
}
