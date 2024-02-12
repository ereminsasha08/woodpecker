import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {LoginService} from "./service/login.service";
import {LoginModel} from "./model/login-model";

@Component({
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {

  error: any;

  loginModel: LoginModel

  ngOnInit(): void {
  }

  constructor(private app: LoginService, private http: HttpClient, private router: Router) {
    this.loginModel = new LoginModel("user@gmail.com", "password");
  }

  login() {
    this.app.authenticate(this.loginModel, () => {
      this.router.navigateByUrl('/orders');
    });
    return false;
  }


}
