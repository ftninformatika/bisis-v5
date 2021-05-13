<#include "_base_ftn.ftl"
><#include "_koncept_ftn.ftl"
><#include "_polja_ftn.ftl"
><#assign noTes=false
><#assign glavni=false
><#assign serijskaF=true
><#assign doktorska=false

><@getSerijski/><#assign ls="<BISIS>"+ls+"</BISIS>">${ls}

<#--

iz ovih operacija dobijamo sledece parametre
odred (@odrednica iz koncept_gbns) - pravi odrednicu (ono sto prvo pise u zapisu)po nekom pravilu
opis (@glavniOpis iz koncept_gbns) - tekst koji dolazi posle oderednice sa separtaorima izmeðu podataka iz 200, 205, ...
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
 <@napomene430i440/>  
 <@specGodista/>
 <@brojUDC/>
 <@predmOdred/>
 <#if godine?exists
 ><#list godine as godina
 	><#assign val=godina.signatura
 	><#if val!=""
 		><#assign sign = val
 	></#if
 	><#assign inv = inv+godina.invBroj+"<BR>"
 ></#list
></#if
 
><#assign ls=zagS+"<sig>"+sign+"</sig><BR><BR>"+odred+opis+napS
  ><#if recUtil.getISSN()!=""
 	><#if nap430i440!="" 
 		><#assign ls = ls+"<BR>"+nap430i440 +"&nbsp;=&nbsp;"+recUtil.getISSN()
 	><#else
 			><#assign ls = ls+"<BR>"+recUtil.getISSN()
     ></#if
   ><#else
   		><#if nap430i440!="" 
 			><#assign ls = ls+"<BR>"+nap430i440
 		></#if   	   			
  	></#if 
  	><#assign ls = ls + "<BR><BR>" + po  
	><#assign ls = ls + "<BR>"+brUDC+"<BR>"+inv  
></#macro 
 
><#macro napomene430i440
	><#assign nap430i440=""
    ><#if recUtil.getSubfieldContent("430a")!=""
	 ><#assign nap430i440 = nap430i440 + "Je nastavak:&nbsp;"+recUtil.getSubfieldContent("430a")
	></#if
	><#if recUtil.getSubfieldContent("440a")!=""
	 ><#assign nap430i440 = nap430i440 + "Nastavlja se kao:&nbsp;"+recUtil.getSubfieldContent("440a")
	></#if	 
></#macro

>



