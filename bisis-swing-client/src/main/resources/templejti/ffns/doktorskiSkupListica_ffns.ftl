<#include "_base_ffns.ftl"

><#assign noTes=false
><#assign glavni=false
><#assign serijskaF=false
><#assign doktorska=true
   
><#assign skup=""
      ><@getMonografski/><@getUDCListic/><@getPredmListic/><@getAutorski/><#--
      --><#assign skup=skup+lm
      ><#if UDClis!=""
            ><#assign skup=skup+"<np>"
      ></#if
      ><#assign skup=skup+UDClis
      ><#if predmLis!=""
            ><#assign skup=skup+"<np>"
      ></#if
      ><#assign skup=skup+predmLis
      
      ><#if autorLis!=""
            ><#assign skup=skup+"<np>"
      ></#if
      ><#assign skup=skup+autorLis
><#assign skup="<BISIS>"+skup+"</BISIS>">${skup}
 