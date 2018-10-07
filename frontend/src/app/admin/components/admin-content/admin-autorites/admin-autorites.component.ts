import {Component, OnInit} from '@angular/core';
import {AutoriteService} from '../../../services/autorites/autorite.service';
import {Object2Autorite} from '../../../utils/object2Autorite';
import {Autorite} from '../../../models/autorite/autorite';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RequestType} from '../../../models/request/requestType';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {AppConstantMessages} from '../../../../appConstantMessages';
import {Strings} from '../../../utils/strings';
import {Router} from '@angular/router';
import {LockComponent} from '../../lock/lock.component';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;


@Component({
  selector: 'app-admin-autorites',
  templateUrl: './admin-autorites.component.html',
  styleUrls: ['./admin-autorites.component.css']
})
export class AdminAutoritesComponent extends LockComponent implements OnInit {

  public autorites: Autorite[] = [];

  public autoriteForm: FormGroup;

  public autorite: Autorite;

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

  constructor(public _formBuilder: FormBuilder, public _autoriteService: AutoriteService, public _router: Router) {
    super(_router);
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();

    this.initAutoriteList();

    this.initForm(null);
  }

  initAutoriteList() {
    this._autoriteService.list().subscribe(response => {
        this.autorites = Object2Autorite.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  initForm(autorite: Autorite) {
    if (autorite == null) {
      this.autoriteForm = this._formBuilder.group({
        nom: ['', [Validators.required]],
        prenom: [''],
        poste: ['', [Validators.required]]
      });
      this.submitButtonTitle = 'Creer';
      this.formTitle = 'Creer une autorite';
      this.submitButtonStyle = 'btn-success';
    } else {
      this.autoriteForm = this._formBuilder.group({
        nom: [autorite.nom, [Validators.required]],
        prenom: [autorite.prenom],
        poste: [autorite.poste, [Validators.required]],
      });
      this.submitButtonTitle = 'Mettre a jour';
      this.formTitle = 'Editer une autorite';
      this.submitButtonStyle = 'btn-warning';
    }
  }

  editForm(id: number) {
    this._autoriteService.get(id).subscribe(response => {
        this.autorite = Object2Autorite.apply(response);
        this.initForm(this.autorite);
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

  submit(autorite: Autorite) {

    if (autorite === undefined) {
      this.autorite = new Autorite(
        null,
        this.autoriteForm.value.nom,
        this.autoriteForm.value.prenom,
        this.autoriteForm.value.poste);

      this._autoriteService.create(this.autorite).subscribe(response => {
          this.autorite = Object2Autorite.apply(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            'Autorite cree',
            'L\'autorite ' + this.autorite.nom +' '+ this.autorite.prenom + ' a ete cree avec succes.',
            RequestVisibility.VISIBLE);

          this.autoriteForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Autorite non cree',
            'Le autorite ' + this.autorite.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
            RequestVisibility.VISIBLE);
        });
    } else {

      this.autorite = new Autorite(
        autorite.id,
        this.autoriteForm.value.nom,
        this.autoriteForm.value.prenom,
        this.autoriteForm.value.poste);
      this._autoriteService.update(this.autorite.id, this.autorite).subscribe(response => {

          this.requestMessage = new RequestMessage(
            RequestType.WARNING,
            'Autorite modifie',
            'Le autorite ' + this.autorite.id + ' a ete modifie avec succes.',
            RequestVisibility.VISIBLE);

          this.autoriteForm.reset();
          this.ngOnInit();
        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            'Autorite non modifie',
            'L\'autorite ' + this.autorite.id + ' n\'a pas ete modifie avec succes. Raison: ' + error.error.message,
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
        this._autoriteService.delete(id).subscribe(response => {
            this.ngOnInit();
          },
          error => {
            const message = Strings.format(
              'L\'autorite {0} n\'a pas ete supprime avec succes. Raison: {1}',
              id,
              error.error.message);

            this.requestMessage = new RequestMessage(
              RequestType.DANGER,
              'Autorites non supprimes',
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
      this.autorites.forEach(autorite => {
        this.ids.push(autorite.id);
      });

      this.titleConfirmDeleteDialog = Strings.format('{0} {1} element(s)', this.titleConfirmDeleteDialogPrefix, this.ids.length);
    }

    this.ngOnInit();
  }

}
