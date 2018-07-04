import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SelectItem} from "primeng/primeng";
import {TranslateService} from "@ngx-translate/core";
import {arrayify} from "tslint/lib/utils";
import {MessageService} from "primeng/components/common/messageservice";
import {RecordsPageModel} from "../../model/RecordsPageModel";
import {LibraryService} from "../../service/library.service";

@Component({
  selector: 'app-bisis-search',
  templateUrl: './bisis-search.component.html',
  styleUrls: ['./bisis-search.component.css']
})
export class BisisSearchComponent implements OnInit {


  searchResults: RecordsPageModel;
  // Kada nam je prosledjena biblioteka kao parametar (na pr. /gbns_com)
  lib: string;

  // Bez parametra, biramo po kojoj biblioteci pretrazujemo
  libList: SelectItem[];
  departmentList: SelectItem[];
  selectedDepartments: string[];
  selectedLibrary: string;
  libHeader: string;



  constructor(private route:ActivatedRoute, public router: Router, public libService: LibraryService,  private translate: TranslateService,
  private messageService: MessageService) {
    this.searchResults = new RecordsPageModel();
    //this.choose = {label:'Одаберите библиотеку', value: null};
    //this.selectedLibrary = 'gbns_com';
    localStorage.setItem("libCode", null);
    this.departmentList = [];
    this.libList = [];
    this.libList.push({label: 'Одаберите библиотеку', value: null});
    this.libList.push({label: 'Градска библиотека у Новом Саду', value: 'gbns'});
    this.libList.push({label: 'Библиотека шабачка', value: 'bs'});
    this.libList.push({label: 'Библиотека Града Београда', value: 'bgb'});
  }

  ngOnInit() {

    this.libService.getLibs().subscribe(
        response => {
          this.route.params.subscribe(
              params => {
                if (params['lib']) {
                  if (response.includes(params['lib'])){
                    this.lib = params['lib'];
                    //console.log("pretraga kao bilbioteka :" + this.lib);
                    localStorage.setItem('libCode', params['lib']);
                    this.selectedLibrary = params['lib'];
                    this.libHeader = this.getLibName(this.selectedLibrary);

                      this.libService.getDepartmentsForLib(this.selectedLibrary).subscribe(
                          response => {
                              this.departmentList = [];
                              this.selectedDepartments = [];
                              //console.log(response);
                              arrayify(response).forEach(
                                  d => {
                                      this.departmentList.push({"label": d.description, "value": d.coder_id});
                                  });
                          }
                      );
                  }
                  else{
                    //console.log("nepostojeca putanja, biblioteka, redirekcija na univerazalnu!!!");
                    this.router.navigate(['/bisis-search']);
                  }
                }
              }
          );
        }
    );
  }

  getLibName(libCode): string{
      for (let i of this.libList)
        if (i.value == libCode)
            return i.label.toString();
      return "";
  }

  selectionChanged(){
      this.departmentList = [];
      this.selectedDepartments = [];
    if (this.selectedLibrary == undefined || this.selectedLibrary == null)
      return

    this.libService.getDepartmentsForLib(this.selectedLibrary).subscribe(
        response => {

          //console.log(response);
          arrayify(response).forEach(
              d => {
                this.departmentList.push({"label": d.description, "value": d.coder_id});
              });
        }
    );
  }

  setRecords(event) {
    this.searchResults = event;
      if (this.searchResults.totalElements > 1000) {
          this.messageService.clear();
          this.messageService.add({
              severity: 'warning',
              summary: 'Упозорење',
              detail: 'Превелик скуп погодака, враћено 1000 од' + this.searchResults.totalElements + '! Формулишите бољи упит. '
          });
      }
    //console.log(this.searchResults);
  }

}