package com.v1ok.commons.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.ArrayUtils;

public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {


  public static BigDecimal add(final int scale, final RoundingMode roundingMode,
      Number... numbers) {
    BigDecimal bigDecimal = null;

    if (ArrayUtils.isNotEmpty(numbers)) {
      for (int i = 0; i < numbers.length; i++) {
        if (i == 0) {
          bigDecimal = NumberUtils
              .toScaledBigDecimal(numbers[i].doubleValue(), scale, roundingMode);
          continue;
        }

        bigDecimal = bigDecimal.add(NumberUtils
            .toScaledBigDecimal(numbers[i].doubleValue(), scale, roundingMode));
      }
    }
    return NumberUtils.toScaledBigDecimal(bigDecimal, scale, roundingMode);
  }

  public static BigDecimal subtract(final int scale, final RoundingMode roundingMode,
      Number... numbers) {
    BigDecimal bigDecimal = null;

    if (ArrayUtils.isNotEmpty(numbers)) {
      for (int i = 0; i < numbers.length; i++) {
        if (i == 0) {
          bigDecimal = NumberUtils
              .toScaledBigDecimal(numbers[i].doubleValue(), scale, roundingMode);
          continue;
        }

        bigDecimal = bigDecimal.subtract(NumberUtils
            .toScaledBigDecimal(numbers[i].doubleValue(), scale, roundingMode));
      }
    }
    return NumberUtils.toScaledBigDecimal(bigDecimal, scale, roundingMode);
  }

  public static BigDecimal multiply(final int scale, final RoundingMode roundingMode,
      Number... numbers) {
    BigDecimal bigDecimal = null;

    if (ArrayUtils.isNotEmpty(numbers)) {
      for (int i = 0; i < numbers.length; i++) {
        if (i == 0) {
          bigDecimal = NumberUtils
              .toScaledBigDecimal(numbers[i].doubleValue(), scale, roundingMode);
          continue;
        }

        bigDecimal = bigDecimal.multiply(NumberUtils
            .toScaledBigDecimal(numbers[i].doubleValue(), scale, roundingMode));
      }
    }
    return NumberUtils.toScaledBigDecimal(bigDecimal, scale, roundingMode);
  }

  public static BigDecimal divide(final int scale, final RoundingMode roundingMode,
      Number... numbers) {
    BigDecimal bigDecimal = null;

    if (ArrayUtils.isNotEmpty(numbers)) {
      for (int i = 0; i < numbers.length; i++) {
        if (i == 0) {
          bigDecimal = NumberUtils
              .toScaledBigDecimal(numbers[i].doubleValue(), scale, roundingMode);
          continue;
        }

        bigDecimal = bigDecimal.divide(NumberUtils
            .toScaledBigDecimal(numbers[i].doubleValue()), scale, roundingMode);
      }
    }
    return NumberUtils.toScaledBigDecimal(bigDecimal, scale, roundingMode);
  }


}
