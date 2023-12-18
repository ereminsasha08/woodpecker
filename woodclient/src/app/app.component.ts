import { RouterOutlet } from '@angular/router';
import {Component} from "@angular/core";
import {AppRoutingModule} from "./app-routing.module";
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AppRoutingModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{

  title = "woodclient";

}
