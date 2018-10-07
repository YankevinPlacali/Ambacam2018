import {AdminDashboardComponent} from '../components/admin-content/admin-dashboard/admin-dashboard.component';
import {AdminComponent} from './../admin.component';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {AdminRolesComponent} from '../components/admin-content/admin-roles/admin-roles.component';
import {AdminRolesFormComponent} from '../components/admin-content/admin-roles-form/admin-roles-form.component';
import {AdminLogoutComponent} from '../components/admin-content/admin-logout/admin-logout.component';
import {AdminProfileComponent} from '../components/admin-content/admin-profile/admin-profile.component';
import {AdminTypeRequetesComponent} from '../components/admin-content/admin-type-requetes/admin-type-requetes.component';
import {AdminTypeRequetesFormComponent} from '../components/admin-content/admin-type-requetes-form/admin-type-requetes-form.component';
import {AdminOperateursComponent} from '../components/admin-content/admin-operateurs/admin-operateurs.component';
import {AdminOperateursFormComponent} from '../components/admin-content/admin-operateurs-form/admin-operateurs-form.component';
import {AdminPaysComponent} from '../components/admin-content/admin-pays/admin-pays.component';
import {AdminPaysFormComponent} from '../components/admin-content/admin-pays-form/admin-pays-form.component';
import {AdminAutoritesComponent} from '../components/admin-content/admin-autorites/admin-autorites.component';
import {AdminAutoritesFormComponent} from '../components/admin-content/admin-autorites-form/admin-autorites-form.component';
import {AdminActionsComponent} from '../components/admin-content/admin-actions/admin-actions.component';
import {AdminActionsFormComponent} from '../components/admin-content/admin-actions-form/admin-actions-form.component';

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
            path: 'type-requetes',
            children: [
              {
                path: '',
                component: AdminTypeRequetesComponent
              },
              {
                path: 'form',
                component: AdminTypeRequetesFormComponent
              }
            ]
          },
          {
            path: 'operateurs',
            children: [
              {
                path: '',
                component: AdminOperateursComponent
              },
              {
                path: 'form',
                component: AdminOperateursFormComponent
              }
            ]
          },
          {
            path: 'pays',
            children: [
              {
                path: '',
                component: AdminPaysComponent
              },
              {
                path: 'form',
                component: AdminPaysFormComponent
              }
            ]
          },
          {
            path: 'autorites',
            children: [
              {
                path: '',
                component: AdminAutoritesComponent
              },
              {
                path: 'form',
                component: AdminAutoritesFormComponent
              }
            ]
          },
          {
            path: 'actions',
            children: [
              {
                path: '',
                component: AdminActionsComponent
              },
              {
                path: 'form',
                component: AdminActionsFormComponent
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
