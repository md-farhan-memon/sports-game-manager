package com.example.gamemanager.dtos.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TransferRequestRequest {

  @NotNull
  @Min(1)
  private Long price;
}
