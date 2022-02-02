package com.example.gamemanager.services.db;

import java.util.Optional;

import com.example.gamemanager.models.User;
import com.example.gamemanager.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserDbService {

  @Autowired
  private UserRepository userRepository;

  public Page<User> findAll(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public User findbyId(Long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      return user.get();
    } else {
      return null;
    }
  }
}
