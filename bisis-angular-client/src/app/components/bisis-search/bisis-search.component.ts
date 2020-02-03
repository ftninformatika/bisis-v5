import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SelectItem} from 'primeng/primeng';
import {TranslateService} from '@ngx-translate/core';
import {arrayify} from 'tslint/lib/utils';
import {MessageService} from 'primeng/components/common/messageservice';
import {RecordsPageModel} from '../../model/RecordsPageModel';
import {LibraryService} from '../../service/library.service';

@Component({
  selector: 'app-bisis-search',
  templateUrl: './bisis-search.component.html',
  styleUrls: ['./bisis-search.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class BisisSearchComponent implements OnInit {

  // TODO - REFACTOR CELOG OPAC- A CIM BUDE VREMENA!!!!!
  searchResults: RecordsPageModel;
  // Kada nam je prosledjena biblioteka kao parametar (na pr. /gbns_com)
  lib: string;

  // Bez parametra, biramo po kojoj biblioteci pretrazujemo
  libList: SelectItem[];
  // Za bgb je ovo opstinka biblioteka
  departmentList: SelectItem[];
  selectedDepartments: string[];
  // Ogranci (samo bgb)
  branchesList: SelectItem[];
  selectedBranches: string[];
  selectedLibrary: string;
  libHeader: string;
  convert = require('latin-to-serbian-cyrillic');



  constructor(private route: ActivatedRoute, public router: Router, public libService: LibraryService,  private translate: TranslateService,
  private messageService: MessageService) {
    this.searchResults = new RecordsPageModel();
    localStorage.setItem('libCode', null);
    this.departmentList = [];
    this.libList = [];
    this.libList.push({label: 'Одаберите библиотеку', value: null});
    this.libList.push({label: 'Градска библиотека у Новом Саду', value: 'gbns'});
    this.libList.push({label: 'Библиотека шабачка', value: 'bs'});
    this.libList.push({label: 'Библиотека Града Београда', value: 'bgb'});
    this.libList.push({label: 'Библиотека Милутин Бојић', value: 'bmb'});
  }

  ngOnInit() {

    this.libService.getLibs().subscribe(
        response => {
          this.route.params.subscribe(
              params => {
                if (params['lib']) {
                  if (response.includes(params['lib'])) {
                    this.lib = params['lib'];
                    localStorage.setItem('libCode', params['lib']);
                    const dep = params['dep'] ? params['dep'] : undefined;
                    this.selectedLibrary = params['lib'];
                    this.libHeader = this.getLibName(this.selectedLibrary);
                    this.libService.getDepartmentsForLib(this.selectedLibrary).subscribe(
                          responseDeps => {
                              this.departmentList = [];
                              this.selectedDepartments = [];
                              arrayify(responseDeps).forEach(
                                  d => {
                                      const labelDesc = (this.selectedLibrary === 'bgb' ) ? this.convert(d.description) : d.description;
                                      this.departmentList.push({label: labelDesc, value: d.coder_id});
                                  });
                              if (this.selectedLibrary === 'bgb' || this.selectedLibrary === 'bmb') {
                                  let selectedDepId = responseDeps[0]['coder_id'];
                                  if (arrayify(responseDeps).some(d => d.coder_id === dep)) {
                                      selectedDepId = dep;
                                  } else if (!arrayify(responseDeps).some(d => d.coder_id === dep) && dep !== undefined) {
                                      this.router.navigate(['/bisis-search']);
                                  }
                                  this.selectedDepartments[0] = selectedDepId;
                                  this.selectionChangedBranch({'value': selectedDepId});
                              }
                          }
                      );
                  } else {
                    this.router.navigate(['/bisis-search']);
                  }
                }
              }
          );
        }
    );
  }

  getLibName(libCode): string {
      for (const i of this.libList) {
          if (i.value === libCode) {
              return i.label.toString();
          }
      }
      return '';
  }

  selectionChangedBranch(event) {
      this.branchesList = [];
      this.libService.getSublocationsForDepartment(this.selectedLibrary, event['value']).subscribe(
        response => {
            arrayify(response).forEach(
                d => {
                    const labelDesc = (this.selectedLibrary === 'bgb' ) ? this.convert(d.description) : d.description;
                    this.branchesList.push({'label': labelDesc, 'value': d.coder_id});
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
  }

    selectionChangedLibrary() {
        this.departmentList = [];
        this.branchesList = [];
        this.selectedDepartments = [];
        if (this.selectedLibrary === undefined || this.selectedLibrary == null) {
            return;
        }

        this.libService.getDepartmentsForLib(this.selectedLibrary).subscribe(
            response => {
                arrayify(response).forEach(
                    d => {
                        const labelDesc = (this.selectedLibrary === 'bgb' ) ? this.convert(d.description) : d.description;
                        this.departmentList.push({'label': labelDesc, 'value': d.coder_id});
                    });
                if (this.selectedLibrary === 'bgb') {
                    this.selectedDepartments[0] = response[0]['coder_id'];
                    this.selectionChangedBranch({'value': response[0]['coder_id']});
                    }
                }
            );
    }

}
