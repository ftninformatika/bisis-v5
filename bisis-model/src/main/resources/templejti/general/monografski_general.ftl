<#include "_base_general.ftl"
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
<#if saInventarnim
><#assign lm="<BISIS>"+brID+"<BR>"+sign+"<BR>"+odred+zag+"<BR>"+opis+nap+pril+isbnBR+"<BR>"+po+brUDC+"<BR>"+inv+"</BISIS>"
><#else
><#assign lm="<BISIS>"+odred+zag+"<BR>"+opis+nap+pril+isbnBR+"<BR>"+po+brUDC+"<BR></BISIS>"
></#if
>${lm}
