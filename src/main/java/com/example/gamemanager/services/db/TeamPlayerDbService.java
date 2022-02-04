package com.example.gamemanager.services.db;

import java.util.Optional;

import javax.transaction.Transactional;

import com.example.gamemanager.models.TeamPlayer;
import com.example.gamemanager.models.User;
import com.example.gamemanager.repositories.TeamPlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerDbService {

  @Autowired
  private TeamPlayerRepository teamPlayerRepository;

  @Transactional
  public TeamPlayer save(TeamPlayer teamPlayer) {
    return teamPlayerRepository.save(teamPlayer);
  }

  @Transactional
  public Optional<TeamPlayer> findByIdAndTeamAndUser(Long id, Long teamId, User user) {
    return teamPlayerRepository.findByIdAndTeam_idAndTeam_User(id, teamId, user);
  }
}
