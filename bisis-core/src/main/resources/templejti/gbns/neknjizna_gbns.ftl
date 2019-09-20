<#include "_base_gbns.ftl"
><#assign brID=""
><#if f001?exists 
         ><#list f001 as field             
                 ><#assign val=""
                 ><#assign allSF="e" 
                 ><@field001 field/><#-- 
                 --><#if val!=""
                      
                      ><#assign brID=val                     
                 ></#if     
         ></#list
></#if
><@signatura/>
 <@odrednica/>
 <@zaglavlje/>
 <@glavniOpis/>
 <@napomene/>
 <@prilozi/>
 <@isbn/> 
 <@brojUDC/>
 <@inventarni/>
 <#assign lm="<BISIS>"+sign+"<BR>"+recUtil.getOdrednicaAnalitika()+"<BR><BR>"+opis+nap+pril+isbnBR+"<BR><BR>"+recUtil.getPredmetneOdrednice()+"<BR><BR>"+brUDC+"<BR></BISIS>">${lm}