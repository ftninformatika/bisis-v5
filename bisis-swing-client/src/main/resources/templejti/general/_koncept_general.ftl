<#include "_polja_general.ftl"
><#--  koncepti se zasnivaju na konceptu Filozofskog fakulteta u Novom Sadu koncepti koji se razlikuju su 
    1. odrednica ne stavlja se unutar tagova <odr></odr> jer se na prebacuje na narednu stranicu listica
       i vadi se i iz polja 600, 6001, 602, 605, 606, 607, 608, 609    
    2. zaglavlje  ne stavlja se unutar tagova <zag></zag> jer se na prebacuje na narednu stranicu listica    
    3. prilozi vade se i iz polja 423
    4. brojUDC razlicita interpunkcija
    5. isbn razlicita interpunkcija
    6. predmOdrednica je novi koncept
koncepti biblioteke iz Kikinde i biblioteke iz Novog Sada razlikuju se po odrednici i predmOdred (Kikinda dodala polja
600, 6001, 602, 605, 606, 607, 608, 609 u predmetnu odrednicu a biblioteka iz Novog Sada ta ista polja u odrednicu)       
--><#assign odr=" "

><#macro cutFirstN  
 ><#assign i=1
 ><#assign pom=" "
 ><#list val?split(" ") as x
        ><#if i!=1
             ><#assign pom=pom+" "
        ></#if
        ><#assign pom=pom+x
        
             ><#if i==number+1
                  ><#break
             ></#if
             ><#assign i=i+1
 ></#list
 ><#assign val=pom
></#macro

><#macro upperFirstN 
 ><#assign i=0
 ><#assign pom=""
 

 ><#if (val?index_of("&nbsp;")!=-1 && val?index_of(" ")=-1 )|| (val?index_of("&nbsp;")!=-1 && (val?index_of("&nbsp;")<val?index_of(" ")))
           ><#list val?split("&nbsp;") as x
                 ><#if i!=0
                      ><#assign pom=pom+"&nbsp;"
                 ></#if
                 ><#if i<number
                     ><#assign pom=pom+x?upper_case
                     ><#assign i=i+1
                 ><#else
                     ><#assign pom=pom+x
                 ></#if 
         ></#list

 ><#elseif val?index_of(" ")!=-1
         ><#list val?split(" ") as x
                ><#if i!=0
                    ><#assign pom=pom+"&nbsp;"
                ></#if
                ><#if i<number
                     ><#assign pom=pom+x?upper_case
                     ><#assign i=i+1
                ><#else
                     ><#assign pom=pom+x
                ></#if 
         ></#list
 ><#else
      ><#assign pom=val
 ></#if
 ><#assign val=pom
></#macro

><#macro glavniOpis
><#assign opis=""
><#if f200?exists 
     ><#list f200 as field            
            ><#if field.ind1="1"
                    ><#assign val="" 
                    ><#assign allSF="@"   
                    ><@field200 field/><#--
                    --><#if odr="200"
                                ><#assign number=1
                                ><@upperFirstN/><#--
                   --></#if
                   ><#assign opis=opis+"&nbsp;&nbsp;&nbsp;"+val  
           ><#else
                    ><#assign val=""
                    ><#assign allSF="@"
                    ><@field200 field/><#--
                    --><#if val!=""
                            ><#assign val=""
                            ><#assign allSF="abcdefghiv"
                            ><@field200 field/><#--
                            --><#assign opis=opis+"&nbsp;&nbsp;&nbsp;"+val
                    ></#if
           ></#if
           ><#break
    ></#list
></#if
><#if f205?exists  
  ><#list f205 as field
      ><#assign val=""
      ><#assign allSF="adfgb"
      ><@field205 field/><#--
      --><#if val!=""
                ><#if opis?ends_with(".") 
                      ><#assign opis=opis+"&nbsp;-&nbsp;"
                ><#else
                      ><#assign opis=opis+".&nbsp;-&nbsp;"
                ></#if
                ><#assign opis=opis+val
      ></#if    
      ><#break
  ></#list
></#if
><#if f207?exists    
  ><#list f207 as field
           ><#assign val=""
           ><#assign allSF="a"
  
           ><@field207 field/><#-- 
           --><#if val!=""
                 ><#if opis?ends_with(".")
                          ><#assign opis=opis+"&nbsp;-&nbsp;"
                 ><#else
                          ><#assign opis=opis+".&nbsp;-&nbsp;"
                 ></#if
                 ><#assign opis=opis+val 
           ></#if
           ><#break
  ></#list
></#if
><#if f210?exists 
  ><#list f210 as field
       ><#assign val=""
       ><#assign allSF="acdegh"
       ><@field210 field/><#--
       --><#if val!=""
                 ><#if opis?ends_with(".")
                          ><#assign opis=opis+"&nbsp;-&nbsp;"
                 ><#else
                          ><#assign opis=opis+".&nbsp;-&nbsp;"
                 ></#if
                 ><#assign opis=opis+val 
       ></#if 
       ><#break
  ></#list 
></#if
><#if f215?exists
  ><#list f215 as field
          ><#assign val=""
          ><#assign allSF="acde"  
          ><@field215 field/><#--
          --><#if val!=""
                 ><#if opis?ends_with(".")
                          ><#assign opis=opis+"&nbsp;-&nbsp;"
                 ><#else
                          ><#assign opis=opis+".&nbsp;-&nbsp;"
                 ></#if
                 ><#assign opis=opis+val 
           ></#if
          ><#break
  ></#list 
></#if 
><#assign pom225=""  
><#if f225?exists   
          ><#if opis?ends_with(".")
              ><#assign pom225=opis+"&nbsp;-&nbsp;"
          ><#else
              ><#assign pom225=opis+".&nbsp;-&nbsp;"
          ></#if  
  ><#assign i=1
  ><#list f225 as field
          ><#assign val=""
          ><#assign allSF="adefhivx" 
          ><@field225 field/><#--
          --><#if val!=""
                   ><#if i=1
                          ><#assign opis=pom225+"&nbsp;("+val+")&nbsp;"
                   ><#else
                          ><#assign opis=opis+"&nbsp;("+val+")&nbsp;"
                   ></#if
          ></#if
          ><#assign i=i+1
  ></#list 
></#if 
><#if opis!="" &&  !opis?ends_with(".") && !opis?ends_with(")&nbsp;")
                   ><#assign opis=opis+"."                 
></#if
></#macro



><#macro toUpperFirst 
 ><#assign pom=""
 ><#if val?index_of(",")!=-1
       ><#assign pom=""
       ><#assign i=1
       ><#list val?split(",") as x
              
              ><#if i=1
                   ><#assign i=2
                   ><#assign pom=pom+ x?upper_case 
             ><#else 
                   ><#assign pom=pom+","+x
             ></#if
       ></#list
 
 ><#elseif (val?index_of("&nbsp;")!=-1 && val?index_of(" ")=-1 )|| (val?index_of("&nbsp;")!=-1 && (val?index_of("&nbsp;")<val?index_of(" ")))

       ><#assign pom=""
       ><#assign i=1
       ><#list val?split("&nbsp;") as x
                ><#if i=1
                        ><#assign i=2
                        ><#assign pom=pom+ x?upper_case 
                ><#else 
                        ><#assign pom=pom+"&nbsp;"+ x
                ></#if
      ></#list 
 ><#elseif val?index_of(" ")!=-1
       ><#assign pom=""
       ><#assign i=1
       ><#list val?split(" ") as x
                ><#if i=1
                        ><#assign i=2
                        ><#assign pom=pom+ x?upper_case 
                ><#else 
                        ><#assign pom=pom+"&nbsp;"+ x
                ></#if
      ></#list
 ><#else
     ><#assign pom=val
 
 ></#if
      ><#assign val=pom
></#macro


><#macro odrednica
      ><#assign odred=""
      ><#assign exist532=false
      ><#if f532?exists 
            ><#list f532 as field 
                    ><#if field.ind1="1" 
                            ><#assign exist532=true                            
                    ></#if
                    ><#break
            ></#list
      ></#if
      ><#assign exist500=false
      ><#if f500?exists 
            ><#list f500 as field 
                    ><#if field.ind1="1" && field.ind2="1"
                            ><#assign exist500=true                            
                    ></#if
                    ><#break
            ></#list
      ></#if
      ><#assign exist503=false
      ><#if f503?exists 
            ><#list f503 as field 
                    ><#if field.ind1="1" 
                            ><#assign exist503=true                            
                    ></#if
                    ><#break
            ></#list
      ></#if           
      ><#if f700?exists               
               ><#list f700 as field
                           ><#assign val=""
                           ><#assign allSF="adbcf" 
                           ><@field700 field/><#--
                           --><@toUpperFirst/><#--
                           --><#if val!=""
                                       ><#assign odr="700"
                                       ><#assign odred="<B>"+val+"</B><BR>"
                           ></#if
                           ><#return 
              ></#list
      ><#elseif f710?exists               
              ><#list f710 as field
                          ><#assign val=""
                          ><#assign allSF="abghcdfe" 
                          ><@field710 field/><#--
                          --><@toUpperFirst/><#--
                          --><#if val!=""
                                    ><#assign odr="710"
                                    ><#assign odred="<B>"+val+"</B><BR>"
                          ></#if
                          ><#return 
              ></#list
     ><#elseif f720?exists              
              ><#list f720 as field
                         ><#assign val=""
                         ><#assign allSF="af" 
                         ><@field720 field/><#--
                        --><@toUpperFirst/><#--
                        --><#if val!=""
                                  ><#assign odr="720"
                                  ><#assign odred="<B>"+val+"</B><BR>"
                          ></#if
                          ><#return 
              ></#list
    ><#elseif f532?exists && exist532
             ><#list f532 as field
                    ><#if field.ind1="1"
                       ><#assign odr="532" 
                       ><#assign val=""
                       ><#assign allSF="a" 
                       ><@field532 field/><#--
                       --><@toUpperFirst/><#--
                       --><#if val!=""
                                ><#assign odr="532" 
                                ><#assign odred="<B>"+val+"</B><BR>"
                       ></#if
                       ><#return 
                   ></#if
             ></#list 
   ><#elseif f500?exists &&  exist500
            ><#list f500 as field
                      ><#if field.ind1="1" && field.ind2="1" 
                                
                                ><#assign val=""
                                ><#assign allSF="abhiklm" 
                                ><@field500 field/><#--
                                --><@toUpperFirst/><#--
                                --><#if val!=""
                                       ><#assign odr="500"
                                       ><#assign odred="<B>"+val+"</B><BR>"
                                ></#if
                                ><#return 
                     ></#if
            ></#list
  ><#elseif f503?exists  && exist503
            ><#list f503 as field
                    ><#if field.ind1="1"                                
                               ><#assign val=""
                               ><#assign allSF="ajb" 
                               ><@field503 field/><#--
                               --><@toUpperFirst/><#--
                               --><#if val!=""
                                       ><#assign odr="503"
                                       ><#assign odred="<B>"+val+"</B><BR>"
                               ></#if
                               ><#return 
                    ></#if
           ></#list
  ><#elseif f200?exists 
           ><#list f200 as field
                             ><#if field.ind1="1"
                                       ><#assign val=""
                                       ><#assign allSF="a" 
                                       ><@field200 field/><#-- 
                                       --><#if val!=""
                                               ><#assign odr="200"
                                       ></#if
                             ></#if
           ></#list
  ><#elseif f600?exists              
              ><#list f600 as field
                         ><#assign val=""
                         ><#assign allSF="@" 
                         ><@field600 field/><#--
                        --><@toUpperFirst/><#--
                        --><#if val!=""
                                  ><#assign odr="600"
                                  ><#assign odred="<B>"+val+"</B><BR>"
                          ></#if
                          ><#return 
              ></#list
  ><#elseif f601?exists              
              ><#list f601 as field
                         ><#assign val=""
                         ><#assign allSF="@" 
                         ><@field601 field/><#--
                        --><@toUpperFirst/><#--
                        --><#if val!=""
                                  ><#assign odr="601"
                                  ><#assign odred="<B>"+val+"</B><BR>"
                          ></#if
                          ><#return 
              ></#list
  ><#elseif f602?exists              
              ><#list f602 as field
                         ><#assign val=""
                         ><#assign allSF="@" 
                         ><@field602 field/><#--
                        --><@toUpperFirst/><#--
                        --><#if val!=""
                                  ><#assign odr="602"
                                  ><#assign odred="<B>"+val+"</B><BR>"
                          ></#if
                          ><#return 
              ></#list
  ><#elseif f605?exists              
              ><#list f605 as field
                         ><#assign val=""
                         ><#assign allSF="@" 
                         ><@field605 field/><#--
                        --><@toUpperFirst/><#--
                        --><#if val!=""
                                  ><#assign odr="605"
                                  ><#assign odred="<B>"+val+"</B><BR>"
                          ></#if
                          ><#return 
              ></#list 
  ><#elseif f606?exists              
              ><#list f606 as field
                         ><#assign val=""
                         ><#assign allSF="@" 
                         ><@field605 field/><#--
                        --><@toUpperFirst/><#--
                        --><#if val!=""
                                  ><#assign odr="606"
                                  ><#assign odred="<B>"+val+"</B><BR>"
                          ></#if
                          ><#return 
              ></#list  
  ><#elseif f607?exists              
              ><#list f607 as field
                         ><#assign val=""
                         ><#assign allSF="@" 
                         ><@field605 field/><#--
                        --><@toUpperFirst/><#--
                        --><#if val!=""
                                  ><#assign odr="607"
                                  ><#assign odred="<B>"+val+"</B><BR>"
                          ></#if
                          ><#return 
              ></#list    
  ><#elseif f608?exists              
              ><#list f608 as field
                         ><#assign val=""
                         ><#assign allSF="@" 
                         ><@field605 field/><#--
                        --><@toUpperFirst/><#--
                        --><#if val!=""
                                  ><#assign odr="608"
                                  ><#assign odred="<B>"+val+"</B><BR>"
                          ></#if
                          ><#return 
              ></#list 
  ><#elseif f609?exists              
              ><#list f609 as field
                         ><#assign val=""
                         ><#assign allSF="@" 
                         ><@field605 field/><#--
                        --><@toUpperFirst/><#--
                        --><#if val!=""
                                  ><#assign odr="609"
                                  ><#assign odred="<B>"+val+"</B><BR>"
                          ></#if
                          ><#return 
              ></#list                                                      
  ><#else
      ><#assign odr=odrednica         
  ></#if  
></#macro

><#macro cutAndUpper  
 ><#assign i=1
 ><#assign pom=""
 ><#if val?index_of(" ")!=-1
 ><#list val?split(" ") as x
        ><#if i=1
               ><#assign pom=pom+x?upper_case
        ><#elseif i!=1 && i<=3
             ><#assign pom=pom+"&nbsp;"+x
        ><#elseif i=4
             ><#assign pom=pom+"..."
             ><#break
        ></#if  
        ><#assign i=i+1
 ></#list
 ><#assign val=pom
 ></#if
></#macro

><#macro zaglavlje
><#assign zag=""
><#if odr="200" 
       ><#if f200?exists
              ><#list f200 as field
                     ><#if field.ind1="1" 
                              ><#assign val=""
                              ><#assign allSF="a" 
                              ><@field200 field/><#--
                              --><#if val!=""                                                                                  
                                         ><@cutAndUpper/><#--  
                                         --><#assign zag="<B>"+val+"</B><BR>"                                                   
                              ></#if 
                   ></#if
                   ><#break
           ></#list 
     ></#if
></#if
></#macro

><#macro napomene
><#assign nap=""
      ><#if f300?exists
               ><#list f300 as field
                       ><#assign val=""                        
                       ><#assign allSF="@" 
                       ><@field300 field/><#--       
                       --><#if val!=""
                                   ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  ><#else
                                            ><#assign nap="<BR><BR>"                                             
                                  ></#if
                                  ><#assign nap=nap+val      
                     ></#if
              ></#list
     ></#if
     ><#if f314?exists
             ><#list f314 as field
                    ><#assign val=""       
                    ><#assign allSF="@" 
                    ><@field300 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  ><#else
                                            ><#assign nap="<BR><BR>"                                             
                                  ></#if
                                  ><#assign nap=nap+val
                   ></#if
          ></#list
   ></#if
   ><#if f320?exists 
         ><#list f320 as field
                 ><#assign val=""                      
                 ><#assign allSF="a"     
                 ><@field200 field/><#--
                 --><#if val!=""
                          ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  ><#else
                                            ><#assign nap="<BR><BR>"                                             
                                  ></#if
                                  ><#assign nap=nap+val
                ></#if
        ></#list
  ></#if
  ><#if f321?exists 
        ><#list f321 as field
                  ><#assign val=""     
                  ><#assign allSF="@" 
                  ><@field321 field/><#--
                  --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  ><#else
                                            ><#assign nap="<BR><BR>"                                             
                                  ></#if
                                  ><#assign nap=nap+val
                ></#if
       ></#list
  ></#if
  ><#if f322?exists 
       ><#list f322 as field
                   ><#assign val=""       
                   ><#assign allSF="@" 
                   ><@field300 field/><#--
                   --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  ><#else
                                            ><#assign nap="<BR><BR>"
                                             
                                  ></#if
                                  ><#assign nap=nap+val
                  ></#if          
       ></#list
  ></#if
  ><#if f323?exists 
          ><#list f323 as field
                    ><#assign val=""        
                    ><#assign allSF="@" 
                    ><@field300 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""

                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  ><#else
                                            ><#assign nap="<BR><BR>"                                             
                                  ></#if
                                  ><#assign nap=nap+val                               
                    ></#if
         ></#list
  ></#if
  ><#if f324?exists 
         ><#list f324 as field
                     ><#assign val=""       
                     ><#assign allSF="@" 
                     ><@field300 field/><#--
                     --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  ><#else
                                            ><#assign nap="<BR><BR>"                                             
                                  ></#if
                                  ><#assign nap=nap+val
                     ></#if          
        ></#list
  ></#if
  ><#if f326?exists 
         ><#list f326 as field
                    ><#assign val=""        
                    ><#assign allSF="@" 
                    ><@field300 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  ><#else
                                            ><#assign nap="<BR><BR>"
                                             
                                  ></#if
                                  ><#assign nap=nap+val        
                    ></#if
        ></#list
  ></#if
  ><#if f327?exists 
         ><#list f327 as field
                    ><#assign val=""         
                    ><#assign allSF="a" 
                    ><@field200 field/><#--
                    --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  ><#else
                                            ><#assign nap="<BR><BR>"                                             
                                  ></#if
                                  ><#assign nap=nap+val
                   ></#if
        ></#list
  ></#if
  ><#if f328?exists 
        ><#list f328 as field
                 ><#assign val=""       
                 ><#assign allSF="a" 
                 ><@field300 field/><#--
                 --><#if val!=""
                                  ><#if nap!=""

                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  ><#else
                                            ><#assign nap="<BR><BR>"
                                             
                                  ></#if
                                  ><#assign nap=nap+val
               ></#if
       ></#list
  ></#if
  ><#if f330?exists 
        ><#list f330 as field
               ><#assign val=""        
               ><#assign allSF="@" 
               ><@field300 field/><#--
               --><#if val!=""
                                  ><#if nap!=""
                                            ><#if nap?ends_with(".")
                                                       ><#assign nap=nap+"&nbsp;-&nbsp;" 
                                            ><#else
                                                       ><#assign nap=nap+".&nbsp;-&nbsp;" 
                                            ></#if
                                  ><#else
                                            ><#assign nap="<BR><BR>"
                                             
                                  ></#if
                                  ><#assign nap=nap+val       
              ></#if
        ></#list
  ></#if
  ><#if nap!="" && !nap?ends_with(".")
            ><#assign nap=nap+"."
  ></#if  
></#macro





><#macro isbn
  ><#assign isbnBR=""  
        ><#if f010?exists                 
                 ><#list f010 as field
                          ><#assign val=""
                          ><#assign allSF="a" 
                          ><@field010 field/><#--
                          --><#if val!=""                                   
                                   ><#assign isbnBR=isbnBR+"<BR>"+"ISBN&nbsp;"+val+"<BR>"
                          ></#if
                ></#list
     ></#if
     
></#macro


><#macro alone532
><#assign a532=""
  ><#if (f700?exists  || f710?exists || f720?exists 
          || f503?exists ) && f532?exists   
                  ><#list f532 as field
                          ><#assign val=""
                          ><#assign allSF="a" 
                          ><@field532 field/><#--
                          --><#if val!=""
                                     ><#assign a532="["+val+"]"+"<BR>"
                          ></#if
                          ><#break
                  ></#list
  ></#if
></#macro

><#macro convertDate 
 ><#assign d=""
 ><#assign m=""
 ><#assign y=""
 ><#assign i=0
 ><#assign date=val?number
 ><#assign pom=""
 ><#assign d=date%100
 ><#assign m=(date%10000-d)/100
 ><#assign y=(date-d-m*100)/10000
 ><#if (d<10)
     ><#assign pom="0"+d?string+"."
 ><#else
     ><#assign pom=d?string+"."
 ></#if
 ><#if (m<10)
     ><#assign pom=pom+"0"+m?string+"."
 ><#else
     ><#assign pom=pom+m?string+"."
 ></#if
 ><#assign pom=pom+y?string("##")+"."
 ><#assign val=pom
></#macro



><#macro specGodista
><#assign specGod=""
><#if f997?exists   
      ><#list f997 as field
            ><#assign val=""
            ><#assign allSF="jkm" 
              ><@field997 field/><#--
              --><#if val!=""
                  ><#if specGod=""
                     ><#assign specGod=specGod+"<BR><BR>"
                     
                  ><#else
                     ><#assign specGod=specGod+"<BR>"
                  ></#if
                ><#assign specGod=specGod+val
              
              ></#if
              
              
      ></#list
></#if
></#macro


><#macro prilozi
><#assign firstPril=true
><#assign pril=""

><#if f421?exists 
      ><#list f421 as fieldSec
            ><#assign val=""             
              ><@field421 fieldSec /><#--
              --><#if val!=""                  
                ><#assign pril=pril+"<BR>--&nbsp;"+val                
              ></#if              
      ></#list
></#if
><#if f423?exists 
      ><#list f423 as fieldSec
            ><#assign val=""             
              ><@field421 fieldSec /><#--
              --><#if val!=""                  
                ><#assign pril=pril+"<BR>--&nbsp;"+val                
              ></#if              
      ></#list
></#if
><#if f469?exists  
      ><#list f469 as fieldSec 
            ><#assign val=""             
              ><@field469 fieldSec /><#--
              --><#if val!=""
                   ><#if firstPril
                     ><#assign pril=pril+"<BR>--&nbsp;"+sadrzaj+"&nbsp;"
                     ><#assign firstPril=false
                   ><#else
                      ><#if fieldSec.ind2="0"
                          ><#assign pril=pril+"&nbsp;;&nbsp;"
                      ><#elseif fieldSec.ind2="1"
                          ><#assign pril=pril+".&nbsp;"
                      ><#elseif fieldSec.ind2="2"
                          ><#assign pril=pril+".&nbsp;-&nbsp;"
                      ><#elseif fieldSec.ind2="3"
                          ><#assign pril=pril+"&nbsp;:&nbsp;"
                      ></#if
                   ></#if
                   ><#assign pril=pril+val                   
              ></#if             
      ></#list
></#if
></#macro





><#macro brojUDC
><#assign brUDC=""
><#if f675?exists   
      ><#list f675 as field
            ><#assign val=""
            ><#assign allSF="a" 
              ><@field010 field/><#--
              --><#if val!=""
                  ><#if brUDC!=""                     
                     ><#assign brUDC=brUDC+"<BR>&nbsp;&nbsp;&nbsp;&nbsp;"
                  ></#if
                  ><#assign brUDC=brUDC+val
              ></#if
      ></#list
     ><#assign brUDC="UDK="+brUDC
></#if
></#macro

><#macro brojID
  
  ><#if rim || arap || predm || udc
      ><#assign brID=true
  ><#else
      ><#assign brID=false
  ></#if
></#macro

><#macro predmOdred
><#assign po=""
><#if f606?exists   
      ><#list f606 as field
            ><#assign val=""
            ><#assign allSF="a" 
              ><@field010 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+",&nbsp;"
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list           
></#if
><#if f610?exists   
      ><#list f610 as field
            ><#assign val=""
            ><#assign allSF="a" 
              ><@field010 field/><#--
              --><#if val!=""
                  ><#if po!=""                     
                     ><#assign po=po+",&nbsp;"
                  ></#if
                  ><#assign po=po+val
              ></#if
      ></#list          
></#if
><#assign po="P.O.:&nbsp;"+po+"<BR>" 
></#macro  

><#macro zaglavljeSer
><#assign zagS=""
><#if odr="f200" 
       ><#if f200?exists 
              ><#list f200 as field
                     ><#if field.ind1="1" 
                              ><#assign val=""
                              ><#assign allSF="a" 
                              ><@field200 field/><#--
                              --><#if val!=""                                    
                                         ><@cutAndUpper/><#--
                                         --><#assign zagS="<ser><B>"+val+"</B><BR></ser>"                                                   
                              ></#if 
                   ></#if
                   ><#break
           ></#list 
     ></#if
></#if
></#macro

><#macro napomeneSerijska

><#assign napS=""

><#if f300?exists 
  
      ><#list f300 as field
                    
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field300 field/><#--
              --><#if val!=""
                
                ><#if napS=""
                    
                    ><#assign napS=napS+"<BR><BR>"
                ><#elseif napS?ends_with(".")
                    ><#assign napS=napS+"&nbsp;-&nbsp;"
                ><#else
                    ><#assign napS=napS+".&nbsp;-&nbsp;"
                ></#if 
                 
               ><#assign napS=napS+val
            ></#if
              
      ></#list
></#if

><#if f326?exists 
  
      ><#list f326 as field
                    
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field300 field/><#--
              --><#if val!=""                
                ><#if napS=""                    
                    ><#assign napS=napS+"<BR><BR>"
                ><#elseif napS?ends_with(".")
                    ><#assign napS=napS+"&nbsp;-&nbsp;"
                ><#else
                    ><#assign napS=napS+".&nbsp;-&nbsp;"
                ></#if                  
               ><#assign napS=napS+val
            ></#if              
      ></#list
></#if



><#if napS!=""  && !napS?ends_with(".")
 ><#assign napS=napS+"."
></#if

></#macro


>