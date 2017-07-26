//mongeez formatted javascript


//changeset petar:ChangeSet-coders_item_status
db.coders.status.insert(
  [
      {'status': '+', 'description': 'Slobodno za razmenu', 'lendable':false, 'showable':false},
      {'status': '-', 'description': 'Deziderat'},
      {'status': '1', 'description': 'Naručeno'},
      {'status': '2', 'description': 'U obradi'},
      {'status': '3', 'description': 'U povezu'},
      {'status': '4', 'description': 'U reviziji'},
      {'status': '5', 'description': 'Preusmereno'},
      {'status': '6', 'description': 'Oštećeno'},
      {'status': '7', 'description': 'Zagubljeno'},
      {'status': '8', 'description': 'Izgubljeno'},
      {'status': '9', 'description': 'Otpisano'},
      {'status': 'A', 'description': 'Aktivno'},
      {'status': 'E', 'description': 'Britanski Savet'}
  ]
);

//changeset dboberic:ChangeSet-coders_location
db.coders.location.insert(
    [
        {'library':'gbns','location_id': '01', 'location_name': 'Stevan Sremac'}

    ]
);

//changeset petar:ChangeSet-coders_process_types
db.coders.process_types.insert(
    [
        {
            "name" : "Monografski - kompletna obrada",
            "libName": "gbns",
            "pubType" : {
                "pubType" : 1.0,
                "name" : "Monografska"
            },
            "initialSubfields" : [
                {
                    "description" : "Abeceda/pismo",
                    "name" : "7",
                    "owner" : {
                        "description" : "Identifikator sloga",
                        "name" : "001",
                        "mandatory" : true,
                        "repeatable" : false
                    }
                },
                {
                    "owner" : {
                        "name" : "001",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "d",
                    "defaultValue" : "2"
                },
                {
                    "owner" : {
                        "name" : "001",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "c",
                    "defaultValue" : "a"
                },
                {
                    "owner" : {
                        "name" : "101",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "a",
                    "defaultValue" : "scc"
                },
                {
                    "owner" : {
                        "name" : "606",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "8"
                },
                {
                    "owner" : {
                        "name" : "966",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "a"
                }
            ]
        },
        {
            "name" : "Serijski - kompletna obrada",
            "libName": "gbns",
            "pubType" : {
                "pubType" : 2.0,
                "name" : "Serijska"
            },
            "initialSubfields" : [
                {
                    "description" : "Abeceda/pismo",
                    "name" : "7",
                    "owner" : {
                        "description" : "Identifikator sloga",
                        "name" : "001",
                        "mandatory" : true,
                        "repeatable" : false
                    }
                },
                {
                    "owner" : {
                        "name" : "001",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "d",
                    "defaultValue" : "2"
                },
                {
                    "owner" : {
                        "name" : "001",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "c",
                    "defaultValue" : "a"
                },
                {
                    "owner" : {
                        "name" : "101",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "a",
                    "defaultValue" : "scc"
                },
                {
                    "owner" : {
                        "name" : "606",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "8"
                },
                {
                    "owner" : {
                        "name" : "966",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "a"
                }
            ]
        },
        {
            "name" : "Neknjizna gradja - kompletna obrada",
            "pubType" : {
                "pubType" : 3.0,
                "name" : "Neknjizna"
            },
            "initialSubfields" : [
                {
                    "description" : "Abeceda/pismo",
                    "name" : "7",
                    "owner" : {
                        "description" : "Identifikator sloga",
                        "name" : "001",
                        "mandatory" : true,
                        "repeatable" : false
                    }
                },
                {
                    "owner" : {
                        "name" : "001",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "d",
                    "defaultValue" : "2"
                },
                {
                    "owner" : {
                        "name" : "001",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "c",
                    "defaultValue" : "a"
                },
                {
                    "owner" : {
                        "name" : "101",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "a",
                    "defaultValue" : "scc"
                },
                {
                    "owner" : {
                        "name" : "606",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "8"
                },
                {
                    "owner" : {
                        "name" : "966",
                        "repetable" : false,
                        "mandatory" : false
                    },
                    "name" : "a"
                }
            ]
        }
    ]
);