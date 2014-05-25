package ru.timebilling.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.timebilling.model.service.ApplicationException;
import ru.timebilling.rest.domain.ErrorInfo;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(ApplicationException.class)
    public @ResponseBody ErrorInfo handleApplicationException(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURI(), ex);
    }

}
