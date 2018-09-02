import {AdminRoutingModule} from './admin-routing/admin-routing.module';
import {AdminDashboardComponent} from './components/admin-content/admin-dashboard/admin-dashboard.component';
import {AdminControlSidebarComponent} from './components/page-elements/admin-control-sidebar/admin-control-sidebar.component';
import {AdminFooterComponent} from './components/page-elements/admin-footer/admin-footer.component';
import {AdminContentComponent} from './components/admin-content/admin-content/admin-content.component';
import {AdminLeftSideComponent} from './components/page-elements/admin-left-side/admin-left-side.component';
import {AdminHeaderComponent} from './components/page-elements/admin-header/admin-header.component';
import {AdminComponent} from './admin.component';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminRolesComponent} from './components/admin-content/admin-roles/admin-roles.component';
import {AdminRolesFormComponent} from './components/admin-content/admin-roles-form/admin-roles-form.component';
import {RoleService} from './services/roles/role.service';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {YesNoDialogComponent} from '../components/yes-no-dialog/yes-no-dialog.component';
import { LockComponent } from './components/lock/lock.component';
import { AdminLogoutComponent } from './components/admin-content/admin-logout/admin-logout.component';
import { AdminProfileComponent } from './components/admin-content/admin-profile/admin-profile.component';
import {OperateurService} from './services/operateurs/operateur.service';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserModule
  ],
  declarations: [
    AdminComponent,
    AdminHeaderComponent,
    AdminLeftSideComponent,
    AdminContentComponent,
    AdminFooterComponent,
    AdminControlSidebarComponent,
    AdminDashboardComponent,
    AdminRolesComponent,
    AdminRolesFormComponent,
    YesNoDialogComponent,
    LockComponent,
    AdminLogoutComponent,
    AdminProfileComponent
  ],
  exports: [AdminComponent],
  providers: [RoleService, OperateurService]
})
export class AdminModule {
}
