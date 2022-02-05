package com.example.gamemanager.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.gamemanager.dtos.response.DetailedResponse;
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

  public DetailedResponse unAthorisedResponseBody(String message, HttpServletRequest request) {
    return new DetailedResponse(
        HttpServletResponse.SC_UNAUTHORIZED,
        "Unauthorized",
        message,
        request.getServletPath());
  }

  public DetailedResponse notFoundResponseBody(String message, HttpServletRequest request) {
    return new DetailedResponse(
        HttpServletResponse.SC_NOT_FOUND,
        "Not Found",
        message,
        request.getServletPath());
  }

  public DetailedResponse invalidResponseBody(String message, HttpServletRequest request) {
    return new DetailedResponse(
        HttpServletResponse.SC_BAD_REQUEST,
        "Bad Request",
        message,
        request.getServletPath());
  }
}
