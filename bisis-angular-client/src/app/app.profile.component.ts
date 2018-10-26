import {Component, } from '@angular/core';
import {AuthHelper} from './components/auth/utilities/authhelper';
import {trigger, state, transition, style, animate} from '@angular/animations';
import {AppComponent} from './app.component';

@Component({
    selector: 'app-inline-profile',
    template: `        
        <ul class="ultima-menu profile-menu " *ngIf="(this.ah.authenticated)" >
            <li  role="menuitem">
                <a href="#/profile" class="ripplelink" >
                    <i class="material-icons">person</i>
                    <span>{{ 'sideMenuProfile' | translate }}</span>
                </a>
            </li>
             <li role="menuitem">
                <a href="#/my-bookshelf" class="ripplelink" >
                    <i class="material-icons">book</i>
                    <span>{{ 'sideMenuShelf' | translate }}</span>
                </a>
            </li>
            <li role="menuitem">
                <a href="#" class="ripplelink" >
                    <i class="material-icons">security</i>
                    <span>{{ 'sideMenuPrivacy' | translate }}</span>
                </a>
            </li>
            <li role="menuitem">
                <a href="#" class="ripplelink" >
                    <i class="material-icons">settings_application</i>
                    <span>{{ 'sideMenuSettings' | translate }}</span>
                </a>
            </li>
            <!--li role="menuitem">
                <a href="#" (click)="this.ah.logout()" class="ripplelink" >
                    <i class="material-icons">power_settings_new</i>
                    <span>Одјавите се</span>
                </a>
            </li-->
        </ul>
        <!-- Disabled buttons, kada korisnik nije ulogovan -->
         <ul class="ultima-menu profile-menu fc-state-disabled" *ngIf="!(this.ah.authenticated)" 
            pTooltip="{{ 'loginTooltip' | translate }}" >
            <li  role="menuitem">
                <a href="#/profile" class="ripplelink" >
                    <i class="material-icons">person</i>
                    <span>{{ 'sideMenuProfile' | translate }}</span>
                </a>
            </li>
             <li role="menuitem">
                <a href="#" class="ripplelink" >
                    <i class="material-icons">book</i>
                    <span>{{ 'sideMenuShelf' | translate }}</span>
                </a>
            </li>
            <li role="menuitem">
                <a href="#/profile" class="ripplelink" >
                    <i class="material-icons">security</i>
                    <span>{{ 'sideMenuPrivacy' | translate }}</span>
                </a>
            </li>
            <li role="menuitem">
                <a href="#/profile" class="ripplelink" >
                    <i class="material-icons">settings_application</i>
                    <span>{{ 'sideMenuSettings' | translate }}</span>
                </a>
            </li>
        </ul>
    `,
    animations: [
        trigger('menu', [
            state('hidden', style({
                height: '0px'
            })),
            state('visible', style({
                height: '*'
            })),
            transition('visible => hidden', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)')),
            transition('hidden => visible', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)'))
        ])
    ]
})
export class AppInlineProfileComponent {

    active: boolean;

    constructor( public ah: AuthHelper ) {

    }

    onClick(event) {
        this.active = !this.active;
        event.preventDefault();
    }



}
