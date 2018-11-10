import { Component, OnInit } from '@angular/core';
import {RequestType} from '../../../models/request/requestType';
import {AppConstantMessages} from '../../../../appConstantMessages';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {RequestMessage} from '../../../models/request/requestMessage';
import {MotifSuppressionService} from '../../../services/motif-suppression/motif-suppression.service';
import {Router} from '@angular/router';
import {Object2Motif} from '../../../utils/object2Motif';
import {MotifSuppression} from '../../../models/motif-suppression/motifSuppression';
import {Strings} from '../../../utils/strings';
import {LockComponent} from '../../lock/lock.component';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-motif',
  templateUrl: './admin-motif-suppression.component.html',
  styleUrls: ['./admin-motif-suppression.component.css']
})
export class AdminMotifSuppressionComponent  extends LockComponent implements OnInit {
  public motifs: MotifSuppression[] = [];

  public motifForm: FormGroup;

  public motif: MotifSuppression;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public nomPattern = '^([A-Z_]*)$';
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

  constructor(public _formBuilder: FormBuilder, public _motifService: MotifSuppressionService, public _router: Router) {
    super(_router);
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();

    this.motif = null;

    this.initMotifList();

    this.initForm(null);
  }

  initMotifList() {
    this._motifService.list().subscribe(response => {
        this.motifs = Object2Motif.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initForm(motif: MotifSuppression) {
    if (motif == null) {
      this.motifForm = this._formBuilder.group({
        nom: ['', [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
        description: ['', [Validators.required, Validators.required, Validators.pattern(this.descriptionPattern)]]
      });
      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer un motif-suppression';
      this.submitButtonStyle = 'btn-success';
    } else {
      this.motifForm = this._formBuilder.group({
        nom: [motif.nom, [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
        description: [motif.description, [Validators.required, Validators.required, Validators.pattern(this.descriptionPattern)]]
      });
      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer un motif-suppression de suppression';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  editForm(id: number) {
    this._motifService.get(id).subscribe(response => {
        this.motif = Object2Motif.apply(response);
        this.initForm(this.motif);
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

  submit(motif: MotifSuppression) {

    if (motif === null) {
      this.motif = new MotifSuppression(
        null,
        this.motifForm.value.nom,
        this.motifForm.value.description);

      this._motifService.create(this.motif).subscribe(response => {
          this.motif = Object2Motif.apply(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            'MotifSuppression cree',
            'Le motif-suppression ' + this.motif.nom + ' a ete cree avec succes.',
            RequestVisibility.VISIBLE);

          this.motifForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'MotifSuppression non cree',
            'Le motif-suppression ' + this.motif.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    } else {

      this.motif = new MotifSuppression(
        motif.id,
        this.motifForm.value.nom,
        this.motifForm.value.description);
      this._motifService.update(this.motif.id, this.motif).subscribe(response => {

          this.requestMessage = new RequestMessage(
            RequestType.WARNING,
            'MotifSuppression modifie',
            'Le motif-suppression ' + this.motif.id + ' a ete modifie avec succes.',
            RequestVisibility.VISIBLE);

          this.motifForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'MotifSuppression non modifie',
            'Le motif-suppression ' + this.motif.id + ' n\'a pas ete modifie avec succes. Raison: ' + error.error.message,
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
        this._motifService.delete(id).subscribe(response => {
            this.ngOnInit();
          },
          error => {
            const message = Strings.format(
              'Le motif-suppression {0} n\'a pas ete supprime avec succes. Raison: {1}',
              id,
              error.error.message);

            this.requestMessage = new RequestMessage(
              RequestType.DANGER,
              'Motifs non supprimes',
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
      this.motifs.forEach(motif => {
        this.ids.push(motif.id);
      });

      this.titleConfirmDeleteDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDeleteDialogPrefix, this.ids.length);
    }

    this.ngOnInit();
  }

}
