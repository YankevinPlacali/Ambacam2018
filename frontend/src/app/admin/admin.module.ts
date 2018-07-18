import {AdminRoutingModule} from './admin-routing/admin-routing.module';
import {AdminDashboardComponent} from './components/admin-dashboard/admin-dashboard.component';
import {AdminControlSidebarComponent} from './components/admin-control-sidebar/admin-control-sidebar.component';
import {AdminFooterComponent} from './components/admin-footer/admin-footer.component';
import {AdminContentComponent} from './components/admin-content/admin-content.component';
import {AdminLeftSideComponent} from './components/admin-left-side/admin-left-side.component';
import {AdminHeaderComponent} from './components/admin-header/admin-header.component';
import {AdminComponent} from './admin.component';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AdminRolesComponent} from './components/admin-roles/admin-roles.component';
import {AdminRolesFormComponent} from './components/admin-roles-form/admin-roles-form.component';
import {RoleService} from './services/roles/role.service';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {YesNoDialogComponent} from '../components/yes-no-dialog/yes-no-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
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
    YesNoDialogComponent
  ],
  exports: [AdminComponent],
  providers: [RoleService]
})
export class AdminModule {
}
