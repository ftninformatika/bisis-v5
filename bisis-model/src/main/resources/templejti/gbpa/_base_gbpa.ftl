<#include "_koncept_gbpa.ftl"
><#--Isti kao base biblioteke Filozofskog fakulteta u Novom Sadu
--><#assign udcList=false
><#assign arap=false
><#assign rim=false
><#assign predm=false
><#assign udc=false

><#macro blank 
><#assign ost10=duz%10
><#assign ost100=duz%100-ost10
><#assign brB=ost10
><#assign outB=""


><#if ost10=1
       ><#assign outB=outB+"<sp>"
><#elseif ost10=2
       ><#assign outB=outB+"<sp><sp>"
><#elseif ost10=3
       ><#assign outB=outB+"<sp><sp><sp>"
><#elseif ost10=4
       ><#assign outB=outB+"<sp><sp><sp><sp>"
><#elseif ost10=5
       ><#assign outB=outB+"<sp><sp><sp><sp><sp>"
><#elseif ost10=6
       ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp>"
><#elseif ost10=7
       ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost10=8
       ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost10=9
       ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp>"
></#if
><#if ost100=10
       ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
        
><#elseif ost100=20
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=30 
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=40
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=50 
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=60 
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=70 
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=80 
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
><#elseif ost100=90      
         ><#assign outB=outB+"<sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
></#if
></#macro




><#macro rightAlign 

><#assign desno=""
><#assign rec=""
><#assign first=true
><#list word?split("&nbsp;") as x
     ><#if first
         ><#assign rec=rec+x
         ><#assign first=false
     ><#else
         ><#assign rec=rec+x+" "
     ></#if  
></#list
><#assign len=rec?length

><#assign duz=brk-len



><@blank/><#--

--></#macro










><#assign udc=false


><#macro base
><#assign brUDC=""
><#assign out=""
><#assign outB=""

><@odrednica/><#--
--><#assign out=out+odred
><#if doktorska
       ><#assign out=out?replace("<BR></odr>","<DR><BR></DR></odr>")
       ><@dsTitula/><#--
--><#assign out=out+dsTit+"<BR>"
></#if
><@zaglavlje/><#--
--><#assign out=out+zag
><@alone532/><#--
--><#assign out=out+a532
><@glavniOpis/><#--

--><#assign out=out+opis
><#if doktorska
      ><@dsOdbrana/><@dsPromocija/><#-- 
      --><#assign out=out+dsOd
      ><#if dsProm!=""
               ><#assign duz=brk-dsOd?length+8-dsProm?length
               ><#if dsOd?index_of("&nbsp;")!=-1
                    ><#assign duz=duz+5
               ></#if
               ><#if dsProm?index_of("&nbsp;")!=-1
                    ><#assign duz=duz+5
               ></#if
               
               ><@blank/><#--
               --><#assign out=out+outB+dsProm+"<BR>"
      ></#if
      
      ><#if dsProm="" && dsOd=""
               ><#assign out=out+"<BR>"
      ></#if
      ><@dsKomisija/><#--
      --><#assign out=out+dsKom
></#if
><@napomene/><@prilozi/><@isbn/><#--
--><#assign out=out+nap+pril+isbnBR
><#if !noTes

      ><@arapTrejsing/><@rimTrejsing/><@predmTrejsing/><#--
      --><#assign out=out+arapT+rimT+predmT
></#if

><#if !udcList

      ><@brojUDC/><#--
      --><#assign out=out+brUDC
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


><#macro getSporedniListic  
><#assign sign=""
><#assign sign1=""
><#assign sListic=""
><#assign inv=""
><#assign out=""
><@odrednica/><@zaglavlje/><@alone532/><#--
--><@base/><#--
--><#assign firstSpored=true
   ><#if primerci?exists
      ><#assign brojac=0
      ><#list primerci as primerak             
                   ><#assign val=primerak.signatura
                   ><#if val!="" && sign1!=val
                             ><#assign sign1=val 
                             ><#assign word=val
                             ><#if brojac=brSignatura
                                  ><#assign sign="<sig>"+sign+"</sig>"
                                  ><#if zag="" && odred="" && a532="" 
                                        ><#assign sOdrednica=sOdrednica+"<BR>"
                                  ></#if
                                  ><#if firstSpored
                                       ><#assign firstSpored=false
                                       ><#assign sListic=sListic+sign+"<BR>"+"<sodr><B>"+sOdrednica+"</B><BR></sodr>"+out+inv 
                                  ><#else
                                       ><#assign sListic=sListic+"<np>"+sign+"<BR>"+"<sodr><B>"+sOdrednica+"</B><BR></sodr>"+out+inv
                                  ></#if
                                  ><#assign sign=""
                                  ><#assign inv=""
                                  ><#assign brojac=0
                             ></#if
                             ><#assign val=word
                             ><@rightAlign/><#--
                             --><#assign sign=sign+outB+val+"<BR>"
                             ><#assign brojac=brojac+1
                   ></#if
                   ><#assign val=primerci.invBroj
                   ><#if val!="" 
                              ><#assign word=val
                              ><@rightAlign/><#--
                              --><#assign inv=inv+outB+val+"<BR>"
                   ></#if   
       ></#list
       ><#if brojac!=0
                  ><#assign sign="<sig>"+sign+"</sig>"
                  ><#if zag="" && odred="" && a532="" 
                      ><#assign sOdrednica=sOdrednica+"<BR>"
                  ></#if
                  ><#if firstSpored
                      ><#assign first=false
                      ><#assign sListic=sListic+sign+"<BR>"+"<sodr><B>"+sOdrednica+"</B><BR></sodr>"+out+inv 
                  ><#else
                      ><#assign sListic=sListic+"<np>"+sign+"<BR>"+"<sodr><B>"+sOdrednica+"</B><BR></sodr>"+out+inv
                  ></#if
       
       ></#if
><#else
    ><#assign sListic=sListic+"<sodr><B>"+sOdrednica+"</B><BR></sodr>"+out       
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
><@base/><#--
--><#if primerci?exists
      ><#assign brojac=0
      ><#list primerci as primerak             
             ><#assign val=primerak.signatura
             ><#if val!="" && val!=sign1
                   ><#assign existsSign=true                        
                   ><#assign word=val 
                   ><#assign sign1=val
                   ><#if brojac=brSignatura
                       ><#if zag="" && odred="" && a532="" 
                              ><#assign sign=sign+"<BR>"
                       ></#if
                       ><#assign sign="<sig>"+sign+"</sig>"
                       ><#if firstLM
                             ><#assign firstLM=false
                             ><#assign lm=lm+sign+out+inv 
                       ><#else
                             ><#assign lm=lm+"<np><BR>"+sign+out+inv      
                       ></#if
                       ><#assign sign=""
                       ><#assign inv=""
                       ><#assign brojac=0
                   ></#if
                   ><#assign val=word    
                   ><@rightAlign/><#--
                   --><#assign sign=sign+outB+val+"<BR>"
                   ><#assign brojac=brojac+1 
                   
             ></#if
             ><#assign val=primerak.invBroj
             ><#if val!="" 
                     ><#assign word=val
                     ><@rightAlign/><#--
                     --><#assign inv=inv+outB+val+"<BR>"
             ></#if 
             
                  
       ></#list      
       ><#if brojac!=0
                  ><#if zag="" && odred="" && a532="" 
                      ><#assign sign=sign+"<BR>"
                  ></#if
                  ><#assign sign="<sig>"+sign+"</sig>"
                  ><#if firstLM
                      ><#assign first=false
                      ><#assign lm=lm+sign+out+inv 
                  ><#else
                      ><#assign lm=lm+"<np><BR>"+sign+out+inv      
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


             
><#macro getPredmListic
><#assign predmLis=""
><#assign prvi=true

><#if f600?exists
      
      ><#list f600 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                
                ><#assign udcList=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field600 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if  
                ></#if        
           ></#if 
      ></#list
></#if
                               
                              



><#if f601?exists
      
      ><#list f601 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field601 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if





><#if f602?exists
      
      ><#list f602 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field602 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if


><#if f605?exists
      
      ><#list f605 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign udc=false
          
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field605 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if



><#if f606?exists
      
      ><#list f606 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field606 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign n=val><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if



><#if f607?exists
      
      ><#list f607 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field606 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if



><#if f608?exists
      
      ><#list f608 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field606 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if



><#if f609?exists
      
      ><#list f609 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                
                ><#assign udc=false
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field606 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if predmLis!=""
                                       ><#assign predmLis=predmLis+"<np>"+sListic
                               ><#else
                                       ><#assign predmLis=sListic 
                               ></#if 
                ></#if        
           ></#if 
      ></#list
></#if


    



></#macro





><#macro getAutorski
><#assign autorLis=""
><#assign prvi=true

><#if f700?exists
      
      ><#list f700 as field
           ><#if field.ind1="1"  
                
          
                ><#assign val=""
                ><#assign allSF="abcdf" 
                ><@field700 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if




><#if f701?exists
      
      ><#list f701 as field
           ><#if field.ind1="1"  
                
          
                ><#assign val=""
                ><#assign allSF="abcdf" 
                ><@field700 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if





><#if f702?exists
      
      ><#list f702 as field
           ><#if field.ind1="1" 
                
          
                ><#assign val=""
                ><#assign allSF="abcdf" 
                ><@field700 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if        
           ></#if    

              
              
      ></#list
></#if


><#if f711?exists
      
      ><#list f711 as field
                ><#assign val=""
                ><#assign allSF="abcdefgh" 
                ><@field710 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if
              
      ></#list
></#if



><#if f712?exists
      
      ><#list f712 as field
                ><#assign val=""
                ><#assign allSF="abcdefgh" 
                ><@field710 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if
      ></#list
></#if



><#if f721?exists
      
      ><#list f721 as field
                ><#assign val=""
                ><#assign allSF="af" 
                ><@field720 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if
      ></#list
></#if



><#if f722?exists     
      ><#list f722 as field                     
                ><#assign val=""
                ><#assign allSF="af" 
                ><@field720 field/><#--
                --><#if val!=""
                               
                               ><#assign number=1
                               ><@upperFirstN/><#--
                               --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                ></#if
      ></#list
></#if



><#if f423?exists
      
      ><#list f423 as secField
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field423Analitika secField /><#--
              
                --><#if val!=""
                         ><#assign word=val
                         ><#list word?split("<split423>") as val                                 
                                 
                                 ><#assign number=1
                                 ><@upperFirstN/><#--
                                 --><#assign sOdrednica=val
                               ><@getSporedniListic/><#--
                               --><#if autorLis!=""
                                       ><#assign autorLis=autorLis+"<np>"+sListic
                               ><#else
                                       ><#assign autorLis=autorLis+sListic 
                               ></#if 
                         ></#list
                 ></#if 
      ></#list
></#if

></#macro


><#macro getSerijski
><#assign ls=""
><#assign inv=""
><#assign out=""
><#assign sign=""
><#assign sign1=""
><#assign firstLS=true
><#assign existsSign=false

><@odrednica/><@zaglavljeSer/><@glavniOpis/><@napomeneSerijska/><@issn/><@specGodista/><@brojUDC/><#--
--><#if godine?exists
      ><#assign brojac=0
      ><#list godine as godina             
                   ><#assign val=godina.signatura
                   ><#if val!="" && val!=sign1
                             ><#assign existsSign=true
                             ><#assign word=val
                             ><#assign sign1=val
                             ><#if brojac=brSignatura
                                 ><#assign sign="<sig>"+sign+"</sig><BR>"
                                 ><#if firstLS
                                      ><#assign firstLS=false
                                      ><#assign ls=ls+zagS+sign+odred+opis+napS+issnBR
                                      ><#if serijskaF
                                              ><#assign ls=ls+"<BR>"+brUDC 
                                      ></#if
                                      ><#if !serijskaF
                                             ><#assign ls=ls+specGod+"<BR>" 
                                      ></#if
                                       ><#assign ls=ls+"<BR>"+inv  
                                  ><#else
                                       ><#assign ls=ls+"<np><BR>"+zagS+sign+odred+opis+napS+issnBR  
                                       ><#if serijskaF
                                            ><#assign ls=ls+"<BR>"+brUDC  
                                       ></#if
                                       ><#if !serijskaF
                                                 ><#assign ls=ls+specGod+"<BR>" 
                                       ></#if 
                                       ><#assign ls=ls+"<BR>"+inv     
                                  ></#if
                                  ><#assign sign=""
                                  ><#assign inv=""
                                  ><#assign brojac=0
                             ></#if
                             ><#assign val=word
                             ><@rightAlign/><#--
                             --><#assign sign=sign+outB+val+"<BR>"
                             ><#assign brojac=brojac+1  
                   ></#if
                   ><#assign val=godina.invBroj
                   ><#if val!="" 
                              ><#assign word=val
                              ><@rightAlign/><#--
                              --><#assign inv=inv+outB+val+"<BR>"
                   ></#if  
       ></#list      
       ><#if brojac!=0
                  ><#assign sign="<sig>"+sign+"</sig><BR>"
                  ><#if firstLS
                      ><#assign first=false                      
                      ><#assign ls=ls+zagS+sign+odred+opis+napS+issnBR
                      ><#if serijskaF
                           ><#assign ls=ls+"<BR>"+brUDC  
                      ></#if
                      ><#if !serijskaF
                           ><#assign ls=ls+specGod+"<BR>" 
                      ></#if 
                      ><#assign ls=ls+"<BR>"+inv  
                  ><#else                      
                      ><#assign ls=ls+"<np><BR>"+zagS+sign+odred+opis+napS+issnBR 
                      ><#if serijskaF
                           ><#assign ls=ls+"<BR>"+brUDC 
                      ></#if
                      ><#if !serijskaF
                           ><#assign ls=ls+specGod+"<BR>" 
                      ></#if 
                      ><#assign ls=ls+"<BR>"+inv    
                  ></#if
       ></#if
></#if
><#if !existsSign
    ><#assign ls="<sig>"+nemaSignature+"<BR></sig><BR>"+zagS+odred+opis+napS+issnBR+"<BR>"          
></#if



></#macro




>