import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/share';
import 'rxjs/add/operator/startWith';
import 'rxjs/Rx';

@Injectable()
export class GetCoder {

    private readonly _http: HttpClient;

    constructor(http: HttpClient) {
        this._http = http;
    }
    public getCoderData(prefix: string) {
        return this._http.get('/assets/utils/coders.json')
            .map((data: any) => data.json());
    }

}
