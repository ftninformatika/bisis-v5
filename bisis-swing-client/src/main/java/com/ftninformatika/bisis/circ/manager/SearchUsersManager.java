package com.ftninformatika.bisis.circ.manager;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.SearchOperandModel;
import com.ftninformatika.bisis.circ.common.UsersPrefix;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.validator.Validator;
import com.ftninformatika.bisis.circ.view.SearchUsers;
import com.ftninformatika.bisis.circ.view.UsersPrefixModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;



public class SearchUsersManager {
	private List<String> comboBoxList;
	private UsersPrefixModel upref;
	boolean full = false;

	public SearchUsersManager() {
		initComboList();
		if (!full) {
			upref = new UsersPrefixModel();
			upref.initUsersPrefix();
			upref.initDatePrefix();
			full = true;
		}
	}
  
	public void executeSearch(SearchUsers search) {
		List<SearchOperandModel> operandList = new ArrayList<SearchOperandModel>();
		List<String> operatorList = new ArrayList<String>();
		Object temp = null;
		SearchOperandModel operand;
		UsersPrefix up;
		
		//1. operand
		if (!comboBoxList.contains(search.getLPref1().getText())) {
			temp = (String) search.getTfPref1().getText();
			temp = ((String)temp).replace('*','%');
		} else {
			temp = Utils.getCmbValue(search.getCmbPref1());
			if (temp == null) {
				if (!search.getCmbPref1().getSelectedItem().equals(" ")) { // combo Godina
					String str = (String) search.getCmbPref1().getSelectedItem();
					temp = Utils.getInteger(str);
				}
			}
		}
		if (temp != null && !temp.equals("")) {
	      up= upref.getUsersPrefixByName(search.getLPref1().getText());
	      if (up.getDbname().equals("userId"))
	        temp = Validator.convertUserId2DB((String)temp);
	      if (up.getDbname().equals("ctlgNo"))
	        temp = Validator.convertCtlgNo2DB((String)temp);
	      
			operand = new SearchOperandModel();
			operand.setLabel(up);
			operand.setValue(temp);
			operandList.add(operand);
			
			if (!search.getTfPref2().equals("") || !search.getTfPref3().equals("")
					|| !search.getTfPref4().equals("") || !search.getTfPref5().equals("")) {
				operatorList.add((String) search.getCmbOper1().getSelectedItem());
			}
		}
		//2. operand
		if (!comboBoxList.contains(search.getLPref2().getText())) {
			temp = (String) search.getTfPref2().getText();
			temp = ((String)temp).replace('*','%');
		} else {
			temp = Utils.getCmbValue(search.getCmbPref2());
			if (temp == null) {
				if (!search.getCmbPref2().getSelectedItem().equals(" ")) { // combo Godina
					String str = (String) search.getCmbPref2().getSelectedItem();
					temp = Utils.getInteger(str);
				}
			}
		}
		if (temp != null && !temp.equals("")) {
	      up= upref.getUsersPrefixByName(search.getLPref2().getText());
	      if (up.getDbname().equals("userId"))
	        temp = Validator.convertUserId2DB((String)temp);
	      
			operand = new SearchOperandModel();
			operand.setLabel(up);
			operand.setValue(temp);
			operandList.add(operand);
			
			if (!search.getTfPref3().equals("") || !search.getTfPref4().equals("") || !search.getTfPref5().equals("")) {
				operatorList.add((String) search.getCmbOper2().getSelectedItem());
			}
			}
		//3. operand
		if (!comboBoxList.contains(search.getLPref3().getText())) {
			temp = (String) search.getTfPref3().getText();
			temp = ((String)temp).replace('*','%');
		} else {
			temp = Utils.getCmbValue(search.getCmbPref3());
			if (temp == null) {
				if (!search.getCmbPref3().getSelectedItem().equals(" ")) { // combo Godina
					String str = (String) search.getCmbPref3().getSelectedItem();
					temp = Utils.getInteger(str);
				}
			}
		}
		if (temp != null && !temp.equals("")) {
	      up= upref.getUsersPrefixByName(search.getLPref3().getText());
	      if (up.getDbname().equals("userId"))
	        temp = Validator.convertUserId2DB((String)temp);
	      
			operand = new SearchOperandModel();
			operand.setLabel(up);
			operand.setValue(temp);
			operandList.add(operand);
			
			if (!search.getTfPref4().equals("") || !search.getTfPref5().equals("")) {
				operatorList.add((String) search.getCmbOper3().getSelectedItem());
			}
		}
		//4. operand
		if (!comboBoxList.contains(search.getLPref4().getText())) {
			temp = (String) search.getTfPref4().getText();
			temp = ((String)temp).replace('*','%');
		} else {
			temp = Utils.getCmbValue(search.getCmbPref4());
			if (temp == null) {
				if (!search.getCmbPref4().getSelectedItem().equals(" ")) { // combo Godina
					String str = (String) search.getCmbPref4().getSelectedItem();
					temp = Utils.getInteger(str);
				}
			}
		}
		if (temp != null && !temp.equals("")) {
	      up= upref.getUsersPrefixByName(search.getLPref4().getText());
	      if (up.getDbname().equals("userId"))
	        temp = Validator.convertUserId2DB((String)temp);
	      
			operand = new SearchOperandModel();
			operand.setLabel(up);
			operand.setValue(temp);
			operandList.add(operand);
			
			if (!search.getTfPref5().equals("")) {
				operatorList.add((String) search.getCmbOper4().getSelectedItem());
			}
		}
		//5. operand
		if (!comboBoxList.contains(search.getLPref5().getText())) {
			temp = (String) search.getTfPref5().getText();
			temp = ((String)temp).replace('*','%');
		} else {
			temp = Utils.getCmbValue(search.getCmbPref5());
			if (temp == null) {
				if (!search.getCmbPref5().getSelectedItem().equals(" ")) { // combo Godina
					String str = (String) search.getCmbPref5().getSelectedItem();
					temp = Utils.getInteger(str);
				}
			}
		}
		if (temp != null && !temp.equals("")) {
	      up= upref.getUsersPrefixByName(search.getLPref5().getText());
	      if (up.getDbname().equals("userId"))
	        temp = Validator.convertUserId2DB((String)temp);
	      
			operand = new SearchOperandModel();
			operand.setLabel(up);
			operand.setValue(temp);
			operandList.add(operand);
		}
		
		// 1. period
		if (search.getStartDate1() != null || search.getEndDate1() != null) {
			operatorList.add("and");
			up= upref.getUsersPrefixByName(search.getLDate1().getText());
			operand = new SearchOperandModel();
			operand.setLabel(up);
			if (Utils.getCmbValue(search.getCmbLoc1()) != null) {
				operand.setLocation(Utils.getCmbValue(search.getCmbLoc1()));
			}
			if (search.getStartDate1() != null && search.getEndDate1() != null) {
				operand.setStart(search.getStartDate1());
				operand.setEnd(search.getEndDate1());
			} else if (search.getStartDate1() == null && search.getEndDate1() != null) {
				operand.setStart(search.getEndDate1());
			} else if (search.getStartDate1() != null && search.getEndDate1() == null) {
				operand.setStart(search.getStartDate1());

			}
			operandList.add(operand);
		}
		//2. period
		if (search.getStartDate2() != null || search.getEndDate2() != null) {
			operatorList.add("and");
			up= upref.getUsersPrefixByName(search.getLDate2().getText());
			operand = new SearchOperandModel();
			operand.setLabel(up);
			if (Utils.getCmbValue(search.getCmbLoc2()) != null) {
				operand.setLocation(Utils.getCmbValue(search.getCmbLoc2()));
			}
			if (search.getStartDate2() != null && search.getEndDate2() != null) {
				operand.setStart(search.getStartDate2());
				operand.setEnd(search.getEndDate2());
			} else if (search.getStartDate2() == null && search.getEndDate2() != null) {
				operand.setStart(search.getEndDate2());
			} else if (search.getStartDate2() != null && search.getEndDate2() == null) {
				operand.setStart(search.getStartDate2());

			}
			operandList.add(operand);
		}

		List l = null;
		if (operandList.size() != 0) {
//			ExecuteSearchCommand searchcommand = new ExecuteSearchCommand(operandList,operatorList);
//			searchcommand = (ExecuteSearchCommand)Cirkulacija.getApp().getService().executeCommand(searchcommand);
//			if (searchcommand != null)
//				l = searchcommand.getList();
		}
		Cirkulacija.getApp().getMainFrame().getSearchUsersResults().setResult(l, search.getSearchQuery());
		Cirkulacija.getApp().getMainFrame().showPanel("searchUsersResultsPanel");
	}

	

	private void initComboList() {
		comboBoxList = new ArrayList<String>();
		comboBoxList.add("Nivo obrazovanja");
		comboBoxList.add("Vrsta u\u010dlanjenja");
		comboBoxList.add("Kategorija");
		comboBoxList.add("Organizacija");
		comboBoxList.add("Jezik");
		comboBoxList.add("Grupa");
		comboBoxList.add("Godina");

	}

}
