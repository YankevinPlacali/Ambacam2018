import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActionService} from '../../../services/actions/action.service';
import {Action} from '../../../models/action/action';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestType} from '../../../models/request/requestType';
import {Object2Action} from '../../../utils/object2Action';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {LockComponent} from '../../lock/lock.component';
import {Router} from '@angular/router';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;


@Component({
  selector: 'app-admin-actions-form',
  templateUrl: './admin-actions-form.component.html',
  styleUrls: ['./admin-actions-form.component.css']
})
export class AdminActionsFormComponent extends LockComponent implements OnInit {

  public actionCreateForm: FormGroup;

  public action: Action;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public nomPattern = '^([A-Z_]*)$';

  constructor(public _router: Router, public _formBuilder: FormBuilder, public _actionService: ActionService) {
    super(_router);
    this.actionCreateForm = this._formBuilder.group({
      nom: ['', [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
      description: ['']
    });
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();
  }

  create() {
    this.action = new Action(
      null,
      this.actionCreateForm.value.nom,
      this.actionCreateForm.value.description);

    this._actionService.create(this.action).subscribe(response => {
        this.action = Object2Action.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Action cree',
          'Le action ' + this.action.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.actionCreateForm.reset();
        this.ngOnInit();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Action non cree',
          'Le action ' + this.action.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

}
