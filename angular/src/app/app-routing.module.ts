import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './components/mainpage/app.component';
import { TestcomponentComponent } from './components/testcomponent/testcomponent.component';
import { OpenComponent } from './components/openingpage/open.component'
import { LoginComponent } from './components/login/login.component';
import { MerchantOverviewComponent } from './components/merchantoverview/merchantoverview.component'
import { CustomerRegisterComponent } from './components/customer-register/customer-register.component';
import { AdminPageComponent } from './components/admin-page/admin-page.component';
import { CheckoutPageComponent } from './components/checkout-page/checkout-page.component';
import { TrackComponent } from './components/track/track.component';
import { MerchantRegisterComponent } from './components/merchant-register/merchant-register.component';

const routes: Routes = [
  { path: '', component: OpenComponent },
  { path: 'merchant', component: AppComponent },
  { path: 'merchant/orders', component: MerchantOverviewComponent },
  { path: 'login', component: LoginComponent },
  { path: 'customer/register', component: CustomerRegisterComponent },
  { path: 'admin/manage', component: AdminPageComponent },
  { path: 'checkout', component: CheckoutPageComponent },
  { path: 'order/status', component: TrackComponent },
  { path: 'merchant/register', component: MerchantRegisterComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
