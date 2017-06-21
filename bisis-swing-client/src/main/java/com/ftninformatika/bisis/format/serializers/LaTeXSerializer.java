package com.ftninformatika.bisis.format.serializers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ftninformatika.bisis.format.*;
import com.gint.util.string.StringUtils;

/**
 * Class LaTeXSerializer comment.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class LaTeXSerializer implements FormatSerializer {

  public static final int INITIAL_BUFFER_SIZE = 204800;

  public String getType() {
    return TYPE_LATEX;
  }

  public String serializeFormat(UFormat fieldSet) {
    StringBuffer retVal = new StringBuffer(INITIAL_BUFFER_SIZE);
    lineSep = System.getProperty("line.separator");

    retVal.append("\\documentclass[dvipdfm,a4paper,10pt]{article}");
    retVal.append(lineSep);
    retVal.append("\\usepackage[cp1250]{inputenc}");
    retVal.append(lineSep);
    retVal.append("\\usepackage[T1]{fontenc}");
    retVal.append(lineSep);
    retVal.append("\\frenchspacing");
    retVal.append(lineSep);
    retVal.append("\\raggedbottom");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\hoffset}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\voffset}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\textwidth}{17cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\textheight}{25cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\oddsidemargin}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\evensidemargin}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\topmargin}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\headheight}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\headsep}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\begin{document}");
    retVal.append(lineSep);
    retVal.append("\\author{BISIS v3}");
    retVal.append(lineSep);
    retVal.append("\\title{UNIMARC format}");
    retVal.append(lineSep);
    retVal.append("\\date{"+new java.util.Date()+ "}");
    retVal.append(lineSep);
    retVal.append("\\maketitle");
    retVal.append(lineSep);
    retVal.append("\\tableofcontents");
    retVal.append(lineSep);
    
    for (int i = 0; i < fieldSet.getFields().size(); i++) {
      UField field = (UField)fieldSet.getFields().get(i);
      retVal.append(lineSep);
      serializeFormat(retVal, field);
    }

    retVal.append("\\end{document}");
    retVal.append(lineSep);
    return retVal.toString();
  }

  public String serializeCoders(UFormat fieldSet) {
    StringBuffer retVal = new StringBuffer(INITIAL_BUFFER_SIZE);
    lineSep = System.getProperty("line.separator");

    retVal.append("\\documentclass[dvipdfm,a4paper,10pt]{article}");
    retVal.append(lineSep);
    retVal.append("\\usepackage[cp1250]{inputenc}");
    retVal.append(lineSep);
    retVal.append("\\usepackage[T1]{fontenc}");
    retVal.append(lineSep);
    retVal.append("\\frenchspacing");
    retVal.append(lineSep);
    retVal.append("\\raggedbottom");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\hoffset}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\voffset}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\textwidth}{17cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\textheight}{25cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\oddsidemargin}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\evensidemargin}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\topmargin}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\headheight}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\setlength{\\headsep}{0cm}");
    retVal.append(lineSep);
    retVal.append("\\begin{document}");
    retVal.append(lineSep);
    retVal.append("\\author{BISIS v3}");
    retVal.append(lineSep);
    retVal.append("\\title{UNIMARC format -- \u0160ifarnici}");
    retVal.append(lineSep);
    retVal.append("\\date{"+new java.util.Date()+ "}");
    retVal.append(lineSep);
    retVal.append("\\maketitle");
    retVal.append(lineSep);
    retVal.append("\\tableofcontents");
    retVal.append(lineSep);
    
    List externalCoders = new ArrayList();
    for (int i = 0; i < fieldSet.getFields().size(); i++) {
      UField field = (UField)fieldSet.getFields().get(i);
      for (int j = 0; j < field.getSubfields().size(); j++) {
        USubfield sf = (USubfield)field.getSubfields().get(j);
        serializeCoders(retVal, sf, externalCoders);
      }
    }
    
    Iterator it = externalCoders.iterator();
    while (it.hasNext()) {
      UCoder coder = (UCoder)it.next();
      retVal.append(lineSep);
      String title = "Eksterni \u0161ifarnik: " + coder.getName();
      retVal.append("\\subsection*{");
      retVal.append(title);
      retVal.append("}");
      retVal.append(lineSep);
      retVal.append("\\addcontentsline{toc}{subsection}{");
      retVal.append(title);
      retVal.append("}");
      retVal.append(lineSep);
      retVal.append(lineSep);
      writeCoderItems(retVal, coder);
    }
    

    retVal.append("\\end{document}");
    retVal.append(lineSep);
    return retVal.toString();
  }
  
  private void serializeFormat(StringBuffer buff, UField field) {
    buff.append("\\section*{Polje ");
    buff.append(field.getName());
    buff.append(" -- ");
    buff.append(StringUtils.replace(field.getDescription(), "_", "\\_"));
    buff.append("}");
    buff.append(lineSep);
    buff.append("\\addcontentsline{toc}{section}{Polje ");
    buff.append(field.getName());
    buff.append(" -- ");
    buff.append(StringUtils.replace(field.getDescription(), "_", "\\_"));
    buff.append("}");
    buff.append(lineSep);
    buff.append(lineSep);
    buff.append("{\\bf Obavezno:} ");
    buff.append(dane(field.isMandatory()));
    buff.append(lineSep);
    buff.append(lineSep);
    buff.append("\\noindent {\\bf Ponovljivo:} ");
    buff.append(dane(field.isRepeatable()));
    buff.append(lineSep);
    buff.append(lineSep);
    buff.append("\\noindent {\\bf Sekundarno:} ");
    buff.append(dane(field.isSecondary()));
    buff.append(lineSep);
    buff.append(lineSep);
    buff.append("\\noindent {\\bf Prvi indikator:}  ");
    serializeFormat(buff, field.getInd1());
    buff.append("\\noindent {\\bf Drugi indikator:}  ");
    serializeFormat(buff, field.getInd2());
    for (int i = 0; i < field.getSubfields().size(); i++) {
      USubfield subfield = (USubfield)field.getSubfields().get(i);
      buff.append(lineSep);
      serializeFormat(buff, subfield);
    }
  }
  
  private void serializeFormat(StringBuffer buff, UIndicator ind) {
    if (ind == null) {
      buff.append("nema");
      buff.append(lineSep);
      buff.append(lineSep);
    } else {
      buff.append(lineSep);
      buff.append(lineSep);
      buff.append("\\begin{tabular}{c|l}");
      buff.append(lineSep);
      buff.append("{\\bf Vrednost} & {\\bf Opis} \\\\ \\hline");
      buff.append(lineSep);
      for (int i = 0; i < ind.getValues().size(); i++) {
        UItem item = (UItem)ind.getValues().get(i);
        buff.append(item.getCode());
        buff.append(" & ");
        buff.append(item.getValue());
        buff.append(" \\\\ \\hline");
        buff.append(lineSep);
      }
      buff.append("\\end{tabular}");
      buff.append(lineSep);
      buff.append(lineSep);
    }
  }

  private void serializeFormat(StringBuffer buff, USubfield sf) {
    String id = sf.getOwner().getName() + sf.getName();

    buff.append("\\subsection*{Potpolje ");
    buff.append(id);
    buff.append(" -- ");
    buff.append(StringUtils.replace(sf.getDescription(), "_", "\\_"));
    buff.append("}");
    buff.append(lineSep);
    buff.append("\\addcontentsline{toc}{subsection}{Potpolje ");
    buff.append(id);
    buff.append(" -- ");
    buff.append(StringUtils.replace(sf.getDescription(), "_", "\\_"));
    buff.append("}");
    buff.append(lineSep);
    buff.append(lineSep);
    buff.append("{\\bf Obavezno:} ");
    buff.append(dane(sf.isMandatory()));
    buff.append(lineSep);
    buff.append(lineSep);
    buff.append("\\noindent {\\bf Ponovljivo:} ");
    buff.append(dane(sf.isRepeatable()));
    buff.append(lineSep);
    buff.append(lineSep);
    if (sf.getCoder() != null) {
      buff.append("\\noindent {\\bf \u0160ifrirano}");
      buff.append(lineSep);
      buff.append(lineSep);
    }
    for (int i = 0; i < sf.getSubsubfields().size(); i++) {
      USubsubfield ssf = (USubsubfield)sf.getSubsubfields().get(i);
      buff.append(lineSep);
      serializeFormat(buff, ssf);
    }
  }

  private void serializeFormat(StringBuffer buff, USubsubfield ssf) {
    String id = ssf.getOwner().getOwner().getName() + 
      ssf.getOwner().getName() + ssf.getName();

    buff.append("\\subsubsection*{Potpotpolje ");
    buff.append(id);
    buff.append(" -- ");
    buff.append(ssf.getDescription());
    buff.append("}");
    buff.append(lineSep);
    buff.append("\\addcontentsline{toc}{subsubsection}{Potpotpolje ");
    buff.append(id);
    buff.append(" -- ");
    buff.append(ssf.getDescription());
    buff.append("}");
    buff.append(lineSep);
    buff.append(lineSep);
    buff.append("{\\bf Obavezno:} ");
    buff.append(dane(ssf.isMandatory()));
    buff.append(lineSep);
    buff.append(lineSep);
    buff.append("\\noindent {\\bf Ponovljivo:} ");
    buff.append(dane(ssf.isRepeatable()));
    buff.append(lineSep);
    buff.append(lineSep);
    if (ssf.getCoder() != null) {
      buff.append("\\noindent {\\bf \u0160ifrirano}");
      buff.append(lineSep);
      buff.append(lineSep);
    }
  }
  
  private void serializeCoders(StringBuffer buff, USubfield sf, List extCoders) {
    if (sf.getSubsubfields().size() > 0) {
      for (int i = 0; i < sf.getSubsubfields().size(); i++) {
        USubsubfield ssf = (USubsubfield)sf.getSubsubfields().get(i);
        serializeCoders(buff, ssf, extCoders);
      }
      return;
    }
    UCoder coder = sf.getCoder();
    if (coder == null)
      return;

    String id = sf.getOwner().getName() + sf.getName();
    buff.append("\\subsection*{Potpolje ");
    buff.append(id);
    buff.append(" -- ");
    buff.append(StringUtils.replace(sf.getDescription(), "_", "\\_"));
    buff.append("}");
    buff.append(lineSep);
    buff.append("\\addcontentsline{toc}{subsection}{Potpolje ");
    buff.append(id);
    buff.append(" -- ");
    buff.append(StringUtils.replace(sf.getDescription(), "_", "\\_"));
    buff.append("}");
    buff.append(lineSep);
    buff.append(lineSep);
    if (coder.isExternal()) {
      if (!extCoders.contains(coder))
        extCoders.add(coder);
      buff.append("\\noindent Koristi eksterni \u0161ifarnik: ");
      buff.append(coder.getName());
      buff.append(lineSep);
      buff.append(lineSep);
      return;
    }
    writeCoderItems(buff, coder);
    
  }
  
  private void serializeCoders(StringBuffer buff, USubsubfield ssf, List extCoders) {
    UCoder coder = ssf.getCoder();
    if (coder == null)
      return;

    String id = ssf.getOwner().getOwner().getName() + 
    		ssf.getOwner().getName() + ssf.getName();
	  buff.append("\\subsubsection*{Potpotpolje ");
	  buff.append(id);
	  buff.append(" -- ");
	  buff.append(ssf.getDescription());
	  buff.append("}");
	  buff.append(lineSep);
	  buff.append("\\addcontentsline{toc}{subsubsection}{Potpotpolje ");
	  buff.append(id);
	  buff.append(" -- ");
	  buff.append(ssf.getDescription());
	  buff.append("}");
	  buff.append(lineSep);
	  buff.append(lineSep);
	  writeCoderItems(buff, coder);
  }
  
  private void writeCoderItems(StringBuffer buff, UCoder coder) {
    buff.append("\\begin{tabular}{c|l}");
    buff.append(lineSep);
    Iterator it = coder.getItems().iterator();
    while (it.hasNext()) {
      UItem item = (UItem)it.next();
      buff.append(item.getCode());
      buff.append(" & ");
      buff.append(StringUtils.replace(item.getValue(), "%", "\\%"));
      buff.append(" \\\\ \\hline");
      buff.append(lineSep);
    }
    buff.append("\\end{tabular}");
    buff.append(lineSep);
    buff.append(lineSep);
  }

  private static String dane(boolean b) {
    return b ? "da" : "ne";
  }

  private String lineSep;
}
