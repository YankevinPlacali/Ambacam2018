import {Component, OnInit} from '@angular/core';
import {ActionService} from '../../../services/actions/action.service';
import {Object2Action} from '../../../utils/object2Action';
import {Action} from '../../../models/action/action';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestType} from '../../../models/request/requestType';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {AppConstantMessages} from '../../../../appConstantMessages';
import {Strings} from '../../../utils/strings';
import {Router} from '@angular/router';
import {LockComponent} from '../../lock/lock.component';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-actions',
  templateUrl: './admin-actions.component.html',
  styleUrls: ['./admin-actions.component.css']
})
export class AdminActionsComponent extends LockComponent implements OnInit {

  public actions: Action[] = [];

  public actionForm: FormGroup;

  public action: Action;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public nomPattern = '^([A-Z_]*)$';

  public submitButtonTitle;

  public submitButtonStyle;

  public formTitle;

  public ids: number[] = [];

  public titleConfirmDeleteDialogPrefix = 'Suppression: ';

  public titleConfirmDeleteDialog: string;

  public messageConfirmDeleteDialog = AppConstantMessages.CONFIRM;

  public yesConfirmDeleteDialog = AppConstantMessages.YES;

  public noConfirmDeleteDialog = AppConstantMessages.NO;

  public confirm: boolean;

  public allChecked = false;

  constructor(public _formBuilder: FormBuilder, public _actionService: ActionService, public _router: Router) {
    super(_router);
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();

    this.initActionList();

    this.initForm(null);
  }

  initActionList() {
    this._actionService.list().subscribe(response => {
        this.actions = Object2Action.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initForm(action: Action) {
    if (action == null) {
      this.actionForm = this._formBuilder.group({
        nom: ['', [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
        description: ['']
      });
      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer un action';
      this.submitButtonStyle = 'btn-success';
    } else {
      this.actionForm = this._formBuilder.group({
        nom: [action.nom, [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
        description: [action.description]
      });
      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer un action';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  editForm(id: number) {
    this._actionService.get(id).subscribe(response => {
        this.action = Object2Action.apply(response);
        this.initForm(this.action);
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Echec',
          'Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

  createForm() {
    this.initForm(null);
  }

  submit(action: Action) {

    if (action === undefined) {
      this.action = new Action(
        null,
        this.actionForm.value.nom,
        this.actionForm.value.description);

      this._actionService.create(this.action).subscribe(response => {
          this.action = Object2Action.apply(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            'Action cree',
            'Le action ' + this.action.nom + ' a ete cree avec succes.',
            RequestVisibility.VISIBLE);

          this.actionForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Action non cree',
            'Le action ' + this.action.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    } else {

      this.action = new Action(
        action.id,
        this.actionForm.value.nom,
        this.actionForm.value.description);
      this._actionService.update(this.action.id, this.action).subscribe(response => {

          this.requestMessage = new RequestMessage(
            RequestType.WARNING,
            'Action modifie',
            'Le action ' + this.action.id + ' a ete modifie avec succes.',
            RequestVisibility.VISIBLE);

          this.actionForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Action non modifie',
            'Le action ' + this.action.id + ' n\'a pas ete modifie avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    }
  }

  // this function allows to add and remove an id to a list of ids to delete
  assignId(id: number) {
    const idx = this.ids.indexOf(id, 0);

    if (idx > -1) {
      this.ids.splice(idx, 1);
    } else {
      this.ids.push(id);
    }

    this.titleConfirmDeleteDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDeleteDialogPrefix, this.ids.length);
  }

  delete(confirm) {

    if (confirm) {

      this.ids.forEach(id => {
        this._actionService.delete(id).subscribe(response => {
            this.ngOnInit();
          },
          error => {
            const message = Strings.format(
              'Le action {0} n\'a pas ete supprime avec succes. Raison: {1}',
              id,
              error.error.message);

            this.requestMessage = new RequestMessage(
              RequestType.DANGER,
              'Actions non supprimes',
              message,
              RequestVisibility.VISIBLE);
          });
      });

      this.confirm = false;
      this.ids = [];
    }
    this.confirm = false;
  }

  checkAll(allChecked: boolean) {

    this.allChecked = allChecked;

    this.ids = [];

    if (this.allChecked) {
      this.actions.forEach(action => {
        this.ids.push(action.id);
      });

      this.titleConfirmDeleteDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDeleteDialogPrefix, this.ids.length);
    }

    this.ngOnInit();
  }

}
