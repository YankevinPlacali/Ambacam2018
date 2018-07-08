
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LayoutComponent } from '../home/layout/layout.component';
import {
  RoundProgressModule,
  ROUND_PROGRESS_DEFAULTS
} from 'angular-svg-round-progressbar';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RoundProgressModule
  ],
  providers: [{
    provide: ROUND_PROGRESS_DEFAULTS,
    useValue: {
      color: '#f00',
      background: '#0f0'
    }
  }],
  declarations: [LayoutComponent],
  exports: [LayoutComponent]
})
export class UiModule { }
