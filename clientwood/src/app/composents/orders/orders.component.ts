import { Component, OnInit } from '@angular/core';
import { Order } from "./model/orders-model";
import { OrdersService} from "./service/orders.service";

@Component({
  selector: 'order-map',
  templateUrl: './orders.component.html',
})
export class OrdersComponent implements OnInit {

  orders: Order[] = [];

  constructor(private orderService: OrdersService) { }

  ngOnInit(): void {
    this.getAllOrders();
  }

  getAllOrders(): void {
    this.orderService.getAllOrders()
      .subscribe(orders => this.orders = orders);
  }

}
