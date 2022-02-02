package com.example.gamemanager.dtos.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SigninRequest {

  @Email
  @NotNull
  @NotBlank
  private String email;

  @NotNull
  @NotBlank
  private String password;
}
