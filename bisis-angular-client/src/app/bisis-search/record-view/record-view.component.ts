import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BisisSearchService } from '../service/bisis-search.service';
@Component({
  selector: 'app-record-view',
  templateUrl: './record-view.component.html',
  styleUrls: ['./record-view.component.css']
})
export class RecordViewComponent implements OnInit {

  record: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public bisisService: BisisSearchService
  ) {
    this. record = null;
  }

  ngOnInit() {
     this.route
    .queryParams
    .subscribe(params => {
    });
  }

}
