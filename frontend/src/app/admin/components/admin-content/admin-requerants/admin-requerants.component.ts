import {Component, OnInit, SimpleChange, OnChanges} from '@angular/core';
import {Component, OnInit} from '@angular/core';
import {Requerant} from '../../../models/requerant/requerant';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestType} from '../../../models/request/requestType';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {AppConstantMessages} from '../../../../appConstantMessages';
import {RequerantService} from '../../../services/requerants/requerant.service';
import {Router} from '@angular/router';
import {Object2Requerant} from '../../../utils/object2Requerant';
import {Strings} from '../../../utils/strings';
import {LockComponent} from '../../lock/lock.component';
import {Pays} from '../../../models/pays/pays';
import {PaysService} from '../../../services/pays/pays.service';
import {Object2Pays} from '../../../utils/object2Pays';
import {OperateurService} from '../../../services/operateurs/operateur.service';
import {Object2OperateurStd} from '../../../utils/object2OperateurStd';
import {OperateurStd} from '../../../models/operateur/operateurStd';
import {RequerantCriteria} from '../../../models/requerant/requerantCriteria';
import {RequerantSearchResult} from '../../../models/requerant/requerantSearchResult';
import {PaginationComponent} from '../../../../components/pagination/pagination.component';

// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-requerants',
  templateUrl: './admin-requerants.component.html',
  styleUrls: ['./admin-requerants.component.css'],
  entryComponents: [PaginationComponent]
})
export class AdminRequerantsComponent extends LockComponent implements OnInit, OnChanges {

  public operateurs: OperateurStd[] = [];

  public requerants: Requerant[] = [];

  public requerantForm: FormGroup;

  public requerantSearchForm: FormGroup;

  public requerant: Requerant;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public submitButtonTitle;

  public submitButtonStyle;

  public formTitle;

  public searchFormTitle;

  public ids: number[] = [];

  public titleConfirmDeleteDialogPrefix = 'Suppression: ';

  public titleConfirmDeleteDialog: string;

  public messageConfirmDeleteDialog = AppConstantMessages.CONFIRM;

  public yesConfirmDeleteDialog = AppConstantMessages.YES;

  public noConfirmDeleteDialog = AppConstantMessages.NO;

  public confirm: boolean;

  public allChecked = false;

  public allPays: Pays[];

  public requerantSearchResult: RequerantSearchResult;

  public criteria: RequerantCriteria;

  public page = 0;

  public totalPages = 1;

  constructor(public _formBuilder: FormBuilder,
              public _requerantService: RequerantService,
              public _operateurService: OperateurService,
              public _paysService: PaysService,
              public _router: Router) {
    super(_router);
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();

    this.criteria = new RequerantCriteria('', null, null, null, null);

    this.initRequerantList(null, 0, 1);

    this.requerant = null;

    this.initForm(null);

    this.initSearchForm();

    this.searchFormTitle = 'Rechercher un requerant';
  }

  ngOnChanges(changes: { [propKey: string]: SimpleChange }) {

  }

  initRequerantList(requerants: Requerant[], page: number, totalPages: number) {
    if (requerants === null) {
      this._requerantService.list().subscribe(response => {
          this.requerants = Object2Requerant.applyOnArray(response);
          this.totalPages = totalPages;
          this.page = page;
        },
        error => {
          console.log(error);
        });
    } else {
      this.requerants = requerants;
      this.totalPages = totalPages;
      this.page = page;
    }
  }

  initOperateurList() {
    this._operateurService.list().subscribe(response => {
        this.operateurs = Object2OperateurStd.applyOnArray(response);
      },
      error => {
        console.log(error);
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

  initForm(requerant: Requerant) {
    if (requerant == null) {
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

      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer un requerant';
      this.submitButtonStyle = 'btn-success';
    } else {
      this.requerantForm = this._formBuilder.group({
        nom: [requerant.nom, [Validators.required]],
        prenom: [requerant.prenom, [Validators.required]],
        dateNaissance: [requerant.dateNaissance, [Validators.required]],
        sexe: [requerant.sexe, [Validators.required]],
        profession: [requerant.profession, [Validators.required]],
        lieuNaissance: [requerant.lieuNaissance, [Validators.required]],
        nationalite: [this.getPaysId(requerant.nationalite), [Validators.required]],
      });
      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer un requerant';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  initSearchForm() {
    this.requerantSearchForm = this._formBuilder.group({
      keyword: [this.criteria.keyword],
      dateNaissance: [this.criteria.dateNaissance],
      creatorId: [''],
      creeLeBefore: [this.criteria.creeLeBefore],
      creeLeAfter: [this.criteria.creeLeAfter]
    });

    this.initOperateurList();
  }

  search(page: number) {

    this.criteria = new RequerantCriteria(
      this.requerantSearchForm.value.keyword,
      this.requerantSearchForm.value.dateNaissance,
      this.requerantSearchForm.value.creatorId,
      this.requerantSearchForm.value.creeLeBefore,
      this.requerantSearchForm.value.creeLeAfter
    );

    if (page != null) {
      this._requerantService.search(this.criteria, page).subscribe(response => {
          this.requerantSearchResult = Object2Requerant.applyOnSearchResult(response);
          this.initRequerantList(
            this.requerantSearchResult.content,
            this.requerantSearchResult.page,
            this.requerantSearchResult.totalPages);
        },
        error => {
          console.log(error);
        });
    }
  }

  clearForm() {

    this.criteria = new RequerantCriteria('', null, null, null, null);

    this.initSearchForm();
  }

  getPaysId(nationalite): number {

    let paysId = -1;
    this.allPays.forEach(pays => {
      if (pays.nom === nationalite) {
        paysId = pays.id;
      }
    });
    return paysId;

  }

  editForm(id: number) {
    this._requerantService.get(id).subscribe(response => {
        this.requerant = Object2Requerant.apply(response);
        this.initForm(this.requerant);
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

  submit(requerant: Requerant) {

    this.requerant = new Requerant();

    this.requerant.id = null;
    this.requerant.nom = this.requerantForm.value.nom;
    this.requerant.prenom = this.requerantForm.value.prenom;
    this.requerant.dateNaissance = this.requerantForm.value.dateNaissance;
    this.requerant.creatorId = 1; // service to get actual connected operator
    this.requerant.sexe = this.requerantForm.value.sexe;
    this.requerant.profession = this.requerantForm.value.profession;
    this.requerant.lieuNaissance = this.requerantForm.value.lieuNaissance;
    this.requerant.paysId = parseInt(this.requerantForm.value.nationalite);

    if (requerant === null) {

      this._requerantService.create(this.requerant).subscribe(response => {
          this.requerant = Object2Requerant.apply(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            'Requerant cree',
            'Le requerant ' + this.requerant.nom + ' a ete cree avec succes.',
            RequestVisibility.VISIBLE);

          this.requerantForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Requerant non cree',
            'Le requerant ' + this.requerant.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    } else {

      this.requerant.id = requerant.id;

      this._requerantService.update(this.requerant.id, this.requerant).subscribe(response => {

          this.requestMessage = new RequestMessage(
            RequestType.WARNING,
            'Requerant modifie',
            'Le requerant ' + this.requerant.id + ' a ete modifie avec succes.',
            RequestVisibility.VISIBLE);

          this.requerantForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Requerant non modifie',
            'Le requerant ' + this.requerant.id + ' n\'a pas ete modifie avec succes. Raison: ' + error.error.message,
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
        this._requerantService.delete(id).subscribe(response => {
            this.ngOnInit();
          },
          error => {
            const message = Strings.format(
              'Le requerant {0} n\'a pas ete supprime avec succes. Raison: {1}',
              id,
              error.error.message);

            this.requestMessage = new RequestMessage(
              RequestType.DANGER,
              'Requerants non supprimes',
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
      this.requerants.forEach(requerant => {
        this.ids.push(requerant.id);
      });

      this.titleConfirmDeleteDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDeleteDialogPrefix, this.ids.length);
    }

    this.ngOnInit();
  }

}
