package com.example.gamemanager.commons;

import java.util.Random;

public class Constant {
  // User
  public static final String USER_ID_SEQ = "users_id_seq";

  // Team
  public static final String TEAM_ID_SEQ = "teams_id_seq";
  public static final Long TEAM_INITIAL_WALLET = 5000000l;

  // Player
  public static final String PLAYER_ID_SEQ = "players_id_seq";

  // Team Player
  public static final String TEAM_PLAYER_ID_SEQ = "team_players_id_seq";
  public static final int TEAM_PLAYER_INCREMENTAL_MIN = 10;
  public static final int TEAM_PLAYER_INCREMENTAL_MAX = 100;
  public static final Random RANDOM = new Random();

  // Transfer Request
  public static final String TRANSFER_REQUEST_ID_SEQ = "transfer_request_id_seq";

  // Transfer History
  public static final String TRANSFER_HISTORY_ID_SEQ = "transfer_history_id_seq";

  // Access Header
  public static final String ACCESS_TOKEN_HEADER = "accessToken";

  // Private Constructor
  private Constant() {
    throw new IllegalStateException("Utility class");
  }
}
