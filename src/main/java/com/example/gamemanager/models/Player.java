package com.example.gamemanager.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.gamemanager.commons.Constant;
import com.example.gamemanager.commons.Country;
import com.example.gamemanager.commons.Sport;
import com.example.gamemanager.commons.PlayerType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Validated
@Table(name = "players")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@SequenceGenerator(name = Constant.PLAYER_ID_SEQ, sequenceName = Constant.PLAYER_ID_SEQ, allocationSize = 1)
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.PLAYER_ID_SEQ)
  private Long id;

  @NotNull
  @NotBlank
  @Size(max = 250)
  @Column(nullable = false)
  private String firstName;

  @NotNull
  @NotBlank
  @Size(max = 250)
  @Column(nullable = false)
  private String lastName;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Country country;

  @NotNull
  @Min(1)
  @Column(nullable = false)
  private Integer age;

  @NotNull
  @Min(1)
  @Column(nullable = false)
  private Long value;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private PlayerType type;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Sport sport = Sport.Soccer;
}
