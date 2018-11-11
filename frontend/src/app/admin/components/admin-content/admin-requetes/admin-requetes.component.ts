import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestType} from '../../../models/request/requestType';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {AppConstantMessages} from '../../../../appConstantMessages';
import {Router} from '@angular/router';
import {LockComponent} from '../../lock/lock.component';
import {Requete} from '../../../models/requete/requete';
import {RequeteService} from '../../../services/requetes/requete.service';
import {Object2Requete} from '../../../utils/object2Requete';
import {Object2Requerant} from '../../../utils/object2Requerant';
import {TypeRequeteService} from '../../../services/type-requetes/type-requete.service';
import {RequerantService} from '../../../services/requerants/requerant.service';
import {TypeRequete} from '../../../models/type-requete/type-requete';
import {Requerant} from '../../../models/requerant/requerant';
import {Object2TypeRequete} from '../../../utils/object2TypeRequete';
import {Strings} from '../../../utils/strings';
import {Object2Pays} from '../../../utils/object2Pays';
import {PaysService} from '../../../services/pays/pays.service';
import {Pays} from '../../../models/pays/pays';
import {Operateur} from '../../../models/operateur/operateur';
import {GlobalConstantsService} from '../../../services/variables/global-constants.service';
import {Statut} from '../../../models/statut/statut';
import {StatutService} from '../../../services/statuts/statut.service';
import {Object2Statut} from '../../../utils/object2Statut';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-requetes',
  templateUrl: './admin-requetes.component.html',
  styleUrls: ['./admin-requetes.component.css']
})

export class AdminRequetesComponent extends LockComponent implements OnInit {

  public requetes: Requete[] = [];

  public checkedRequetes: Requete[] = [];

  public requeteForm: FormGroup;

  public requete: Requete;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public submitButtonTitle;

  public submitButtonStyle;

  public formTitle;

  public ids: number[] = [];

  public titleConfirmDialogPrefix = 'Suppression: ';

  public titleConfirmDialog: string;

  public messageConfirmDialog = AppConstantMessages.CONFIRM;

  public yesConfirmDialog = AppConstantMessages.YES;

  public noConfirmDialog = AppConstantMessages.NO;

  public confirm: boolean;

  public deleteAction = false;

  public statusId: number;

  public allChecked = false;

  public typeRequetes: TypeRequete[] = [];

  public requerants: Requerant[] = [];

  public typeRequeteForm: FormGroup;

  public tR_submitButtonTitle;

  public tR_submitButtonStyle;

  public tR_formTitle;

  public requerantForm: FormGroup;

  public r_submitButtonTitle;

  public r_submitButtonStyle;

  public r_formTitle;

  public allPays: Pays[] = [];

  public operateur: Operateur;

  public statutUpForm: FormGroup;

  public sUp_submitButtonTitle;

  public sUp_submitButtonStyle;

  public sUp_formTitle;

  public statutForm: FormGroup;

  public statuts: Statut[] = [];

  public s_submitButtonTitle;

  public s_submitButtonStyle;

  public s_formTitle;

  public updateRequete: boolean;

  public showRequerant = true;


  constructor(private _globalConstants: GlobalConstantsService,
              public _formBuilder: FormBuilder,
              public _requeteService: RequeteService,
              public _typeRequeteService: TypeRequeteService,
              public _requerantService: RequerantService,
              public _paysService: PaysService,
              public _statutService: StatutService,
              public _router: Router) {
    super(_router);
    this.operateur = this._globalConstants.getConnectedOperateur();
  }

  initRequerantList() {
    this._requerantService.list().subscribe(response => {
        this.requerants = Object2Requerant.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initTypeRequeteList() {
    this._typeRequeteService.list().subscribe(response => {
        this.typeRequetes = Object2TypeRequete.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();

    this.requete = null;

    this.deleteAction = true;

    this.initRequeteList();

    this.initForm(null);

    this.createRequerantForm();

    this.createTypeRequeteForm();

    this.createStatutForm();

    this.createStatutUpForm(false);

  }

  initRequeteList() {
    this._requeteService.list().subscribe(response => {
        this.requetes = Object2Requete.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initForm(requete: Requete) {
    if (requete == null) {
      this.requeteForm = this._formBuilder.group({
        typeRequete: ['', [Validators.required]],
        requerant: ['', [Validators.required]]
      });

      this.initRequerantList();
      this.initTypeRequeteList();
      this.initStatuts();

      this.updateRequete = false;

      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer une requete';
      this.submitButtonStyle = 'btn-success';
    } else {
      this.requeteForm = this._formBuilder.group({
        typeRequete: [requete.typeRequete.id, [Validators.required]],
        requerant: [Strings.format('{0} {1}', requete.requerant.nom, requete.requerant.prenom)]
      });

      this.updateRequete = true;

      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer une requete';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  editForm(requete: Requete) {
    this._requeteService.get(requete).subscribe(response => {
        this.requete = Object2Requete.apply(response);
        this.initForm(this.requete);
        this.showRequerant = false;
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Echec',
          'Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
        this.showRequerant = true;
      });

  }

  createForm() {
    this.initForm(null);
    this.showRequerant = true;
  }


  submit(requete: Requete) {

    this.requete = new Requete();
    this.requete.id = null;
    this.requete.operateurId = this.operateur.id;
    this.requete.typeId = this.requeteForm.value.typeRequete;
    this.requete.requerantId = this.requeteForm.value.requerant;

    if (requete === null) {
      this._requeteService.create(this.requete).subscribe(response => {
          this.requete = Object2Requete.apply(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            'Requete cree',
            'Le requete de' + this.requete.requerant.nom + ' a ete cree avec succes.',
            RequestVisibility.VISIBLE);

          this.requeteForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Requete non cree',
            'Le requete de' + this.requete.requerant.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });

    } else {

      this.requete.id = requete.id;
      this._requeteService.update(this.requete).subscribe(response => {
          this.requestMessage = new RequestMessage(
            RequestType.WARNING,
            'Requete modifie',
            'Le requete ' + this.requete.id + ' a ete modifie avec succes.',
            RequestVisibility.VISIBLE);

          this.requeteForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Requete non modifie',
            'Le requete ' + this.requete.id + ' n\'a pas ete modifie avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    }
  }

  updateStatut() {
    this.statusId = parseInt(this.statutUpForm.value.statut);
    this.deleteAction = false;

  }

// this function allows to add and remove an id to a list of ids to delete
  assignId(id: number) {
    const idx = this.ids.indexOf(id, 0);

    if (idx > -1) {
      this.ids.splice(idx, 1);
    } else {
      this.ids.push(id);
    }

    this.titleConfirmDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDialogPrefix, this.ids.length);
  }


  /* Use action to update status of requetes  or delete requetes */
  action(confirm) {

    if (confirm) {

      this.checkedRequetes = [];

      this.requetes.forEach(requete => {
        if (this.ids.includes(requete.id)) {
          this.checkedRequetes.push(requete);
        }
      });

      if (this.deleteAction) {
        this.checkedRequetes.forEach(requete => {
          this._requeteService.delete(requete).subscribe(response => {
              this.ngOnInit();
            },
            error => {
              const message = Strings.format(
                'La requete {0} n\'a pas ete supprime avec succes. Raison: {1}',
                requete,
                error.error.message);

              this.requestMessage = new RequestMessage(
                RequestType.DANGER,
                'Requetes non supprimes',
                message,
                RequestVisibility.VISIBLE);
            });
        });

      } else {
        this.checkedRequetes.forEach(requete => {
          requete.statusId = this.statusId;

          this._requeteService.updateStatut(requete).subscribe(response => {
              this.ngOnInit();
            },
            error => {
              const message = Strings.format(
                'Le statut de la requete {0} n\'a pas ete modifié avec succes. Raison: {1}',
                requete,
                error.error.message);

              this.requestMessage = new RequestMessage(
                RequestType.DANGER,
                'Statut Requetes non modifiés',
                message,
                RequestVisibility.VISIBLE);
            });
        });

      }

    }
    this.ids = [];
    this.checkedRequetes = [];
    this.confirm = false;
  }

  checkAll(allChecked: boolean) {

    this.allChecked = allChecked;

    this.ids = [];

    if (this.allChecked) {
      this.requetes.forEach(requete => {
        this.ids.push(requete.id);
      });

      this.titleConfirmDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDialogPrefix, this.ids.length);
    }

    this.ngOnInit();
  }

  createStatutForm() {
    this.statutForm = this._formBuilder.group({
      nom: ['', [Validators.required]],
      description: ['']
    });
    this.s_submitButtonTitle = 'Creer';
    this.s_formTitle = 'Creer un statut requete';
    this.s_submitButtonStyle = 'btn-success';
  }

  submitStatut() {
    let statut = new Statut(
      null,
      this.statutForm.value.nom,
      this.statutForm.value.description);

    this._statutService.create(statut).subscribe(response => {
        statut = Object2Statut.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Statut Requete cree',
          'Le statut requete' + statut.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.statutForm.reset();
        this.initStatuts();
        this.createStatutUpForm(false);
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Statut Requete non cree',
          'Le statut requete ' + statut.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

  createStatutUpForm(clickEvent: boolean) {
    this.statutUpForm = this._formBuilder.group({
      statut: ['', [Validators.required]],
    });

    if (clickEvent) {
      this.titleConfirmDialogPrefix = 'Modification Statut: ';
      this.titleConfirmDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDialogPrefix, this.ids.length);
    } else {
      this.titleConfirmDialogPrefix = 'Suppression: ';
    }

    this.sUp_submitButtonTitle = 'Modifier Statut';
    this.sUp_formTitle = 'Modifier le statut de requetes';
    this.sUp_submitButtonStyle = 'btn-warning';
  }

  createTypeRequeteForm() {
    this.typeRequeteForm = this._formBuilder.group({
      nom: ['', [Validators.required]],
      description: ['']
    });
    this.tR_submitButtonTitle = 'Creer';
    this.tR_formTitle = 'Creer un type requete';
    this.tR_submitButtonStyle = 'btn-success';

  }

  submitTypeRequete() {
    let typeRequete = new TypeRequete(
      null,
      this.typeRequeteForm.value.nom,
      this.typeRequeteForm.value.description);

    this._typeRequeteService.create(typeRequete).subscribe(response => {
        typeRequete = Object2TypeRequete.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Type Requete cree',
          'Le type requete ' + typeRequete.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.typeRequeteForm.reset();
        this.initTypeRequeteList();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Type Requete non cree',
          'Le type requete ' + typeRequete.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

  initAllPays() {
    this._paysService.list().subscribe(response => {
        this.allPays = Object2Pays.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initStatuts() {
    this._statutService.list().subscribe(response => {
        this.statuts = Object2Statut.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  createRequerantForm() {

    this.requerantForm = this._formBuilder.group({
      nom: ['', [Validators.required]],
      prenom: ['', [Validators.required]],
      dateNaissance: ['', [Validators.required]],
      sexe: ['', [Validators.required]],
      profession: ['', [Validators.required]],
      lieuNaissance: ['', [Validators.required]],
      nationalite: ['', [Validators.required]],
    });

    this.initAllPays();

    this.r_submitButtonTitle = 'Creer';
    this.r_formTitle = 'Creer un requerant';
    this.r_submitButtonStyle = 'btn-success';

  }

  submitRequerant() {

    let requerant = new Requerant();

    requerant.id = null;
    requerant.nom = this.requerantForm.value.nom;
    requerant.prenom = this.requerantForm.value.prenom;
    requerant.dateNaissance = this.requerantForm.value.dateNaissance;
    requerant.creatorId = 1; // service to get actual connected operator
    requerant.sexe = this.requerantForm.value.sexe;
    requerant.profession = this.requerantForm.value.profession;
    requerant.lieuNaissance = this.requerantForm.value.lieuNaissance;
    requerant.paysId = parseInt(this.requerantForm.value.nationalite);

    this._requerantService.create(requerant).subscribe(response => {
        requerant = Object2Requerant.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Requerant cree',
          'Le requerant ' + requerant.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.requerantForm.reset();
        this.ngOnInit();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Requerant non cree',
          'Le requerant ' + requerant.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });


  }

}
