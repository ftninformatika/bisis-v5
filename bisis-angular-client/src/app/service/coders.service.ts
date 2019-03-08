import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Config} from '../config/config';
import {Observable} from 'rxjs/Observable';
import {ItemStatus} from '../model/coders/ItemStatus';

@Injectable()
export class CodersService {

    constructor(public http: Http) {

    }

    getItemStatusCoders(libName: string){
        return this.http.get(Config.getEnvironmentVariable('endPoint') +  'coders/item_status?libName=' + libName)
            .map(response => response.json() as ItemStatus[])
            .catch(this.handleError);
    }

    private handleError (error: Response | any) {
        let errMsg: string;
        if (error instanceof Response) {
            const body = error.json() || '';
            const err = body.error || JSON.stringify(body);
            errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Observable.throw(errMsg);
    }
}