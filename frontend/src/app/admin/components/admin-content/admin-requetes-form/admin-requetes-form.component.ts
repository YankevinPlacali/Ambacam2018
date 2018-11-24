import {Component, OnInit} from '@angular/core';
import {Requete} from '../../../models/requete/requete';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestType} from '../../../models/request/requestType';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {AppConstantMessages} from '../../../../appConstantMessages';
import {TypeRequete} from '../../../models/type-requete/type-requete';
import {Requerant} from '../../../models/requerant/requerant';
import {Pays} from '../../../models/pays/pays';
import {Operateur} from '../../../models/operateur/operateur';
import {GlobalConstantsService} from '../../../services/variables/global-constants.service';
import {RequeteService} from '../../../services/requetes/requete.service';
import {TypeRequeteService} from '../../../services/type-requetes/type-requete.service';
import {RequerantService} from '../../../services/requerants/requerant.service';
import {PaysService} from '../../../services/pays/pays.service';
import {Router} from '@angular/router';
import {Object2Requerant} from '../../../utils/object2Requerant';
import {Object2TypeRequete} from '../../../utils/object2TypeRequete';
import {Object2Requete} from '../../../utils/object2Requete';
import {Object2Pays} from '../../../utils/object2Pays';
import {LockComponent} from '../../lock/lock.component';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-requetes-form',
  templateUrl: './admin-requetes-form.component.html',
  styleUrls: ['./admin-requetes-form.component.css']
})
export class AdminRequetesFormComponent extends LockComponent implements OnInit {

  public requetes: Requete[] = [];

  public requeteForm: FormGroup;

  public requete: Requete;

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

  constructor(private _globalConstants: GlobalConstantsService,
              public _formBuilder: FormBuilder, public _requeteService: RequeteService,
              public _typeRequeteService: TypeRequeteService, public _requerantService: RequerantService,
              public _paysService: PaysService, public _router: Router) {
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

    this.initRequeteList();

    this.initForm(null);

    this.createRequerantForm();

    this.createTypeRequeteForm();
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

      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer une requete';
      this.submitButtonStyle = 'btn-success';
    } else {


      this.requeteForm = this._formBuilder.group({
        typeRequete: [requete.typeRequete.id, [Validators.required]],
        requerant: [requete.requerant.id, [Validators.required]]
      });
      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer une requete';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  editForm(requete: Requete) {
    this._requeteService.get(requete, this.operateur.id).subscribe(response => {
        this.requete = Object2Requete.apply(response);
        this.initForm(this.requete);
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


  create() {
    this.requete = new Requete();
    this.requete.id = null;
    this.requete.operateurId = this.operateur.id;
    this.requete.typeId = this.requeteForm.value.typeRequete;
    this.requete.requerantId = this.requeteForm.value.requerant;

    this._requeteService.create(this.requete, this.operateur.id).subscribe(response => {
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

