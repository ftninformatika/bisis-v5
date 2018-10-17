import { NgModule } from '@angular/core';
import { Http, RequestOptions } from '@angular/http';
import { JwtModule, JWT_OPTIONS } from '@auth0/angular-jwt';
import { PasswordResetConfirmationComponent } from './password-reset-confirmation/password-reset-confirmation.component';
import { HttpClientModule } from '@angular/common/http';
import {AuthGuard} from './authguard';
import {AuthService} from './auth.service';
import {AppRoutes} from '../../app.routes';

export function jwtOptionsFactory(authService: AuthService) {
    return {
        tokenGetter: () => {
            // return authService.getToken();
            return 'dummyToken';
        },
    };
}

@NgModule({
    imports     : [
        HttpClientModule,
        AppRoutes,

        // Jwt Token Injection
        JwtModule.forRoot({
            jwtOptionsProvider: {
                provide: JWT_OPTIONS,
                useFactory: jwtOptionsFactory,
                deps: [AuthService]
            }
        })
    ],
  providers: [
    {
      provide: AuthService,
      useFactory: AuthGuard,
      deps: [Http, RequestOptions]
    }
  ],
  declarations: [PasswordResetConfirmationComponent]
})
export class AuthModule {}
