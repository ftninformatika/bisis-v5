//mongeez formatted javascript

//changeset petar:ChangeSet-circulation

db.coders.warning_type.insert([
    {"library": "gbns", "description": "neka opomena", "template": "neki template"},
    {"description": "univerzalna opomena", "template": "univerzalni template"}
]);

db.coders.user_categ.insert(
    [
        {'description':'zaposlen','titlesNo':500, 'period':30, 'maxPeriod':500 }

    ]
);

db.coders.membership_type.insert(
    [
        { 'description': 'redovan', 'period':365}

    ]
);

db.coders.membership.insert(
    [
        {'memberType':'redovan','userCateg':'zaposlen','cost':500.0 }

    ]
);

db.coders.education.insert(
    [
        { 'description': 'osnovna škola'},
        { 'description': 'srednja škola'}

    ]
);

db.coders.place.insert(
    [
        {'city':'Novi Sad', 'zip':'21000' }

    ]
);

db.coders.organization.insert(
    [
        {"library": "gbns", "name": "FTN Informatika", "adress": "Laze Nancic 123", "city": "Novi Sad", "zip": "21000"}
    ]
);

db.coders.language.insert(
  [
      {'library': "gbns", 'description': "Srpski jezik"},
      {'library': "gbns", 'description': "Engleski jezik"}
  ]
);