package com.woodpecker.model.map;

public enum LightType {

    WITHOUT("Без"),
    WIRE("Проводная"),
    WITHOUT_WIRE("Беспроводная"),
    ANOTHER("Другое");

    private final String name;

    LightType(String name) {
        this.name = name;
    }
}
