package com.example.gamemanager.commons;

public class Constant {
  // User
  public static final String USER_ID_SEQ = "users_id_seq";

  // Team
  public static final String TEAM_ID_SEQ = "teams_id_seq";

  // Player
  public static final String PLAYER_ID_SEQ = "players_id_seq";

  // Team Player
  public static final String TEAM_PLAYER_ID_SEQ = "team_players_id_seq";

  // Access Header
  public static final String ACCESS_TOKEN_HEADER = "accessToken";

  // Private Constructor
  private Constant() {
    throw new IllegalStateException("Utility class");
  }
}
