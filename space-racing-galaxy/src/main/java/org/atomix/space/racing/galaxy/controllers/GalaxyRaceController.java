package org.atomix.space.racing.galaxy.controllers;

import org.atomix.space.racing.galaxy.services.GalaxyRaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/galaxy")
@AllArgsConstructor
public class GalaxyRaceController {

  private final GalaxyRaceService galaxyRaceService;

  @GetMapping("/race/state")
  public Map<String, Integer> getRaceState() {

    return galaxyRaceService.getRaceState();
  }

  @PostMapping("/race/start")
  public void startRace() {

    galaxyRaceService.startRace();
  }

  @PostMapping("/race/stop")
  public void stopRace() {

    galaxyRaceService.stopRace();
  }

  @PostMapping("/race/reset")
  public void resetRace() {

    galaxyRaceService.resetRace();
  }
}
