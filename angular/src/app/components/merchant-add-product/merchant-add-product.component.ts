import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { v4 as uuidv4 } from 'uuid';
import { FileUploadService } from 'src/app/services/fileupload.service';
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
  fileName = '';
  selectedFile: File | undefined;
  isFileSelected: boolean = false;
  maxFileNameLength: number = 10;

  public constructor(private productService: ProductService, private http: HttpClient, private fileUploadService : FileUploadService) { }
  public submitForm() {
    const uuid = uuidv4();
    console.log("Name:", this.name);
    console.log("Description:", this.description);
    console.log("ItemPrice:", this.itemPrice);
    this.imgSource = uuid + '_' + this.name;
    const imgname = this.imgSource;
    const fileName = this.selectedFile ? this.selectedFile.name : '';
    this.imgSource = 'assets/pictures/indexpictures/'+ fileName
    console.log("ImgSource:", this.imgSource);
    var product = {
      name: this.name,
      description: this.description,
      imgSource: this.imgSource,
      price: this.itemPrice
    };
    this.productService.addProduct(product).subscribe(
      response => {
        console.log('Product added successfully', response);
      },
      error => {
        console.error('Error while adding product', error);
      }
    );
    this.onSave(this.imgSource);
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    const fileName = this.selectedFile ? this.selectedFile.name : '';
    const truncatedFileName = fileName.substring(0, this.maxFileNameLength) + "(...)"+ fileName.substring(fileName.length-4, fileName.length);
    document.getElementById('file-name')!.textContent = truncatedFileName;
    this.isFileSelected = true;
  }

  onSave(name:string) {
    if (!this.selectedFile) {
      return;
    }

    this.fileUploadService.uploadFile(this.selectedFile,name).subscribe(
      (response) => {
        console.log('File uploaded successfully');
        // Do something after the file is uploaded
      },
      (error) => {
        console.error('File upload failed:', error);
      }
    );
  }
}
