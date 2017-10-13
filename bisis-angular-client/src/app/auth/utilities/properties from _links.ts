 /**Ovaj get smo koristili kada smo dodavali propertije iz _links-a
 kao propertije institution-a*/
/* getInstitutions() {
  this.institutionService.getInstitutions()
    .subscribe(
     institutions => {
      institutions.forEach(oneInst =>{
        this.addPropertyToObjectFromLinks('institution', oneInst)
         })
       this.institutions  = institutions as Institution[]
     },
     error => {
       this.errorMessage = <any>error})
  }
*/
  /**Genericka metoda koja kada joj prosledimo objekat ce iz _links
  izvuci sve propertije i dodati ih kao propertije u u isti*/
/*  addPropertyToObjectFromLinks(className: string, object: any) {
    var properties = []
    for(var key in object._links) {
      if(object._links.hasOwnProperty(key) && typeof object._links[key] !== 'function') {
          properties.push(key);
      }
    }
    properties.forEach(
      oneProp => {
      if (oneProp != 'self' && oneProp != className) {
        let href: string = object._links[oneProp].href
        
        this.countryService.loadBiloSta(href)
          .subscribe(
           response => object[oneProp] = response,
           error => this.errorMessage = <any>error)
      }
    })
  }
*/


/**Metoda koja na osnovu href-a koji se nalaze u property-ima u _links-u
  dovlaci get zahtevom objekte koje kasnije dodajemo kao propertije u zeljeni objekat*/
/*  loadBiloSta(href: string): Observable<any> {
    return this.authHttp.get(href)
      .map(response => response.json())
      .catch(this.handleError)       
  }
*/


/*

  addPropertyToObjectFrom_Embedded(object: any) {
    var properties = []
    for(var key in object._embedded) {
      if(object._embedded.hasOwnProperty(key) && typeof object._embedded[key] !== 'function') {
          properties.push(key);
      }
    }
    properties.forEach(
      oneProp => {
        object[oneProp] = object._embedded
      })
  }

*/





  /**https://angular.io/docs/ts/latest/api/core/index/OnChanges-class.html
  What it does: Lifecycle hook that is called when any data-bound property 
  of a directive changes.
  Class Description: ngOnChanges is called right after the data-bound properties 
  have been checked and before view and content children are checked if at least 
  one of them has changed. The changes parameter contains the changed properties.
  https://angular.io/docs/ts/latest/guide/lifecycle-hooks.html#!#onchanges*/
  // ngOnChanges(simpleChanges: SimpleChanges) {}

