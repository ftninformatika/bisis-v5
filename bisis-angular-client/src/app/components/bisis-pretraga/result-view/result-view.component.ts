import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-result-view',
  templateUrl: './result-view.component.html',
  styleUrls: ['./result-view.component.css']
})
export class ResultViewComponent implements OnInit {

  @Input() records: any[];
  data: any[];

  constructor() { }

  ngOnInit() {
    //this.data = this.sharedService.dataArray;
    //console.log("obavios")
  }

}
