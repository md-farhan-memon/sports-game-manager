package com.example.gamemanager.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.example.gamemanager.dtos.request.PatchTeamPlayerRequest;
import com.example.gamemanager.models.TeamPlayer;
import com.example.gamemanager.models.User;
import com.example.gamemanager.services.helper.TeamPlayerService;

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
@RequestMapping("/api")
public class TeamPlayerController extends BaseController {

  @Autowired
  private TeamPlayerService teamPlayerService;

  @GetMapping("/teams/{teamId}/players/{id}")
  @PreAuthorize("hasAnyAuthority('User')")
  public ResponseEntity<Object> teamPlayer(@PathVariable Long teamId, @PathVariable Long id,
      HttpServletRequest request) {
    User user = getCurrentUser();
    Optional<TeamPlayer> teamPlayer = teamPlayerService.getUserTeamPlayer(id, teamId, user);

    if (teamPlayer.isPresent()) {
      return ResponseEntity.ok().body(teamPlayer);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(notFoundResponseBody("Player not found.", request));
    }
  }

  @PatchMapping("/teams/{teamId}/players/{id}")
  @PreAuthorize("hasAnyAuthority('User')")
  public ResponseEntity<Object> update(@PathVariable Long teamId, @PathVariable Long id,
      @RequestBody PatchTeamPlayerRequest patchTeamPlayerRequest,
      HttpServletRequest request) {

    User user = getCurrentUser();
    Optional<TeamPlayer> teamPlayer = teamPlayerService.getUserTeamPlayer(id, teamId, user);
    if (teamPlayer.isPresent()) {
      return ResponseEntity.ok().body(teamPlayerService.patchTeam(patchTeamPlayerRequest, teamPlayer.get()));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(notFoundResponseBody("Team not found.", request));
    }
  }
}
