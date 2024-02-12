import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {TableModule} from "primeng/table";
import {DialogModule} from "primeng/dialog";
import {GoodsComponent} from "./composents/goods/goods.component";
import {HomeComponent} from "./composents/home/home.component";
import {LoginComponent} from "./composents/login/login.component";
import {GoodsModalComponent} from "./composents/goods/goods-modal.component";
import {ActionPanelComponent} from "./composents/action-panel/action-panel.component";
import {OrdersService} from "./composents/orders/service/orders.service";
import {GoodsService} from "./composents/goods/service/goods.service";
import {LoginService} from "./composents/login/service/login.service";
import {TabViewModule} from "primeng/tabview";
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {FileUploadModule} from "primeng/fileupload";
import {ToolbarModule} from "primeng/toolbar";
import {InputTextModule} from "primeng/inputtext";
import {ConfirmationService, MessageService} from "primeng/api";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {MessageModule} from "primeng/message";
import {PaginatorModule} from "primeng/paginator";
import {RadioButtonModule} from "primeng/radiobutton";
import {InputTextareaModule} from "primeng/inputtextarea";
import {ToastModule} from "primeng/toast";

@NgModule({
  declarations: [
    AppComponent,
    GoodsComponent,
    HomeComponent,
    LoginComponent,
    GoodsModalComponent,
    ActionPanelComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    TableModule,
    DialogModule,
    TabViewModule,
    ButtonModule,
    RippleModule,
    FileUploadModule,
    ToolbarModule,
    InputTextModule,
    ConfirmDialogModule,
    MessageModule,
    PaginatorModule,
    RadioButtonModule,
    InputTextareaModule,
    ToastModule
  ],
  providers: [
    OrdersService,
    GoodsService,
    LoginService,
    MessageService,
    ConfirmationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
