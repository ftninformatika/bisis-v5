import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {Config} from '../config/config';

@Injectable()
export class LibraryService {


    constructor(public http: Http) {

    }

    getLibs() {
        return this.http.get(Config.getEnvironmentVariable('endPoint') +  'coders/lib_configurations')
            .map(response => response.json().map(
                item => item.libraryName
            ) )
            .catch(this.handleError);
    }

    getDepartmentsForLib(libName) {
        return this.http.get(Config.getEnvironmentVariable('endPoint') + 'coders/location?libName=' + libName)
            .map( response => response.json())
            .catch(this.handleError);
    }

    getSublocationForDepartment(libname, location) {
        return this.http.get(Config.getEnvironmentVariable('endPoint')
            + 'coders/sublocation/get_by_location?lib=' + libname + '&loc=' + location)
            .map( response => response.json())
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
