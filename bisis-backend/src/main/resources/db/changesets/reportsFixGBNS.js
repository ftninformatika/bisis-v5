//mongeez formatted javascript


//changeset danijela:ChangeSet-records

db.configs.update({ _id: ObjectId("5a2a820d03c19d0c3c8af69f") }, {
    $set: {

        "reports": [
            {
                "invnumpattern": "^[012][0123456789]00.*",
                "reportName": "Inventarna Knjiga Monografske",
                "className": "com.ftninformatika.bisis.gbns.InvKnjigaMonografske",
                "type": "month",
                "menuitem": "Инвентарна књига|Монографске|Oпште",
                "jasper": "/jaspers/general/InvKnjigaMonografske.jasper",
                "library": "Градска библиотека Нови Сад",
                "reportTitle": "Инвентарна књига ѕа монографске публикације - опште"
            },
            {
                "invnumpattern": "^313[45689].*|^314[01].*",
                "reportName": "Inventarna Knjiga Rariteti",
                "className": "com.ftninformatika.bisis.gbns.InvKnjigaMonografske",
                "type": "month",
                "menuitem": "Инвентарна књига|Монографске|Раритети",
                "jasper": "/jaspers/general/InvKnjigaMonografske.jasper",
                "library": "Градска библиотека Нови Сад",
                "reportTitle": "Инвентарна књига ѕа монографске публикације - раритети"
            },
            {
                "invnumpattern": "^313[123].*",
                "reportName": "Inventarna Knjiga Zavicajna",
                "className": "com.ftninformatika.bisis.gbns.InvKnjigaMonografske",
                "type": "month",
                "menuitem": "Инвентарна књига|Монографске|Завичајна",
                "jasper": "/jaspers/general/InvKnjigaMonografske.jasper",
                "library": "Градска библиотека Нови Сад",
                "reportTitle": "Инвентарна књига ѕа монографске публикације - завичајна збирка"
            },
            {
                "invnumpattern": "^[012][0123456789]01.*",
                "reportName": "Inventara Knjiga Serijske",
                "className": "com.ftninformatika.bisis.gbns.KnjigaInventaraSerijske",
                "type": "month",
                "menuitem": "Инвентарна књига|Серијске",
                "jasper": "/jaspers/general/KnjigaInventaraSerijske.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "JeziciPoOgrancimaGodine",
                "className": "com.ftninformatika.bisis.gbns.JeziciPoOgrancima",
                "type": "year",
                "menuitem": "Стање фонда|По језику|По датуму инвентарисања|Годишњи",
                "jasper": "/jaspers/gbns/JeziciPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "JeziciPoOgrancimaKvartal",
                "className": "com.ftninformatika.bisis.gbns.JeziciPoOgrancima",
                "type": "quarter",
                "menuitem": "Стање фонда|По језику|По датуму инвентарисања|Квартални",
                "jasper": "/jaspers/gbns/JeziciPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "JeziciPoOgrancimaMesec",
                "className": "com.ftninformatika.bisis.gbns.JeziciPoOgrancima",
                "type": "month",
                "menuitem": "Стање фонда|По језику|По датуму инвентарисања|Месечни",
                "jasper": "/jaspers/gbns/JeziciPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "JeziciPoOgrancimaPola",
                "className": "com.ftninformatika.bisis.gbns.JeziciPoOgrancima",
                "type": "half",
                "menuitem": "Стање фонда|По језику|По датуму инвентарисања|Полугодишњи",
                "jasper": "/jaspers/gbns/JeziciPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "JeziciPoOgrancimaRacunGodine",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoJezicimaPoRacunu",
                "type": "year",
                "menuitem": "Стање фонда|По језику|По рачуну|Годишњи",
                "jasper": "/jaspers/gbns/JeziciPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "JeziciPoOgrancimaRacunKvartal",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoJezicimaPoRacunu",
                "type": "quarter",
                "menuitem": "Стање фонда|По језику|По рачуну|Квартални",
                "jasper": "/jaspers/gbns/JeziciPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "JeziciPoOgrancimaMesec",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoJezicimaPoRacunu",
                "type": "month",
                "menuitem": "Стање фонда|По језику|По рачуну|Месечни",
                "jasper": "/jaspers/gbns/JeziciPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "JeziciPoOgrancimaRacunPola",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoJezicimaPoRacunu",
                "type": "half",
                "menuitem": "Стање фонда|По језику|По рачуну|Полугодишњи",
                "jasper": "/jaspers/gbns/JeziciPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "JeziciPoOgrancimaSve",
                "className": "com.ftninformatika.bisis.gbns.JeziciPoOgrancima",
                "type": "whole",
                "menuitem": "Стање фонда|По језику|Цео фонд",
                "jasper": "/jaspers/gbns/JeziciPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoOgrancimaGodine",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoOgrancima",
                "type": "year",
                "menuitem": "Набавка|По начину|По датуму инвентарисања|Годишњи",
                "jasper": "/jaspers/gbns/NabavkaPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoOgrancimaKvartal",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoOgrancima",
                "type": "quarter",
                "menuitem": "Набавка|По начину|По датуму инвентарисања|Квартални",
                "jasper": "/jaspers/gbns/NabavkaPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoOgrancimaMesec",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoOgrancima",
                "type": "month",
                "menuitem": "Набавка|По начину|По датуму инвентарисања|Месечни",
                "jasper": "/jaspers/gbns/NabavkaPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoOgrancimaPola",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoOgrancima",
                "type": "half",
                "menuitem": "Набавка|По начину|По датуму инвентарисања|Полугодишњи",
                "jasper": "/jaspers/gbns/NabavkaPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoOgrancimaRacunGodine",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoNacinuPoRacunu",
                "type": "year",
                "menuitem": "Набавка|По начину|По рачуну|Годишњи",
                "jasper": "/jaspers/gbns/NabavkaPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoOgrancimaRacunKvartal",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoNacinuPoRacunu",
                "type": "quarter",
                "menuitem": "Набавка|По начину|По рачуну|Квартални",
                "jasper": "/jaspers/gbns/NabavkaPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoOgrancimaRacunMesec",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoNacinuPoRacunu",
                "type": "month",
                "menuitem": "Набавка|По начину|По рачуну|Месечни",
                "jasper": "/jaspers/gbns/NabavkaPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoOgrancimaRacunPola",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoNacinuPoRacunu",
                "type": "half",
                "menuitem": "Набавка|По начину|По рачуну|Полугодишњи",
                "jasper": "/jaspers/gbns/NabavkaPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoOgrancimaRacunSve",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoNacinuPoRacunu",
                "type": "whole",
                "menuitem": "Набавка|По начину|По рачуну|Цео фонд",
                "jasper": "/jaspers/gbns/NabavkaPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoOgrancimaSve",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoOgrancima",
                "type": "whole",
                "menuitem": "Набавка|По начину|По датуму инвентарисања|Цео фонд",
                "jasper": "/jaspers/gbns/NabavkaPoOgrancima.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoUDKGodine",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoUDK",
                "type": "year",
                "menuitem": "Набавка|По УДК|По датуму инвентарисања|Годишњи",
                "jasper": "/jaspers/gbns/NabavkaPoUDK.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoUDKKvartal",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoUDK",
                "type": "quarter",
                "menuitem": "Набавка|По УДК|По датуму инвентарисања|Квартални",
                "jasper": "/jaspers/gbns/NabavkaPoUDK.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoUDKRacunKvartal",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoUDKPoRacunu",
                "type": "quarter",
                "menuitem": "Набавка|По УДК|По рачуну|Квартални",
                "jasper": "/jaspers/gbns/NabavkaPoUDK.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoUDKMesec",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoUDK",
                "type": "month",
                "menuitem": "Набавка|По УДК|По датуму инвентарисања|Месечни",
                "jasper": "/jaspers/gbns/NabavkaPoUDK.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoUDKRacunMesec",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoUDKPoRacunu",
                "type": "month",
                "menuitem": "Набавка|По УДК|По рачуну|Месечни",
                "jasper": "/jaspers/gbns/NabavkaPoUDK.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoUDKPola",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoUDK",
                "type": "half",
                "menuitem": "Набавка|По УДК|По датуму инвентарисања|Полугодишњи",
                "jasper": "/jaspers/gbns/NabavkaPoUDK.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoUDKRacunPola",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoUDKPoRacunu",
                "type": "half",
                "menuitem": "Набавка|По УДК|По рачуну|Полугодишњи",
                "jasper": "/jaspers/gbns/NabavkaPoUDK.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoUDKPoRacunuGodine",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoUDKPoRacunu",
                "type": "year",
                "menuitem": "Набавка|По УДК|По рачуну|Годишњи",
                "jasper": "/jaspers/gbns/NabavkaPoUDK.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoUDKSve",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoUDK",
                "type": "whole",
                "menuitem": "Набавка|По УДК|По датуму инвентарисања|Цео фонд",
                "jasper": "/jaspers/gbns/NabavkaPoUDK.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "NabavkaPoUDKRacunSve",
                "className": "com.ftninformatika.bisis.gbns.NabavkaPoUDKPoRacunu",
                "type": "whole",
                "menuitem": "Набавка|По УДК|По рачуну|Цео фонд",
                "jasper": "/jaspers/gbns/NabavkaPoUDK.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "RashodovaneMonografske",
                "className": "com.ftninformatika.bisis.gbns.RashodovaneMonografske",
                "type": "year",
                "menuitem": "Расходоване",
                "jasper": "/jaspers/gbns/RashodovaneMonografske.jasper",
                "library": "Градска библиотека Нови Сад"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "StanjeFondaGodine",
                "className": "com.ftninformatika.bisis.gbns.StanjeFonda",
                "type": "year",
                "menuitem": "Стање фонда|Монографске|По годинама",
                "jasper": "/jaspers/gbns/StanjeFonda.jasper",
                "library": "Градска библиотека Нови Сад",
                "reportTitle": "Стање фонда монографских публикација"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "StanjeFondaSerijskeGodine",
                "className": "com.ftninformatika.bisis.gbns.StanjeFondaSerijske",
                "type": "year",
                "menuitem": "Стање фонда|Серијске|По годинама",
                "jasper": "/jaspers/gbns/StanjeFonda.jasper",
                "library": "Градска библиотека Нови Сад",
                "reportTitle": "Стање фонда серијских публикација"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "StanjeFondaSerijskeSve",
                "className": "com.ftninformatika.bisis.gbns.StanjeFondaSerijske",
                "type": "whole",
                "menuitem": "Стање фонда|Серијске|Цео фонд",
                "jasper": "/jaspers/gbns/StanjeFonda.jasper",
                "library": "Градска библиотека Нови Сад",
                "reportTitle": "Стање фонда серијских публикација"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "StanjeFondaSve",
                "className": "com.ftninformatika.bisis.gbns.StanjeFonda",
                "type": "whole",
                "menuitem": "Стање фонда|Монографске|Цео фонд",
                "jasper": "/jaspers/gbns/StanjeFonda.jasper",
                "library": "Градска библиотека Нови Сад",
                "reportTitle": "Стање фонда монографских публикација"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "StatistikaObradjivacaGodina",
                "className": "com.ftninformatika.bisis.gbns.StatistikaObradjivaca",
                "type": "year",
                "menuitem": "Статистика|Обрађивача|Монографске|По години",
                "jasper": "/jaspers/gbns/StatistikaObradjivaca.jasper",
                "library": "Градска библиотека Нови Сад",
                "reportTitle": "Статистика обрађивача - монографске публикације"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "StatistikaObradjivacaKvartal",
                "className": "com.ftninformatika.bisis.gbns.StatistikaObradjivaca",
                "type": "quarter",
                "menuitem": "Статистика|Обрађивача|Монографске|Кватрални",
                "jasper": "/jaspers/gbns/StatistikaObradjivaca.jasper",
                "library": "Градска библиотека Нови Сад",
                "reportTitle": "Статистика обрађивача - монографске публикације"
            },
            {
                "invnumpattern": "^0100.*",
                "reportName": "StatistikaObradjivacaMesec",
                "className": "com.ftninformatika.bisis.gbns.StatistikaObradjivaca",
                "type": "month",
                "menuitem": "Статистика|Обрађивача|Монографске|По месецу",
                "jasper": "/jaspers/gbns/StatistikaObradjivaca.jasper",
                "library": "Градска библиотека Нови Сад",
                "reportTitle": "Статистика обрађивача - монографске публикације"
            }
        ]

    }
})