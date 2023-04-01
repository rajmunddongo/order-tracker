import { Customer } from "./customer.type";
import { Delivery } from "./delivery.type";

export interface Order {
    id:number;
    customer : Customer;
    orderDate:string;
    delivery:Delivery;
}