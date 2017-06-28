<#include "_base_szpb.ftl"
><#macro obr
   ><#assign obradjivac=""
   ><#if f999?exists               
      ><#list f999 as field 
           ><#assign val=""
           ><#assign allSF="a"     
           ><@field010 field/><#-- 
           --><#if val!=""
               ><#if obradjivac!=""
                  ><#assign obradjivac=obradjivac+"<BR>"
               ></#if
               ><#assign obradjivac=obradjivac+val
           ></#if       
      ></#list     
  ></#if 
></#macro  
><#macro datum
 ><#assign dat=""
 ><#if f998?exists               
      ><#list f998 as field 
           ><#assign val=""
           ><#assign allSF="a"     
           ><@field010 field/><#-- 
           --><#if val!=""
               ><#if dat!=""
                  ><#assign dat=dat+"<BR>"
               ></#if
               ><#assign dat=dat+val
           ></#if       
      ></#list     
 ></#if 
></#macro
><#macro autor
 ><#assign val=""
 ><#if f700?exists               
      ><#list f700 as field 
           ><#assign allSF="ab"     
           ><@field700 field/><#-- 
           --><#break
      ></#list     
 ></#if
></#macro       
><#macro naslov
 ><#assign val=""
 ><#if f200?exists               
      ><#list f200 as field 
           ><#assign allSF="af"     
           ><@field200 field/><#-- 
           --><#break
      ></#list     
 ></#if   
  
></#macro
><#macro izdavac
 ><#assign val=""
 ><#if f210?exists               
      ><#list f210 as field 
           ><#assign allSF="c"     
           ><@field210 field/><#-- 
           --><#break
      ></#list     
 ></#if  
 ><#assign open=-1
 ><#list val?split("(") as x
        ><#assign open=open+1
 ></#list  
 ><#assign close=-1
 ><#list val?split(")") as x
        ><#assign close=close+1
 ></#list  
 ><#if (open>close)
  ><#assign val=val+")"
 ></#if
></#macro 
><#macro godIzd
 ><#assign val=""
 ><#if f210?exists               
      ><#list f210 as field 
           ><#assign allSF="d"     
           ><@field210 field/><#-- 
           --><#break
      ></#list     
 ></#if 
 ><#assign open=-1
 ><#list val?split("(") as x
        ><#assign open=open+1
 ></#list  
 ><#assign close=-1
 ><#list val?split(")") as x
        ><#assign close=close+1
 ></#list  
 ><#if (open>close)
  ><#assign val=val+")"
 ></#if
></#macro
><#macro izdanje
 ><#assign val=""
 ><#if f205?exists               
      ><#list f205 as field 
           ><#assign allSF="a"     
           ><@field205 field/><#-- 
           --><#break
      ></#list     
 ></#if  
></#macro
><#macro UDC
   ><#assign udc=""
   ><#if f675?exists               
      ><#list f675 as field 
           ><#assign val=""
           ><#assign allSF="a"     
           ><@field010 field/><#-- 
           --><#if val!=""
               ><#if udc!=""
                  ><#assign udc=udc+"<BR>"
               ></#if
               ><#assign udc=udc+val
           ></#if       
      ></#list     
  ></#if 
></#macro  
><#macro inventar
   ><#assign inv1=""
   ><#if primerci?exists
       ><#list primerci as primerak
           ><#assign inv1=inv1+"<TR>"
           ><#assign val=primerak.invBroj
           ><#assign inv1=inv1+"<TD>"+val+"</TD>"
           ><#assign val=primerak.datInv
           ><#assign inv1=inv1+"<TD>"+val+"</TD>"
           ><#assign val=primerak.povez
           ><#assign inv1=inv1+"<TD>"+val+"</TD>"
           ><#assign val=primerak.nacinNabavke
           ><#assign inv1=inv1+"<TD>"+val+"</TD>"
           ><#assign val=primerak.cena
           ><#assign inv1=inv1+"<TD>"+val+"</TD>"
           ><#assign val=primerak.napomene
           ><#assign inv1=inv1+"<TD>"+val+"</TD>"
           ><#assign val=primerak.status
           ><#assign inv1=inv1+"<TD>"+val+"</TD>"           
           ><#assign inv1=inv1+"</TR>"
       ></#list    
   ></#if
   
></#macro 
><#macro lokacijskiPodaci
   ><#assign lok=""
   ><#if f998?exists               
      ><#list f998 as field 
           ><#assign val=""
           ><#assign allSF="a"     
           ><@field998 field/><#-- 
           --><#if val!=""
               ><#if lok!=""
                  ><#assign lok=lok+"<BR>"
               ></#if
               ><#assign lok=lok+val
           ></#if       
      ></#list     
  ></#if 
></#macro       
><#assign inv="Broj zapisa (MFN)="
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
><#assign inv=inv+brID
><#assign inv=inv+"<BR><TABLE cellpadding=\"0\" cellspacing=\"0\" ><TR><TD>&nbsp;Obra\x0111iva\x010D i datum:&nbsp;&nbsp;&nbsp;</TD>"
><@obr/><@datum/>
<#if obradjivac!="" && dat!=""
   ><#assign dat="-"+dat
></#if   
><#assign inv=inv+"<TD>"+obradjivac+dat+"</TD></TR><TR><TD valign=top>&nbsp;Autori:&nbsp;&nbsp;&nbsp;</TD><TD>"
><@autor/>
<#assign inv=inv+val+"</TD></TR><TR><TD valign=top>&nbsp;Naslov i odgovornost:&nbsp;&nbsp;&nbsp;</TD><TD>"
><@naslov/>
<#assign inv=inv+val+"</TD></TR><TR><TD valign=top>&nbsp;Izadava\x010D:&nbsp;&nbsp;&nbsp;</TD><TD>"
><@izdavac/>
<#assign inv=inv+val+"</TD></TR><TR><TD valign=top>&nbsp;Godina izdavanja:&nbsp;&nbsp;&nbsp;</TD><TD>"
><@godIzd/>
<#assign inv=inv+val+"</TD></TR><TR><TD valign=top>&nbsp;Izdanje:&nbsp;&nbsp;&nbsp;</TD><TD>"
><@izdanje/>
<#assign inv=inv+val+"</TD></TR><TR><TD valign=top>&nbsp;Signatura:&nbsp;&nbsp;&nbsp;</TD><TD>"
><@signatura/>
<#assign inv=inv+sign+"</TD></TR><TR><TD valign=top>&nbsp;UDK:&nbsp;&nbsp;&nbsp;</TD><TD>"
><@UDC/>
<#assign inv=inv+udc+"</TD></TR>"
><#assign inv=inv+"</TABLE>INVENTAR:"
><@inventar/>
<#assign inv=inv+
"<TABLE  cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse\" bordercolor=\"#111111\" width=\"100%\" id=\"AutoNumber1\" ><TR><TH align=left>Inv. br.</TH><TH align=left>Dat. inv.</TH><TH align=left>Vrsta pov.</TH><TH align=left>Na\x010Din nab.</TH><TH align=left>Cena</TH><TH align=left>Napomena</TH><TH align=left>Status</TH></TR>"
+inv1+"</TABLE>&nbsp;<TABLE cellpadding=\"0\" cellspacing=\"0\" ><TR><TD valign=top>&nbsp;Lokacijski podaci:&nbsp;&nbsp;&nbsp;</TD><TD>"
><@lokacijskiPodaci/>
<#assign inv=inv+lok+"</TD></TR></TABLE><BR>"
>${inv}