import { Component, OnInit } from '@angular/core';
import {SelectItem} from "primeng/primeng";
import {MemberService} from "../service/member.service";
import {AuthHelper} from "../auth/utilities/authhelper";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  lendings: any[];
  userInfo: any;

  constructor(public ah: AuthHelper, public memberService: MemberService) { }


  ngOnInit() {
    this.memberService.getMemberData(localStorage.getItem("authenticatedUserId")).subscribe(
        response => {
          this.userInfo = response;
          this.memberService.getLendings(response.userId).subscribe(
              responseLendings => {
                console.log(responseLendings);
                this.lendings = responseLendings;
              }
          );
        }
    );
  }
}
