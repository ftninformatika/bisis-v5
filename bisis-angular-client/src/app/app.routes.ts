import {Routes, RouterModule} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {BisisSearchComponent} from './bisis-search/bisis-search.component';
import {RecordViewComponent} from './bisis-search/record-view/record-view.component';
import {AboutViewComponent} from './about-view/about-view.component';
import {MyBookshelfComponent} from './my-bookshelf/my-bookshelf.component';
import {LoginComponent} from "./auth/login/login.component";
import {ProfileComponent} from "./profile/profile.component";
import {HomeComponent} from "./home/home.component";
import {PasswordResetComponent} from "./auth/password-reset/password-reset.component";
import {PasswordResetConfirmationComponent} from "./auth/password-reset-confirmation/password-reset-confirmation.component";
import {AuthGuard} from "./auth/authguard";

export const routes: Routes = [
    {path: '', redirectTo: 'bisis-search', pathMatch: 'full'},
    //{path: 'home', component: HomeComponent},
    //{path: '#/:email', component: HomeComponent},
    {path: 'bisis-search', component: BisisSearchComponent},
    {path: 'bisis-search/:lib', component: BisisSearchComponent},
    {path: 'record-view/:libCode/:recId', component: RecordViewComponent},
    {path: 'about-view', component: AboutViewComponent},
    {path: 'my-bookshelf', canActivate: [ AuthGuard ], component: MyBookshelfComponent},
    {path: 'login', component: LoginComponent},
    {path: 'profile', canActivate: [ AuthGuard ], component: ProfileComponent},
    {path: 'forgot-pass', component: PasswordResetComponent},
    {path: 'password-reset-confirmation/:userId/:pRS', component:PasswordResetConfirmationComponent}
];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes);
