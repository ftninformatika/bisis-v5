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

db.coders.circ_location.insert([
    {
        'library': "gbns",
        'location_id': "01",
        'description': "Some description",
        'lastUserId': 1
    },
    {
        'library': "gbns",
        'location_id': "02",
        'description': "Some description2",
        'lastUserId': 2
    }
]);

db.coders.corporate_member.insert([
    {
        'library': "gbns",
        'userId': "01000000000",
        'instName': "FTN Informatika doo",
        'signDate': ISODate("2002-12-15T23:00:00.000Z"),
        'address': "Laze Nancica 36",
        'city': "Novi Sad",
        'zip': 21000,
        'phone': "012301230123",
        'email': "nesto@email.com",
        'fax': "fax",
        'secAddress': "/",
        'contFirstName': "Petar",
        'contLastName': "Petrovic",
        'contEmail': 'cont@email.com'
    }
]);