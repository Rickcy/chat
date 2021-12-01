package com.demo.chat.application;

//Внутренние статусы приложения для разбора на фронте стандартных кодов хватает только для общих случаев
public enum AppHttpStatus {
    ACCESS_TOKEN_EXPIRED(1000),
    REFRESH_TOKEN_EXPIRED(1001);
    private final int value;

    AppHttpStatus(int statusCode) {
        this.value = statusCode;
    }

    public int value() {
        return this.value;
    }
}
