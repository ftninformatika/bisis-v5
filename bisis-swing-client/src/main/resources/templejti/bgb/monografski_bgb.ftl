<#include "_base_bgb.ftl">
<#assign brID="">
<#if f001?exists>
    <#list f001 as field>
        <#assign val="">
        <#assign allSF="e">
        <@field001 field/>
        <#if val!="">
            <#assign brID=val>
            </#if>
            </#list>
        </#if>
<@signatura/>
<@odrednica/>
<@zaglavlje/>
<@glavniOpis/>
<@napomene/>
<@prilozi/>
<@isbn/>
<@brojUDC/>
<@inventarni/>
 <#if saInventarnim>
     <#assign lm="<BISIS>"+brID+"<BR>"+sign+"<BR>"+odred+zag+"<BR>"+opis+nap+pril+isbnBR+"<BR>"+recUtil.getPredmetneOdrednice()+"<BR>"+brUDC+"<BR><BR>"+recUtil.getRaspodelaNSCirc()+recUtil.getBrojPrimerakaSvezakaNS()+"<BR><BR>"+inv+"</BISIS>">
 <#else>
     <#assign lm="<BISIS>"+odred+zag+"<BR>"+opis+nap+pril+isbnBR+"</BISIS>">
     </#if>${lm}
