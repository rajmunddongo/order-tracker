import { Customer } from "./customer.type"

export interface User {
    id:number
    firstName:string
    lastName:string
    email:string
    password:string
    profilePicture:string
    customer:Customer
}