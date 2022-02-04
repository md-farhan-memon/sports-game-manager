package com.example.gamemanager.mappers;

import com.example.gamemanager.dtos.request.PatchUserRequest;
import com.example.gamemanager.models.User;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateUserFromDto(PatchUserRequest dto, @MappingTarget User entity);
}
