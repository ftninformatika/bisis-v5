//mongeez formatted javascript

//changeset petar:ChangeSet-circulation

db.coders.warning_type.insert(
    [ {
        "library" : "gbns",
        "description" : "Opomena",
        "template" : "<war:root xmlns:war=\"warning\"><opomena><zaglavlje><naziv>Gradska biblioteka u Novom Sadu</naziv><biblioteka>Informaciono-referalni centar sa čitaonicom</biblioteka><ogranak/><sifra/><adresa>Dunavska 1</adresa><mesto>21000 Novi Sad</mesto><bropomenetext/><bropomene/><naslov>OPOMENA</naslov><roktext>rok vraćanja:</roktext></zaglavlje><body><textiznad>Uvidom u našu evidenciju ustanovili smo da niste u predviđenom roku vratili pozajmljene knjigu-e:</textiznad><tabelazg><rbr>R.br.</rbr><naslov>Naslov</naslov><autor>Autor</autor><invbroj>Inv.broj</invbroj><signatura>Signatura</signatura><brdana>Br.dana prekoračenja</brdana></tabelazg><textispod>Molimo Vas da svoju obavezu ispunite u roku od 3 (tri) dana kako ne bi došlo do primene drugih predviđenih mera, kao i da prilikom vraćanja knjiga u Gradsku biblioteku u Novom Sadu, ogranak \"Đura Daničić\" Dunavska 1, izvršite obavezu nadoknade troškova u iznosu:</textispod><nadoknada1/><nadoknada2/><cena1>0.0</cena1><cena2>0.0</cena2><din/><dodatuma>Ukupno do</dodatuma><napomena>Napomena: Obavezno poneti i ovu opomenu.</napomena></body><podaci><prezime/><ime/><roditelj/><imeroditelja/><adresa/><mesto/><zip/><useridtext>br. članske karte:</useridtext><userid/><docno/><docmesto/><jmbg/></podaci><footer><pojedinacno/><trostrukotext/><biblioteka/><direktor/><ime/></footer></opomena><cirilica>1</cirilica></war:root>"
    }, {
        "library" : "gbns",
        "description" : "Opomena pred utuženje",
        "template" : "<war:root xmlns:war=\"warning\"><opomena><zaglavlje><naziv>Biblioteka grada Beograda</naziv><biblioteka>BIBLIOTEKA GRADA BEOGRADA</biblioteka><ogranak>Ogranak </ogranak><sifra>Šifra: 01</sifra><adresa>Knez Mihailova 56</adresa><mesto>11000 Beograd</mesto><bropomenetext>Br.opomene:</bropomenetext><bropomene/><naslov>OPOMENA PRED UTUŽENJE</naslov><roktext>rok vraćanja:</roktext></zaglavlje><body><textiznad>Uvidom u našu evidenciju ustanovili smo da niste u predviđenom roku vratili pozajmljene knjigu-e:</textiznad><tabelazg><rbr>R.br.</rbr><naslov>Naslov</naslov><autor>Autor</autor><invbroj>Inv.broj</invbroj><signatura>Signatura</signatura><brdana>Br.dana prekoračenja</brdana></tabelazg><textispod>Molimo vas da svoju obavezu isputnite u roku od 3 (tri) dana kako ne bi doslo do primene drugih predviđenih mera, kao i da prilikom vraćanja knjiga u Biblioiteku grada Beograda, ul. Knez Mihailova 56, izvršite obavezu nadoknade troškova u iznosu:</textispod><nadoknada1>nadoknada za prekoračenje roka dnevno po svakoj knjizi:</nadoknada1><nadoknada2>nadoknada za troškove opomene:</nadoknada2><cena1>3</cena1><cena2>200</cena2><din>din.</din><dodatuma>Ukupno do</dodatuma><napomena/></body><podaci><prezime/><ime/><roditelj>roditelj</roditelj><imeroditelja/><adresa/><mesto/><zip/><useridtext>br. članske karte:</useridtext><userid/><docno>br. lične karte: </docno><docmesto>mesto izdavanja:</docmesto><jmbg>JMBG:</jmbg></podaci><footer><pojedinacno>Pojedinačna vrednost knjiga:</pojedinacno><trostrukotext>Trostruka vrednost knjiga:</trostrukotext><biblioteka>Biblioteka grada Beograda</biblioteka><direktor>Direktor</direktor><ime>Jovan Radulović</ime></footer></opomena><cirilica>0</cirilica></war:root>"
    }, {
        "library" : "gbns",
        "description" : "Opomena pred utuženje - treća lica",
        "template" : "<war:root xmlns:war=\"warning\"><opomena><zaglavlje><naziv>Biblioteka grada Beograda</naziv><biblioteka>BIBLIOTEKA GRADA BEOGRADA</biblioteka><ogranak>Ogranak </ogranak><sifra>Šifra: 01</sifra><adresa>Knez Mihailova 56</adresa><mesto>11000 Beograd</mesto><bropomenetext>Br.opomene:</bropomenetext><bropomene/><naslov>OPOMENA PRED UTUŽENJE</naslov><roktext>rok vraćanja:</roktext></zaglavlje><body><textiznad>Uvidom u našu evidenciju ustanovili smo da niste u predviđenom roku vratili pozajmljene knjigu-e:</textiznad><tabelazg><rbr>R.br.</rbr><naslov>Naslov</naslov><autor>Autor</autor><invbroj>Inv.broj</invbroj><signatura>Signatura</signatura><brdana>Br.dana prekoračenja</brdana></tabelazg><textispod>Molimo vas da svoju obavezu isputnite u roku od 3 (tri) dana kako ne bi doslo do primene drugih predviđenih mera, kao i da prilikom vraćanja knjiga u Biblioiteku grada Beograda, ul. Knez Mihailova 56, izvršite obavezu nadoknade troškova u iznosu:</textispod><nadoknada1>nadoknada za prekoračenje roka dnevno po svakoj knjizi:</nadoknada1><nadoknada2>nadoknada za troškove opomene:</nadoknada2><cena1>3</cena1><cena2>200</cena2><din>din.</din><dodatuma>Ukupno do</dodatuma><napomena/></body><podaci><prezime/><ime/><roditelj>roditelj</roditelj><imeroditelja/><adresa/><mesto/><zip/><useridtext>br. članske karte:</useridtext><userid/><docno>br. lične karte: </docno><docmesto/><jmbg>JMBG:</jmbg></podaci><footer><pojedinacno/><trostrukotext>Trostruka vrednost knjiga:</trostrukotext><biblioteka>Biblioteka grada Beograda</biblioteka><direktor>Direktor</direktor><ime>Jovan Radulović</ime></footer></opomena><cirilica>0</cirilica></war:root>"
    } ]
);

db.coders.user_categ.insert(
    [ {
        "library" : "gbns",
        "description" : "Deca do 14 godina",
        "titlesNo" : 15,
        "period" : 21,
        "maxPeriod" : 0
    }, {
        "library" : "gbns",
        "description" : "Učenici srednjih škola",
        "titlesNo" : 15,
        "period" : 21,
        "maxPeriod" : 0
    }, {
        "library" : "gbns",
        "description" : "Studenti",
        "titlesNo" : 15,
        "period" : 21,
        "maxPeriod" : 0
    }, {
        "library" : "gbns",
        "description" : "Zaposleni",
        "titlesNo" : 15,
        "period" : 21,
        "maxPeriod" : 0
    }, {
        "library" : "gbns",
        "description" : "Penzioneri",
        "titlesNo" : 15,
        "period" : 21,
        "maxPeriod" : 0
    }, {
        "library" : "gbns",
        "description" : "Zemljoradnici",
        "titlesNo" : 15,
        "period" : 21,
        "maxPeriod" : 0
    }, {
        "library" : "gbns",
        "description" : "Ostali",
        "titlesNo" : 15,
        "period" : 21,
        "maxPeriod" : 0
    }, {
        "library" : "gbns",
        "description" : "Nepoznato",
        "titlesNo" : 15,
        "period" : 21,
        "maxPeriod" : 0
    }, {
        "library" : "gbns",
        "description" : "Nepoznato",
        "titlesNo" : 15,
        "period" : 21,
        "maxPeriod" : 0
    } ]
);

db.coders.membership_type.insert(
    [ {
        "library" : "gbns",
        "description" : "R (redovno plaćanje)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "U1 (deca do 14 godina)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "U2 (deca 50%)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "U3 (vojnici)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "U4 (iz RO)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "U5 (50%popust)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "UE (EURO-26 20%popust)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "UK (učenici)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "PP (porodično-plaćanje)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "PB (porodi~no-besplatno)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "B1 (službena zaduženja)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "B2 (darodavci)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "B3 (sa knjigom)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "B4 (socijalni slučajevi)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "B5 (počasni članovi)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "B6 (iz drugih ogranaka)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "BP (prvaci)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "CM (jedan mesec)",
        "period" : 30
    }, {
        "library" : "gbns",
        "description" : "CT (tri meseca)",
        "period" : 90
    }, {
        "library" : "gbns",
        "description" : "CP (šest meseci)",
        "period" : 180
    }, {
        "library" : "gbns",
        "description" : "N (posebna zaduženja)",
        "period" : 180
    }, {
        "library" : "gbns",
        "description" : "E1 Britanski Savet 100%",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "E2 Britanski Savet 50%",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "E3 Britanski Savet Deca 50%",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "E4 Britanski Savet kolektivno",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "E5 Britanski Savet popust za odrasle",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "E6 Britanski Savet popust za decu",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "B7 (Preko 70 godina)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "KG (kurs grčkog)",
        "period" : 30
    }, {
        "library" : "gbns",
        "description" : "GC (Gete centar)",
        "period" : 365
    }, {
        "library" : "gbns",
        "description" : "BM (besplatno 1 mesec)",
        "period" : 30
    } ]
);

db.coders.membership.insert(
    [ {
        "library" : "gbns",
        "memberType" : "1",
        "userCateg" : "1",
        "cost" : 500.0
    }, {
        "library" : "gbns",
        "memberType" : "2",
        "userCateg" : "1",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "3",
        "userCateg" : "1",
        "cost" : 150.0
    }, {
        "library" : "gbns",
        "memberType" : "5",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "6",
        "userCateg" : "1",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "7",
        "userCateg" : "1",
        "cost" : 400.0
    }, {
        "library" : "gbns",
        "memberType" : "8",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "9",
        "userCateg" : "1",
        "cost" : 700.0
    }, {
        "library" : "gbns",
        "memberType" : "10",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "11",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "12",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "13",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "14",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "15",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "16",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "17",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "18",
        "userCateg" : "1",
        "cost" : 100.0
    }, {
        "library" : "gbns",
        "memberType" : "19",
        "userCateg" : "1",
        "cost" : 200.0
    }, {
        "library" : "gbns",
        "memberType" : "20",
        "userCateg" : "1",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "21",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "1",
        "userCateg" : "2",
        "cost" : 500.0
    }, {
        "library" : "gbns",
        "memberType" : "2",
        "userCateg" : "2",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "3",
        "userCateg" : "2",
        "cost" : 150.0
    }, {
        "library" : "gbns",
        "memberType" : "5",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "6",
        "userCateg" : "2",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "7",
        "userCateg" : "2",
        "cost" : 400.0
    }, {
        "library" : "gbns",
        "memberType" : "8",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "9",
        "userCateg" : "2",
        "cost" : 700.0
    }, {
        "library" : "gbns",
        "memberType" : "10",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "11",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "12",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "13",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "14",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "15",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "16",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "17",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "18",
        "userCateg" : "2",
        "cost" : 100.0
    }, {
        "library" : "gbns",
        "memberType" : "19",
        "userCateg" : "2",
        "cost" : 200.0
    }, {
        "library" : "gbns",
        "memberType" : "20",
        "userCateg" : "2",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "21",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "1",
        "userCateg" : "3",
        "cost" : 500.0
    }, {
        "library" : "gbns",
        "memberType" : "2",
        "userCateg" : "3",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "3",
        "userCateg" : "3",
        "cost" : 150.0
    }, {
        "library" : "gbns",
        "memberType" : "5",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "6",
        "userCateg" : "3",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "7",
        "userCateg" : "3",
        "cost" : 400.0
    }, {
        "library" : "gbns",
        "memberType" : "8",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "9",
        "userCateg" : "3",
        "cost" : 700.0
    }, {
        "library" : "gbns",
        "memberType" : "10",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "11",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "12",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "13",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "14",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "15",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "16",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "17",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "18",
        "userCateg" : "3",
        "cost" : 100.0
    }, {
        "library" : "gbns",
        "memberType" : "19",
        "userCateg" : "3",
        "cost" : 200.0
    }, {
        "library" : "gbns",
        "memberType" : "20",
        "userCateg" : "3",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "21",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "1",
        "userCateg" : "4",
        "cost" : 500.0
    }, {
        "library" : "gbns",
        "memberType" : "2",
        "userCateg" : "4",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "3",
        "userCateg" : "4",
        "cost" : 150.0
    }, {
        "library" : "gbns",
        "memberType" : "5",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "6",
        "userCateg" : "4",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "7",
        "userCateg" : "4",
        "cost" : 400.0
    }, {
        "library" : "gbns",
        "memberType" : "8",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "9",
        "userCateg" : "4",
        "cost" : 700.0
    }, {
        "library" : "gbns",
        "memberType" : "10",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "11",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "12",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "13",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "14",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "15",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "16",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "17",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "18",
        "userCateg" : "4",
        "cost" : 100.0
    }, {
        "library" : "gbns",
        "memberType" : "19",
        "userCateg" : "4",
        "cost" : 200.0
    }, {
        "library" : "gbns",
        "memberType" : "20",
        "userCateg" : "4",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "21",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "1",
        "userCateg" : "5",
        "cost" : 500.0
    }, {
        "library" : "gbns",
        "memberType" : "2",
        "userCateg" : "5",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "3",
        "userCateg" : "5",
        "cost" : 150.0
    }, {
        "library" : "gbns",
        "memberType" : "5",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "6",
        "userCateg" : "5",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "7",
        "userCateg" : "5",
        "cost" : 400.0
    }, {
        "library" : "gbns",
        "memberType" : "8",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "9",
        "userCateg" : "5",
        "cost" : 700.0
    }, {
        "library" : "gbns",
        "memberType" : "10",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "11",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "12",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "13",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "14",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "15",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "16",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "17",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "18",
        "userCateg" : "5",
        "cost" : 100.0
    }, {
        "library" : "gbns",
        "memberType" : "19",
        "userCateg" : "5",
        "cost" : 200.0
    }, {
        "library" : "gbns",
        "memberType" : "20",
        "userCateg" : "5",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "21",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "1",
        "userCateg" : "6",
        "cost" : 500.0
    }, {
        "library" : "gbns",
        "memberType" : "2",
        "userCateg" : "6",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "3",
        "userCateg" : "6",
        "cost" : 150.0
    }, {
        "library" : "gbns",
        "memberType" : "5",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "6",
        "userCateg" : "6",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "7",
        "userCateg" : "6",
        "cost" : 400.0
    }, {
        "library" : "gbns",
        "memberType" : "8",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "9",
        "userCateg" : "6",
        "cost" : 700.0
    }, {
        "library" : "gbns",
        "memberType" : "10",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "11",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "12",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "13",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "14",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "15",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "16",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "17",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "18",
        "userCateg" : "6",
        "cost" : 100.0
    }, {
        "library" : "gbns",
        "memberType" : "19",
        "userCateg" : "6",
        "cost" : 200.0
    }, {
        "library" : "gbns",
        "memberType" : "20",
        "userCateg" : "6",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "21",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "1",
        "userCateg" : "7",
        "cost" : 500.0
    }, {
        "library" : "gbns",
        "memberType" : "2",
        "userCateg" : "7",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "3",
        "userCateg" : "7",
        "cost" : 150.0
    }, {
        "library" : "gbns",
        "memberType" : "5",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "6",
        "userCateg" : "7",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "7",
        "userCateg" : "7",
        "cost" : 400.0
    }, {
        "library" : "gbns",
        "memberType" : "8",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "9",
        "userCateg" : "7",
        "cost" : 700.0
    }, {
        "library" : "gbns",
        "memberType" : "10",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "11",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "12",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "13",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "14",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "15",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "16",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "17",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "18",
        "userCateg" : "7",
        "cost" : 100.0
    }, {
        "library" : "gbns",
        "memberType" : "19",
        "userCateg" : "7",
        "cost" : 200.0
    }, {
        "library" : "gbns",
        "memberType" : "20",
        "userCateg" : "7",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "21",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "1",
        "userCateg" : "8",
        "cost" : 500.0
    }, {
        "library" : "gbns",
        "memberType" : "2",
        "userCateg" : "8",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "3",
        "userCateg" : "8",
        "cost" : 150.0
    }, {
        "library" : "gbns",
        "memberType" : "5",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "6",
        "userCateg" : "8",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "7",
        "userCateg" : "8",
        "cost" : 400.0
    }, {
        "library" : "gbns",
        "memberType" : "8",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "9",
        "userCateg" : "8",
        "cost" : 700.0
    }, {
        "library" : "gbns",
        "memberType" : "10",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "11",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "12",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "13",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "14",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "15",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "16",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "17",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "18",
        "userCateg" : "8",
        "cost" : 100.0
    }, {
        "library" : "gbns",
        "memberType" : "19",
        "userCateg" : "8",
        "cost" : 200.0
    }, {
        "library" : "gbns",
        "memberType" : "20",
        "userCateg" : "8",
        "cost" : 250.0
    }, {
        "library" : "gbns",
        "memberType" : "21",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "2",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "22",
        "userCateg" : "1",
        "cost" : 1600.0
    }, {
        "library" : "gbns",
        "memberType" : "22",
        "userCateg" : "2",
        "cost" : 1600.0
    }, {
        "library" : "gbns",
        "memberType" : "22",
        "userCateg" : "3",
        "cost" : 1600.0
    }, {
        "library" : "gbns",
        "memberType" : "22",
        "userCateg" : "4",
        "cost" : 1600.0
    }, {
        "library" : "gbns",
        "memberType" : "22",
        "userCateg" : "5",
        "cost" : 1600.0
    }, {
        "library" : "gbns",
        "memberType" : "22",
        "userCateg" : "6",
        "cost" : 1600.0
    }, {
        "library" : "gbns",
        "memberType" : "22",
        "userCateg" : "7",
        "cost" : 1600.0
    }, {
        "library" : "gbns",
        "memberType" : "22",
        "userCateg" : "8",
        "cost" : 1600.0
    }, {
        "library" : "gbns",
        "memberType" : "23",
        "userCateg" : "1",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "23",
        "userCateg" : "2",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "23",
        "userCateg" : "3",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "23",
        "userCateg" : "4",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "23",
        "userCateg" : "5",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "23",
        "userCateg" : "6",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "23",
        "userCateg" : "7",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "23",
        "userCateg" : "8",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "24",
        "userCateg" : "1",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "24",
        "userCateg" : "2",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "24",
        "userCateg" : "3",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "24",
        "userCateg" : "4",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "24",
        "userCateg" : "5",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "24",
        "userCateg" : "6",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "24",
        "userCateg" : "7",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "24",
        "userCateg" : "8",
        "cost" : 800.0
    }, {
        "library" : "gbns",
        "memberType" : "25",
        "userCateg" : "1",
        "cost" : 1200.0
    }, {
        "library" : "gbns",
        "memberType" : "25",
        "userCateg" : "2",
        "cost" : 1200.0
    }, {
        "library" : "gbns",
        "memberType" : "25",
        "userCateg" : "3",
        "cost" : 1200.0
    }, {
        "library" : "gbns",
        "memberType" : "25",
        "userCateg" : "4",
        "cost" : 1200.0
    }, {
        "library" : "gbns",
        "memberType" : "25",
        "userCateg" : "5",
        "cost" : 1200.0
    }, {
        "library" : "gbns",
        "memberType" : "25",
        "userCateg" : "6",
        "cost" : 1200.0
    }, {
        "library" : "gbns",
        "memberType" : "25",
        "userCateg" : "7",
        "cost" : 1200.0
    }, {
        "library" : "gbns",
        "memberType" : "25",
        "userCateg" : "8",
        "cost" : 1200.0
    }, {
        "library" : "gbns",
        "memberType" : "26",
        "userCateg" : "2",
        "cost" : 1100.0
    }, {
        "library" : "gbns",
        "memberType" : "26",
        "userCateg" : "3",
        "cost" : 1100.0
    }, {
        "library" : "gbns",
        "memberType" : "26",
        "userCateg" : "4",
        "cost" : 1100.0
    }, {
        "library" : "gbns",
        "memberType" : "26",
        "userCateg" : "5",
        "cost" : 1100.0
    }, {
        "library" : "gbns",
        "memberType" : "26",
        "userCateg" : "6",
        "cost" : 1100.0
    }, {
        "library" : "gbns",
        "memberType" : "26",
        "userCateg" : "7",
        "cost" : 1100.0
    }, {
        "library" : "gbns",
        "memberType" : "26",
        "userCateg" : "8",
        "cost" : 1100.0
    }, {
        "library" : "gbns",
        "memberType" : "27",
        "userCateg" : "1",
        "cost" : 500.0
    }, {
        "library" : "gbns",
        "memberType" : "28",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "28",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "28",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "28",
        "userCateg" : "7",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "28",
        "userCateg" : "8",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "29",
        "userCateg" : "2",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "29",
        "userCateg" : "3",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "29",
        "userCateg" : "4",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "29",
        "userCateg" : "5",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "29",
        "userCateg" : "7",
        "cost" : 300.0
    }, {
        "library" : "gbns",
        "memberType" : "30",
        "userCateg" : "1",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "30",
        "userCateg" : "2",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "30",
        "userCateg" : "3",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "30",
        "userCateg" : "4",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "30",
        "userCateg" : "5",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "30",
        "userCateg" : "6",
        "cost" : 0.0
    }, {
        "library" : "gbns",
        "memberType" : "30",
        "userCateg" : "7",
        "cost" : 0.0
    } ]
);

db.coders.education.insert(
    [ {
        "library" : "gbns",
        "description" : "Nepoznato"
    }, {
        "library" : "gbns",
        "description" : "Osnovno"
    }, {
        "library" : "gbns",
        "description" : "Srednje"
    }, {
        "library" : "gbns",
        "description" : "Više"
    }, {
        "library" : "gbns",
        "description" : "Visoko"
    } ]
);

db.coders.place.insert(
    [
        {'city':'Novi Sad', 'zip':'21000' }

    ]
);

db.coders.organization.insert(
    [ {
        "library" : "gbns",
        "name" : "Izvršno veće",
        "address" : "Bul. M. Pupina 16",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Karate klub Budo",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad gimn.\"J.J.Zmaj\"3/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Boneco MV",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VŠOV",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Frizerski salon Duo",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PIV",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gradska biblioteka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS-Inžinjering",
        "address" : "21000",
        "city" : "NOvi SAd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Studenski centar",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "apoteka Raj",
        "address" : "branka ćopića 24a",
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša tehnička škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tiac DO",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Šajkaška",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VTŠ",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "gradska,biblioteka",
        "address" : "dunavska1",
        "city" : "Novi,Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gradska biblioteka u Novom Sadu",
        "address" : "Dunavska 1",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gradska biblioteka",
        "address" : "dunavska 1",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Nekxi Grupa",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "gradska biblioteka",
        "address" : "dunavska 1",
        "city" : "novi sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "N.Sad Gradska biblioteka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Institut za srpsku kulturu - Priština",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Viša tehnička škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Elektro mreža Srbije",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ministarstvo finansija",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad Butik LN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom zdravlja",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Radio Novi Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Nepolanta",
        "address" : "Primorska 90",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "JORDAN",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN - elektrotehnika",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad Filoy.fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DOO Dijagonala",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Moto Magazin",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad Muzička šk.Isidor Bajić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad Filoz.fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : "Stevana Musića",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša Poslovna Škola",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "22 oktobat",
        "address" : null,
        "city" : "žABALJ",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIVA AD",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ŠOSO MIlan Petrović",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Globos osiguranje  filijala Jugins",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad Filoz.fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "N.Sad PTT",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : "Stevana Musića 27",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Farmacija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Agencija za privatizaciju",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "A Banka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "albus",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Olimpik plus",
        "address" : null,
        "city" : "Movi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Prirodnomatematički fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Akademija umetnosti",
        "address" : null,
        "city" : "Banjaluka",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vidi Klub",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Farmalogist",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Centar za puteve Vojvodine",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "IVAP Vojvodina",
        "address" : "Bulevar M. Pupina 16",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Radio Sajam",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Novkabel",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Nort Inženjering",
        "address" : null,
        "city" : "Subotica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Butik Sunčica",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PU Novi Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Centar za kulturneu aninaciju",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Skupština Vojvodine",
        "address" : "Platonova bb",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SNP",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakulter",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Albus",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "MUP RSrbije",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tržnica",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Nis Naftagas",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom zdravlja",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "OŠ I.,L.ribar",
        "address" : null,
        "city" : "Sutjeska",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "IZV Vojvodine",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Novi Sad JŽTP",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tehnološki fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Muzička akademija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ERSTE Bank",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija S. Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS INŽENJERING",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N. Sad Klininički centar",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "UZP Sremska Kamenica",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Novkabel",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Akademija umetnosti",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "učiteljski fak.",
        "address" : null,
        "city" : "Sombor",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SZR Bull",
        "address" : null,
        "city" : "Bukovac",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.Muzička škola I.Bajić 3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja škola Pavle Savić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad Novkabel",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Radio Futog",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gugoinspekt",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : "NOvi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gimnazija",
        "address" : null,
        "city" : "Šabac",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija JJ Zmaj",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Muzička škola Isidor Bajoć",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "COMALCO",
        "address" : null,
        "city" : "Noi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "J.J.Zmaj",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Juginspekt",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SZR RICOH",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "SISTEM",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "RSGV Novi Sad",
        "address" : "Kiš Ernea 4",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska banka",
        "address" : null,
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tehnička škola Pavle Savić",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad I.Sekulić IV",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "siz pio sam",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vrtić Veseli vrtić",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Radosno detinjstvo",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski Fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ETŠ \"M. Pupin\" AE1",
        "address" : "Futoški put 17",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : "Novi Sadf",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Ginako",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Viša škola za obrazovanje vaspitača",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "J.J. Zmaj 4/7",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Akademija umetnosti",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Neimar projekt",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "b4b",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad Filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad Sv.Marković 2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Učiteljski fakultet",
        "address" : null,
        "city" : "Sombor",
        "zip" : "25000"
    }, {
        "library" : "gbns",
        "name" : "Fakultet za menadžment Braće Karić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "TIMS",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Novosadsk mlekara",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Motins",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Banka Oportjuniti",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad rafinerija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša poslovna",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "C Market",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "fILOZOFSKI FAK.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Novi Sad Fil. fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS-Rafinerija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SS Svetizar Marković 1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SZR Enterijer-Projekt",
        "address" : "Baranji Karolja 47",
        "city" : "Temerin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filološki fak.",
        "address" : null,
        "city" : "Beograd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "filološki fakultet",
        "address" : null,
        "city" : "Beograd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimn. I. Sekulić 2/5",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ZIG",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "N.Sad 7.april 4/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ADU N. Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Akademija umetnosti",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "AD Neimar",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Geadska biblioteka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Izvršno veće",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VPŠ",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Izvršno veće",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"I: Sekulić\" 4/1",
        "address" : "Vladike Platona 2",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad gimn.\"J.J.Zmaj\"2/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "radosno Detinjstvo",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Radosno detinjstvo",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Rafinerija",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom Zdravlja",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Fepps, Novi Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ŽTP",
        "address" : null,
        "city" : "N. Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "HOME Invest",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Građevinski fak",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Narodna banka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Vasa Stajić VIII",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Koprodukt",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Spomen zbiraka Pavla Beljanskog",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Novisadski sajam",
        "address" : "Hajduk Veljkova 11",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Naftagas",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gradska biblioteka u Novom Sadu",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Narodna Lutrija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS Naftagas",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekulić",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "7 april 2",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SŠ Svetoz.Miletić 4",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tonbola Brazil",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad gimn.\"Sv.Marković\"2/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija\"Svetozar Marković\"4/8",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad gimn.\"J.J.Zmaj\"4/8",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni Fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ginmazija Isidora Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SŠ Pavle Savić 4/13",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Nikol",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "JP Srbijagas",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FAM",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Bačka put",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "O. Š. \"B. Radičević\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "N.Sad FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad MŠ\"I.Bajić\"",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gim.",
        "address" : null,
        "city" : "Sr. Karlovci",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tehnološki fak",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša škola za trenere i menadžere u sportu",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Darkom",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DOO Vajer",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "N.Sad SM\"I.Bajić\"2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Izvršno Veće",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS Naftagas Promet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Apotekarska  Ustanova  Novi Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija \"I. Sekulić\" 4/5",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Ekonomska škola Svetozar Miletić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SŠUP Sr. Kamenica",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "HTŠ \"Pavle Savić\" HT31",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredna škola",
        "address" : null,
        "city" : "Futog",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim \"I. Sekulić\" 4/1",
        "address" : "Vladike Platona 2",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "S.Š\"S. Miletić\"1/7",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad SŠ\"7.April\"LT2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ zarko zrenjanin",
        "address" : null,
        "city" : "Zrenjanin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinska škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VPŠ",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gimn. \"J.J.Zmaj\" I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VPŠ",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Čačak Niskogradnja",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Šajkaška",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad VPŠ",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Institut za rat. i povrtarstvo",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. SEKILIĆ",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija\"I. Sekulić\"1/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "\"Bomiluks\"N.Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad CSR",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom \"Bajić\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimn.\"J.J.Zmaj\"N.Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Univer",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SUP- Novi Sad",
        "address" : "P.Pavla",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PMF Biologija",
        "address" : null,
        "city" : "N.Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Elektrovojvodina",
        "address" : "Bulevar oslobođenja 100",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Elektrovojvodina",
        "address" : "Bul. Oslobođenja 51",
        "city" : "21000 Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "dr.Ličina",
        "address" : "Slovačka 48",
        "city" : "21112 Kisač",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "TV Novi Sad",
        "address" : "Sutjeka 1",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Si partner",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DDOR N.Sad AD",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Klinićki centar",
        "address" : "Hajduk Veljkova 1-9",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gimnazija L. Kostić II",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dizajn",
        "address" : null,
        "city" : "Sr.Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dnevnik",
        "address" : "Bulevar oslobođenja",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "T. Š. Pavle Savić",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Koteksprodukt",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Banja Vrdnik Termal",
        "address" : null,
        "city" : "Vrdnik",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Trćniva",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Narodna banka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Jovan Vukanović 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "T.Š, \"J. Vukanović\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Udruženje Grand",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Institut zazaštitu zdravllja",
        "address" : "Futoška 121",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "ŽTP",
        "address" : null,
        "city" : "Beograd",
        "zip" : "11000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "O. Š. \"Ž. Zrenjanin\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomska škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "JŽTP-Beograd",
        "address" : "Mileve Marić 23",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "NIS - Inžinjering",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Isidora Sekulić",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Elektrovojvodina",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Feniks lek",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NS Plus",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.Š. S. Marković II",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Zapadni univerzitet Temišvar",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Likovna Akademija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filološki fak.",
        "address" : null,
        "city" : "Beograd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "isidora sekulić",
        "address" : null,
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "katastar",
        "address" : null,
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SZTR Dulka",
        "address" : null,
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "TV Novi sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.Š S. Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša škola za vaspitače",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dnevnik",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekotrans",
        "address" : null,
        "city" : "NOvi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "trafika nik",
        "address" : null,
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Color-press",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "jugo san",
        "address" : null,
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Knjigovodstvena agencija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Rfinerija nafte",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom zdravlja Novi Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Klinički centar",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SVETOZAR MARKOVIĆ",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "M: Š. \"7april\" 2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Muzej Vojvodine",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Zvezda kompani",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Panšped",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja mašinska škola I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filološki fakultet",
        "address" : null,
        "city" : "Beograd",
        "zip" : "11000"
    }, {
        "library" : "gbns",
        "name" : "Gimn. J. J. Zmaj III",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Picerija Adrijana",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gimnazija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimn.J.J.Zmaj",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim.\"I. Sekulić\" 3/6",
        "address" : "Vladike Platona",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gim. \"Sv. Marković\" 2/3",
        "address" : "Njegoševa 22",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "NIS Rafinerija nafte",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom zdravlja",
        "address" : "Bulevar cara Lzara 75",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "SUP",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "INstitut za plućne bolesti",
        "address" : null,
        "city" : "Sr. Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija L. Kostić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tehnička škola J. Vukanović",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tehnička škola J. Vukanović",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Svetozar Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"J. J. Zmaj\" 1/4",
        "address" : "Zlatne grede",
        "city" : "Noivi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gim. \"Laza Kostić\" 3/4",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gim. \"Is. Sekulić\" 2/2",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "M. Š \"I. Bajić\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Saobraćajna škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekilić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Novosadska banka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Institut za onkologiju",
        "address" : "Sr. Kamenica",
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"J. J. Zmaj\" 1",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "\"Jovan Vukanović\" V11",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Fakultet za menadžment",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Mašinska škola 3",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "2100"
    }, {
        "library" : "gbns",
        "name" : "Učiteljski",
        "address" : null,
        "city" : "Užice",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "LINK- NS",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Muzej grada Novog Sada",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija L. Kostić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "T.Š. \"Pavle Savić\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"I. Sekulić\" 4/3",
        "address" : "Vladike Visariona 2",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Opštinski sud",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Centar za socijalni rad",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimn. I. Sekulić IV",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekulić 3/3",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Srednja ekonomska škola S. Miletić II",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SUP",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "BK menadžment",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š. \"J. J. Zmaj\"",
        "address" : null,
        "city" : "Sremska Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "M. Š. \"Isidor Bajić\" 1/2",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "S. Š. B. Šuput I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Kafe Tref",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Dans DO",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "\"Sv. Miletić\" 1",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"I: Sekulić\" 4",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sredna mašinska škola N. Sad I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"I. Sekulić\" 1/3",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "T. Š. \"Pavle Savić\" 1/14",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "BK Univerzitet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filološki fakultet-komparativna književnost",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S. Š. E. S. Mihajlo Pupin T31",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Agrokop",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša Pedagoška",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet- Engleski jezik i književnost",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Medicinski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. Sekulić IV",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Poreska uprava",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sv. Miletić 2/15",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Medicinska škola III",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. Sekulić III",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja muzička škola I. Bajić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "TIMS",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"Isidora Sekulić\" 1/2",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "RT Panonija",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "STR Oftalnica",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Tehnološki fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VPŠ",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "RTNS",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Klinički Centar",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Koteks produkt",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Mizička škola Isidor Bajić",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekulić, 3/5 razred",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Vodovod i grejanje",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gimnazija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Radosno detinjstvo",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom zdravlja Novi sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Put Invest",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VPŠ",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ 23.oktobar",
        "address" : null,
        "city" : "Sr.Karlovci",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "TIMS",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozoffski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "JT Gradnja",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gimnazija \"I. Sekulić\" 1/4",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "DIF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Prva Vojvošanska brigada",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Institut",
        "address" : null,
        "city" : "Sr.Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Trenerska Škola za košarku",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \" Laza Kostić\" 1/1",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Akademija Umetnosti",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Beograd - Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OD \"SHOWROOM\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Slavija COP",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS- AD Petrol",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimna.Isidora Sekulić 3r",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Laza Kostić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja muzička škola Isidor Bajić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "REVI",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Novosadsko pozorište",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska banka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Južna Bačka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Institut",
        "address" : null,
        "city" : "Sremska Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \" Laza Kostić\" 1",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Svetizar marković 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "BK",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N S Plakat",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "B K",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Katlovačka gimnazija",
        "address" : null,
        "city" : "Sremski Karlovci",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Srednja škola P. Savić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DIF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Rednja škola Svetozar Miletić",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Tomi Hiltiger",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Uprava Grada Novog Sada",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VPŠ",
        "address" : "Novi Sad",
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filološki fakultet",
        "address" : null,
        "city" : "Beograd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VPŠ",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "\"Bogdan Šuput\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Zlatibor Invest",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija J.J. Zmaj 1/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Klinički centar Novi Sad, Uprava",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredna škola sa domom učenika 1",
        "address" : null,
        "city" : "Futog",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. \"Đ.Jakšić\"",
        "address" : null,
        "city" : "Zrenjanin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojska",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS-Naftagas promet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad, Gold auto",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SPC",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Muzička akademija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SŠ\"Pavle Aabić\"",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"J.J. Zmaj\" 1/2",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "E.Š.\"Sv. Miletić\"  1/5",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "M.Š. 7april 1",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gim. \" Sv. Marković\" 4/7",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Taksi služba",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PJP vojvodine",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "STR K in",
        "address" : null,
        "city" : "Novi SAD",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Flozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "SS Jovan Vukanović 3",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FAM",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "VPŠ",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "institut za plućne bolesti",
        "address" : null,
        "city" : "Sr.Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SŠ\"J.Vukanović\" II",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Fakultet za menadžment i bankarstvo",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tehn.fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Enterijer Janković",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PIV",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gimnazija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : "Novi Sad",
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SNP",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojna ustanova trandžament",
        "address" : null,
        "city" : "Petrovaradin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SNP",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tehnološki fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet, Smer mađarski jezik i književnost",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Rafinerija",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Izvršno veće",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SS Laza Kostoć 3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "TV novi Sad",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Delta M Beograd",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredna škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DOO Šark",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Ministarstvo poljoprivrede",
        "address" : null,
        "city" : "Novi saad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VPŠ",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska banka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "I V  APV",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SNP",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska banka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tehnička škola Pavle Savić",
        "address" : "Novi Sad",
        "city" : "21000",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"I. Sekulić\" 1/5",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PIOZ",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Novosadska banka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VPŠ",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska banka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FM",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.Š.C. Jovan Jovanović Zmaj-3/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Naftagas Promet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : "Zorana Đinđića 1",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Svetozar marković 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Narodno pozorište",
        "address" : null,
        "city" : "Zrenjanin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SUP",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Jan Kolar",
        "address" : null,
        "city" : "Bački Petrovac",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska banka",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Muzička Škola Isidor Bajić",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Joli travel",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Laza Kostić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Mašinska škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Maksi Market",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Voće DO",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Klinički centar Hematologija",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "taksi Delta",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Rafinerija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fak.",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vrtić Veseli vrtić",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultwet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "rafinerija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF-informatika",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "JP Apolo",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "KD AM Gradnja",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SŠ Pinki",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filožofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Futura",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "SZT Mrvica",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "INNOVA",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Frizerska radionica",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "JP GIC Apolo",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Signal",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša poslovna",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Stomatološka ordinacija Estetika",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša Poslovna",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Oš Veljko Petrović",
        "address" : null,
        "city" : "Begeč",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sportska klad. Mocart",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija S. Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "MB Rodić",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Erste banka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "picerija Aleksandar",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ZOX Inženjering",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ZGOP",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VP 5003",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gimnazija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "JGSP",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Unimezt",
        "address" : null,
        "city" : "Kać",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fak.",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTn",
        "address" : null,
        "city" : "Novoi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SNP",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VPŠ",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska banka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Dom za decu i omladinu",
        "address" : null,
        "city" : "Veternik",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Giim. \"L.Kostić\" 2/4",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska banka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "SNP",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "\"Bogdan Šuput\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SŠ\"Sv.Miletić\"",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska banka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Dom zdravlja",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SNP",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ERSTE banka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Toplana",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska banka",
        "address" : "Trg Slobode 7",
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Minakva",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Spens",
        "address" : "Sutjeska 2",
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poreska uorava",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Milenium Agencija za nekretnine",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poreska Urava",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Izvršno veće",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Erste banka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pinki",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom Zdravlja",
        "address" : null,
        "city" : "Inđija",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom zdravlja Novi Sad",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Klinički centar",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija \"J. J. Zmaj\" 2/5",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š.\"Ž. Zrenjanin\"",
        "address" : null,
        "city" : "Gospođinci",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SS Sindikata Vojvodine",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VP 8909 Novi Sad",
        "address" : null,
        "city" : "Noovi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljpoprivredna škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "JKP Tržnica",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Biblioteka Filozofskog Fak.",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"S. Marković\" 2/3",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gim. \"Laza Kostić\" 2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Do Art Ind",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Bogdan Šuput 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Radosno detinjstvo",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim \"J. J. Zmaj\" 4/7",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"J. J. Zmaj\" 1/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Mađarso",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gradska uprava",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"Laza Kostić\"",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Svetozar Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom zdravlja",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Inioteka",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Bibago",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "TV Novi Sad",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša pedagoška škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom Zdravlja",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "M Rodić",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Dositej Obradović",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Auto komision MG",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojska Srbije",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Akademija umetnosti",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Akademija umetnosti",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "BK",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTn",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Evropski univerzitet",
        "address" : null,
        "city" : "Beograd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SZR Arsenov",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "UNIhemkom",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "E:Š.\"Sv. Miletić\" 1",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Pedagoška akademija",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Likovna akademija",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dečja bolnica",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Jožef Atila",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Super Taxi",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Akademija umetnosti",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dečija bolnica",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VŠP",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VŠV",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Magretron",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pedagoška akademija",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIneks",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "TV Novi Sad",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojska Srbije",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Žarko Zrenjanin",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "izvršno veće",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Panonska banka",
        "address" : null,
        "city" : "NOvi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sipekx",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojni okrug",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DeDIS",
        "address" : null,
        "city" : "Despotovac",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Perinatal",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Perinatal",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Masmi",
        "address" : null,
        "city" : "Beograd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Š Milan Petrović",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojska Srbije",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Zavod za zaštitu sp. kulture grada N.S",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni Fak.",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Miraro Beograd",
        "address" : null,
        "city" : "Beograd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "revnost",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Nivi Sad Gas",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Novi Sad gas",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "TV Panonija",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Radosno detinjstvo",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojna Bolnica",
        "address" : null,
        "city" : "Petrovaradin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska Bnka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "AB-Honda",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Mizej savremene umetnosti Vojvodine",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Apoteka Anđelija",
        "address" : null,
        "city" : "Sr.karlovci",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Eskspres gas DO",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sud",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Morbideli",
        "address" : null,
        "city" : "Novi sd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Lim-IT",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "GP NOvotehna",
        "address" : null,
        "city" : "Novi SAd",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Zavod za urbanizam",
        "address" : "Bul. Cara Lazara 3",
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojska Srbije",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DO Znoni",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pokrajinski organi uprave",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pokrajinski organi uprave",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "MIX DOO",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ZZZZ ŽTP-A Beograd",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Televizija Novi Sad",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "NIS AD Petrol",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Foto oko",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "MUP-Žandarmerija",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Taksi Grand",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Izvršno veće VOjvodine",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DDOR",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Muzička škola Josip Slavenski",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Alpha bank",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi SAd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "RTV",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Eko pumpa",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FAkultet za pravne i poslovne studije",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FAkultet za pravne i poslovne studije",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "NIS Petrol",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "RUV RTV",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "MUP Srbije",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "MIREX",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Naftagas",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "tehnička škola Pavle Savić",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Marković Kompani",
        "address" : null,
        "city" : "Subotica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ADF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pokrainski organi uprave",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Picerija Bolgna",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Carina Novi sSd",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pedagoška akademija",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Žarko Zrenjanin",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : "dr Zorana Djindjića",
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Energosoft",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "STR Komisiom PJB",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Mizej Vojvodine",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Institut",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Polj. fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Rafinerija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ZIG",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Elekto partner",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Jan Kolar",
        "address" : null,
        "city" : "Bački Petrovac",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ERSTE Banka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Novi sad gas",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DP Novi Sad Gas",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi SAd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : "NOvi SAd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "bUTIL dŽESIKA",
        "address" : null,
        "city" : "nOVI sAD",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Magan-YU",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "STRk PANNO Prvi",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Unitek Frizer centar",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sigma Inženjering",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ADIŽES",
        "address" : null,
        "city" : "NOvi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SS Svetozar Miletić",
        "address" : "Novi sad",
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gradska čistoća",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Humanitarna organizacija \"Duga\"",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ŽTP",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Privatni preduzetnik",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "AD Instalacije",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vode Vojvodine",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Apoteka Novi Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gerontološki centar",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS",
        "address" : null,
        "city" : "NOvi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomska",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poreska uprava",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Klinički Centar Vojvodine",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SS Pavle Savić",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Institut za javno zdravlje Vojvodine",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sanitarija",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Radio AS",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fal.",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIs Petrol",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Taxi SOS",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Taxi Novosađanin",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "RTB",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "RTB",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Vasa Stajić\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ALPINA",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "MRodić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sud",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SIZ Štit",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gim.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FPM",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Opštinsko tužilaštvo",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FAM",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Bakar Komerc",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Banat Promet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sinteza",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ I.L.Ribar",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Riznica",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠJan Đdžajak",
        "address" : null,
        "city" : "B.Petrovac",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Galens",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pobeda",
        "address" : null,
        "city" : "Petrovaradin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "RUV",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednjoškolski dom Nikolajevska",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tehnološki fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Agrovojvodina tehnohemija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Albus",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Radosnica",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Danas",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SZR Živković Laura",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Kontrola prihoda",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Izvršno veće",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Elektrovojvodina",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Akademija umetnosti",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Institut u Kamenici",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Komision Kona",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Petefi Šandor",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "MG-KIMBERLI KLARK",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Institut za plućne bolesti",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Šeherezada poslastičarnica",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "HIPO Banka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ta Šmizla",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Institut za onkologiju",
        "address" : null,
        "city" : "Sremska Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "sanitarija",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "pokrajinski sekretarijat za finansije",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "JP Srbija Gas",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DUENT DOO",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Radosno detinjstvo",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sfinga",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sfinga",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvodina šped",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Nektar",
        "address" : null,
        "city" : "Bačka Palanka",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredna škola Futog",
        "address" : null,
        "city" : "Futog",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DOO 24 oktobar",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Azzaro Parallel",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Zeleznice Srbije",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dimanika DOO",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Štamparija Partner druk",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Humanitarna organitacija ARCI",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "EN Stajčić",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Naftagas",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Veterinarska stanica",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "AD Revnost",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Studenski centar Novi sad",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "La Farž",
        "address" : null,
        "city" : "Beočin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Play off",
        "address" : "Strumička 16",
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Delta Maxi",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Centar za puteve Vojvodine",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Frizerski salom NS",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "RGZ",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sportska kladionica Mocart",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Sunoko",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Veterinarski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "IPLB",
        "address" : null,
        "city" : "Sr.Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Narodna banka Srbije",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pozorište Mladih",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS Naftagas",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvodina šume",
        "address" : null,
        "city" : "Petrovaradin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvodina šume",
        "address" : null,
        "city" : "Petrovaradin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Studentski dom zravlja",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "MULTI VAC",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Aktiv plus",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Visoka pedagoška",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pekara KUM",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Motins",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "CMC Turs",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet, Novi Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom Zdravlja Novi Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NUTRI VIT",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvođanska banka",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pežo servis",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Muzej Vojvodine",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Elektrovojvodina",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Telekom",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "S. Š. I. Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Novosadska banka",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Jovan Dučić",
        "address" : "Petrovaradin",
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Srednja medicinska škola 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gimnazija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Reifeisenbank",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet-",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekulić II",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poreska uprva",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofsski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Institut za PLB",
        "address" : null,
        "city" : "Sr.Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.Š. S. Miletić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š. D. Radović V",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "RTV Novi Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Železnice Srbije",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SŠ Ekonomska 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja ekonomska škola S. Miletić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Mašinska škola",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gimn. 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ZAvo za ZZR",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Dom Zdravlja Novi sad",
        "address" : null,
        "city" : "Sr.Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.Š. Jovan Vukanović",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Futoška 65",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Jovan Jovanović Zmaj-1/7",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tehnološki fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "BK",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredna škola",
        "address" : null,
        "city" : "Futog",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Fakultet za menadžment",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.Š. S. Miletić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Jožef Atila-5/f",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja škola Jovan Vukanović",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"J.J. Zmaj\" 1/5",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Veljko Petrović",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom zdravlja",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "S. Š. S. Miletić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Naftagas promet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. \"S.Marković Toza\" 7",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.Š. S. Miletić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Svetozar Toza Marković-7/6",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimn. \"Svetozar Marković\" 3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja poljoprivrdna škola Futog",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim \"Sv. Marković\" 3/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. 20. Oktobar",
        "address" : null,
        "city" : "Bačka Palank",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Petefi Šandor-8/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SŠ Bogdan Šuput",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : "Novi sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FAM",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "T. Š. \"Jovan Vukanović\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "J.J.Zmaj 2",
        "address" : null,
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimn. S. Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša medicinska škola",
        "address" : null,
        "city" : "Zemun",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gim. \" L. Kostić\" 1/5",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. Sekulić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"Sv. Marković\" 1/4",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fak.",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Mileva Marić Ekonomsa",
        "address" : null,
        "city" : "Titel",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Svetozar Marković Toza, 7. razred",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "N.Sad Gimnazija S. Marković I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ I.L.Ribar 6/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Fakultet za usužni biznis",
        "address" : null,
        "city" : "Sremska Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Sv. Maarković 2",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Građevinska 1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja škola S. Miletić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja škola I. Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija S. Marković I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.E. \"Sv. Miletić\" 1/12",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Isidora Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Svetozar Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Mašinska škola",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Srednja muzička škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja škola S. Miletić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša pedagoška škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gimnazija I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. Sekulić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "SS Pavle Savić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Svetozar Marković, 2. razred",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Vasa Stajić,4/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Svetozar Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Đura Daničić-8/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Laza Kostić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "23  8/3",
        "address" : null,
        "city" : "Sremski Karlovci",
        "zip" : "21250"
    }, {
        "library" : "gbns",
        "name" : "S.Š.E. S. M. Pupin",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gim.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija:J.J.Zmaj",
        "address" : "21000",
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "aprilM. Š.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Jovan Dučić, 7. razred",
        "address" : "Petrovaradin",
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ \"Đura Daničić\" 7",
        "address" : "21000",
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "GIm.\"S. Marković\" 1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. Sekulić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S:M. 7april",
        "address" : null,
        "city" : "Novi  Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "gim.Isidora Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Ivo Lola Ribar-3/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja medicinska škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ \"Ivo Lola Ribar\",3/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "gim.Isidora Sekulić",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekulić 1. razred",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gim. \"Sv. Marković\" 1/2",
        "address" : "Njegoševa 22",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gimnazija J. J. Zmaj",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimn. \"J.J.Zmaj\" 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.S.Š. Medicinska- 7. april",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ S.Marinković 4/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Đura Daničić-1/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š. \"J. Dučić\"1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ K.Trifković 7/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredna škola",
        "address" : null,
        "city" : "Futog",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Petefi Šandor-5/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Ivo Lola Ribar-3/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"J. J. Zmaj\" 2/4",
        "address" : "Zlatne  grede 4",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Petefi Šandor\",1.razr.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.Š. I. Sekulić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Đura Daničić7/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "J. Vukaanović GO 11",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Sv.Marković Toza\" 7/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija \"S. Marković\" 1/7",
        "address" : "Njegoševa  22",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gim.\"Sv.Markovića\" 1",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Dositej Obradović\", 3. razred",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Ivo Lola Ribar-8/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "E:Š. \"Sv. Miletić\" 1",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Srednja elektrotehnička škola M. Pupin",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Svetozar Marković Toza, 8. razred",
        "address" : "Janka Čmelika",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "OŠ Đura Daničić 7 razred",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Srednja škola S. Miletić I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "svetozar marković",
        "address" : null,
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š. \" Vasa Stajić\"8/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"N.Tesla\"8/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Ivan Gundulić-8/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Đura Daničić\"4/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Kosta Trifković- 8/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š.Kosta Trifković-1/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gim.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Svetozar Miletić 1",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Jovan Dučić-8/6",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija J.J. Zmaj I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Oš \"S. Marković Toza\" 4/4",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š:\"Sonja Marinković\"1/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š:\"Sonja Marinković\"4/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ P.Šandor",
        "address" : null,
        "city" : "N.Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "OŠ Ž.Zrenjanin",
        "address" : null,
        "city" : "N.Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "OŠ P Šandor 7/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. B. Radičević I",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ \"J. Jovanović Zmaj\" 7/3",
        "address" : null,
        "city" : "Sr. Kamenica",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ \"Đura Jakšić\" 8/1",
        "address" : null,
        "city" : "Kać",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Predškolsko",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š. 22 avgust",
        "address" : null,
        "city" : "Bukovac",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š prva vojv. brigada",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "O.Š.Jovan Dučić 6/6",
        "address" : null,
        "city" : "Petrovaradin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š.\"Branko Radičević\"",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "đorđe natošević 3",
        "address" : null,
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "I. L. Rubar II",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Đura Daničić -3/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Ivo Lola Ribar-8/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. ĐURA DANIČIĆ- 5/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Đorđe Natošević-6/d",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š.\"S. M. Toza\" 3/5",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "OŠ Ivo Lola Ribar, 3. razred",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "O. Š. \"Ivan Gundulić\"6/3",
        "address" : null,
        "city" : "Čenej",
        "zip" : "21233"
    }, {
        "library" : "gbns",
        "name" : "O. Š. Đ. Natošević IV",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š. Đ. Natošević II",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š. Đ. Natošević VII",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Aleksa Šantić-7",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Sonja Marinkovič-4/5",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Đura Daničić-2/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Svetozar Marković Toza 7/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Jovan Popović-4/5",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Branko Radičević-5/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Petefi Šandor 6/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Jovan Popović-7/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Veljko Vlahović-7",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Miloš Crnjanski-8/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. \"Kosta Trifković\" 7/1",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "O.Š. \"Jovann Popović\" 5/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Ivo Lola Ribar-7/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Đura Daničić-1/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Prva vovjođanska brigada        VI 5",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ivo Lola Ribar,3.razr.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ivan Gundulić, I2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Đura Daničić- 4/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Đorđe Natošević,4/d",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Đorđe Natošević,I/v",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ivo Lola Ribar, 8/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Đura Daničić,2/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dušan Radović,3/9",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Jovan Jovanović Zmaj-6/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Prva vojvođanska brigada\",3/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Mihajlo Pupin\",4/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Svetozar Marković Toza\",7/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Kosta Trifković\",5/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Jovan Popović\",6/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Prva vojvođanska brigada\",3/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"ivo Andrić\",6/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Ivo Andrić\",5/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Jožef Atila\",6/G",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Petefi Šandor-2/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ \"Dositej Obradović\", 6. razred",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "O.Š. Prva vojvođanska brigada VII",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Petefi Šandor-6/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Dositej Obradović-3/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Đorđe Natošević-3/d",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Đurđe Natošević3/d",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Jožef Atila-6/c",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. IBC-8",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Toza Marković-2/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š.\"J. Grčić Milenko\" 1",
        "address" : null,
        "city" : "Čerević",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Đura Daničić-5/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "18 meseci",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Vuk Karadžić-7/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Dušan Radović-4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Svetozar Marković Toza-6/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Svetozar Marković Toza -3/5",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "vrtić-7",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Petefi Šandor-4/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Nikola Tesla I3",
        "address" : null,
        "city" : "Novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Kosta Trifković-8/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Jovan Dučić-7/5",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O. Š. Jožef Atila",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Vuk Karadžić-6/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Svetozar Marković Toza-2/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Miloš Crnjanski-8/3",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Petefi Šandor-7/1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Petefi Šandor-6/5",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Petefi Šandor-6/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Žarko Zrenjanin-4/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Prva vojvođ. brigada, 6 razred",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Đura Daničić\",3/2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dečije selo",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Prdškolska ustanova",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Đura Daničić\", IV razred",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Petefi Šandor\", V raz.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ivo Lola Ribar, IV razred",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Jožef Atila\", 3. razred",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Mihailo Pupin\", 3. razred",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"Mihailo Pupin\", 3. razred",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "oš miloš crnjanski 4/2",
        "address" : null,
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "oš petefi šandor",
        "address" : null,
        "city" : "novi sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"I. L. Ribar\", 7. razred",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"I. L. Ribar\", 7. razred",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "GBNS",
        "address" : "Dunavska 1",
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "GBNS",
        "address" : "Dunavska 1",
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Ekonomska škola",
        "address" : null,
        "city" : "Novi SAd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ\"22. avgust\", I razred",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Slovačko Vojvođansko pozorište",
        "address" : null,
        "city" : "Kulpin",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Vojvodina Komision",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "7 april1",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja škola Svetozar Miletić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Svetozar Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Duvanpromet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "O.Š. Miroslav Antić VIII",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Laza Kostić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja škola \"M.Pupin\" 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. D. Radović VIII",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Dušan Radović",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "OŠ Svetozar Marković Toza 7",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Prvni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija J.J. Zmaj",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet, smer Žurnalistika",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Visoka poslovna škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Futog, Poljoprivredan škola 2",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN, smer Saobraćaj",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja ekonomska škola \"S.Miletić\" 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja poljoprivredna škola u Topoli 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša tehnička škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "N.Sad, PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinki fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Fabus",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja poljoprivredna škola sa domom učenika 4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Fakultet za fizičku kulturu",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VTŠ",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DIF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša tehnička škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija L. Kostić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DIF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF, smer Fizika",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. D. Radović V",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija S. Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fak.",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "marketing i menadžment koledž",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija S. Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DIF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Jovan Jovanović Zmaj",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filzofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija S. Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija L. Kostić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša škola za menadžment",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gimnazija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Laza Kostić IV",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "srednja škola Svetozar Miletić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "VPŠ - Novi Sad",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja mašinska škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Akademija lepih umetnosti",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filološki fakultet",
        "address" : null,
        "city" : "Beograd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Teološki fakultet",
        "address" : null,
        "city" : "Beograd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Megatrend Univerzitet",
        "address" : null,
        "city" : "Beograd",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Tehnološki fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DIF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Narodna Univerzitetska Biblioteka Skoplje",
        "address" : null,
        "city" : "Skoplje",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "RTV",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija S. Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija S. Marković",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Visoka poslovna škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "FTN",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Pravni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Medicinski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Visoka poslovna škola",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija Isidora Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Srednja saobraćajna škola",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Visoka poslovna škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ekonomski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Meteor",
        "address" : null,
        "city" : "Veternik",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Izvršno veće",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gimnazija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Karlovačka gimnazija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "STR.Moja čarolija",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet, Smer Arheologija",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Viša pkola za obrazovanje vaspitača",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S.Š. S. Miletić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Poljoprivredni fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "PMF",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "S Saobraćajna",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija I. Sekulić",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "NIS Naftagas",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Filozofski fakultet",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gimnazija J.J. Zmaj",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. Vasa Stajić-7/4",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "DIF",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "Gin \"J.J. Zmaj\" 1/6",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "S.S.Š. Jovan Vukanović",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Ing Projekt",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Gim. \"J. J. Zmaj\"",
        "address" : null,
        "city" : "Novi Sad",
        "zip" : "21000"
    }, {
        "library" : "gbns",
        "name" : "O.Š. Žarko Zrenjanin",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Dom zdravlja Liman",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "Saobraćajna škola",
        "address" : null,
        "city" : null,
        "zip" : null
    }, {
        "library" : "gbns",
        "name" : "O.Š. S. Sava 4/3",
        "address" : null,
        "city" : null,
        "zip" : null
    } ]
);

db.coders.language.insert(
    [ {
        "library" : "gbns",
        "description" : "Nepoznat jezik"
    }, {
        "library" : "gbns",
        "description" : "Srpski - ćirilica"
    }, {
        "library" : "gbns",
        "description" : "Srpski - latinica"
    }, {
        "library" : "gbns",
        "description" : "Mađarski"
    }, {
        "library" : "gbns",
        "description" : "Rumunski"
    }, {
        "library" : "gbns",
        "description" : "Slovački"
    }, {
        "library" : "gbns",
        "description" : "Ruski"
    } ]
);

db.coders.circ_location.insert([
    {
        'library': "gbns_com",
        'location': "01",
        'description': "Some description",
        'lastUserId': 1
    },
    {
        'library': "gbns_com",
        'location': "02",
        'description': "Some description2",
        'lastUserId': 2
    }
]);

db.coders.corporate_member.insert(
    [ {
        "library" : "gbns",
        "userId" : "0",
        "instName" : "x",
        "signDate" :ISODate("2002-12-15T23:00:00.000Z"),
        "address" : "x",
        "city" : "x",
        "zip" : 123,
        "phone" : null,
        "email" : null,
        "fax" : null,
        "secAddress" : null,
        "secZip" : 0,
        "secCity" : null,
        "secPhone" : null,
        "contFirstName" : null,
        "contLastName" : null,
        "contEmail" : null
    }, {
        "library" : "gbns",
        "userId" : "23000010609",
        "instName" : "TV  studio Bečkerek",
        "signDate" : ISODate("2002-12-15T23:00:00.000Z"),
        "address" : "Bul. M.Pupina 25",
        "city" : "Novi Sad",
        "zip" : 21000,
        "phone" : "021/4722-292",
        "email" : "null",
        "fax" : "null",
        "secAddress" : "null",
        "secZip" : 0,
        "secCity" : "null",
        "secPhone" : "null",
        "contFirstName" : "Zoran",
        "contLastName" : "Radovanov",
        "contEmail" : "null"
    }, {
        "library" : "gbns",
        "userId" : "01000049647",
        "instName" : "Isidora Sekulić",
        "signDate" : ISODate("2002-12-15T23:00:00.000Z"),
        "address" : "Lončarska 2",
        "city" : "",
        "zip" : 0,
        "phone" : "060-333-0213",
        "email" : "",
        "fax" : "",
        "secAddress" : "",
        "secZip" : 0,
        "secCity" : "",
        "secPhone" : "",
        "contFirstName" : "Đulija",
        "contLastName" : "Danilović",
        "contEmail" : ""
    } ]
);