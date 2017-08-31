//mongeez formatted javascript


//changeset petar:ChangeSet-coders

db.coders.acquisition.insert(
    [ {
        "library" : "gbns",
        "coder_id" : "a",
        "description" : "Pokrajina (otkup)"
    }, {
        "library" : "gbns",
        "coder_id" : "b",
        "description" : "Razmena"
    }, {
        "library" : "gbns",
        "coder_id" : "d",
        "description" : "Obavezni primerak"
    }, {
        "library" : "gbns",
        "coder_id" : "e",
        "description" : "Zatečeni (stari) fond"
    }, {
        "library" : "gbns",
        "coder_id" : "k",
        "description" : "Kupovina"
    }, {
        "library" : "gbns",
        "coder_id" : "o",
        "description" : "Otkup"
    }, {
        "library" : "gbns",
        "coder_id" : "p",
        "description" : "Poklon"
    }, {
        "library" : "gbns",
        "coder_id" : "s",
        "description" : "Sopstvena izdanja"
    } ]
);
db.coders.accessionReg.insert(
    [ {
        "library" : "gbns",
        "coder_id" : "00",
        "description" : "monografske publikacije"
    }, {
        "library" : "gbns",
        "coder_id" : "01",
        "description" : "serijske publikacije"
    }, {
        "library" : "gbns",
        "coder_id" : "02",
        "description" : "Razglednice"
    }, {
        "library" : "gbns",
        "coder_id" : "11",
        "description" : "doktorske disertacije"
    }, {
        "library" : "gbns",
        "coder_id" : "23",
        "description" : "Serijske, citaonica"
    }, {
        "library" : "gbns",
        "coder_id" : "31",
        "description" : "Zavičajna zbirka, Prvi format"
    }, {
        "library" : "gbns",
        "coder_id" : "32",
        "description" : "zavičajna zbirka, drugi format (sigla 31)"
    }, {
        "library" : "gbns",
        "coder_id" : "33",
        "description" : "zavičajna zbirka, treći format (sigla 31)"
    }, {
        "library" : "gbns",
        "coder_id" : "34",
        "description" : "zavičajna zbirka, rariteti prvi format (sigla 31)"
    }, {
        "library" : "gbns",
        "coder_id" : "35",
        "description" : "zavičajna zbirka, rariteti drugi format (sigla 31)"
    }, {
        "library" : "gbns",
        "coder_id" : "36",
        "description" : "zavičajna zbirka, rariteti treći format (sigla 31)"
    }, {
        "library" : "gbns",
        "coder_id" : "37",
        "description" : "nepoznato"
    }, {
        "library" : "gbns",
        "coder_id" : "38",
        "description" : "zavič. zbirka stara-raritet do 1868 format I (sigla 31)"
    }, {
        "library" : "gbns",
        "coder_id" : "39",
        "description" : "zavič. zbirka stara-raritet do 1868 format II (sigla 31)"
    }, {
        "library" : "gbns",
        "coder_id" : "40",
        "description" : "zavič. zbirka stara-raritet do 1868 III format (sigla 31)"
    }, {
        "library" : "gbns",
        "coder_id" : "41",
        "description" : "zavič. zbirka stara-rariteti do 1868 format IV sigla (31)"
    }, {
        "library" : "gbns",
        "coder_id" : "44",
        "description" : "Rum. knj. Nikita Stanesku"
    }, {
        "library" : "gbns",
        "coder_id" : "91",
        "description" : "nepoznato"
    }, {
        "library" : "gbns",
        "coder_id" : "92",
        "description" : "nepoznato"
    }, {
        "library" : "gbns",
        "coder_id" : "99",
        "description" : "nepoznato"
    } ]
);
db.coders.availability.insert(
    [ {
        "library" : "gbns",
        "coder_id" : "1",
        "description" : "Vremenski ograničena dostupnost - do 7 dana"
    }, {
        "library" : "gbns",
        "coder_id" : "2",
        "description" : "Vremenski ograničena dostupnost - 7 dana"
    }, {
        "library" : "gbns",
        "coder_id" : "3",
        "description" : "Vremenski ograničena dostupnost - 14 dana"
    }, {
        "library" : "gbns",
        "coder_id" : "4",
        "description" : "Delimična dostupnost - čitaonica"
    }, {
        "library" : "gbns",
        "coder_id" : "5",
        "description" : "Delimična dostupnost - sa dozvolom autora"
    }, {
        "library" : "gbns",
        "coder_id" : "6",
        "description" : "Sadržaj dokumenta nedostupan (poseban tretman)"
    }, {
        "library" : "gbns",
        "coder_id" : "7",
        "description" : "Potpuna nedostupnost (arhivski primerak ...)"
    } ]
);

db.coders.binding.insert(
    [ {
        "library" : "gbns",
        "coder_id" : "a",
        "description" : "Bibliofilski povez"
    }, {
        "library" : "gbns",
        "coder_id" : "b",
        "description" : "Broširano (tvrde korice, meke korice)"
    }, {
        "library" : "gbns",
        "coder_id" : "c",
        "description" : "Fascikl povez"
    }, {
        "library" : "gbns",
        "coder_id" : "d",
        "description" : "Italijanski povez"
    }, {
        "library" : "gbns",
        "coder_id" : "f",
        "description" : "Platno"
    }, {
        "library" : "gbns",
        "coder_id" : "g",
        "description" : "Poluplatno"
    }, {
        "library" : "gbns",
        "coder_id" : "h",
        "description" : "Polukoža"
    }, {
        "library" : "gbns",
        "coder_id" : "i",
        "description" : "Spiralni povez (npr. kalendar)"
    }, {
        "library" : "gbns",
        "coder_id" : "k",
        "description" : "Veštačka koža"
    }, {
        "library" : "gbns",
        "coder_id" : "l",
        "description" : "Ostali povezi (nove vrste, nenaved. pov, moderni)"
    }, {
        "library" : "gbns",
        "coder_id" : "m",
        "description" : "Meki povez"
    }, {
        "library" : "gbns",
        "coder_id" : "t",
        "description" : "Tvrdi povez"
    } ]
);

db.coders.format.insert(
    [ {
        "library" : "gbns",
        "coder_id" : "AI",
        "description" : "Format AI – Stare knjige do 1868, I format"
    }, {
        "library" : "gbns",
        "coder_id" : "AII",
        "description" : "Format AII - Stare knjige do 1868, II format"
    }, {
        "library" : "gbns",
        "coder_id" : "AIII",
        "description" : "Format AIII - Stare knjige do 1868, III format"
    }, {
        "library" : "gbns",
        "coder_id" : "AIV",
        "description" : "Format AIV - Stare knjige do 1868, IV format"
    }, {
        "library" : "gbns",
        "coder_id" : "Ac",
        "description" : "Album crteža"
    }, {
        "library" : "gbns",
        "coder_id" : "Af",
        "description" : "Album fotografija"
    }, {
        "library" : "gbns",
        "coder_id" : "Ar",
        "description" : "Album razglednica"
    }, {
        "library" : "gbns",
        "coder_id" : "BNI",
        "description" : "Format BNI – Poklon Boška Novakovića I format"
    }, {
        "library" : "gbns",
        "coder_id" : "BNII",
        "description" : "Format BNII - Poklon Boška Novakovića II format"
    }, {
        "library" : "gbns",
        "coder_id" : "BNIII",
        "description" : "Format BNIII - Poklon Boška Novakovića III format"
    }, {
        "library" : "gbns",
        "coder_id" : "BNIV",
        "description" : "Format BNIV - Poklon Boška Novakovića IV format"
    }, {
        "library" : "gbns",
        "coder_id" : "DMI",
        "description" : "Format DMI"
    }, {
        "library" : "gbns",
        "coder_id" : "DMII",
        "description" : "Format DMII"
    }, {
        "library" : "gbns",
        "coder_id" : "DMIII",
        "description" : "Format DMIII"
    }, {
        "library" : "gbns",
        "coder_id" : "DMIV",
        "description" : "Format DMIV"
    }, {
        "library" : "gbns",
        "coder_id" : "Dk",
        "description" : "Pozivnice, čestitke, programi, flajeri"
    }, {
        "library" : "gbns",
        "coder_id" : "FgI",
        "description" : "fotografija I format"
    }, {
        "library" : "gbns",
        "coder_id" : "FgII",
        "description" : "fotografija II format"
    }, {
        "library" : "gbns",
        "coder_id" : "FgIII",
        "description" : "fotografija III format"
    }, {
        "library" : "gbns",
        "coder_id" : "FgIV",
        "description" : "fotografija IV format"
    }, {
        "library" : "gbns",
        "coder_id" : "FgV",
        "description" : "fotografija V format"
    }, {
        "library" : "gbns",
        "coder_id" : "GkI",
        "description" : "kartografska građa I format"
    }, {
        "library" : "gbns",
        "coder_id" : "GkII",
        "description" : "kartografska građa II format"
    }, {
        "library" : "gbns",
        "coder_id" : "GkIII",
        "description" : "kartografska građa III format"
    }, {
        "library" : "gbns",
        "coder_id" : "GkIV",
        "description" : "kartografska građa IV format"
    }, {
        "library" : "gbns",
        "coder_id" : "GkV",
        "description" : "kartografska građa V format"
    }, {
        "library" : "gbns",
        "coder_id" : "GpI",
        "description" : "gramofonske ploče I format"
    }, {
        "library" : "gbns",
        "coder_id" : "GpII",
        "description" : "gramofonske ploče II format"
    }, {
        "library" : "gbns",
        "coder_id" : "I",
        "description" : "Format I – Monografske publikacije I format"
    }, {
        "library" : "gbns",
        "coder_id" : "II",
        "description" : "Format II - Monografske publikacije II format"
    }, {
        "library" : "gbns",
        "coder_id" : "III",
        "description" : "Format III - Monografske publikacije III format"
    }, {
        "library" : "gbns",
        "coder_id" : "IV",
        "description" : "Format IV - Monografske publikacije IV format"
    }, {
        "library" : "gbns",
        "coder_id" : "KalI",
        "description" : "kalendari I format"
    }, {
        "library" : "gbns",
        "coder_id" : "KalII",
        "description" : "kalendari II format"
    }, {
        "library" : "gbns",
        "coder_id" : "KalIII",
        "description" : "kalendari III format"
    }, {
        "library" : "gbns",
        "coder_id" : "KalIV",
        "description" : "kalendari IV format"
    }, {
        "library" : "gbns",
        "coder_id" : "KdM",
        "description" : "zvučni snimci, muzički"
    }, {
        "library" : "gbns",
        "coder_id" : "NI",
        "description" : "Format - Note"
    }, {
        "library" : "gbns",
        "coder_id" : "NII",
        "description" : "Format - Note"
    }, {
        "library" : "gbns",
        "coder_id" : "NIII",
        "description" : "Format - Note"
    }, {
        "library" : "gbns",
        "coder_id" : "PlI",
        "description" : "Plakat I format"
    }, {
        "library" : "gbns",
        "coder_id" : "PlII",
        "description" : "Plakat II format"
    }, {
        "library" : "gbns",
        "coder_id" : "PlIII",
        "description" : "Plakat III format"
    }, {
        "library" : "gbns",
        "coder_id" : "PlIV",
        "description" : "Plakat IV format"
    }, {
        "library" : "gbns",
        "coder_id" : "PlV",
        "description" : "Plakat lV format"
    }, {
        "library" : "gbns",
        "coder_id" : "Ps",
        "description" : "prospekti"
    }, {
        "library" : "gbns",
        "coder_id" : "RI",
        "description" : "Format RI - Rariteti, monografske publikacije I format"
    }, {
        "library" : "gbns",
        "coder_id" : "RII",
        "description" : "Format RII - Rariteti, monografske publikacije II format"
    }, {
        "library" : "gbns",
        "coder_id" : "RIII",
        "description" : "Format RIII - Rariteti, monografske publikacije III format"
    }, {
        "library" : "gbns",
        "coder_id" : "RIV",
        "description" : "Format RIV - Rariteti, monografske publikacije IV format"
    }, {
        "library" : "gbns",
        "coder_id" : "Ra",
        "description" : "razglednice"
    }, {
        "library" : "gbns",
        "coder_id" : "SPI",
        "description" : "Format SPI – Legat Stevana Pešića, I format"
    }, {
        "library" : "gbns",
        "coder_id" : "SPII",
        "description" : "Format SPII - Legat Stevana Pešića, II format"
    }, {
        "library" : "gbns",
        "coder_id" : "SPIII",
        "description" : "Format SPIII - Legat Stevana Pešića, III format"
    }, {
        "library" : "gbns",
        "coder_id" : "SPRI",
        "description" : "Format SPRI"
    }, {
        "library" : "gbns",
        "coder_id" : "SPRII",
        "description" : "Format SPRII"
    }, {
        "library" : "gbns",
        "coder_id" : "SPRIII",
        "description" : "Format SPRIII"
    } ]
);

db.coders.internalMark.insert(
    [ {
        "library" : "gbns",
        "coder_id" : "A1",
        "description" : "arheografsko odeljenje 1 (br. 20)"
    }, {
        "library" : "gbns",
        "coder_id" : "A2",
        "description" : "arheografsko odeljenje 2 (br. 23)"
    }, {
        "library" : "gbns",
        "coder_id" : "A3",
        "description" : "arheografsko odeljenje 3 (br. 17)"
    }, {
        "library" : "gbns",
        "coder_id" : "A4",
        "description" : "arheografsko odeljenje 4 (potkrovlje)"
    }, {
        "library" : "gbns",
        "coder_id" : "A5",
        "description" : "arheografsko odeljenje 5 (potkrovlje)"
    }, {
        "library" : "gbns",
        "coder_id" : "AG",
        "description" : "AGRIS"
    }, {
        "library" : "gbns",
        "coder_id" : "B1",
        "description" : "bibliografija (br. 60)"
    }, {
        "library" : "gbns",
        "coder_id" : "IN",
        "description" : "Ingormacije"
    }, {
        "library" : "gbns",
        "coder_id" : "M1",
        "description" : "matično 1"
    }, {
        "library" : "gbns",
        "coder_id" : "M2",
        "description" : "matično 2"
    }, {
        "library" : "gbns",
        "coder_id" : "M3",
        "description" : "matično 3"
    }, {
        "library" : "gbns",
        "coder_id" : "M4",
        "description" : "matično 4"
    }, {
        "library" : "gbns",
        "coder_id" : "N1",
        "description" : "nabavka"
    }, {
        "library" : "gbns",
        "coder_id" : "O1",
        "description" : "obrada 1 (br. 58)"
    }, {
        "library" : "gbns",
        "coder_id" : "O2",
        "description" : "obrada 2 (br. 65)"
    }, {
        "library" : "gbns",
        "coder_id" : "O3",
        "description" : "obrada 3 (br. 64)"
    }, {
        "library" : "gbns",
        "coder_id" : "P1",
        "description" : "periodika - obrada (br. 52)"
    }, {
        "library" : "gbns",
        "coder_id" : "PK",
        "description" : "posebne zbirke - karte"
    }, {
        "library" : "gbns",
        "coder_id" : "PL",
        "description" : "posebne zbirke - likovni fond"
    }, {
        "library" : "gbns",
        "coder_id" : "PM",
        "description" : "posebne zbirke - muzikalije"
    }, {
        "library" : "gbns",
        "coder_id" : "PS",
        "description" : "posebne zbirke - sitno štampa"
    }, {
        "library" : "gbns",
        "coder_id" : "RC",
        "description" : "referalni centar ( priru na )"
    }, {
        "library" : "gbns",
        "coder_id" : "RE",
        "description" : "rezervni fond"
    }, {
        "library" : "gbns",
        "coder_id" : "S1",
        "description" : "Sekretarijat - arhiva"
    }, {
        "library" : "gbns",
        "coder_id" : "U1",
        "description" : "Upravnik"
    }, {
        "library" : "gbns",
        "coder_id" : "U2",
        "description" : "Pomoćnik upravnika za stručne poslove"
    }, {
        "library" : "gbns",
        "coder_id" : "U3",
        "description" : "Pomoćnik upravnika za opšte poslove"
    }, {
        "library" : "gbns",
        "coder_id" : "Z1",
        "description" : "Zaštita"
    }, {
        "library" : "gbns",
        "coder_id" : "Č1",
        "description" : "čitaonica rariteta (br. 18)"
    }, {
        "library" : "gbns",
        "coder_id" : "Č2",
        "description" : "donja čitaonica"
    }, {
        "library" : "gbns",
        "coder_id" : "Č3",
        "description" : "gornja čitaonica"
    }, {
        "library" : "gbns",
        "coder_id" : "Č4",
        "description" : "čitaonica posebnih zbirki"
    }, {
        "library" : "gbns",
        "coder_id" : "Č5",
        "description" : "čitaonica referalnog centra"
    } ]
);
//changeset petar:ChangeSet-coders_item_status
db.coders.status.insert(
    [
        {'coder_id': '+', 'description': 'Slobodno za razmenu', 'lendable':false, 'showable':true},
        {'coder_id': '-', 'description': 'Deziderat'},
        {'coder_id': '1', 'description': 'Naručeno'},
        {'coder_id': '2', 'description': 'U obradi'},
        {'coder_id': '3', 'description': 'U povezu'},
        {'coder_id': '4', 'description': 'U reviziji'},
        {'coder_id': '5', 'description': 'Preusmereno'},
        {'coder_id': '6', 'description': 'Oštećeno'},
        {'coder_id': '7', 'description': 'Zagubljeno'},
        {'coder_id': '8', 'description': 'Izgubljeno'},
        {'coder_id': '9', 'description': 'Otpisano'},
        {'coder_id': 'A', 'description': 'Aktivno'},
        {'coder_id': 'E', 'description': 'Britanski Savet'}
    ]
);


//changeset dboberic:ChangeSet-coders_location
db.coders.location.insert(
    [ {
        "library" : "gbns",
        "coder_id" : "0",
        "description" : "Gradska biblioteka NS"
    }, {
        "library" : "gbns",
        "coder_id" : "1",
        "description" : "Đura Daničić"
    }, {
        "library" : "gbns",
        "coder_id" : "2",
        "description" : "Stevan Sremac"
    }, {
        "library" : "gbns",
        "coder_id" : "3",
        "description" : "Petefi Šandor"
    }, {
        "library" : "gbns",
        "coder_id" : "4",
        "description" : "Toša Trifunov"
    }, {
        "library" : "gbns",
        "coder_id" : "5",
        "description" : "Kosta Trifković"
    }, {
        "library" : "gbns",
        "coder_id" : "6",
        "description" : "Sr. Kamenica - J.J. Zmaj"
    }, {
        "library" : "gbns",
        "coder_id" : "7",
        "description" : "Dečje odeljenje"
    }, {
        "library" : "gbns",
        "coder_id" : "8",
        "description" : "B fond"
    }, {
        "library" : "gbns",
        "coder_id" : "9",
        "description" : "Petar Kočić"
    }, {
        "library" : "gbns",
        "coder_id" : "10",
        "description" : "Milica Stojadinović Srpkinja"
    }, {
        "library" : "gbns",
        "coder_id" : "11",
        "description" : "Đorđe Aracki"
    }, {
        "library" : "gbns",
        "coder_id" : "12",
        "description" : "Majur"
    }, {
        "library" : "gbns",
        "coder_id" : "13",
        "description" : "Mihal Babinka"
    }, {
        "library" : "gbns",
        "coder_id" : "14",
        "description" : "Endre Adi"
    }, {
        "library" : "gbns",
        "coder_id" : "15",
        "description" : "7. jul"
    }, {
        "library" : "gbns",
        "coder_id" : "16",
        "description" : "Nikola Tesla"
    }, {
        "library" : "gbns",
        "coder_id" : "17",
        "description" : "Futog - J.J. Zmaj"
    }, {
        "library" : "gbns",
        "coder_id" : "18",
        "description" : "Đura Jakšić"
    }, {
        "library" : "gbns",
        "coder_id" : "19",
        "description" : "Veljko Petrović"
    }, {
        "library" : "gbns",
        "coder_id" : "20",
        "description" : "Laza Kostić"
    }, {
        "library" : "gbns",
        "coder_id" : "21",
        "description" : "Strana knjiga"
    }, {
        "library" : "gbns",
        "coder_id" : "22",
        "description" : "Vladimir Nazor"
    }, {
        "library" : "gbns",
        "coder_id" : "23",
        "description" : "Čitaonica"
    }, {
        "library" : "gbns",
        "coder_id" : "24",
        "description" : "Medicinska škola"
    }, {
        "library" : "gbns",
        "coder_id" : "25",
        "description" : "Branko Radičević"
    }, {
        "library" : "gbns",
        "coder_id" : "26",
        "description" : "Žarko Zrenjanin"
    }, {
        "library" : "gbns",
        "coder_id" : "27",
        "description" : "Danilo Kiš"
    }, {
        "library" : "gbns",
        "coder_id" : "28",
        "description" : "Ivo Andrić"
    }, {
        "library" : "gbns",
        "coder_id" : "29",
        "description" : "Serijske publikacije"
    }, {
        "library" : "gbns",
        "coder_id" : "30",
        "description" : "Knjigobus"
    }, {
        "library" : "gbns",
        "coder_id" : "31",
        "description" : "Zavičajna zbirka"
    }, {
        "library" : "gbns",
        "coder_id" : "39",
        "description" : "B fond"
    }, {
        "library" : "gbns",
        "coder_id" : "40",
        "description" : "Trifun Dimić"
    }, {
        "library" : "gbns",
        "coder_id" : "41",
        "description" : "Izvan biblioteke"
    }, {
        "library" : "gbns",
        "coder_id" : "42",
        "description" : "Posebne usluge"
    }, {
        "library" : "gbns",
        "coder_id" : "43",
        "description" : "MZ Omladinski pokret"
    }, {
        "library" : "gbns",
        "coder_id" : "90",
        "description" : "Testiranje"
    }, {
        "library" : "gbns",
        "coder_id" : "99",
        "description" : "Koričenje"
    } ]
);

//changeset petar:ChangeSet-coders_process_types
db.coders.process_types.insert(
    [
        { 'name': 'Tip obrade1', 'pubType': 1, 'libName': 'gbns',
            'initialFields': [
                { 'fieldName': '001', 'subfieldName': '7'},
                { 'fieldName': '001', 'subfieldName': 'a'},
                { 'fieldName': '001', 'subfieldName': 'b'},
                { 'fieldName': '001', 'subfieldName': 'c'},
            ],
            'mandatoryFields': [
                { 'fieldName': '001', 'subfieldName': '7'},
                { 'fieldName': '001', 'subfieldName': 'a'}
            ]
        }
    ]
);

db.coders.sublocation.insert(
    [ {
        "library" : "gbns",
        "coder_id" : "BH",
        "description" : "Istorija Bosne i Hercegovine"
    }, {
        "library" : "gbns",
        "coder_id" : "BN",
        "description" : "Bajke naroda"
    }, {
        "library" : "gbns",
        "coder_id" : "C",
        "description" : "Casopisi opsti fond"
    }, {
        "library" : "gbns",
        "coder_id" : "CD",
        "description" : "CD"
    }, {
        "library" : "gbns",
        "coder_id" : "CDc",
        "description" : "CD Casopisi"
    }, {
        "library" : "gbns",
        "coder_id" : "CDm",
        "description" : "Compact Disc"
    }, {
        "library" : "gbns",
        "coder_id" : "Cg",
        "description" : "Istorija Crne Gore"
    }, {
        "library" : "gbns",
        "coder_id" : "D",
        "description" : "Fizika-diplomski"
    }, {
        "library" : "gbns",
        "coder_id" : "Db",
        "description" : "Direktor biblioteke"
    }, {
        "library" : "gbns",
        "coder_id" : "De",
        "description" : "Knjige na nemačkom jeziku"
    }, {
        "library" : "gbns",
        "coder_id" : "E1",
        "description" : "Engleska knj. Đ.D."
    }, {
        "library" : "gbns",
        "coder_id" : "E23",
        "description" : "Engleska knj. Čitaonica"
    }, {
        "library" : "gbns",
        "coder_id" : "E7",
        "description" : "Engleska knj. Dečje"
    }, {
        "library" : "gbns",
        "coder_id" : "El",
        "description" : "Knjige na grčkom"
    }, {
        "library" : "gbns",
        "coder_id" : "En",
        "description" : "Knjige na eng. jeziku"
    }, {
        "library" : "gbns",
        "coder_id" : "Es",
        "description" : "Knjige na španskom"
    }, {
        "library" : "gbns",
        "coder_id" : "F",
        "description" : "Fizika"
    }, {
        "library" : "gbns",
        "coder_id" : "Fc",
        "description" : "Fizika-casopisi"
    }, {
        "library" : "gbns",
        "coder_id" : "Fr",
        "description" : "Knjige na francuskom jeziku"
    }, {
        "library" : "gbns",
        "coder_id" : "Fs",
        "description" : "Separati - fizika"
    }, {
        "library" : "gbns",
        "coder_id" : "Hr",
        "description" : "Istorija Hrvatske"
    }, {
        "library" : "gbns",
        "coder_id" : "Hu",
        "description" : "Knjige na Mađarskom jeziku"
    }, {
        "library" : "gbns",
        "coder_id" : "It",
        "description" : "Knjige na italijanskom"
    }, {
        "library" : "gbns",
        "coder_id" : "Izd",
        "description" : "Izdavačka delatnost"
    }, {
        "library" : "gbns",
        "coder_id" : "M",
        "description" : "Matematika"
    }, {
        "library" : "gbns",
        "coder_id" : "M1",
        "description" : "Matematika"
    }, {
        "library" : "gbns",
        "coder_id" : "Mc",
        "description" : "Matematika casopisi"
    }, {
        "library" : "gbns",
        "coder_id" : "Md",
        "description" : "Matematika-diplomski"
    }, {
        "library" : "gbns",
        "coder_id" : "Mk",
        "description" : "Istorija Makedonije"
    }, {
        "library" : "gbns",
        "coder_id" : "Mo",
        "description" : "Matično odeljenje"
    }, {
        "library" : "gbns",
        "coder_id" : "Ms",
        "description" : "Separati - matematika"
    }, {
        "library" : "gbns",
        "coder_id" : "NS",
        "description" : "Istorija Novog Sada"
    }, {
        "library" : "gbns",
        "coder_id" : "Nl",
        "description" : "Knjige na holandskom"
    }, {
        "library" : "gbns",
        "coder_id" : "No",
        "description" : "Nabavno odeljenje"
    }, {
        "library" : "gbns",
        "coder_id" : "Ob",
        "description" : "Obradno odeljenje"
    }, {
        "library" : "gbns",
        "coder_id" : "PFo",
        "description" : "Priručni fond Odraslo odeljenje"
    }, {
        "library" : "gbns",
        "coder_id" : "Pf",
        "description" : "Priručni fond Dečje odeljenje"
    }, {
        "library" : "gbns",
        "coder_id" : "Pt",
        "description" : "Knjige na portugalskom"
    }, {
        "library" : "gbns",
        "coder_id" : "RS",
        "description" : "Istorija Sbije"
    }, {
        "library" : "gbns",
        "coder_id" : "Ru",
        "description" : "Knjige na ruskom jeziku"
    }, {
        "library" : "gbns",
        "coder_id" : "Rue",
        "description" : "Knjige na rusinskom jeziku"
    }, {
        "library" : "gbns",
        "coder_id" : "Sk",
        "description" : "Knjige na slovačkom jeziku"
    }, {
        "library" : "gbns",
        "coder_id" : "Sl",
        "description" : "Istorija Slovenije"
    }, {
        "library" : "gbns",
        "coder_id" : "Sv",
        "description" : "Knjige na švedskom"
    }, {
        "library" : "gbns",
        "coder_id" : "Tu",
        "description" : "Knjige na turskom"
    }, {
        "library" : "gbns",
        "coder_id" : "Uk",
        "description" : "Knjige na Ukrajinskom"
    }, {
        "library" : "gbns",
        "coder_id" : "Vj",
        "description" : "Istorija Vojvodine"
    }, {
        "library" : "gbns",
        "coder_id" : "YU",
        "description" : "Istorija Jugoslavije 1918-1992"
    }, {
        "library" : "gbns",
        "coder_id" : "Zz",
        "description" : "Zavicajna zbirka, prirucna lit."
    }, {
        "library" : "gbns",
        "coder_id" : "heb",
        "description" : "Knjige na hebrejskom"
    }, {
        "library" : "gbns",
        "coder_id" : "kr",
        "description" : "Fond za radionice"
    }, {
        "library" : "gbns",
        "coder_id" : "ppf",
        "description" : "Poseban priručni fond"
    }, {
        "library" : "gbns",
        "coder_id" : "ro",
        "description" : "Knjige na rumunskom"
    }, {
        "library" : "gbns",
        "coder_id" : "s",
        "description" : "Tekuća periodika"
    }, {
        "library" : "gbns",
        "coder_id" : "sr",
        "description" : "Raritetna periodika"
    } ]
);





