import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import {AuthHelper} from './utilities/authhelper';


@Injectable()
export class AuthGuard implements CanActivate {

 // constructor(private router: Router, public ah: AuthHelper) {}

  canActivate() {

    // if (!(this.ah.authenticated)) {
    //   this.router.navigate(['login']);
    //   return false;
    // }
    //  TODO - change code for authentification change is made agular2-jwt to auth0-angularjwt dep
    // if (tokenNotExpired()) {
    //   return true;
    // }
    return true;
  }

}
