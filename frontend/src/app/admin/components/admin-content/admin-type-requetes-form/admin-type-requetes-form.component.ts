import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TypeRequeteService} from '../../../services/type-requetes/type-requete.service';
import {TypeRequete} from '../../../models/type-requete/type-requete';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestType} from '../../../models/request/requestType';
import {Object2TypeRequete} from '../../../utils/object2TypeRequete';
import {RequestVisibility} from '../../../models/request/requestVisibility';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;


@Component({
  selector: 'app-admin-type-requetes-form',
  templateUrl: './admin-type-requetes-form.component.html',
  styleUrls: ['./admin-type-requetes-form.component.css']
})

export class AdminTypeRequetesFormComponent implements OnInit {

  public typeRequeteCreateForm: FormGroup;

  public typeRequete: TypeRequete;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  constructor(public _formBuilder: FormBuilder, public _typeRequeteService: TypeRequeteService) {
    this.typeRequeteCreateForm = this._formBuilder.group({
      nom: ['', [Validators.required]],
      description: ['']
    });
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();
  }

  create() {
    this.typeRequete = new TypeRequete(
      null,
      this.typeRequeteCreateForm.value.nom,
      this.typeRequeteCreateForm.value.description);

    this._typeRequeteService.create(this.typeRequete).subscribe(response => {
        this.typeRequete = Object2TypeRequete.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Type requete cree',
          'Le type requete ' + this.typeRequete.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.typeRequeteCreateForm.reset();
        this.ngOnInit();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Type requete non cree',
          'Le type requete ' + this.typeRequete.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

}

