package com.example;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

public class Constants {
    public static final Locale TEST_LOCALE = Locale.ENGLISH;

    public static final Instant TEST_INSTANT =
            LocalDate.of(2024, 7, 16).atStartOfDay(ZoneId.systemDefault()).toInstant();
    public static final Instant TEST_INSTANT2 =
            LocalDate.of(2024, 7, 17).atStartOfDay(ZoneId.systemDefault()).toInstant();

    public static final String TEST_KEY = "test.key";
    public static final String TEST_MESSAGE = "Test message";
    public static final String TEST_USERNAME = "test";
    public static final String TEST_USERNAME2 = "test.2";
    public static final String TEST_PASSWORD = "T3stP@ssw0rd";
    public static final String TEST_PASSWORD2 = "T3stP@ssw0rd2";
    public static final String TEST_INVALID_PASSWORD = "password";
    public static final String TEST_INVALID_SHORT_PASSWORD = "short";
    public static final String TEST_INVALID_LONG_PASSWORD =
            "12345678910111213141516171819202122232425262728293031323334353637";
    public static final String TEST_INVALID_DATE = "invalidDate";
    public static final String TEST_DATE = "2024-07-16";
    public static final String TEST_FIRST_NAME = "Test";
    public static final String TEST_FIRST_NAME2 = "Test2";
    public static final String TEST_LAST_NAME = "User";
    public static final String TEST_LAST_NAME2 = "User2";
    public static final String TEST_ORDER_BY = "id";
    public static final String TEST_ORDER_DIRECTION = "ASC";
    public static final String TEST_FIELD_PAGE = "page";
    public static final String TEST_FIELD_LIMIT = "limit";
    public static final String TEST_FIELD_ORDER_BY = "orderBy";
    public static final String TEST_FIELD_ORDER_DIRECTION = "orderDirection";
    public static final String TEST_FIELD_ID = "id";
    public static final String TEST_FIELD_PASSWORD = "password";
    public static final String TEST_FIELD_USERNAME = "username";
    public static final String TEST_FIELD_CREATED_AT = "createdAt";
    public static final String TEST_FIELD_UPDATED_AT = "updatedAt";
    public static final String TEST_FIELD_CREATED_BY = "createdBy";
    public static final String TEST_FIELD_DETAILS_ID = "details.id";
    public static final String TEST_FIELD_DETAILS_FIRST_NAME = "details.firstName";
    public static final String TEST_FIELD_DETAILS_LAST_NAME = "details.lastName";
    public static final String TEST_FIELD_DETAILS_CREATED_AT = "details.createdAt";
    public static final String TEST_FIELD_DETAILS_UPDATED_AT = "details.updatedAt";
    public static final String TEST_FIELD_DETAILS_CREATED_BY = "details.createdBy";

    public static final long TEST_ID = 1L;

    public static final int TEST_PAGE = 1;
    public static final int TEST_PAGE2 = 0;
    public static final int TEST_LIMIT = 10;
}
