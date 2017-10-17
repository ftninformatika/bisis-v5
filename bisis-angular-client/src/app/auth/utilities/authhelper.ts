import {Injectable} from "@angular/core";

@Injectable()
export class AuthHelper {

    public get authenticated() {
        return localStorage.getItem('authenticated');
    }


    public logout() {
        localStorage.clear();
    }


}