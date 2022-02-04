package com.example.gamemanager.mappers;

import com.example.gamemanager.dtos.request.PatchTeamPlayerRequest;
import com.example.gamemanager.models.TeamPlayer;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TeamPlayerMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateTeamPlayerFromDto(PatchTeamPlayerRequest dto, @MappingTarget TeamPlayer entity);
}
