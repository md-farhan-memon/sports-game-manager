# Soccer Online Manager Game API

* RESTful API for a simple application where football/soccer fans will create fantasy teams and will be able to sell or buy players.
* User will be able to create an account and log in using the API.
* Each user will have one team to begin with (user is identified by an email)
* When the user is signed up, they will get a team of 20 players automatically generated:
  * 3 goalkeepers
  * 6 defenders
  * 6 midfielders
  * 5 attackers
* Each player has an initial value of $1,000,000.
* Each team has an additional $5,000,000 to buy other players.
* When logged in, a user can see their team and player information
* Team has the following information:
  * Team name and a country (can be edited)
  * Team value (sum of player values)
* Player has the following information
  * First name, last name, country (can be edited by a team owner)
  * Age (random number from 18 to 40) and market value
* A team owner can set the player on a transfer list
* When a user places a player on a transfer list, they must set the asking price/value for this player. This value is then listed on a market list. When another user/team buys this player, they must be bought for this price.
* Each user will be able to see all players on a transfer list.
* With each transfer, team budgets are updated.
* When a player is transferred to another team, their value is increased between 10 and 100 percent.

#
# Solution

## Dependencies
* Maven
* PostgreSQL

## Setup

1. Copy Configuration files

   ````
   $ cp src/main/resources/application.yml.example cp src/main/resources/application.yml

   $ cp src/main/resources/liquibase.properties.example cp src/main/resources/liquibase.properties
   ````

2. Create database
3. Replace following variables with relevant values
   * $DB_HOST - Database host
   * $DB_NAME - Database Name
   * $USERNAME - Database Username
   * $PASSWORD - Database Password

4. Make sure `spring.liquibase.enabled` is set to `true` before next step in `src/main/resources/application.yml`
5. Run the app, it will generate all required tables in the specified database
6. Seed Players data by running the SQL query from `src/main/resources/scripts/seedPlayers.sql`
7. Restart the application

## APIs

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/ff59e56dcc242d314596)
