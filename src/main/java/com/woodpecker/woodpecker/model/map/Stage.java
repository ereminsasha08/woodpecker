package com.woodpecker.woodpecker.model.map;

public enum Stage {

    НЕИЗВЕСТНО,
    НОВЫЙ_ЗАКАЗ,
    В_ОЧЕРЕДИ_НА_РЕЗКУ,
    ПИЛИТСЯ,
    ЖДЕТ_ПОКРАСКИ,
    КРАСИТСЯ,
    ЖДЕТ_ПРИКЛЕЙКИ,
    ПРИКЛЕИВАЕТСЯ,
    ДОДЕЛЫВАЕТСЯ,
    ЗАПАКОВЫВАЕТСЯ,
    ГОТОВ_К_ОТПРАВКЕ,
    ОТПРАВЛЕН,
    НАЛИЧИЕ,
    ЗАКАЗ_ИЗ_НАЛИЧИЯ,

    ЖДУ_ПОКРАСКУ_НАЛИЧИЕ,

    ЖДУ_ПРИКЛЕЙКУ_НАЛИЧИЕ,

    ЗАКАЗ_ИЗ_НАЛИЧИЯ_НЕ_ПОКРАШЕННЫЙ,

    ЗАКАЗ_ИЗ_НАЛИЧИЯ_НЕ_ПРИКЛЕЕННЫЙ

}
