import {Component, OnInit} from '@angular/core';
import {RequeteGroupeService} from '../../../services/requete-groupes/requete-groupe.service';
import {Object2RequeteGroupe} from '../../../utils/object2RequeteGroupe';
import {RequeteGroupe} from '../../../models/requete-groupe/requete-groupe';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestType} from '../../../models/request/requestType';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {AppConstantMessages} from '../../../../appConstantMessages';
import {Strings} from '../../../utils/strings';
import {Router} from '@angular/router';
import {LockComponent} from '../../lock/lock.component';
import {Requete} from '../../../models/requete/requete';
import {AdminRequeteGroupesModalComponent} from '../admin-requete-groupes-modal/admin-requete-groupes-modal.component';
import {GlobalConstantsService} from '../../../services/variables/global-constants.service';
import {Operateur} from '../../../models/operateur/operateur';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-requete-groupes',
  templateUrl: './admin-requete-groupes.component.html',
  styleUrls: ['./admin-requete-groupes.component.css'],
  entryComponents: [AdminRequeteGroupesModalComponent]
})
export class AdminRequeteGroupesComponent extends LockComponent implements OnInit {

  public requeteGroupes: RequeteGroupe[] = [];

  public requeteGroupeForm: FormGroup;

  public requeteGroupe: RequeteGroupe;

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

  public requetes: Requete[] = [];

  public operateur: Operateur;

  constructor(private _globalConstants: GlobalConstantsService,
              public _formBuilder: FormBuilder,
              public _requeteGroupeService: RequeteGroupeService,
              public _router: Router) {
    super(_router);
    this.operateur = this._globalConstants.getConnectedOperateur();
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();

    this.requeteGroupe = null;

    this.initRequeteGroupeList();

    this.initForm(null);
  }

  initRequeteGroupeList() {
    this._requeteGroupeService.list(this.operateur.id).subscribe(response => {
        this.requeteGroupes = Object2RequeteGroupe.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initForm(requeteGroupe: RequeteGroupe) {

    if (requeteGroupe == null) {
      this.requeteGroupeForm = this._formBuilder.group({
        nom: [''],
        description: ['', Validators.required]
      });
      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer un Groupe';
      this.submitButtonStyle = 'btn-success';
    } else {
      this.requeteGroupeForm = this._formBuilder.group({
        nom: [requeteGroupe.nom],
        description: [requeteGroupe.description, Validators.required]
      });
      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer un Groupe';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  editForm(id: number) {
    this._requeteGroupeService.get(id, this.operateur.id).subscribe(response => {
        this.requeteGroupe = Object2RequeteGroupe.apply(response);
        this.initForm(this.requeteGroupe);
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

  submit(requeteGroupe: RequeteGroupe) {

    if (requeteGroupe === null) {
      this.requeteGroupe = new RequeteGroupe(
        null,
        this.requeteGroupeForm.value.nom,
        this.requeteGroupeForm.value.description);

      this._requeteGroupeService.create(this.requeteGroupe, this.operateur.id).subscribe(response => {
          this.requeteGroupe = Object2RequeteGroupe.apply(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            'Requete-Groupe cree',
            'Le Groupe ' + this.requeteGroupe.nom + ' a ete cree avec succes.',
            RequestVisibility.VISIBLE);

          this.requeteGroupeForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Requete-Groupe non cree',
            'Le Groupe ' + this.requeteGroupe.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    } else {

      this.requeteGroupe = new RequeteGroupe(
        requeteGroupe.id,
        this.requeteGroupeForm.value.nom,
        this.requeteGroupeForm.value.description);
      this._requeteGroupeService.update(this.requeteGroupe.id, this.requeteGroupe, this.operateur.id).subscribe(response => {

          this.requestMessage = new RequestMessage(
            RequestType.WARNING,
            'Requete-Groupe modifie',
            'Le Groupe ' + this.requeteGroupe.id + ' a ete modifie avec succes.',
            RequestVisibility.VISIBLE);

          this.requeteGroupeForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Requete-Groupe non modifie',
            'Le Groupe ' + this.requeteGroupe.id + ' n\'a pas ete modifie avec succes. Raison: ' + error.error.message,
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
        this._requeteGroupeService.delete(id, this.operateur.id).subscribe(response => {
            this.ngOnInit();
          },
          error => {
            const message = Strings.format(
              'Le Groupe {0} n\'a pas ete supprime avec succes. Raison: {1}',
              id,
              error.error.message);

            this.requestMessage = new RequestMessage(
              RequestType.DANGER,
              'Requete-Groupes non supprimes',
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
      this.requeteGroupes.forEach(requeteGroupe => {
        this.ids.push(requeteGroupe.id);
      });

      this.titleConfirmDeleteDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDeleteDialogPrefix, this.ids.length);
    }

    this.ngOnInit();
  }

  setRequeteGroupe(requeteGroupe: RequeteGroupe): void {
    this.requeteGroupe = requeteGroupe;
  }

}
