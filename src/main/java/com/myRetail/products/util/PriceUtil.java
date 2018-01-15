package com.myRetail.products.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceUtil {
    public static BigDecimal getTwoDecimalPlaceValue(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.CEILING);
    }
}
