import { Injectable } from '@angular/core';
import { AuthHttp } from 'angular2-jwt';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {
  Http,
  Response,
  RequestOptions,
  Headers
} from '@angular/http'

import { Country } from "../model/country";
import { CountryService } from "./country.service";
import { Institution } from "../model/institution";


@Injectable()
export class InstitutionService {

  private institutionsUrl: string = '/institutions';
  errorMessage: string

  constructor(private authHttp: AuthHttp,
    private countryService: CountryService) { }
  

  getInstitutions(): Observable<any[]> {
    return this.authHttp.get(this.institutionsUrl)
      .map(response => response.json()._embedded.institutions/* as Institution[]*/)
      .catch(this.handleError)
  }

  getInstitutionsSearch(href: string): Observable<any[]> {
    return this.authHttp.get(href)
      .map(response => response.json()._embedded.institutions/* as Institution[]*/)
      .catch(this.handleError)
  }

  createInstitution(json: string): Observable<Institution> {
    console.log(json)
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.authHttp.post(this.institutionsUrl, json, options)
                          .map(response => response.json())
                          .catch(this.handleError)
  }

  updateInstitution(json: string): Observable<Institution>{
    console.log(json)
    let headers = new Headers({ 'Content-Type': 'application/hal+json' });
    let options = new RequestOptions({ headers: headers });
    return this.authHttp.post(this.institutionsUrl,json, options)
                          .map(response => response.json())
                          .catch(this.handleError)
  }

/**Da nam radi put koristili bi metodu ispod, ali put ne radi za objekte koji su dovucenu _links-om*/
/*  updateInstitution(json: string, id: number): Observable<Institution>{
    console.log(json)
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.authHttp.put(`${this.institutionsUrl}/${id}`,json, options)
                          .map(response => response.json())
                          .catch(this.handleError)
  }*/

  deleteInstitution(id: number): void {
    this.authHttp.delete(`${this.institutionsUrl}/${id}`)
      .subscribe()
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