package com.example.gamemanager.repositories;

import java.util.Optional;

import com.example.gamemanager.models.TeamPlayer;
import com.example.gamemanager.models.User;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeamPlayerRepository extends PagingAndSortingRepository<TeamPlayer, Long> {

  Optional<TeamPlayer> findByIdAndTeam_idAndTeam_User(Long id, Long teamId, User user);
}
