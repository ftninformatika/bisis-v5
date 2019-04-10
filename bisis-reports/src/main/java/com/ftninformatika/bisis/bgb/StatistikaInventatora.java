package com.ftninformatika.bisis.bgb;

import com.ftninformatika.bisis.records.*;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;

import java.text.SimpleDateFormat;
import java.util.*;


public class StatistikaInventatora extends Report {
    private Map<String, Map<String, Item>> itemMap = new HashMap<>();
    SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
//    private Pattern pattern;

    @Override
    public void init() {
        itemMap.clear();
//        pattern = Pattern.compile(getReportSettings().getInvnumpattern());
    }

  @Override
  public void finish() {
    for (String key : itemMap.keySet()) {
      Map<String, Item> map = itemMap.get(key);
      StringBuilder out = getWriter(key);
      for (Item i : map.values()){
        out.append(i.toString());
    }out.append("</report>");
      GeneratedReport gr=new GeneratedReport();
      if (key.indexOf("-") >= 0){
        gr.setReportName(key.substring(0,key.indexOf("-")));
        gr.setFullReportName(key);
        gr.setPeriod(key.substring(key.indexOf("-")+1));
      }
      else{
        gr.setReportName(key);
        gr.setFullReportName(key);
        gr.setPeriod(LatCyrUtils.toCyrillic("ceo fond"));

      }
      gr.setContent(out.toString());
      gr.setReportType(getType().name().toLowerCase());
      getReportRepository().save(gr);
  }
    itemMap.clear();
  }

  @Override
  public void handleRecord(Record rec) {

    if (rec == null || rec.getPrimerci().size() == 0 || rec.getGodine().size() == 0)
        return;

    String inventator = rec.getCreator().getUsername();
    Date recCrDate = rec.getCreationDate();

    if (recCrDate == null || inventator == null || inventator.equals(""))
        return;

    try {
        String key = settings.getReportName() + getFilenameSuffix(recCrDate);
        Item itemCr = getItem(key, inventator);
        itemCr.createdRecords++;
        for (Primerak p: rec.getPrimerci()) {
            String prInvetator = p.getInventator() == null ? "Nepoznat" : p.getInventator();
            Item itemPr = getItem(key, prInvetator);
            itemPr.createdInv++;
        }
        for (Godina g: rec.getGodine()) {
            String prInvetator = g.getInventator() == null ? "Nepoznat" : g.getInventator();
            Item itemPr = getItem(key, prInvetator);
            itemPr.createdInv++;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  public class Item implements Comparable<Item> {
    public String inventator;
    public int createdRecords;
    public int createdInv;

    public int compareTo(Item o) {
          if (o instanceof Item) {
            Item b = o;
            return inventator.compareTo(b.inventator);
          }
          return 0;
    }

    public Item(String obr) {
          createdRecords = 0;
          createdInv = 0;
          inventator = obr;
    }

    public String toString() {
        return "<item>" +
                "<inventator>"
                + inventator +
                "</inventator>" +
                "<createdRecords>"
                + createdRecords +
                "</createdRecords><createdInv>"
                + createdInv +
                "</createdInv></item>\n";
      }

    public int hashCode() {
      return inventator.hashCode();
    }

    public boolean equals(Object o) {
      Item i = (Item) o;
      return inventator.equals(i.inventator);
    }

    public void add(int createdRecords, int createdInv) {
      this.createdRecords += createdRecords;
      this.createdInv += createdInv;
    }

  }

  public Item getItem(String key, String obr) {
    Map<String, Item> map = getItemMap(key);
    Item i = getItem(map, obr);
    return i;
  }

  public Item getItem(Map<String, Item> map, String obr) {
    Item i = map.get(obr);
    if (i == null) {
      i = new Item(obr);
      map.put(obr, i);
    }
    return i;
  }

  public Map<String, Item> getItemMap(String key) {
    Map<String, Item> map = itemMap.get(key);
    if (map == null) {
      map = new HashMap<String, Item>();
      itemMap.put(key, map);
    }
    return map;
  }
}
