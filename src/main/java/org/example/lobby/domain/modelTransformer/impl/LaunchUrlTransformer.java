package org.example.lobby.domain.modelTransformer.impl;

import org.example.lobby.domain.modelTransformer.ModelTransformer;
import org.example.lobby.domain.models.servicesModels.LaunchUrlFromGameRepo;
import org.example.lobby.web.modelsDTO.LaunchUrlDto;
import org.springframework.stereotype.Component;

@Component
public class LaunchUrlTransformer implements ModelTransformer<LaunchUrlFromGameRepo, LaunchUrlDto> {

  @Override
  public LaunchUrlDto asDto(LaunchUrlFromGameRepo launchUrlFromGameRepo) {
    return new LaunchUrlDto(launchUrlFromGameRepo.getUrl());
  }


}
