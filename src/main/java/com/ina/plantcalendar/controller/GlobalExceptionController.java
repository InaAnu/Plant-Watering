package com.ina.plantcalendar.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandling(Exception e) {
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        errorPage.addObject("error_msg", e.getMessage());
        return errorPage;
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView sqlExceptionHandling(Exception e) {
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        errorPage.addObject("error_msg", "Could not connect to database:" + e.getMessage());
        return errorPage;
    }
}
