import { Customer } from "./customer.type"
import { Merchant } from "./merchant.type"

export interface User {
    id:number
    firstname:string
    lastname:string
    email:string
    password:string
    role:string
    profilePicture:string
    customer:Customer
    merchant:Merchant
}