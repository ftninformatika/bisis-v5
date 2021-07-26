<#include "_koncept_bs.ftl"
><#assign udcList=false
><#assign arap=false
><#assign rim=false
><#assign predm=false
><#assign udc=false



><#assign udc=false





><#macro getMonografski
><#assign lm=""
    ><#assign inv=""
    ><#assign out=""
    ><#assign sign=""
    ><#assign sign1=","
    ><#assign firstLM=true
    ><#assign existsSign=false
    ><@odrednica/><@brojID/><@glavniOpis/><@napomene/><@isbn/><#--
--><#if nap!="" && isbnBR!=""
    ><#assign nap=nap+"<BR>"
        ></#if
    ><#if nap!="" || isbnBR!=""
    ><#assign opis=opis+"<BR><BR>"
        ></#if
    ><#if primerci?exists
    ><#assign brojac=0
        ><#list primerci as primerak
        ><#assign val=primerak.signatura
            ><#if val!="" && sign1?index_of(","+val+",")=-1
            ><#assign existsSign=true
                ><#assign sign1=sign1+val+","
                ><#assign sign=val
                ><@inventar/><#--
                   --><#assign sve=","+sign+","
                ><#if firstLM
                ><#assign firstLM=false
                    ><#assign lm=lm+"<sig>"+sign+"<BR><BR></sig>"+odred+"<BR>"+opis+nap
                    ><#if nap!="" && isbnBR!=""
                    ><#assign lm=lm+"<BR>"
                        ></#if
                    ><#assign lm=lm+isbnBR+"<BR><BR>"+inv+"<BR>"+brID
                    ><#else
                ><#assign lm=lm+"<BR><np>"+"<sig>"+sign+"<BR><BR></sig>"+odred+"<BR>"+opis+"<BR>"+nap
                    ><#if nap!="" && isbnBR!=""
                    ><#assign lm=lm+"<BR>"
                        ></#if
                    ><#assign lm=lm+isbnBR+"<BR><BR>"+inv+"<BR>"+brID
                    ></#if
                ></#if
            ></#list
        ></#if
    ><#if !existsSign
    ><#assign lm=lm+odred+"<BR>"+opis+"<BR>"+nap
        ><#if nap!="" && isbnBR!=""
        ><#assign lm=lm+"<BR>"
            ></#if
        ><#assign lm=lm+isbnBR+"<BR><BR>"+inv+"<BR>"+brID
        ></#if
    ><#assign lm=lm+"<BR>"
    ></#macro








><#macro getPredmetni
><#assign lp=""

    ><#assign out=""
    ><#assign predm=""
    ><#assign predm1=""
    ><#assign firstPO=true

    ><@odrednica/><@predmOdred1/><@brojID/><@glavniOpis/><@signatura/><#--
--><#if po!=""
    ><#assign lp=lp+"<B>"+po+"</B><BR>"+brID+"<BR>"+opis+"<BR><BR>"+sign+"<BR>"
        ></#if
    ></#macro




><#macro skraceniOpis
><#assign opisSkrac="&nbsp;&nbsp;&nbsp;"
    ><@autor1/><@naslov/><@deoKnj/><@naslovDeo/><@mesto/><@izdavac/><@godIzd/><#--
--><#if aut!=""
    ><#assign opisSkrac=opisSkrac+aut
        ><#if nas!="" ||deo!="" ||nasDeo!="" ||mes!=""||izd!="" ||god!=""
        ><#assign opisSkrac=opisSkrac+":&nbsp;"
            ></#if
        ></#if
    ><#if nas!=""
    ><#assign opisSkrac=opisSkrac+nas
        ><#if deo!="" ||nasDeo!="" ||mes!="" ||izd!="" ||god!=""
        ><#assign opisSkrac=opisSkrac+",&nbsp;"
            ></#if
        ></#if
    ><#if deo!=""
    ><#assign opisSkrac=opisSkrac+deo
        ><#if nasDeo!="" ||mes!="" ||izd!="" ||god!=""
        ><#assign opisSkrac=opisSkrac+".&nbsp;"
            ></#if
        ></#if
    ><#if nasDeo!=""
    ><#assign opisSkrac=opisSkrac+nasDeo
        ><#if mes!="" ||izd!="" ||god!=""
        ><#assign opisSkrac=opisSkrac+".&nbsp;-&nbsp;"
            ></#if
        ></#if
    ><#if mes!=""
    ><#assign opisSkrac=opisSkrac+mes
        ><#if izd!="" ||god!=""
        ><#assign opisSkrac=opisSkrac+":&nbsp;"
            ></#if
        ></#if
    ><#if izd!=""
    ><#assign opisSkrac=opisSkrac+izd
        ><#if god!=""
        ><#assign opisSkrac=opisSkrac+",&nbsp;"
            ></#if
        ></#if
    ><#if god!=""
    ><#assign opisSkrac=opisSkrac+god
        ></#if
    ><#if opisSkrac!="" && !opisSkrac?ends_with(".")
    ><#assign opisSkrac=opisSkrac+"."
        ></#if
    ></#macro



><#macro opisAnalitika
><#assign opisA=""
    ><#if f200?exists
    ><#list f200 as field
        ><#assign val=""
            ><#assign allSF="aefi"
            ><@field200 field/><#--
              --><#if field.ind1="1"
            ><#assign number=1
                ><@upperFirstN/><#--
             --></#if
            ><#assign opisA=opisA+"&nbsp;&nbsp;&nbsp;"+val

            ></#list
        ></#if
    ><#if recUtil.getSubfieldContent("856u")!=""
    ><#assign opisA = opisA+".-&nbsp;\x041D\x0430\x0447\x0438\x043D \x043F\x0440\x0438\x0441\x0442\x0443\x043F\x0430 (URL):"+ recUtil.getSubfieldContent("856u")
        ></#if
    ><#if recUtil.getSubfieldContent("300a")!=""
    ><#assign opisA = opisA+".-&nbsp;"+ recUtil.getSubfieldContent("300a")
        ></#if
    ><#if recUtil.getSubfieldContent("320a")!=""
    ><#assign opisA = opisA+".-&nbsp;"+ recUtil.getSubfieldContent("320a")
        ></#if
    ><#if f205?exists
    ><#list f205 as field
        ><#assign val=""
            ><#assign allSF="adfgb"
            ><@field205 field/><#--
      --><#if val!=""
            ><#if opisA?ends_with(".")
                ><#assign opisA=opisA+"&nbsp;-&nbsp;"
                    ><#else
                ><#assign opisA=opisA+".&nbsp;-&nbsp;"
                    ></#if
                ><#assign opisA=opisA+val
                ></#if
            ><#break
            ></#list
        ></#if

    ><#if f210?exists
    ><#list f210 as field
        ><#assign val=""
            ><#assign allSF="d"
            ><@field210 field/><#--
       --><#if val!=""
            ><#if opisA?ends_with(".")
                ><#assign opisA=opisA+"&nbsp;-&nbsp;"
                    ><#else
                ><#assign opisA=opisA+".&nbsp;-&nbsp;"
                    ></#if
                ><#assign opisA=opisA+val
                ></#if
            ><#break
            ></#list
        ></#if

    ><#assign pom225=""
    ><#if f225?exists
    ><#if opisA?ends_with(".")
        ><#assign pom225=opisA+"&nbsp;-&nbsp;"
            ><#else
        ><#assign pom225=opisA+".&nbsp;-&nbsp;"
            ></#if
        ><#assign i=1
        ><#list f225 as field
        ><#assign val=""
            ><#assign allSF="adefhivx"
            ><@field225 field/><#--
          --><#if val!=""
            ><#if i=1
                ><#assign opisA=pom225+"&nbsp;("+val+")&nbsp;"
                    ><#else
                ><#assign opisA=opisA+"&nbsp;("+val+")&nbsp;"
                    ></#if
                ></#if
            ><#assign i=i+1
            ></#list
        ></#if
    ><#if opisA!="" &&  !opisA?ends_with(".") && !opisA?ends_with(")&nbsp;")
    ><#assign opisA=opisA+"."
        ></#if
    ></#macro

><#macro analitika
><#assign an=""
    ><#if f423?exists
    ><#list f423 as fieldSec
        ><#assign val=""
            ><@field423Analitika fieldSec /><#--
              --><#if val!=""
            ><#assign an=an+val+"<BR><BR>"
                ></#if
            ></#list
        ><#elseif f469?exists
    ><#list f469 as fieldSec
        ><#assign val=""
            ><@field423Analitika fieldSec /><#--
              --><#if val!=""
            ><#assign an=an+val+"<BR><BR>"
                ></#if
            ></#list
        ><#elseif f474?exists
    ><#list f474 as fieldSec
        ><#assign val=""
            ><@field423Analitika fieldSec /><#--
              --><#if val!=""
            ><#assign an=an+val+"<BR><BR>"
                ></#if
            ></#list
        ></#if

    ></#macro
><#macro ostaleSignature
><#assign ostaleS=""
    ><#assign old=""

    ><#list primerci as primerak
    ><#assign val=primerak.signatura
        ><#if sve?index_of(","+val+",")=-1

        ><#if ostaleS!=""
            ><#assign ostaleS=ostaleS+",&nbsp;"
                ></#if
            ><#assign ostaleS=ostaleS+val
            ><#assign old=val
            ><#assign sve=sve+val+","

            ></#if
        ></#list
    ><#if ostaleS!=""
    ><#assign ostaleS="\x041E\x0441\x0442\x0430\x043B\x0435&nbsp;\x0441\x0438\x0433\x043D\x0430\x0442\x0443\x0440\x0435:&nbsp;"+ostaleS+"<BR>"
        ></#if
    ></#macro
><#macro inventar
><#assign inv=""
    ><#assign old=""
    ><#list primerci as primerak
    ><#assign val=primerak.signatura
        ><#assign help=val
        ><#if sign=val || (val="" && sign=old)
        ><#assign val=primerak.invBroj
            ><#if inv!=""
            ><#assign inv=inv+",&nbsp;"
                ></#if
            ><#assign inv=inv+val
            ></#if
        ><#if help!=""
        ><#assign old=help
            ></#if
        ></#list
    ></#macro

>