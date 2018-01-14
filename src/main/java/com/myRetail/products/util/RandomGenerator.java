package com.myRetail.products.util;

import java.util.Random;

public class RandomGenerator {
    private static Random random = new Random();

    public static Integer getRandomInteger() {
        return random.nextInt(9000000) + 1000000;
    }
}
