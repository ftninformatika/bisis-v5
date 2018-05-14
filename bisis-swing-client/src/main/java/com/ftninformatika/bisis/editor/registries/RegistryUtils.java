package com.ftninformatika.bisis.editor.registries;

import java.sql.Statement;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Comparator;


public class RegistryUtils {

  //TODO da cita iz konfiguracije
  public static boolean registriesAvailable() {
    return true;
//    if (available == -1)
//      return false;
//    if (available == 1)
//      return true;
//    try {
////      Statement stmt = BisisApp.getConnection().createStatement();
////      stmt.executeQuery("SELECT naziv FROM registar_zbirke");
////      stmt.close();
//
//      ExecuteQueryCommand command = new ExecuteQueryCommand();
//	  command.setSqlString("SELECT naziv FROM registar_zbirke");
//	  command = (ExecuteQueryCommand)BisisApp.getJdbcService().executeCommand(command);
//	  if (command != null){
//		  available = 1;
//	  }else {
//		  available = -1;
//	  }
//
//    } catch (Exception ex) {
//      ex.printStackTrace();
//      available = -1;
//    }
//    return available == 1;
  }
  
  public static Collator getCyrCollator() {
    return cyrCollator;
  }
  
  public static Collator getLatCollator() {
    return latCollator;
  }
  
  public static Comparator getCyrComparator() {
    return cyrComparator;
  }
  
  public static Comparator getLatComparator() {
    return latComparator;
  }
  
  private static String cyrRules =
     "='''='\"' " +    
     "< ' ' " +		
     "< a      =A      =\u0430 =\u0410 " + // a
     "< b      =B      =\u0431 =\u0411 " + // b
     "< v      =V      =\u0432 =\u0412 " + // v
     "< g      =G      =\u0433 =\u0413 " + // g
     "< d      =D      =\u0434 =\u0414 " + // d
     "< \u0111 =\u0110 =\u0452 =\u0402 " + // dj
     "< e      =E      =\u0435 =\u0415 " + // e
     "< \u017e =\u017d =\u0436 =\u0416 " + // zz
     "< z      =Z      =\u0437 =\u0417 " + // z
     "< i      =I      =\u0438 =\u0418 " + // i
     "< j      =J      =\u0458 =\u0408 " + // j
     "< k      =K      =\u043a =\u041a " + // k
     "< l      =L      =\u041b =\u041b " + // l
     "< lj =Lj = LJ    =\u0459 =\u0409 " + // lj
     "< m      =M      =\u043c =\u041c " + // m
     "< n      =N      =\u043d =\u041d " + // n
     "< nj =Nj = NJ    =\u045a =\u040a " + // nj
     "< o      =O      =\u043e =\u041e " + // o
     "< p      =P      =\u043f =\u041f " + // p
     "< r      =R      =\u0440 =\u0420 " + // r
     "< s      =S      =\u0441 =\u0421 " + // s
     "< t      =T      =\u0442 =\u0422 " + // t
     "< \u0107 =\u0106 =\u045b =\u040b " + // cc
     "< u      =U      =\u0443 =\u0423 " + // u
     "< f      =F      =\u0444 =\u0424 " + // f
     "< h      =H      =\u0445 =\u0425 " + // h
     "< c      =C      =\u0446 =\u0426 " + // c
     "< \u010d =\u010c =\u0447 =\u0427 " + // ch
     "< d\u017e=D\u017e=\u045f =\u040f " + // dz
     "< \u0161 =\u0160 =\u0448 =\u0428 "   // ss
     ;

  private static String latRules =
    "='''='\"' " +  
    "< ' ' " +   
    "< a      =A      =\u0430 =\u0410 " + // a
    "< b      =B      =\u0431 =\u0411 " + // b
    "< c      =C      =\u0446 =\u0426 " + // c
    "< \u010d =\u010c =\u0447 =\u0427 " + // ch
    "< \u0107 =\u0106 =\u045b =\u040b " + // cc
    "< d      =D      =\u0434 =\u0414 " + // d
    "< \u0111 =\u0110 =\u0452 =\u0402 " + // dj
    "< d\u017e=D\u017e=\u045f =\u040f " + // dz
    "< e      =E      =\u0435 =\u0415 " + // e
    "< f      =F      =\u0444 =\u0424 " + // f
    "< g      =G      =\u0433 =\u0413 " + // g
    "< h      =H      =\u0445 =\u0425 " + // h
    "< i      =I      =\u0438 =\u0418 " + // i
    "< j      =J      =\u0458 =\u0408 " + // j
    "< k      =K      =\u043a =\u041a " + // k
    "< l      =L      =\u041b =\u041b " + // l
    "< lj  =Lj = LJ   =\u0459 =\u0409 " + // lj
    "< m      =M      =\u043c =\u041c " + // m
    "< n      =N      =\u043d =\u041d " + // n
    "< nj  =Nj = NJ   =\u045a =\u040a " + // nj
    "< o      =O      =\u043e =\u041e " + // o
    "< p      =P      =\u043f =\u041f " + // p
    "< q      =Q " +
    "< r      =R      =\u0440 =\u0420 " + // r
    "< s      =S      =\u0441 =\u0421 " + // s
    "< \u0161 =\u0160 =\u0448 =\u0428 " + // ss
    "< t      =T      =\u0442 =\u0422 " + // t
    "< u      =U      =\u0443 =\u0423 " + // u
    "< v      =V      =\u0432 =\u0412 " + // v
    "< w      =W " +
    "< x      =X " +
    "< y      =Y " +
    "< z      =Z      =\u0437 =\u0417 " + // z
    "< \u017e =\u017d =\u0436 =\u0416 "   // zz
    ;
  
  private static Collator cyrCollator;
  private static Collator latCollator;
  private static ItemComparator cyrComparator;
  private static ItemComparator latComparator;
  private static int available = 0;
  
  static {
    try {
      cyrCollator = new RuleBasedCollator(cyrRules);  
      latCollator = new RuleBasedCollator(latRules);
      cyrComparator = new ItemComparator(cyrCollator);
      latComparator = new ItemComparator(latCollator);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
