import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { tokenNotExpired } from 'angular2-jwt';


@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate() {
    if (tokenNotExpired()) {
      console.log('not expired');
      return true;
    }
    console.log('expired');
    this.router.navigate(['login']);
    return false;
  }

}
