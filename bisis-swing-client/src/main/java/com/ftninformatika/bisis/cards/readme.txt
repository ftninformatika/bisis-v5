Uputstvo za korišćenje paketa report

ako se prosledjuje jedan zapis poziva se  makeOne(docID, zapis u obliku stringa, ime listića) 

ako se prosledjuje više zapisa odjednom poziva se

makeMulti(int[], String[], String) gde je int[] niz id brojeva zapisa, String[] je niz zapisa u obliku stringa i 

String je ime listića 

pri podizanju sistema moraju se pozvati ucitajParam() i loadReportTypes()

ucitajParam() postavlja parametre za posmatranu instituciju tako što ih preuzme iz config.ini fajla

parametri su

Locale – ime ustanove služi da se odredi iz kog direktorijuma se učitavaju listic-templejti za listiće svaka institucija
ima svoj direktorijum 
nextPage – prelazak na narednu stranu koristi se u samim templejtima
currentType – default listić 



naredna parametri koriste se za podešavanje štampanja

translateX i translateY - od koje tačke u levom gornjem uglu listića kreće štampanje, ove vrednosti ubacuju se kao
parametri u funkciju translate kojom se podešava štampanje
fontSize – veličina slova ovaj parametar dodaje se stringu koji predstavlja htmlPrintHeader 
brRedova – broj redova na listiću koristi se u proceduri koja prilikom štampanja broji redove i određuje kada se formira
nova strana za štampanje

parametri za podešavanje štampanja koriste se u kalsi ReportDlgLis.java iz paketa Editor pa se tamo može videti kako
se koriste 



