import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TableModule} from "primeng/table";
import {DialogModule} from "primeng/dialog";

const routes: Routes = [];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
