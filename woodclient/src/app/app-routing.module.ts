import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {OrdersComponent} from "./composents/orders/orders.component";
import {HttpClientModule} from "@angular/common/http";
import {OrdersService} from "./service/ordermap/orders.service";
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {HomeComponent} from "./composents/home/home.component";
import {LoginComponent} from "./composents/login/login.component";
import {LoginService} from "./service/login/login.service";

@NgModule({
  declarations: [
    OrdersComponent,
    HomeComponent,
    LoginComponent
  ],
  imports: [
    HttpClientModule,
    FormsModule,
    NgForOf
  ],
  exports: [
    RouterModule,
    OrdersComponent
  ],
  providers: [
    OrdersService,
    LoginService
  ]
})
export class AppRoutingModule {
}
