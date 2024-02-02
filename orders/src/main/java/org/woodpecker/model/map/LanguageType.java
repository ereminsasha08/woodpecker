package org.woodpecker.model.map;

public enum LanguageType {

    RUSSIAN("Русскиц"),
    ENGLISH("Английский"),
    ANOTHER("Другое");

    private final String name;

    LanguageType(String name) {
        this.name = name;
    }
}
