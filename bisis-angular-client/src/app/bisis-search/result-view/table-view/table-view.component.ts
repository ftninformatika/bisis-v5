import { Component, OnInit, Input } from '@angular/core';
import {Message} from 'primeng/components/common/api';

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.css']
})
export class TableViewComponent implements OnInit {

  @Input() resultRecords: any[];
  msgs: Message[];

  constructor() { }

  ngOnInit() {
  }

  onRowSelect(event) {
    this.msgs = [];
    this.msgs.push({severity: 'info', summary: 'Record Selected:', detail: event.data._id + ' - ' + event.data.creator.username});
  }


}
