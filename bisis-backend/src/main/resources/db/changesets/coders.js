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
    [
        {'coder_id': 's', 'description': 'Tekuća periodika'}

    ]
);




