import {NgModule} from '@angular/core';
import {ThemeModule} from '../../@theme/theme.module';
import {MiscellaneousRoutingModule, routedComponents} from './miscellaneous-routing.module';
import {CardModule} from "primeng/card";

@NgModule({
  imports: [
    ThemeModule,
    MiscellaneousRoutingModule,
    CardModule,
  ],
  declarations: [
    ...routedComponents,
  ],
})
export class MiscellaneousModule {
}
