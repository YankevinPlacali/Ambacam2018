import { Component, OnInit } from '@angular/core';
import {LockComponent} from '../../lock/lock.component';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RecepisseService} from '../../../services/recepisse/recepisse.service';
import {RequestType} from '../../../models/request/requestType';
import {Recepisse} from '../../../models/recepisse/recepisse';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {Object2Recepisse} from '../../../utils/object2Recepisse';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-recepisse-form',
  templateUrl: './admin-recepisse-form.component.html',
  styleUrls: ['./admin-recepisse-form.component.css']
})
export class AdminRecepisseFormComponent  extends LockComponent implements OnInit {

  public recepisseCreateForm: FormGroup;

  public recepisse: Recepisse;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);


  constructor(public _router: Router, public _formBuilder: FormBuilder, public _recepisseService: RecepisseService) {
    super(_router);
    this.recepisseCreateForm = this._formBuilder.group({
      nom: [''],
      description: ['']
    });
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();
  }

  create() {
    this.recepisse = new Recepisse(
      this.recepisseCreateForm.value.requerantId,
      this.recepisseCreateForm.value.requeteId);

    this._recepisseService.create(this.recepisse).subscribe(response => {
        this.recepisse = Object2Recepisse.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Recepisse cree',
          'Le recepisse ' + this.recepisse.recepisseNo + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.recepisseCreateForm.reset();
        this.ngOnInit();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Recepisse non cree',
          'Le recepisse ' + this.recepisse.recepisseNo + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

}
