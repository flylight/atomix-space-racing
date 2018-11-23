package org.atomix.space.racing.galaxy.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomix.core.Atomix;
import io.atomix.core.map.AtomicMap;
import io.atomix.core.profile.Profile;
import io.atomix.core.value.AtomicValue;

@Configuration
public class ResourceConfiguration {

  @Bean
  public AtomicValue<Boolean> initializeRaceStatusFlag(@Autowired Atomix atomix,
                                                       @Value("${race.status.variable.name}") String raceStatusVarName) {

    return atomix.<Boolean>atomicValueBuilder(raceStatusVarName)
        .build();
  }

  @Bean
  public AtomicMap<String, Integer> initializeRaceState(@Autowired Atomix atomix,
                                                        @Value("${race.state.variable.name}") String raceStateVarName) {

    return atomix.<String, Integer>atomicMapBuilder(raceStateVarName)
        .build();
  }

}
