import { Component } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-merchant-add-product',
  templateUrl: './merchant-add-product.component.html',
  styleUrls: ['./merchant-add-product.component.scss']
})
export class MerchantAddProductComponent {
  public price:number = 0
  public name:string = ""
  public description = ""
  public itemPrice:string  = ""
  public imgSource:string  = ""

  public constructor(private productService : ProductService) {}

  public submitForm() {
    console.log("Name:", this.name);
    console.log("Description:", this.description);
    console.log("ItemPrice:", this.itemPrice);
    console.log("ImgSource:", this.imgSource);
    var product  = {
      name : this.name,
      description : this.description,
      imgSource : this.imgSource,
      price : this.itemPrice
    };
    this.productService.addProduct(product)
  }
}
