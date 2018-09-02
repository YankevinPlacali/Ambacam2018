import {Component, OnInit} from '@angular/core';
import {UserType} from '../admin/models/userType';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthServiceService} from '../admin/services/auth/auth-service.service';
import {ObjectToAuthResponse} from '../admin/utils/objectToAuthResponse';
import {AuthResponseSuccess} from '../admin/models/auth/authResponseSuccess';
import {RequestVisibility} from '../admin/models/request/requestVisibility';
import {RequestType} from '../admin/models/request/requestType';
import {RequestMessage} from '../admin/models/request/requestMessage';
import {Strings} from '../admin/utils/strings';
import {Auth} from '../admin/utils/auth/auth';
import {Object2Operateur} from '../admin/utils/object2Operateur';
import {ActivatedRoute, Router} from '@angular/router';
import {Operateur} from '../admin/models/operateur/operateur';

@Component({
  selector: 'app-auth-form',
  templateUrl: './auth-form.component.html',
  styleUrls: ['./auth-form.component.css']
})

export class AuthFormComponent implements OnInit {

  public userType: string;
  public typeOperateur;
  public typeRequerant;

  public authAsOperateurForm: FormGroup;
  public authAsRequerantForm: FormGroup;

  public dateOfBirthPattern = '^[0-9]{2}/[0-9]{2}/[0-9]{4}$';

  public authResponseSuccess: AuthResponseSuccess;

  public requestMessage: RequestMessage = new RequestMessage('', RequestType.DEFAULT, '', RequestVisibility.INVISIBLE);

  public operateur: Operateur;

  public AUTH_SUCCESS_MESSAGE = 'Operation successful';

  public GET_OPERATEUR_ERROR_MESSAGE = 'Get Operateur failure';

  constructor(public _formBuilder: FormBuilder, public _authService: AuthServiceService, public _route: ActivatedRoute,
              public _router: Router) {
    this.typeOperateur = UserType.OPERATEUR;
    this.typeRequerant = UserType.REQUERANT;
    this.userType = this.typeOperateur;

    this.authAsOperateurForm = this._formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });

    this.authAsRequerantForm = this._formBuilder.group({
      identifier: ['', [Validators.required, Validators.required]],
      dateOfBirth: ['', [Validators.required, Validators.pattern(this.dateOfBirthPattern)]]
    });
  }

  ngOnInit() {
  }

  authenticate(userType: string) {
    this.userType = userType;

    if (this.userType === UserType.OPERATEUR) {
      this._authService.authenticateAsOperateur(
        this.authAsOperateurForm.value.username,
        this.authAsOperateurForm.value.password).subscribe(response => {

          this.authResponseSuccess = ObjectToAuthResponse.applyOnSuccess(response);

          this.requestMessage = new RequestMessage(
            RequestType.SUCCESS,
            this.AUTH_SUCCESS_MESSAGE,
            Strings.format(this.AUTH_SUCCESS_MESSAGE + ', Expires in: {0}', this.authResponseSuccess.expiresIn),
            RequestVisibility.VISIBLE);

          // save the operateur token
          Auth.store('operateur.token', this.authResponseSuccess.accessToken);

          const valueTO = {
            value: this.authAsOperateurForm.value.username
          };

          this._authService.findOperateurByUsername(valueTO).subscribe(responseFindOperateur => {

              // save the operateur
              Auth.storeOperateur(Object2Operateur.apply(responseFindOperateur));
              this._router.navigate(['admin']);
            },
            errorFindOperateur => {
              this.requestMessage = new RequestMessage(
                RequestType.DANGER,
                this.GET_OPERATEUR_ERROR_MESSAGE,
                'Raison: ' + errorFindOperateur.error.message,
                RequestVisibility.VISIBLE);
            });

        },
        error => {
          this.requestMessage = new RequestMessage(
            RequestType.DANGER,
            error.error.error,
            'Raison: ' + error.error.error_description,
            RequestVisibility.VISIBLE);
        });
    }

    if (this.userType === UserType.REQUERANT) {

    }

  }
}
