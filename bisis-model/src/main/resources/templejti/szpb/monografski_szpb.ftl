<#include "_base_szpb.ftl"
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
><@signatura/><@odrednica/><@zaglavlje/><@glavniOpis/><@napomene/><@prilozi/><@isbn/><@predmOdred/><@brojUDC/><@inventarni/>
<#assign lm="<BISIS>"+brID+"<BR>"+sign+"<BR>"+odred+zag+"<BR>"+opis+nap+pril+isbnBR+"<BR>"+po+brUDC+"<BR>"+inv+"</BISIS>">${lm}
