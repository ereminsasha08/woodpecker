alter table orders
    alter column stage set data type varchar(255);
update orders set stage = 'UNKNOWN' where stage = '0';
update orders set stage = 'NEW_ORDER' where stage = '1';
update orders set stage = 'SEQUENCING_CUT' where stage = '2';
update orders set stage = 'CUTTING' where stage = '3';
update orders set stage = 'WAITING_PAINT' where stage = '4';
update orders set stage = 'PAINTING' where stage = '5';
update orders set stage = 'WAITING_GLUE' where stage = '6';
update orders set stage = 'GLUE' where stage = '7';
update orders set stage = 'BEING_COMPLETED' where stage = '8';
update orders set stage = 'PACKAGING' where stage = '9';
update orders set stage = 'WAITING_SENT' where stage = '10';
update orders set stage = 'SENDING' where stage = '11';
update orders set stage = 'AVAILABILITY' where stage = '12';
update orders set stage = 'ORDERS_FROM_AVAILABILITY' where stage = '13';
update orders set stage = 'WAITING_PAINT_AVAILABILITY' where stage = '14';
update orders set stage = 'WAITING_GLUE_AVAILABILITY' where stage = '15';
update orders set stage = 'ORDER_FROM_AVAILABILITY_NO_PAINT' where stage = '16';
update orders set stage = 'ORDER_FROM_AVAILABILITY_NO_GLUE' where stage = '17';

