import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-yes-no-dialog',
  templateUrl: './yes-no-dialog.component.html',
  styleUrls: ['./yes-no-dialog.component.css']
})
export class YesNoDialogComponent implements OnInit {

  @Input() public title: string;
  @Input() public message: string;
  @Input() public yes: string;
  @Input() public no: string;

  @Output() public confirmEvent = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

  sayYes() {
    this.confirmEvent.emit(true);
  }

  sayNo() {
    this.confirmEvent.emit(false);
  }

}
