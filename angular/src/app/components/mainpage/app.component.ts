import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.type';
import { MerchantService } from 'src/app/services/merchant.service';
import { ProductService } from 'src/app/services/product.service';
import { Merchant } from "../../models/merchant.type";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(private _merchantService : MerchantService, private _productService : ProductService) {}
  title = 'angular';
  
  public merchants : Merchant[]= [];
  public products : Product[] =  [];


  ngOnInit() {
    this._merchantService.getMerchants().subscribe(data =>{ 
      this.merchants = data;
    });
    this._productService.getProducts().subscribe( data => this.products=data);
  }
}