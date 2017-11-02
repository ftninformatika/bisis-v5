import { Component, OnInit, Input } from '@angular/core';
import {BisisSearchService} from "../../service/bisis-search.service";

import { RecordsPageModel } from '../../model/RecordsPageModel';
import {MessageService} from "primeng/components/common/messageservice";

@Component({
  selector: 'app-scroller-view',
  templateUrl: './scroller-view.component.html',
  styleUrls: ['./scroller-view.component.css']
})
export class ScrollerViewComponent implements OnInit {

  @Input() resultRecords: RecordsPageModel;
  selectedRec: any;
  displayDialog: boolean;

  constructor( public bisisService: BisisSearchService, public messageService: MessageService) { }

  selectRec(rec) {
    this.selectedRec = rec;
    this.displayDialog = true;
  }

  ngOnInit() {
  }

  lazyLoadRecords(event){

    var page = this.resultRecords.content.length / 20;
    var size = 20;

    if (this.resultRecords['query'] != undefined) { //univerzalna pretraga
        this.bisisService.searchRecordsByEP('universal', this.resultRecords['query'],  page, size).subscribe(
            response => {
                var responseObject = this.createInstanceFromJson(RecordsPageModel, response);
                if (this.resultRecords.content.length < 200){
                    this.resultRecords.content = this.resultRecords.content.concat(responseObject.content);
                }

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
