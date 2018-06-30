import { Component, OnInit } from '@angular/core';
import { User } from '../../../components/User';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent implements OnInit {

  constructor() { }

  model = new User(18, new Date());

   submitted = false;

  onSubmit() { this.submitted = true; console.log("click")}
  

  ngOnInit() {
  }
}