package com.example.gamemanager.services.helper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.gamemanager.commons.Constant;
import com.example.gamemanager.commons.PlayerType;
import com.example.gamemanager.commons.Sport;
import com.example.gamemanager.dtos.request.PatchTeamRequest;
import com.example.gamemanager.mappers.TeamMapper;
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

  @Autowired
  private TeamMapper teamMapper;

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
    team.setWallet(Constant.TEAM_INITIAL_WALLET);
    teamDbService.save(team);
  }

  public Team patchTeam(PatchTeamRequest patchTeamRequest, Team team) {
    teamMapper.updateTeamFromDto(patchTeamRequest, team);
    return teamDbService.save(team);
  }

  public Optional<Team> getUserTeam(User user, Long teamId) {
    return teamDbService.findByIdAndUser(teamId, user.getId());
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
