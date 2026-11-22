package com.khanh.job.utils;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

public final class UUIDGenerator {
  private UUIDGenerator() {}

  private static final TimeBasedEpochGenerator UUID_V7 = Generators.timeBasedEpochGenerator();

  public static @NonNull UUID generateUUID() {
    return UUID_V7.generate();
  }
}
