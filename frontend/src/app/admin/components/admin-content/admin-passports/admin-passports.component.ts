import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges} from '@angular/core';
import {Object2PassportRead} from '../../../utils/object2Passport';
import {Strings} from '../../../utils/strings';
import {Object2Requete} from '../../../utils/object2Requete';
import {PassportService} from '../../../services/passports/passport.service';
import {PassportRead} from '../../../models/passport/passport';
import {RequeteService} from '../../../services/requetes/requete.service';
import {LockComponent} from '../../lock/lock.component';
import {Router} from '@angular/router';
import {Requete} from '../../../models/requete/requete';
import {AdminPassportsFormComponent} from '../admin-passports-form/admin-passports-form.component';
import {Validators} from '@angular/forms';

@Component({
  selector: 'app-admin-passports',
  templateUrl: './admin-passports.component.html',
  styleUrls: ['./admin-passports.component.css'],
  entryComponents: [AdminPassportsFormComponent]
})
export class AdminPassportsComponent extends LockComponent implements OnInit, OnChanges {

  public passportListTitle = 'Requerants > {0} > Requetes > {1} > Passports';
  public passportEditTitle = this.passportListTitle + ' > {2}';
  public passports: PassportRead[] = [];
  public passport: PassportRead;
  public requete: Requete;
  public passportId;
  private _requeteId;

  constructor(public _passportService: PassportService,
              public _requeteService: RequeteService,
              public _router: Router) {
    super(_router);
  }

  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges) {
    const requeteId: SimpleChange = changes.requeteId;
    this._requeteId = requeteId.currentValue;
    if (this._requeteId) {
      this.getPassports(this.requeteId);
    }
  }

  get requeteId(): number {
    return this._requeteId;
  }

  @Input()
  set requeteId(requeteId: number) {
    this._requeteId = requeteId;
  }


  getPassports(requeteId: number) {
    this.passportListTitle = 'Requerants > {0} > Requetes > {1} > Passports';
    this._requeteService.get(requeteId).subscribe(response => {
        this.requete = Object2Requete.apply(response);
        this.passportListTitle = Strings.format(
          this.passportListTitle,
          this.requete.requerant.nom + ' ' + this.requete.requerant.prenom,
          this.requete.id);
      },
      error => {
        console.log(error);
      });

    this._passportService.list(requeteId).subscribe(response => {
        this.initPassportsList(Object2PassportRead.applyOnArray(response));
      },
      error => {
        console.log(error);
      });
  }

  initPassportsList(passports: PassportRead[]) {
    this.passports = passports;
    this.passportId = null;
  }

  editForm(requeteId: number, passportId: number) {
    this.passportId = passportId;
    this._passportService.get(passportId).subscribe(response => {
        this.passport = Object2PassportRead.apply(response);

        this.passportEditTitle = this.passportListTitle + ' > {0}';
        this.passportEditTitle = Strings.format(this.passportEditTitle, this.passport.numero);
      },
      error => {
        console.log(error);
      });
  }
}
