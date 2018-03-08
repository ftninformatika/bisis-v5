package com.ftninformatika.bisis.circ.view;

import javax.swing.JPanel;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import java.awt.BorderLayout;

public class ReportResults extends JPanel {

  
  private JRViewer jr;
  
	public ReportResults() {
		super();
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(300, 200);
		this.add(getPanel(), java.awt.BorderLayout.CENTER);
	}
	
	private JRViewer getPanel(){
    if (jr == null){
      try{
        jr = new JRViewer(null);
      }catch (Exception e){
        e.printStackTrace();
      }
    }
    return jr;
  }
  
  private JPanel getPrint(){
		
	    try{
		    JasperPrint jp = JasperFillManager.fillReport(
		              User.class.getResource(
		                "/cirkulacija/jasper/empty.jasper").openStream(),
		                null, new JREmptyDataSource());
		    jr = new JRViewer(jp);
		    return jr;
	    }catch (Exception e){
	    	e.printStackTrace();
	    	return null;
	    }
	}
  
  public void setJasper(JasperPrint jp){
    try{
       this.remove(jr);
      jr = new JRViewer(jp);
      this.add(jr,java.awt.BorderLayout.CENTER);
    }catch (Exception e){
      e.printStackTrace();
    } 
  }

}
