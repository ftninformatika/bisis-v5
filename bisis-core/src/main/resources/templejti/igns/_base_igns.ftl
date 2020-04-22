<#include "_koncept_igns.ftl"
><#macro signatura
 ><#assign sign=""
 ><#assign old=""
 ><#if primerci?exists               
      ><#list primerci as primerak 
           ><#assign val=primerak.signatura
           ><#if val!="" && old!=val
                 ><#assign old=val
                 ><#if sign!=""
                    ><#assign sign=sign+"<BR>"
                 ></#if   
                 ><#assign sign=sign+val
          ></#if       
      ></#list     
 ></#if  
 
></#macro

><#macro opisAnalitika
><#assign opisA=""
><#if f200?exists 
     ><#list f200 as field 
              ><#assign val=""
              ><#assign allSF="aefg"
              ><@field200 field/><#--
              --><#assign opisA=opisA+"&nbsp;&nbsp;&nbsp;"+val
              
    ></#list
></#if
><#if f205?exists  
  ><#list f205 as field
      ><#assign val=""
      ><#assign allSF="adfgb"
      ><@field205 field/><#--
      --><#if val!=""
                ><#if opisA?ends_with(".") 
                      ><#assign opisA=opisA+"&nbsp;-&nbsp;"
                ><#else
                      ><#assign opisA=opisA+".&nbsp;-&nbsp;"
                ></#if
                ><#assign opisA=opisA+val
      ></#if    
      ><#break
  ></#list
></#if
></#macro

><#macro oznakaUCasopisu
><#assign oznaka=""
><#if f210?exists 
  ><#list f210 as field
       ><#assign val=""
       ><#assign allSF="d"
       ><@field210 field/><#--
       --><#if val!=""                
            ><#assign godina="&nbsp;("+val+")&nbsp;"                
       ></#if 
       ><#break
  ></#list 
></#if

><#assign pom215=""  
><#if f215?exists           
      ><#list f215 as field
      ><#assign val=""
      ><#assign allSF="i"
      ><@field215 field/><#--
      --><#if val!="" 
      		><#assign oznaka=oznaka+"&nbsp;"+val
      	></#if     
      	><#assign oznaka=oznaka+godina       	
      	
      ><#assign val=""
      ><#assign allSF="h"
      ><@field215 field/><#--
      --><#if val!="" 
      		><#assign oznaka=oznaka+val
      	></#if     
      
      ><#assign val=""
      ><#assign allSF="g"
      ><@field215 field/><#--
      --><#if val!="" 
      		><#assign oznaka=oznaka+"&nbsp;"+val
      	></#if
      	
      	 ><#assign val=""
      ><#assign allSF="ac"
      ><@field215 field/><#--
      --><#if val!="" 
      		><#assign oznaka=oznaka+val
      	></#if
      	
      	
      ></#list
></#if
></#macro

><#macro inventarni 
 ><#assign inv=""
 ><#assign sigle=""  
 
 ><#if primerci?exists               
      ><#list primerci as primerak 
           ><#assign val=primerak.invBroj
           ><#assign inventar=val
           ><#if inv!=""
             ><#assign inv=inv+",&nbsp;"
           ></#if  
           ><#assign inv=inv+val 
           ><#assign val=primerak.status 
           ><#if val="\x010D"
             ><#assign inv=inv+"!\x010D"
           ><#elseif val="9"
             ><#assign inv=inv+"!R"
           ></#if  
            
                      
      ></#list     
 ></#if  
 
></#macro



>