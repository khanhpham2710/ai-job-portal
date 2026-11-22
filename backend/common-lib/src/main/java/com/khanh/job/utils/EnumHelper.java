package com.khanh.job.utils;

import com.khanh.job.domain.enums.BaseEnum;
import com.khanh.job.domain.enums.EnumValidationType;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jspecify.annotations.NonNull;

public final class EnumHelper {
  private EnumHelper() {}

  public static String toText(Enum<?> value, EnumValidationType type) {
    return switch (type) {
      case NAME -> value.name();
      case CODE -> {
        if (!(value instanceof BaseEnum baseEnum)) {
          throw new IllegalArgumentException("Enum must implement BaseEnum when using CODE");
        }
        yield baseEnum.getCode();
      }
      case ORDINAL -> String.valueOf(value.ordinal());
    };
  }

  public static Set<String> acceptedValues(Class<?> enumType, EnumValidationType type) {
    Set<String> result = new LinkedHashSet<>();
    Object[] constants = enumType.getEnumConstants();
    if (constants != null) {
      for (Object constant : constants) {
        result.add(toText((Enum<?>) constant, type));
      }
    }
    return result;
  }

  public static String invalidValue(
      @NonNull Object value, Class<?> enumType, EnumValidationType type) {
    String accepted = String.join(", ", acceptedValues(enumType, type));
    return "Invalid value '" + value + "'" + ". Accepted values: " + accepted;
  }
}
