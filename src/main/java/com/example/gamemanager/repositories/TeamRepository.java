package com.example.gamemanager.repositories;

import java.util.Optional;

import com.example.gamemanager.models.Team;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {
  Optional<Team> findByIdAndUser_Id(Long id, Long userId);

  Optional<Team> findById(Long id);
}
