package com.epam.introduction.song_service.util;

import lombok.Getter;

public enum ResponseProperty {
    ID("id"),
    IDS("ids");

    @Getter
    private final String value;

    ResponseProperty(String value) {
        this.value = value;
    }
}
