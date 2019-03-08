import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { RecordsPageModel } from '../model/RecordsPageModel';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {MessageService} from 'primeng/components/common/messageservice';
import {Config} from '../config/config';

@Injectable()
export class BisisSearchService {



    constructor(public http: Http, public messageService: MessageService) {

    }

    getRecord(recId, libCode) {
        const headers = new Headers();
        headers.append('Library', libCode);
        const options = new RequestOptions({ headers: headers });
        return this.http.get(Config.getEnvironmentVariable('endPoint') + 'records/opac_wrapperrec/' + recId, options)
            .map(response => response.json())
            .catch(this.handleError);
    }


    searchUniversal(choice: string, text: string, departments: string[], branches: string[], page = 0, size = 1000) {
        if (localStorage.getItem('libCode') === undefined ||
            localStorage.getItem('libCode') == null || localStorage.getItem('libCode') === '') {
            this.messageService.clear();
            this.messageService.add({
                severity: 'error',
                summary: 'Грешка',
                detail: 'Одаберите библиотеку!'
            });
            return;
        }

        const headers = new Headers();
        headers.append('Library', localStorage.getItem('libCode'));
        const options = new RequestOptions({ headers: headers });
        const universalSearchModel = {
            'searchText': text,
            'departments': departments,
            'branches': branches

        };

        return this.http.post(Config.getEnvironmentVariable('endPoint') + 'records/wrapperrec/opac_universal?pageNumber='
            + page + '&pageSize=' + size, universalSearchModel , options)
            .map(response => response.json() as RecordsPageModel)
            .catch(this.handleError);

    }



    searchRecordsAdvanced(searchModel, page = 0, size = 1000): Observable<RecordsPageModel> {
        const headers = new Headers();
        headers.append('Library', localStorage.getItem('libCode'));
        const options = new RequestOptions({ headers: headers });
            return this.http.post(Config.getEnvironmentVariable('endPoint') + 'records/query/opac_full?pageNumber='
                + page + '&pageSize=' + size, searchModel, options)
              .map(response =>  response.json() as RecordsPageModel)
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
