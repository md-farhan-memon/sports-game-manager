package com.example.gamemanager.mappers;

import com.example.gamemanager.dtos.request.PatchTeamRequest;
import com.example.gamemanager.models.Team;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TeamMapper {
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateTeamFromDto(PatchTeamRequest dto, @MappingTarget Team entity);
}
