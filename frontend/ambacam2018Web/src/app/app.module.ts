///<reference path="../../node_modules/@angular/core/src/metadata/directives.d.ts"/>
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {Component} from '@angular/core';

import { AppComponent } from './app.component';
import { MaterialModule } from './material.module';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatCardModule} from "@angular/material/card";
import { CardComponent } from './card/card.component';
import {MatListModule} from "@angular/material/list";
import { ListComponent } from './list/list.component';


@NgModule({
  declarations: [
    AppComponent,
    CardComponent,
    ListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule, MatCardModule, MatListModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
export class ListOverviewExample {}

