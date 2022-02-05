package com.example.gamemanager.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.gamemanager.commons.Constant;
import com.example.gamemanager.commons.Country;
import com.example.gamemanager.commons.PlayerType;
import com.example.gamemanager.commons.Sport;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Validated
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team_players")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@SequenceGenerator(name = Constant.TEAM_PLAYER_ID_SEQ, sequenceName = Constant.TEAM_PLAYER_ID_SEQ, allocationSize = 1)
public class TeamPlayer {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.TEAM_PLAYER_ID_SEQ)
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

  @JsonIgnore
  @JoinColumn(name = "team_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Team team;

  @CreationTimestamp
  private LocalDateTime creationAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @SuppressWarnings("java:S107")
  public TeamPlayer(String firstName, String lastName, Country country, Integer age, Long value, PlayerType type,
      Sport sport, Team team) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.country = country;
    this.age = age;
    this.value = value;
    this.type = type;
    this.sport = sport;
    this.team = team;
  }

  public static TeamPlayer build(Player player, Team team) {
    return new TeamPlayer(
        player.getFirstName(),
        player.getLastName(),
        player.getCountry(),
        player.getAge(),
        player.getValue(),
        player.getType(),
        player.getSport(), team);
  }

  public Long improvedValueAfterTranser() {
    int increamentalPercentage = randomNumberBetween(Constant.TEAM_PLAYER_INCREMENTAL_MIN,
        Constant.TEAM_PLAYER_INCREMENTAL_MAX);
    return (this.getValue() + (this.getValue() * increamentalPercentage / 100));
  }

  // Private Methods
  private int randomNumberBetween(int min, int max) {
    return Constant.RANDOM.ints(min, max).findFirst().getAsInt();
  }
}
