package org.woodpecker.repository.model.goods.map;

public enum LanguageType {

    RUSSIAN("Русскиц"),
    ENGLISH("Английский"),
    ANOTHER("Другое");

    private final String name;

    LanguageType(String name) {
        this.name = name;
    }
}
