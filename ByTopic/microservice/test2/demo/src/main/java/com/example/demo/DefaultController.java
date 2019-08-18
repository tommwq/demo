package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultController {

  // @ExceptionHandler(Exception.class)
  // @ResponseStatus(HttpStatus.NOT_FOUND)
  // @ResponseBody
  // public String handleException(Exception e) {
  //   return e.getMessage();
  // }

  @RequestMapping("*")
  @ResponseBody
  public String handle(HttpServletRequest request) {
    return "OK";
  }
  
}
