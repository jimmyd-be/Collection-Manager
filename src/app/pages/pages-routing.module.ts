import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddCollectionComponent } from './add-collection/add-collection.component';
import { AddItemManuallyComponent } from './add-item-manually/add-item-manually.component';
import { ViewCollectionComponent } from './view-collection/view-collection.component';

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'dashboard',
      component: DashboardComponent,
    },
    {
      path: 'collection',
      children: [
        {
          path: 'add',
          component: AddCollectionComponent
        },
        {
          path: 'edit/:id',
          component: AddCollectionComponent
        },
        {
          path: 'view/:id',
          component: ViewCollectionComponent
        }
      ]
    },
    {
      path: 'item',
      children: [
        {
          path: 'addManual',
          component: AddItemManuallyComponent
        }
      ]
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
