<#include "_base_bs.ftl"
><#assign lm=""
><#assign inv=""
><#assign out=""
><#assign sign=""
><#assign sign1=","
><#assign firstLM=true
><#assign existsSign=false
><@signatura1/><@odrednica/><@brojID/><@glavniOpis/><@napomene/><@prilozi/><@isbn/><@inventarni/><#--
--><#assign opis=opis+"<BR>"
  
  ><#if nap!="" 
  ><#assign nap=nap+"<BR><BR>"
  ></#if 
  ><#if pril!="" 
  ><#assign pril=pril+"<BR><BR>"
  ></#if 
  ><#if isbnBR!=""    
    ><#assign isbnBR=isbnBR+"<BR><BR>"
  ></#if  
  ><#assign out1="<sig>"+sign+"<BR><BR></sig>"
   
  ><#assign out2="<BR>"+opis+"<BR>"+nap+pril+isbnBR+inv+"<BR>"+brID
                                 
><#if f700?exists
      ><#assign brojac=0
      ><#list f700 as field                         
             ><#assign val=""
             ><#assign allSF="ab"
             ><@field700 field/><#-- 
             --><@toUpperFirst/><#--
             --><#if val!=""  
                   ><#assign odred="<odr><B>"+val+"</B><BR></odr>"                
                   ><#if firstLM
                             ><#assign firstLM=false
                             ><#assign out=out+out1+odred+out2 
                       ><#else
                             ><#assign out=out+"<BR><np>"+out1+odred+out2 
                       ></#if                       
             ></#if 
       ></#list
       ><#if f701?exists
         ><#assign brojac=0
         ><#list f701 as field                         
             ><#assign val=""
             ><#assign allSF="ab"
             ><@field700 field/><#-- 
             --><@toUpperFirst/><#--
             --><#if val!=""  
                   ><#assign odred="<odr><B>"+val+"</B><BR></odr>"                
                   ><#if firstLM
                             ><#assign firstLM=false
                             ><#assign out=out+out1+odred+out2 
                       ><#else
                             ><#assign out=out+"<BR><np>"+out1+odred+out2 
                       ></#if                       
             ></#if 
         ></#list  
         
></#if

><#else
   ><#assign out=out1+odred+out2
></#if
><#assign out="<BISIS>"+out+"</BISIS>"
>${out}