import { Response, RequestOptions } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { ApiConfig } from '../config/api.config';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class MemberService {

    private readonly _httpClient: HttpClient;

    constructor(httpClient: HttpClient) {
        this._httpClient = httpClient;
    }

    getLendings(memberNo) {
        const httpOptions = {
            headers: new HttpHeaders({
                'Library': localStorage.getItem('libCode'),
                'Authorization': localStorage.getItem('token')})
        };
        return this._httpClient.get(ApiConfig.origin + 'circ_report/get_lending_history_full?memberNo=' + memberNo, httpOptions)
            .map( (response: any) => response.json())
            .catch(this.handleError);
    }

    getMemberData(id) {
        const httpOptions = {headers: new HttpHeaders({'Library': localStorage.getItem('libCode')})};
        return this._httpClient.get(ApiConfig.origin + 'members_repository/' + id, httpOptions)
            .map( (response: any) => response.json())
            .catch(this.handleError);

    }

    private handleError(error: Response | any) {
        let errMsg: string;
        if (error instanceof Response)  {
            const body = error.json() || '';
            const err = body.error || JSON.stringify(body);
            errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        return Observable.throw(errMsg);
    }


}