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

@Injectable()
export class BisisPretragaService {

  errorMessage: string
    private url: string = '/records'
    
    //dummySearchResults: any[];

	constructor(private authHttp: AuthHttp,public http: Http) {

       /* this.dummySearchResults.push({'author': "Brashares, Ann", 'title': "Steve Jobs : think different / Ann Brashares.",
         'description': "Includes bibliographical references (p. 78) and in", 'yearPublished': "2001", 'available': "7" });
        this.dummySearchResults.push({'author': "Ivo Andric", 'title': "Na Drini Cuprija",
        'description': "Radnja romana traje otprilike četiri veka i skup je više priča povezanih sa mostom na Drini, koji je tačka okosnica i glavni simbol naracije. Sam most predstavlja na neki način suprotnost ljudskoj sudbini koja je prolazna u odnosu na kamenu građevinu, koja je večna. ",
         'yearPublished': "1945", 'available': "3" });*/
    }

  // GET
	search(): Observable<any[]> {
    let headers = new Headers();
    //TODO-hardcoded
    headers.append('Authorization', 'eyJhbGciOiJIUzUxMiJ9.eyJjbGllbnRUeXBlIjoibGlicmFyaWFuIiwidG9rZW5fZXhwaXJhdGlvbl9kYXRlIjoxNTA2OTQzNDcxNDM4LCJ1c2VySUQiOiI1OWE3ZDVjODBhYTVjZGE4ZGNiYmI3OTMiLCJ1c2VybmFtZSI6ImFkbWluLmFkbWluQGdibnMuY29tIiwidG9rZW5fY3JlYXRlX2RhdGUiOnsiaG91ciI6MTIsIm1pbnV0ZSI6NTQsIm5hbm8iOjQzNzAwMDAwMCwic2Vjb25kIjozMSwiZGF5T2ZNb250aCI6MiwiZGF5T2ZXZWVrIjoiTU9OREFZIiwiZGF5T2ZZZWFyIjoyNzUsIm1vbnRoIjoiT0NUT0JFUiIsIm1vbnRoVmFsdWUiOjEwLCJ5ZWFyIjoyMDE3LCJjaHJvbm9sb2d5Ijp7ImlkIjoiSVNPIiwiY2FsZW5kYXJUeXBlIjoiaXNvODYwMSJ9fX0._Mn9gCziBYRX-O-rWuZ7KXS2fLSDD2lTe8d030F849uS3XF9daTkQ-hDfOce0vqxb3gS9QZ9w-vPGeo29gQcMA');
    headers.append('Library', 'gbns_com');
    let options = new RequestOptions({ headers: headers });

	    return this.http.get(this.url, options)
	      .map(response => response.json())
          .catch(this.handleError)	
         // return this.dummySearchResults;
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