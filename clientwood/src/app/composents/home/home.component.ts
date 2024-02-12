import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent {

  greeting = {};

  constructor(private http: HttpClient) {
    // http.get('resource').subscribe(data => this.greeting = {id: 1});
  }

  authenticated = false;
}

