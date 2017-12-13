import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { tokenNotExpired } from 'angular2-jwt';
import {AuthHelper} from "./utilities/authhelper";


@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router,public ah: AuthHelper) {}

  canActivate() {

    if (!(this.ah.authenticated)) {
      this.router.navigate(['login']);
      //console.log("Niste ulogovani");
      return false;
    }
    if (tokenNotExpired()) {
      //console.log('not expired');
      return true;
    }
    return true;
  }

}
