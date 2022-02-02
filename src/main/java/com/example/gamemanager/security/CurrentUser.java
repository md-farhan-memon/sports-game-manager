package com.example.gamemanager.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.gamemanager.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class CurrentUser implements UserDetails {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  @JsonIgnore
  private String password;

  private String accessToken;

  private Collection<? extends GrantedAuthority> authorities;

  public CurrentUser(Long id, String firstName, String lastName, String email, String password, String accessToken,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.accessToken = accessToken;
  }

  public static CurrentUser build(User user) {
    List<GrantedAuthority> authorities = Arrays.asList(user.getRole()).stream()
        .map(role -> new SimpleGrantedAuthority(role.name()))
        .collect(Collectors.toList());

    return new CurrentUser(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getPassword(),
        user.getAccessToken(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    CurrentUser user = (CurrentUser) o;
    return Objects.equals(id, user.id);
  }

}
