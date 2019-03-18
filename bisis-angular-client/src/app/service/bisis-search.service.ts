import { Response, RequestOptions, Headers } from '@angular/http';
import { MessageService } from 'primeng/components/common/messageservice';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { RecordsPageModel } from '../model/RecordsPageModel';
import { ApiConfig } from '../config/api.config';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class BisisSearchService {

    private readonly _httpClient: HttpClient;
    private readonly _messageService: MessageService;

    constructor(httpClient: HttpClient, messageService: MessageService) {
        this._httpClient = httpClient;
        this._messageService = messageService;
    }

    public getRecord(recId, libCode) {
        const httpOptions = {
            headers: new HttpHeaders({'Library': libCode})
        };
        return this._httpClient.get(ApiConfig.Origin + 'records/opac_wrapperrec/' + recId, httpOptions)
            .map((response: any) => response)
            .catch(this.handleError);
    }


    public searchUniversal(choice: string, text: string,
                           departments: string[], branches: string[],
                           page = 0, size = 1000): Observable<RecordsPageModel> {
        if (localStorage.getItem('libCode') === undefined ||
            localStorage.getItem('libCode') == null || localStorage.getItem('libCode') === '') {
            this._messageService.clear();
            this._messageService.add({
                severity: 'error',
                summary: 'Грешка',
                detail: 'Одаберите библиотеку!'
            });
            return;
        }

        // TODO - all this shit goes to interceptor
        const httpOptions = {
          headers: new HttpHeaders({'Library': localStorage.getItem('libCode')})};
        // TODO - make models for everything that makes sense
        const universalSearchModel = {
            'searchText': text,
            'departments': departments,
            'branches': branches

        };

        return this._httpClient.post(ApiConfig.Origin + 'records/wrapperrec/opac_universal?pageNumber='
            + page + '&pageSize=' + size, universalSearchModel , httpOptions) as Observable<RecordsPageModel>;
    }



    public searchRecordsAdvanced(searchModel, page = 0, size = 1000): Observable<RecordsPageModel> {
        const httpOptions = {headers: new HttpHeaders({'Library': localStorage.getItem('libCode')})};
        return this._httpClient.post(ApiConfig.Origin + 'records/query/opac_full?pageNumber='
            + page + '&pageSize=' + size, searchModel, httpOptions)
          .map((response: RecordsPageModel) =>  response as RecordsPageModel)
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
