import { Component, OnInit } from '@angular/core';
import {LockComponent} from '../../lock/lock.component';
import {Router} from '@angular/router';
import {RequestType} from '../../../models/request/requestType';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Statut} from '../../../models/statut/statut';
import {RequestMessage} from '../../../models/request/requestMessage';
import {Object2Statut} from '../../../utils/object2Statut';
import {StatutService} from '../../../services/statuts/statut.service';
import {RequestVisibility} from '../../../models/request/requestVisibility';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-statuts-form',
  templateUrl: './admin-statuts-form.component.html',
  styleUrls: ['./admin-statuts-form.component.css']
})
export class AdminStatutsFormComponent  extends LockComponent  implements OnInit {

  public statutCreateForm: FormGroup;

  public statut: Statut;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public nomPattern = '[A-Z][a-zA-Z0-9_].*';

  constructor(public _router: Router, public _formBuilder: FormBuilder, public _statutService: StatutService) {
    super(_router);
    this.statutCreateForm = this._formBuilder.group({
      nom: ['', [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
      description: ['',  [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]]
    });
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();
  }

  create() {
    this.statut = new Statut(
      null,
      this.statutCreateForm.value.nom,
      this.statutCreateForm.value.description);

    this._statutService.create(this.statut).subscribe(response => {
        this.statut = Object2Statut.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Statut cree',
          'Le statut ' + this.statut.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.statutCreateForm.reset();
        this.ngOnInit();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Statut non cree',
          'Le statut ' + this.statut.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

}
