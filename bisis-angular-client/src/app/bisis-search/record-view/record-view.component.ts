import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BisisSearchService } from '../service/bisis-search.service';
@Component({
  selector: 'app-record-view',
  templateUrl: './record-view.component.html',
  styleUrls: ['./record-view.component.css']
})
export class RecordViewComponent  {

  _selectedRec: any;
  fullRecord: any;

  @Input() set selectedRec(rec: any) {
    this._selectedRec = rec;
    if (rec) { // Prvi put ulazi sa rec == undefined
      this.getFullRec(rec.id);
    }
  }

  get selectedRec(): any {
    return this._selectedRec;
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public bisisService: BisisSearchService
  ) {}

  readUnimarc(rec) {
    this.bisisService.getUnimarcForRecord(rec.id)
    .subscribe(
      response => console.log(response),
      error => console.log(error)
    );
    console.log(this.fullRecord);
    this.makeUnimarc(this.fullRecord);
    }

  getFullRec(id: any) {
    this.bisisService.getRecord(id)
    .subscribe(
      response => {
        this.fullRecord = response;
      },
      error => console.log(error)
    );
  }

  makeUnimarc(record: any): any {
    let retVal = '';

    record.fields.forEach(element => {
      retVal += element.name + ' ';
      // tslint:disable-next-line:triple-equals
      if (element.ind1 == undefined || element.ind1 == null || element.ind1 == ' ' || element.ind1 == '' ) {
        retVal += '#';
      } else {
        retVal += element.ind1;
      }
      // tslint:disable-next-line:triple-equals
      if (element.ind2 == undefined || element.ind2 == null || element.ind2 == ' ' || element.ind2 == '' ) {
        retVal += '#';
      } else {
        retVal += element.ind2;
      }

      element.subfields.forEach(e => {
        retVal += '[' + e.name + ']' + e.content;
      });

       retVal += ' \n';

    });
    console.log(retVal);
    return retVal;
  }

}
