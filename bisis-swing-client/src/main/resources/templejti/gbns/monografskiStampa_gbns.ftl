<#include "_base_gbns.ftl"
><@odrednica/><@zaglavlje/><@glavniOpis/><@napomene/><@prilozi/><@isbn/>
<#assign out=odred+zag+"<BR>"+opis+nap+pril+isbnBR+"<BR>"
><#assign lm=""
><#assign sign=""
><#assign sign1=""
><#assign firstLM=true
><#assign existsSign=false
><#if primerci?exists
      ><#assign brojac=0
      ><#list primerci as primerak             
             ><#assign val=primerak.signatura
             ><#if val!="" && val!=sign1
                   ><#assign existsSign=true            
                   ><#assign word=val 
                   ><#assign sign1=val
                   ><#if brojac=brSignatura
                       ><#assign sign="<sig>"+sign+"</sig>"
                       ><#if firstLM
                             ><#assign firstLM=false                             
                             ><#assign lm=lm+sign+"<BR>"+out 
                       ><#else
                             ><#assign lm=lm+"<np><BR>"+sign+"<BR>"+out      
                       ></#if
                       ><#assign sign=""                       
                       ><#assign brojac=0
                   ></#if
                   ><#assign val=word                       
                   ><#assign sign=sign+val+"<BR>"
                   ><#assign brojac=brojac+1 
                   
             ></#if  
       ></#list      
       ><#if brojac!=0
                  ><#assign sign="<sig>"+sign+"</sig>"
                  ><#if firstLM
                      ><#assign first=false
                      ><#assign lm=lm+sign+"<BR>"+out 
                  ><#else
                      ><#assign lm=lm+"<np><BR>"+sign+"<BR>"+out      
                  ></#if       
       ></#if
></#if 
><#if !existsSign
    ><#assign lm=lm+out       
></#if


><#assign lm="<BISIS>"+lm+"</BISIS>">${lm}
