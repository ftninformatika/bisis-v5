import {Component, Input, OnInit} from '@angular/core';
import {Message} from 'primeng/primeng';
@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {

  msgs: Message[];

  constructor() {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:'Info Message', detail:'PrimeNG rocks'});
  }

  ngOnInit() {
  }

  requestPasswordReset(email){
    console.log(email);
  }

}
