import {Component, OnInit} from '@angular/core';
import {LockComponent} from '../../lock/lock.component';
import {ActivatedRoute, Router} from '@angular/router';
import {OperateurStd} from '../../../models/operateur/operateurStd';
import {Auth} from '../../../utils/auth/auth';
import {AuthServiceService} from '../../../services/auth/auth-service.service';

@Component({
  selector: 'app-admin-logout',
  templateUrl: './admin-logout.component.html',
  styleUrls: ['./admin-logout.component.css']
})
export class AdminLogoutComponent implements OnInit {

  constructor(public _router: Router, public _authService: AuthServiceService) {

    this._authService.logout().subscribe(response => {
        this.close();
      },
      error => {

      });
  }

  ngOnInit() {
  }

  close() {
    Auth.reset();
    this._router.navigate(['/']);
  }

}
