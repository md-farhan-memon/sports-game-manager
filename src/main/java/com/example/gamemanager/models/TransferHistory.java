package com.example.gamemanager.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.gamemanager.commons.Constant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CreationTimestamp;
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
@Table(name = "transfer_histories")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@SequenceGenerator(name = Constant.TRANSFER_HISTORY_ID_SEQ, sequenceName = Constant.TRANSFER_HISTORY_ID_SEQ, allocationSize = 1)
public class TransferHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.TRANSFER_HISTORY_ID_SEQ)
  private Long id;

  @JoinColumn(name = "team_player_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private TeamPlayer teamPlayer;

  @JoinColumn(name = "transfer_request_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private TransferRequest transferRequest;

  @JoinColumn(name = "old_team_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Team oldTeam;

  @JoinColumn(name = "new_team_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Team newTeam;

  @CreationTimestamp
  private LocalDateTime creationAt;
}
