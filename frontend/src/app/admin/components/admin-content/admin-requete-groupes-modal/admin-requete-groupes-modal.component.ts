import {Component, Input, OnChanges, OnInit, SimpleChange, SimpleChanges} from '@angular/core';
import {RequeteGroupe} from '../../../models/requete-groupe/requete-groupe';
import {Object2Requete} from '../../../utils/object2Requete';
import {Strings} from '../../../utils/strings';
import {Requete} from '../../../models/requete/requete';
import {RequeteGroupeService} from '../../../services/requete-groupes/requete-groupe.service';
import {LockComponent} from '../../lock/lock.component';
import {Router} from '@angular/router';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestType} from '../../../models/request/requestType';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {AppConstantMessages} from '../../../../appConstantMessages';
import {RequeteService} from '../../../services/requetes/requete.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Object2Statut} from '../../../utils/object2Statut';
import {StatutService} from '../../../services/statuts/statut.service';
import {Statut} from '../../../models/statut/statut';
import {Operateur} from '../../../models/operateur/operateur';
import {GlobalConstantsService} from '../../../services/variables/global-constants.service';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-requete-groupes-modal',
  templateUrl: './admin-requete-groupes-modal.component.html',
  styleUrls: ['./admin-requete-groupes-modal.component.css']
})
export class AdminRequeteGroupesModalComponent extends LockComponent implements OnInit, OnChanges {

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);
  public submitButtonTitle;
  public submitButtonStyle;
  public formTitle;
  public ids: number[] = [];
  public ids_rg: number[] = [];
  public titleConfirmDialogPrefix = 'Suppression: ';
  public titleConfirmDialog: string;
  public messageConfirmDeleteDialog = AppConstantMessages.CONFIRM;
  public yesConfirmDeleteDialog = AppConstantMessages.YES;
  public noConfirmDeleteDialog = AppConstantMessages.NO;
  public confirm: boolean;
  public allChecked = false;
  private _requeteGroupe: RequeteGroupe;
  public requeteListTitle;

  public requetes: Requete[] = [];
  public allRequetes: Requete[] = [];

  public statutUpForm: FormGroup;
  public sUp_submitButtonTitle;
  public sUp_submitButtonStyle;
  public sUp_formTitle;

  public statuts: Statut[];
  private statusId: number;

  public operateur: Operateur;

  constructor(private _globalConstants: GlobalConstantsService,
              public _requeteGroupeService: RequeteGroupeService,
              public _router: Router,
              public _requeteService: RequeteService,
              private _formBuilder: FormBuilder,
              private _statutService: StatutService) {
    super(_router);
    this.operateur = this._globalConstants.getConnectedOperateur();
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();
    this.initAllRequetes();
    this.createStatutUpForm(true);
  }

  ngOnChanges(changes: SimpleChanges) {
    const requeteGroupe: SimpleChange = changes.requeteGroupe;
    this._requeteGroupe = requeteGroupe.currentValue;
    if (this._requeteGroupe) {
      this.initRequetesOfGroupe(this._requeteGroupe);
    }
  }

  initAllRequetes(): void {
    //TODO: call the right methode to load the list of requetes not belonging to a group
    this._requeteService.list().subscribe(
      response => this.allRequetes = Object2Requete.applyOnArray(response));

  }

  initRequetesOfGroupe(requeteGroupe: RequeteGroupe): void {
    if (requeteGroupe) {
      this.requeteListTitle = 'Liste de Requetes du groupe : ' + requeteGroupe.nom;
      this._requeteGroupeService.getRequetes(requeteGroupe.id, this.operateur.id).subscribe(
        response => this.requetes = Object2Requete.applyOnArray(response));
    }
  }

  @Input()
  set requeteGroupe(requeteGroupe: RequeteGroupe) {
    this._requeteGroupe = requeteGroupe;
  }

  initStatuts() {
    this._statutService.list().subscribe(response => {
        this.statuts = Object2Statut.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  createStatutUpForm(updateGroupStatus: boolean) {
    this.statutUpForm = this._formBuilder.group({
      statut: ['', [Validators.required]],
      groupe: [this._requeteGroupe ? this._requeteGroupe.nom : '', []]
    });

    this.initStatuts();

    if (!updateGroupStatus) {
      this.sUp_submitButtonTitle = 'Modifier Statut';
      this.sUp_formTitle = 'Modifier le statut des ' + this.ids_rg.length + ' requetes selectionnees';
    } else {
      this.sUp_submitButtonTitle = 'Modifier Statut Groupe';
      this.sUp_formTitle = 'Modifier le statut des requetes du groupe';
    }

    this.sUp_submitButtonStyle = 'btn-warning';
  }

  // this function allows to add and remove an id to a list of ids to delete
  assignId(id: number, context: string) {
    if (context === 'REQUETES_GROUPE') {
      const idx = this.ids_rg.indexOf(id, 0);

      if (idx > -1) {
        this.ids_rg.splice(idx, 1);
      } else {
        this.ids_rg.push(id);
      }
    } else if (context === 'REQUETES') {
      const idx = this.ids.indexOf(id, 0);

      if (idx > -1) {
        this.ids.splice(idx, 1);
      } else {
        this.ids.push(id);
      }
    }
  }

  checkAll(allChecked: boolean, context: string) {

    this.allChecked = allChecked;

    if (context === 'REQUETES_GROUPE') {
      this.ids_rg = [];

      if (this.allChecked) {
        this.requetes.forEach(requete => {
          this.ids_rg.push(requete.id);
        });
      }
    } else if (context === 'REQUETES') {
      this.ids = [];

      if (this.allChecked) {
        this.allRequetes.forEach(requete => {
          this.ids.push(requete.id);
        });
      }
    }

    this.ngOnInit();
  }


  updateStatutOfRequetes() {

    this.statusId = parseInt(this.statutUpForm.value.statut);
    // update the status of requetes
    const data = {
      requeteIds: this.ids_rg,
      statusId: this.statusId
    };

    this._requeteService.updateStatusOfRequetes(data, this._requeteGroupe.id, this.operateur.id).subscribe(response => {
        this.ngOnInit();
        this.initRequetesOfGroupe(this._requeteGroupe);
        this.ids_rg = [];
      },
      error => {
        const message = Strings.format(
          'Le statut des requetes n\'a pas ete modifié avec succes. Raison: {0}',
          error.error.message);

        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Statut Requetes non modifiés',
          message,
          RequestVisibility.VISIBLE);
      });
  }

  updateStatutOfGroupe() {

    this.statusId = parseInt(this.statutUpForm.value.statut);
    // update the status of requetes
    const statusId = {
      id: this.statusId
    };

    this._requeteGroupeService.updateGroupeStatus(statusId, this._requeteGroupe.id, this.operateur.id).subscribe(response => {
        this.ngOnInit();
        this.initRequetesOfGroupe(this._requeteGroupe);
        this.ids_rg = [];
        const message = Strings.format(
          'Le statut du groupe {0} a ete modifié avec succes.',
          this._requeteGroupe.nom);

        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Statut Requete Groupe modifié',
          message,
          RequestVisibility.VISIBLE);
      },
      error => {
        const message = Strings.format(
          'Le statut du groupe {0} n\'a pas ete modifié avec succes. Raison: {1}',
          this._requeteGroupe.nom,
          error.error.message);

        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Statut Requetes non modifiés',
          message,
          RequestVisibility.VISIBLE);
      });
  }

  assignGroupe(addModus: boolean) {

    let requeteIds = null;
    if (addModus) {
      // assign group to requetes
      requeteIds = {
        'add': this.ids,
        'remove': []
      };
    } else {
      // remove requetes from group
      requeteIds = {
        'add': [],
        'remove': this.ids_rg
      };
    }

    this._requeteGroupeService.updateGroupe(requeteIds, this._requeteGroupe.id, this.operateur.id).subscribe(response => {
        this.ngOnInit();
        this.initRequetesOfGroupe(this._requeteGroupe);
        this.ids = [];
        this.ids_rg = [];
      },
      error => {
        const message = Strings.format(
          'La requete groupe n\'a pas ete assignee avec succes. Raison: {0}',
          error.error.message);

        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Requete groupe non assignee',
          message,
          RequestVisibility.VISIBLE);
      });

  }

}
