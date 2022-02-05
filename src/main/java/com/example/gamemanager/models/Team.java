package com.example.gamemanager.models;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.gamemanager.commons.Constant;
import com.example.gamemanager.commons.Country;
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
@Table(name = "teams")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@SequenceGenerator(name = Constant.TEAM_ID_SEQ, sequenceName = Constant.TEAM_ID_SEQ, allocationSize = 1)
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.TEAM_ID_SEQ)
  private Long id;

  @JsonIgnore
  @JoinColumn(name = "user_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Sport sport = Sport.Soccer;

  @NotNull
  @Column(nullable = false)
  @Size(max = 250)
  private String name;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Country country;

  @NotNull
  @Min(1)
  @Column(nullable = false)
  private Long value;

  @NotNull
  @Min(1)
  @Column(nullable = false, columnDefinition = "BIGINT default 5000000")
  private Long wallet = Constant.TEAM_INITIAL_WALLET;

  @CreationTimestamp
  private LocalDateTime creationAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<TeamPlayer> players = new HashSet<>();

  public Country findDominantCountry() {
    Map<Country, Integer> occurences = new EnumMap<>(Country.class);
    List<Country> countries = players.stream().map(TeamPlayer::getCountry).collect(Collectors.toList());
    countries.forEach(c -> occurences.put(c, Collections.frequency(countries, c)));
    Set<Country> sortedCountries = occurences.entrySet().stream().sorted(Entry.comparingByValue())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
            (e1, e2) -> e1, LinkedHashMap::new))
        .keySet();
    return Country.valueOf(sortedCountries.toArray()[sortedCountries.size() - 1].toString());
  }

  public Long calculateTeamValue() {
    Long sum = 0l;
    for (TeamPlayer teamPlayer : players) {
      sum += teamPlayer.getValue();
    }

    return sum;
  }
}
