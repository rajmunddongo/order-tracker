import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { Merchant } from 'src/app/models/merchant.type';
import { Product } from 'src/app/models/product.type';
import { MerchantService } from 'src/app/services/merchant.service';
@Component({
    selector: 'app-root',
    templateUrl: './open.component.html',
    styleUrls: ['./open.component.scss']
})
  export class OpenComponent implements OnInit {
    
    title = 'Angular';
    constructor(private _merchantService : MerchantService) {}
  ngOnInit(): void {
    this._merchantService.getMerchants().subscribe(data => this.merchants=data);
  }
    
    public merchants : Merchant[] = [];
}