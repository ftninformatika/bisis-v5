import { Response } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { ApiConfig } from '../config/api.config';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class LibraryService {

    private readonly _httpClient: HttpClient;

    constructor(httpClient: HttpClient) {
        this._httpClient = httpClient;
    }

    getLibs(): Observable<any> {
        return this._httpClient.get(ApiConfig.Origin +  'coders/lib_configurations')
            .map((response: any) => response.map(
                item => item.libraryName
            ) )
            .catch(this.handleError);
    }

    getDepartmentsForLib(libName) {
        return this._httpClient.get(ApiConfig.Origin + 'coders/location?libName=' + libName)
            .map( (response: any) => response)
            .catch(this.handleError);
    }

    getSublocationsForDepartment(libName, location) {
        return this._httpClient.get(ApiConfig.Origin
            + 'coders/sublocation/get_by_location?lib=' + libName + '&loc=' + location)
            .map( (response: any) => response)
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
