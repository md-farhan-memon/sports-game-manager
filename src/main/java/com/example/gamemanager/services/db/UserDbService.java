package com.example.gamemanager.services.db;

import java.util.Optional;

import javax.transaction.Transactional;

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

  @Transactional
  public Page<User> findAll(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  @Transactional
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }

  @Transactional
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Transactional
  public User findbyId(Long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      return user.get();
    } else {
      return null;
    }
  }
}
