package org.atomix.space.racing.racer.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomix.core.Atomix;

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

}
