<#macro field001  field 
   ><#list field.sf as subField
         ><#if (allSF?index_of("b")!=-1 || allSF?index_of("@")!=-1) && subField.name="b"    
                      
                    ><#assign val=val+subField.content
        ></#if
   ></#list
></#macro



><#macro field010 field 
   ><#list field.sf as subField
         ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"
     
                       ><#assign val=val+subField.content     
         
        ></#if
  ></#list
></#macro


><#macro field011 field 
   
   ><#list field.sf as subField
        ><#if (allSF?index_of("e")!=-1 || allSF?index_of("@")!=-1) && subField.name="e"  
                     
                     ><#assign val=val+subField.content
        ></#if
  ></#list  

></#macro





><#macro field200 field 
   ><#assign past=""
   ><#assign pastA=false
   ><#list field.sf as subField
      ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"
                                 
                        ><#if pastA
                              ><#assign val=val+"&nbsp;;&nbsp;"
                        ><#else
                              ><#assign pastA=true  
                        ></#if
                        ><#assign past="a"
                        ><#assign val=val+subField.content
                      
     ></#if
     ><#if (allSF?index_of("b")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="b"
           
                        ><#assign past="b"
                        ><#assign val=val+"&nbsp;["+subField.content+"]&nbsp;"
                                     
     ></#if
     
     ><#if (allSF?index_of("c")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="c"
          
                   ><#assign past="c"
                   ><#assign val=val+".&nbsp;"+subField.content
         
     ></#if

     ><#if (allSF?index_of("d")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="d"
                   ><#assign past="d"
                   ><#assign val=val+"&nbsp;=&nbsp;"+subField.content
                  
     ></#if

     ><#if (allSF?index_of("e")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="e"
           
                       ><#assign past="e"
                       ><#assign val=val+"&nbsp;:&nbsp;"+subField.content
                    
                 
     ></#if
     ><#if (allSF?index_of("h")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="h"
          
                        ><#assign past="h"
                        ><#assign val=val+".&nbsp;"+subField.content
                  
      ></#if
      ><#if (allSF?index_of("f")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="f"
          
                    ><#assign past="f"
                    ><#assign val=val+"&nbsp;/&nbsp;"+subField.content
      ></#if

      ><#if (allSF?index_of("g")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="g" && subField.content?exists
              
                          ><#assign past="g"
                          ><#assign val=val+"&nbsp;;&nbsp;"+subField.content
            
      ></#if

      

      ><#if (allSF?index_of("i")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="i"
            
                       ><#if past="h"
                              ><#assign val=val+",&nbsp;"
                       ><#else
                              ><#assign val=val+".&nbsp;"  
                       ></#if
                       ><#assign past="i"
                       ><#assign val=val+subField.content
      ></#if

  ></#list
></#macro
><#macro field205 field 
    ><#list field.sf as subField

      ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"  && subField.content?exists
                                 
                          ><#assign val=val+subField.content
      ></#if 


     ><#if (allSF?index_of("b")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="b"
                     ><#if val!=""                            
                        ><#assign val=val+".&nbsp;"
                     ></#if
          
                     ><#assign val=val+subField.content
    ></#if         


    ><#if (allSF?index_of("d")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="d"        
                    ><#if val!=""                           
                         ><#assign val=val+"&nbsp;=&nbsp;"
                    ></#if
                    ><#assign val=val+subField.content
    ></#if

    ><#if (allSF?index_of("f")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="f"  && subField.content?exists      
                   ><#if val!=""                            
                        ><#assign val=val+"&nbsp;/&nbsp;"
                   ></#if
                   ><#assign val=val+subField.content
    ></#if

    ><#if (allSF?index_of("g")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="g"        
                   ><#if val!=""                            
                        ><#assign val=val+"&nbsp;;&nbsp;"
                   ></#if
                   ><#assign val=val+subField.content
    ></#if
    
  ></#list

></#macro






><#macro field207 field 
  ><#list field.sf as subField      
      ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"           
                      ><#assign val=val+subField.content            
                      ><#if subField.content?ends_with("-")
                            ><#assign val=val+"&nbsp;&nbsp;&nbsp;&nbsp;"
                      ></#if
      ></#if 
  ></#list

></#macro







><#macro field210 field 
  ><#assign past=""><#-- proverava koje je zadnje potpolje
  --><#assign pastA=false><#--
 
 naredni proveravaju da li se javljaju E, G, H  jer nam je potrebna bilo koja njihova pojava
 
  --><#assign pastE=false
  ><#assign pastG=false
  ><#assign pastH=false  
  ><#list field.sf as subField
    
      
      ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"           
                       ><#if pastA
                            ><#assign val=val+"&nbsp;;&nbsp;"  
                       ><#else
                            ><#assign pastA=true
                       ></#if
                       ><#assign past="a"
            
                       ><#assign val=val+subField.content
      ></#if       
      
      
      ><#if (allSF?index_of("c")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="c"
        
                    ><#if val!=""
                         ><#assign val=val+"&nbsp;:&nbsp;"  
                    ></#if
                    ><#assign past="c"
            
                    ><#assign val=val+subField.content
      ></#if

      ><#if (allSF?index_of("d")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="d"        
                ><#if val!=""
                      ><#assign val=val+",&nbsp;"  
                ></#if
                ><#assign past="d"
            
                ><#assign val=val+subField.content
                ><#if subField.content?ends_with("-")
                        ><#assign val=val+"&nbsp;&nbsp;&nbsp;&nbsp;"
                ></#if
      ></#if

      ><#if (allSF?index_of("e")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="e" && subField.content?exists
           
                     ><#if pastE
                         ><#assign val=val+"&nbsp;;&nbsp;"
                     ><#else
                         ><#assign pastE=true
                         ><#assign val=val+"&nbsp;("  
                     ></#if
                     ><#assign past="e"
            
                     ><#assign val=val+subField.content
      ></#if
      

      ><#if (allSF?index_of("g")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="g" && subField.content?exists
           
                        ><#if past="e" ||past="g" 
                               ><#assign val=val+"&nbsp;:&nbsp;"
                        ><#else
                               ><#assign val=val+"&nbsp;("  
                        ></#if
                        ><#assign past="g"
                        ><#assign pastG=true
                        ><#assign val=val+subField.content
      ></#if

      ><#if (allSF?index_of("h")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="h"
         
                    ><#if past="e" ||past="g"|| past="h"
                              ><#assign val=val+",&nbsp;"  
                    ><#else
                              ><#assign val=val+"&nbsp;("
                    ></#if
                    ><#assign past="h"
                    ><#assign pastH=true
                    ><#assign val=val+subField.content
      ></#if
      
    
  ></#list
  ><#if pastE ||pastG || pastH 
        ><#assign val=val+")&nbsp;"     
        
  ></#if

></#macro






><#macro field215 field 
  ><#assign next=""
  ><#list field.sf as subField
     
      ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"        
                   ><#assign val=val+subField.content
      ></#if       
      
      
      ><#if (allSF?index_of("c")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="c" && subField.content?exists
         
                  ><#if val!=""
                        ><#assign val=val+"&nbsp;:&nbsp;"  
                  ></#if            
                  
                  ><#assign val=val+subField.content
      ></#if

      ><#if (allSF?index_of("d")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="d"
         
                     ><#if val!=""
                          ><#assign val=val+"&nbsp;;&nbsp;"  
                     ></#if            
            
                     ><#assign val=val+subField.content
      ></#if

      ><#if (allSF?index_of("e")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="e"
         
                ><#if val!=""
                    ><#assign val=val+"&nbsp;+&nbsp;"
                ></#if           
            
                ><#assign val=val+subField.content
      ></#if
      
      ><#if (allSF?index_of("g")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="g"
           
                   ><#assign val=val+"Knj.&nbsp;"+subField.content
      ></#if

      ><#if (allSF?index_of("h")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="h"
                       
                     ><#assign val=val+",&nbsp;br.&nbsp;"+subField.content
      ></#if
      
      ><#if (allSF?index_of("i")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="i"
                     
                     ><#assign val=val+"God.&nbsp;"+subField.content
      ></#if
      
    
  ></#list 

></#macro



><#macro field225 field 
  ><#assign past=""
  ><#assign next=false
  ><#assign pom=""  
  
  ><#list field.sf as subField
      ><#if next && subField.name="v"
           ><#assign val=val+"&nbsp;;&nbsp;"
           ><#assign val=val+pom
           ><#assign next=false
           
      ><#elseif next
           ><#assign val=val+".&nbsp;"
           ><#assign next=false
           ><#assign val=val+pom
      ></#if
    
      
      ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a" && subField.content?exists
           
                 ><#assign val=val+subField.content            
                 ><#assign past="a" 
      ></#if
          
      

      ><#if (allSF?index_of("d")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="d"          
                 ><#if val!=""
                       ><#assign val=val+"&nbsp;=&nbsp;"  
                 ></#if            
            
                 ><#assign val=val+subField.content            
                 ><#assign past="d"
      ></#if

      ><#if (allSF?index_of("e")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="e"   && subField.content?exists       
                 ><#if val!=""
                      ><#assign val=val+"&nbsp;:&nbsp;"
                 ></#if
                        
                 ><#assign val=val+subField.content             
                 ><#assign past="e"
      ></#if
      
      ><#if (allSF?index_of("f")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="f"  && subField.content?exists        
                  ><#if val!=""
                       ><#assign val=val+"&nbsp;/&nbsp;"
                  ></#if           
            
                  ><#assign val=val+subField.content             
                  ><#assign past="f"
      ></#if
      
      ><#if (allSF?index_of("h")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="h"  && subField.content?exists          
                
                ><#if past!=""
                       ><#assign next=true      
                       ><#assign pom=subField.content
                ><#else
                       ><#assign val=val+subField.content
                ></#if
                ><#assign past="h"
      ></#if

      ><#if (allSF?index_of("i")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="i"  && subField.content?exists
        
                  ><#if val!=""
                      ><#if past="h"
                            ><#assign val=val+",&nbsp;"
                      ><#else
                            ><#assign val=val+".&nbsp;"  
                      ></#if
                  ></#if
                  ><#assign val=val+subField.content            
                  ><#assign past="i"
      ></#if

      ><#if (allSF?index_of("v")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="v" && subField.content?exists
        
               ><#if val!=""
                   ><#if past="h"
                        ><#assign val=val+",&nbsp;"
                   ><#else
                        ><#assign val=val+"&nbsp;;&nbsp;"
                   ></#if
               ></#if            
               ><#assign val=val+subField.content
               ><#assign past="v"
      ></#if
      
      ><#if (allSF?index_of("x")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="x"  && !next
        
                ><#if val!=""
                     ><#assign val=val+",&nbsp;"
                ></#if 
                ><#assign val=val+subField.content
      ></#if      
  
  ></#list
  ><#if next
       ><#assign val=val+".&nbsp;" 
       ><#assign val=val+pom
  ></#if

></#macro

><#macro field300 field 

  ><#list field.sf as subField

      ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"
          
                 ><#assign val=val+subField.content
     ></#if
  ></#list

></#macro


><#macro field321 field 

  ><#list field.sf as subField

    ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"
      
                 ><#assign val=val+subField.content
    ></#if

    ><#if (allSF?index_of("b")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="b"
      
                 ><#assign val=val+",&nbsp;"+subField.content
                 ><#if subField.content?ends_with("-")
                        ><#assign val=val+"&nbsp;&nbsp;&nbsp;&nbsp;"          
                 ></#if
    ></#if

   ><#if (allSF?index_of("x")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="x"
      
                ><#if val=""
                      ><#assign val=val+"ISSN"
                ><#else
                      ><#assign val=val+"&nbsp;ISSN"
                ></#if
                ><#assign val=val+subField.content
    ></#if
   
  ></#list

></#macro






><#macro field328 field 

  ><#list field.sf as subField

    ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"
        
                 ><#assign val=val+subField.content 
    ></#if

    ><#if (allSF?index_of("d")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="d"
        
                  ><#assign val=val+subField.content
    ></#if

    ><#if (allSF?index_of("e")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="e"
        
                ><#assign val=val+subField.content
    ></#if

    ><#if (allSF?index_of("f")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="f"
      
          ><#assign val=val+subField.content
    ></#if

    ><#if (allSF?index_of("g")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="g"
                
            ><#assign val=val+subField.content
    ></#if
   
  ></#list

></#macro









><#macro field500 field 

  ><#list field.sf as subField

      ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"
        
            ><#assign val=val+subField.content
      ></#if
      ><#if (allSF?index_of("b")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="b"
        
            ><#if val!=""
                             
              ><#assign val=val+".&nbsp;"
            ></#if
            ><#assign val=val+subField.content
      ></#if
      ><#if (allSF?index_of("h")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="h"
        
          ><#if val!=""
                         
              ><#assign val=val+".&nbsp;"
          ></#if
            ><#assign val=val+subField.content
          
        
      ></#if 
      ><#if (allSF?index_of("i")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="i"
        
            ><#if val!=""
                             
              ><#assign val=val+".&nbsp;"
            ></#if
            ><#assign val=val+subField.content
      ></#if 
      ><#if (allSF?index_of("k")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="k"
        
            ><#if val!=""
                             
              ><#assign val=val+".&nbsp;"
            ></#if
            ><#assign val=val+subField.content
      ></#if 
      ><#if (allSF?index_of("l")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="l"
        
            ><#if val!=""
                              
              ><#assign val=val+".&nbsp;"
            ></#if
            ><#assign val=val+subField.content
      ></#if 
      ><#if (allSF?index_of("m")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="m"
        
            ><#assign val=val+"&nbsp;["+subField.content+"]&nbsp;"
      ></#if   
    

  ></#list
 
></#macro

><#macro field503 field 

  ><#list field.sf as subField

      ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"
        
            ><#assign val=val+subField.content
      ></#if 
    
      ><#if (allSF?index_of("b")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="b"
      
            ><#if val!=""                           
                    ><#assign val=val+".&nbsp;"
            ></#if
            ><#assign val=val+subField.content
      ></#if  
      ><#if (allSF?index_of("j")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="j"
      
            ><#assign val=val+"&nbsp;("+subField.content+")&nbsp;"
      ></#if

    
    
  ></#list

></#macro
><#macro field532 field 

  ><#list field.sf as subField

    ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"
             
          ><#assign val=val+subField.content
    ></#if
    
  ></#list

></#macro



><#macro field600 field 

   ><#list field.sf as subField
     ><#if allSF?index_of("6")!=-1 &&  subField.name="6"      
            ><#assign val=subField.content
            ><#return
     ></#if 
     ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"       
            ><#assign val=val+subField.content
     ></#if
     ><#if (allSF?index_of("b")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="b"       
           ><#assign val=val+",&nbsp;"+subField.content
     ></#if
     
     ><#if (allSF?index_of("c")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="c"       
           ><#assign val=val+",&nbsp;"+subField.content
     ></#if

     ><#if (allSF?index_of("d")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="d"       
           ><#assign val=val+"&nbsp;"+subField.content
     ></#if

      ><#if (allSF?index_of("f")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="f"        
            ><#assign val=val+"&nbsp;"+subField.content
      ></#if
      
      ><#if (allSF?index_of("x")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="x"       
           ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

      ><#if (allSF?index_of("y")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="y"        
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

      ><#if (allSF?index_of("z")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="z"
        
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

      ><#if (allSF?index_of("w")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="w"
                   
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

  ></#list
></#macro

><#macro field601 field 
   ><#assign open=false
   ><#assign pastE=false
   
   ><#list field.sf as subField
     ><#if allSF?index_of("6")!=-1 &&  subField.name="6"      
          ><#assign val=subField.content
          ><#return
     ></#if 
     ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"       
            ><#assign val=val+subField.content
     ></#if
     ><#if (allSF?index_of("b")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="b"       
           ><#assign val=val+".&nbsp;"+subField.content
     ></#if     
     ><#if (allSF?index_of("c")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="c"       
           ><#assign val=val+"&nbsp;("+subField.content+")&nbsp;"
     ></#if
     ><#if (allSF?index_of("d")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="d"       
           ><#assign val=val+"&nbsp;("+subField.content
           ><#assign open=true
     ></#if

      ><#if (allSF?index_of("e")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="e"
                  
            ><#if pastE=true
              ><#assign val=val+",&nbsp;"
            ><#else
               ><#if open
                  ><#assign val=val+"&nbsp;;&nbsp;"
               ><#else
                  ><#assign val=val+"&nbsp;("
                  ><#assign open=true
               ></#if
            ></#if
            ><#assign val=val+subField.content
            ><#assign pastE=true
      ></#if

      ><#if (allSF?index_of("f")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="f"
                    
            ><#if open
                  ><#assign val=val+"&nbsp;;&nbsp;"
            ><#else
                  ><#assign val=val+"&nbsp;("
                  ><#assign open=true
            ></#if
            ><#assign val=val+subField.content
      ></#if
      
      ><#if (allSF?index_of("g")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="g"
                   
            ><#if open
                  ><#assign val=val+").&nbsp;"
                  ><#assign open=false
            ><#else
                  ><#assign val=val+".&nbsp;"
                  
            ></#if
            ><#assign val=val+subField.content
      ></#if

      ><#if (allSF?index_of("h")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="h"
                    
            ><#if open
                  ><#assign val=val+").&nbsp;"
                  ><#assign open=false
            ><#else
                  ><#assign val=val+".&nbsp;"
                  
            ></#if
            ><#assign val=val+subField.content
      ></#if

      ><#if (allSF?index_of("x")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="x"
       
           ><#if open
                  ><#assign val=val+")"
                  ><#assign open=false
            
            ></#if 
          
           ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if


      ><#if (allSF?index_of("y")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="y"
        
            ><#if open
                  ><#assign val=val+")"
                  ><#assign open=false
            
            ></#if 
            
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

      ><#if (allSF?index_of("z")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="z"
        
            ><#if open
                  ><#assign val=val+")"
                  ><#assign open=false
            
            ></#if 
            
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

      ><#if (allSF?index_of("w")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="w"
        
            ><#if open
                  ><#assign val=val+")"
                  ><#assign open=false
            
            ></#if             
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if
      
  ></#list
  ><#if open
       ><#assign val=val+")"            
            
  ></#if 
></#macro



><#macro field602 field 

   ><#list field.sf as subField
     ><#if allSF?index_of("6")!=-1 &&  subField.name="6"      
          ><#assign val=subField.content
          ><#return
     ></#if 
     ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"
       
            ><#assign val=val+subField.content
     ></#if
     
      ><#if (allSF?index_of("f")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="f"
        
            ><#assign val=val+",&nbsp;"+subField.content
      ></#if
      
      ><#if (allSF?index_of("x")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="x"
       
           ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if


      ><#if (allSF?index_of("y")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="y"
        
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

      ><#if (allSF?index_of("z")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="z"
        
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

      ><#if (allSF?index_of("w")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="w"
                
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

  ></#list
></#macro




><#macro field605 field 

   ><#list field.sf as subField
     ><#if allSF?index_of("6")!=-1 &&  subField.name="6"      
          ><#assign val=subField.content
          ><#return
     ></#if 
     ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"       
            ><#assign val=val+subField.content
     ></#if
     ><#if (allSF?index_of("h")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="h"       
           ><#assign val=val+".&nbsp;"+subField.content
     ></#if
     
     ><#if (allSF?index_of("i")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="i"       
           ><#assign val=val+".&nbsp;"+subField.content
     ></#if

     ><#if (allSF?index_of("k")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="k"       
           ><#assign val=val+".&nbsp;"+subField.content
     ></#if

      ><#if (allSF?index_of("l")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="l"
        
            ><#assign val=val+".&nbsp;"+subField.content
      ></#if
      
      ><#if (allSF?index_of("m")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="m"
       
           ><#assign val=val+"&nbsp;["+subField.content+"]&nbsp;"
      ></#if
      
      ><#if (allSF?index_of("n")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="n"
       
           ><#assign val=val+"&nbsp;["+subField.content+"]&nbsp;"
      ></#if
   
      ><#if (allSF?index_of("q")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="q"
       
           ><#assign val=val+"&nbsp;["+subField.content+"]&nbsp;"
      ></#if

      ><#if (allSF?index_of("x")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="x"
        
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if


      ><#if (allSF?index_of("y")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="y"
        
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

      ><#if (allSF?index_of("z")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="z"
        
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

      ><#if (allSF?index_of("w")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="w"
                   
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

  ></#list
></#macro

><#macro field606 field 

   ><#list field.sf as subField
      ><#if allSF?index_of("6")!=-1  &&  subField.name="6"      
          ><#assign val=subField.content
          ><#return
      ></#if 
      ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"
            ><#assign val=val+subField.content
      ></#if

      ><#if (allSF?index_of("x")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="x"       
           ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if


      ><#if (allSF?index_of("y")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="y"        
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if
      
      ><#if (allSF?index_of("z")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="z"        
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if  
      ><#if (allSF?index_of("w")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="w"        
            ><#assign val=val+"&nbsp;-&nbsp;"+subField.content
      ></#if

  ></#list
></#macro


><#macro field700 field 
  ><#list field.sf as subField
    ><#if allSF?index_of("6")!=-1  &&  subField.name="6"      
          ><#assign val=subField.content
          ><#return
    ></#if 
    ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"      
          ><#assign val=val+subField.content
    ></#if 
    ><#if (allSF?index_of("b")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="b"      
          ><#assign val=val+",&nbsp;"+subField.content
    ></#if
    ><#if (allSF?index_of("c")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="c"      
          ><#assign val=val+",&nbsp;"+subField.content
    ></#if
    ><#if (allSF?index_of("d")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="d"      
          ><#assign val=val+"&nbsp;"+subField.content
    ></#if
    ><#if (allSF?index_of("f")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="f"      
          ><#assign val=val+"&nbsp;"+subField.content
    ></#if

  ></#list
 
></#macro

><#macro field710 field 
  ><#assign open=false
  ><#assign pastE=false
  ><#assign past=""
  ><#list field.sf as subField
    ><#if allSF?index_of("6")!=-1  &&  subField.name="6"      
          ><#assign val=subField.content
          ><#return
    ></#if 
    ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"      
          ><#assign val=val+subField.content
          ><#assign past="a"
    ></#if     
    ><#if (allSF?index_of("b")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="b"      
          ><#assign val=val+".&nbsp;"+subField.content
          ><#assign past="b"
    ></#if
    ><#if (allSF?index_of("c")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="c"
          ><#assign past="c"      
          ><#assign val=val+"&nbsp;("+subField.content+")&nbsp;"
    ></#if

    ><#if (allSF?index_of("d")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="d"
          ><#assign past="d"      
          ><#assign val=val+"&nbsp;("+subField.content+";"
    ></#if

    ><#if (allSF?index_of("e")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="e"
          ><#assign past="e"
          ><#if pastE
            ><#assign val=val+",&nbsp;"
          ><#else            
            ><#if open
              ><#assign val=val+"&nbsp;;&nbsp;"
            ><#else
              ><#assign val=val+"&nbsp;("
              ><#assign open=true
            ></#if 
          ></#if
          ><#assign val=val+subField.content
          ><#assign pastE=false
    ></#if

    ><#if (allSF?index_of("f")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="f"
          ><#assign past="f"      
          ><#if open
            ><#assign val=val+"&nbsp;;&nbsp;"
          ><#else
            ><#assign val=val+"&nbsp;("
            ><#assign open=true
          ></#if
          ><#assign val=val+subField.content
    ></#if

    ><#if (allSF?index_of("g")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="g"          
          ><#if open
            ><#assign val=val+")&nbsp;"
            ><#assign open=false
          ></#if
          ><#if past="c"
            ><#assign val=val+"&nbsp;"
          ><#else
            ><#assign val=val+".&nbsp;"
          ></#if 
          ><#assign val=val+subField.content
          ><#assign past="g"
    ></#if
    ><#if (allSF?index_of("h")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="h"      
          ><#if open
            ><#assign val=val+")&nbsp;"
            ><#assign open=false
          ></#if
          ><#assign val=val+".&nbsp;"+subField.content
          ><#assign past="h"
    ></#if
    

  ></#list
  ><#if open
      ><#assign val=val+")"      
    ></#if

></#macro

><#macro field720 field 

  ><#list field.sf as subField  

    ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"
      
          ><#assign val=val+subField.content
    ></#if
            
    ><#if (allSF?index_of("f")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="f"
      
          ><#assign val=val+",&nbsp;"+subField.content
    ></#if

  ></#list



></#macro



><#macro field997 field 
   
   ><#list field.sf as subField 
     ><#if (allSF?index_of("j")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="j"
       
           ><#assign val=val+subField.content
     ></#if
     

     ><#if (allSF?index_of("k")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="k"
       
           ><#assign val=val+"&nbsp;("+subField.content+")&nbsp"
     ></#if

     ><#if (allSF?index_of("m")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="m"
       
           ><#assign val=val+"&nbsp;:&nbsp;"+subField.content
     ></#if     

  ></#list
></#macro




><#macro field996 field 
  
   ><#list field.sf as subField 
     ><#if (allSF?index_of("d")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="d"
  
         
              ><#list subField.ssf as subSubField    
            
            
                 ><#if subSubField.name="u"
                      ><#assign val=val+subSubField.content                 
                 ></#if
                 
             ></#list
        
   ></#if
   ><#if (allSF?index_of("f")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="f"            
                  ><#assign val=val+subField.content            
   ></#if 
   ><#if (allSF?index_of("o")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="o"            
                  ><#assign val=val+subField.content            
   ></#if   
   ><#if (allSF?index_of("s")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="s"            
                  ><#assign val=val+subField.content            
   ></#if     
   ><#if (allSF?index_of("v")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="v"            
                  ><#assign val=val+subField.content            
   ></#if 
   ><#if (allSF?index_of("3")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="3"            
                  ><#assign val=val+subField.content            
   ></#if 
   ><#if (allSF?index_of("r")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="r"            
                  ><#assign val=val+subField.content            
   ></#if 
   ><#if (allSF?index_of("q")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="q"            
                  ><#assign val=val+subField.content            
   ></#if 
   ><#if (allSF?index_of("w")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="w"            
                  ><#assign val=val+subField.content            
   ></#if                
  ></#list
></#macro
><#macro field998 field 
  
   ><#list field.sf as subField 
     
       ><#if (allSF?index_of("a")!=-1 || allSF?index_of("@")!=-1) &&  subField.name="a"            
                  ><#assign val=val+subField.content            
       ></#if    
                  
   ></#list
></#macro


><#macro field421 fieldSec
><#assign val=""
><#assign val421=""
><#assign first=true
><#list fieldSec.sf as subField
><#if subField.content="200"  
  ><#assign allSF="aefg" 
  ><#assign val=""
  ><#assign field=subField.secField
  ><@field200 field/><#--
  --><#assign val421=val421+val  
><#elseif subField.content="205"
  ><#assign val=""
  ><#assign allSF="a"
  ><#assign field=subField.secField 
  ><@field205 field/><#--
  --><#if val421!=""    
       ><#assign val421=val421+".&nbsp;-&nbsp;"+val
  ><#else
       ><#assign val421=val
    
  ></#if
         
><#elseif subField.content="210"
  ><#assign val=""
  ><#assign allSF="d" 
  ><#assign field=subField.secField
  ><@field210 field/><#--
  --><#if val421!=""    
       ><#assign val421=val421+".&nbsp;-&nbsp;"+val
  ><#else
       ><#assign val421=val
    
  ></#if
><#elseif subField.content="215"
  ><#assign val=""
  ><#assign allSF="acd" 
  ><#assign field=subField.secField
  ><@field215 field/><#--
--><#if val!=""
    ><#if val421=""
       ><#assign val421=val
    ><#else 
       ><#assign val421=val421+".&nbsp;-&nbsp;"+val
    ></#if
  ></#if

><#elseif subField.content="225"
  ><#assign val=""
  ><#assign allSF="@" 
  ><#assign field=subField.secField
  ><@field225 field/><#--
--><#if val!=""
    ><#if val421=""
       ><#assign val421=val
    ><#else 
       ><#assign val421=val421+".&nbsp;-&nbsp;"+val
    ></#if
  ></#if

><#elseif subField.content="300" || subField.content="314" || subField.content="320" ||
subField.content="322" || subField.content="323" || subField.content="324" || subField.content="326" ||
subField.content="328" || subField.content="330" 
  ><#assign val=""
  ><#assign allSF="a" 
  ><#assign field=subField.secField
  ><@field300 field/><#--
--><#if val!=""
    ><#if val421=""
       ><#assign val421=val
    ><#else 
       ><#assign val421=val421+".&nbsp;-&nbsp;"+val
    ></#if
  ></#if

><#elseif subField.content="321" || subField.content="327"
  ><#assign val=""
  ><#assign allSF="a" 
  ><#assign field=subField.secField
  ><@field200 field/><#--
--><#if val!=""
    ><#if val421=""
       ><#assign val421=val
    ><#else 
       ><#assign val421=val421+".&nbsp;-&nbsp;"+val
    ></#if
  ></#if

></#if    
></#list
><#assign val=val421
></#macro


><#macro getFieldAuthor fieldSec

><#assign i=0

><#list fieldSec.sf as subField
   ><#if (subField.content="700" || subField.content="701" || subField.content="702") 
       ><#if pos=i
            ><#assign val=""
            ><#assign allSF="@" 
            ><#assign field=subField.secField
            ><@field700 field/><#--
            --><#return
       ></#if
       ><#assign i=i+1 
   ><#elseif subField.content="710" || subField.content="711" || subField.content="712"
       ><#if pos=i
            ><#assign val=""
            ><#assign allSF="@"
            ><#assign field=subField.secField 
            ><@field710 field/><#--
            --><#return  
       ></#if
       ><#assign i=i+1 
   ><#elseif subField.content="503" 
       ><#if pos=i 
            ><#assign val=""
            ><#assign allSF="@" 
            ><#assign field=subField.secField
            ><@field710 field/><#--
            --><#return
       ></#if 
       ><#assign i=i+1 
   ></#if
   
></#list

></#macro







><#macro fieldAuthor fieldSec brA
><#assign brA=0
><#list fieldSec.sf as subField
   ><#if subField.content="700" || subField.content="701" || subField.content="702" ||
      subField.content="710" || subField.content="711" || subField.content="712" ||
      subField.content="503"
      
     ><#assign brA=brA+1
       
  ></#if
   
></#list

></#macro




><#assign br200=0

><#macro field423 fieldSec br

><#assign pom=""

><#assign pomBR=br
><#assign v200=0
><#assign v500=0
><#assign pos=0
><#assign brF=0

><#list fieldSec.sf as subField
   ><#if subField.content="200"
       ><#assign v200=v200+1       
  ></#if  
  ><#assign brF=brF+1 
></#list


><#list fieldSec.sf as subField
   ><#if subField.content="500"
       ><#assign v500=v500+1       
  ></#if   
></#list
><#assign brA=0
><@fieldAuthor fieldSec,brA/><#-- 
--><#if (v200>=1)
    ><#assign br200=v200
><#elseif (v500>0)
    ><#assign br200=v500
><#elseif (brF>0) 
  ><#if (brA>0)
        ><#assign val=val+f423mustHave200
        ><#return
  ><#else
        ><#assign val=val+f423content
        ><#return   
  ></#if 
></#if
><#assign h=0
><#list fieldSec.sf as subField
   ><#if subField.content="700" || subField.content="701" || subField.content="702" ||
      subField.content="710" || subField.content="711" || subField.content="712" ||
      subField.content="503"
      ><#if h=0
         ><#assign val=val+f423wrongFieldOrder
         ><#return
      ><#else
         ><#assign pos=pos+1
      ></#if
   ><#elseif subField.content="200" || subField.content="500"
       ><#assign autor=""
       ><#if (brA>0)
            ><#assign val=""
            ><@getFieldAuthor fieldSec/><#-- trazi autora broj pos
            --><#assign autor=val
            
       ></#if
       ><#if subField.content="200"
           ><#assign val=""
           ><#assign allSF="@" 
           ><#assign field=subField.secField
           ><@field200 field/><#--
           --><#if val!=""
                      
                      ><#if pom!=""
                             ><#assign pom=pom+"&nbsp;"
                      ></#if
                      ><#assign pom=pom+pomBR+".&nbsp;"+autor
                      ><#if autor!=""
                            ><#assign pom=pom+":&nbsp;"
                      ></#if
                      ><#assign pom=pom+val
                      ><#assign pomBR=pomBR+1
             ></#if
       ><#elseif subField.content="500"
            ><#assign val=""
            ><#assign allSF="@"
            ><#assign field=subField.secField 
            ><@field500 field/><#--
            --><#if val!=""
                      
                      ><#if pom!=""
                             ><#assign pom=pom+"&nbsp;"
                      ></#if
                      ><#assign pom=pom+pomBR+".&nbsp;"+autor
                      ><#if autor!=""
                            ><#assign pom=pom+":&nbsp;"
                      ></#if
                      ><#assign pom=pom+val
                      ><#assign pomBR=pomBR+1
             ></#if
      ></#if
  

  ></#if
  ><#assign h=h+1
   
></#list

><#assign val=pom
></#macro


><#macro field423Analitika fieldSec 

><#assign pom=""

><#assign v200=0
><#assign v500=0
><#assign pos=0
><#assign brF=0

><#list fieldSec.sf as subField
   ><#if subField.content="200"
       ><#assign v200=v200+1       
  ></#if  
  ><#assign brF=brF+1 
></#list


><#list fieldSec.sf as subField
   ><#if subField.content="500"
       ><#assign v500=v500+1       
  ></#if   
></#list
><#assign brA=0
><@fieldAuthor fieldSec, brA/><#-- 
--><#if (v200>0)
    ><#assign br200=v200
><#elseif (v500>0)
    ><#assign br200=v500
><#elseif (brF>0) 
  ><#if (brA>0)
        ><#assign val=val+f423mustHave200
        ><#return
  ><#else
        ><#assign val=val+f423content
        ><#return   
  ></#if 
></#if
><#assign h=0
><#list fieldSec.sf as subField
   ><#if subField.content="700" || subField.content="701" || subField.content="702" ||
      subField.content="710" || subField.content="711" || subField.content="712" ||
      subField.content="503"
      ><#if h=0
         ><#assign val=val+f423wrongFieldOrder
         ><#return
      ><#else
         ><#assign pos=pos+1
      ></#if
   ><#elseif subField.content="200" || subField.content="500"
       ><#assign autor=""
       ><#if (brA>0)
            ><#assign val=""
            ><@getFieldAuthor fieldSec/><#--  trazi autora broj pos
            --><#assign autor=val
            
       ></#if
       ><#if subField.content="200"
           ><#assign val=""
           ><#assign allSF="@" 
           ><#assign field=subField.secField
           ><@field200 field/><#--
           --><#if val!=""
                      
                      ><#if pom!=""
                             ><#assign pom=pom+"<split423>"
                      ></#if
                      ><#assign pom=pom+autor
                      ><#if autor!=""
                            ><#assign pom=pom+":&nbsp;"
                      ></#if
                      ><#assign pom=pom+val
                      
                      
             ></#if

       ><#elseif subField.content="500"
            ><#assign val=""
            ><#assign allSF="@" 
            ><#assign field=subField.secField
            ><@field500 field/><#--
            --><#if val!=""
                      
                      ><#if pom!=""                              
                             ><#assign pom=pom+"<split423>"
                      ></#if
                      ><#assign pom=pom+autor
                      ><#if autor!=""
                            ><#assign pom=pom+":&nbsp;"
                      ></#if
                      ><#assign pom=pom+val
                      
                      
             ></#if
      ></#if
  

  ></#if
  ><#assign h=h+1
   
></#list

><#assign val=pom
></#macro






><#macro field469 fieldSec

><#assign val469=""
><#list fieldSec.sf as subField 
    ><#if subField.content="200"
          ><#assign val=""
          ><#assign allSF="aefg" 
          ><#assign field=subField.secField
          ><@field200 field/><#--
          --><#if val!=""
                  ><#assign val469=val469+val
    
          ></#if
    ><#elseif subField.content="215"
                ><#assign val=""
                ><#assign allSF="a" 
                ><#assign field=subField.secField
                ><@field215 field/><#--
                --><#if val!=""    
                         ><#assign val469=val469+"&nbsp;("+val+")&nbsp;"
                ></#if
    ><#elseif subField.content="300" || subField.content="314" || subField.content="320" || subField.content="321" ||
         subField.content="322" || subField.content="323" || subField.content="324" || 
         subField.content="328" 
          ><#assign val=""
          ><#assign allSF="a" 
          ><#assign field=subField.secField
           ><@field300 field/><#--
           --><#if val!=""
                    ><#if val469=""
                          ><#assign val469=val
                    ><#else 
                          ><#assign val469=val469+".&nbsp;-&nbsp;"+val
                    ></#if
            ></#if

    ></#if    
></#list
><#assign val=val469
></#macro






>