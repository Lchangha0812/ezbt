package com.purefunction.ezbt.accommodation.client.agoda.dto;

public record AgodaError(Integer id, String message) implements AgodaResponse {
    @Override
    public boolean isSuccess() {
        return false;
    }
}
