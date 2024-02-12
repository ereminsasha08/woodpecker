import { HttpInterceptor, HttpRequest, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {LoginService} from "../login.service";

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private authenticationService: LoginService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    if (this.authenticationService.isUserLoggedIn() && req.url.indexOf('user') === -1) {
      const authReq = req.clone({
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `Basic ${window.btoa(this.authenticationService.loginModel?.username + ":" + this.authenticationService.loginModel?.password)}`
        })
      });
      return next.handle(authReq);
    } else {
      return next.handle(req);
    }
  }
}
