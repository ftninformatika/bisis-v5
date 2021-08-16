<#include "_base_pfso.ftl"
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
><#assign lm="<BISIS>"+brID+"<BR>"+sign+"<BR>"+odred+zag+opis+nap+pril+isbnBR+recUtil.getPredmetneOdrednice()+"<BR>"+brUDC+"<BR><BR>"+inv+"</BISIS>"
><#else
><#assign lm="<BISIS>"+odred+zag+"<BR>"+opis+nap+pril+isbnBR+recUtil.getPredmetneOdrednice()+"<BR><BR>"+brUDC+"<BR></BISIS>"
></#if
>${lm}
