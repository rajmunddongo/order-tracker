import { Address } from "./address.type";

export interface Customer{
    name:string;
    password:string;
    email:string;
    address:Address;
}