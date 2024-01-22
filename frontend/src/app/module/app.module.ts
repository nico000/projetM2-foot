import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from '../@app/app.component';
import {HeaderComponent} from "../@header/header.component";
import {HomeComponent} from "../@home/home.component";
import {FooterComponent} from "../@footer/footer.component";
import {AppRoutingModule} from "../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CommonModule} from "@angular/common";
import {ModalComponent} from "../@modal/modal.component";
import {CreationComponent} from "../@creation/creation.component";
import {ResultatComponent} from "../@resultat/resultat.component";




@NgModule({
  declarations: [
    AppComponent,
      HeaderComponent,
      HomeComponent,
      CreationComponent,
      ResultatComponent,
      FooterComponent,
      ModalComponent
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        NgbModule,
        CommonModule
    ],
  providers: [

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
