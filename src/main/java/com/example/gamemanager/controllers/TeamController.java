package com.example.gamemanager.controllers;

import java.util.Set;

import com.example.gamemanager.models.Team;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/teams")
public class TeamController extends BaseController {

  @GetMapping
  @PreAuthorize("hasAnyAuthority('User')")
  public ResponseEntity<Set<Team>> teams() {
    return ResponseEntity.ok().body(getCurrentUser().getTeams());
  }
}
