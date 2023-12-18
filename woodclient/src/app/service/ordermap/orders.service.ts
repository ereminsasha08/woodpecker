import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from '../../models/orders/orders.model';
import {environment} from "../../../environments/environment.development";
@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  private apiUrl = environment.backendUrl + '/orders';

  constructor(private http: HttpClient) { }

  getAllOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.apiUrl);
  }

  getOrderById(id: number): Observable<Order> {
    return this.http.get<Order>(`${this.apiUrl}/${id}`);
  }

  createOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(this.apiUrl, order);
  }

  updateOrder(id: number, order: Order): Observable<Order> {
    return this.http.put<Order>(`${this.apiUrl}/${id}`, order);
  }

  deleteOrder(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  // addOrderLine(orderId: number, orderLine: OrderLine): Observable<OrderLine> {
  //   return this.http.post<OrderLine>(`${this.apiUrl}/${orderId}/positions`, orderLine);
  // }
  //
  // updateOrderLine(orderId: number, lineId: number, orderLine: OrderLine): Observable<OrderLine> {
  //   return this.http.put<OrderLine>(`${this.apiUrl}/${orderId}/positions/${lineId}`, orderLine);
  // }

  deleteOrderLine(lineId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/positions/${lineId}`);
  }
}
