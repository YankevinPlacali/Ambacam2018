import {AdminDashboardComponent} from '../components/admin-content/admin-dashboard/admin-dashboard.component';
import {AdminComponent} from './../admin.component';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {AdminRolesComponent} from '../components/admin-content/admin-roles/admin-roles.component';
import {AdminRolesFormComponent} from '../components/admin-content/admin-roles-form/admin-roles-form.component';
import {AdminLogoutComponent} from '../components/admin-content/admin-logout/admin-logout.component';
import {AdminProfileComponent} from '../components/admin-content/admin-profile/admin-profile.component';

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
            path: 'profile',
            component: AdminProfileComponent
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
          },
          {
            path: 'logout',
            component: AdminLogoutComponent
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
