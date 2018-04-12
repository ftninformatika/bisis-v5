	<#include "_base_bs.ftl"
><#include "_koncept_bs.ftl"
><#include "_polja_bs.ftl"
><#assign noTes=false
><#assign glavni=false
><#assign serijskaF=true
><#assign doktorska=false

><@getSerijski/><#assign ls="<BISIS>"+ls+"</BISIS>">${ls}

<#--

iz ovih operacija dobijamo sledece parametre
odred (@odrednica iz koncept_gbns) - pravi odrednicu (ono sto prvo pise u zapisu)po nekom pravilu
opis (@glavniOpis iz koncept_gbns) - tekst koji dolazi posle oderednice sa separtaorima izme�u podataka iz 200, 205, ...
napS (@napomeneSerijska iz koncept_gbns) - za sada uzima samo iz 300 i 326
issnBR (@issn iz koncept_gbns) - issn broj iz 011


-->

><#macro getSerijski
><#assign ls=""
><#assign inv=""
><#assign out=""
><#assign sign=""
><#assign sign1=""
><@odrednica/>
 <@zaglavljeSer/>
 <@glavniOpis/>
 <@napomeneSerijska/> 
 <@specGodista/>
 <@brojUDC/>
 <#list godine as godina
 	><#assign val=godina.signatura
 	><#if val!=""
 		><#assign sign = val
 	></#if
 	><#assign inv = inv+godina.invBroj+"<BR>"
 ></#list
 
><#assign ls=zagS+"<sig>"+sign+"</sig><BR><BR>"+odred+opis+napS
  ><#if recUtil.getISSN()!="" 	
 		><#assign ls = ls+"<BR>"+recUtil.getISSN()+"&nbsp;=&nbsp;"+recUtil.getSubfieldContent("200a")     
   ><#else   		
	><#assign ls = ls + "<BR><BR>"+brUDC+"<BR>" 
	></#if 
></#macro 
>
 
