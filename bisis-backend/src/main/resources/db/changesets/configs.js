//mongeez formatted javascript


//changeset petar:ChangeSet-configs
db.configs.insert({
    "libraryName" : "gbns",
    "libCollectionSufix":"gbns_com",

    "networkServerList": "http://bisis.uns.ac.rs/servers/bisisXMLM/servers.xml",

    "catalougingPrimerciModel" : "0 1 2 3 13 10 11",
    "catalougingGodineModel" : "0 1 2 3 12 13 15",
    "catalougingInvbrSubStr" : "1 4",
    "catalougingDefaultPrimerakInvKnj" : "00",
    "catalougingDefaultSveskaInvKnj" : "00",
    "catalougingDefaultGodinaInvKnj" : "00",
    "catalougingValidator" : "com.gint.app.bisis4.client.editor.validation.GBValidator",
    "catalougingReportset" : "gbbg",

    "bookcardsLocale": "gbns",
    "bookcardsNextPage" : "Još...",
    "bookcardsCurrentType" : "glavni",
    "bookcardsTranslateX" : "15",
    "bookcardsTranslateY" : "5",
    "bookcardsFontSize" : "-1",
    "bookcardsBrRedova" : "13",

    "barcodePort": "lpt",
    "barcodeOptionName": "library1",
    "barcodeLibrary1": "БИБЛИОТЕКА ШАБАЧКА",
    "barcodeLabelWidth": "456",
    "barcodeLabelHeight": "0",
    "barcodeLabelResolution": "203",
    "barcodeBarwidth": "140",
    "barcodeNarrowbar": "2",
    "barcodeWidebar": "3",
    "barcodeSigfont": "4",
    "barcodeLabelfont": "3",
    "barcodePageCode": "1251",
    "barcodeWrap": "26",

    "barcodeUsersLabelWidth": "300",
    "barcodeUsersLabelHeight": "0",
    "barcodeUsersLabelResolution": "203",
    "barcodeUsersBarwidth": "50",
    "barcodeUsersNarrowbar": "2",
    "barcodeUsersWidebar": "2",
    "barcodeUsersPageCode": "1251",
    "barcodeUsersLinelength": "19",
    "reports" : [
        {
            "className" : "com.ftninformatika.bisis.reportsImpl.JeziciPoOgrancima",
            "menuitem" : "Stanje Fonda|Po Jeziku|Ceo fond",
            "invnumpattern" : "^0100.*",
            "type" : "whole",
            "reportName" : "JeziciPoOgrancimaSve",
            "jasper" : "/jaspers/gbns/JeziciPoOgrancima.jasper"
        },
        {
            "reportName" : "InventarnaKnjigaMonografske",
            "invnumpattern" : "^0100.*",
            "menuitem" : "Inventarna Knjiga|Monografske|Opšte",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaMonografske",
            "type" : "year",
            "jasper" : "/jaspers/gbns/InvKnjigaMonografske.jasper"
        },
        {
            "reportName" : "InvKnjigaZaFilmoveIVideosnimke",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaZaFilmoveIVideosnimke",
            "menuitem" : "Inventarna Knjiga|Filmovi i videosnimci",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/InvKnjigaZaFilmoveIVideosnimke.jasper"
        },
        {
            "reportName" : "InvKnjigaMonografskeZavicajna",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaMonografskeZavicajna",
            "menuitem" : "Inventarna Knjiga|Monografske|Zavičajna",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/InvKnjigaMonografske.jasper"
        },
        {
            "reportName" : "KnjigaInventaraSerijske",
            "className" : "com.ftninformatika.bisis.reportsImpl.KnjigaInventaraSerijske",
            "menuitem" : "Inventarna Knjiga|Serijske",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/KnjigaInventaraSerijske.jasper"
        },
        {
            "reportName" : "InvKnjigaStaraIRetkaKnjiga",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaStaraIRetkaKnjiga",
            "menuitem" : "Inventarna Knjiga|Stara i retka knjiga",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/InvKnjigaStaraIRetkaKnjiga.jasper"
        },
        {
            "reportName" : "InvKnjigaMonografskeRaritet",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaMonografskeRaritet",
            "menuitem" : "Inventarna Knjiga|Monografske|Rariteti",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/InvKnjigaMonografske.jasper"
        },
        {
            "reportName" : "InvKnjigaKartografskaGradja",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaKartografskaGradja",
            "menuitem" : "Inventarna Knjiga|Kartografska građa",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/InvKnjigaKartografskaGradja.jasper"
        },
        {
            "reportName" : "InvKnjigaZaMikrooblik",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaZaMikrooblik",
            "menuitem" : "Inventarna Knjiga|Mikrooblik ",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/InvKnjigaZaMikrooblik.jasper"
        },
        {
            "reportName" : "InvKnjigaMonografskeRaritet",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaMonografskeRaritet",
            "menuitem" : "Inventarna Knjiga|Monografske|Rariteti",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/InvKnjigaMonografske.jasper"
        },
        {
            "reportName" : "InvKnjigaZaMuzikalije",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaZaMuzikalije",
            "menuitem" : "Inventarna Knjiga|Muzikalije",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/InvKnjigaZaMuzikalije.jasper"
        },
        {
            "reportName" : "InvKnjigaZaRukopisnuGradju",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaZaRukopisnuGradju",
            "menuitem" : "Inventarna Knjiga|Rukopisna gradja",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/InvKnjigaZaRukopisnuGradju.jasper"
        },
        {
            "reportName" : "InvKnjigaZaVizuelnuGradjuSlika",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaZaVizuelnuGradjuSlika",
            "menuitem" : "Inventarna Knjiga|Vizuelna gradja",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/InvKnjigaZaVizuelnuGradjuSlika.jasper"
        },
        {
            "reportName" : "InvKnjigaZaVizuelnuProjekciju",
            "className" : "com.ftninformatika.bisis.reportsImpl.InvKnjigaZaVizuelnuProjekciju",
            "menuitem" : "Inventarna Knjiga|Vizuelne projekcije",
            "invnumpattern" : "^0100.*",
            "type" : "month",
            "jasper" : "/jaspers/gbns/InvKnjigaZaVizuelnuProjekciju.jasper"
        }
    ]

});

db.coders.circ_config.insert({
    "library": "gbns",
    "circOptionsXML": "<?xml version='1.0' encoding='UTF-8'?><opt:options xmlns:opt='options'> <client mac='default'>    <general>    <nonCtlgNo>false</nonCtlgNo> <blockedCard>true</blockedCard> <autoReturn>true</autoReturn> <defaultZipCity>true</defaultZipCity> <defaultCity>Beograd</defaultCity> <defaultZip>11000</defaultZip> <fontSize>12</fontSize> <maximize>false</maximize> <lookAndFeel>default</lookAndFeel><theme>com.jgoodies.looks.plastic.theme.LightGray</theme><location>0</location></general><userid><length>11</length><prefix>false</prefix><prefixLength>2</prefixLength><defaultPrefix>1</defaultPrefix><separator>false</separator><separatorSign>/</separatorSign><inputUserid>1</inputUserid></userid><ctlgno><lengthCtlgno>11</lengthCtlgno><locationCtlgno>true</locationCtlgno><locationLength>2</locationLength><defaultLocation>0</defaultLocation><book>true</book><bookLength>2</bookLength><defaultBook>0</defaultBook><separators>true</separators><separator1>/</separator1> <separator2>-</separator2> <inputCtlgno>1</inputCtlgno></ctlgno> <revers><libraryName>Beogradska poslovna škola</libraryName><libraryAddress></libraryAddress><selected>true</selected><height>1</height><width>1</width><rowCount>1</rowCount><count>1</count></revers></client><client mac='00:0E:A6:71:83:77' note='moj racunar'>  <general>    <nonCtlgNo>false</nonCtlgNo>    <blockedCard>true</blockedCard>    <autoReturn>false</autoReturn>    <defaultZipCity>true</defaultZipCity>    <defaultCity>Novi </defaultCity>    <defaultZip>21000</defaultZip>    <fontSize>12</fontSize>    <maximize>false</maximize>    <lookAndFeel>default</lookAndFeel><theme>com.jgoodies.looks.plastic.theme.LightGray</theme><location>0</location></general><userid><length>11</length><prefix>false</prefix><prefixLength>2</prefixLength><defaultPrefix>0</defaultPrefix><separator>false</separator><separatorSign>/</separatorSign><inputUserid>1</inputUserid></userid><ctlgno><lengthCtlgno>11</lengthCtlgno><locationCtlgno>true</locationCtlgno><locationLength>2</locationLength><defaultLocation>0</defaultLocation><book>true</book><bookLength>2</bookLength><defaultBook>0</defaultBook><separators>true</separators><separator1>/</separator1><separator2>-</separator2><inputCtlgno>1</inputCtlgno></ctlgno><revers><libraryName>Biblioteka Departmana za matematiku i informatiku</libraryName><libraryAddress>Adresa</libraryAddress><selected>true</selected><height>1</height><width>1</width><rowCount>1</rowCount><count>1</count></revers></client></opt:options>",
    "validatorOptionsXML": "<?xml version='1.0' encoding='UTF-8'?> <!DOCTYPE form-validation PUBLIC '-//Apache Software Foundation//DTD Commons Validator Rules Configuration 1.3.0//EN' 'http://jakarta.apache.org/commons/dtds/validator_1_3_0.dtd'> <form-validation> <global> <validator name='required' classname='com.ftninformatika.bisis.circ.validator.Validator' method='validateRequired' methodParams='java.lang.Object, org.apache.commons.validator.Field' msg='required.field' /> <validator name='intorblank' classname='com.ftninformatika.bisis.circ.validator.Validator' method='validateIntOrBlank' methodParams='java.lang.Object, org.apache.commons.validator.Field' msg='int.field' /> <validator name='positiveorblank' classname='com.ftninformatika.bisis.circ.validator.Validator' method='validatePositiveOrBlank' methodParams='java.lang.Object, org.apache.commons.validator.Field' msg='positive.field' /> <validator name='doubleorblank' classname='com.ftninformatika.bisis.circ.validator.Validator' method='validateDoubleOrBlank' methodParams='java.lang.Object, org.apache.commons.validator.Field' msg='double.field' /> <validator name='emailorblank' classname='com.ftninformatika.bisis.circ.validator.Validator' method='validateEmailOrBlank' methodParams='java.lang.Object, org.apache.commons.validator.Field' msg='invalid.email' /> <validator name='dateorblank' classname='com.ftninformatika.bisis.circ.validator.Validator' method='validateDateOrBlank' methodParams='java.lang.Object, org.apache.commons.validator.Field' msg='invalid.date' /> <validator name='userorblank' classname='com.ftninformatika.bisis.circ.validator.Validator' method='validateUserIdOrBlank' methodParams='java.lang.Object, org.apache.commons.validator.Field' msg='invalid.userid' /> </global> <formset> <form name='userData'> <field property='firstName' depends='required'> <arg key='userData.firstname.displayname' /> </field> <field property='lastName' depends='required'> <arg key='userData.lastname.displayname' /> </field> <field property='parentName' depends='required'> <arg key='userData.parentname.displayname' /> </field> <field property='address' depends='required'> <arg key='userData.address.displayname' /> </field> <field property='zip' depends='intorblank,required'> <arg key='userData.zip.displayname' /> </field> <field property='city' depends='required'> <arg key='userData.city.displayname' /> </field> <field property='phone' depends=''> <arg key='userData.phone.displayname' /> </field> <field property='email' depends='emailorblank'> <arg key='userData.email.displayname' /> </field> <field property='tmpAddress' depends=''> <arg key='userData.tmpaddress.displayname' /> </field> <field property='tmpZip' depends='intorblank'> <arg key='userData.tmpzip.displayname' /> </field> <field property='tmpCity' depends=''> <arg key='userData.tmpcity.displayname' /> </field> <field property='tmpPhone' depends=''> <arg key='userData.tmpphone.displayname' /> </field> <field property='jmbg' depends=''> <arg key='userData.jmbg.displayname' /> </field> <field property='docNo' depends=''> <arg key='userData.docno.displayname' /> </field> <field property='docCity' depends=''> <arg key='userData.doccity.displayname' /> </field> <field property='country' depends=''> <arg key='userData.country.displayname' /> </field> <field property='title' depends=''> <arg key='userData.title.displayname' /> </field> <field property='occupation' depends=''> <arg key='userData.occupation.displayname' /> </field> <field property='organization' depends=''> <arg key='userData.organization.displayname' /> </field> <field property='eduLvl' depends=''> <arg key='userData.edulvl.displayname' /> </field> <field property='classNo' depends=''> <arg key='userData.classno.displayname' /> </field> <field property='indexNo' depends=''> <arg key='userData.indexno.displayname' /> </field> <field property='note' depends=''> <arg key='userData.note.displayname' /> </field> <field property='interests' depends=''> <arg key='userData.interests.displayname' /> </field> <field property='languages' depends=''> <arg key='userData.language.displayname' /> </field> <field property='dupDate' depends=''> <var> <var-name>datePattern</var-name> <var-value>dd.MM.yyyy</var-value> </var> <arg key='userData.dupdate.displayname' /> </field> <field property='dupNo' depends='intorblank'> <arg key='userData.dupno.displayname' /> </field> </form> <form name='mmbrship'> <field property='userID' depends='userorblank,required'> <arg key='mmbrship.userid.displayname' /> </field> <field property='mmbrshipDate' depends='required'> <var> <var-name>datePattern</var-name> <var-value>dd.MM.yyyy</var-value> </var> <arg key='mmbrship.mmbrshipdate.displayname' /> </field> <field property='mmbrshipUntilDate' depends='required'> <var> <var-name>datePattern</var-name> <var-value>dd.MM.yyyy</var-value> </var> <arg key='mmbrship.mmbrshipuntildate.displayname' /> </field> <field property='mmbrshipCost' depends=''> <arg key='mmbrship.mmbrshipcost.displayname' /> </field> <field property='mmbrshipReceiptId' depends=''> <arg key='mmbrship.mmbrshipreceiptid.displayname' /> </field> <field property='mmbrType' depends='required'> <arg key='mmbrship.mmbrtype.displayname' /> </field> <field property='userCateg' depends='required'> <arg key='mmbrship.usercateg.displayname' /> </field> </form> </formset> </form-validation>"

});
