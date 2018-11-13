package org.atomix.space.racing.racer.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomix.core.Atomix;
import io.atomix.core.map.AsyncAtomicMap;
import io.atomix.core.value.AsyncAtomicValue;

@Configuration
public class ClusterConfiguration {

  @Bean
  public Atomix atomixInstance(@Value("${racer.name}") String racerName,
                               @Value("${racer.port}") int racerPort) {

    Atomix atomix = Atomix.builder()
        .withMemberId(racerName)
        .withAddress("localhost", racerPort)
        .withMulticastEnabled()
        .build();

    atomix.start().join();

    return atomix;
  }

  @Bean
  public AsyncAtomicValue<Boolean> getRaceStatusFlag(@Autowired Atomix atomix,
                                                     @Value("${race.status.variable.name}") String statusVarName) {

    return atomix.<Boolean>getAtomicValue(statusVarName).async();
  }

  @Bean
  public AsyncAtomicMap<String, Integer> getRaceState(@Autowired Atomix atomix,
                                                      @Value("${race.state.variable.name}") String raceVarName) {

    return atomix.<String, Integer>getAtomicMap(raceVarName).async();
  }
}
