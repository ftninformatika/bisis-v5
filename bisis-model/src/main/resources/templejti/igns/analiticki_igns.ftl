<#include "_base_igns.ftl"
><@getAnalitika/><#assign la="<BISIS>"+la+"</BISIS>">${la}

><#macro getAnalitika
><#assign la=""
><#assign out=""
><#assign predm=""
><#assign predm1=""
><#assign firstPO=true

><@odrednica/><@brojID/><@opisAnalitika/><@brojUDC/><@fieldAnalitika/><@brojUDC/><@oznakaUCasopisu/><@napomene/>    
	><#assign la=la+odred+"<BR>"+opisA+"<BR><BR>"+"&nbsp;&nbsp;&nbsp;U:&nbsp;"+recUtil.getMaticnaPublikacijaMR()+oznaka+nap+"<BR><BR>"+brUDC+"<BR>RN="+recUtil.getRecordRN()+"<BR>"	
></#macro
>






