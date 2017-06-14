//mongeez formatted javascript

//changeset petar:ChangeSet-1
db.gbns.members.remove({});
//changeset petar:ChangeSet-2
db.gbns.records.remove({});
//changeset petar:ChangeSet-3
db.user.remove({});
//changeset petar:ChangeSet-4
db.libraries.remove({});


//changeset petar:ChangeSet-5
db.user.insert({
    "_class" : "com.ftninformatika.bisis.auth.model.User",
    "authorities" : [
        "ROLE_USER"
    ],
    "username" : "admin",
    "password" : "admin",
    "accountNonExpired" : false,
    "accountNonLocked" : false,
    "credentialsNonExpired" : false,
    "isEnabled" : true,
    "createdAt" : "2017-06-14T09:51:38.464"
});
//changeset petar:ChangeSet-6
db.libraries.insert({

    "libraryName" : "gbns",
    "bibliotekari" : [
        {
            "username" : "admin",
            "password" : "admin1",
            "ime" : "Bojana",
            "prezime" : "Dimić",
            "email" : "bdimic@uns.ns.ac.yu",
            "napomena" : "napomena",
            "obrada" : 1,
            "cirkulacija" : 1,
            "administracija" : 1,
            "context" : ""
        },
        {
            "username" : "Agi",
            "password" : "aron",
            "ime" : "Agneš",
            "prezime" : "Pisar",
            "email" : "",
            "napomena" : "",
            "obrada" : 1,
            "cirkulacija" : 0,
            "administracija" : 0,
            "context" : ""
        },
        {
            "username" : "ana",
            "password" : "letol",
            "ime" : "Ana",
            "prezime" : "Ana",
            "email" : "",
            "napomena" : "",
            "obrada" : 1,
            "cirkulacija" : 0,
            "administracija" : 0,
            "context" : ""
        },
        {
            "username" : "Angela",
            "password" : "sunce",
            "ime" : "Angelina",
            "prezime" : "Dačić",
            "email" : "",
            "napomena" : "",
            "obrada" : 1,
            "cirkulacija" : 0,
            "administracija" : 0,
            "context" : ""  },
        {
            "username" : "blaza",
            "password" : "blpl77",
            "ime" : "Blaženka",
            "prezime" : "Marković",
            "email" : "",
            "napomena" : "",
            "obrada" : 1,
            "cirkulacija" : 1,
            "administracija" : 1,
            "context" : "neko podesavanje"
        },
        {
            "username" : "Boba",
            "password" : "boba",
            "ime" : "Slobodanka",
            "prezime" : "Čerevicki",
            "email" : "",
            "napomena" : "",
            "obrada" : 1,
            "cirkulacija" : 1,
            "administracija" : 0,
            "context" : ""
        },
        {
            "username" : "circ",
            "password" : "circ1",
            "ime" : "Danijela",
            "prezime" : "Tesendic",
            "email" : "tesendic@uns.ns.ac.yu",
            "napomena" : "",
            "obrada" : 0,
            "cirkulacija" : 1,
            "administracija" : 0,
            "context" : ""
        },
        {
            "username" : "Danijela",
            "password" : "panona",
            "ime" : "Danijela",
            "prezime" : "Dobretić",
            "email" : "",
            "napomena" : "",
            "obrada" : 1,
            "cirkulacija" : 0,
            "administracija" : 0,
            "context" : ""
        }]})
//changeset petar:ChangeSet-7
db.gbns.members.insert({
    "_id" : ObjectId("59391fc3ea9b8fbe1ed5ed28"),
    "sysId" : 12,
    "organizationId" : 0,
    "languages" : 0,
    "educationLevel" : 0,
    "membershipType" : 1,
    "userCategory" : 2,
    "groups" : 0,
    "userId" : "01000000000",
    "firstName" : "Milka",
    "lastName" : "Grubešić",
    "parentName" : "Ilija",
    "address" : "Veselina Masleše 146",
    "city" : "Novi Sad",
    "zip" : 21000,
    "phone" : null,
    "email" : null,
    "jmbg" : null,
    "docId" : 0,
    "docNo" : "8028424325/97",
    "docCity" : "NS",
    "country" : null,
    "gender" : "F",
    "age" : "C",
    "secAddress" : null,
    "secZip" : 0,
    "secCity" : null,
    "secPhone" : null,
    "note" : "Futog Poljoprivredna škola",
    "interests" : null,
    "warningInd" : 1,
    "occupation" : null,
    "title" : null,
    "indexNo" : null,
    "classNo" : 0,
    "pass" : null,
    "blockReason" : null,
    "lending" : [
        {
            "id" : 339,
            "ctlgNo" : "17000019566",
            "lendDate" : ISODate("2002-12-15T23:00:00.000Z"),
            "location" : 1,
            "returnDate" : null,
            "resumeDate" : null,
            "deadline" : ISODate("2003-01-04T23:00:00.000Z"),
            "librarianLend" : "demo",
            "librarianReturn" : null,
            "librarianResume" : null
        }
        ,
        {
            "id" : 357,
            "ctlgNo" : "01000062181",
            "lendDate" : ISODate("2004-04-14T23:00:00.000Z"),
            "location" : 1,
            "returnDate" : ISODate("2004-05-12T23:00:00.000Z"),
            "resumeDate" : null,
            "deadline" : ISODate("2004-05-04T23:00:00.000Z"),
            "librarianLend" : "demo",
            "librarianReturn" : "demo",
            "librarianResume" : null
        },
        {
            "id" : 358,
            "ctlgNo" : "01000044428",
            "lendDate" : ISODate("2004-05-12T23:00:00.000Z"),
            "location" : 1,
            "returnDate" : ISODate("2004-05-13T23:00:00.000Z"),
            "resumeDate" : null,
            "deadline" : ISODate("2004-06-01T23:00:00.000Z"),
            "librarianLend" : "demo",
            "librarianReturn" : "demo",
            "librarianResume" : null
        },
        {
            "id" : 359,
            "ctlgNo" : "01000087566",
            "lendDate" : ISODate("2004-05-12T23:00:00.000Z"),
            "location" : 1,
            "returnDate" : ISODate("2004-05-23T23:00:00.000Z"),
            "resumeDate" : null,
            "deadline" : ISODate("2004-06-01T23:00:00.000Z"),
            "librarianLend" : "demo",
            "librarianReturn" : "demo",
            "librarianResume" : null
        },
        {
            "id" : 360,
            "ctlgNo" : "01000107073",
            "lendDate" : ISODate("2004-05-12T23:00:00.000Z"),
            "location" : 1,
            "returnDate" : ISODate("2004-05-13T23:00:00.000Z"),
            "resumeDate" : null,
            "deadline" : ISODate("2004-06-01T23:00:00.000Z"),
            "librarianLend" : "demo",
            "librarianReturn" : "demo",
            "librarianResume" : null
        },
        {
            "id" : 361,
            "ctlgNo" : "01000038834",
            "lendDate" : ISODate("2004-05-13T23:00:00.000Z"),
            "location" : 1,
            "returnDate" : ISODate("2004-05-23T23:00:00.000Z"),
            "resumeDate" : null,
            "deadline" : ISODate("2004-06-02T23:00:00.000Z"),
            "librarianLend" : "demo",
            "librarianReturn" : "demo",
            "librarianResume" : null
        }
    ],
    "signing" : []
});
//changeset petar:ChangeSet-8
db.gbns.records.insertMany([{
        "_id" : ObjectId("59391f89ea9b8fbe1ed417f7"),
        "recordID" : 16,
        "pubType" : 1,
        "fields" : [
            {
                "name" : "001",
                "ind1" : " ",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "a",
                        "content" : "c"
                    },
                    {
                        "name" : "b",
                        "content" : "a"
                    },
                    {
                        "name" : "c",
                        "content" : "m"
                    },
                    {
                        "name" : "d",
                        "content" : "b"
                    },
                    {
                        "name" : "e",
                        "content" : "16"
                    }
                ]
            },
            {
                "name" : "010",
                "ind1" : " ",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "a",
                        "content" : "2-228-32590-2"
                    }
                ]
            },
            {
                "name" : "100",
                "ind1" : " ",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "b",
                        "content" : "d"
                    },
                    {
                        "name" : "c",
                        "content" : "1975"
                    }
                ]
            },
            {
                "name" : "101",
                "ind1" : " ",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "a",
                        "content" : "fre"
                    }
                ]
            },
            {
                "name" : "102",
                "ind1" : " ",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "a",
                        "content" : "fra"
                    }
                ]
            },
            {
                "name" : "200",
                "ind1" : "0",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "a",
                        "content" : "Ekole et psychologie individuelle comparee"
                    },
                    {
                        "name" : "e",
                        "content" : "avant-propos et traduction du dr H. Schaffer"
                    }
                ]
            },
            {
                "name" : "210",
                "ind1" : " ",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "a",
                        "content" : "Paris"
                    },
                    {
                        "name" : "c",
                        "content" : "Payot"
                    },
                    {
                        "name" : "d",
                        "content" : "1975"
                    }
                ]
            },
            {
                "name" : "215",
                "ind1" : " ",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "a",
                        "content" : "259 str."
                    },
                    {
                        "name" : "d",
                        "content" : "18 cm"
                    }
                ]
            },
            {
                "name" : "225",
                "ind1" : " ",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "a",
                        "content" : "Petite bibliotheque Payot"
                    },
                    {
                        "name" : "v",
                        "content" : "259"
                    }
                ]
            },
            {
                "name" : "540",
                "ind1" : " ",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "a",
                        "content" : "Ekole et psychologie"
                    }
                ]
            },
            {
                "name" : "675",
                "ind1" : " ",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "a",
                        "content" : "159.9"
                    },
                    {
                        "name" : "v",
                        "content" : "1"
                    },
                    {
                        "name" : "z",
                        "content" : "scr"
                    }
                ]
            },
            {
                "name" : "700",
                "ind1" : " ",
                "ind2" : "1",
                "subfields" : [
                    {
                        "name" : "4",
                        "content" : "070"
                    },
                    {
                        "name" : "a",
                        "content" : "ADLER"
                    },
                    {
                        "name" : "b",
                        "content" : "Alfred"
                    }
                ]
            },
            {
                "name" : "992",
                "ind1" : " ",
                "ind2" : " ",
                "subfields" : [
                    {
                        "name" : "b",
                        "content" : "crob ; prbm20080623"
                    }
                ]
            }
        ],
        "primerci" : [
            {
                "primerakID" : 27,
                "invBroj" : "01000045885",
                "datumRacuna" : null,
                "brojRacuna" : null,
                "dobavljac" : null,
                "cena" : null,
                "finansijer" : null,
                "usmeravanje" : null,
                "datumInventarisanja" : null,
                "sigFormat" : null,
                "sigPodlokacija" : null,
                "sigIntOznaka" : null,
                "sigDublet" : null,
                "sigNumerusCurens" : null,
                "sigUDK" : "159.9",
                "povez" : null,
                "nacinNabavke" : null,
                "odeljenje" : "21",
                "status" : "5",
                "datumStatusa" : ISODate("2012-07-24T23:00:00.000Z"),
                "inventator" : "stgs20120725",
                "stanje" : 0,
                "dostupnost" : "3",
                "napomene" : "pr20080623",
                "version" : 1,
                "sigDefined" : true
            }
        ],
        "godine" : [],
        "creator" : {
            "username" : "import",
            "institution" : "gb.ns.ac.yu",
            "compact" : "import@gb.ns.ac.yu"
        },
        "modifier" : {
            "username" : "galja",
            "institution" : "gbns",
            "compact" : "galja@gbns"
        },
        "creationDate" : "2005-05-26",
        "lastModifiedDate" : "2012-07-25",
        "mr" : 0,
        "rn" : 16
    }
        ,
        {
            "_id" : ObjectId("59391f89ea9b8fbe1ed417f8"),
            "recordID" : 17,
            "pubType" : 1,
            "fields" : [
                {
                    "name" : "001",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "c"
                        },
                        {
                            "name" : "b",
                            "content" : "a"
                        },
                        {
                            "name" : "c",
                            "content" : "m"
                        },
                        {
                            "name" : "d",
                            "content" : "b"
                        },
                        {
                            "name" : "e",
                            "content" : "17"
                        }
                    ]
                },
                {
                    "name" : "100",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "b",
                            "content" : "d"
                        },
                        {
                            "name" : "c",
                            "content" : "1985"
                        },
                        {
                            "name" : "h",
                            "content" : "scc"
                        }
                    ]
                },
                {
                    "name" : "101",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "ger"
                        }
                    ]
                },
                {
                    "name" : "102",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "deu"
                        }
                    ]
                },
                {
                    "name" : "200",
                    "ind1" : "0",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "Verlorene Herzen"
                        },
                        {
                            "name" : "f",
                            "content" : "Warren Adler"
                        }
                    ]
                },
                {
                    "name" : "210",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "Muenchen"
                        },
                        {
                            "name" : "c",
                            "content" : "Wilhelm Heyne Verlag"
                        },
                        {
                            "name" : "d",
                            "content" : "1985"
                        }
                    ]
                },
                {
                    "name" : "215",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "d",
                            "content" : "cm"
                        }
                    ]
                },
                {
                    "name" : "540",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "VERLORENE Herzen"
                        }
                    ]
                },
                {
                    "name" : "675",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "821.111(73)-31"
                        },
                        {
                            "name" : "b",
                            "content" : "821.111(73)-31"
                        },
                        {
                            "name" : "v",
                            "content" : "2"
                        },
                        {
                            "name" : "z",
                            "content" : "scr"
                        }
                    ]
                },
                {
                    "name" : "700",
                    "ind1" : " ",
                    "ind2" : "1",
                    "subfields" : [
                        {
                            "name" : "4",
                            "content" : "070"
                        },
                        {
                            "name" : "a",
                            "content" : "ADLER"
                        },
                        {
                            "name" : "b",
                            "content" : "Warren"
                        }
                    ]
                },
                {
                    "name" : "701",
                    "ind1" : " ",
                    "ind2" : "1",
                    "subfields" : [
                        {
                            "name" : "4",
                            "content" : "071"
                        }
                    ]
                },
                {
                    "name" : "992",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "b",
                            "content" : "crob ; sgda20130729"
                        }
                    ]
                }
            ],
            "primerci" : [
                {
                    "primerakID" : 28,
                    "invBroj" : "01000070551",
                    "datumRacuna" : null,
                    "brojRacuna" : null,
                    "dobavljac" : null,
                    "cena" : null,
                    "finansijer" : null,
                    "usmeravanje" : null,
                    "datumInventarisanja" : null,
                    "sigFormat" : null,
                    "sigPodlokacija" : null,
                    "sigIntOznaka" : null,
                    "sigDublet" : null,
                    "sigNumerusCurens" : null,
                    "sigUDK" : "821.111(73)-31",
                    "povez" : null,
                    "nacinNabavke" : null,
                    "odeljenje" : "21",
                    "status" : "5",
                    "datumStatusa" : ISODate("2013-08-06T23:00:00.000Z"),
                    "inventator" : "priv20080701",
                    "stanje" : 0,
                    "dostupnost" : null,
                    "napomene" : "pr20080701",
                    "version" : 3,
                    "sigDefined" : true
                }
            ],
            "godine" : [],
            "creator" : {
                "username" : "import",
                "institution" : "gb.ns.ac.yu",
                "compact" : "import@gb.ns.ac.yu"
            },
            "modifier" : {
                "username" : "ljiljakos",
                "institution" : "gbns",
                "compact" : "ljiljakos@gbns"
            },
            "creationDate" : "2005-05-26",
            "lastModifiedDate" : "2013-08-07",
            "mr" : 0,
            "rn" : 17
        }
        ,
        {
            "_id" : ObjectId("59391f89ea9b8fbe1ed417f9"),
            "recordID" : 18,
            "pubType" : 1,
            "fields" : [
                {
                    "name" : "001",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "c"
                        },
                        {
                            "name" : "b",
                            "content" : "a"
                        },
                        {
                            "name" : "c",
                            "content" : "m"
                        },
                        {
                            "name" : "d",
                            "content" : "b"
                        },
                        {
                            "name" : "e",
                            "content" : "18"
                        },
                        {
                            "name" : "7",
                            "content" : "vv"
                        }
                    ]
                },
                {
                    "name" : "100",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "b",
                            "content" : "d"
                        },
                        {
                            "name" : "c",
                            "content" : "1990"
                        },
                        {
                            "name" : "h",
                            "content" : "srp"
                        }
                    ]
                },
                {
                    "name" : "101",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "eng"
                        }
                    ]
                },
                {
                    "name" : "102",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "yug"
                        }
                    ]
                },
                {
                    "name" : "200",
                    "ind1" : "0",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "Adventures of Sindbad the Sailor"
                        },
                        {
                            "name" : "f",
                            "content" : "simplified by D.K. Swan"
                        },
                        {
                            "name" : "g",
                            "content" : "illustrated by Andrew Brown"
                        }
                    ]
                },
                {
                    "name" : "210",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "Hong Kong"
                        },
                        {
                            "name" : "c",
                            "content" : "Longman"
                        },
                        {
                            "name" : "d",
                            "content" : "1990"
                        }
                    ]
                },
                {
                    "name" : "215",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "57 str."
                        },
                        {
                            "name" : "d",
                            "content" : "19 cm"
                        }
                    ]
                },
                {
                    "name" : "225",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "Longman Classics"
                        }
                    ]
                },
                {
                    "name" : "540",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "ADVENTURES of Sindbad"
                        }
                    ]
                },
                {
                    "name" : "675",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "a",
                            "content" : "821.411.21-93-34"
                        },
                        {
                            "name" : "b",
                            "content" : "821.411.21-93-34"
                        },
                        {
                            "name" : "v",
                            "content" : "2"
                        },
                        {
                            "name" : "z",
                            "content" : "scr"
                        }
                    ]
                },
                {
                    "name" : "700",
                    "ind1" : " ",
                    "ind2" : "1",
                    "subfields" : [
                        {
                            "name" : "4",
                            "content" : "070"
                        },
                        {
                            "name" : "a",
                            "content" : "SWAN"
                        },
                        {
                            "name" : "b",
                            "content" : "D.K."
                        }
                    ]
                },
                {
                    "name" : "702",
                    "ind1" : " ",
                    "ind2" : "1",
                    "subfields" : [
                        {
                            "name" : "4",
                            "content" : "440"
                        },
                        {
                            "name" : "a",
                            "content" : "Brown"
                        },
                        {
                            "name" : "b",
                            "content" : "Andrew"
                        }
                    ]
                },
                {
                    "name" : "992",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "b",
                            "content" : "crob"
                        }
                    ]
                },
                {
                    "name" : "992",
                    "ind1" : " ",
                    "ind2" : " ",
                    "subfields" : [
                        {
                            "name" : "b",
                            "content" : "sg"
                        },
                        {
                            "name" : "c",
                            "content" : "20160328"
                        },
                        {
                            "name" : "f",
                            "content" : "joca"
                        }
                    ]
                }
            ],
            "primerci" : [
                {
                    "primerakID" : 29,
                    "invBroj" : "07000038486",
                    "datumRacuna" : null,
                    "brojRacuna" : null,
                    "dobavljac" : null,
                    "cena" : null,
                    "finansijer" : null,
                    "usmeravanje" : null,
                    "datumInventarisanja" : null,
                    "sigFormat" : null,
                    "sigPodlokacija" : null,
                    "sigIntOznaka" : null,
                    "sigDublet" : null,
                    "sigNumerusCurens" : null,
                    "sigUDK" : "821.411.21-93-34",
                    "povez" : null,
                    "nacinNabavke" : null,
                    "odeljenje" : "07",
                    "status" : "A",
                    "datumStatusa" : null,
                    "inventator" : null,
                    "stanje" : 0,
                    "dostupnost" : null,
                    "napomene" : null,
                    "version" : 1,
                    "sigDefined" : true
                }
            ],
            "godine" : [],
            "creator" : {
                "username" : "import",
                "institution" : "gb.ns.ac.yu",
                "compact" : "import@gb.ns.ac.yu"
            },
            "modifier" : {
                "username" : "joca",
                "institution" : "gbns",
                "compact" : "joca@gbns"
            },
            "creationDate" : "2005-05-26",
            "lastModifiedDate" : "2016-03-28",
            "mr" : 0,
            "rn" : 18
        }]
)