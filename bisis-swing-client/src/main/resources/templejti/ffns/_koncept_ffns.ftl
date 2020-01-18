<#include "_polja_ffns.ftl"

><#assign odr=""


><#macro toRoman

     ><#assign roman=""
     ><#assign ost10=br % 10
     ><#if ost10=1
              ><#assign roman=roman+"I"
     ><#elseif ost10=2
              ><#assign roman=roman+"II" 
     ><#elseif ost10=3
              ><#assign roman=roman+"III" 
     ><#elseif ost10=4
              ><#assign roman=roman+"IV" 
     ><#elseif ost10=5
              ><#assign roman=roman+"V" 
     ><#elseif ost10=6
              ><#assign roman=roman+"VI" 
     ><#elseif ost10=7
              ><#assign roman=roman+"VII" 
     ><#elseif ost10=8
              ><#assign roman=roman+"VIII" 
     ><#elseif ost10=9
              ><#assign roman=roman+"IX" 
     ></#if


     ><#assign ost100=br % 100-ost10
     ><#if ost100=10
              ><#assign roman="X"+roman
     ><#elseif ost100=20
              ><#assign roman="XX" +roman
     ><#elseif ost100=30
              ><#assign roman="XXX" +roman
     ><#elseif ost100=40
              ><#assign roman="XL" +roman
     ><#elseif ost100=50
              ><#assign roman="L"+roman 
     ><#elseif ost100=60
              ><#assign roman="LX" +roman
     ><#elseif ost100=70
              ><#assign roman="LXX"+roman 
     ><#elseif ost100=80
              ><#assign roman="LXXX" +roman
     ><#elseif ost100=90
              ><#assign roman="XC"+roman 
     ></#if

     ><#assign ost1000=br % 1000-ost10-ost100
     ><#if ost1000=100
              ><#assign roman="C"+roman
     ><#elseif ost1000=200
              ><#assign roman="CC" +roman
     ><#elseif ost1000=300
              ><#assign roman="CCC" +roman
     ><#elseif ost1000=400
              ><#assign roman="CD" +roman
     ><#elseif ost1000=500
              ><#assign roman="D"+roman 
     ><#elseif ost1000=600
              ><#assign roman="DC" +roman
     ><#elseif ost1000=700
             ><#assign roman="DCC"+roman 
     ><#elseif ost1000=800
             ><#assign roman="DCCC" +roman
     ><#elseif ost1000=900
             ><#assign roman="CM"+roman 
     ></#if
 
     ><#assign ost10000=br % 10000-ost10-ost100-ost1000
     ><#if ost10000=1000
             ><#assign roman="M"+roman
     ><#elseif ost10000=2000
             ><#assign roman="MM" +roman
     ><#elseif ost10000=3000
             ><#assign roman="MMM" +roman
     ><#elseif ost10000=4000
             ><#assign roman="MMMM" +roman
     ><#elseif ost10000=5000
             ><#assign roman="MMMMM"+roman 
     ><#elseif ost10000=6000
             ><#assign roman="MMMMMM" +roman
     ><#elseif ost10000=7000
             ><#assign roman="MMMMMMM"+roman 
     ><#elseif ost10000=8000
             ><#assign roman="MMMMMMMM" +roman
     ><#elseif ost10000=9000
             ><#assign roman="MMMMMMMMM"+roman 
     ></#if
></#macro


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
                    --><#if odr="f200"
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
                                       ><#assign odr="f700"
                                       ><#assign odred="<odr><BR><B>"+val+"</B><BR></odr>"
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
                                    ><#assign odr="f710"
                                    ><#assign odred="<odr><BR><B>"+val+"</B><BR></odr>"
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
                                  ><#assign odr="f720"
                                  ><#assign odred="<odr><BR><B>"+val+"</B><BR></odr>"
                          ></#if
                          ><#return 
              ></#list
    ><#elseif f532?exists && exist532
             ><#list f532 as field
                    ><#if field.ind1="1"
                       ><#assign odr="f532" 
                       ><#assign val=""
                       ><#assign allSF="a" 
                       ><@field532 field/><#--
                       --><@toUpperFirst/><#--
                       --><#if val!=""
                                ><#assign odr="f532" 
                                ><#assign odred="<odr><BR><B>"+val+"</B><BR></odr>"
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
                                       ><#assign odr="f500"
                                       ><#assign odred="<odr><BR><B>"+val+"</B><BR></odr>"
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
                                       ><#assign odr="f503"
                                       ><#assign odred="<odr><BR><B>"+val+"</B><BR></odr>"
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
                                               ><#assign odr="f200"
                                       ></#if
                             ></#if
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
><#if odr="f200" 
       ><#if f200?exists
              ><#list f200 as field
                     ><#if field.ind1="1" 
                              ><#assign val=""
                              ><#assign allSF="a" 
                              ><@field200 field/><#--
                              --><#if val!=""                                                                                  
                                         ><@cutAndUpper/><#--  
                                         --><#assign zag="<zag><BR><B>"+val+"</B><BR></zag>"                                                   
                              ></#if 
                   ></#if
                   ><#break
           ></#list 
     ></#if
></#if
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
                                   ><#if isbnBR=""
                                           ><#assign isbnBR=isbnBR+"<BR><BR>"
                                   ><#else
                                           ><#assign isbnBR=isbnBR+".&nbsp;-&nbsp;"        
                                   ></#if
                                   ><#assign isbnBR=isbnBR+"ISBN&nbsp;"+val+"&nbsp;"
                          ></#if
                ></#list
     ></#if
     
></#macro









><#macro issn
  ><#assign issnBR=""
  ><#if f011?exists 
    
          ><#list f011 as field
                    ><#assign val=""
                    ><#assign allSF="e"
                    ><@field011 field/><#--
                   --><#if val!=""
                                   ><#assign issnBR="<BR><BR>ISSN&nbsp;"+val
                   ></#if 
                   ><#break                    
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





><#macro dsPromocija
><#assign dsProm=""

><#if f328?exists 
  
      ><#list f328 as field           
          
            ><#assign val=""
            ><#assign allSF="e" 
              ><@field328 field/><#--
              --><#if val!=""
                ><@convertDate/><#--  
                --><#assign dsProm=dsProm+promocija+"&nbsp;"+val
                ><#return
              ></#if
              
      ></#list
></#if
></#macro




><#macro dsTitula
><#assign dsTit=""
><#if f328?exists 
      ><#list f328 as field
            ><#assign val=""
            ><#assign allSF="f" 
              ><@field328 field/><#--
              --><#if val!=""
                  ><#assign dsTit=dsTit+",&nbsp;"+val
                  ><#return
              ></#if
              
      ></#list
></#if
></#macro



><#macro dsOdbrana
><#assign dsOd=""
><#if f328?exists 
      ><#list f328 as field
            ><#assign val=""
            ><#assign allSF="d" 
              ><@field328 field/><#--
              --><#if val!=""
                ><@convertDate/><#--  
               --><#assign dsOd=dsOd+"<BR><BR>"+odbrana+"&nbsp;"+val
               ><#return
              ></#if
      ></#list
></#if
></#macro



><#macro dsKomisija
><#assign dsKom=""
><#assign i=0
><#if f702?exists   
      ><#list f702 as field
            ><#assign val=""
            ><#assign allSF="ab" 
              ><@field700 field/><#--
              --><#if val!=""
                ><#if i=0
                    ><#assign dsKom=dsKom+"<BR>"+mentor+"&nbsp;"
                ><#elseif i=1
                    ><#assign dsKom=dsKom+"<BR>"+komisija+"&nbsp;"
                ><#else
                    ><#assign dsKom=dsKom+"<BR><sp><sp><sp><sp><sp><sp><sp><sp><sp><sp>"
                ></#if 
                 
               ><#assign dsKom=dsKom+val
            ></#if
            ><#assign i=i+1  
      ></#list
></#if
></#macro


><#macro opisSerijske
><#assign opisS=""
      ><#assign exsist=false
      ><#list f200 as field
          ><#if field.ind1="0" 
             ><#assign exsist=true  
          ></#if
      ></#list
      ><#if exsist             
            ><#if f532?exists 
               ><#list f532 as field
                   ><#assign val=""
                   ><#assign allSF="a" 
                   ><@field532 field/><#--
                   --><#if val!=""                            
                            ><#assign opisS=opisS+val
                   ></#if
                   ><#break
               ></#list
            ><#else 
                ><#list f200 as field
                    ><#if field.ind1="0"  
                        ><#assign val=""
                        ><#assign allSF="aefhi" 
                        ><@field200 field/><#--
                        --><#if val!=""                    
                                ><#assign opisS=opisS+val
                        ></#if
                        ><#break
                    ></#if
               ></#list 
           ></#if
      ><#elseif f200?exists 
           ><#list f200 as field               
                  ><#assign val=""
                  ><#assign allSF="aefhi" 
                  ><@field200 field/><#--
                  --><#if val!=""                    
                    ><#assign opisS=opisS+val
                  ></#if
                  ><#break              
            ></#list 
      ></#if 
><#if f011?exists 
   ><#list f011 as field
      ><#assign val=""
      ><#assign allSF="e" 
      ><@field011 field/><#--
      --><#if val!=""
          ><#if opisS!=""
            ><#assign opisS=opisS+".&nbsp;-&nbsp;ISSN&nbsp;"          
          ></#if       
          ><#assign opisS=opisS+val
      ></#if
      ><#break             
   ></#list
></#if 
></#macro




><#macro napomeneSerijska

><#assign napS=""

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
><#if napS!=""  && !napS?ends_with(".")
 ><#assign napS=napS+"."
></#if

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
><#assign first=true
><#assign brUDC=""
><#if f675?exists 
  
      ><#list f675 as field
            ><#assign val=""
            ><#assign allSF="a" 
              ><@field010 field/><#--
              --><#if val!=""
                  ><#if brUDC!=""                     
                     ><#assign brUDC=brUDC+"<BR>"
                  ></#if
                  ><#assign brUDC=brUDC+val
              ></#if
      ></#list
     
></#if
><#if brUDC!=""
   ><#assign udc=true
   ><#if rim || arap || predm
       ><#assign brUDC="<BR>"+brUDC 
   ><#else
       ><#assign brUDC="<BR><BR>"+brUDC 
   ></#if
></#if
></#macro


><#macro arapTrejsing

><#assign br=1
><#assign arapT=""

><#if f200?exists       
      ><#assign exsist=false
      ><#list f200 as field
          ><#if field.ind1="1" 
             ><#assign exsist=true  
          ></#if
      ></#list
      ><#if exsist && odr!="" 
          ><#if arapT=""             
             ><#assign arapT=arapT+"<BR><BR>"
          ><#else
             ><#assign arapT=arapT+"&nbsp;"   
          ></#if  
          ><#assign arapT=arapT+br+".&nbsp;"+mrtitle    
          ><#assign br=br+1    
      ></#if        
></#if


><#if f510?exists     
      ><#assign exsist=false
      ><#list f510 as field
          ><#if field.ind1="1" 
             ><#assign exsist=true  
          ></#if
      ></#list
      ><#if exsist 
          ><#if arapT=""             
             ><#assign arapT=arapT+"<BR><BR>"
          ><#else
             ><#assign arapT=arapT+"&nbsp;"
          ></#if  
          ><#assign arapT=arapT+br+".&nbsp;"+crtitle    
          ><#assign br=br+1    
      ></#if        
></#if



><#if f512?exists       
      ><#assign exsist=false
      ><#list f512 as field
          ><#if field.ind1="1" 
             ><#assign exsist=true  
          ></#if
      ></#list
      ><#if exsist 
          ><#if arapT=""             
             ><#assign arapT=arapT+"<BR><BR>"
          ><#else
             ><#assign arapT=arapT+"&nbsp;"   
          ></#if  
          ><#assign arapT=arapT+br+".&nbsp;"+ortitle    
          ><#assign br=br+1    
      ></#if        
></#if




><#if f513?exists   
      ><#assign exsist=false
      ><#list f513 as field
          ><#if field.ind1="1" 
             ><#assign exsist=true  
          ></#if
      ></#list
      ><#if exsist 
          ><#if arapT=""             
             ><#assign arapT=arapT+"<BR><BR>"
          ><#else
             ><#assign arapT=arapT+"&nbsp;"   
          ></#if  
          ><#assign arapT=arapT+br+".&nbsp;"+rtitleshead    
          ><#assign br=br+1    
      ></#if        
></#if



><#if f514?exists      
      ><#assign exsist=false
      ><#list f514 as field
          ><#if field.ind1="1" 
             ><#assign exsist=true  
          ></#if
      ></#list
      ><#if exsist 
          ><#if arapT=""             
             ><#assign arapT=arapT+"<BR><BR>"
          ><#else
             ><#assign arapT=arapT+"&nbsp;"   
          ></#if  
          ><#assign arapT=arapT+br+".&nbsp;"+rtitleabovetext    
          ><#assign br=br+1    
      ></#if        
></#if




><#if f515?exists    
      ><#assign exsist=false
      ><#list f515 as field
          ><#if field.ind1="1" 
             ><#assign exsist=true  
          ></#if
      ></#list
      ><#if exsist 
          ><#if arapT=""
             
             ><#assign arapT=arapT+"<BR><BR>"
          ><#else
             ><#assign arapT=arapT+"&nbsp;"   
          ></#if  
          ><#assign arapT=arapT+br+".&nbsp;"+currtitle    
          ><#assign br=br+1    
      ></#if        
></#if



><#if f516?exists      
      ><#assign exsist=false
      ><#list f510 as field
          ><#if field.ind1="1" 
             ><#assign exsist=true  
          ></#if
      ></#list
      ><#if exsist 
          ><#if arapT=""
             
             ><#assign arapT=arapT+"<BR><BR>"
          ><#else
             ><#assign arapT=arapT+"&nbsp;"   
          ></#if  
          ><#assign arapT=arapT+br+".&nbsp;"+hrbtitle    
          ><#assign br=br+1    
      ></#if        
></#if



><#if f517?exists       
      ><#assign exsist=false
      ><#list f510 as field
          ><#if field.ind1="1" 
             ><#assign exsist=true  
          ></#if
      ></#list
      ><#if exsist
          ><#if arapT=""
             
             ><#assign arapT=arapT+"<BR><BR>"
          ><#else
             ><#assign arapT=arapT+"&nbsp;"   
          ></#if  
          ><#assign arapT=arapT+br+".&nbsp;"+ssltitle    
          ><#assign br=br+1    
      ></#if        
></#if



><#if f540?exists      
      ><#assign exsist=false
      ><#list f540 as field
          ><#if field.ind1="1" 
             ><#assign exsist=true  
          ></#if
      ></#list
      ><#if exsist 
          ><#if arapT=""             
             ><#assign arapT=arapT+"<BR><BR>"
          ><#else
             ><#assign arapT=arapT+"&nbsp;"   
          ></#if  
          ><#assign arapT=arapT+br+".&nbsp;"+addtitle    
          ><#assign br=br+1    
      ></#if        
></#if

><#if f701?exists   
      ><#list f701 as field
          ><#if field.ind1="1"  
          
            ><#assign val=""
            ><#assign allSF="abcdf" 
              ><@field700 field/><#--
              --><#if val!=""
                  ><#if arapT=""                     
                     ><#assign arapT=arapT+"<BR><BR>"
                  ><#else
                     ><#assign arapT=arapT+"&nbsp;"
                  ></#if
                ><#assign arapT=arapT+br+".&nbsp;"+val    
                ><#assign br=br+1    
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
                  ><#if arapT=""                     
                     ><#assign arapT=arapT+"<BR><BR>"
                  ><#else
                     ><#assign arapT=arapT+"&nbsp;"
                  ></#if
                ><#assign arapT=arapT+br+".&nbsp;"+val    
                ><#assign br=br+1    
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
                  ><#if arapT=""                     
                     ><#assign arapT=arapT+"<BR><BR>"
                  ><#else
                     ><#assign arapT=arapT+"&nbsp;"
                  ></#if
                ><#assign arapT=arapT+br+".&nbsp;"+val    
                ><#assign br=br+1    
              ></#if
      ></#list
></#if


><#if f712?exists
  
      ><#list f712 as field
            ><#assign val=""
            ><#assign allSF="abcdefgh" 
              ><@field710 field/><#--
              --><#if val!=""
                  ><#if arapT=""                     
                     ><#assign arapT=arapT+"<BR><BR>"
                  ><#else
                     ><#assign arapT=arapT+"&nbsp;"
                  ></#if
                ><#assign arapT=arapT+br+".&nbsp;"+val    
                ><#assign br=br+1    
              ></#if
      ></#list
></#if


><#if f721?exists 
  
      ><#list f721 as field
          
          
            ><#assign val=""
            ><#assign allSF="af" 
              ><@field720 field/><#--
              --><#if val!=""
                  ><#if arapT=""                     
                     ><#assign arapT=arapT+"<BR><BR>"
                  ><#else
                     ><#assign arapT=arapT+"&nbsp;"
                  ></#if
                ><#assign arapT=arapT+br+".&nbsp;"+val    
                ><#assign br=br+1    
              ></#if
      ></#list
></#if

><#if f722?exists  
      ><#list f722 as field
            ><#assign val=""
            ><#assign allSF="af" 
              ><@field720 field/><#--
              --><#if val!=""
                  ><#if arapT=""                     
                     ><#assign arapT=arapT+"<BR><BR>"
                  ><#else
                     ><#assign arapT=arapT+"&nbsp;"
                  ></#if
                ><#assign arapT=arapT+br+".&nbsp;"+val    
                ><#assign br=br+1    
              ></#if
      ></#list
></#if



><#if f423?exists 
      ><#list f423 as secField
            ><#assign val=""
            ><#assign br200=0 
              ><@field423 secField, br/><#--
              --><#if val!=""
                  ><#if arapT=""                     
                     ><#assign arapT=arapT+"<BR><BR>"
                  ><#else
                     ><#assign arapT=arapT+"&nbsp;"
                  ></#if        
                  ><#assign arapT=arapT+val            
                ><#assign br=br+br200    
              ></#if
              
              
                
      ></#list
></#if
><#if arapT!=""
      
      ><#assign arap=true
></#if

></#macro



><#macro rimTrejsing
><#assign firstRim=true
><#assign rimT=""
><#assign prvi=""
><#assign drugi=""
><#assign prvi6=""
><#assign drugi6=""

><#assign br=1

><#if f900?exists 
      ><#assign i=0
      ><#list f900 as field
            ><#assign val=""
            ><#assign allSF="@" 
              ><@field700 field/><#--
              --><#assign prvi=val
              
              ><#if f700?exists  &&  prvi!=""  
                   ><#assign j=0
                   ><#list f700 as field
                        ><#if i=j
                              ><#assign val=""
                              ><#assign allSF="@" 
                              ><@field700 field/><#--
                              --><#assign drugi=val

                              ><#if  drugi!=""
                                           ><#if firstRim
                                                  ><#assign firstRim=false 
                                                  ><#if arap
                                                          ><#assign rimT=rimT+"<BR>"
                                                  ><#else
                                                          ><#assign rimT=rimT+"<BR><BR>"
                                                  ></#if
  
                                           ></#if
                                      ><#assign roman=""
                                      ><@toRoman/><#--
                                      --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                      ><#assign br=br+1
                             ></#if
                        ></#if
                        ><#assign j=j+1
                    ></#list
              ></#if
              ><#assign i=i+1
      ></#list
></#if

><#if f701?exists 
      ><#assign duz7=0
      ><#list f701 as field
              ><#if field.ind1="1"
                    ><#assign duz7=duz7+1
              ></#if
      ></#list
></#if
><#if f901?exists 
      ><#assign duz9=0
      ><#list f901 as field
              ><#if field.ind1="1"
                    ><#assign duz9=duz9+1
              ></#if
      ></#list
></#if

><#if f901?exists 
      ><#list f901 as field
           
           ><#if field.ind1="1"    
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field700 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6" 
                ><@field700 field/><#--
                --><#assign prvi6=val
                ><#if f701?exists   &&  prvi!=""
                        ><#list f701 as field           
                           ><#if field.ind1="1"
                                     ><#assign val=""
                                     ><#assign allSF="@" 
                                     ><@field700 field/><#--
                                     --><#assign drugi=val
                                     ><#assign val=""
                                     ><#assign allSF="6" 
                                     ><@field700 field/><#--
                                     --><#assign drugi6=val

                                     ><#if drugi!="" 
                                             ><#if (prvi6!="" && drugi6=prvi6) || (duz7=1 && duz9=1)
                                                   ><#if firstRim
                                                           ><#assign firstRim=false 
                                                           ><#if arap
                                                                 ><#assign rimT=rimT+"<BR>"
                                                           ><#else
                                                                 ><#assign rimT=rimT+"<BR><BR>"
                                                           ></#if

                                                    ></#if
                                                    ><#assign roman=""
                                                    ><@toRoman/><#--
                                                    --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                                    ><#assign br=br+1
                                              ></#if
                                     ></#if

                              
                           ></#if    
              
                        ></#list
               ></#if


           ></#if    
              
              
      ></#list
></#if

   

><#if f902?exists 
      ><#assign duz9=0
      ><#list f902 as field           
          ><#if field.ind1="1" 
                ><#assign duz9=duz9+1                
          ></#if    
              
      ></#list
></#if


><#if f702?exists 
      ><#assign duz7=0
      ><#list f702 as field
           ><#if field.ind1="1" 
                ><#assign duz7=duz7+1                 
           ></#if             
      ></#list
></#if

><#if f902?exists 
      ><#list f902 as field
           ><#if field.ind1="1"
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field700 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6" 
                ><@field700 field/><#--
                --><#assign prvi6=val

                ><#if f702?exists && prvi!=""
                      ><#list f702 as field
           
                           ><#if field.ind1="1" 
                                ><#assign val=""
                                ><#assign allSF="@" 
                                ><@field700 field/><#--
                                --><#assign drugi=val
                                ><#assign val=""
                                ><#assign allSF="6" 
                                ><@field700 field/><#--
                                --><#assign drugi6=val

                                ><#if drugi!="" 
                                         ><#if (prvi6!="" && drugi6=prvi6) || (duz7=1 && duz9=1)
                                               ><#if firstRim
                                                     ><#assign firstRim=false 
                                                     ><#if arap
                                                          ><#assign rimT=rimT+"<BR>"
                                                     ><#else
                                                          ><#assign rimT=rimT+"<BR><BR>"
                                                     ></#if

                                               ></#if
                                               ><#assign roman=""
                                               ><@toRoman/><#--
                                               --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                               ><#assign br=br+1
                                        ></#if
                                ></#if

                              
                           ></#if    
              
                     ></#list
               ></#if

           ></#if    
              
              
      ></#list
></#if



><#if f910?exists 
      ><#assign i=0
      ><#list f910 as field
           
          
            ><#assign val=""
            ><#assign allSF="a" 
              ><@field710 field/><#--
              --><#assign prvi=val
              ><#if f710?exists &&  prvi!=""
                     ><#assign j=0
                     ><#list f710 as field           
                          ><#if i=j
                            ><#assign val=""
                            ><#assign allSF="a" 
                            ><@field710 field/><#--
                            --><#assign drugi=val
                            ><#if drugi!="" 
                                       ><#if firstRim
                                            ><#assign firstRim=false 
                                            ><#if arap
                                                 ><#assign rimT=rimT+"<BR>"
                                            ><#else
                                                 ><#assign rimT=rimT+"<BR><BR>"
                                            ></#if

                                       ></#if
                                       ><#assign roman=""
                                       ><@toRoman/><#--
                                       --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                       ><#assign br=br+1
                            ></#if 
                          ></#if
                          ><#assign j=j+1
                     ></#list
              ></#if

              ><#assign i=i+1  
              
      ></#list
></#if


><#if f911?exists 
      ><#assign duz9=0
      ><#list f911 as field           
          ><#if field.ind1="1" 
                ><#assign duz9=duz9+1                
          ></#if    
              
      ></#list
></#if


><#if f711?exists 
      ><#assign duz7=0
      ><#list f711 as field
           ><#if field.ind1="1" 
                ><#assign duz7=duz7+1                 
           ></#if             
      ></#list
></#if


><#if f911?exists 
      ><#list f911 as field
           ><#if field.ind1="1"          
                ><#assign val=""
                ><#assign allSF="a" 
                ><@field710 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6" 
                ><@field710 field/><#--
                --><#assign prvi6=val
                
                ><#if f711?exists &&  prvi!=""
                       ><#list f911 as field
           
                               ><#if field.ind1="1" 
                                     ><#assign val=""
                                     ><#assign allSF="a" 
                                     ><@field710 field/><#--
                                     --><#assign drugi=val
                                     ><#assign val=""
                                     ><#assign allSF="6" 
                                     ><@field710 field/><#--
                                     --><#assign drugi6=val
                                     ><#if drugi!="" 
                                           ><#if (prvi6!="" && drugi6=prvi6) || (duz7=1 && duz9=1)
                                                 ><#if firstRim
                                                       ><#assign firstRim=false 
                                                       ><#if arap
                                                              ><#assign rimT=rimT+"<BR>"
                                                       ><#else
                                                              ><#assign rimT=rimT+"<BR><BR>"
                                                       ></#if

                                                 ></#if
                                                 ><#assign roman=""
                                                 ><@toRoman/><#--
                                                 --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                                 ><#assign br=br+1
                                             ></#if

                                     ></#if              
                 
                              ></#if    
              
                       ></#list
               ></#if

           ></#if    

              
              
      ></#list
></#if




><#if f912?exists 
      ><#assign duz9=0
      ><#list f912 as field
           
          ><#if field.ind1="1" 
                ><#assign duz9=duz9+1 
                
          ></#if    
              
      ></#list
></#if

><#if f712?exists 
      ><#assign duz7=0
      ><#list f712 as field
           ><#if field.ind1="1" 
                ><#assign duz7=duz7+1          
                
           ></#if 
      ></#list
></#if

><#if f912?exists 
      ><#list f912 as field
           
          ><#if field.ind1="1" 
                ><#assign val=""
                ><#assign allSF="a" 
                ><@field710 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6" 
                ><@field710 field/><#--
                --><#assign prvi6=val 
                 
                ><#if f712?exists && prvi!=""
                       ><#list f712 as field
                               ><#if field.ind1="1"
          
                                    ><#assign val=""
                                    ><#assign allSF="a" 
                                    ><@field710 field/><#--
                                    --><#assign drugi=val
                                    ><#assign val=""
                                    ><#assign allSF="6" 
                                    ><@field710 field/><#--
                                    --><#assign drugi6=val

                                    ><#if drugi!="" 
                                           ><#if (prvi6!="" && drugi6=prvi6) || (duz7=1 && duz9=1)
                                                 ><#if firstRim
                                                        ><#assign firstRim=false 
                                                        ><#if arap
                                                               ><#assign rimT=rimT+"<BR>"
                                                        ><#else
                                                               ><#assign rimT=rimT+"<BR><BR>"
                                                        ></#if

                                                 ></#if
                                                 ><#assign roman=""
                                                 ><@toRoman/><#--
                                                 --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                                 ><#assign br=br+1
                                            ></#if

                                    ></#if

                               ></#if    

              
              
                        ></#list
                ></#if
                 
          ></#if    
              
      ></#list
></#if




><#if f960?exists 
      ><#assign duz9=0
      ><#list f960 as field
           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz9=duz9+1                 
          ></#if    
              
      ></#list
></#if


><#if f600?exists 
      ><#assign duz6=0
      ><#list f600 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz6=duz6+1
           ></#if 
      ></#list
></#if
><#if f960?exists 
      ><#list f960 as field
           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3"
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field600 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6"
                ><@field600 field/><#--
                --><#assign prvi6=val 
                ><#if f600?exists && prvi!=""
                      ><#list f600 as field
                               ><#if field.ind1="1" || field.ind1="2" || field.ind1="3"
          
                                         ><#assign val=""
                                         ><#assign allSF="@" 
                                         ><@field600 field/><#--
                                         --><#assign drugi=val
                                         ><#assign val=""
                                         ><#assign allSF="6" 
                                         ><@field600 field/><#--
                                         --><#assign drugi6=val
                                         ><#if drugi!="" 
                                                ><#if (prvi6!="" && drugi6=prvi6) || (duz6=1 && duz9=1)
                                                       ><#if firstRim
                                                              ><#assign firstRim=false 
                                                              ><#if arap
                                                                     ><#assign rimT=rimT+"<BR>"
                                                              ><#else
                                                                     ><#assign rimT=rimT+"<BR><BR>"
                                                              ></#if

                                                       ></#if
                                                       ><#assign roman=""
                                                       ><@toRoman/><#--
                                                       --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                                       ><#assign br=br+1
                                                 ></#if

                                         ></#if

                               ></#if    

              
              
                      ></#list
               ></#if             
                 
          ></#if    
              
      ></#list
></#if





><#if f601?exists 
      ><#assign duz6=0
      ><#list f601 as field           
                ><#assign duz6=duz6+1 
      ></#list
></#if

><#if f961?exists 
      ><#assign duz9=0
      ><#list f961 as field
                ><#assign duz9=duz9+1 
                
      ></#list
></#if

><#if f961?exists 
      ><#list f961 as field
                ><#assign val=""
                ><#assign allSF="a" 
                ><@field601 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6" 
                ><@field601 field/><#--
                --><#assign prvi6=val              
                ><#if f601?exists && prvi!=""
                        ><#list f601 as field          
                                  ><#assign val=""
                                  ><#assign allSF="a" 
                                  ><@field601 field/><#--
                                  --><#assign drugi=val
                                  ><#assign val=""
                                  ><#assign allSF="6" 
                                  ><@field601 field/><#--
                                  --><#assign drugi6=val  
                                  ><#if drugi!="" 
                                         ><#if (prvi6!="" && drugi6=prvi6) || (duz6=1 && duz9=1)
                                              ><#if firstRim
                                                   ><#assign firstRim=false 
                                                   ><#if arap
                                                          ><#assign rimT=rimT+"<BR>"
                                                   ><#else
                                                          ><#assign rimT=rimT+"<BR><BR>"
                                                   ></#if

                                               ></#if
                                               ><#assign roman=""
                                               ><@toRoman/><#--
                                               --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                               ><#assign br=br+1
                                         ></#if

                                  ></#if

            
                        ></#list
               ></#if
             
              
      ></#list
></#if






><#if f962?exists 
      ><#assign duz9=0
      ><#list f962 as field           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz9=duz9+1                 
          ></#if    
              
      ></#list
></#if


><#if f602?exists 
      ><#assign duz6=0
      ><#list f602 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz6=duz6+1
           ></#if
      ></#list
></#if

><#if f962?exists 
      ><#list f962 as field
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3"
                ><#assign val=""
                ><#assign allSF="af" 
                ><@field602 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6" 
                ><@field602 field/><#--
                --><#assign prvi6=val              
                ><#if f602?exists && prvi!=""
                      ><#list f602 as field
                            ><#if field.ind1="1" || field.ind1="2" || field.ind1="3"
                                    ><#assign val=""
                                    ><#assign allSF="af" 
                                    ><@field602 field/><#--
                                    --><#assign drugi=val
                                    ><#assign val=""
                                    ><#assign allSF="6" 
                                    ><@field602 field/><#--
                                    --><#assign drugi6=val
                                    ><#if drugi!="" 
                                         ><#if (prvi6!="" && drugi6=prvi6) || (duz6=1 && duz9=1)
                                             ><#if firstRim
                                                   ><#assign firstRim=false 
                                                   ><#if arap
                                                          ><#assign rimT=rimT+"<BR>"
                                                   ><#else
                                                          ><#assign rimT=rimT+"<BR><BR>"
                                                   ></#if

                                             ></#if
                                             ><#assign roman=""
                                             ><@toRoman/><#--
                                             --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                             ><#assign br=br+1
                                         ></#if

                                   ></#if
                             ></#if    

              
              
                      ></#list
                ></#if 
          ></#if    
              
      ></#list
></#if



><#if f965?exists 
      ><#assign duz9=0
      ><#list f965 as field           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz9=duz9+1
          ></#if 
      ></#list
></#if


><#if f605?exists 
      ><#assign duz6=0
      ><#list f605 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz6=duz6+1
           ></#if              
      ></#list
></#if

><#if f965?exists 
      ><#list f965 as field
           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign val=""
                ><#assign allSF="@" 
                ><@field605 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6" 
                ><@field605 field/><#--
                --><#assign prvi6=val              
                ><#if f605?exists && prvi!=""
                       ><#list f605 as field
                              ><#if field.ind1="1" || field.ind1="2" || field.ind1="3"          
                                    ><#assign val=""
                                    ><#assign allSF="@" 
                                    ><@field605 field/><#--
                                    --><#assign drugi=val
                                    ><#assign val=""
                                    ><#assign allSF="6" 
                                    ><@field605 field/><#--
                                    --><#assign drugi6=val
                                    ><#if drugi!="" 
                                             ><#if (prvi6!="" && drugi6=prvi6) || (duz6=1 && duz9=1)
                                                   ><#if firstRim
                                                          ><#assign firstRim=false 
                                                          ><#if arap
                                                               ><#assign rimT=rimT+"<BR>"
                                                          ><#else
                                                               ><#assign rimT=rimT+"<BR><BR>"
                                                          ></#if

                                                   ></#if
                                                   ><#assign roman=""
                                                   ><@toRoman/><#--
                                                   --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                                   ><#assign br=br+1
                                             ></#if

                                   ></#if
                              ></#if    

              
              
                       ></#list
               ></#if
          ></#if    
              
      ></#list
></#if

><#if f966?exists 
      ><#assign duz9=0
      ><#list f966 as field
           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz9=duz9+1 
                
          ></#if    
              
      ></#list
></#if


><#if f606?exists 
      ><#assign duz6=0
      ><#list f606 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz6=duz6+1
           ></#if
      ></#list
></#if
><#if f966?exists 
      ><#list f966 as field
           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign val=""
                ><#assign allSF="a" 
                ><@field606 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6" 
                ><@field606 field/><#--
                --><#assign prvi6=val              
                ><#if f606?exists && prvi!=""
                       ><#list f606 as field
                             ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                                   ><#assign val=""
                                   ><#assign allSF="a" 
                                   ><@field606 field/><#--
                                   --><#assign drugi=val
                                   ><#assign val=""
                                   ><#assign allSF="6" 
                                   ><@field606 field/><#--
                                   --><#assign drugi6=val
                                   ><#if drugi!="" 
                                            ><#if (prvi6!="" && drugi6=prvi6) || (duz6=1 && duz9=1)
                                                 ><#if firstRim
                                                        ><#assign firstRim=false 
                                                        ><#if arap
                                                              ><#assign rimT=rimT+"<BR>"
                                                        ><#else
                                                              ><#assign rimT=rimT+"<BR><BR>"
                                                        ></#if

                                                 ></#if
                                                 ><#assign roman=""
                                                 ><@toRoman/><#--
                                                 --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                                 ><#assign br=br+1
                                            ></#if

                                   ></#if
                             ></#if               
                       ></#list
               ></#if 
          ></#if    
              
      ></#list
></#if


><#if f967?exists 
      ><#assign duz9=0
      ><#list f967 as field           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz9=duz9+1                
          ></#if    
              
      ></#list
></#if


><#if f607?exists 
      ><#assign duz6=0
      ><#list f607 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz6=duz6+1
           ></#if 
      ></#list
></#if
><#if f967?exists 
      ><#list f967 as field
           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3"
                ><#assign val=""
                ><#assign allSF="a" 
                ><@field606 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6" 
                ><@field606 field/><#--
                --><#assign prvi6=val              
                ><#if f607?exists && prvi!=""
                      ><#list f607 as field
                           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3"          
                                ><#assign val=""
                                ><#assign allSF="a" 
                                ><@field606 field/><#--
                                --><#assign drugi=val
                                ><#assign val=""
                                ><#assign allSF="6" 
                                ><@field606 field/><#--
                                --><#assign drugi6=val
                                ><#if drugi!=""
                                       ><#if (prvi6!="" && drugi6=prvi6) || (duz6=1 && duz9=1)
                                            ><#if firstRim
                                                 ><#assign firstRim=false 
                                                 ><#if arap
                                                      ><#assign rimT=rimT+"<BR>"
                                                 ><#else
                                                      ><#assign rimT=rimT+"<BR><BR>"
                                                 ></#if

                                            ></#if
                                            ><#assign roman=""
                                            ><@toRoman/><#--
                                            --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                            ><#assign br=br+1
                                       ></#if

                               ></#if
                          ></#if
                      ></#list
               ></#if 
          ></#if    
              
      ></#list
></#if
><#if f968?exists 
      ><#assign duz9=0
      ><#list f968 as field
           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz9=duz9+1 
          ></#if    
              
      ></#list
></#if


><#if f608?exists 
      ><#assign duz6=0
      ><#list f608 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz6=duz6+1 
           ></#if 
      ></#list
></#if
><#if f968?exists 
      ><#list f968 as field           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign val=""
                ><#assign allSF="a" 
                ><@field606 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6" 
                ><@field606 field/><#--
                --><#assign prvi6=val              
                ><#if f608?exists && prvi!=""
                      ><#list f608 as field
                           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3"
                                ><#assign val=""
                                ><#assign allSF="a" 
                                ><@field606 field/><#--
                                --><#assign drugi=val
                                ><#assign val=""
                                ><#assign allSF="6" 
                                ><@field606 field/><#--
                               --><#assign drugi6=val
                               ><#if drugi!="" 
                                    ><#if (prvi6!="" && drugi6=prvi6) || (duz6=1 && duz9=1)
                                         ><#if firstRim
                                               ><#assign firstRim=false 
                                               ><#if arap
                                                     ><#assign rimT=rimT+"<BR>"
                                               ><#else
                                                    ><#assign rimT=rimT+"<BR><BR>"
                                               ></#if

                                        ></#if
                                        ><#assign roman=""
                                        ><@toRoman/><#--
                                        --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                        ><#assign br=br+1
                                   ></#if
                               ></#if
                            ></#if 
                      ></#list
              ></#if
          ></#if    
              
      ></#list
></#if

><#if f969?exists 
      ><#assign duz9=0
      ><#list f969 as field           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz9=duz9+1                 
          ></#if    
              
      ></#list
></#if

><#if f609?exists 
      ><#assign duz6=0
      ><#list f609 as field
           ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign duz6=duz6+1
           ></#if
      ></#list
></#if
><#if f969?exists 
      ><#list f969 as field           
          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3"
                ><#assign val=""
                ><#assign allSF="a" 
                ><@field606 field/><#--
                --><#assign prvi=val
                ><#assign val=""
                ><#assign allSF="6" 
                ><@field606 field/><#--
                --><#assign prvi6=val              
                ><#if f609?exists 
                     ><#list f609 as field
                          ><#if field.ind1="1" || field.ind1="2" || field.ind1="3"
                                 ><#assign val=""
                                 ><#assign allSF="a" 
                                 ><@field606 field/><#--
                                 --><#assign drugi=val
                                 ><#assign val=""
                                 ><#assign allSF="6" 
                                 ><@field606 field/><#--
                                 --><#assign drugi6=val
                                 ><#if drugi!="" && prvi!=""
                                     ><#if (prvi6!="" && drugi6=prvi6) || (duz6=1 && duz9=1)
                                          ><#if firstRim
                                                ><#assign firstRim=false 
                                                ><#if arap
                                                     ><#assign rimT=rimT+"<BR>"
                                                ><#else
                                                     ><#assign rimT=rimT+"<BR><BR>"
                                                ></#if
                                         ></#if
                                         ><#assign roman=""
                                         ><@toRoman/><#--
                                         --><#assign rimT=rimT+""+roman+".&nbsp;"+prvi+"&nbsp;V.&nbsp;"+drugi
                                         ><#assign br=br+1
                                      ></#if
                                ></#if
                           ></#if 
                      ></#list
               ></#if 
          ></#if    
              
      ></#list
></#if
><#if rimT!=""
  ><#assign  rim=true  
></#if
></#macro


><#macro letter slova num
><#assign i=1
><#list slova as sl
   ><#if i=num
        ><#assign slovo=sl
   ></#if
   ><#assign i=i+1
></#list
></#macro


><#macro predmTrejsing
><#assign firstPredm=true
><#assign past=""
><#assign num=1
><#assign slovo=""
><#assign slova=["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","r","s","t","u","v","z"]
><#assign predmT=""
         ><#if f600?exists             
              ><#list f600 as field
                  ><#if field.ind1="1" || field.ind1="2" || field.ind1="3"                             
                       ><#assign val=""
                       ><#assign allSF="@" 
                       ><@field600 field/><#--
                       --><@letter slova,num/><#--
                       --><#if val!=""
                                 ><#if firstPredm
                                       ><#assign firstPredm=false 
                                       ><#if arap || rim
                                                    ><#assign predmT=predmT+"<BR>"
                                       ><#else
                                                    ><#assign predmT=predmT+"<BR><BR>"
                                       ></#if
                                 ></#if
                                 ><#assign predmT=predmT+slovo+")&nbsp;"+val+"&nbsp;"
                                 ><#assign num=num+1
                      ></#if                
                 ></#if              
           ></#list
        ></#if
       ><#if f601?exists           
           ><#list f601 as field                             
                       ><#assign val=""
                       ><#assign allSF="@" 
                       ><@field601 field/><#--
                       --><@letter slova,num/><#--
                       --><#if val!=""
                                 ><#if firstPredm
                                       ><#assign firstPredm=false 
                                       ><#if arap || rim
                                                    ><#assign predmT=predmT+"<BR>"
                                       ><#else
                                                    ><#assign predmT=predmT+"<BR><BR>"
                                       ></#if
                                 ></#if
                                 ><#assign predmT=predmT+slovo+")&nbsp;"+val+"&nbsp;"
                                 ><#assign num=num+1
                      ></#if
          ></#list
    ></#if
   ><#if f602?exists      
          ><#list f602 as field
                ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                       ><#assign val=""
                       ><#assign allSF="@" 
                       ><@field602 field/><#--
                       --><@letter slova,num/><#--
                       --><#if val!=""
                                 ><#if firstPredm
                                       ><#assign firstPredm=false 
                                       ><#if arap || rim
                                                    ><#assign predmT=predmT+"<BR>"
                                       ><#else
                                                    ><#assign predmT=predmT+"<BR><BR>"
                                       ></#if
                                 ></#if
                                 ><#assign predmT=predmT+slovo+")&nbsp;"+val+"&nbsp;"
                                 ><#assign num=num+1
                      ></#if 
               ></#if              
          ></#list
    ></#if
   ><#if f605?exists     
          ><#list f605 as field
              ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                       ><#assign val=""
                       ><#assign allSF="@" 
                       ><@field605 field/><#--
                       --><@letter slova,num/><#--
                       --><#if val!=""
                                 ><#if firstPredm
                                       ><#assign firstPredm=false 
                                       ><#if arap || rim
                                                    ><#assign predmT=predmT+"<BR>"
                                       ><#else
                                                    ><#assign predmT=predmT+"<BR><BR>"
                                       ></#if
                                 ></#if
                                 ><#assign predmT=predmT+slovo+")&nbsp;"+val+"&nbsp;"
                                 ><#assign num=num+1
                      ></#if
            ></#if 
       ></#list
    ></#if
    ><#if f606?exists   
          ><#list f606 as field
               ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                       ><#assign val=""
                       ><#assign allSF="@" 
                       ><@field606 field/><#--
                       --><@letter slova,num/><#--
                       --><#if val!=""
                                 ><#if firstPredm
                                       ><#assign firstPredm=false 
                                       ><#if arap || rim
                                                    ><#assign predmT=predmT+"<BR>"
                                       ><#else
                                                    ><#assign predmT=predmT+"<BR><BR>"
                                       ></#if
                                 ></#if
                                 ><#assign predmT=predmT+slovo+")&nbsp;"+val+"&nbsp;"
                                 ><#assign num=num+1
                      ></#if 
              ></#if 
          ></#list
     ></#if
    ><#if f607?exists     
         ><#list f607 as field
              ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                     ><#assign val=""
                       ><#assign allSF="@" 
                       ><@field606 field/><#--
                       --><@letter slova,num/><#--
                       --><#if val!=""
                                 ><#if firstPredm
                                       ><#assign firstPredm=false 
                                       ><#if arap || rim
                                                    ><#assign predmT=predmT+"<BR>"
                                       ><#else
                                                    ><#assign predmT=predmT+"<BR><BR>"
                                       ></#if
                                 ></#if
                                 ><#assign predmT=predmT+slovo+")&nbsp;"+val+"&nbsp;"
                                 ><#assign num=num+1
                      ></#if 
              ></#if
         ></#list
    ></#if
   ><#if f608?exists       
        ><#list f608 as field
             ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                       ><#assign val=""
                       ><#assign allSF="@" 
                       ><@field606 field/><#--
                       --><@letter slova,num/><#--
                       --><#if val!=""
                                 ><#if firstPredm
                                       ><#assign firstPredm=false 
                                       ><#if arap || rim
                                                    ><#assign predmT=predmT+"<BR>"
                                       ><#else
                                                    ><#assign predmT=predmT+"<BR><BR>"
                                       ></#if
                                 ></#if
                                 ><#assign predmT=predmT+slovo+")&nbsp;"+val+"&nbsp;"
                                 ><#assign num=num+1
                      ></#if 
              ></#if               
         ></#list
   ></#if
   ><#if f609?exists 
         ><#list f609 as field
             ><#if field.ind1="1" || field.ind1="2" || field.ind1="3" 
                ><#assign val=""
                       ><#assign allSF="@" 
                       ><@field606 field/><#--
                       --><@letter slova,num/><#--
                       --><#if val!=""
                                 ><#if firstPredm
                                       ><#assign firstPredm=false 
                                       ><#if arap || rim
                                                    ><#assign predmT=predmT+"<BR>"
                                       ><#else
                                                    ><#assign predmT=predmT+"<BR><BR>"
                                       ></#if
                                 ></#if
                                 ><#assign predmT=predmT+slovo+")&nbsp;"+val+"&nbsp;"
                                 ><#assign num=num+1
                      ></#if 
              ></#if
         ></#list
   ></#if
><#if  predmT!=""
    ><#assign predm=true   
></#if  

></#macro


><#macro brojID
  
  ><#if rim || arap || predm || udc
      ><#assign brID=true
  ><#else
      ><#assign brID=false
  ></#if
></#macro

>