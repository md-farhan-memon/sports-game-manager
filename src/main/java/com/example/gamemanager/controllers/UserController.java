package com.example.gamemanager.controllers;

import com.example.gamemanager.dtos.request.PatchUserRequest;
import com.example.gamemanager.models.User;
import com.example.gamemanager.services.helper.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController extends BaseController {

  @Autowired
  private UserService userService;

  @GetMapping("/me")
  @PreAuthorize("hasAnyAuthority('User')")
  public ResponseEntity<User> currentUser() {
    return ResponseEntity.ok().body(getCurrentUser());
  }

  @PatchMapping("/update")
  @PreAuthorize("hasAnyAuthority('User')")
  public ResponseEntity<User> update(@RequestBody PatchUserRequest patchUserRequest) {
    User user = getCurrentUser();
    return ResponseEntity.ok().body(userService.patchUser(patchUserRequest, user));
  }
}
