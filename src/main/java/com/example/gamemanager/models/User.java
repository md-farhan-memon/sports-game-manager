package com.example.gamemanager.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.gamemanager.commons.Constant;
import com.example.gamemanager.commons.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Validated
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@SequenceGenerator(name = Constant.USER_ID_SEQ, sequenceName = Constant.USER_ID_SEQ, allocationSize = 1)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constant.USER_ID_SEQ)
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

  @Email
  @NotNull
  @NotBlank
  @Size(max = 250)
  @Column(nullable = false, unique = true)
  private String email;

  @JsonIgnore
  @ToString.Exclude
  @NotNull
  @NotBlank
  @Column(nullable = false)
  private String password;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role = Role.User;

  @JsonIgnore
  @ToString.Exclude
  private String accessToken;

  @CreationTimestamp
  private LocalDateTime creationAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<Team> teams = new HashSet<>();

  public User(String firstName, String lastName, String email, String password, Role role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
  }
}
