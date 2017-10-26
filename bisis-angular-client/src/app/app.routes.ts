import {Routes, RouterModule} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {DashboardDemoComponent} from './demo/view/dashboarddemo.component';
import {SampleDemoComponent} from './demo/view/sampledemo.component';
import {FormsDemoComponent} from './demo/view/formsdemo.component';
import {DataDemoComponent} from './demo/view/datademo.component';
import {PanelsDemoComponent} from './demo/view/panelsdemo.component';
import {OverlaysDemoComponent} from './demo/view/overlaysdemo.component';
import {MenusDemoComponent} from './demo/view/menusdemo.component';
import {MessagesDemoComponent} from './demo/view/messagesdemo.component';
import {MiscDemoComponent} from './demo/view/miscdemo.component';
import {EmptyDemoComponent} from './demo/view/emptydemo.component';
import {ChartsDemoComponent} from './demo/view/chartsdemo.component';
import {FileDemoComponent} from './demo/view/filedemo.component';
import {UtilsDemoComponent} from './demo/view/utilsdemo.component';
import {DocumentationComponent} from './demo/view/documentation.component';
import {BisisSearchComponent} from './bisis-search/bisis-search.component';
import {RecordViewComponent} from './bisis-search/record-view/record-view.component';
import {AboutViewComponent} from './about-view/about-view.component';
import {MyBookshelfComponent} from './my-bookshelf/my-bookshelf.component';
import {LoginComponent} from "./auth/login/login.component";
import {ProfileComponent} from "./profile/profile.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuard} from "./auth/authguard";

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: '#/:email', component: HomeComponent},
    {path: 'sample', component: SampleDemoComponent},
    {path: 'forms', component: FormsDemoComponent},
    {path: 'data', component: DataDemoComponent},
    {path: 'panels', component: PanelsDemoComponent},
    {path: 'overlays', component: OverlaysDemoComponent},
    {path: 'menus', component: MenusDemoComponent},
    {path: 'messages', component: MessagesDemoComponent},
    {path: 'misc', component: MiscDemoComponent},
    {path: 'empty', component: EmptyDemoComponent},
    {path: 'charts', component: ChartsDemoComponent},
    {path: 'file', component: FileDemoComponent},
    {path: 'utils', component: UtilsDemoComponent},
    {path: 'documentation', component: DocumentationComponent},
    {path: 'bisis-search', component: BisisSearchComponent},
    {path: 'bisis-search/:lib', component: BisisSearchComponent},
    {path: 'record-view/:libCode/:recId', component: RecordViewComponent},
    {path: 'about-view', component: AboutViewComponent},
    {path: 'my-bookshelf', component: MyBookshelfComponent},
    {path: 'login', component: LoginComponent},
    {path: 'profile', canActivate: [ AuthGuard ], component: ProfileComponent}
];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes);
