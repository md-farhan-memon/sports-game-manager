package com.example.gamemanager.security;

import com.example.gamemanager.models.User;
import com.example.gamemanager.services.db.UserDbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService implements UserDetailsService {

  @Autowired
  private UserDbService userDbService;

  @Override
  public CurrentUser loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userDbService.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

    return CurrentUser.build(user);
  }

}
