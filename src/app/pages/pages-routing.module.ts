import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddCollectionComponent } from './add-collection/add-collection.component';
import { AddItemManuallyComponent } from './add-item-manually/add-item-manually.component';
import { ViewCollectionComponent } from './view-collection/view-collection.component';
import { AuthGuard } from '../auth.guard';
import { ViewUserComponent } from './view-user/view-user.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { EditPasswordUserComponent } from './edit-password-user/edit-password-user.component';
import { DeleteUserComponent } from './delete-user/delete-user.component';

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'dashboard',
      canActivate: [AuthGuard],
      component: DashboardComponent,
    },
    {
      path: 'profile',
      children: [
        {
          path: '',
          canActivate: [AuthGuard],
          component: ViewUserComponent,
        },
        {
          path: 'edit',
          canActivate: [AuthGuard],
          component: EditUserComponent,
        },
        {
          path: 'delete',
          canActivate: [AuthGuard],
          component: DeleteUserComponent,
        },
        {
          path: 'edit/password',
          canActivate: [AuthGuard],
          component: EditPasswordUserComponent,
        },
      ],
    },
    {
      path: 'collection',
      children: [
        {
          path: 'add',
          canActivate: [AuthGuard],
          component: AddCollectionComponent,
        },
        {
          path: 'edit/:id',
          canActivate: [AuthGuard],
          component: AddCollectionComponent,
        },
        {
          path: 'view/:id',
          canActivate: [AuthGuard],
          component: ViewCollectionComponent,
        },
      ],
    },
    {
      path: 'item',
      children: [
        {
          path: 'addManual',
          canActivate: [AuthGuard],
          component: AddItemManuallyComponent,
        },
        {
          path: 'edit/:colId/:id',
          canActivate: [AuthGuard],
          component: AddItemManuallyComponent,
        },
      ],
    },
    {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
   },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
