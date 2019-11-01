import { NgModule } from '@angular/core';
import { ThemeModule } from '../../@theme/theme.module';
import { MiscellaneousRoutingModule, routedComponents } from './miscellaneous-routing.module';
import { NbCardModule } from '@nebular/theme';

@NgModule({
  imports: [
    ThemeModule,
    MiscellaneousRoutingModule,
    NbCardModule,
  ],
  declarations: [
    ...routedComponents,
  ],
})
export class MiscellaneousModule { }
