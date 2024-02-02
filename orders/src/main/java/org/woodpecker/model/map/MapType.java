package org.woodpecker.model.map;

public enum MapType {
    RUSSIA("Россия"),
    WORLD("Мир"),
    ANOTHER("Другое");

    private final String name;

    MapType(String name) {
        this.name = name;
    }
}
