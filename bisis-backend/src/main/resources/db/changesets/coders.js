//mongeez formatted javascript


//changeset petar:ChangeSet-coders

db.coders.acquisition.insert(
    [
        {'coder_id':'c', 'description':'Poklon'}

    ]
);
db.coders.accessionReg.insert(
    [
        {'library':'gbns','coder_id': '01', 'description': 'Monografske publikacije'}

    ]
);
db.coders.availability.insert(
    [
        {'coder_id': '1', 'description': 'Vremenski ograničena dostupnost - do 7 dana'}

    ]
);

db.coders.binding.insert(
    [
        {'coder_id':'t', 'description':'Tvrdi povez'}

    ]
);

db.coders.format.insert(
    [
        {'coder_id':'I', 'description':'Format I – Monografske publikacije I format'}

    ]
);

db.coders.internalMark.insert(
    [
        {'coder_id':'A1', 'description':'arheografsko odeljenje 1 (br. 20)'}

    ]
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
    [
        {'library':'gbns','coder_id': '01', 'description': 'Đura Daničić, Dunavska 1, Novi Sad'}

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

db.coders.sublocation.insert(
    [
        {'coder_id': 's', 'description': 'Tekuća periodika'}

    ]
);
db.coders.education.insert(
    [
        { 'description': 'osnovna škola'},
        { 'description': 'srednja škola'}

    ]
);
db.coders.membership.insert(
    [
        {'memberType':'redovan','userCateg':'zaposlen','cost':500.0 }

    ]
);
db.coders.coders.membership_type.insert(
    [
        { 'description': 'redovan', 'period':365}

    ]
);
db.coders.coders.user_categ.insert(
    [
        {'description':'zaposlen','titlesNo':500, 'period':30, 'maxPeriod':500 }

    ]
);
db.coders.coders.place.insert(
    [
        {'city':'Novi Sad', 'zip':'21000' }

    ]
);

