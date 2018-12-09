import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AddCollectionComponent } from './add-collection/add-collection.component';

const routes: Routes = [
  {
    path: 'home', component: HomeComponent
  },
  {
    path: 'addCollection', component: AddCollectionComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
