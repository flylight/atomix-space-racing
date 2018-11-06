package org.atomix.space.racing.galaxy.services;

import org.springframework.stereotype.Service;

import io.atomix.core.map.AsyncAtomicMap;
import io.atomix.core.map.AtomicMap;
import io.atomix.core.value.AsyncAtomicValue;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GalaxyRaceService {

  private final AsyncAtomicValue<Boolean> raceStatus;

  private final AsyncAtomicMap<String, Integer> raceState;

  public AtomicMap<String, Integer> getRaceState() {
    return raceState.sync();
//    Map<String, Integer> state = new HashMap<>();
//
//    raceState.entrySet().forEach(entry -> state.put(entry.getKey(), entry.getValue().value()));
//
//    return state;
  }
}
