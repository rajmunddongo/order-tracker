import { Customer } from "./customer.type";
import { Delivery } from "./delivery.type";
import { Merchant } from "./merchant.type";

export interface Order {
    id:number;
    customer : Customer;
    orderDate:string;
    delivery:Delivery;
    merchant:Merchant;
}