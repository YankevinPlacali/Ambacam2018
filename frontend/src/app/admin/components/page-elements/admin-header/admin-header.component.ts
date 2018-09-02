import {Component, OnInit} from '@angular/core';
import {OperateurStd} from '../../../models/operateur/operateurStd';
import {GlobalConstantsService} from '../../../services/variables/global-constants.service';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrls: ['./admin-header.component.css']
})
export class AdminHeaderComponent implements OnInit {

  public operateur: OperateurStd;

  constructor(private _globalConstants: GlobalConstantsService) {
    this.operateur = this._globalConstants.getConnectedOperateur();
  }

  ngOnInit() {
  }

}
