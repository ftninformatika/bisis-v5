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
        var libCode = localStorage.getItem('libCode');
        localStorage.clear();
        localStorage.setItem('libCode', libCode);


    }


}