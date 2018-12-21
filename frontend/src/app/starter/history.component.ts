import {Component, OnInit, Input, OnChanges, SimpleChanges, SimpleChange} from '@angular/core';
import {RequeteStatusHistoryRead} from './models/requeteStatusHistoryRead';
import {Requerant} from '../admin/models/requerant/requerant';

@Component({
  selector: 'app-history-component',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit, OnChanges {

  private _histories;
  private _error;
  public code;
  public requerant: Requerant;
  public message;

  constructor() {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.histories) {
      const histories: SimpleChange = changes.histories;
      this._histories = histories.currentValue;
      if (this._histories !== undefined && this._histories !== null && this._histories.length !== 0) {
        this.requerant = this._histories[0].requete.requerant;
        this.message = undefined;
        this.code = 0;
      } else {
        if (this.error === '') {
          this.message = 'There is no history';
          this.code = 1;
        } else {
          this.message = this.error;
          this.code = 2;
        }
      }
    }
  }

  get histories(): RequeteStatusHistoryRead {
    return this._histories;
  }

  @Input()
  set histories(histories) {
    this._histories = histories;
  }

  get error(): string {
    return this._error;
  }

  @Input()
  set error(error) {
    this._error = error;
  }


  ngOnInit() {

  }

}
