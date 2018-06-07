import {Routes, RouterModule} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {BisisSearchComponent} from "./components/bisis-search/bisis-search.component";
import {RecordViewComponent} from "./components/bisis-search/record-view/record-view.component";
import {AboutViewComponent} from "./components/about-view/about-view.component";
import {AuthGuard} from "./components/auth/authguard";
import {LoginComponent} from "./components/auth/login/login.component";
import {MyBookshelfComponent} from "./components/my-bookshelf/my-bookshelf.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {PasswordResetComponent} from "./components/auth/password-reset/password-reset.component";
import {PasswordResetConfirmationComponent} from "./components/auth/password-reset-confirmation/password-reset-confirmation.component";

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
