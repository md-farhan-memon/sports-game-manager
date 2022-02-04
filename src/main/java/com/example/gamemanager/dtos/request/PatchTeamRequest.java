package com.example.gamemanager.dtos.request;

import com.example.gamemanager.commons.Country;

import lombok.Data;

@Data
public class PatchTeamRequest {
  private String name;
  private Country country;
}
