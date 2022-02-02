package com.example.gamemanager.repositories;

import java.util.Set;

import com.example.gamemanager.models.Player;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

  @Query(value = "SELECT * FROM players where type = :type order by random() limit :count", nativeQuery = true)
  Set<Player> randomList(@Param("type") String type, @Param("count") int count);
}
