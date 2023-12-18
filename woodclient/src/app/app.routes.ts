import {Routes} from '@angular/router';
import {OrdersComponent} from "./composents/orders/orders.component";
import {HomeComponent} from "./composents/home/home.component";
import {LoginComponent} from "./composents/login/login.component";

export const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'home'},
  {path: 'home', component: HomeComponent},
  {path: 'orders', component: OrdersComponent},
  {path: 'login', component: LoginComponent}
];
