package com.example.utils.mapper;

public interface BaseMapper<T, RQ> {
    public T toEntity(RQ request, String createdBy);
}
