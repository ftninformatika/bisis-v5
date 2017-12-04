import {Injectable} from "@angular/core";
import {Http,Response} from '@angular/http';
import { HttpModule }  from '@angular/http';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/share';
import 'rxjs/add/operator/startWith';
import 'rxjs/Rx';

@Injectable()
export class GetCoder {


    constructor(private _http:Http) {

    }


    getCoderData(prefix) {

        return this._http.get('/assets/utils/coders.json')
            .map(data => data.json());

    }

}