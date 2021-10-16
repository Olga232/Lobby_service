package org.example.lobby.domain.models.servicesModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDetails {

  private String displayName;
  private String countryName;
  private String currencyName;



}
