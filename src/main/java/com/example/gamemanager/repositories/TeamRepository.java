package com.example.gamemanager.repositories;

import com.example.gamemanager.models.Team;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {
}
