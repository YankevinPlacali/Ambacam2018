import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestType} from '../../../models/request/requestType';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {Router} from '@angular/router';
import {OperateurService} from '../../../services/operateurs/operateur.service';
import {Strings} from '../../../utils/strings';
import {Object2OperateurStd} from '../../..//utils/object2OperateurStd';
import {RequestMessage} from '../../../models/request/requestMessage';
import {Operateur} from '../../../models/operateur/operateur';
import {AppConstantMessages} from '../../../../appConstantMessages';
import {LockComponent} from '../../lock/lock.component';
import {OperateurCreate} from '../../../models/operateur/operateurCreate';
import {Object2OperateurCreate} from '../../../utils/object2OperateurCreate';
import {Auth} from '../../../utils/auth/auth';
import {OperateurStd} from '../../../models/operateur/operateurStd';
import {Object2Pays} from '../../../utils/object2Pays';
import {Pays} from '../../../models/pays';
import {PaysService} from '../../../services/pays/pays.service';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-operateurs',
  templateUrl: './admin-operateurs.component.html',
  styleUrls: ['./admin-operateurs.component.css']
})
export class AdminOperateursComponent extends LockComponent implements OnInit {

  public operateurs: OperateurStd[] = [];

  public operateurForm: FormGroup;

  public operateurCreate: OperateurCreate;

  public operateurStd: OperateurStd;

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

  public pays: Pays[];

  public showUsernameField = false;

  constructor(public _formBuilder: FormBuilder, public _operateurService: OperateurService, public _router: Router, public _paysService: PaysService) {
    super(_router);

    this._paysService.list().subscribe(response => {
        this.pays = Object2Pays.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();

    this.initOperateurList();

    this.initForm(null);
  }

  initOperateurList() {
    this._operateurService.list().subscribe(response => {
        this.operateurs = Object2OperateurStd.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initForm(operateur: OperateurCreate) {
    if (operateur == null) {
      this.showUsernameField = true;
      this.operateurForm = this._formBuilder.group({
        nom: ['', [Validators.required]],
        prenom: [''],
        sexe: ['', Validators.required],
        paysId: ['', Validators.required],
        username: ['', [Validators.required]],
        password: ['', [Validators.required]],
      });
      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer un operateur';
      this.submitButtonStyle = 'btn-success';
    } else {
      this.operateurForm = this._formBuilder.group({
        nom: [operateur.nom, [Validators.required]],
        prenom: [operateur.prenom],
        sexe: [operateur.sexe, Validators.required],
        paysId: [operateur.paysId, Validators.required]
      });
      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer un operateur';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  editForm(id: number) {
    this.showUsernameField = false;
    this._operateurService.get(id).subscribe(response => {
        this.operateurCreate = Object2OperateurCreate.apply(response);
        this.initForm(this.operateurCreate);
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

  submit(operateur: Operateur) {

    if (operateur === undefined) {
      const creatorId = Auth.getOperateur().id;
      this.operateurCreate = new OperateurCreate(
        this.operateurForm.value.nom,
        this.operateurForm.value.prenom,
        this.operateurForm.value.sexe,
        this.operateurForm.value.paysId,
        this.operateurForm.value.username,
        this.operateurForm.value.password,
        creatorId);

      this._operateurService.create(this.operateurCreate).subscribe(response => {
          this.operateurStd = Object2OperateurStd.apply(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            'Operateur cree',
            'L\'operateur ' + this.operateurStd.nom + ' a ete cree avec succes.',
            RequestVisibility.VISIBLE);

          this.operateurForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Operateur non cree',
            'L\'operateur ' + this.operateurCreate.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    } else {
      this.operateurCreate = new OperateurCreate(
        this.operateurForm.value.nom,
        this.operateurForm.value.prenom,
        this.operateurForm.value.sexe,
        this.operateurForm.value.paysId,
        null,
        null,
        null);
      this._operateurService.update(operateur.id, this.operateurCreate).subscribe(response => {

          this.requestMessage = new RequestMessage(
            RequestType.WARNING,
            'Operateur modifie',
            'L\'operateur ' + operateur.id + ' a ete modifie avec succes.',
            RequestVisibility.VISIBLE);

          this.operateurForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Operateur non modifie',
            'L\'operateur ' + this.operateurCreate.id + ' n\'a pas ete modifie avec succes. Raison: ' + error.error.message,
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
        this._operateurService.delete(id).subscribe(response => {
            this.ngOnInit();
          },
          error => {
            const message = Strings.format(
              'Le operateurStd {0} n\'a pas ete supprime avec succes. Raison: {1}',
              id,
              error.error.message);

            this.requestMessage = new RequestMessage(
              RequestType.DANGER,
              'Operateurs non supprimes',
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
      this.operateurs.forEach(operateur => {
        this.ids.push(operateur.id);
      });

      this.titleConfirmDeleteDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDeleteDialogPrefix, this.ids.length);
    }

    this.ngOnInit();
  }

}
