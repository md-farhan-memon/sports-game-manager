package com.example.gamemanager.controllers;

import javax.validation.Valid;

import com.example.gamemanager.commons.Role;
import com.example.gamemanager.dtos.request.SigninRequest;
import com.example.gamemanager.dtos.request.SignupRequest;
import com.example.gamemanager.dtos.response.MessageResponse;
import com.example.gamemanager.models.User;
import com.example.gamemanager.security.CurrentUser;
import com.example.gamemanager.security.JwtUtils;
import com.example.gamemanager.services.db.UserDbService;
import com.example.gamemanager.services.helper.TeamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController extends BaseController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDbService userDbService;

  @Autowired
  private TeamService teamService;

  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userDbService.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmail(),
        passwordEncoder.encode(signUpRequest.getPassword()), Role.User);

    userDbService.save(user);

    teamService.generateNewTeam(user.getId());

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signin")
  public ResponseEntity<CurrentUser> authenticateUser(@Valid @RequestBody SigninRequest signinRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();

    String jwt = jwtUtils.generateTokenFromUsername(currentUser.getUsername());
    User user = userDbService.findbyId(currentUser.getId());
    user.setAccessToken(jwt);
    userDbService.save(user);

    currentUser = CurrentUser.build(user);
    return ResponseEntity.ok().body(currentUser);
  }

  @DeleteMapping("/signout")
  @PreAuthorize("hasAnyAuthority('User')")
  public ResponseEntity<MessageResponse> logoutUser() {
    CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    User user = userDbService.findbyId(currentUser.getId());
    user.setAccessToken(null);
    userDbService.save(user);
    return ResponseEntity.ok().body(new MessageResponse("You've been signed out!"));
  }
}
