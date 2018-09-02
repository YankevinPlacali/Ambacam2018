import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RoleService} from '../../../services/roles/role.service';
import {Role} from '../../../models/role/role';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestType} from '../../../models/request/requestType';
import {Object2Role} from '../../../utils/object2Role';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {LockComponent} from '../../lock/lock.component';
import {Router} from '@angular/router';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;


@Component({
  selector: 'app-admin-roles-form',
  templateUrl: './admin-roles-form.component.html',
  styleUrls: ['./admin-roles-form.component.css']
})
export class AdminRolesFormComponent extends LockComponent implements OnInit {

  public roleCreateForm: FormGroup;

  public role: Role;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public nomPattern = '^([A-Z_]*)$';

  constructor(public _router: Router, public _formBuilder: FormBuilder, public _roleService: RoleService) {
    super(_router);
    this.roleCreateForm = this._formBuilder.group({
      nom: ['', [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
      description: ['']
    });
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();
  }

  create() {
    this.role = new Role(
      null,
      this.roleCreateForm.value.nom,
      this.roleCreateForm.value.description);

    this._roleService.create(this.role).subscribe(response => {
        this.role = Object2Role.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Role cree',
          'Le role ' + this.role.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.roleCreateForm.reset();
        this.ngOnInit();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Role non cree',
          'Le role ' + this.role.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

}
