import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Auth} from '../../utils/auth/auth';

@Component({
  selector: 'app-lock',
  templateUrl: './lock.component.html',
  styleUrls: ['./lock.component.css']
})
export class LockComponent implements OnInit {

  constructor(public _router: Router) {
    this.lock();
  }

  ngOnInit() {
  }

  lock() {
    if (!Auth.isAuthenticated()) {
      this.close();
    }
  }

  close() {
    Auth.reset();
    this._router.navigate(['/']);
  }
}
