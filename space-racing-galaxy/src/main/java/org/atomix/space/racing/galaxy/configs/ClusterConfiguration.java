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
public class ClusterConfiguration {

  @Bean
  public Atomix atomixInstance(@Value("${galaxy.name}") String galaxyName,
                               @Value("${galaxy.port}") int galaxyPort) {

    Atomix atomix = Atomix.builder()
        .withMemberId(galaxyName)
        .withAddress("localhost", galaxyPort)
        .withMulticastEnabled()
        .withProfiles(Profile.dataGrid(1))
        .build();

    atomix.start().join();

    return atomix;
  }

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
