package com.example.gamemanager.services.db;

import java.util.Optional;

import javax.transaction.Transactional;

import com.example.gamemanager.models.Team;
import com.example.gamemanager.repositories.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamDbService {

  @Autowired
  private TeamRepository teamRepository;

  @Transactional
  public Team save(Team team) {
    return teamRepository.save(team);
  }

  @Transactional
  public Optional<Team> findByIdAndUser(Long id, Long userId) {
    return teamRepository.findByIdAndUser_Id(id, userId);
  }
}
