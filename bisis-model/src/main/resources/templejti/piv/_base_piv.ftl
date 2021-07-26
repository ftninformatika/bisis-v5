<#include "_koncept_piv.ftl"

><#assign udcList=false


><#macro base
><#assign brUDC=""
><#assign out=""
><#assign outB=""

><@odrednica/><#--
--><#assign out=out+odred

><@zaglavlje/><#--
--><#assign out=out+zag
><@alone532/><#--
--><#assign out=out+a532
><@glavniOpis/><#--

--><#assign out=out+opis

><@napomene/><@prilozi/><@isbn/><@predmOdred1/><#--
--><#assign out=out+nap+pril+isbnBR


><#if !udcList

      ><@brojUDC/><#--
      --><#assign out=out+brUDC
></#if
><#if po!=""
    ><#assign out=out+"<BR>"+po
></#if    

><#if out=""
      ><#assign out=out+"ID="
><#else
      ><@brojID/><#--
      --><#if brID
              ><#assign out=out+"<BR>ID="
      ><#else
              ><#assign out=out+"<BR><BR>ID="
      ></#if
      ><#if f001?exists 
         ><#list f001 as field             
                 ><#assign val=""
                 ><#assign allSF="e" 
                 ><@field001 field/><#-- 
                 --><#if val!=""
                      
                      ><#assign out=out+val+"<BR>"                     
                 ></#if     
         ></#list
      ></#if
      
      
></#if

></#macro  

><#macro baseSednice
><#assign brUDC=""
><#assign out=""
><#assign outB=""


><@glavniOpis1/><#--

--><#assign out=out+opis

><@napomene/><@prilozi/><@isbn/><@predmOdred1/><#--
--><#assign out=out+nap+pril+isbnBR



      ><@brojUDC/><#--
      --><#assign out=out+brUDC

><#if po!=""
    ><#assign out=out+"<BR>"+po
></#if    

><#if out=""
      ><#assign out=out+"ID="
><#else
      ><@brojID/><#--
      --><#if brID
              ><#assign out=out+"<BR>ID="
      ><#else
              ><#assign out=out+"<BR><BR>ID="
      ></#if
      
      
      ><#assign out=out+docID?string("#")+"<BR>"
></#if

></#macro  



><#macro getMonografski
><#assign lm=""
><#assign inv=""
><#assign out=""
><#assign sign=""
><#assign sign1=""
><#assign firstLM=true
><#assign existsSign=false
><#assign old=","
><@base/><#--
--><#if primerci?exists
 ><#list primerci as primerak 	
 	><#assign val=primerak.signatura
 	><#if val!=""
 		><#assign sign = val
 		><#assign existsSign=true          
 	></#if
 	><#assign inv = inv+primerak.invBroj+"<BR>"
 ></#list
 ></#if
 ><#if existsSign
 ><#assign sign="<sig>"+sign+"</sig>"      
 ><#assign lm=lm+sign+"<BR>"+out+"<BR>"+inv  
 ><#else
   ><#assign lm=lm+out 
></#if  
></#macro


><#macro getSednice
><#assign lm=""
><#assign inv=""
><#assign out=""
><#assign sign=""
><#assign sign1=""
><#assign firstLM=true
><#assign existsSign=false
><#assign potR=""
><#assign old=","
><@baseSednice/><#--
--><#if primerci?exists
      ><#assign brojac=0
      ><#list primerci as primerak             
             ><#assign val=primerak.signatura
             ><#if val!="" && old?index_of(","+val+",")=-1
                  ><#assign old=old+val+","                             
                   ><#assign existsSign=true                        
                   ><#assign word=val 
                   ><#assign sign1=val
                   ><#if brojac=brSignatura                       
                       ><#assign sign="<sig>"+sign+"</sig>"
                       ><#if potR!=""
                         ><#assign potR=potR+"<BR>"
                       ></#if  
                       ><#if firstLM
                             ><#assign firstLM=false
                             ><#assign lm=lm+sign+out+potR+inv 
                       ><#else
                             ><#assign lm=lm+"<np><BR>"+sign+out+potR+inv      
                       ></#if
                       ><#assign sign=""
                       ><#assign inv=""
                       ><#assign brojac=0
                       ><#assign potR=""
                   ></#if
                   ><#assign val=word    
                   ><@rightAlign/><#--
                   --><#assign sign=sign+outB+val+"<BR>"
                   ><#assign brojac=brojac+1 
                   ><@inventar/><@polje996potpoljeR/><#--
             --></#if                  
       ></#list      
       ><#if brojac!=0
                  
                  ><#assign sign="<sig>"+sign+"</sig>"
                  ><#if firstLM
                      ><#assign first=false
                      ><#assign lm=lm+sign+out+potR+inv 
                  ><#else
                      ><#assign lm=lm+"<np><BR>"+sign+out+potR+inv      
                  ></#if       
       ></#if

           
></#if
><#if !existsSign
  ><#assign lm=lm+out 
></#if  
></#macro






><#macro getUDCListic
><#assign UDClis=""
><#assign prvi=true

><#if f675?exists
          ><#list f675 as field
                  ><#assign udcList=true
                  ><#assign udc=false
                  ><#assign val=""
                  ><#assign allSF="a"
                  ><@field010 field/><#--
                  --><#if val!=""
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if UDClis!=""
                                       ><#assign UDClis=UDClis+"<np>"+sListic
                               ><#else
                                       ><#assign UDClis=UDClis+sListic 
                               ></#if    
                 ></#if                    
           ></#list
></#if
><#assign udc=false 
><#assign udcList=false
></#macro    


             


><#macro getSerijski
><#assign ls=""
><#assign inv=""
><#assign out=""
><#assign sign=""
><#assign sign1=""
><#assign firstLS=true
><#assign existsSign=false
><#assign word=""
><#assign old=","
><@odrednica/><@zaglavljeSer/><@glavniOpis/><@napomeneSerijska/><@issn/><@specGodista/><@brojUDCSerijska/><#--
--><#if godine?exists
 ><#list godine as godina
 	><#assign val=godina.signatura
 	><#if val!=""
 		><#assign sign = val
 		><#assign existsSign=true          
 	></#if
 	><#assign inv = inv+godina.invBroj+"<BR>"
 ></#list
></#if                                                                                                          
      ><#assign ls=ls+zagS+odred+opis+napS+issnBR
      ><#if issnBR!=""
           ><#assign ls=ls+"<BR>"
      ></#if                                        
      ><#assign ls=ls+brUDC                         
      ><#if !ls?ends_with("<BR>")
             ><#assign ls=ls+"<BR>"
       ></#if  
       ><#assign ls=ls+"<BR>"+inv                                                          
     ><@rightAlign/><#--
     --><#assign sign=sign+outB+"<BR>"       
        ><#assign ls = sign+ls                  
     ><@inventarSerijska/>         
<#if !existsSign
    ><#assign ls="<sig><B>"+nemaSignature+"</B><BR></sig><BR>"+zagS+odred+opis+napS+issnBR+"<BR>"           
></#if



></#macro




>