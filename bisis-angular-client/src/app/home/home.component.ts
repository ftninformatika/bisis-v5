import { Component, OnInit } from '@angular/core';
import {AuthHelper} from "../auth/utilities/authhelper";
import {MemberService} from "../service/member.service";
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

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

              }
          );
        }
    );
  }

}
