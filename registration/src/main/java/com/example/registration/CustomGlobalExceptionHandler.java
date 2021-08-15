package com.example.registration;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<String, Object>();
        body.put("code", status.value());
        /*JAVA 1.8*/
        //Get all errors
        List<Object> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(new Function<FieldError, Object>() {
					@Override
					public Object apply(FieldError x) {
						return x.getDefaultMessage();
					}
				})
                .collect(Collectors.toList());

//        body.put("errors", errors);
        /*JAVA 1.6*/
//        List<String> errors = new ArrayList<String>();
//		for(FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
//			errors.add(fieldError.getDefaultMessage());
//		}
		body.put("description", errors);
		body.put("result", status.value());
	
        
        return new ResponseEntity<Object>(body, headers, status);

    }

}
