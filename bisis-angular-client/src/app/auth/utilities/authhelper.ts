import {Injectable} from "@angular/core";

@Injectable()
export class AuthHelper {

    public get authenticated() {
        return localStorage.getItem('authenticated');
    }

    public get token(){
        return localStorage.getItem('token');
    }

    public logout() {
        localStorage.clear();
    }


}