import { ItemStatus } from '../model/coders/ItemStatus';
import { ItemAvailability } from '../model/ItemAvailability';
import { Injectable } from '@angular/core';
import { PresentItem } from '../model/PresentItem';

@Injectable()
export class PresentItemGenerator {
    convert = require('latin-to-serbian-cyrillic');

    public generatePresentItems(free: string[], lended: string[], notLendable: string[],
                                itemAvailabilities: ItemAvailability[]): PresentItem[] {
        const retVal: PresentItem[] = [];

        for (let i = 0; i < itemAvailabilities.length; i++) {
            if (free.includes(itemAvailabilities[i].ctlgNo)) {
              let libDep =  itemAvailabilities[i].libDepartment;
              if (typeof libDep === 'string') {
                  libDep = (localStorage.getItem('libCode') === 'bgb') ? this.convert(itemAvailabilities[i].libDepartment)
                      : itemAvailabilities[i].libDepartment;
              }
              retVal.push({
                  invBroj: itemAvailabilities[i].ctlgNo,
                  libDepartment: libDep,
                  presentation: 'слободан',
                  presentationId: 1
              });
            } else if (lended.includes(itemAvailabilities[i].ctlgNo)) {
                let libDep =  itemAvailabilities[i].libDepartment;
                if (typeof libDep === 'string') {
                    libDep = (localStorage.getItem('libCode') === 'bgb') ? this.convert(itemAvailabilities[i].libDepartment)
                        : itemAvailabilities[i].libDepartment;
                }
                retVal.push({
                    invBroj: itemAvailabilities[i].ctlgNo,
                    libDepartment: libDep,
                    presentation: 'заузет',
                    presentationId: 2
                });
            } else if (notLendable.includes(itemAvailabilities[i].ctlgNo)) {
                let libDep =  itemAvailabilities[i].libDepartment;
                if (typeof libDep === 'string') {
                    libDep = (localStorage.getItem('libCode') === 'bgb') ? this.convert(itemAvailabilities[i].libDepartment)
                        : itemAvailabilities[i].libDepartment;
                }
                retVal.push({
                    invBroj: itemAvailabilities[i].ctlgNo,
                    libDepartment: libDep,
                    presentation: 'незадужив',
                    presentationId: 3
                });
            }
        }
        return retVal;
    }

    public generatePresentItemsList(record: any, statuses: ItemStatus[], itemAvailabilities: ItemAvailability[]): PresentItem[] {
        const lendables = this.getLenablesIds(statuses);
        const showables = this.getShowablesIds(statuses);
        const free = [];
        const lended = [];
        const notLendable = [];
        // monografija
        if (record['pubType'] === 1) {
            for (let i = 0; i < record['primerci'].length; i++) {
                if (showables.includes(record['primerci'][i]['status'])) {
                    if (lendables.includes(record['primerci'][i]['status']) === false) {
                        notLendable.push(record['primerci'][i]['invBroj']);
                    } else {
                        if (this.isPrimerakLended(record['primerci'][i]['invBroj'], itemAvailabilities)) {
                            lended.push(record['primerci'][i]['invBroj']);
                        } else {
                            free.push(record['primerci'][i]['invBroj']);
                        }
                    }
                }
            } // serijska
        } else if (record['pubType'] === 2) {
            for (let i = 0; i < record['godine'].length; i++) {
                if (showables.includes(record['godine'][i]['status'])) {
                    if (lendables.includes(record['godine'][i]['status']) === false) {
                        notLendable.push(record['godine'][i]['invBroj']);
                    } else {
                        if (this.isPrimerakLended(record['primerci'][i]['invBroj'], itemAvailabilities)) {
                            lended.push(record['godine'][i]['invBroj']);
                        } else {
                            free.push(record['godine'][i]['invBroj']);
                        }
                    }
                }
            }
        }

        return this.generatePresentItems(free, lended, notLendable, itemAvailabilities);
    }

    private isPrimerakLended(invNum: string, itemAvailabilities: ItemAvailability[]): boolean {
        let retVal = false;
        for (let i = 0; i < itemAvailabilities.length; i++) {
            if (itemAvailabilities[i].ctlgNo === invNum) {
                retVal = itemAvailabilities[i].borrowed;
                break;
            }
        }
        return retVal;
    }

    private getLenablesIds(statuses: ItemStatus[]): string[] {
        const retVal = [];
        for (let i = 0; i < statuses.length; i++) {
            if (statuses[i].lendable) {
                retVal.push(statuses[i].coder_id);
            }
        }
        return retVal;
    }

    private getShowablesIds(statuses: ItemStatus[]): string[] {
        const retVal = [];
        for (let i = 0; i < statuses.length; i++) {
            if (statuses[i].showable) {
                retVal.push(statuses[i].coder_id);
            }
        }
        return retVal;
    }
}
