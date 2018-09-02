import {AdminModule} from './admin/admin.module';
import {AppRoutingModule} from './app-routing/app-routing.module';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {StarterComponent} from './starter/starter.component';
import {StarterHeaderComponent} from './starter/starter-header/starter-header.component';
import {StarterLeftSideComponent} from './starter/starter-left-side/starter-left-side.component';
import {StarterContentComponent} from './starter/starter-content/starter-content.component';
import {StarterFooterComponent} from './starter/starter-footer/starter-footer.component';
import {StarterControlSidebarComponent} from './starter/starter-control-sidebar/starter-control-sidebar.component';
import {AuthFormComponent} from './auth-form/auth-form.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthServiceService} from './admin/services/auth/auth-service.service';
import {GlobalVariablesService} from './admin/services/global-variables/global-variables.service';
import {GlobalConstantsService} from './admin/services/variables/global-constants.service';

@NgModule({
  declarations: [
    AppComponent,
    StarterComponent,
    StarterHeaderComponent,
    StarterLeftSideComponent,
    StarterContentComponent,
    StarterFooterComponent,
    StarterControlSidebarComponent,
    AuthFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AdminModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [AuthServiceService, GlobalVariablesService, GlobalConstantsService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
