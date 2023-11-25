alter table order
    alter column stage set data type varchar(255);
update order set stage = 'UNKNOWN' where stage = '0';
update order set stage = 'NEW_ORDER' where stage = '1';
update order set stage = 'SEQUENCING_CUT' where stage = '2';
update order set stage = 'CUTTING' where stage = '3';
update order set stage = 'WAITING_PAINT' where stage = '4';
update order set stage = 'PAINTING' where stage = '5';
update order set stage = 'WAITING_GLUE' where stage = '6';
update order set stage = 'GLUE' where stage = '7';
update order set stage = 'BEING_COMPLETED' where stage = '8';
update order set stage = 'PACKAGING' where stage = '9';
update order set stage = 'WAITING_SENT' where stage = '10';
update order set stage = 'SENDING' where stage = '11';
update order set stage = 'AVAILABILITY' where stage = '12';
update order set stage = 'ORDERS_FROM_AVAILABILITY' where stage = '13';
update order set stage = 'WAITING_PAINT_AVAILABILITY' where stage = '14';
update order set stage = 'WAITING_GLUE_AVAILABILITY' where stage = '15';
update order set stage = 'ORDER_FROM_AVAILABILITY_NO_PAINT' where stage = '16';
update order set stage = 'ORDER_FROM_AVAILABILITY_NO_GLUE' where stage = '17';

