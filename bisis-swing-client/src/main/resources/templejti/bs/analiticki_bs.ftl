<#include "_base_bs.ftl"
>
><#macro getAnalitika
><#assign la=""
><#assign out=""
><#assign predm=""
><#assign predm1=""
><#assign firstPO=true
><@odrednica/><@brojID/><@opisAnalitika/><@signatura/><@fieldAnalitika/><@brojUDC/><#--

--><#assign la=la+recUtil.getMaticnaPublikacijaMRSignatura()+"<BR><BR>"+odred+opisA+"<BR><BR>\x0423:&nbsp;"+recUtil.getMaticnaPublikacijaMRSabac()+"<BR><BR>"+brUDC
></#macro

><@getAnalitika/><#assign la="<BISIS>"+la+"</BISIS>">${la}