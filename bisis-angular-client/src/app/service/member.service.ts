import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {MessageService} from "primeng/components/common/messageservice";
import {Router} from "@angular/router";
import {config} from "../config/config";

@Injectable()
export class MemberService {


    constructor(public http: Http) {

    }

    getLendings(memberNo){
        const headers = new Headers();
        headers.append('Library', localStorage.getItem("authenticatedUserLib"));
        headers.append('Authorization', localStorage.getItem("token"))
        const options = new RequestOptions({ headers: headers });
        return this.http.get(config.getEnvironmentVariable('endPoint') + 'circ_report/get_lending_history_full?memberNo=' + memberNo, options)
            .map( response => response.json())
            .catch(this.handleError);
    }

    getMemberData(id){
        const headers = new Headers();
        headers.append('Library', localStorage.getItem("authenticatedUserLib"));
        headers.append('Authorization', localStorage.getItem("token"))
        const options = new RequestOptions({ headers: headers });
        return this.http.get(config.getEnvironmentVariable('endPoint') + 'members_repository/'+id, options)
            .map( response => response.json())
            .catch(this.handleError);

    }

    private handleError (error: Response | any) {
        let errMsg: string;
        if (error instanceof Response)  {
            const body = error.json() || '';
            const err = body.error || JSON.stringify(body);
            errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        //console.error(errMsg);
        return Observable.throw(errMsg);
    }


}