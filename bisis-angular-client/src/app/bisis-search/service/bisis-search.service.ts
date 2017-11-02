import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { RecordsPageModel } from '../model/RecordsPageModel';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class BisisSearchService {



    constructor(public http: Http) {

    }

    getRecord(recId, libCode) {
        const headers = new Headers();
        headers.append('Library', libCode);
        const options = new RequestOptions({ headers: headers });
        return this.http.get('/records/wrapperrec/' + recId, options)
            .map(response => response.json())
            .catch(this.handleError);
    }

    getUnimarcForRecord(recId) {
        const headers = new Headers();
        headers.append('Library', localStorage.getItem('libCode'));
        const options = new RequestOptions({ headers: headers });
        return this.http.get('/records/unimarc/' + recId, options)
            .map(response => response)
            .catch(this.handleError);
    }

    searchRecordsByEP(choice: string, text: string, page = 0, size = 20) {
        const headers = new Headers();
        headers.append('Library', localStorage.getItem('libCode'));
        const options = new RequestOptions({ headers: headers });
        console.log('Searching ' + choice + ':' + text + '\nPage: ' + page + '\nPageSize: ' + size);

        return this.http.get('/records/wrapperrec/universal/' + text + '?pageNumber=' + page + '&pageSize=' + size, options)
            .map(response => response.json() as RecordsPageModel)
            .catch(this.handleError);

    }



    searchRecordsAdvanced(searchModel): Observable<any[]> {
        const headers = new Headers();
        headers.append('Library', localStorage.getItem('libCode'));
        console.log(localStorage.getItem('libCode'));
        const options = new RequestOptions({ headers: headers });
            return this.http.post('/records/query/full',searchModel, options)
              .map(response =>  response.json())
              .catch(this.handleError);
    }

    getRecordsEP(){}


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
