package com.roigcode.app.testsupport;

import java.util.Random;
import net.datafaker.Faker;

/**
 * Single entry point for Datafaker in tests. Every call returns a new Faker with the
 * same fixed seed, so each test gets the same values regardless of execution order.
 */
public final class TestFaker {

    public static final long SEED = 20261112L;

    private TestFaker() {}

    public static Faker create() {
        return new Faker(new Random(SEED));
    }
}
