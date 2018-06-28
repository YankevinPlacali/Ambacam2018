import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from '../home/layout/layout.component';
import { HeaderComponent } from '../home/header/header.component';
import { BodyComponent } from '../home/body/body.component';
import { FooterComponent } from '../home/footer/footer.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [LayoutComponent, HeaderComponent, BodyComponent, FooterComponent],
  exports: [LayoutComponent]
})
export class UiModule { }
