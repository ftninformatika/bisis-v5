<#include "_koncept_gbbg.ftl"
><#assign udcList=false
><#assign arap=false
><#assign rim=false
><#assign predm=false
><#assign udc=false



><#assign udc=false





><#macro getMonografski
><#assign lm=""
><#assign inv=""
><#assign out=""
><#assign sign=""
><#assign sign1=","
><#assign firstLM=true
><#assign existsSign=false
><@odrednica/><@brojID/><@glavniOpis/><@napomene/><@isbn/><#--
--><#if nap!="" && isbnBR!=""
  ><#assign nap=nap+"<BR>"
  ></#if
  ><#if nap!="" || isbnBR!=""
     ><#assign opis=opis+"<BR><BR>"
  ></#if
><#if primerci?exists
      ><#assign brojac=0
      ><#list primerci as primerak                         
             ><#assign val=primerak.signatura
            ><#if sign1?index_of(","+val+",")=-1                   
                   ><#assign existsSign=true 
                   ><#assign sign1=sign1+val+","                   
                   ><#assign sign=val   
                   ><@inventar/><#--                
                   --><#if inv!=""
                      ><#assign inv="\x0418\x043D\x0432\x0435\x043D\x0442\x0430\x0440\x043D\x0438&nbsp;\x0431\x0440\x043E\x0458\x0435\x0432\x0438:&nbsp;"+inv
                   ></#if   
                       ><#assign sve=","+sign+","                    
                       ><@ostaleSignature/><#--
                       --><#if firstLM
                             ><#assign firstLM=false
                             ><#assign lm=lm+"<sig>"+brID+sign+"<BR><BR></sig>"+odred+opis+nap+isbnBR+"<BR><BR>"+ostaleS+"<B>"+inv+"</B>" 
                       ><#else
                             ><#assign lm=lm+"<BR><np>"+"<sig>"+brID+sign+"<BR><BR></sig>"+odred+opis+"<BR>"+nap+isbnBR+"<BR><BR>"+ostaleS+"<B>"+inv+"</B>"     
                       ></#if                       
             ></#if 
       ></#list  
></#if
><#if !existsSign
  ><#assign lm=lm+"<sig>"+brID+"</sig>"+odred+opis+"<BR>"+nap+isbnBR
></#if 
><#assign lm=lm+"<BR>" 
></#macro








><#macro getPredmetni
><#assign lp=""

><#assign out=""
><#assign predm=""
><#assign predm1=""
><#assign firstPO=true

><@odrednica/><@brojID/><@skraceniOpis/><@signatura/><#assign opis="\x0412\x0438\x0434\x0438:<BR>"+opisSkrac+"<BR><BR>"+sign+"<BR>"><#--
--><#if f600?exists
      ><#list f600 as field             
             ><#assign val=""
             ><#assign allSF="abcdf"
             ><@field600 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field600 field/><#--
                       --><#if val!=""                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign lp=lp+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign lp=lp+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if
><#if f601?exists
      ><#list f601 as field             
             ><#assign val=""
             ><#assign allSF="abcdefgh"
             ><@field601 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field601 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign lp=lp+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign lp=lp+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f602?exists
      ><#list f602 as field             
             ><#assign val=""
             ><#assign allSF="af"
             ><@field602 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field602 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign lp=lp+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign lp=lp+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f605?exists
      ><#list f605 as field             
             ><#assign val=""
             ><#assign allSF="ahiklmnq"
             ><@field605 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field605 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign lp=lp+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign lp=lp+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f606?exists
      ><#list f606 as field             
             ><#assign val=""
             ><#assign allSF="a"
             ><@field606 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field606 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign lp=lp+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign lp=lp+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f607?exists
      ><#list f607 as field             
             ><#assign val=""
             ><#assign allSF="a"
             ><@field606 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field606 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign lp=lp+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign lp=lp+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f608?exists
      ><#list f608 as field             
             ><#assign val=""
             ><#assign allSF="a"
             ><@field606 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field606 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign lp=lp+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign lp=lp+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f609?exists
      ><#list f609 as field             
             ><#assign val=""
             ><#assign allSF="a"
             ><@field606 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field606 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign lp=lp+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign lp=lp+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f610?exists
      ><#list f610 as field             
             ><#assign val=""
             ><#assign allSF="a"
             ><@field606 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field606 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign lp=lp+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign lp=lp+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
></#macro

><#macro getAnalitika
><#assign la=""

><#assign out=""
><#assign predm=""
><#assign predm1=""
><#assign firstPO=true

><@odrednica/><@brojID/><@skraceniOpis/><@signatura/><@analitika/><#assign opis=an+"\x0412\x0438\x0434\x0438:<BR>"+opisSkrac+"<BR><BR>"+sign+"<BR><BR>"><#--
--><#if f600?exists
      ><#list f600 as field             
             ><#assign val=""
             ><#assign allSF="abcdf"
             ><@field600 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field600 field/><#--
                       --><#if val!=""                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign la=la+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign la=la+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if
><#if f601?exists
      ><#list f601 as field             
             ><#assign val=""
             ><#assign allSF="abcdefgh"
             ><@field601 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field601 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign la=la+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign la=la+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f602?exists
      ><#list f602 as field             
             ><#assign val=""
             ><#assign allSF="af"
             ><@field602 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field602 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign la=la+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign la=la+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f605?exists
      ><#list f605 as field             
             ><#assign val=""
             ><#assign allSF="ahiklmnq"
             ><@field605 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field605 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign la=la+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign la=la+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f606?exists
      ><#list f606 as field             
             ><#assign val=""
             ><#assign allSF="a"
             ><@field606 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field606 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign la=la+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign la=la+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f607?exists
      ><#list f607 as field             
             ><#assign val=""
             ><#assign allSF="a"
             ><@field606 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field606 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign la=la+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign la=la+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f608?exists
      ><#list f608 as field             
             ><#assign val=""
             ><#assign allSF="a"
             ><@field606 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field606 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign la=la+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign la=la+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f609?exists
      ><#list f609 as field             
             ><#assign val=""
             ><#assign allSF="a"
             ><@field606 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field606 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign la=la+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign la=la+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
><#if f610?exists
      ><#list f610 as field             
             ><#assign val=""
             ><#assign allSF="a"
             ><@field606 field/><#-- 
             --><#if val!="" && val!=predm                   
                       ><#assign predm=val
                       ><#assign val=""
                       ><#assign allSF="xyzw"
                       ><@field606 field/><#--
                       --><#if val!=""
                             
                             ><#assign predm1=val+"<BR><BR>"
                       ></#if      
                       ><#if firstPO
                             ><#assign firstPO=false
                             ><#assign la=la+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis
                       ><#else
                             ><#assign la=la+"<np>"+"<sig><B>"+predm+"<BR></B>"+brID+"</sig>"+predm1+opis    
                       ></#if
             ></#if
             
       ></#list  
></#if 
></#macro


><#macro skraceniOpis
><#assign opisSkrac="&nbsp;&nbsp;&nbsp;"
><@autor1/><@naslov/><@deoKnj/><@naslovDeo/><@mesto/><@izdavac/><@godIzd/><#--
--><#if aut!=""
     ><#assign opisSkrac=opisSkrac+aut
     ><#if nas!="" ||deo!="" ||nasDeo!="" ||mes!=""||izd!="" ||god!="" 
          ><#assign opisSkrac=opisSkrac+":&nbsp;"
     ></#if
></#if 
><#if nas!=""
     ><#assign opisSkrac=opisSkrac+nas
     ><#if deo!="" ||nasDeo!="" ||mes!="" ||izd!="" ||god!=""  
          ><#assign opisSkrac=opisSkrac+",&nbsp;"
     ></#if
></#if     
><#if deo!=""
     ><#assign opisSkrac=opisSkrac+deo
     ><#if nasDeo!="" ||mes!="" ||izd!="" ||god!="" 
          ><#assign opisSkrac=opisSkrac+".&nbsp;"
     ></#if
></#if 
><#if nasDeo!=""
     ><#assign opisSkrac=opisSkrac+nasDeo
     ><#if mes!="" ||izd!="" ||god!="" 
          ><#assign opisSkrac=opisSkrac+".&nbsp;-&nbsp;"
     ></#if
></#if 
><#if mes!=""
     ><#assign opisSkrac=opisSkrac+mes
     ><#if izd!="" ||god!="" 
          ><#assign opisSkrac=opisSkrac+":&nbsp;"
     ></#if
></#if 
><#if izd!=""
     ><#assign opisSkrac=opisSkrac+izd
     ><#if god!="" 
          ><#assign opisSkrac=opisSkrac+",&nbsp;"
     ></#if
></#if 
><#if god!=""
     ><#assign opisSkrac=opisSkrac+god     
></#if 
><#if opisSkrac!="" && !opisSkrac?ends_with(".")
            ><#assign opisSkrac=opisSkrac+"."
  ></#if  
></#macro

><#macro analitika
><#assign an=""
><#if f423?exists 
      ><#list f423 as fieldSec
              ><#assign val=""             
              ><@field423Analitika fieldSec /><#--
              --><#if val!=""                  
                ><#assign an=an+val+"<BR><BR>"                
              ></#if              
      ></#list
><#elseif f469?exists 
      ><#list f469 as fieldSec
              ><#assign val=""             
              ><@field423Analitika fieldSec /><#--
              --><#if val!=""                  
                ><#assign an=an+val+"<BR><BR>"                
              ></#if                   
      ></#list 
><#elseif f474?exists 
      ><#list f474 as fieldSec
              ><#assign val=""             
              ><@field423Analitika fieldSec /><#--
              --><#if val!=""                  
                ><#assign an=an+val+"<BR><BR>"                
              ></#if                 
      ></#list                
></#if

></#macro
><#macro ostaleSignature
><#assign ostaleS=""
><#assign old=""

><#list primerci as primerak
    ><#assign val=primerak.signatura
    ><#if sve?index_of(","+val+",")=-1         
         
            ><#if ostaleS!=""
               ><#assign ostaleS=ostaleS+",&nbsp;"
            ></#if
            ><#assign ostaleS=ostaleS+val
            ><#assign old=val
            ><#assign sve=sve+val+","
         
    ></#if
></#list
><#if ostaleS!=""
   ><#assign ostaleS="\x041E\x0441\x0442\x0430\x043B\x0435&nbsp;\x0441\x0438\x0433\x043D\x0430\x0442\x0443\x0440\x0435:&nbsp;"+ostaleS+"<BR>"
></#if
></#macro
><#macro inventar
><#assign inv=""
><#list primerci as primerak
    ><#assign val=primerak.signatura
    ><#if sign=val && primerak.invBroj?exists        
            ><#assign val=primerak.invBroj
            ><#if inv!=""
               ><#assign inv=inv+",&nbsp;"
            ></#if
            ><#assign inv=inv+val 
    ></#if
></#list
></#macro
>