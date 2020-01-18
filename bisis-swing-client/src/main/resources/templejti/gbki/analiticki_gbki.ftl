<#include "_base_gbki.ftl"
><@getAnalitika/><#assign la="<BISIS>"+la+"</BISIS>"/>${la}

<#macro getAnalitika
><#assign la=""
><#assign out=""
><#assign predm=""
><#assign predm1=""

><@odrednica/><@glavniOpis/><@brojUDC/><@napomene/><@analitikaPripada/>    
	<#assign la=odred+"<BR>"+opis+nap+"<BR>"+brUDC+"<BR>"+pripada	
></#macro
>






