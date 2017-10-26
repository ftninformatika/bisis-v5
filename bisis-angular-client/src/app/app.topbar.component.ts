import {Component} from '@angular/core';
import {AppComponent} from './app.component';
import {AuthHelper} from "./auth/utilities/authhelper";
import {Router} from "@angular/router";

@Component({
    selector: 'app-topbar',
    template: `
        <div class="topbar clearfix">
            <div class="topbar-left">
                <div class="logo"></div>
            </div>

            <div class="topbar-right">
                <a id="menu-button" href="#" (click)="app.onMenuButtonClick($event)">
                    <i></i>
                </a>
                <a id="topbar-menu-button" href="#" (click)="app.onTopbarMenuButtonClick($event)">
                    <i class="material-icons">menu</i>
                </a>
                <ul class="topbar-items animated fadeInDown" [ngClass]="{'topbar-items-visible': app.topbarMenuActive}">
                   
                    <li #login *ngIf="!(this.ah.authenticated)" [ngClass]="{'active-top-menu':app.activeTopbarItem === login}">
                         <a href="#/login" >
                            <button pButton label="{{ 'loginBtn' | translate }}" icon="ui-icon-person" class="blue-grey-btn"></button>
                        </a>
                    </li>
                     <li #login *ngIf="this.ah.authenticated" [ngClass]="{'active-top-menu':app.activeTopbarItem === logout}">
                         <a (click)="logout()" >
                            <button pButton label="{{ 'logoutBtn' | translate }}" icon="ui-icon-power-settings-new" class="blue-grey-btn"></button>
                        </a>
                    </li>
                   
                </ul>
            </div>
        </div>
    `
})
export class AppTopbarComponent {

    constructor(public app: AppComponent, public ah: AuthHelper) {}



    logout(){
        var libCode = localStorage.getItem('libCode');
        localStorage.clear();
        localStorage.setItem('libCode', libCode);
    }
}
