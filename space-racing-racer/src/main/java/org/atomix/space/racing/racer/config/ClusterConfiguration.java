package org.atomix.space.racing.racer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.atomix.core.Atomix;
import io.atomix.core.profile.Profile;

@Configuration
public class ClusterConfiguration {

  @Bean
  public Atomix atomixInstance() {

    Atomix atomix = Atomix.builder()
        .withMemberId("racer")
        .withAddress("localhost", 8082)
        .withMulticastEnabled()
        .withProfiles(Profile.dataGrid())
        .build();

    atomix.start().join();

    return atomix;
  }

}
