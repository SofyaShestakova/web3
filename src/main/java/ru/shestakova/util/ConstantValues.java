package ru.shestakova.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstantValues {

  @Getter(lazy = true)
  private final double machineEpsilon = countMachineEpsilon();

  private double countMachineEpsilon() {
    double dTemp = 0.5D;

    while (1 + dTemp > 1) {
      dTemp /= 2;
    }

    return dTemp;
  }
}
