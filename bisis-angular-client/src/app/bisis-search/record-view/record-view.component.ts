import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BisisSearchService } from '../service/bisis-search.service';
@Component({
  selector: 'app-record-view',
  templateUrl: './record-view.component.html',
  styleUrls: ['./record-view.component.css']
})
export class RecordViewComponent implements OnInit {

  @Input() selectedRec: any;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public bisisService: BisisSearchService
  ) {
  }

  ngOnInit() {
     this.route
    .queryParams
    .subscribe(params => {
    });
  }

}
