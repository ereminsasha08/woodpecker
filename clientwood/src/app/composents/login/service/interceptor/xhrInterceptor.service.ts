import {Injectable} from "@angular/core";
import {HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {

    const xhr = req.clone({
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest',
            authorization: 'Basic ' + btoa('user@gmail.com' + ':' + 'password')
          }
        )
      }
    );
    return next.handle(xhr);
  }
}
