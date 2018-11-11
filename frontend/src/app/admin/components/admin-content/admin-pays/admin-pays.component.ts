import {Component, OnInit} from '@angular/core';
import {PaysService} from '../../../services/pays/pays.service';
import {Object2Pays} from '../../../utils/object2Pays';
import {Pays} from '../../../models/pays/pays';
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
  selector: 'app-admin-pays',
  templateUrl: './admin-pays.component.html',
  styleUrls: ['./admin-pays.component.css']
})
export class AdminPaysComponent extends LockComponent implements OnInit {

  public pays: Pays[] = [];

  public pForm: FormGroup;

  public p: Pays;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

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

  constructor(public _formBuilder: FormBuilder, public _pService: PaysService, public _router: Router) {
    super(_router);
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();

    this.pays = null;

    this.initPaysList();

    this.initForm(null);
  }

  initPaysList() {
    this._pService.list().subscribe(response => {
        this.pays = Object2Pays.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initForm(p: Pays) {
    if (p == null) {
      this.pForm = this._formBuilder.group({
        nom: ['', [Validators.required]],
        description: ['']
      });
      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer un pays';
      this.submitButtonStyle = 'btn-success';
    } else {
      this.pForm = this._formBuilder.group({
        nom: [p.nom, [Validators.required]],
        description: [p.description]
      });
      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer un pays';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  editForm(id: number) {
    this._pService.get(id).subscribe(response => {
        this.p = Object2Pays.apply(response);
        this.initForm(this.p);
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

  submit(p: Pays) {

    if (p === null) {
      this.p = new Pays(
        null,
        this.pForm.value.nom,
        this.pForm.value.description);

      this._pService.create(this.p).subscribe(response => {
          this.p = Object2Pays.apply(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            'Pays cree',
            'Le pays ' + this.p.nom + ' a ete cree avec succes.',
            RequestVisibility.VISIBLE);

          this.pForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Pays non cree',
            'Le pays ' + this.p.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    } else {

      this.p = new Pays(
        p.id,
        this.pForm.value.nom,
        this.pForm.value.description);
      this._pService.update(this.p.id, this.p).subscribe(response => {

          this.requestMessage = new RequestMessage(
            RequestType.WARNING,
            'Pays modifie',
            'Le pays ' + this.p.id + ' a ete modifie avec succes.',
            RequestVisibility.VISIBLE);

          this.pForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Pays non modifie',
            'Le pays ' + this.p.id + ' n\'a pas ete modifie avec succes. Raison: ' + error.error.message,
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
        this._pService.delete(id).subscribe(response => {
            this.ngOnInit();
          },
          error => {
            const message = Strings.format(
              'Le pays {0} n\'a pas ete supprime avec succes. Raison: {1}',
              id,
              error.error.message);

            this.requestMessage = new RequestMessage(
              RequestType.DANGER,
              'Pays non supprimes',
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
      this.pays.forEach(p => {
        this.ids.push(p.id);
      });

      this.titleConfirmDeleteDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDeleteDialogPrefix, this.ids.length);
    }

    this.ngOnInit();
  }

}
