package com.ftninformatika.bisis.report;


import java.awt.Component;
import java.util.List;
import com.ftninformatika.bisis.library_configuration.Report;
import com.ftninformatika.bisis.reports.GeneratedReport;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class ReportMenuBuilder {

  public static void addReport(JMenu root, Report report) {
	 if(report.getType().equals("online"))
		 return;
    String[] path = report.getMenuitem().split("\\|");
    if (path.length == 0)
      return;
    JMenu current = root;
    for (int i = 0; i < path.length - 1; i++) {
      current = makeMenu(current, path[i]);
    }
    JMenuItem menuItem = new JMenuItem(path[path.length - 1]);
    current.add(menuItem);
    final Report r = report;
    menuItem.addActionListener(ev -> {
        GeneratedReport currentReport = ReportUtils.loadReport(r);
        if ((currentReport != null))
           ReportUtils.showReport(currentReport, r);
        });
  }

  public static void addReports(JMenu root, List<Report> reports) {
    for (Report r : reports)
      addReport(root, r);
  }



  private static JMenu makeMenu(JMenu parent, String name) {
    int n = parent.getMenuComponentCount();
    JMenu target = null;
    for (int i = 0; i < n; i++) {
      Component c = parent.getMenuComponent(i);
      if (c instanceof JMenu) {
        JMenu m = (JMenu) c;
        if (m.getText().equals(name))
          return m;
      }
    }
    if (target == null) {
      target = new JMenu(name);
      parent.add(target);
    }
    return target;
  }
}
