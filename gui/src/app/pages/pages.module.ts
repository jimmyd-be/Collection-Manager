import {NgModule} from '@angular/core';

import {PagesComponent} from './pages.component';
import {PagesRoutingModule} from './pages-routing.module';
import {ThemeModule} from '../@theme/theme.module';
import {MiscellaneousModule} from './miscellaneous/miscellaneous.module';
import {AddCollectionComponent} from './add-collection/add-collection.component';
import {AddCustomFieldComponent} from './add-collection/add-custom-field/add-custom-field.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {AddItemManuallyComponent} from './add-item-manually/add-item-manually.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CustomFieldFormComponent} from './add-item-manually/custom-field-form/custom-field-form.component';
import {
  NbCheckboxModule,
  NbDialogModule,
  NbInputModule,
  NbMenuModule,
  NbRadioModule,
  NbSelectModule,
  NbToastrModule
} from '@nebular/theme';
import {DashboardComponent} from './dashboard/dashboard.component';
import {ViewCollectionComponent} from './view-collection/view-collection.component';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {ViewItemComponent} from './view-item/view-item.component';
import {ItemFieldComponent} from './view-item/item-field/item-field.component';
import {ImageWidgetComponent} from './view-item/item-field/image-widget/image-widget.component';
import {RateWidgetComponent} from './view-item/item-field/rate-widget/rate-widget.component';
import {DefaultWidgetComponent} from './view-item/item-field/default-widget/default-widget.component';
import {RatingModule} from 'ng-starrating';
import {ItemDialogComponent} from './item-dialog/item-dialog.component';
import {ConfirmationDialogComponent} from './confirmation-dialog/confirmation-dialog.component';
import {ViewUserComponent} from './view-user/view-user.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {EditPasswordUserComponent} from './edit-password-user/edit-password-user.component';
import {DeleteUserComponent} from './delete-user/delete-user.component';
import {ShareCollectionComponent} from './share-collection/share-collection.component';
import {NbEvaIconsModule} from '@nebular/eva-icons';
import {ShareCollectionDialogComponent} from './share-collection-dialog/share-collection-dialog.component';
import {AddItemExternallyComponent} from './add-item-externally/add-item-externally.component';
import {SearchItemResultComponent} from './search-item-result/search-item-result.component';
import {AdminSettingsComponent} from './admin-settings/admin-settings.component';
import {AdminUsersComponent} from './admin-users/admin-users.component';
import { EmailWidgetComponent } from './view-item/item-field/email-widget/email-widget.component';
import { UrlWidgetComponent } from './view-item/item-field/url-widget/url-widget.component';
import { GlobalSearchComponent } from './global-search/global-search.component';
import {CardModule} from 'primeng/card';
import {AccordionModule} from "primeng/accordion";
import {ButtonModule} from "primeng/button";
import {CalendarModule} from "primeng/calendar";

@NgModule({
    imports: [
        PagesRoutingModule,
        ThemeModule,
        MiscellaneousModule,
        FontAwesomeModule,
        ReactiveFormsModule,
        NbInputModule,
        NbCheckboxModule,
      CalendarModule,
        NbEvaIconsModule,
        NbRadioModule,
        FormsModule,
        ReactiveFormsModule,
        NbSelectModule,
        InfiniteScrollModule,
        NbDialogModule.forRoot(),
        RatingModule,
        NbMenuModule,
        NbToastrModule.forRoot({
            duration: 5000,
        }),
      ButtonModule,
      CardModule,
      AccordionModule
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
        ItemDialogComponent,
        ConfirmationDialogComponent,
        ViewUserComponent,
        EditUserComponent,
        EditPasswordUserComponent,
        DeleteUserComponent,
        ShareCollectionComponent,
        ShareCollectionDialogComponent,
        AddItemExternallyComponent,
        SearchItemResultComponent,
        AdminSettingsComponent,
        AdminUsersComponent,
        EmailWidgetComponent,
        UrlWidgetComponent,
        GlobalSearchComponent,
    ]
})
export class PagesModule {
}
