import {AdminDashboardComponent} from '../components/admin-dashboard/admin-dashboard.component';
import {AdminComponent} from './../admin.component';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {AdminRolesComponent} from '../components/admin-roles/admin-roles.component';
import {AdminRolesFormComponent} from '../components/admin-roles-form/admin-roles-form.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'admin',
        component: AdminComponent,
        children: [
          {
            path: '',
            redirectTo: 'dashboard',
            pathMatch: 'full'
          },
          {
            path: 'dashboard',
            component: AdminDashboardComponent
          },
          {
            path: 'roles',
            children: [
              {
                path: '',
                component: AdminRolesComponent
              },
              {
                path: 'form',
                component: AdminRolesFormComponent
              }
            ]
          }
        ]
      }
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class AdminRoutingModule {
}
