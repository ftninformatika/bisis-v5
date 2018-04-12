<#include "_base_bs.ftl"
><#assign brUDC=""
><#assign out=""
><#assign outB=""
><#assign odr=""
><@brojID/><@odrednica/><#--
--><#assign out=out+"<sig>"+brID+"</sig>"+odred

><@zaglavlje/><#--
--><#assign out=out+zag
><@glavniOpis/><#--

--><#assign out=out+opis+"<BR><BR>"

><@napomene/><@prilozi/><@isbn/><@drugiAutor/><#--
--><#assign out=out+nap
><#if nap!=""
 ><#assign out=out+"<BR>"
></#if
><#assign out=out+pril
><#if pril!=""
 ><#assign out=out+"<BR>"
></#if 
><#assign out=out+isbnBR
><#if isbnBR!=""
 ><#assign out=out+"<BR>"
></#if
><#if isbnBR!="" || nap!=""
 ><#assign out=out+"<BR>"
></#if
><#assign out=out+drugiA
><#if drugiA!=""
	><#assign out=out+"<BR><BR>"
></#if
><@brojUDC/><@predmOdred/><@signatura/><#--
--><#assign out=out+brUDC
><#if brUDC!=""
	><#assign out=out+"<BR><BR>"
></#if
><#assign out=out+po
><#if po!=""
	><#assign out=out+"<BR><BR>"
></#if
><#assign kraj="__________________________________________________________________________________________"
><#assign out="<BISIS>"+out+"\x0421\x438\x0433\x043D\x0430\x0442\x0443\x0440\x0435:&nbsp;"+sign+"<BR>"+kraj+"<BR></BISIS>"
>${out}