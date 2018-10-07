import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AutoriteService} from '../../../services/autorites/autorite.service';
import {Autorite} from '../../../models/autorite/autorite';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestType} from '../../../models/request/requestType';
import {Object2Autorite} from '../../../utils/object2Autorite';
import {RequestVisibility} from '../../../models/request/requestVisibility';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-autorites-form',
  templateUrl: './admin-autorites-form.component.html',
  styleUrls: ['./admin-autorites-form.component.css']
})
export class AdminAutoritesFormComponent implements OnInit {

  public autoriteCreateForm: FormGroup;

  public autorite: Autorite;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  constructor(public _formBuilder: FormBuilder, public _autoriteService: AutoriteService) {
    this.autoriteCreateForm = this._formBuilder.group({
      nom: ['', [Validators.required]],
      prenom: [''],
      poste: ['', [Validators.required]],
    });
  }


  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();
  }


  create() {
    this.autorite = new Autorite(
      null,
      this.autoriteCreateForm.value.nom,
      this.autoriteCreateForm.value.prenom,
      this.autoriteCreateForm.value.poste,
      );

    this._autoriteService.create(this.autorite).subscribe(response => {
        this.autorite = Object2Autorite.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'Autorite cree',
          'L\'autorite ' + this.autorite.nom + ' '+ this.autorite.prenom+' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.autoriteCreateForm.reset();
        this.ngOnInit();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'Autorite non cree',
          'L\'autorite' + this.autorite.nom + ' ' + this.autorite.prenom + 'n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

}

