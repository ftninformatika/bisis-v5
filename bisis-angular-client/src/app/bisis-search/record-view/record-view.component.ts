import { Component, OnInit, Input } from '@angular/core';
import { BisisSearchService } from '../service/bisis-search.service';
import {SelectItem} from 'primeng/primeng';
@Component({
  selector: 'app-record-view',
  templateUrl: './record-view.component.html',
  styleUrls: ['./record-view.component.css']
})
export class RecordViewComponent  {

  @Input() selectedRec: any;
  viewTypes: SelectItem[];
  selectedViewType: string;
  unimarcRows: string[];


  constructor(
    public bisisService: BisisSearchService
  ) {
    this.viewTypes = [];
    this.viewTypes.push({label: 'General', value:'general'});
    this.viewTypes.push({label: 'Unimarc', value:'unimarc'});
    this.selectedViewType = 'general';
  }

  makeUnimarc(record: any) {
    if(!record) {
      console.log('something went wrong');
      return;
    }

    var retVal = new Array();

    record.fields.forEach(element => {
      var el = '';
      el += element.name + ' ';
      if (element.ind1 == undefined || element.ind1 == null || element.ind1 == ' ' || element.ind1 == '' ) {
        el += '#';
      } else {
        el += element.ind1;
      }
      if (element.ind2 == undefined || element.ind2 == null || element.ind2 == ' ' || element.ind2 == '' ) {
        el += '# ';
      } else {
        el += element.ind2;
      }

      element.subfields.forEach(e => {
        el += '[' + e.name + ']' + e.content;
      });
       retVal.push(el);

    });
    this.unimarcRows = retVal;
  }

}
