package com.example.gamemanager.controllers;

import com.example.gamemanager.models.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController extends BaseController {

  @GetMapping("/me")
  @PreAuthorize("hasAnyAuthority('User')")
  public ResponseEntity<User> currentUser() {
    return ResponseEntity.ok().body(getCurrentUser());
  }
}
