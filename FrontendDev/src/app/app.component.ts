import { Component } from '@angular/core';
import { MatDialog } from '@angular/material';
import { LoginComponent } from './pages/login/login.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  dialogResult;
  constructor(public loginDialog: MatDialog) { }

  openDialog1() {
    console.log("Test Button")
  }

  openDialog() {
    let dialogRef = this.loginDialog.open(LoginComponent, {
      width: '600px',
      data: 'This text is passed into the dialog!'
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog closed: ${result}`);
      this.dialogResult = result;
    });
  }
}
