<#include "_koncept_general.ftl"
<#--mart 2008. bojana -->
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
><#macro odrediBrojSigli primerak
 ><#if primerak.status="9"
 	<#-- broji rashodovane primerke -->
   ><#if sigla?starts_with("00")
      ><#assign s0R=s0R+1
   ><#elseif sigla?starts_with("01")
      ><#assign s1R=s1R+1
   ><#elseif sigla?starts_with("02")
      ><#assign s2R=s2R+1 
   ><#elseif sigla?starts_with("03")
      ><#assign s3R=s3R+1 
   ><#elseif sigla?starts_with("04")
      ><#assign s4R=s4R+1 
   ><#elseif sigla?starts_with("05")
      ><#assign s5R=s5R+1 
   ><#elseif sigla?starts_with("06")
      ><#assign s6R=s6R+1
   ><#elseif sigla?starts_with("07")
      ><#assign s7R=s7R+1  
   ><#elseif sigla?starts_with("08")
      ><#assign s8R=s8R+1  
   ><#elseif sigla?starts_with("09")
      ><#assign s9R=s9R+1 
   ><#elseif sigla?starts_with("10")
      ><#assign s10R=s10R+1
   ><#elseif sigla?starts_with("11")
      ><#assign s11R=s11R+1
   ><#elseif sigla?starts_with("12")
      ><#assign s12R=s12R+1 
   ><#elseif sigla?starts_with("13")
      ><#assign s13R=s13R+1 
   ><#elseif sigla?starts_with("14")
      ><#assign s14R=s14R+1 
   ><#elseif sigla?starts_with("15")
      ><#assign s15R=s15R+1 
   ><#elseif sigla?starts_with("16")
      ><#assign s16R=s16R+1
   ><#elseif sigla?starts_with("17")
      ><#assign s17R=s17R+1  
   ><#elseif sigla?starts_with("18")
      ><#assign s18R=s18R+1  
   ><#elseif sigla?starts_with("19")
      ><#assign s19R=s19R+1   
   ><#elseif sigla?starts_with("20")
      ><#assign s20R=s20R+1
   ><#elseif sigla?starts_with("21")
      ><#assign s21R=s21R+1
   ><#elseif sigla?starts_with("22")
      ><#assign s22R=s22R+1 
   ><#elseif sigla?starts_with("23")
      ><#assign s23R=s23R+1 
   ><#elseif sigla?starts_with("24")
      ><#assign s24R=s24R+1 
   ><#elseif sigla?starts_with("25")
      ><#assign s25R=s25R+1 
   ><#elseif sigla?starts_with("26")
      ><#assign s26R=s26R+1
   ><#elseif sigla?starts_with("27")
      ><#assign s27R=s27R+1  
   ><#elseif sigla?starts_with("28")
      ><#assign s28R=s28R+1  
   ><#elseif sigla?starts_with("29")
      ><#assign s29R=s29R+1  
   ><#elseif sigla?starts_with("30")
      ><#assign s30R=s30R+1
   ><#elseif sigla?starts_with("31")
      ><#assign s31R=s31R+1
   ><#elseif sigla?starts_with("32")
      ><#assign s32R=s32R+1 
   ><#elseif sigla?starts_with("33")
      ><#assign s33R=s33R+1 
   ><#else 
      ><#assign s34R=s34R+1 
   ></#if   
 ><#else
   ><#if sigla?starts_with("00")
      ><#assign s0=s0+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i0="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i0="("+primerak.status+")" 
         ><#else
           ><#assign i0="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
                  
      ></#if
      
   ><#elseif sigla?starts_with("01")
      ><#assign s1=s1+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i1="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i1="("+primerak.status+")" 
         ><#else
           ><#assign i1="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("02")
      ><#assign s2=s2+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i2="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i2="("+primerak.status+")" 
         ><#else
           ><#assign i2="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("03")
      ><#assign s3=s3+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i3="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i3="("+primerak.status+")" 
         ><#else
           ><#assign i3="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("04")
      ><#assign s4=s4+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i4="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i4="("+primerak.status+")" 
         ><#else
           ><#assign i4="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("05")
      ><#assign s5=s5+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i5="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i5="("+primerak.status+")" 
         ><#else
           ><#assign i5="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("06")
      ><#assign s6=s6+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i6="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i6="("+primerak.status+")" 
         ><#else
           ><#assign i6="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("07")
      ><#assign s7=s7+1  
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i7="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i7="("+primerak.status+")" 
         ><#else
           ><#assign i7="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("08")
      ><#assign s8=s8+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i8="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i8="("+primerak.status+")" 
         ><#else
           ><#assign i8="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if  
   ><#elseif sigla?starts_with("09")
      ><#assign s9=s9+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i9="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i9="("+primerak.status+")" 
         ><#else
           ><#assign i9="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("10")
      ><#assign s10=s10+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i10="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i10="("+primerak.status+")" 
         ><#else
           ><#assign i10="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("11")
      ><#assign s11=s11+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i11="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i11="("+primerak.status+")" 
         ><#else
           ><#assign i11="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("12")
      ><#assign s12=s12+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i12="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i12="("+primerak.status+")" 
         ><#else
           ><#assign i12="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if 
   ><#elseif sigla?starts_with("13")
      ><#assign s13=s13+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i13="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i13="("+primerak.status+")" 
         ><#else
           ><#assign i13="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("14")
      ><#assign s14=s14+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i14="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i14="("+primerak.status+")" 
         ><#else
           ><#assign i14="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("15")
      ><#assign s15=s15+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i15="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i15="("+primerak.status+")" 
         ><#else
           ><#assign i15="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if 
   ><#elseif sigla?starts_with("16")
      ><#assign s16=s16+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i16="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i16="("+primerak.status+")" 
         ><#else
           ><#assign i16="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("17")
      ><#assign s17=s17+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i17="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i17="("+primerak.status+")" 
         ><#else
           ><#assign i17="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if 
   ><#elseif sigla?starts_with("18")
      ><#assign s18=s18+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i18="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i18="("+primerak.status+")" 
         ><#else
           ><#assign i18="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if 
   ><#elseif sigla?starts_with("19")
      ><#assign s19=s19+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i19="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i19="("+primerak.status+")" 
         ><#else
           ><#assign i19="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if  
   ><#elseif sigla?starts_with("20")
      ><#assign s20=s20+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i20="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i20="("+primerak.status+")" 
         ><#else
           ><#assign i20="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("21")
      ><#assign s21=s21+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i21="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i21="("+primerak.status+")" 
         ><#else
           ><#assign i21="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("22")
      ><#assign s22=s22+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i22="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i22="("+primerak.status+")" 
         ><#else
           ><#assign i22="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if 
   ><#elseif sigla?starts_with("23")
      ><#assign s23=s23+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i23="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i23="("+primerak.status+")" 
         ><#else
           ><#assign i23="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("24")
      ><#assign s24=s24+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i24="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i24="("+primerak.status+")" 
         ><#else
           ><#assign i24="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if 
   ><#elseif sigla?starts_with("25")
      ><#assign s25=s25+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i25="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i25="("+primerak.status+")" 
         ><#else
           ><#assign i25="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("26")
      ><#assign s26=s26+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i26="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i26="("+primerak.status+")" 
         ><#else
           ><#assign i26="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("27")
      ><#assign s27=s27+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i27="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i27="("+primerak.status+")" 
         ><#else
           ><#assign i27="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if 
   ><#elseif sigla?starts_with("28")
      ><#assign s28=s28+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i28="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i28="("+primerak.status+")" 
         ><#else
           ><#assign i28="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if 
   ><#elseif sigla?starts_with("29")
      ><#assign s29=s29+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i29="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i29="("+primerak.status+")" 
         ><#else
           ><#assign i29="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if  
   ><#elseif sigla?starts_with("30")
      ><#assign s30=s30+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i30="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i30="("+primerak.status+")" 
         ><#else
           ><#assign i30="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("31")
      ><#assign s31=s31+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i31="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i31="("+primerak.status+")" 
         ><#else
           ><#assign i31="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("32")
      ><#assign s32=s32+1 
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i32="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i32="("+primerak.status+")" 
         ><#else
           ><#assign i32="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if
   ><#elseif sigla?starts_with("33")
      ><#assign s33=s33+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i33="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i33="("+primerak.status+")" 
         ><#else
           ><#assign i33="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if 
   ><#else 
      ><#assign s34=s34+1
      ><#if primerak.status!="" || primerak.odeljenje!=""
         ><#if primerak.status=""
           ><#assign i34="("+primerak.odeljenje+")" 
         ><#elseif primerak.odeljenje=""
           ><#assign i34="("+primerak.status+")" 
         ><#else
           ><#assign i34="("+primerak.status+"&nbsp;/&nbsp;"+primerak.odeljenje+")" 
         ></#if
      ></#if 
   ></#if   
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
 
 <#-- strigovi koji se nalaze u zagradama-->
 ><#assign i0=""
 ><#assign i1=""
 ><#assign i2=""
 ><#assign i3=""
 ><#assign i4=""
 ><#assign i5=""
 ><#assign i6=""
 ><#assign i7=""
 ><#assign i8=""
 ><#assign i9=""
 ><#assign i10=""
 ><#assign i11=""
 ><#assign i12=""
 ><#assign i13=""
 ><#assign i14=""
 ><#assign i15=""
 ><#assign i16=""
 ><#assign i17=""
 ><#assign i18=""
 ><#assign i19=""
 ><#assign i20=""
 ><#assign i21=""
 ><#assign i22=""
 ><#assign i23=""
 ><#assign i24=""
 ><#assign i25=""
 ><#assign i26=""
 ><#assign i27=""
 ><#assign i28=""
 ><#assign i29=""
 ><#assign i30=""
 ><#assign i31=""
 ><#assign i32=""
 ><#assign i33=""
 ><#assign i34=""
 
 ><#assign s0R=0
 ><#assign s1R=0
 ><#assign s2R=0
 ><#assign s3R=0
 ><#assign s4R=0
 ><#assign s5R=0
 ><#assign s6R=0
 ><#assign s7R=0
 ><#assign s8R=0
 ><#assign s9R=0
 ><#assign s10R=0
 ><#assign s11R=0
 ><#assign s12R=0
 ><#assign s13R=0
 ><#assign s14R=0
 ><#assign s15R=0
 ><#assign s16R=0
 ><#assign s17R=0
 ><#assign s18R=0
 ><#assign s19R=0
 ><#assign s20R=0
 ><#assign s21R=0
 ><#assign s22R=0
 ><#assign s23R=0
 ><#assign s24R=0
 ><#assign s25R=0
 ><#assign s26R=0
 ><#assign s27R=0
 ><#assign s28R=0
 ><#assign s29R=0
 ><#assign s30R=0
 ><#assign s31R=0
 ><#assign s32R=0
 ><#assign s33R=0
 ><#assign s34R=0
 
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
               ><#assign val=primerak.siglaInv
               ><#assign sigla=val
              ><#if sigla=""
                 ><#assign sigla=inventar
              ></#if   
              ><@odrediBrojSigli primerak/>
              <#-- pozivom ovog makroa azuriraju se globalne promenljve sa brojevima
              	   i formira se ispis sa zagradama	 -->
            </#if  
      ></#list     
 ></#if  
 
 >
 <#--<#if inv!=""
    ><#assign sigle=""
    ><#if s0!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"0-"+s0?string+"&nbsp;"+i0
    ></#if 
    ><#if s0R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"0-R"
    ></#if 
    ><#if s1!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"1-"+s1?string+"&nbsp;"+i1
    ></#if 
    ><#if s1R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"1-R"
    ></#if 
    ><#if s2!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"2-"+s2?string+"&nbsp;"+i2
    ></#if
    ><#if s2R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"2-R"
    ></#if  
    ><#if s3!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"3-"+s3?string+"&nbsp;"+i3
    ></#if
    ><#if s3R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"3-R"
    ></#if  
    ><#if s4!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"4-"+s4?string+"&nbsp;"+i4
    ></#if 
    ><#if s4R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"4-R"
    ></#if 
    ><#if s5!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"5-"+s5?string+"&nbsp;"+i5
    ></#if 
    ><#if s5R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"5-R"
    ></#if 
    ><#if s6!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"6-"+s6?string+"&nbsp;"+i6
    ></#if 
    ><#if s6R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"6-R"
    ></#if 
    ><#if s7!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"7-"+s7?string+"&nbsp;"+i7
    ></#if 
    ><#if s7R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"7-R"
    ></#if 
    ><#if s8!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"8-"+s8?string+"&nbsp;"+i8
    ></#if 
    ><#if s8R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"8-R"
    ></#if 
    ><#if s9!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"9-"+s9?string+"&nbsp;"+i9
    ></#if 
    ><#if s9R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"9-R"
    ></#if 
    ><#if s10!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"10-"+s10?string+"&nbsp;"+i10
    ></#if 
    ><#if s10R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"10-R"
    ></#if 
    ><#if s11!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"11-"+s11?string+"&nbsp;"+i11
    ></#if 
    ><#if s11R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"11-R"
    ></#if 
    ><#if s12!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"12-"+s12?string+"&nbsp;"+i12
    ></#if 
    ><#if s12R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"12-R"
    ></#if 
    ><#if s13!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"13-"+s13?string+"&nbsp;"+i13
    ></#if 
    ><#if s13R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"13-R"
    ></#if 
    ><#if s14!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"14-"+s14?string+"&nbsp;"+i14
    ></#if 
    ><#if s14R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"14-R"
    ></#if 
    ><#if s15!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"15-"+s15?string+"&nbsp;"+i15
    ></#if 
    ><#if s15R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"15-R"
    ></#if 
    ><#if s16!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"16-"+s16?string+"&nbsp;"+i16
    ></#if 
    ><#if s16R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"16-R"
    ></#if 
    ><#if s17!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"17-"+s17?string+"&nbsp;"+i17
    ></#if 
    ><#if s17R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"17-R"
    ></#if 
    ><#if s18!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"18-"+s18?string+"&nbsp;"+i18
    ></#if 
    ><#if s18R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"18-R"
    ></#if 
    ><#if s19!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"19-"+s19?string+"&nbsp;"+i19
    ></#if 
    ><#if s19R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"19-R"
    ></#if 
    ><#if s20!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"20-"+s20?string+"&nbsp;"+i20
    ></#if
    ><#if s20R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"20-R"
    ></#if 
    ><#if s21!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"21-"+s21?string+"&nbsp;"+i21
    ></#if 
    ><#if s21R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"21-R"
    ></#if 
    ><#if s22!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"22-"+s22?string+"&nbsp;"+i22
    ></#if 
    ><#if s22R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"22-R"
    ></#if 
    ><#if s23!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"23-"+s23?string+"&nbsp;"+i23
    ></#if 
    ><#if s23R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"23-R"
    ></#if 
    ><#if s24!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"24-"+s24?string+"&nbsp;"+i24
    ></#if 
    ><#if s24R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"24-R"
    ></#if 
    ><#if s25!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"25-"+s25?string+"&nbsp;"+i25
    ></#if
    ><#if s25R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"25-R"
    ></#if 
    ><#if s26!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"26-"+s26?string+"&nbsp;"+i26
    ></#if 
    ><#if s26R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"26-R"
    ></#if 
    ><#if s27!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"27-"+s27?string+"&nbsp;"+i27
    ></#if 
    ><#if s27R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"27-R"
    ></#if 
    ><#if s28!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"28-"+s28?string+"&nbsp;"+i28
    ></#if 
    ><#if s28R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"28-R"
    ></#if 
    ><#if s29!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"29-"+s29?string+"&nbsp;"+i29
    ></#if 
    ><#if s29R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"29-R"
    ></#if 
    ><#if s30!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"30-"+s30?string+"&nbsp;"+i30
    ></#if 
    ><#if s30R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"30-R"
    ></#if 
    ><#if s31!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"31-"+s31?string+"&nbsp;"+i31
    ></#if 
    ><#if s31R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"31-R"
    ></#if 
    ><#if s32!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"32-"+s32?string+"&nbsp;"+i32
    ></#if 
    ><#if s32R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"32-R"
    ></#if 
    ><#if s33!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"33-"+s33?string+"&nbsp;"+i33
    ></#if 
    ><#if s33R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"33-R"
    ></#if 
    ><#if s34!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"34-"+s34?string+"&nbsp;"+i34
    ></#if    
    ><#if s34R!=0
       ><#if sigle!=""
           ><#assign sigle=sigle+",&nbsp;"
       ></#if    
       ><#assign sigle=sigle+"34-R"
    ></#if    
 ></#if   
 
   >
   -->
   
   <#assign inv=inv+"<BR>"
   
></#macro

>