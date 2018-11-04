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
import {ActionService} from './services/actions/action.service';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {YesNoDialogComponent} from '../components/yes-no-dialog/yes-no-dialog.component';
import {LockComponent} from './components/lock/lock.component';
import {AdminLogoutComponent} from './components/admin-content/admin-logout/admin-logout.component';
import {AdminProfileComponent} from './components/admin-content/admin-profile/admin-profile.component';
import {OperateurService} from './services/operateurs/operateur.service';
import {AdminTypeRequetesComponent} from './components/admin-content/admin-type-requetes/admin-type-requetes.component';
import {AdminTypeRequetesFormComponent} from './components/admin-content/admin-type-requetes-form/admin-type-requetes-form.component';
import {TypeRequeteService} from './services/type-requetes/type-requete.service';
import {AdminOperateursComponent} from './components/admin-content/admin-operateurs/admin-operateurs.component';
import {AdminOperateursFormComponent} from './components/admin-content/admin-operateurs-form/admin-operateurs-form.component';
import {PaysService} from './services/pays/pays.service';
import {AdminMotifSuppressionComponent} from './components/admin-content/admin-motif-suppression/admin-motif-suppression.component';
import {MotifSuppressionService} from './services/motif-suppression/motif-suppression.service';
import {AdminMotifSuppressionFormComponent} from './components/admin-content/admin-motif-suppression-form/admin-motif-suppression-form.component';
import {AdminPaysComponent} from './components/admin-content/admin-pays/admin-pays.component';
import {AdminActionsComponent} from './components/admin-content/admin-actions/admin-actions.component';
import {AdminActionsFormComponent} from './components/admin-content/admin-actions-form/admin-actions-form.component';
import {AdminPaysFormComponent} from './components/admin-content/admin-pays-form/admin-pays-form.component';
import {AdminAutoritesComponent} from './components/admin-content/admin-autorites/admin-autorites.component';
import {AdminAutoritesFormComponent} from './components/admin-content/admin-autorites-form/admin-autorites-form.component';
import {AutoriteService} from './services/autorites/autorite.service';
import {AdminRequerantsComponent} from './components/admin-content/admin-requerants/admin-requerants.component';
import {AdminRequerantsFormComponent} from './components/admin-content/admin-requerants-form/admin-requerants-form.component';
import {RequerantService} from './services/requerants/requerant.service';
import {AdminStatutsComponent} from './components/admin-content/admin-statuts/admin-statuts.component';
import {StatutService} from './services/statuts/statut.service';
import {AdminStatutsFormComponent} from './components/admin-content/admin-statuts-form/admin-statuts-form.component';
import {AdminRequetesComponent} from './components/admin-content/admin-requetes/admin-requetes.component';
import {AdminRequetesFormComponent} from './components/admin-content/admin-requetes-form/admin-requetes-form.component';
import {PaginationComponent} from '../components/pagination/pagination.component';
import {RequeteService} from './services/requetes/requete.service';

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
    AdminProfileComponent,
    AdminTypeRequetesComponent,
    AdminTypeRequetesFormComponent,
    AdminOperateursComponent,
    AdminOperateursFormComponent,
    AdminMotifSuppressionComponent,
    AdminMotifSuppressionFormComponent,
    AdminPaysComponent,
    AdminPaysFormComponent,
    AdminAutoritesComponent,
    AdminAutoritesFormComponent,
    AdminActionsComponent,
    AdminActionsFormComponent,
    AdminStatutsComponent,
    AdminStatutsFormComponent,
    AdminRequerantsComponent,
    AdminRequerantsFormComponent,
    AdminRequetesComponent,
    PaginationComponent,
    AdminRequetesFormComponent
  ],
  exports: [AdminComponent],
  providers: [
    RoleService,
    OperateurService,
    TypeRequeteService,
    PaysService,
    AutoriteService,
    ActionService,
    RequerantService,
    MotifSuppressionService,
    StatutService,
    RequeteService
  ]
})

export class AdminModule {
}
