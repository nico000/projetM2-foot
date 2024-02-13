import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from '../@app/app.component';
import {HeaderComponent} from "../@header/header.component";
import {HomeComponent} from "../@home/home.component";
import {HomeService} from "../@home/home.service";
import {FooterComponent} from "../@footer/footer.component";
import {AppRoutingModule} from "../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CommonModule} from "@angular/common";
import {ModalComponent} from "../@modal/modal.component";
import {CreationComponent} from "../@creation/creation.component";
import {ResultatComponent} from "../@resultat/resultat.component";
import {CreationService} from "../@creation/creation.service";
import {ResultatService} from "../@resultat/resultat.service";
import {FormsModule} from "@angular/forms";
import {SerieComponent} from "../@serie/serie.component";
import {SerieService} from "../@serie/serie.service";
//import {DragDropModule} from '@angular/cdk/drag-drop';



@NgModule({
  declarations: [
    AppComponent,
      HeaderComponent,
      HomeComponent,
      CreationComponent,
      ResultatComponent,
      FooterComponent,
      ModalComponent,
      SerieComponent,
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        FormsModule,
        NgbModule,
        CommonModule,
        //DragDropModule
    ],
  providers: [
      HomeService,
      CreationService,
      ResultatService,
      SerieService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
