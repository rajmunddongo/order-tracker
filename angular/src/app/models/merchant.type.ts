import { SafeUrl } from "@angular/platform-browser";
import { Address } from "../models/address.type";
export interface Merchant {
    id: number;
    name: string;
    email: string;
    picture: string;
    deliveryPrice: string;
    contact: string;
    password: string;
    rating: string;
    address : Address;
    icon :SafeUrl;
}