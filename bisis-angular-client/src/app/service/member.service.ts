import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import * as bisisGlobals from "../globals";
import {MessageService} from "primeng/components/common/messageservice";
import {Router} from "@angular/router";

@Injectable()
export class MemberService {


    constructor(public http: Http, public router: Router) {

    }

    getLendings(memberNo){
        const headers = new Headers();
        headers.append('Library', localStorage.getItem("authenticatedUserLib"));
        headers.append('Authorization', localStorage.getItem("token"));
        const options = new RequestOptions({ headers: headers });
        return this.http.get('circ_report/get_lending_history_full?memberNo=' + memberNo, options)
            .map( response => response.json())
            .catch(this.handleError);
    }

    getMemberData(id){
        console.log(localStorage);
        const headers = new Headers();
        headers.append('Library', localStorage.getItem("authenticatedUserLib"));
        headers.append('Authorization', localStorage.getItem("token"));
        const options = new RequestOptions({ headers: headers });
        return this.http.get('members_repository/'+id, options)
            .map( response => response.json())
            .catch(this.handleError);

    }

    private handleError (error: Response | any) {
        let errMsg: string;
        if (error instanceof Response) {
            const body = error.json() || '';
            const err = body.error || JSON.stringify(body);

            if (err == 'Forbidden'){ //ako je istekao token
                var libCode = localStorage.getItem('libCode');
                localStorage.clear();
                localStorage.setItem('libCode', libCode);
                this.router.navigate(['/login']);
            }


            errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Observable.throw(errMsg);
    }


}