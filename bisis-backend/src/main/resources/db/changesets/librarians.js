//mongeez formatted javascript


//changeset petar:ChangeSet-librarians
db.user.insert({
    "username": "admin.admin@gbns.com",
    "password": "admin1",
    "biblioteka": "gbns",
    "ime": "Bojana",
    "prezime": "Dimić",
    "email": "bdimic@uns.ac.rs",
    "napomena": "napomena",
    "obrada": true,
    "cirkulacija": true,
    "administracija": true,
    "context": {"pref1": "AU", "pref2": "TI", "pref3": "KW", "pref4": "LA", "pref5": "PY",
        "processTypes": [
            {
                "name": "Monografski - kompletna obrada",
                "pubType": {
                    "pubType": 1,
                    "name": "Monografska"
                },
                "initialSubfields": [
                    {
                        "description": "Abeceda/pismo",
                        "name": '7',
                        "owner": {
                            "description": "Identifikator sloga",
                            "name": "001",
                            "mandatory": true,
                            "repeatable": false
                        }
                    },
                    {   "owner": {
                        "name": "001",
                        "repetable": false,
                        "mandatory": false
                    },
                        "name": "d",
                        "defaultValue": "2"
                    },
                    {   "owner": {
                        "name": "001",
                        "repetable": false,
                        "mandatory": false
                    },
                        "name": "c",
                        "defaultValue": "a"
                    },
                    {   "owner": {
                        "name": "101",
                        "repetable": false,
                        "mandatory": false
                    },
                        "name": "a",
                        "defaultValue": "scc"
                    },
                    {   "owner": {
                        "name": "606",
                        "repetable": false,
                        "mandatory": false
                    },
                        "name": "8"
                    },
                    {   "owner": {
                        "name": "966",
                        "repetable": false,
                        "mandatory": false
                    },
                        "name": "a"
                    }
                ],
                "mandatorySubfields": [
                    {
                        "owner": {
                            "name": "001",
                            "repetable": false,
                            "mandatory": true
                        },
                        "name": "b"
                    },
                    {
                        "owner": {
                            "name": "001",
                            "repetable": false,
                            "mandatory": true
                        },
                        "name": "c"
                    },
                    {
                        "owner": {
                            "name": "001",
                            "repetable": false,
                            "mandatory": true
                        },
                        "name": "d"
                    },
                    {
                        "owner": {
                            "name": "200",
                            "repetable": false,
                            "mandatory": true
                        },
                        "name": "a"
                    }
                ],
                "indicators": [
                    {
                        "description": "Neki opis"
                    }
                ]
            }
        ]
    },
    "curentProcessType":
        {
            "name": "Monografski - kompletna obrada",
            "pubType": {
                "pubType": 1,
                "name": "Monografska"
            },
            "initialSubfields": [
                {
                    "description": "Abeceda/pismo",
                    "name": '7',
                    "owner": {
                        "description": "Identifikator sloga",
                        "name": "001",
                        "mandatory": true,
                        "repeatable": false
                    }
                },
                {   "owner": {
                        "name": "001",
                        "repetable": false,
                        "mandatory": false
                    },
                    "name": "d",
                    "defaultValue": "2"
                },
                {   "owner": {
                        "name": "001",
                        "repetable": false,
                        "mandatory": false
                    },
                    "name": "c",
                    "defaultValue": "a"
                },
                {   "owner": {
                        "name": "101",
                        "repetable": false,
                        "mandatory": false
                    },
                    "name": "a",
                    "defaultValue": "scc"
                },
                {   "owner": {
                        "name": "606",
                        "repetable": false,
                        "mandatory": false
                    },
                    "name": "8"
                },
                {   "owner": {
                        "name": "966",
                        "repetable": false,
                        "mandatory": false
                    },
                    "name": "a"
                }
            ],
            "mandatorySubfields": [
                {
                    "owner": {
                        "name": "001",
                        "repetable": false,
                        "mandatory": true
                    },
                    "name": "b"
                },
                {
                    "owner": {
                        "name": "001",
                        "repetable": false,
                        "mandatory": true
                    },
                    "name": "c"
                },
                {
                    "owner": {
                        "name": "001",
                        "repetable": false,
                        "mandatory": true
                    },
                    "name": "d"
                },
                {
                    "owner": {
                        "name": "200",
                        "repetable": false,
                        "mandatory": true
                    },
                    "name": "a"
                }
            ]
        }
});