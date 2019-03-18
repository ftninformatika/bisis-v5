import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { ItemStatus } from '../model/coders/ItemStatus';
import { ApiConfig } from '../config/api.config';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class CodersService {

    private readonly _httpClient: HttpClient;

    constructor(httpClient: HttpClient) {
        this._httpClient = httpClient;
    }

    public getItemStatusCoders(libName: string): Observable<ItemStatus[]> {
        return this._httpClient.get(ApiConfig.Origin +  'coders/item_status?libName=' + libName) as Observable<ItemStatus[]>;
    }
}
