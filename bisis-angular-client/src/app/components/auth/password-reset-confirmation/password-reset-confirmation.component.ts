import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "primeng/components/common/messageservice";
import {Http} from "@angular/http";

@Component({
  selector: 'app-password-reset-confirmation',
  templateUrl: './password-reset-confirmation.component.html',
  styleUrls: ['./password-reset-confirmation.component.css']
})
export class PasswordResetConfirmationComponent implements OnInit {

    userId: string;
    pRS: string;

  constructor(private route:ActivatedRoute, public router: Router,private messageService: MessageService, private http: Http) {

  }

    confirmNewPassword(pass1, pass2){

      this.messageService.clear();

      if (pass1 != pass2){
          this.messageService.add({
              severity: 'error',
              summary: 'Упозорење',
              detail: 'Лозинке се морају поклапати!'
          });
          return;
      }

      if (this.validatePasswordStrength(pass1) == false){
            this.messageService.add({
                severity: 'error',
                summary: 'Упозорење',
                detail: 'Лозинке мора имати најманје 6 знакова!'
            });
            return;
       }

       var passDTO = {
          "newPassword": pass1,
          "newPasswordAgain": pass2,
          "passwordResetString": this.pRS,
          "userId": this.userId
       }

       this.http.post("/library_members/reset_password", passDTO).subscribe(
           response =>{
               if (response.json() == false) {
                   this.messageService.add({
                       severity: 'error',
                       summary: 'Грешка',
                       detail: 'Грешка у промени лозинке!'
                   });
               }
                else{
                       this.messageService.add({
                           severity: 'info',
                           summary: 'Обавештење',
                           detail: 'Успешно промењена лозинка!'
                       });
                       this.router.navigate(['/login']);
                   }
               }
       );


    }

    validatePasswordStrength(pass){
        if (pass.toString().length < 6)
            return false;
        return true;
    }

  ngOnInit() {
      this.pRS = '';
      this.userId = '';

      this.route.params.subscribe(
          params => {
              if(params['userId'] != undefined && params['pRS'] != undefined)
              {
                  this.userId = params['userId'];
                  this.pRS = params['pRS'];
                  alert(this.pRS);
              }

          }
      );
  }

}
