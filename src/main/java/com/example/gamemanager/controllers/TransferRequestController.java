package com.example.gamemanager.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.gamemanager.dtos.request.TransferRequestRequest;
import com.example.gamemanager.models.Team;
import com.example.gamemanager.models.TeamPlayer;
import com.example.gamemanager.models.TransferRequest;
import com.example.gamemanager.models.User;
import com.example.gamemanager.services.db.TransferRequestDbService;
import com.example.gamemanager.services.helper.TeamPlayerService;
import com.example.gamemanager.services.helper.TeamService;
import com.example.gamemanager.services.helper.TransferRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransferRequestController extends BaseController {

  @Autowired
  private TransferRequestDbService transferRequestDbService;

  @Autowired
  private TeamPlayerService teamPlayerService;

  @Autowired
  private TransferRequestService transferRequestService;

  @Autowired
  private TeamService teamService;

  @GetMapping("/transferRequests/pending")
  @PreAuthorize("hasAnyAuthority('User')")
  public Page<TransferRequest> getAllPaginated(@PageableDefault(size = 10) Pageable pageable) {
    return transferRequestDbService.findAllPendingPaginated(pageable);
  }

  @PostMapping("/teams/{teamId}/players/{playerId}/transferRequest")
  @PreAuthorize("hasAnyAuthority('User')")
  public ResponseEntity<Object> create(@PathVariable Long teamId, @PathVariable Long playerId,
      @Valid @RequestBody TransferRequestRequest transferRequestRequest,
      HttpServletRequest request) {

    User user = getCurrentUser();
    Optional<TeamPlayer> teamPlayer = teamPlayerService.getUserTeamPlayer(playerId, teamId, user);
    if (teamPlayer.isPresent()) {
      return ResponseEntity.ok()
          .body(transferRequestService.placeRequest(transferRequestRequest, teamPlayer.get(), user));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(notFoundResponseBody("Team not found.", request));
    }
  }

  @PostMapping("teams/{teamId}/transferRequests/{id}/buy")
  @PreAuthorize("hasAnyAuthority('User')")
  public ResponseEntity<Object> buy(@PathVariable Long teamId, @PathVariable Long id, HttpServletRequest request) {
    User user = getCurrentUser();
    Optional<TransferRequest> transferRequest = transferRequestDbService.findByIdAndPending(id);
    Optional<Team> team = teamService.getUserTeam(user, teamId);

    if (transferRequest.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(notFoundResponseBody("Transfer Request not found.", request));
    } else if (team.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(notFoundResponseBody("Team not found.", request));
    } else if (transferRequestService.canBuy(transferRequest.get(), team.get())) {
      return ResponseEntity.ok()
          .body(transferRequestService.completeTransfer(transferRequest.get(), team.get()));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(notFoundResponseBody("Invalid Transfer Request.", request));
    }
  }

}
