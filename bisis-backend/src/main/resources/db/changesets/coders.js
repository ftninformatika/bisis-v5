//mongeez formatted javascript


//changeset petar:ChangeSet-coders
db.coders.status.insert(
  [
      {'status': '+', 'opis': 'Slobodno za razmenu', 'zaduziv':0},
      {'status': '-', 'opis': 'Deziderat', 'zaduziv':0},
      {'status': '1', 'opis': 'Naručeno', 'zaduziv':0},
      {'status': '2', 'opis': 'U obradi', 'zaduziv':0},
      {'status': '3', 'opis': 'U povezu', 'zaduziv':0},
      {'status': '4', 'opis': 'U reviziji', 'zaduziv':0},
      {'status': '5', 'opis': 'Preusmereno', 'zaduziv':1},
      {'status': '6', 'opis': 'Oštećeno', 'zaduziv':0},
      {'status': '7', 'opis': 'Zagubljeno', 'zaduziv':0},
      {'status': '8', 'opis': 'Izgubljeno', 'zaduziv':0},
      {'status': '9', 'opis': 'Otpisano', 'zaduziv':2},
      {'status': 'A', 'opis': 'Aktivno', 'zaduziv':1},
      {'status': 'E', 'opis': 'Britanski Savet', 'zaduziv':0}
  ]
);