package com.example.gamemanager.services.helper;

import com.example.gamemanager.dtos.request.PatchUserRequest;
import com.example.gamemanager.mappers.UserMapper;
import com.example.gamemanager.models.User;
import com.example.gamemanager.services.db.UserDbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserDbService userDbService;

  @Autowired
  private UserMapper userMapper;

  public User patchUser(PatchUserRequest patchUserRequest, User user) {
    userMapper.updateUserFromDto(patchUserRequest, user);
    return userDbService.save(user);
  }
}
