package com.example.ChatBot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({HttpClientErrorException.Unauthorized.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    Map<String, String> unauthorizedAccess(Exception e) {
        Map<String, String> exception = new HashMap<String, String>();

//        log.error("unauthorized Access to the API: " + e.getMessage(), e);
        exception.put("code", "401");
        exception.put("reason", e.getMessage());

        return exception;
    }
}
