import {Component, OnInit} from '@angular/core';
import {OperateurService} from '../../../services/operateurs/operateur.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Object2OperateurStd} from '../../../utils/object2OperateurStd';
import {RequestType} from '../../../models/request/requestType';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {RequestMessage} from '../../../models/request/requestMessage';
import {Router} from '@angular/router';
import {OperateurStd} from '../../../models/operateur/operateurStd';
import {LockComponent} from '../../lock/lock.component';
import {OperateurCreate} from '../../../models/operateur/operateurCreate';
import {Auth} from '../../../utils/auth/auth';
import {Pays} from '../../../models/pays';
import {PaysService} from '../../../services/pays/pays.service';
import {Object2Pays} from '../../../utils/object2Pays';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-operateurs-form',
  templateUrl: './admin-operateurs-form.component.html',
  styleUrls: ['./admin-operateurs-form.component.css']
})
export class AdminOperateursFormComponent extends LockComponent implements OnInit {


  public operateurCreateForm: FormGroup;

  public operateurCreate: OperateurCreate;

  public operateurStd: OperateurStd;

  public pays: Pays[];

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  constructor(public _router: Router, public _formBuilder: FormBuilder, public _operateurService: OperateurService, public _paysService: PaysService) {
    super(_router);
    this.operateurCreateForm = this._formBuilder.group({
      nom: ['', [Validators.required]],
      prenom: ['', [Validators.required]],
      sexe: ['', Validators.required],
      paysId: ['', Validators.required],
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });

    this._paysService.list().subscribe(response => {
        this.pays = Object2Pays.applyOnArray(response);
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
    const creatorId = Auth.getOperateur().id;
    this.operateurCreate = new OperateurCreate(
      this.operateurCreateForm.value.nom,
      this.operateurCreateForm.value.prenom,
      this.operateurCreateForm.value.sexe,
      this.operateurCreateForm.value.paysId,
      this.operateurCreateForm.value.username,
      this.operateurCreateForm.value.password,
      creatorId);

    this._operateurService.create(this.operateurCreate).subscribe(response => {
        this.operateurStd = Object2OperateurStd.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Operateur cree',
          'L\' operateur ' + this.operateurStd.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.operateurCreateForm.reset();
        this.ngOnInit();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Operateur non cree',
          'L\' operateurStd ' + this.operateurCreate.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

}
