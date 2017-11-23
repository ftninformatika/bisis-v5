package com.ftninformatika.bisis.circ.manager;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.common.SearchOperandModel;
import com.ftninformatika.bisis.circ.common.UsersPrefix;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.validator.Validator;
import com.ftninformatika.bisis.circ.view.SearchUsers;
import com.ftninformatika.bisis.circ.view.UsersPrefixModel;
import com.ftninformatika.bisis.search.SearchModelMember;

import java.util.ArrayList;
import java.util.Date;
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
		Object temp = null;
		UsersPrefix up;
		SearchModelMember searchModel = new SearchModelMember();
		
		//1. operand
		if (!comboBoxList.contains(search.getLPref1().getText())) {
			temp = (String) search.getTfPref1().getText();
		} else {
			temp = Utils.getCmbValue(search.getCmbPref1().getSelectedItem());
		}
		if (temp != null && !temp.equals("")) {
	      up= upref.getUsersPrefixByName(search.getLPref1().getText());
	      if (up.getDbname().equals("userId"))
	        temp = Validator.convertUserId2DB((String)temp);
	      if (up.getDbname().equals("ctlgNo"))
	        temp = Validator.convertCtlgNo2DB((String)temp);

	      	searchModel.setPref1(up.getDbname());
	      	searchModel.setText1((String)temp);
			
			if (!search.getTfPref2().equals("") || !search.getTfPref3().equals("")
					|| !search.getTfPref4().equals("") || !search.getTfPref5().equals("")) {
				searchModel.setOper1((String) search.getCmbOper1().getSelectedItem());
			}
		}
		//2. operand
		if (!comboBoxList.contains(search.getLPref2().getText())) {
			temp = (String) search.getTfPref2().getText();
		} else {
			temp = Utils.getCmbValue(search.getCmbPref2().getSelectedItem());
		}
		if (temp != null && !temp.equals("")) {
	      up= upref.getUsersPrefixByName(search.getLPref2().getText());
	      if (up.getDbname().equals("userId"))
	        temp = Validator.convertUserId2DB((String)temp);
		  if (up.getDbname().equals("ctlgNo"))
			temp = Validator.convertCtlgNo2DB((String)temp);

			searchModel.setPref2(up.getDbname());
			searchModel.setText2((String)temp);
			
			if (!search.getTfPref3().equals("") || !search.getTfPref4().equals("") || !search.getTfPref5().equals("")) {
				searchModel.setOper2((String) search.getCmbOper2().getSelectedItem());
			}
        }
		//3. operand
		if (!comboBoxList.contains(search.getLPref3().getText())) {
			temp = (String) search.getTfPref3().getText();
		} else {
			temp = Utils.getCmbValue(search.getCmbPref3().getSelectedItem());
		}
		if (temp != null && !temp.equals("")) {
	      up= upref.getUsersPrefixByName(search.getLPref3().getText());
	      if (up.getDbname().equals("userId"))
	        temp = Validator.convertUserId2DB((String)temp);
          if (up.getDbname().equals("ctlgNo"))
            temp = Validator.convertCtlgNo2DB((String)temp);

            searchModel.setPref3(up.getDbname());
            searchModel.setText3((String)temp);
			
			if (!search.getTfPref4().equals("") || !search.getTfPref5().equals("")) {
                searchModel.setOper3((String) search.getCmbOper3().getSelectedItem());
			}
		}
		//4. operand
		if (!comboBoxList.contains(search.getLPref4().getText())) {
			temp = (String) search.getTfPref4().getText();
		} else {
			temp = Utils.getCmbValue(search.getCmbPref4().getSelectedItem());
		}
		if (temp != null && !temp.equals("")) {
	      up= upref.getUsersPrefixByName(search.getLPref4().getText());
	      if (up.getDbname().equals("userId"))
	        temp = Validator.convertUserId2DB((String)temp);
	      if (up.getDbname().equals("ctlgNo"))
            temp = Validator.convertCtlgNo2DB((String)temp);

            searchModel.setPref4(up.getDbname());
            searchModel.setText4((String)temp);
			
			if (!search.getTfPref5().equals("")) {
                searchModel.setOper4((String) search.getCmbOper4().getSelectedItem());
			}
		}
		//5. operand
		if (!comboBoxList.contains(search.getLPref5().getText())) {
			temp = (String) search.getTfPref5().getText();
		} else {
			temp = Utils.getCmbValue(search.getCmbPref5().getSelectedItem());
		}
		if (temp != null && !temp.equals("")) {
	      up= upref.getUsersPrefixByName(search.getLPref5().getText());
	      if (up.getDbname().equals("userId"))
	        temp = Validator.convertUserId2DB((String)temp);
	      if (up.getDbname().equals("ctlgNo"))
	          temp = Validator.convertCtlgNo2DB((String)temp);

            searchModel.setPref5(up.getDbname());
            searchModel.setText5((String)temp);
		}
		
		// 1. period
		if (search.getStartDate1() != null || search.getEndDate1() != null) {
			up= upref.getUsersPrefixByName(search.getLDate1().getText());
			searchModel.setPrefDate1(up.getDbname());
			if (Utils.getCmbValue(search.getCmbLoc1().getSelectedItem()) != null) {
				searchModel.setLocation1(Utils.getCmbValue(search.getCmbLoc1().getSelectedItem()));
			}

			if (search.getStartDate1() != null && search.getEndDate1() != null) {
                searchModel.setStartDate1(Utils.setMinDate(search.getStartDate1()));
                searchModel.setEndDate1(Utils.setMaxDate(search.getEndDate1()));
			} else if (search.getStartDate1() == null && search.getEndDate1() != null) {
                searchModel.setStartDate1(Utils.setMinDate(search.getEndDate1()));
                searchModel.setEndDate1(Utils.setMaxDate(search.getEndDate1()));
			} else if (search.getStartDate1() != null && search.getEndDate1() == null) {
                searchModel.setStartDate1(Utils.setMinDate(search.getStartDate1()));
                searchModel.setEndDate1(Utils.setMaxDate(search.getStartDate1()));
			}
		}
		//2. period
		if (search.getStartDate2() != null || search.getEndDate2() != null) {
			up= upref.getUsersPrefixByName(search.getLDate2().getText());
			if (Utils.getCmbValue(search.getCmbLoc2().getSelectedItem()) != null) {
                searchModel.setLocation2(Utils.getCmbValue(search.getCmbLoc2().getSelectedItem()));
			}
			if (search.getStartDate2() != null && search.getEndDate2() != null) {
                searchModel.setStartDate2(Utils.setMinDate(search.getStartDate2()));
                searchModel.setEndDate2(Utils.setMaxDate(search.getEndDate2()));
			} else if (search.getStartDate2() == null && search.getEndDate2() != null) {
                searchModel.setStartDate2(Utils.setMinDate(search.getEndDate2()));
                searchModel.setEndDate2(Utils.setMaxDate(search.getEndDate2()));
			} else if (search.getStartDate2() != null && search.getEndDate2() == null) {
                searchModel.setStartDate2(Utils.setMinDate(search.getStartDate2()));
                searchModel.setEndDate2(Utils.setMaxDate(search.getStartDate2()));
			}
		}

		List<Member> l = null;
		if (!searchModel.isEmpty()) {
			try {
				l = BisisApp.bisisService.searchMembers(searchModel).execute().body();
			} catch (Exception e) {
				e.printStackTrace();
			}
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

	}

}
