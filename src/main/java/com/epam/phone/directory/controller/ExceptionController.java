package com.epam.phone.directory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNotValidInputData(Throwable e) {
        logger.error("Sending client error because of not valid input data: ", e);
        return new ModelAndView("error", "errorMessage", takeErrorMessage(e));
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleUnexpectedExceptions(Throwable e) {
        logger.error("Sending internal server error because of unexpected exception: ", e);
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
