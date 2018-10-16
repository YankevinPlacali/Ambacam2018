import {Component, OnInit} from '@angular/core';
import {LockComponent} from '../../lock/lock.component';
import {RequestType} from '../../../models/request/requestType';
import {Statut} from '../../../models/statut/statut';
import {RequestMessage} from '../../../models/request/requestMessage';
import {AppConstantMessages} from '../../../../appConstantMessages';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {Router} from '@angular/router';
import {StatutService} from '../../../services/statuts/statut.service';
import {Strings} from '../../../utils/strings';
import {Object2Statut} from '../../../utils/object2Statut';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-statut',
  templateUrl: './admin-statuts.component.html',
  styleUrls: ['./admin-statuts.component.css']
})
export class AdminStatutsComponent extends LockComponent implements OnInit {

  public statuts: Statut[] = [];

  public statutForm: FormGroup;

  public statut: Statut;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public nomPattern = '[A-Z][a-zA-Z0-9_].*';
  public descriptionPattern = '^[A-Za-z0-9- ]*$';


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


  constructor(public _formBuilder: FormBuilder, public _statutService: StatutService, public _router: Router) {
    super(_router);
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();

    this.initStatutList();

    this.initForm(null);
  }

  initStatutList() {
    this._statutService.list().subscribe(response => {
        this.statuts = Object2Statut.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initForm(statut: Statut) {
    if (statut == null) {
      this.statutForm = this._formBuilder.group({
        nom: ['', [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
        description: ['', [Validators.required, Validators.required, Validators.pattern(this.descriptionPattern)]]
      });
      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer un statut';
      this.submitButtonStyle = 'btn-success';
    } else {
      this.statutForm = this._formBuilder.group({
        nom: [statut.nom, [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
        description: [statut.description, [Validators.required, Validators.required, Validators.pattern(this.descriptionPattern)]]
      });
      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer un statut';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  editForm(id: number) {
    this._statutService.get(id).subscribe(response => {
        this.statut = Object2Statut.apply(response);
        this.initForm(this.statut);
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

  submit(statut: Statut) {

    if (statut === undefined) {
      this.statut = new Statut(
        null,
        this.statutForm.value.nom,
        this.statutForm.value.description);

      this._statutService.create(this.statut).subscribe(response => {
          this.statut = Object2Statut.apply(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            'Statut cree',
            'Le statut ' + this.statut.nom + ' a ete cree avec succes.',
            RequestVisibility.VISIBLE);

          this.statutForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Statut non cree',
            'Le statut ' + this.statut.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    } else {

      this.statut = new Statut(
        statut.id,
        this.statutForm.value.nom,
        this.statutForm.value.description);
      this._statutService.update(this.statut.id, this.statut).subscribe(response => {

          this.requestMessage = new RequestMessage(
            RequestType.WARNING,
            'Statut modifie',
            'Le statut ' + this.statut.id + ' a ete modifie avec succes.',
            RequestVisibility.VISIBLE);

          this.statutForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Statut non modifie',
            'Le statut ' + this.statut.id + ' n\'a pas ete modifie avec succes. Raison: ' + error.error.message,
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
        this._statutService.delete(id).subscribe(response => {
            this.ngOnInit();
          },
          error => {
            const message = Strings.format(
              'Le statut {0} n\'a pas ete supprime avec succes. Raison: {1}',
              id,
              error.error.message);

            this.requestMessage = new RequestMessage(
              RequestType.DANGER,
              'Statuts non supprimes',
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
      this.statuts.forEach(statut => {
        this.ids.push(statut.id);
      });

      this.titleConfirmDeleteDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDeleteDialogPrefix, this.ids.length);
    }

    this.ngOnInit();
  }

}
