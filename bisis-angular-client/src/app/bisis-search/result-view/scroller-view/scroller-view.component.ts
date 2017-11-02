import { Component, OnInit, Input } from '@angular/core';
import {BisisSearchService} from "../../service/bisis-search.service";

import { RecordsPageModel } from '../../model/RecordsPageModel';

@Component({
  selector: 'app-scroller-view',
  templateUrl: './scroller-view.component.html',
  styleUrls: ['./scroller-view.component.css']
})
export class ScrollerViewComponent implements OnInit {

  @Input() resultRecords: RecordsPageModel;
  selectedRec: any;
  displayDialog: boolean;

  constructor( public bisisService: BisisSearchService) { }

  selectRec(rec) {
    this.selectedRec = rec;
    this.displayDialog = true;
  }

  ngOnInit() {
  }

  lazyLoadRecords(event){
    console.log(event);

    var page = event['first'] / event['rows'] ;
    var size = 20;

    if (this.resultRecords['query'] != undefined) { //univerzalna pretraga
        this.bisisService.searchRecordsByEP('universal', this.resultRecords['query'],  page, size).subscribe(
            response => {
              //console.log(response);
              //this.resultRecords['content'] = [];
                var responseObject = this.createInstanceFromJson(RecordsPageModel, response);
              this.resultRecords.content = this.resultRecords.content.concat(responseObject.content);
            }
        );
    }

  }

    createInstanceFromJson<T>(objType: { new(): T; }, json: any) {
        const newObj = new objType();
        const relationships = objType["relationships"] || {};

        for (const prop in json) {
            if (json.hasOwnProperty(prop)) {
                if (newObj[prop] == null) {
                    if (relationships[prop] == null) {
                        newObj[prop] = json[prop];
                    }
                    else {
                        newObj[prop] = this.createInstanceFromJson(relationships[prop], json[prop]);
                    }
                }
                else {
                    console.warn(`Property ${prop} not set because it already existed on the object.`);
                }
            }
        }

        return newObj;
    }

}
