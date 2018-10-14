import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Requerant} from "../../../models/requerant/requerant";
import {RequestMessage} from "../../../models/request/requestMessage";
import {RequestVisibility} from "../../../models/request/requestVisibility";
import {RequestType} from "../../../models/request/requestType";
import {RequerantService} from "../../../services/requerants/requerant.service";
import {Router} from "@angular/router";
import {PaysService} from "../../../services/pays/pays.service";
import {LockComponent} from "../../lock/lock.component";
import {Object2Pays} from "../../../utils/object2Pays";
import {Pays} from "../../../models/pays/pays";
import {Object2Requerant} from "../../../utils/object2Requerant";

// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-requerants-form',
  templateUrl: './admin-requerants-form.component.html',
  styleUrls: ['./admin-requerants-form.component.css']
})
export class AdminRequerantsFormComponent extends LockComponent implements OnInit {

  public requerantCreateForm: FormGroup;

  public requerant: Requerant;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public allPays: Pays[] = [];

  public pForm: FormGroup;

  public p_submitButtonTitle;

  public p_submitButtonStyle;

  public p_formTitle;

  constructor(public _formBuilder: FormBuilder, public _requerantService: RequerantService, public _paysService: PaysService, public _router: Router) {
    super(_router);
    this.requerantCreateForm = this._formBuilder.group({
      nom: ['', [Validators.required]],
      prenom: ['', [Validators.required]],
      dateNaissance: ['', [Validators.required]],
      sexe: ['', [Validators.required]],
      profession: ['', [Validators.required]],
      lieuNaissance: ['', [Validators.required]],
      nationalite: ['', [Validators.required]],
    });

    this.initAllPays();
    this.createPaysForm();
  }

  initAllPays() {
    this._paysService.list().subscribe(response => {
        this.allPays = Object2Pays.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }


  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();
  }

  create() {

    this.requerant = new Requerant();

    this.requerant.id = null;
    this.requerant.nom = this.requerantCreateForm.value.nom;
    this.requerant.prenom = this.requerantCreateForm.value.prenom;
    this.requerant.dateNaissance = this.requerantCreateForm.value.dateNaissance;
    this.requerant.creatorId = 1; // service to get actual connected operator
    this.requerant.sexe = this.requerantCreateForm.value.sexe;
    this.requerant.profession = this.requerantCreateForm.value.profession;
    this.requerant.lieuNaissance = this.requerantCreateForm.value.lieuNaissance;
    this.requerant.paysId = parseInt(this.requerantCreateForm.value.nationalite);


    this._requerantService.create(this.requerant).subscribe(response => {
        this.requerant = Object2Requerant.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Requerant cree',
          'Le requerant ' + this.requerant.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.requerantCreateForm.reset();
        this.ngOnInit();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Requerant non cree',
          'Le requerant ' + this.requerant.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

  createPaysForm() {
    this.pForm = this._formBuilder.group({
      nom: ['', [Validators.required]],
      description: ['']
    });
    this.p_submitButtonTitle = 'Creer';
    this.p_formTitle = 'Creer un pays';
    this.p_submitButtonStyle = 'btn-success';

  }

  submitPays() {
    let p = new Pays(
      null,
      this.pForm.value.nom,
      this.pForm.value.description);

    this._paysService.create(p).subscribe(response => {
        p = Object2Pays.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Pays cree',
          'Le pays ' + p.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.pForm.reset();
        this.initAllPays();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Pays non cree',
          'Le pays ' + p.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }


}

