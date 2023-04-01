import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './components/mainpage/app.component';
import { TestcomponentComponent } from './components/testcomponent/testcomponent.component';
import { OpenComponent } from './components/openingpage/open.component'
import { MerchantOverviewComponent } from './components/merchantoverview/merchantoverview.component'

const routes: Routes = [
  { path: '', component: OpenComponent },
  { path: 'merchant', component: AppComponent },
  { path: 'm/orders', component: MerchantOverviewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
