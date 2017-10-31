import {Component,  OnInit} from '@angular/core';
import {Message, GrowlModule} from 'primeng/primeng';
import {Http} from "@angular/http";
@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {

  msgs: Message[];

  constructor( public http: Http) {

  }

  ngOnInit() {
    this.msgs = [];
    //this.msgs.push({severity:'info', summary:'Info Message', detail:'PrimeNG rocks'});
  }

  requestPasswordReset(email){
    console.log(email);
    this.http.get("/library_members/generate_reset?email="+email).subscribe(
        response => {
            if (response.json() == true) {
                this.msgs = [];
                this.msgs.push({
                    severity: 'info',
                    summary: 'Обавештење',
                    detail: 'Линк за рестарт лозинке је успешно послат на вашу адресу!'
                });
            }
            else {
                this.msgs = [];
                this.msgs.push({
                    severity: 'error',
                    summary: 'Упозорење',
                    detail: 'Унета адреса не постоји као регистрована у нашем систему. Молимо вас покушајте поново.'
                });
            }
        }
    );
  }

}
