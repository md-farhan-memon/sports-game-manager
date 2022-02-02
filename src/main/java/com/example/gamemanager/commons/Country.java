package com.example.gamemanager.commons;

public enum Country {
  Argentina("Argentina"), Portugal("Portugal"), Spain("Spain"), Sweden("Sweden"), Colombia("Colombia"),
  Netherlands("Netherlands"), Italy("Italy"), Ivory_Coast("Ivory Coast"), Uruguay("Uruguay"), Brazil("Brazil"),
  Germany("Germany"), Belgium("Belgium"), France("France"), England("England"), Wales("Wales"),
  Czech_Republic("Czech Republic"), Cameroon("Cameroon"), Chile("Chile"), Serbia("Serbia"), Gabon("Gabon"),
  Poland("Poland"), Denmark("Denmark"), Croatia("Croatia"), Kenya("Kenya"), Zambia("Zambia"), Ghana("Ghana"),
  Armenia("Armenia"), Japan("Japan"), Slovakia("Slovakia");

  private final String displayName;

  Country(String displayName) {
    this.displayName = displayName;
  }

  public String displayName() {
    return displayName;
  }

  public static Country getByDisplayName(String displayName) {
    for (Country country : Country.values()) {
      if (displayName.equals(country.displayName())) {
        return country;
      }
    }

    return null;
  }
}
