import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginModel} from "../model/login-model";
import {environment} from "../../../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public loginModel?: LoginModel | null;
  authenticated = false;

  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'

  constructor(private http: HttpClient) {
  }

  private readonly backendUrl = environment.backendUrl + '/login';

  authenticate(loginModel: LoginModel, callback: any) {

    const headers = new HttpHeaders(loginModel ? {
      authorization: 'Basic ' + btoa(loginModel.username + ':' + loginModel.password)
    } : {});

    this.http.get(this.backendUrl, {headers: headers}).subscribe((response: any) => {
      this.loginModel = loginModel;
      this.registerSuccessfulLogin(loginModel);
      return callback && callback();
    });

  }

  registerSuccessfulLogin(loginModel: LoginModel) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, loginModel.username)
  }

  logout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    this.loginModel = null;
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return false
    return true
  }

  getLoggedInUserName() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return ''
    return user
  }

}


