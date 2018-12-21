import {HistoryComponent} from '../starter/history.component';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {AuthFormComponent} from '../auth-form/auth-form.component';

@NgModule({
  imports: [
    RouterModule.forRoot([
      {path: '', redirectTo: 'starter', pathMatch: 'full'},
      {path: 'starter', component: AuthFormComponent},
      {path: 'requete/history', component: HistoryComponent}
    ])
  ],
  declarations: [],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
