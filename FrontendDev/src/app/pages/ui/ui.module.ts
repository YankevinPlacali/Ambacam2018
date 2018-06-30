import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { LayoutComponent } from '../home/layout/layout.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [LayoutComponent],
  exports: [LayoutComponent]
})
export class UiModule { }
