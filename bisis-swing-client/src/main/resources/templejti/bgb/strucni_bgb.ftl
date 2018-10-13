<#include "_base_bgb.ftl"
><#assign brUDC=""
><#assign out=""
><@brojID/><@signatura/><@odrednica/><@brojUDCPrvi/><#--
--><#assign odred=odred
><#assign out=out+"<sig><B>"+brUDCprvi+"</B><BR>"+brID+"<BR></sig>"

><@skraceniOpis/><#--

--><#assign out=out+opisSkrac+"<BR>"

><@brojUDCOstali/><#--
--><#assign out=out+"<BR>"+brUDCostali
><#if brUDCostali!=""
	><#assign out=out+"<BR><BR>"
></#if
><#assign out=out+sign

><#assign out="<BISIS>"+out+"<BR></BISIS>"
>${out}