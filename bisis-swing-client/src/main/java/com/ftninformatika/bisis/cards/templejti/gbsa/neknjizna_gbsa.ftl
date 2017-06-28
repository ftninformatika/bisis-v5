<#include "_base_gbsa.ftl"
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
 <@napomeneNeknjizna/>
 <@prilozi/>
 <@isbn/> 
 <@brojUDC/>
 <@inventarni/>
 <#assign lm="<BISIS><BR>"+odred+"<BR>"+opis+"<BR><BR>"+nap+pril+isbnBR+"<BR><BR>"+recUtil.getPredmetneOdrednice()+"<BR><BR>"+brUDC+"<BR></BISIS>">${lm}