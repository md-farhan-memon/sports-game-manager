package com.example.gamemanager.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailedResponse {
  private int status;
  private String error;
  private String message;
  private String path;
}
