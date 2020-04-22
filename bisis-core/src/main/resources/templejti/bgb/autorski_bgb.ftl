<#include "_base_bgb.ftl"
><#assign brUDC3=""
><#assign out=""
><@brojID/><@signaturaX/><@odrednica/><#--
--><#assign out=out+"<sig>"+signX+"<BR>"+brID+"</sig>"+odred

><@zaglavlje/><#--
--><#assign out=out+zag
><@glavniOpis/><#--

--><#assign out=out+opis+"<BR><BR>"

><@napomene/><@isbn/><@drugiAutor/><#--
--><#assign out=out+nap
><#if nap!=""
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
><@brojUDC3/><#--
--><#assign out=out+brUDC3

><#assign out="<BISIS>"+out+"<BR><BR></BISIS>"
>${out}