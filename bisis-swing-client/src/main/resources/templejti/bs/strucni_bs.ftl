<#include "_base_bs.ftl"
><#assign brUDC=""
><#assign out=""
><@brojID/><@signatura/><@odrednica/><@brojUDCPrvi/><#--
--><#assign odred=odred
><#assign out=out+"<sig><B>"+brUDCprvi+"</B><BR>"+brID+"<BR></sig>"

><@glavniOpis/><#--

--><#assign out=out+opis+"<BR>"

><@brojUDCOstali/><#--
--><#assign out=out+"<BR>"+brUDCostali
><#if brUDCostali!=""
	><#assign out=out+"<BR><BR>"
></#if
><#assign out=out+sign
><#if out!=""
     ><#assign out=out+"<BR>__________________________________________________________________________________________"
></#if     
><#assign out="<BISIS>"+out+"<BR></BISIS>"
>${out}