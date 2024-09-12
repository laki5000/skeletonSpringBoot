package com.example.utils.dto.request;

import com.example.enums.FilterOperator;
import lombok.Builder;
import lombok.Getter;

/** DTO class for filtering. */
@Getter
@Builder
public class FilteringDTO {
    private String field;
    private FilterOperator operator;
    private String value;
    private String otherValue;
}
