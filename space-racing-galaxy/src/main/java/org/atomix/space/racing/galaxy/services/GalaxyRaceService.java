package org.atomix.space.racing.galaxy.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

import io.atomix.core.map.AtomicMap;
import io.atomix.core.value.AtomicValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GalaxyRaceService {

  private final AtomicValue<Boolean> raceStatus;

  private final AtomicMap<String, Integer> raceState;


  @Value("${race.state.variable.name")
  private String raceStateVarName;


  public Map<String, Integer> getRaceState() {

    return raceState.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, o -> o.getValue().value()));
  }

  public void startRace() {

    raceStatus.set(true);
  }

  public void stopRace() {

    raceStatus.set(false);
  }

  public void resetRace() {

    raceState.keySet().forEach(racerName -> raceState.put(racerName, 0));
  }
}
