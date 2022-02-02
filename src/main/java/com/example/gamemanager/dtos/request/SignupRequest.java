package com.example.gamemanager.dtos.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest {

  @NotNull
  @NotEmpty
  @NotBlank
  @Size(max = 250)
  private String firstName;

  @NotNull
  @NotEmpty
  @NotBlank
  @Size(max = 250)
  private String lastName;

  @Email
  @NotNull
  @NotEmpty
  @NotBlank
  @Size(max = 250)
  private String email;

  @NotNull
  @NotEmpty
  @NotBlank
  @Size(min = 8, max = 25)
  private String password;
}
