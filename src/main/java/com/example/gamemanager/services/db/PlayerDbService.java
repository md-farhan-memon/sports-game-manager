package com.example.gamemanager.services.db;

import java.util.Set;

import javax.transaction.Transactional;

import com.example.gamemanager.commons.PlayerType;
import com.example.gamemanager.models.Player;
import com.example.gamemanager.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerDbService {

  @Autowired
  private PlayerRepository playerRepository;

  @Transactional
  public Set<Player> randomList(PlayerType type, int count) {
    return playerRepository.randomList(type.name(), count);
  }
}
