import { Component, OnInit } from '@angular/core';
import { MerchantService } from 'src/app/services/merchant.service';
import { Merchant } from "../../models/merchant.type";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(private _merchantService : MerchantService) {}
  title = 'angular';
  
  public merchants : Merchant[]= [];


  ngOnInit() {
  this._merchantService.getMerchants().subscribe(data => this.merchants = data);
  for (let i = 0; i < this.merchants.length; i++) {
    console.log(this.merchants[i].name);
  }
}
}