import { SafeUrl } from "@angular/platform-browser";

export interface Product {
    id:number;
    name:string;
    description:string;
    imgSource:string;
    imgDataUrl:SafeUrl;
    indexPicture:Blob;
    price:number;
}