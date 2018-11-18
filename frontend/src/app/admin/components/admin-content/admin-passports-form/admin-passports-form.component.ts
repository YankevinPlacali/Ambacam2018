import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AutoriteService} from '../../../services/autorites/autorite.service';
import {Autorite} from '../../../models/autorite/autorite';
import {Object2Autorite} from '../../../utils/object2Autorite';
import {LockComponent} from '../../lock/lock.component';
import {PassportRead, PassportStd, PassportWrite} from '../../../models/passport/passport';
import {PassportService} from '../../../services/passports/passport.service';
import {Object2PassportRead} from '../../../utils/object2Passport';
import {Strings} from '../../../utils/strings';

@Component({
  selector: 'app-admin-passports-form',
  templateUrl: './admin-passports-form.component.html',
  styleUrls: ['./admin-passports-form.component.css']
})
export class AdminPassportsFormComponent extends LockComponent implements OnInit, OnChanges {

  public passportForm: FormGroup;
  public autorites: Autorite[];
  private passport = null;
  private _requeteId;
  private _passportId;
  @Output()
  passports = new EventEmitter<PassportRead[]>();

  constructor(public _autoriteService: AutoriteService,
              public _passportService: PassportService,
              public _router: Router,
              public _formBuilder: FormBuilder) {
    super(_router);
    this.createPassportForm(null);
  }

  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges) {
    const passportId: SimpleChange = changes.passportId;
    if (this._passportId) {
      this._passportId = passportId.currentValue;

      this._passportService.get(this._passportId).subscribe(response => {
          this.passport = Object2PassportRead.apply(response);
          this.createPassportForm(this.passport);
        },
        error => {
          console.log(error);
        });
    }
  }

  get requeteId(): number {
    return this._requeteId;
  }

  @Input()
  set requeteId(requeteId: number) {
    this._requeteId = requeteId;
  }

  get passportId(): number {
    return this._passportId;
  }

  @Input()
  set passportId(passportId: number) {
    this._passportId = passportId;
  }

  createPassportForm(passportRead: PassportRead) {

    if (passportRead === null) {
      this.passportForm = this._formBuilder.group({
        numero: ['', [Validators.required]],
        dateDelivrance: ['', [Validators.required]],
        dateExpiration: ['', [Validators.required]],
        lieuDelivrance: ['', [Validators.required]],
        autoriteId: ['', [Validators.required]]
      });
    } else {
      this.passportForm = this._formBuilder.group({
        numero: [passportRead.numero, [Validators.required]],
        dateDelivrance: [new Date(passportRead.dateDelivrance).toISOString().substring(0, 10), [Validators.required]],
        dateExpiration: [new Date(passportRead.dateExpiration).toISOString().substring(0, 10), [Validators.required]],
        lieuDelivrance: [passportRead.lieuDelivrance, [Validators.required]],
        autoriteId: [passportRead.delivrePar.id, [Validators.required]]
      });
    }
    this.initAllAutorites();
  }

  initAllAutorites() {
    this._autoriteService.list().subscribe(response => {
        this.autorites = Object2Autorite.applyOnArray(response);
      },
      error => {
        console.log(error);
      });
  }

  submitPassport() {
    let passportWrite: PassportWrite;
    passportWrite = new PassportWrite(
      this.passportForm.value.numero,
      this.passportForm.value.dateDelivrance,
      this.passportForm.value.dateExpiration,
      this.passportForm.value.lieuDelivrance,
      this.passportForm.value.autoriteId
    );

    if (!this.passportId) {
      this._passportService.create(this._requeteId, passportWrite).subscribe(response => {
          this._passportService.list(this._requeteId).subscribe(responseList => {
              this.passports.emit(Object2PassportRead.applyOnArray(responseList));
              this.passport = null;
            },
            error => {
              console.log(error);
              this.passport = null;
            });
        },
        error => {
          this.passport = null;
        });
    } else {
      this._passportService.update(this.passportId, passportWrite).subscribe(response => {
          this._passportService.list(this._requeteId).subscribe(responseList => {
              this.passports.emit(Object2PassportRead.applyOnArray(responseList));
              this.passport = null;
            },
            error => {
              console.log(error);
              this.passport = null;
            });
        },
        error => {
          this.passport = null;
        });
    }
  }
}
