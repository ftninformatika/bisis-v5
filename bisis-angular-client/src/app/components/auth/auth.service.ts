import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiConfig } from '../../config/api.config';

@Injectable()
export class AuthService {
    private _currentUser: any;
    private _adminAccess: boolean;
    private readonly _httpClient: HttpClient;

    constructor( private httpClient: HttpClient) {
        this._httpClient = httpClient;
    }

    logIn(username: string, password: string, persist?: boolean): Observable<boolean> {
        persist = persist || false;
        return this._httpClient.post<any>(
            ApiConfig.Origin + '/memauth',
            { username: username, password: password, remember: persist }
        ).map(response  => {
                // login successful if there's a jwt token in the response
                if (response.token) {
                    // set current user data
                    this._currentUser = response;

                    // store username and jwt token in local storage to keep user logged in between page refreshes
                    const storage = (persist) ? localStorage : sessionStorage;
                    storage.setItem('currentUser', JSON.stringify(this._currentUser));

                    // return true to indicate successful login
                    return true;
                } else {
                    // return false to indicate failed login
                    return false;
                }
            },
            error => {
                return false;
            });
    }

    logOut() {
        // clear token remove user from local/seesion storage to log user out
        this._currentUser = null;
        sessionStorage.removeItem('currentUser');
        localStorage.removeItem('currentUser');
        localStorage.removeItem('hideModifyTitleHint');
    }

    getToken(): string {
        const currentUser = this._currentUser || { token: '' };
        // return this._adminAccess ? ADMIN_TOKEN : currentUser.token;
        return currentUser.token;
    }
}
