package org.example.lobby.domain.models.servicesModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaunchUrlFromGameRepo {

  private String url;
  private String launchProviderUrl;
  private String gameName;
  private String sessionKey;
  private String displayName;
  private String currencyName;
  private String hash;


}
