import { HttpClient } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { v4 as uuidv4 } from 'uuid';
import { FileManagerService } from 'src/app/services/fileupload.service';
import { waitForAsync } from '@angular/core/testing';
import { Observable, switchMap } from 'rxjs';
import { MerchantService } from 'src/app/services/merchant.service';
@Component({
  selector: 'app-merchant-add-product',
  templateUrl: './merchant-add-product.component.html',
  styleUrls: ['./merchant-add-product.component.scss']
})
export class MerchantAddProductComponent {
  public price: number = 0
  public name: string = ""
  public description = ""
  public itemPrice: string = ""
  public imgSource: string = ""
  public successfulUpload :boolean =false;
  public unSuccessfulUpload :boolean =false;
  fileName = '';
  selectedFile: File | undefined;
  isFileSelected: boolean = false;
  maxFileNameLength: number = 10;

  public constructor(private productService: ProductService, private http: HttpClient, private fileUploadService : FileManagerService, private merchantService: MerchantService) { }
  public submitForm() {
    const uuid = uuidv4();
    console.log("Name:", this.name);
    console.log("Description:", this.description);
    console.log("ItemPrice:", this.itemPrice);
    this.imgSource = uuid + '_' + this.name;
    const imgname = this.imgSource;
    const fileName = this.selectedFile ? this.selectedFile.name : '';
    console.log("ImgSource:", this.imgSource);
    var product = {
      name: this.name,
      description: this.description,
      imgSource: this.imgSource,
      price: this.itemPrice
    };
    this.onSave(this.imgSource)
    .pipe(
      switchMap((result) => this.productService.addProduct(product))
    )
    .subscribe(
      (response) => {
        console.log('Product added successfully', response);
        this.successfulUpload= true;
        this.unSuccessfulUpload=false;
        return;
      },
      (error) => {
        console.error('Error while adding product', error);
      }
    );
    if(this.successfulUpload == false)this.unSuccessfulUpload=true;
    this.successfulUpload= false;
  }

  onKey(event: any) {
    console.log('Price is set to: '+this.price);
    this.merchantService.postMerchantDeliveryPrice(this.price).subscribe();
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    const fileName = this.selectedFile ? this.selectedFile.name : '';
    const truncatedFileName = fileName.substring(0, this.maxFileNameLength) + "(...)"+ fileName.substring(fileName.length-4, fileName.length);
    document.getElementById('file-name')!.textContent = truncatedFileName;
    this.isFileSelected = true;
  }

  onSave(name:string) : Observable<String> {
    if (!this.selectedFile) {
      throw console.error("Error while uploading");
      ;
    }
    return this.fileUploadService.uploadFile(this.selectedFile,name);
  }
}
