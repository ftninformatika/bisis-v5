import { Injectable } from '@angular/core';
import { AuthHttp } from 'angular2-jwt';
import { Country } from "../model/country";
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/first' 
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {
  Http,
  Response,
  RequestOptions,
  Headers
} from '@angular/http'

@Injectable()
export class CountryService {


  private countryUrl: string = '/countries';

  constructor(private authHttp: AuthHttp) { }


  getCountries(): Observable<Country[]> {
    return this.authHttp.get(this.countryUrl)
      .map(response => response.json()._embedded.countries as Country[])
      .catch(this.handleError)
  }



  private handleError (error: Response | any) {
    // In a real world app, you might use a remote logging infrastructure
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