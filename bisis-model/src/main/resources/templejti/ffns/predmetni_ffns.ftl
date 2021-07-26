<#include "_base_ffns.ftl"
><#assign noTes=false
><#assign glavni=true
><#assign serijskaF=false
><#assign doktorska=false

><@getPredmListic/><#assign predmLis="<BISIS>"+predmLis+"</BISIS>">${predmLis}