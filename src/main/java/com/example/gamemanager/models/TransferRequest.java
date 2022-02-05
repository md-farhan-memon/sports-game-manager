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
import javax.validation.constraints.NotNull;

import com.example.gamemanager.commons.Constant;
import com.example.gamemanager.commons.TransferRequestStatus;
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
@Table(name = "transfer_requests")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@SequenceGenerator(name = Constant.TRANSFER_REQUEST_ID_SEQ, sequenceName = Constant.TRANSFER_REQUEST_ID_SEQ, allocationSize = 1)
public class TransferRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.TRANSFER_REQUEST_ID_SEQ)
  private Long id;

  @JoinColumn(name = "team_player_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private TeamPlayer teamPlayer;

  @NotNull
  @Min(1)
  @Column(nullable = false)
  private Long price;

  @JoinColumn(name = "requester_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private User requester;

  @JoinColumn(name = "buyer_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private User buyer;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TransferRequestStatus status = TransferRequestStatus.Pending;

  @CreationTimestamp
  private LocalDateTime creationAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
