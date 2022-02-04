package com.example.gamemanager.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.gamemanager.models.User;
import com.example.gamemanager.security.CurrentUser;
import com.example.gamemanager.services.db.UserDbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController {
  @Autowired
  private UserDbService userDbService;

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  public User getCurrentUser() {
    CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userDbService.findbyId(currentUser.getId());
  }

  public Map<String, Object> unAthorisedResponseBody(String message, HttpServletRequest request) {
    final Map<String, Object> body = new HashMap<>();
    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    body.put("error", "Unauthorized");
    body.put("message", message);
    body.put("path", request.getServletPath());
    return body;
  }

  public Map<String, Object> notFoundResponseBody(String message, HttpServletRequest request) {
    final Map<String, Object> body = new HashMap<>();
    body.put("status", HttpServletResponse.SC_NOT_FOUND);
    body.put("error", "Not Found");
    body.put("message", message);
    body.put("path", request.getServletPath());
    return body;
  }
}
