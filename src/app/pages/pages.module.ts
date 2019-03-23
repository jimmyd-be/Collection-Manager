import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { PagesRoutingModule } from './pages-routing.module';
import { ThemeModule } from '../@theme/theme.module';
import { MiscellaneousModule } from './miscellaneous/miscellaneous.module';
import { AddCollectionComponent } from './add-collection/add-collection.component';
import { AddCustomFieldComponent } from './add-collection/add-custom-field/add-custom-field.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { AddItemManuallyComponent } from './add-item-manually/add-item-manually.component';
import { ReactiveFormsModule }          from '@angular/forms';
import { CustomFieldFormComponent } from './add-item-manually/custom-field-form/custom-field-form.component';
import { NbInputModule, NbCheckboxModule, NbDatepickerModule } from '@nebular/theme';


@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    DashboardModule,
    MiscellaneousModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    NbInputModule,
    NbCheckboxModule,
    NbDatepickerModule
  ],
  declarations: [
    PagesComponent, 
    AddCollectionComponent, 
    AddCustomFieldComponent,
    AddItemManuallyComponent,
    CustomFieldFormComponent,
  ],
})
export class PagesModule {
}
