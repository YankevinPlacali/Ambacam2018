import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MotifSuppressionService} from '../../../services/motif-suppression/motif-suppression.service';
import {MotifSuppression} from '../../../models/motif-suppression/motifSuppression';
import {RequestMessage} from '../../../models/request/requestMessage';
import {RequestType} from '../../../models/request/requestType';
import {Object2Motif} from '../../../utils/object2Motif';
import {RequestVisibility} from '../../../models/request/requestVisibility';
import {LockComponent} from '../../lock/lock.component';
import {Router} from '@angular/router';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;


@Component({
  selector: 'app-admin-motif-form',
  templateUrl: './admin-motif-suppression-form.component.html',
  styleUrls: ['./admin-motif-suppression-form.component.css']
})
export class AdminMotifSuppressionFormComponent extends LockComponent implements OnInit {

  public motifCreateForm: FormGroup;

  public motif: MotifSuppression;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public nomPattern = '^([A-Z_]*)$';
  public descriptionPattern = '^[A-Za-z0-9- ]*$';

  constructor(public _router: Router, public _formBuilder: FormBuilder, public _motifService: MotifSuppressionService) {
    super(_router);
    this.motifCreateForm = this._formBuilder.group({
      nom: ['', [Validators.required, Validators.required, Validators.pattern(this.nomPattern)]],
      description: ['',[Validators.required, Validators.required, Validators.pattern(this.descriptionPattern)]]
    });
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();
  }

  create() {
    this.motif = new MotifSuppression(
      null,
      this.motifCreateForm.value.nom,
      this.motifCreateForm.value.description);

    this._motifService.create(this.motif).subscribe(response => {
        this.motif = Object2Motif.apply(response);

        this.requestMessage = new RequestMessage(
          RequestType.SUCCESS,
          'MotifSuppression cree',
          'Le motif-suppression ' + this.motif.nom + ' a ete cree avec succes.',
          RequestVisibility.VISIBLE);

        this.motifCreateForm.reset();
        this.ngOnInit();
      },
      error => {
        this.requestMessage = new RequestMessage(
          RequestType.DANGER,
          'MotifSuppression non cree',
          'Le motif-suppression ' + this.motif.nom + ' n\'a pas ete cree avec succes. Raison: ' + error.error.message,
          RequestVisibility.VISIBLE);
      });
  }

}
