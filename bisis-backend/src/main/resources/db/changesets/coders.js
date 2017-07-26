//mongeez formatted javascript


//changeset petar:ChangeSet-coders
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

db.coders.location.insert(
    [
        {'library':'gbns','location_id': '01', 'location_name': 'Stevan Sremac'}

    ]
);