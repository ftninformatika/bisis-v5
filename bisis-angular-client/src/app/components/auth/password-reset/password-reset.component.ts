import {Component,  OnInit} from '@angular/core';
import {Message, GrowlModule} from 'primeng/primeng';
import {Http} from "@angular/http";
import {Router} from "@angular/router";
import {MessageService} from "primeng/components/common/messageservice";
import {config} from "../../../config/config";
@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {

  msgs: Message[];

  constructor( public http: Http,public router: Router,private messageService: MessageService) {

  }

  ngOnInit() {
    this.msgs = [];
  }

  requestPasswordReset(email){

      this.messageService.clear();

      if (this.validateEmail(email) == false){

          this.messageService.add({
              severity: 'error',
              summary: 'Упозорење',
              detail: 'Унесите исправну email адресу.'
          });
          return;
      }


    this.http.get(config.getEnvironmentVariable('endPoint') + "/library_members/generate_reset?email="+email).subscribe(
        response => {
            if (response.json() == true) {
                this.messageService.add({
                    severity: 'info',
                    summary: 'Обавештење',
                    detail: 'Линк за рестарт лозинке ће вам ускоро бити послат на вашу адресу!'
                });
                this.router.navigate(['/']);

            }
            else {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Упозорење',
                    detail: 'Унета адреса не постоји као регистрована у нашем систему. Молимо вас покушајте поново.'
                });
            }
        }
    );
  }


    validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
    }
}
