import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PaysService} from '../../../services/pays/pays.service';
import {Pays} from '../../../models/pays/pays';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestType} from '../../../models/request/requestType';
import {Object2Pays} from '../../../utils/object2Pays';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {LockComponent} from '../../lock/lock.component';
import {Router} from '@angular/router';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;


@Component({
  selector: 'app-admin-pays-form',
  templateUrl: './admin-pays-form.component.html',
  styleUrls: ['./admin-pays-form.component.css']
})
export class AdminPaysFormComponent extends LockComponent implements OnInit {

  public pCreateForm: FormGroup;

  public p: Pays;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  constructor(public _router: Router, public _formBuilder: FormBuilder, public _pService: PaysService) {
    super(_router);
    this.pCreateForm = this._formBuilder.group({
      nom: ['', [Validators.required]],
      description: ['']
    });
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();
  }

  create() {
    this.p = new Pays(
      null,
      this.pCreateForm.value.nom,
      this.pCreateForm.value.description);

    this._pService.create(this.p).subscribe(response => {
        this.p = Object2Pays.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Pays cree',
          'Le pays ' + this.p.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.pCreateForm.reset();
        this.ngOnInit();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Pays non cree',
          'Le pays ' + this.p.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

}
