import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from '../home/layout/layout.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [LayoutComponent],
  exports: [LayoutComponent]
})
export class UiModule { }
