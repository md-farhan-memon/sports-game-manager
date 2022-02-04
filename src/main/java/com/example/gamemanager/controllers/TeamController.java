package com.example.gamemanager.controllers;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.example.gamemanager.dtos.request.PatchTeamRequest;
import com.example.gamemanager.models.Team;
import com.example.gamemanager.models.User;
import com.example.gamemanager.services.helper.TeamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/teams")
public class TeamController extends BaseController {

  @Autowired
  private TeamService teamService;

  @GetMapping
  @PreAuthorize("hasAnyAuthority('User')")
  public ResponseEntity<Set<Team>> teams() {
    return ResponseEntity.ok().body(getCurrentUser().getTeams());
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('User')")
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody PatchTeamRequest patchTeamRequest,
      HttpServletRequest request) {
    User user = getCurrentUser();
    Optional<Team> team = teamService.getUserTeam(user, id);
    if (team.isPresent()) {
      return ResponseEntity.ok().body(teamService.patchTeam(patchTeamRequest, team.get()));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(notFoundResponseBody("Team not found.", request));
    }
  }
}
