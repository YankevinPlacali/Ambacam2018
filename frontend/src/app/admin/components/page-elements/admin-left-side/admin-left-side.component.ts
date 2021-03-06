import {Component, OnInit} from '@angular/core';
import {GlobalConstantsService} from '../../../services/variables/global-constants.service';
import {OperateurStd} from '../../../models/operateur/operateurStd';

@Component({
  selector: 'app-admin-left-side',
  templateUrl: './admin-left-side.component.html',
  styleUrls: ['./admin-left-side.component.css']
})
export class AdminLeftSideComponent implements OnInit {

  public operateur: OperateurStd;

  constructor(private _globalConstants: GlobalConstantsService) {
    this.operateur = this._globalConstants.getConnectedOperateur();
  }

  ngOnInit() {
  }

}
