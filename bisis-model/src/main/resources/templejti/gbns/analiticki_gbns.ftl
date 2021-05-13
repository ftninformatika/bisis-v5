<#include "_base_gbns.ftl"
><@getAnalitika/><#assign la="<BISIS>"+la+"</BISIS>">${la}

><#macro getAnalitika
><#assign la=""
><#assign out=""
><#assign predm=""
><#assign predm1=""
><#assign firstPO=true

><@odrednica/><@brojID/><@opisAnalitika/><@signatura/><@fieldAnalitika/><@brojUDC/>
	<#assign la=recUtil.getOdrednicaAnalitika()+"<BR><BR>"+recUtil.getOpisanalitikaSerijske()+"<BR><BR>"+"U:&nbsp;"+recUtil.getMaticnaPublikacijaNS()+"<BR><BR>"+brUDC+"<BR>"+recUtil.getPredmetneOdrednice()
></#macro
>


