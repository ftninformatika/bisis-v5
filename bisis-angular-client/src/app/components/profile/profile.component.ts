import { Component, OnInit } from '@angular/core';
import {SelectItem} from "primeng/primeng";
import {AuthHelper} from "../auth/utilities/authhelper";
import {Router} from "@angular/router";
import {ProgressBarModule} from 'primeng/primeng';
import {MemberService} from "../../service/member.service";
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  lendings: any[];
  userInfo: any;

  interval: any;
  value = 0;

  constructor(public ah: AuthHelper, public memberService: MemberService, public router: Router) { }


  ngOnInit() {
    this.memberService.getMemberData(localStorage.getItem("authenticatedUserId")).subscribe(
        response => {
          this.userInfo = response;
          this.memberService.getLendings(response.userId).subscribe(
              responseLendings => {
                //console.log(responseLendings);
                this.lendings = responseLendings;
              }
          );
        },
        err => {
            this.ah.logout();
            this.router.navigate(['/login']);
        }
    );

      //za progress bar
      this.interval = setInterval(() => {
          this.value = this.value + Math.floor(Math.random() * 10) + 1;
          if (this.value >= 100) {
              this.value = 100;
              clearInterval(this.interval);
              this.interval = null;
          }
      }, 50);
  }
}
