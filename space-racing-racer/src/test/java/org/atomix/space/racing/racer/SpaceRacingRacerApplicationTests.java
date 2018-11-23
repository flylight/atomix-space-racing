package org.atomix.space.racing.racer;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.atomix.core.Atomix;
import io.atomix.core.profile.Profile;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpaceRacingRacerApplicationTests {

  @BeforeClass
  public static void initMaster() {
    Atomix atomix = Atomix.builder()
        .withMemberId("master")
        .withAddress("localhost", 8080)
        .withMulticastEnabled()
        .withProfiles(Profile.dataGrid(1))
        .build();

    atomix.start().join();
  }

  @Test
  public void contextLoads() {
  }

}
