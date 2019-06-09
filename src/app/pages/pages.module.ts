import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { PagesRoutingModule } from './pages-routing.module';
import { ThemeModule } from '../@theme/theme.module';
import { MiscellaneousModule } from './miscellaneous/miscellaneous.module';
import { AddCollectionComponent } from './add-collection/add-collection.component';
import { AddCustomFieldComponent } from './add-collection/add-custom-field/add-custom-field.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { AddItemManuallyComponent } from './add-item-manually/add-item-manually.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CustomFieldFormComponent } from './add-item-manually/custom-field-form/custom-field-form.component';
import { NbInputModule, NbCheckboxModule, NbDatepickerModule, NbAccordionModule } from '@nebular/theme';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ViewCollectionComponent } from './view-collection/view-collection.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { ViewItemComponent } from './view-item/view-item.component';
import { ItemFieldComponent } from './view-item/item-field/item-field.component';
import { ImageWidgetComponent } from './view-item/item-field/image-widget/image-widget.component';
import { RateWidgetComponent } from './view-item/item-field/rate-widget/rate-widget.component';
import { DefaultWidgetComponent } from './view-item/item-field/default-widget/default-widget.component';
import { RatingModule } from 'ng-starrating';


@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    MiscellaneousModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    NbInputModule,
    NbCheckboxModule,
    NbDatepickerModule,
    NbAccordionModule,
    InfiniteScrollModule,
    NbAccordionModule,
    RatingModule,
  ],
  declarations: [
    PagesComponent,
    AddCollectionComponent,
    AddCustomFieldComponent,
    AddItemManuallyComponent,
    CustomFieldFormComponent,
    DashboardComponent,
    ViewCollectionComponent,
    ViewItemComponent,
    ItemFieldComponent,
    ImageWidgetComponent,
    RateWidgetComponent,
    DefaultWidgetComponent,
  ],
})
export class PagesModule {
}
