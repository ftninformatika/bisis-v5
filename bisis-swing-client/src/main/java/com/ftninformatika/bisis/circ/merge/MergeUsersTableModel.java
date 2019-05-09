package com.ftninformatika.bisis.circ.merge;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.circ.view.RecordBean;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.Messages;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MergeUsersTableModel extends AbstractTableModel implements Serializable {

    protected List<MemberData> data;
    protected  List<String> columnIdentifiers;
    private Configuration cfg;
    private Template template;

    public MergeUsersTableModel() {
      columnIdentifiers=new ArrayList<String>();
      //columnIdentifiers.add(Messages.getString("circulation.select"));
      columnIdentifiers.add(Messages.getString("circulation.member"));
      data=new ArrayList<MemberData>();
    }
   
    public String getColumnName(int column) {
      Object id = null;
  		if (column < columnIdentifiers.size()) {  
  		    id = columnIdentifiers.get(column); 
  		}
      return (id == null) ? super.getColumnName(column) : id.toString();
    }

    public void setData(List<MemberData> data){
    	this.data = data;
    	fireTableDataChanged();
    }

    public List<MemberData> getData() {
        return this.data;
    }
    
    public int getRowCount() {
      return data.size();
    }

    public int getColumnCount() {
      return columnIdentifiers.size();
    }

    public Class getColumnClass(int col) {
       return String.class;
    }
    
    public boolean isCellEditable(int row, int column) {
      return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Member rowData = (Member) data.get(rowIndex).getMember();
        //Sortirati da prva clanarina bude najsvezija
        rowData.getSignings().stream().max(Comparator.comparing(Signing::getSignDate)).get().getSignDate();
        switch (columnIndex) {
//            case 0:
//                return rowData.getUserId();
            case 0:
                return getInfo(rowData);
            default:
                return null;
        }
    }
    
    public Member getUser(int rowIndex) {
      return data.get(rowIndex).getMember();
    }

    private String getInfo(Member member) {
        Writer out = new StringWriter();
        try {
            getTemplate().process(member, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    private Configuration getConfiguration() {
        if (cfg == null) {
            cfg = new Configuration();
            cfg.setClassForTemplateLoading(Cirkulacija.class, "/cirkulacija/docs/");
        }
        return cfg;
    }

    private Template getTemplate() {
        if (template == null) {
            try {
                template = getConfiguration().getTemplate("user.ftl");
            } catch (IOException e) {
            }
        }
        return template;
    }
  }
	 
