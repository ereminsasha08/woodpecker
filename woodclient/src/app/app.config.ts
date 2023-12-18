import {ApplicationConfig} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideClientHydration} from '@angular/platform-browser';
import {OrdersService} from "./service/ordermap/orders.service";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {XhrInterceptor} from "./service/interceptor/xhrInterceptor.service";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideClientHydration(),
    OrdersService,
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}
    // { provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true }
  ]
};
