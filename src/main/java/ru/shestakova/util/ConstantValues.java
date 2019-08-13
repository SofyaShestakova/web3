package ru.shestakova.util;
public class ConstantValues {

    public static final double DOUBLE_MACHINE_EPSILON;

    static {
        double dTemp = 0.5D;

        while (1 + dTemp > 1) {
            dTemp /= 2;
        }

        DOUBLE_MACHINE_EPSILON = dTemp;
    }
}
