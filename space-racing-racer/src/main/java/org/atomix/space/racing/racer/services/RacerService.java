package org.atomix.space.racing.racer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import io.atomix.core.map.AsyncAtomicMap;
import io.atomix.core.value.AsyncAtomicValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RacerService {

  private static final int MAX_RACER_STEP = 3;
  private static final int DELAY_IN_SECONDS = 10;

  private final AsyncAtomicValue<Boolean> remoteRaceStatus;
  private final AsyncAtomicMap<String, Integer> remoteRaceState;

  @Value("${racer.name}")
  private String racerName;

  private AtomicBoolean localRaceStatus = new AtomicBoolean(false);
  private ScheduledExecutorService engine = Executors.newScheduledThreadPool(1);
  private SecureRandom random = new SecureRandom();

  @PostConstruct
  private void initializeStateListener() {

    remoteRaceStatus.addListener(event -> startOrStopRace(event.newValue()));

    remoteRaceState.put(racerName, 0)
        .whenComplete((integerVersioned, throwable) -> log.info("Racer {} registered!", racerName));
  }

  private void startOrStopRace(boolean raceStatus) {

    if (raceStatus)

      startRace();
    else

      stopRace();
  }

  private void stopRace() {

    if (localRaceStatus.get()) {
      log.info("Racer {} stopped!", racerName);

      localRaceStatus.set(false);
    }
  }

  private void startRace() {

    if (!localRaceStatus.get()) {
      log.info("Racer {} started!", racerName);

      localRaceStatus.set(true);

      scheduleNextStep();
    }
  }

  private void scheduleNextStep() {
    engine.schedule(this::doNextStep, DELAY_IN_SECONDS, TimeUnit.MILLISECONDS);
  }

  private void doNextStep() {
    remoteRaceState.get(racerName).whenComplete((integerVersioned, throwable) -> {

      int updatedRaceState = integerVersioned.value() + random.nextInt(MAX_RACER_STEP);

      log.info("Race state: {}", updatedRaceState);

      remoteRaceState.put(racerName, updatedRaceState).whenComplete((integerVersioned1, throwable1) -> log.info("Racer state updated!"));

      if (localRaceStatus.get()) {
        scheduleNextStep();
      }
    });
  }
}
