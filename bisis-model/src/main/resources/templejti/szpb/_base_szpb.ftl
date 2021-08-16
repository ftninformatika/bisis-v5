<#include "_koncept_szpb.ftl"
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
><#macro odrediBrojSigli
   ><#if sigla?starts_with("00")
      ><#assign s0=s0+1
   ><#elseif sigla?starts_with("01")
      ><#assign s1=s1+1
   ><#elseif sigla?starts_with("02")
      ><#assign s2=s2+1 
   ><#elseif sigla?starts_with("03")
      ><#assign s3=s3+1 
   ><#elseif sigla?starts_with("04")
      ><#assign s4=s4+1 
   ><#elseif sigla?starts_with("05")
      ><#assign s5=s5+1 
   ><#elseif sigla?starts_with("06")
      ><#assign s6=s6+1
   ><#elseif sigla?starts_with("07")
      ><#assign s7=s7+1  
   ><#elseif sigla?starts_with("08")
      ><#assign s8=s8+1  
   ><#elseif sigla?starts_with("09")
      ><#assign s9=s9+1 
   ><#elseif sigla?starts_with("10")
      ><#assign s10=s10+1
   ><#elseif sigla?starts_with("11")
      ><#assign s11=s11+1
   ><#elseif sigla?starts_with("12")
      ><#assign s12=s12+1 
   ><#elseif sigla?starts_with("13")
      ><#assign s13=s13+1 
   ><#elseif sigla?starts_with("14")
      ><#assign s14=s14+1 
   ><#elseif sigla?starts_with("15")
      ><#assign s15=s15+1 
   ><#elseif sigla?starts_with("16")
      ><#assign s16=s16+1
   ><#elseif sigla?starts_with("17")
      ><#assign s17=s17+1  
   ><#elseif sigla?starts_with("18")
      ><#assign s18=s18+1  
   ><#elseif sigla?starts_with("19")
      ><#assign s19=s19+1   
   ><#elseif sigla?starts_with("20")
      ><#assign s20=s20+1
   ><#elseif sigla?starts_with("21")
      ><#assign s21=s21+1
   ><#elseif sigla?starts_with("22")
      ><#assign s22=s22+1 
   ><#elseif sigla?starts_with("23")
      ><#assign s23=s23+1 
   ><#elseif sigla?starts_with("24")
      ><#assign s24=s24+1 
   ><#elseif sigla?starts_with("25")
      ><#assign s25=s25+1 
   ><#elseif sigla?starts_with("26")
      ><#assign s26=s26+1
   ><#elseif sigla?starts_with("27")
      ><#assign s27=s27+1  
   ><#elseif sigla?starts_with("28")
      ><#assign s28=s28+1  
   ><#elseif sigla?starts_with("29")
      ><#assign s29=s29+1  
   ><#elseif sigla?starts_with("30")
      ><#assign s30=s30+1
   ><#elseif sigla?starts_with("31")
      ><#assign s31=s31+1
   ><#elseif sigla?starts_with("32")
      ><#assign s32=s32+1 
   ><#elseif sigla?starts_with("33")
      ><#assign s33=s33+1 
   ><#else 
      ><#assign s34=s34+1 
   ></#if   
           
></#macro           
      
        
      
><#macro inventarni
 ><#assign s0=0
 ><#assign s1=0
 ><#assign s2=0
 ><#assign s3=0
 ><#assign s4=0
 ><#assign s5=0
 ><#assign s6=0
 ><#assign s7=0
 ><#assign s8=0
 ><#assign s9=0
 ><#assign s10=0
 ><#assign s11=0
 ><#assign s12=0
 ><#assign s13=0
 ><#assign s14=0
 ><#assign s15=0
 ><#assign s16=0
 ><#assign s17=0
 ><#assign s18=0
 ><#assign s19=0
 ><#assign s20=0
 ><#assign s21=0
 ><#assign s22=0
 ><#assign s23=0
 ><#assign s24=0
 ><#assign s25=0
 ><#assign s26=0
 ><#assign s27=0
 ><#assign s28=0
 ><#assign s29=0
 ><#assign s30=0
 ><#assign s31=0
 ><#assign s32=0
 ><#assign s33=0
 ><#assign s34=0
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
           ><#if inventar!=""
               ><#assign val=primerak.odeljenje
               ><#assign sigla=val
              ><#if sigla=""
                 ><#assign sigla=inventar
              ></#if   
              ><@odrediBrojSigli/><#--
          --></#if  
      ></#list     
 ></#if  
 ><#if inv!=""
    ><#assign sigle=""
    ><#if s0!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"0-"+s0?string
    ></#if 
    ><#if s1!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"1-"+s1?string
    ></#if 
    ><#if s2!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"2-"+s2?string
    ></#if 
    ><#if s3!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"3-"+s3?string
    ></#if 
    ><#if s4!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"4-"+s4?string
    ></#if 
    ><#if s5!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"5-"+s5?string
    ></#if 
    ><#if s6!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"6-"+s6?string
    ></#if 
    ><#if s7!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"7-"+s7?string
    ></#if 
    ><#if s8!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"8-"+s8?string
    ></#if 
    ><#if s9!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"9-"+s9?string
    ></#if 
    ><#if s10!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"10-"+s10?string
    ></#if 
    ><#if s11!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"11-"+s11?string
    ></#if 
    ><#if s12!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"12-"+s12?string
    ></#if 
    ><#if s13!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"13-"+s13?string
    ></#if 
    ><#if s14!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"14-"+s14?string
    ></#if 
    ><#if s15!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"15-"+s15?string
    ></#if 
    ><#if s16!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"16-"+s16?string
    ></#if 
    ><#if s17!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"17-"+s17?string
    ></#if 
    ><#if s18!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"18-"+s18?string
    ></#if 
    ><#if s19!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"19-"+s19?string
    ></#if 
    ><#if s20!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"20-"+s20?string
    ></#if 
    ><#if s21!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"21-"+s21?string
    ></#if 
    ><#if s22!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"22-"+s22?string
    ></#if 
    ><#if s23!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"23-"+s23?string
    ></#if 
    ><#if s24!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"24-"+s24?string
    ></#if 
    ><#if s25!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"25-"+s25?string
    ></#if 
    ><#if s26!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"26-"+s26?string
    ></#if 
    ><#if s27!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"27-"+s27?string
    ></#if 
    ><#if s28!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"28-"+s28?string
    ></#if 
    ><#if s29!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"29-"+s29?string
    ></#if 
    ><#if s30!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"30-"+s30?string
    ></#if 
    ><#if s31!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"31-"+s31?string
    ></#if 
    ><#if s32!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"32-"+s32?string
    ></#if 
    ><#if s33!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"33-"+s33?string
    ></#if 
    ><#if s34!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"34-"+s34?string
    ></#if    
    
 ></#if   
 
   ><#assign inv=inv+"<BR>"+sigle+"<BR>" 
   
></#macro

>