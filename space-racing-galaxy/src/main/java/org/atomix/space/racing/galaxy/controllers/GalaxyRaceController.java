package org.atomix.space.racing.galaxy.controllers;

import org.atomix.space.racing.galaxy.services.GalaxyRaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.atomix.core.map.AtomicMap;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/galaxy")
@AllArgsConstructor
public class GalaxyRaceController {

  private final GalaxyRaceService galaxyRaceService;

  @GetMapping("/race/state")
  public AtomicMap<String, Integer> getRaceState() {

    return galaxyRaceService.getRaceState();
  }

}
