package com.v1ok.commons.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.ArrayUtils;

public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {

  public static BigDecimal add(Number... numbers) {
    return add(3, RoundingMode.HALF_UP, numbers);
  }

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

  public static BigDecimal subtract(Number... numbers) {
    return subtract(3, RoundingMode.HALF_UP, numbers);
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

  public static BigDecimal multiply(Number... numbers) {
    return multiply(3, RoundingMode.HALF_UP, numbers);
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

  public static BigDecimal divide(Number... numbers) {
    return divide(3, RoundingMode.HALF_UP, numbers);
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

  /**
   * 大于判断，num1 > num2 返回 True
   *
   * @param num1 比较数
   * @param num2 比较数
   * @return num1 > num2 返回 True 否则返回 False
   */
  public static boolean greater(BigDecimal num1, BigDecimal num2) {
    int result = num1.compareTo(num2);
    return result >= 1;
  }

  /**
   * 小于判断，num1 < num2 返回 True
   *
   * @param num1 比较数
   * @param num2 比较数
   * @return num1 > num2 返回 True 否则返回 False
   */
  public static boolean less(BigDecimal num1, BigDecimal num2) {
    int result = num1.compareTo(num2);
    return result <= -1;
  }

  /**
   * 等于判断，num1 == num2 返回 True
   *
   * @param num1 比较数
   * @param num2 比较数
   * @return num1 > num2 返回 True 否则返回 False
   */
  public static boolean equal(BigDecimal num1, BigDecimal num2) {
    int result = num1.compareTo(num2);
    return result == 0;
  }

  /**
   * 区间比较
   *
   * @param num 操作数
   * @param min 最少值
   * @param max 最大值
   * @return if(min < num < = max) true
   */
  public static boolean between(BigDecimal num, BigDecimal min, BigDecimal max) {
    return greater(num, min) && (less(num, max) || equal(num, max));
  }
}
