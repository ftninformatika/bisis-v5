//mongeez formatted javascript


//changeset petar:ChangeSet-librarians
db.user.insert({
    "username": "admin.admin@gbns.com",
    "password": "admin1",
    "biblioteka": "gbns",
    "authorities" : [
        "ROLE_ADMIN"
    ],
    "ime": "Bojana",
    "prezime": "Dimić",
    "email": "bdimic@uns.ac.rs",
    "napomena": "napomena",
    "obrada": true,
    "cirkulacija": true,
    "administracija": true,
    "context": {"pref1": "AU", "pref2": "TI", "pref3": "KW", "pref4": "LA", "pref5": "PY",
    "defaultProcessType": { 'name': 'Tip obrade1', 'pubType': 1, 'libName': 'gbns',
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
    },
        "processTypes": [
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
    },
    "curentProcessType": null
});