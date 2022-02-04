package com.example.gamemanager.dtos.request;

import com.example.gamemanager.commons.Country;

import lombok.Data;

@Data
public class PatchTeamPlayerRequest {
  private String firstName;
  private String lastName;
  private Country country;
  private Integer age;
}
