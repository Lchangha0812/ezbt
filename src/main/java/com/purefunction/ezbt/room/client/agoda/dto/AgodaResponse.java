package com.purefunction.ezbt.room.client.agoda.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

// TODO: @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY) 차이 반드시 알아둘것 반드시 시도도 해볼것
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface AgodaResponse permits AgodaSuccess, AgodaError {
    boolean isSuccess();
}