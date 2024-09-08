package com.example.utils.dto.request;

import com.example.utils.enums.FilterOperator;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FilteringDTO {
    private String field;
    private FilterOperator operator;
    private String value;
    private String otherValue;
}
