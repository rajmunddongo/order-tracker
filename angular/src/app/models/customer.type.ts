import { Address } from "./address.type";

export interface Customer{
    id:number;
    name:string;
    password:string;
    email:string;
    address:Address;
}