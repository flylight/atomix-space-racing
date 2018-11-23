package org.atomix.space.racing.galaxy.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomix.core.Atomix;
import io.atomix.core.profile.Profile;

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

}
