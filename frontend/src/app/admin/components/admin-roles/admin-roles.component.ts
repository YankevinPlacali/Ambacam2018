import {Component, OnInit} from '@angular/core';
import {RoleService} from '../../services/roles/role.service';
import {Object2Role} from '../../utils/object2Role';
import {Role} from '../../models/role/role';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestType} from '../../models/request/requestType';
import {RequestMessage} from '../../models/request/requestMessage';
import {RequestVisibility} from '../../models/request/requestVisibility';
import {AppConstantMessages} from '../../../appConstantMessages';
import {Strings} from '../../utils/strings';
import {Location} from '@angular/common';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-roles',
  templateUrl: './admin-roles.component.html',
  styleUrls: ['./admin-roles.component.css']
})
export class AdminRolesComponent implements OnInit {

  public roles: Role[] = [];

  public roleForm: FormGroup;

  public role: Role;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public nomPattern = '^([A-Z_]*)$';

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

  constructor(public _formBuilder: FormBuilder, public _roleService: RoleService, private _location: Location) {

  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();

    this.initRoleList();

    this.initForm(null);
  }

  initRoleList() {
    this._roleService.list().subscribe(response => {
        this.roles = Object2Role.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initForm(role: Role) {
    if (role == null) {
      this.roleForm = this._formBuilder.group({
        nom: ['', [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
        description: ['']
      });
      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer un role';
      this.submitButtonStyle = 'btn-success';
    } else {
      this.roleForm = this._formBuilder.group({
        nom: [role.nom, [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
        description: [role.description]
      });
      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer un role';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  editForm(id: number) {
    this._roleService.get(id).subscribe(response => {
        this.role = Object2Role.apply(response);
        this.initForm(this.role);
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

  submit(role: Role) {

    if (role === undefined) {
      this.role = new Role(
        null,
        this.roleForm.value.nom,
        this.roleForm.value.description);

      this._roleService.create(this.role).subscribe(response => {
          this.role = Object2Role.apply(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            'Role cree',
            'Le role ' + this.role.nom + ' a ete cree avec succes.',
            RequestVisibility.VISIBLE);

          this.roleForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Role non cree',
            'Le role ' + this.role.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    } else {

      this.role = new Role(
        role.id,
        this.roleForm.value.nom,
        this.roleForm.value.description);
      this._roleService.update(this.role.id, this.role).subscribe(response => {

          this.requestMessage = new RequestMessage(
            RequestType.WARNING,
            'Role modifie',
            'Le role ' + this.role.id + ' a ete modifie avec succes.',
            RequestVisibility.VISIBLE);

          this.roleForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Role non modifie',
            'Le role ' + this.role.id + ' n\'a pas ete modifie avec succes. Raison: ' + error.error.message,
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
        this._roleService.delete(id).subscribe(response => {
            this.ngOnInit();
          },
          error => {
            const message = Strings.format(
              'Le role {0} n\'a pas ete supprime avec succes. Raison: {1}',
              id,
              error.error.message);

            this.requestMessage = new RequestMessage(
              RequestType.DANGER,
              'Roles non supprimes',
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
      this.roles.forEach(role => {
        this.ids.push(role.id);
      });

      this.titleConfirmDeleteDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDeleteDialogPrefix, this.ids.length);
    }

    this.ngOnInit();
  }

}
