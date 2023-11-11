package com.woodpecker.woodpecker.model.map;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Stage {

    UNKNOWN("Неизвестно", 0),
    NEW_ORDER("Новый заказ", 1),
    SEQUENCING_CUT("Очередь на резку", 2),
    CUTTING("Пилится", 3),
    WAITING_PAINT("Ждет покраски", 4),
    PAINTING("Красится", 5),
    WAITING_GLUE("Ждет приклейки", 6),
    GLUE("Приклеивается", 7),
    BEING_COMPLETED("Доделывается", 8),
    PACKAGING("Запаковывается", 9),
    WAITING_SENT("Ждет отправки", 10),
    SENDING("Отправлен", 11),
    AVAILABILITY("Наличие", 12),
    ORDERS_FROM_AVAILABILITY("Заказ из наличия", 13),
    WAITING_PAINT_AVAILABILITY("Жду покраску наличие", 14),
    WAITING_GLUE_AVAILABILITY("Жду приклейку наличие", 15),
    ORDER_FROM_AVAILABILITY_NO_PAINT("Заказ из наличия не покрашен", 16),
    ORDER_FROM_AVAILABILITY_NO_GLUE("Заказ из наличия не приклеен", 17);

    private final String description;
    private final int ordersOperation;


    Stage(String description, int ordersOperation) {
        this.description = description;
        this.ordersOperation = ordersOperation;
    }

}
