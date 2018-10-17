import { Injectable, Injector } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs';
import {config} from '../../config/config';

@Injectable()
export class AuthService {
    private _currentUser: any;
    private _adminAccess: boolean;
    constructor( private _http: HttpClient) {}

    logIn(username: string, password: string, persist?: boolean): Observable<boolean> {
        persist = persist || false;
        return this._http.post<any>(
            config.getEnvironmentVariable('endPoint') + '/memauth',
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
