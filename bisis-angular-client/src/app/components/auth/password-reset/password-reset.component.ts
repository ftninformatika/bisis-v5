import {Component,  OnInit} from '@angular/core';
import { Message, GrowlModule, MessageService } from 'primeng/primeng';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ApiConfig } from '../../../config/api.config';
@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {

  msgs: Message[];
  private readonly _httpClient: HttpClient;
  private readonly _messageService: MessageService;
  private readonly _router: Router;

  constructor(httpClient: HttpClient, router: Router, messageService: MessageService) {
      this._httpClient = httpClient;
      this._messageService = messageService;
      this._router = router;
  }

  ngOnInit() {
    this.msgs = [];
  }

  requestPasswordReset(email) {

      this._messageService.clear();

      if (this.validateEmail(email) === false) {
          this._messageService.add({
              severity: 'error',
              summary: 'Упозорење',
              detail: 'Унесите исправну email адресу.'
          });
          return;
      }


    this._httpClient.get(ApiConfig.origin + '/library_members/generate_reset?email=' + email).subscribe(
        (response: any) => {
            if (response.json() === true) {
                this._messageService.add({
                    severity: 'info',
                    summary: 'Обавештење',
                    detail: 'Линк за рестарт лозинке ће вам ускоро бити послат на вашу адресу!'
                });
                this._router.navigate(['/']);

            } else {
                this._messageService.add({
                    severity: 'error',
                    summary: 'Упозорење',
                    detail: 'Унета адреса не постоји као регистрована у нашем систему. Молимо вас покушајте поново.'
                });
            }
        }
    );
  }


    validateEmail(email) {
      const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    }
}
