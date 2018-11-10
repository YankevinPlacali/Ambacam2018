import {Component, OnInit} from '@angular/core';
import {TypeRequeteService} from '../../../services/type-requetes/type-requete.service';
import {Object2TypeRequete} from '../../../utils/object2TypeRequete';
import {TypeRequete} from '../../../models/type-requete/type-requete';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestType} from '../../../models/request/requestType';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {AppConstantMessages} from '../../../../appConstantMessages';
import {Strings} from '../../../utils/strings';
import {Location} from '@angular/common';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-type-requetes',
  templateUrl: './admin-type-requetes.component.html',
  styleUrls: ['./admin-type-requetes.component.css']
})

export class AdminTypeRequetesComponent implements OnInit {

  public typeRequetes: TypeRequete[] = [];

  public typeRequeteForm: FormGroup;

  public typeRequete: TypeRequete;

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

  constructor(public _formBuilder: FormBuilder, public _typeRequeteService: TypeRequeteService, private _location: Location) {

  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();

    this.typeRequete = null;

    this.initTypeRequeteList();

    this.initForm(null);
  }

  initTypeRequeteList() {
    this._typeRequeteService.list().subscribe(response => {
        this.typeRequetes = Object2TypeRequete.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initForm(typeRequete: TypeRequete) {
    if (typeRequete == null) {
      this.typeRequeteForm = this._formBuilder.group({
        nom: ['', [Validators.required]],
        description: ['']
      });
      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer un type de requete';
      this.submitButtonStyle = 'btn-success';
    } else {
      this.typeRequeteForm = this._formBuilder.group({
        nom: [typeRequete.nom, [Validators.required]],
        description: [typeRequete.description]
      });
      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer un type de requete';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  editForm(id: number) {
    this._typeRequeteService.get(id).subscribe(response => {
        this.typeRequete = Object2TypeRequete.apply(response);
        this.initForm(this.typeRequete);
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

  submit(typeRequete: TypeRequete) {

    if (typeRequete === null) {
      this.typeRequete = new TypeRequete(
        null,
        this.typeRequeteForm.value.nom,
        this.typeRequeteForm.value.description);

      this._typeRequeteService.create(this.typeRequete).subscribe(response => {
          this.typeRequete = Object2TypeRequete.apply(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            'Type requete cree',
            'Le type requete ' + this.typeRequete.nom + ' a ete cree avec succes.',
            RequestVisibility.VISIBLE);

          this.typeRequeteForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Type requete non cree',
            'Le type requete ' + this.typeRequete.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    } else {

      this.typeRequete = new TypeRequete(
        typeRequete.id,
        this.typeRequeteForm.value.nom,
        this.typeRequeteForm.value.description);
      this._typeRequeteService.update(this.typeRequete.id, this.typeRequete).subscribe(response => {

          this.requestMessage = new RequestMessage(
            RequestType.WARNING,
            'Type requete modifie',
            'Le type requete ' + this.typeRequete.id + ' a ete modifie avec succes.',
            RequestVisibility.VISIBLE);

          this.typeRequeteForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Type requete non modifie',
            'Le type requete ' + this.typeRequete.id + ' n\'a pas ete modifie avec succes. Raison: ' + error.error.message,
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
        this._typeRequeteService.delete(id).subscribe(response => {
            this.ngOnInit();
          },
          error => {
            const message = Strings.format(
              'Le type requete {0} n\'a pas ete supprime avec succes. Raison: {1}',
              id,
              error.error.message);

            this.requestMessage = new RequestMessage(
              RequestType.DANGER,
              'Type requetes non supprimes',
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
      this.typeRequetes.forEach(typeRequete => {
        this.ids.push(typeRequete.id);
      });

      this.titleConfirmDeleteDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDeleteDialogPrefix, this.ids.length);
    }

    this.ngOnInit();
  }

}

