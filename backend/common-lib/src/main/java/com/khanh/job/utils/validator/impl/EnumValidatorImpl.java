package com.khanh.job.utils.validator.impl;

import com.khanh.job.domain.enums.EnumValidationType;
import com.khanh.job.utils.EnumHelper;
import com.khanh.job.utils.validator.EnumValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;
import org.jspecify.annotations.Nullable;
import org.springframework.util.StringUtils;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, Object> {

  private @Nullable Class<?> configuredType;
  private boolean nullable = true;
  private EnumValidationType validationType = EnumValidationType.NAME;

  @Override
  public void initialize(EnumValidator annotation) {
    this.nullable = annotation.nullable();
    this.validationType = annotation.enumValidationType();

    Class<?> type = annotation.enumClass();

    this.configuredType = type == EnumValidator.Unspecified.class ? null : type;
  }

  @Override
  public boolean isValid(@Nullable Object value, ConstraintValidatorContext context) {
    if (value == null) {
      if (nullable) {
        return true;
      }

      replaceMessage(context, "Value must not be null");
      return false;
    }

    Class<?> enumType;
    String actual;

    if (value instanceof Enum<?> enumValue) {
      enumType = configuredType != null ? configuredType : enumValue.getDeclaringClass();
      actual = EnumHelper.toText(enumValue, validationType);
    } else {
      if (configuredType == null) {
        throw new IllegalStateException(
            "@EnumValidator requires enumClass when the field is not an enum");
      }

      enumType = configuredType;
      actual = value.toString();
    }

    Set<String> accepted = EnumHelper.acceptedValues(enumType, validationType);

    if (accepted.contains(actual)) {
      return true;
    }

    if (!StringUtils.hasText(context.getDefaultConstraintMessageTemplate())) {
      replaceMessage(context, EnumHelper.invalidValue(actual, enumType, validationType));
    }

    return false;
  }

  private static void replaceMessage(ConstraintValidatorContext context, String message) {
    context.disableDefaultConstraintViolation();

    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
  }
}
