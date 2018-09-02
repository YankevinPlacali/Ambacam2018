import {Component, OnInit} from '@angular/core';
import {LockComponent} from '../../lock/lock.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent extends LockComponent implements OnInit {

  constructor(public _router: Router) {
    super(_router);
  }

  ngOnInit() {
  }

}
