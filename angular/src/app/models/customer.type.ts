import { SafeUrl } from "@angular/platform-browser";
import { Address } from "./address.type";

export interface Customer{
    id:number;
    address:Address;
    imgDataUrl:SafeUrl;
    
}