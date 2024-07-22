package com.example;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

public class Constants {
    public static final Locale TEST_LOCALE = Locale.ENGLISH;
    public static final String TEST_KEY = "test.key";
    public static final String TEST_MESSAGE = "Test message";
    public static final String TEST_USERNAME = "test";
    public static final String TEST_USERNAME2 = "test.2";
    public static final String TEST_PASSWORD = "T3stP@ssw0rd";
    public static final String TEST_PASSWORD2 = "T3stP@ssw0rd2";
    public static final String TEST_VALUE = "test value";
    public static final String USER_API_URL = "/api/v1/users";
    public static final long TEST_ID = 1L;
    public static final Instant TEST_INSTANT =
            LocalDate.of(2024, 7, 16).atStartOfDay(ZoneId.systemDefault()).toInstant();
    public static final Instant TEST_INSTANT2 =
            LocalDate.of(2024, 7, 17).atStartOfDay(ZoneId.systemDefault()).toInstant();
}
