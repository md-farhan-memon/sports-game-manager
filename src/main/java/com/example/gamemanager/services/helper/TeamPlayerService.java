package com.example.gamemanager.services.helper;

import java.util.Optional;

import com.example.gamemanager.dtos.request.PatchTeamPlayerRequest;
import com.example.gamemanager.mappers.TeamPlayerMapper;
import com.example.gamemanager.models.TeamPlayer;
import com.example.gamemanager.models.User;
import com.example.gamemanager.services.db.TeamPlayerDbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerService {

  @Autowired
  private TeamPlayerDbService teamPlayerDbService;

  @Autowired
  private TeamPlayerMapper teamPlayerMapper;

  public Optional<TeamPlayer> getUserTeamPlayer(Long id, Long teamId, User user) {
    return teamPlayerDbService.findByIdAndTeamAndUser(id, teamId, user);
  }

  public TeamPlayer patchTeam(PatchTeamPlayerRequest patchTeamPlayerRequest, TeamPlayer teamPlayer) {
    teamPlayerMapper.updateTeamPlayerFromDto(patchTeamPlayerRequest, teamPlayer);
    return teamPlayerDbService.save(teamPlayer);
  }
}
