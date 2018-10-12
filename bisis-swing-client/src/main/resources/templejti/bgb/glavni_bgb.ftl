<#include "_base_bgb.ftl">
<#assign brUDC="">
<#assign out="">
<#assign outB="">
<@brojID/>
<@odrednica/>
<#assign out=out+"<sig>"+brID+"</sig>"+odred>
<@zaglavlje/>
<#assign out=out+zag>
<@glavniOpis/>
<#assign out=out+opis+"<BR><BR>">
<@napomene/>
<@isbn/>
<@drugiAutor/>
<#assign out=out+nap>
<#if nap!="">
    <#assign out=out+"<BR>">
</#if>
<#assign out=out+isbnBR>
<#if isbnBR!="">
    <#assign out=out+"<BR>">
</#if>
<#if isbnBR!="" || nap!="">
    <#assign out=out+"<BR>">
</#if>
<#assign out=out+drugiA>
<#if drugiA!="">
    <#assign out=out+"<BR><BR>">
</#if>
<@brojUDC/>
<@predmOdred/>
<@signatura/>
<#assign out=out+brUDC>
<#if brUDC!="">
    <#assign out=out+"<BR><BR>">
</#if>
<#assign out=out+po>
<#if po!="">
    <#assign out=out+"<BR><BR>">
</#if>
<#assign kraj="__________________________________________________________________________________________">
<#assign out="<BISIS>"+out+"Signatura:&nbsp;"+sign+"<BR>"+kraj+"<BR></BISIS>">
${out}