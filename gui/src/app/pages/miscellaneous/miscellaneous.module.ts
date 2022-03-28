import {NgModule} from '@angular/core';
import {MiscellaneousRoutingModule, routedComponents} from './miscellaneous-routing.module';
import {CardModule} from "primeng/card";

@NgModule({
  imports: [
    MiscellaneousRoutingModule,
    CardModule,
  ],
  declarations: [
    ...routedComponents,
  ],
})
export class MiscellaneousModule {
}
