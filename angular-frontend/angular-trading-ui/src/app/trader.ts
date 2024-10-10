import { SafeHtml } from "@angular/platform-browser";

export interface Trader {
    key: string;
    id: number;
    firstName: string;
    lastName: string;
    dob: string;
    country: string;
    email: string;
    amount: number;
}