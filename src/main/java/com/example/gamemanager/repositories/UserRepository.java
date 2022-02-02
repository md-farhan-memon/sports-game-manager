package com.example.gamemanager.repositories;

import java.util.Optional;

import com.example.gamemanager.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
  Page<User> findAll(Pageable pageable);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}
