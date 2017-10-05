import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class BisisSearchService {

    private url = '/records';
    private url_ep_format = '/records/ep'; // sa ovog end-pointa vraca ElasticPrefixEntity
    // tslint:disable-next-line:max-line-length
    private token = 'eyJhbGciOiJIUzUxMiJ9.eyJjbGllbnRUeXBlIjoibGlicmFyaWFuIiwidG9rZW5fZXhwaXJhdGlvbl9kYXRlIjoxNTA2OTQzNDcxNDM4LCJ1c2VySUQiOiI1OWE3ZDVjODBhYTVjZGE4ZGNiYmI3OTMiLCJ1c2VybmFtZSI6ImFkbWluLmFkbWluQGdibnMuY29tIiwidG9rZW5fY3JlYXRlX2RhdGUiOnsiaG91ciI6MTIsIm1pbnV0ZSI6NTQsIm5hbm8iOjQzNzAwMDAwMCwic2Vjb25kIjozMSwiZGF5T2ZNb250aCI6MiwiZGF5T2ZXZWVrIjoiTU9OREFZIiwiZGF5T2ZZZWFyIjoyNzUsIm1vbnRoIjoiT0NUT0JFUiIsIm1vbnRoVmFsdWUiOjEwLCJ5ZWFyIjoyMDE3LCJjaHJvbm9sb2d5Ijp7ImlkIjoiSVNPIiwiY2FsZW5kYXJUeXBlIjoiaXNvODYwMSJ9fX0._Mn9gCziBYRX-O-rWuZ7KXS2fLSDD2lTe8d030F849uS3XF9daTkQ-hDfOce0vqxb3gS9QZ9w-vPGeo29gQcMA';

    constructor(public http: Http) {

    }

    

    getRecordEP(recId: String): Observable<any> {
        const headers = new Headers();
        console.log('From service, record id is:' + recId);
        // TODO-hardcoded
        headers.append('Authorization', this.token);
        headers.append('Library', 'gbns_com');
        const options = new RequestOptions({ headers: headers });
            return this.http.get(this.url_ep_format + '/' + recId, options)
              .map(response => response.json())
              .catch(this.handleError);
    }

    getRecordsEP(): Observable<any[]> {
        const headers = new Headers();
        // TODO-hardcoded
        headers.append('Authorization', this.token);
        headers.append('Library', 'gbns_com');
        const options = new RequestOptions({ headers: headers });
            return this.http.get(this.url_ep_format, options)
              .map(response => response.json())
              .catch(this.handleError);
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
