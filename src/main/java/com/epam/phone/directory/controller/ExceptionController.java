package com.epam.phone.directory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleServerException(Throwable e) {
        return new ModelAndView("error", "errorMessage", takeErrorMessage(e));
    }

    private String takeErrorMessage(Throwable e) {
        return new StringBuilder()
                .append(e.getClass().getName())
                .append("-> message: ")
                .append(e.getLocalizedMessage())
                .append("; cause: ")
                .append(e.getCause())
                .toString();
    }

}
