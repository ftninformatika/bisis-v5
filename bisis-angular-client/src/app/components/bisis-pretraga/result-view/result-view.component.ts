import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-result-view',
  templateUrl: './result-view.component.html',
  styleUrls: ['./result-view.component.css']
})
export class ResultViewComponent implements OnInit {

  @Input() records: any[];
  @Input() resultViewType: any;
  //msgs: Message[];

  constructor() { 
  }

  selectCar(rec: any) {
    //this.msgs = [];
    //this.msgs.push({severity: 'info', summary: 'Car Selected', detail: 'Vin:'});
  }

  ngOnInit() {
    //this.data = this.sharedService.dataArray;
    //console.log("obavios")
  }

}
