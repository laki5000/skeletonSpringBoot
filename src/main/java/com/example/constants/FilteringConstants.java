package com.example.constants;

/** Constants used in filtering. */
public class FilteringConstants {
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_ID = "id";
    public static final String FIELD_DETAILS_ID = "detailsId";

    public static final String STRING = "String";
    public static final String INSTANT = "Instant";
    public static final String LONG = "Long";

    public static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    public static final String DATE_TIME_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
    public static final String DATE_TIME_WITH_MILLIS_REGEX =
            "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}Z";
    public static final String UTC = "UTC";

    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_LIMIT = "10";
}
