import {Component, OnInit} from '@angular/core';
import {} from 'jquery';
import {} from 'morris.js';
import {} from 'jquery-knob';
import {} from 'bootstrap-datepicker';
import {} from 'jqueryui';
import {} from 'daterangepicker';
import {} from 'jquery.slimscroll';
import * as moment from 'moment';
import {Auth} from '../../../utils/auth/auth';
import {LockComponent} from '../../lock/lock.component';
import {Router} from '@angular/router';
// Variable in assets/js/scripts.js file
declare var AdminLTE: any;

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent extends LockComponent implements OnInit {
  public nom: string;

  constructor(public _router: Router) {
    super(_router);
    this.nom = Auth.getOperateur().nom;
  }

  ngOnInit() {
    // Update the AdminLTE layouts
    AdminLTE.init();
  }

}
