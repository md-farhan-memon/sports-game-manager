package com.example.gamemanager.services.helper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.gamemanager.commons.PlayerType;
import com.example.gamemanager.commons.Sport;
import com.example.gamemanager.models.Player;
import com.example.gamemanager.models.Team;
import com.example.gamemanager.models.TeamPlayer;
import com.example.gamemanager.models.User;
import com.example.gamemanager.services.db.PlayerDbService;
import com.example.gamemanager.services.db.TeamDbService;
import com.example.gamemanager.services.db.UserDbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

  @Autowired
  private UserDbService userDbService;

  @Autowired
  private PlayerDbService playerDbService;

  @Autowired
  private TeamDbService teamDbService;

  @Async
  public void generateNewTeam(Long userId) {
    User user = userDbService.findbyId(userId);
    Team team = new Team();
    team.setUser(user);
    team.setSport(Sport.Soccer);
    team.setName("Team " + user.getFirstName());
    team.setPlayers(generateRandomPlayers(team));
    team.setCountry(team.findDominantCountry());
    team.setValue(team.calculateTeamValue());
    teamDbService.save(team);
  }

  // Private Methods
  private Set<TeamPlayer> generateRandomPlayers(Team team) {
    Set<Player> players = new HashSet<>();
    players.addAll(playerDbService.randomList(PlayerType.Goalkeeper, 3));
    players.addAll(playerDbService.randomList(PlayerType.Defender, 6));
    players.addAll(playerDbService.randomList(PlayerType.Midfielder, 6));
    players.addAll(playerDbService.randomList(PlayerType.Attacker, 5));

    return players.stream().map(player -> TeamPlayer.build(player, team)).collect(Collectors.toSet());
  }
}
