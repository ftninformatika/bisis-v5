package com.ftninformatika.bisis;

import com.ftninformatika.bisis.actions.*;
import com.ftninformatika.bisis.admin.coders.CoderFrame;
import com.ftninformatika.bisis.admin.coders.TableCatalog;
import com.ftninformatika.bisis.circ.options.OptionsMainFrame;
import com.ftninformatika.bisis.circ.view.MmbrshipCoder;
import com.ftninformatika.bisis.circ.warnings.WarningsFrame;
import com.ftninformatika.bisis.libenv.LibEnvironment;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.library_configuration.Report;
import com.ftninformatika.bisis.report.ReportMenuBuilder;
import com.ftninformatika.bisis.search.SearchAdvancedFrame;
import com.ftninformatika.utils.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MenuBuilder extends JMenuBar {

  private JMenu mCirculation = null;
	private JMenu mNewUser = null;
	private JMenu mUsers = null;
	private JMenuItem mNewSingle = null;
	private JMenuItem mNewGroup = null;
	private JMenu mUser = null;
	private JMenuItem mData = null;
	private JMenuItem mLending = null;
	private JMenuItem mMembership = null;
	private JMenuItem mPicturebooks = null;
	private JMenuItem mSearchUser = null;
	private JMenu mSearch = null;
	private JMenuItem mSearchBooks = null;
	private JMenu mSistem = null;
    private JMenu mAdministration = null;
	private JMenu mUserRep = null;
	private JMenu mBooksRep = null;
	private JMenu mHistoryRep = null;
	private JMenu mMmbrBook = null;
	private JMenuItem mLibrarian = null;
	private JMenuItem mCategory = null;
	private JMenuItem mMmbrType = null;
	private JMenuItem mStructureNew = null;
	private JMenuItem mStructureVisitors = null;
	private JMenuItem mVisitors = null;
	private JMenuItem mSumary = null;
	private JMenuItem mHistoryUser = null;
	private JMenuItem mHistoryBook = null;
	private JMenuItem mMostRead = null;
	private JMenuItem mMostReadUDK = null;
	private JMenuItem mBookCard = null;
	private JMenuItem mCategUDK = null;
	private JMenuItem mLendingUDK = null;
  	private JMenuItem mCircReports = null;
	private JMenuItem mOptions = null;
  private JMenu mObrada = null;
  private SearchAction searchAction = null;
  private NewRecordAction newRecordAction = null;
  private GroupInventarAction groupInventarAction = null;
  private InvHolesAction invHolesAction = null;
  private MergeRecordsAction mergeRecAction = null;
  private JMenu mIzvestaji = null;
  private JMenu mAdminReport = null;
  private JMenu mReport = null;
  private JMenu mObradaReport = null;
  private JMenuItem mBackup = null;
  private JMenuItem searchAdvanced = null;
  private JMenuItem onlineReport = null;
  private JMenu mSifInv = null;
  private JMenu mSifCirc = null;
  private JMenuItem miOdeljenja = null;
  private JMenuItem miInvknj = null;
  private JMenuItem miFormati = null;
  private JMenuItem miStatus = null;
  private JMenuItem miPovez = null;
  private JMenuItem miPodlokacija = null;
  private JMenuItem miNacin = null;
  private JMenuItem miIntOzn = null;
  private JMenuItem miBrojaci = null;
  private JMenuItem miBibliotekari = null;
  private JMenuItem miTipoviObrade = null;
  private JMenuItem mIzlaz = null;
  private JMenuItem mLog = null;
  private JMenuItem mMonitor = null;
  private JMenuItem miUserCategs = null;
  private JMenuItem miMmbrTypes = null;
  private JMenuItem miEduLvl = null;
  private JMenuItem miLanguages = null;
  private JMenuItem miLocation = null;
  private JMenuItem miOrganization = null;
  private JMenuItem miPlaces = null;
  private JMenuItem miWarnCounters = null;
  private JMenuItem mi992b = null;
  private JMenuItem miMmbrship = null;
  private JMenuItem miWarnings = null;
  private JMenuItem miChoseTeme = null;
  private CoderFrame userCategsFrame = null;
  private CoderFrame mmbrTypesFrame = null;
  private CoderFrame eduLvlFrame = null;
  private CoderFrame languagesFrame = null;
  private CoderFrame locationFrame = null;
  private CoderFrame organizationFrame = null;
  private CoderFrame placesFrame = null;
  private CoderFrame warnCountersFrame = null;
  private MmbrshipCoder mmbrshipFrame = null;
  private WarningsFrame warningsFrame = null;
  private OptionsMainFrame optionsFrame = null;
  private SearchAdvancedFrame searchAdvancedFrame=null;
  /*private  OnlineReportFrame onlineReportFrame=null;*/
	public MenuBuilder(Librarian lib) {
		super();
    if (lib.isObrada()){
      this.add(getMObrada());
    } else {
      this.add(getMObradaDefault());
    }
    if (lib.isObrada() && lib.isCirkulacija()){
      this.add(getMCirculation());
      this.add(getMAllReport());
      
    } else if (lib.isObrada()){
    	this.add(getMAllObradaReport());
      
    } else if (lib.isCirkulacija()){
      this.add(getMUsers());
      this.add(getMSearch());
      this.add(getMCircReport());
    }
    if (lib.isAdministracija()){
      this.add(getMAdministration());
    }
    this.add(Box.createHorizontalGlue());
    this.add(getMSistem());
	}

  
  private JMenu getMObradaDefault(){
   if (mObrada == null){ 
    mObrada = new JMenu(Messages.getString("MENU_PROCESSING"));
    mObrada.setMnemonic(KeyEvent.VK_O);
    searchAction = new SearchAction();
    mObrada.add(new JMenuItem(searchAction));
   }
   return mObrada;
  }

  private JMenu getMObrada(){
    newRecordAction = new NewRecordAction();
    getMObradaDefault().add(new JMenuItem(newRecordAction));
    groupInventarAction = new GroupInventarAction();
    getMObradaDefault().add(getSearchAdvanced());
    getMObradaDefault().add(new JMenuItem(groupInventarAction));
    invHolesAction = new InvHolesAction();
    getMObradaDefault().add(new JMenuItem(invHolesAction));
    mergeRecAction = new MergeRecordsAction();
    getMObradaDefault().add(new JMenuItem(mergeRecAction));
    return getMObradaDefault();
  }

  private JMenu getMCirculation(){
    if (mCirculation == null) {
      mCirculation = new JMenu();
      mCirculation.setText(Messages.getString("MENU_CIRC"));
      mCirculation.setMnemonic(KeyEvent.VK_C);
      mCirculation.add(getMUsers());
      mCirculation.add(getMSearch());
    }
    return mCirculation;
  }
  
	private JMenu getMUsers() {
		if (mNewUser == null) {
			mUsers = new JMenu();
			mUsers.setText(Messages.getString("MENU_USERS"));
      		mUsers.setMnemonic(KeyEvent.VK_K);
			mUsers.add(getMNewUser());
			mUsers.add(getMUser());
		}
		return mUsers;
	}
	private JMenu getMNewUser() {
		if (mNewUser == null) {
			mNewUser = new JMenu();
			mNewUser.setText(Messages.getString("MENU_NEW_USER"));
			mNewUser.setIcon(new ImageIcon(getClass().getResource("/circ-images/add_user_bold16.png")));
			mNewUser.add(getMNewSingle());
			mNewUser.add(getMNewGroup());
		}
		return mNewUser;
	}

	private JMenuItem getMNewSingle() {
		if (mNewSingle == null) {
			mNewSingle = new JMenuItem(new CircNewUserAction());
		}
		return mNewSingle;
	}

	private JMenuItem getMNewGroup() {
		if (mNewGroup == null) {
			mNewGroup = new JMenuItem(new CircNewGroupAction());
		}
		return mNewGroup;
	}

	private JMenu getMUser() {
		if (mUser == null) {
			mUser = new JMenu();
			mUser.setText(Messages.getString("MENU_EXISTING_USER"));
			mUser.setIcon(new ImageIcon(getClass().getResource("/circ-images/user16.png")));
			mUser.add(getMData());
			mUser.add(getMMembership());
			mUser.add(getMLending());
			mUser.add(getMPicturebooks());
			
		}
		return mUser;
	}

	private JMenuItem getMData() {
		if (mData == null) {
			mData = new JMenuItem(new CircUserDataAction());
		}
		return mData;
	}

	private JMenuItem getMLending() {
		if (mLending == null) {
			mLending = new JMenuItem(new CircUserLendingAction());
		}
		return mLending;
	}

	private JMenuItem getMMembership() {
		if (mMembership == null) {
			mMembership = new JMenuItem(new CircUserMembershipAction());
		}
		return mMembership;
	}
	
	private JMenuItem getMPicturebooks() {
		if (mPicturebooks == null) {
			mPicturebooks = new JMenuItem(new CircPicturebooksAction());
		}
		return mPicturebooks;
	}

	private JMenuItem getMSearchUser() {
		if (mSearchUser == null) {
			mSearchUser = new JMenuItem(new CircSearchUsersAction());
		}
		return mSearchUser;
	}

	private JMenu getMSearch() {
		if (mSearch == null) {
			mSearch = new JMenu();
			mSearch.setText(Messages.getString("MENU_SEARCHING"));
			mSearch.setMnemonic(KeyEvent.VK_P);
			mSearch.add(getMSearchUser());
			mSearch.add(getMSearchBooks());
		}
		return mSearch;
	}

	private JMenuItem getMSearchBooks() {
		if (mSearchBooks == null) {
			mSearchBooks = new JMenuItem(new CircSearchBooksAction());
		}
		return mSearchBooks;
	}

	private JMenu getMCircReport() {
		if (mReport == null) {
			mReport = new JMenu();
			mReport.setText(Messages.getString("MENU_REPORTS"));
			mReport.setMnemonic(KeyEvent.VK_I);
			mReport.add(getMCircReportItem());
		}
		return mReport;
	}
  
  private JMenu getMObradaReport() {
   if (mIzvestaji == null) {
      mIzvestaji = new JMenu(Messages.getString("MENU_REPORTS"));
      mIzvestaji.setMnemonic(KeyEvent.VK_I);
	   List<Report> reports=BisisApp.appConfig.getClientConfig().getReports();
	   ReportMenuBuilder.addReports(mIzvestaji, reports);
    }
    return mIzvestaji;
  }
  
  private JMenu getMAllReport() {
    if (mAdminReport == null) {
      mAdminReport = new JMenu();
      mAdminReport.setText(Messages.getString("MENU_REPORTS"));
      mAdminReport.setMnemonic(KeyEvent.VK_I);
      mAdminReport.add(getMCircReportItem());
      getMObradaReport().setText(Messages.getString("MENU_PROCESSING"));
      mAdminReport.add(getMObradaReport());
      //mAdminReport.add(getOnlineReports());
    }
    return mAdminReport;
  }
  private JMenu getMAllObradaReport() {
	    if (mObradaReport == null) {
	    	mObradaReport = new JMenu();
	    	mObradaReport.setText(Messages.getString("MENU_REPORTS"));
	    	mObradaReport.setMnemonic(KeyEvent.VK_I);
		  getMObradaReport().setText(Messages.getString("MENU_PROCESSING"));
		  mObradaReport.add(getMObradaReport());
	      //mObradaReport.add(getOnlineReports());
	    }
	    return mObradaReport;
	  }

  private JMenu getMAdministration() {
    if (mAdministration == null) {
      mAdministration = new JMenu();
      mAdministration.setText(Messages.getString("MENU_ADMINISTRATION"));
      mAdministration.setMnemonic(KeyEvent.VK_A);
      //mAdministration.add(getMBackup());
      mAdministration.addSeparator();
      mAdministration.add(getMiBibliotekari());
      mAdministration.add(getMiTipoviObrade());
      mAdministration.add(getMSifInv());
      mAdministration.addSeparator();
      mAdministration.add(getMSifCirc());
      mAdministration.add(getMOptions());
      mAdministration.add(getMiWarnings());
      mAdministration.addSeparator();
      mAdministration.add(getMiBrojaci());
    }
    return mAdministration;
  }
  
  private JMenu getMSistem() {
		if (mSistem == null) {
			mSistem = new JMenu();
			mSistem.setText(Messages.getString("MENU_SYSTEM"));
			mSistem.add(getMLog());
    		mSistem.add(getMMonitor());
      		mSistem.add(getMIzlaz());
		}
		return mSistem;
	}


	private JMenu getMUserRep() {
		if (mUserRep == null) {
			mUserRep = new JMenu();
			mUserRep.setText(Messages.getString("MENU_USERS"));
			mUserRep.add(getMMmbrBook());
			mUserRep.add(getMStructureNew());
			mUserRep.add(getMStructureVisitors());
			mUserRep.add(getMVisitors());
			mUserRep.add(getMSumary());
		}
		return mUserRep;
	}

	private JMenu getMBooksRep() {
		if (mBooksRep == null) {
			mBooksRep = new JMenu();
			mBooksRep.setText(Messages.getString("MENU_PUBLICATIONS"));
			mBooksRep.add(getMMostRead());
			mBooksRep.add(getMMostReadUDK());
			mBooksRep.add(getMBookCard());
			mBooksRep.add(getMCategUDK());
			mBooksRep.add(getMLendingUDK());
		}
		return mBooksRep;
	}

	private JMenu getMHistoryRep() {
		if (mHistoryRep == null) {
			mHistoryRep = new JMenu();
			mHistoryRep.setText(Messages.getString("MENU_HISTORY"));
			mHistoryRep.add(getMHistoryUser());
			mHistoryRep.add(getMHistoryBook());
		}
		return mHistoryRep;
	}

	private JMenu getMMmbrBook() {
		if (mMmbrBook == null) {
			mMmbrBook = new JMenu();
			mMmbrBook.setText(Messages.getString("MENU_ENTRY_BOOK"));
			mMmbrBook.add(getMLibrarian());
			mMmbrBook.add(getMCategory());
			mMmbrBook.add(getMMmbrType());
		}
		return mMmbrBook;
	}

	private JMenuItem getMLibrarian() {
		if (mLibrarian == null) {
			mLibrarian = new JMenuItem();
			mLibrarian.setText(Messages.getString("MENU_BY_LIBRARIAN"));
		}
		return mLibrarian;
	}

	private JMenuItem getMCategory() {
		if (mCategory == null) {
			mCategory = new JMenuItem();
			mCategory.setText(Messages.getString("MENU_BY_CATEG"));
		}
		return mCategory;
	}

	private JMenuItem getMMmbrType() {
		if (mMmbrType == null) {
			mMmbrType = new JMenuItem();
			mMmbrType.setText(Messages.getString("MENU_BY_USER_CATEG"));
		}
		return mMmbrType;
	}

	private JMenuItem getMStructureNew() {
		if (mStructureNew == null) {
			mStructureNew = new JMenuItem();
			mStructureNew.setText(Messages.getString("MENU_STRUCT_MEMBERS"));
		}
		return mStructureNew;
	}

	private JMenuItem getMStructureVisitors() {
		if (mStructureVisitors == null) {
			mStructureVisitors = new JMenuItem();
			mStructureVisitors.setText(Messages.getString("MENU_STRUCT_VISITORS"));
		}
		return mStructureVisitors;
	}

	private JMenuItem getMVisitors() {
		if (mVisitors == null) {
			mVisitors = new JMenuItem();
			mVisitors.setText(Messages.getString("MENU_VISITORS"));
		}
		return mVisitors;
	}

	private JMenuItem getMSumary() {
		if (mSumary == null) {
			mSumary = new JMenuItem();
			mSumary.setText(Messages.getString("MENU_COLLECTIVE_REPORT"));
		}
		return mSumary;
	}

	private JMenuItem getMHistoryUser() {
		if (mHistoryUser == null) {
			mHistoryUser = new JMenuItem();
			mHistoryUser.setText(Messages.getString("MENU_FROM_USER"));
		}
		return mHistoryUser;
	}

	private JMenuItem getMHistoryBook() {
		if (mHistoryBook == null) {
			mHistoryBook = new JMenuItem();
			mHistoryBook.setText(Messages.getString("MENU_PUBLICATIONS"));
		}
		return mHistoryBook;
	}

	private JMenuItem getMMostRead() {
		if (mMostRead == null) {
			mMostRead = new JMenuItem();
			mMostRead.setText(Messages.getString("MENU_MOST_RED"));
		}
		return mMostRead;
	}

	private JMenuItem getMMostReadUDK() {
		if (mMostReadUDK == null) {
			mMostReadUDK = new JMenuItem();
			mMostReadUDK.setText(Messages.getString("MENU_MOST_READ_BY_UDK"));
		}
		return mMostReadUDK;
	}

	private JMenuItem getMBookCard() {
		if (mBookCard == null) {
			mBookCard = new JMenuItem();
			mBookCard.setText(Messages.getString("MENU_BOOK_CARD"));
		}
		return mBookCard;
	}

	private JMenuItem getMCategUDK() {
		if (mCategUDK == null) {
			mCategUDK = new JMenuItem();
			mCategUDK.setText(Messages.getString("MENU_BY_CATEG_USER_UDK"));
		}
		return mCategUDK;
	}

	private JMenuItem getMLendingUDK() {
		if (mLendingUDK == null) {
			mLendingUDK = new JMenuItem();
			mLendingUDK.setText(Messages.getString("MENU_TAKEN_RETURNED_BY_UDK"));
		}
		return mLendingUDK;
	}
  
  private JMenuItem getMCircReportItem() {
    if (mCircReports == null) {
      mCircReports = new JMenuItem(new CircReportAction());
      mCircReports.setText(Messages.getString("MENU_CIRC"));
    }
    return mCircReports;
  }
	
	private JMenuItem getMOptions() {
		if (mOptions == null) {
			mOptions = new JMenuItem();
			mOptions.setText(Messages.getString("MENU_OPTIONS"));
			mOptions.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				  getOptionsFrame().setVisible(true);
				}
			});
		}
		return mOptions;
	}
  /*
  private JMenuItem getMBackup() {
    if (mBackup == null) {
      mBackup = new JMenuItem("Bekap");
      mBackup.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          BisisApp.mf.getBackupDlg().setVisible(true);
        }
      });
    }
    return mBackup;
  }*/
  private JMenuItem getSearchAdvanced() {
	    if (searchAdvanced == null) {
	    	searchAdvanced = new JMenuItem(Messages.getString("SEARCH_ADVANCED_SEARCH"));
	    	searchAdvanced.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          getSearchAdvancedFrame().setVisible(true);
	        }
	      });
	    }
	    return searchAdvanced;
	  }
  /*
  private JMenuItem getOnlineReports() {
	    if (onlineReport == null) {
	    	onlineReport = new JMenuItem("Online");
	    	onlineReport.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          getOnlineReportFrame().setVisible(true);
	        }
	      });
	    }
	    return onlineReport;
	  }*/
  private JMenu getMSifInv(){
    if (mSifInv == null){
      mSifInv = new JMenu(Messages.getString("MENU_INV_CODERS"));
      mSifInv.add(getMiOdeljenja());
      mSifInv.add(getMiInvknj());
      mSifInv.add(getMiFormati());
      mSifInv.add(getMiStatus());
      mSifInv.add(getMiPovez());
      mSifInv.add(getMiPodlokacija());
      mSifInv.add(getMiNacin());
      mSifInv.add(getMiIntOzn());
      mSifInv.add(getMi992b());
    }
    return mSifInv;
  }

  private JMenuItem getMiOdeljenja() {
    if (miOdeljenja == null) {
      miOdeljenja = new JMenuItem(Messages.getString("MENU_LOCATIONS"));
      miOdeljenja.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
         BisisApp.getMainFrame().getOdeljenjeFrame().setVisible(true);
        }
      });
    }
    return miOdeljenja;
  }
  
  private JMenuItem getMiInvknj() {
    if (miInvknj == null) {
      miInvknj = new JMenuItem(Messages.getString("INV_BOOK"));
      miInvknj.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          BisisApp.getMainFrame().getInvknjFrame().setVisible(true);
        }
      });
    }
    return miInvknj;
  }
  
  private JMenuItem getMiFormati() {
    if (miFormati == null) {
      miFormati = new JMenuItem(Messages.getString("MENU_FORMATS"));
      miFormati.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          BisisApp.getMainFrame().getFormatFrame().setVisible(true);
        }
      });
    }
    return miFormati;
  }
  
  private JMenuItem getMiStatus() {
    if (miStatus == null) {
      miStatus = new JMenuItem(Messages.getString("MENU_ITEM_STATUS"));
      miStatus.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          BisisApp.getMainFrame().getStatusFrame().setVisible(true);
        }
      });
    }
    return miStatus;
  }
  
  private JMenuItem getMiPovez() {
    if (miPovez == null) {
      miPovez = new JMenuItem(Messages.getString("MENU_BINDING"));
      miPovez.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          BisisApp.getMainFrame().getPovezFrame().setVisible(true);
        }
      });
    }
    return miPovez;
  }
  
  private JMenuItem getMi992b() {
	    if (mi992b == null) {
	    	mi992b = new JMenuItem(Messages.getString("MENU_ACTIONS"));
	    	mi992b.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          BisisApp.getMainFrame().get992bFrame().setVisible(true);
	        }
	      });
	    }
	    return mi992b;
	  }
  
  private JMenuItem getMiPodlokacija() {
    if (miPodlokacija == null) {
      miPodlokacija = new JMenuItem(Messages.getString("MENU_SUBLOCATIONS"));
      miPodlokacija.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          BisisApp.getMainFrame().getPodlokacijaFrame().setVisible(true);
        }
      });
    }
    return miPodlokacija;
  }
  
  private JMenuItem getMiNacin() {
    if (miNacin == null) {
      miNacin = new JMenuItem(Messages.getString("MENU_ACQ_TYPE"));
      miNacin.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          BisisApp.getMainFrame().getNacinFrame().setVisible(true);
        }
      });
    }
    return miNacin;
  }
  
  private JMenuItem getMiIntOzn() {
    if (miIntOzn == null) {
      miIntOzn = new JMenuItem(Messages.getString("INTERNAL_MARK"));
      miIntOzn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          BisisApp.getMainFrame().getIntOznFrame().setVisible(true);
        }
      });
    }
    return miIntOzn;
  }
  
  private JMenuItem getMiBrojaci() {
    if (miBrojaci == null) {
      miBrojaci = new JMenuItem(Messages.getString("MENU_COUNTERS"));
      miBrojaci.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          BisisApp.getMainFrame().getCountersFrame().setVisible(true);
        }
      });
    }
    return miBrojaci;
  }
  
  private JMenuItem getMiBibliotekari() {
    if (miBibliotekari == null) {
      miBibliotekari = new JMenuItem(Messages.getString("MENU_LIBRARIANS"));
      miBibliotekari.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					LibEnvironment.showLibrariansFrame();
				}      	
      });     
    }
    return miBibliotekari;
  }
  
  private JMenuItem getMiTipoviObrade(){
  	if(miTipoviObrade == null) {
  		miTipoviObrade = new JMenuItem(Messages.getString("MENU_PROCESS_TYPES"));
  		miTipoviObrade.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					LibEnvironment.showProcessTypesFrame();
				}      	
      }); 
  		
  	}
  	return miTipoviObrade;
  }
  
  private JMenuItem getMIzlaz() {
    if (mIzlaz == null) {
      mIzlaz = new JMenuItem(Messages.getString("MENU_EXIT"));
      mIzlaz.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.exit(0);
        }
      });
    }
    return mIzlaz;
  }
  
  private JMenuItem getMLog() {
    if (mLog == null) {
      mLog = new JMenuItem(Messages.getString("MENU_LOG"));
      //TODO
    }
    return mLog;
  }

  private JMenuItem getMMonitor() {
    if (mMonitor == null) {
      mMonitor = new JMenuItem(new MonitorAction());
      mMonitor.setText(Messages.getString("MENU_MONITOR"));
    }
    return mMonitor;
  }

  private JMenu getMSifCirc(){
    if (mSifCirc == null){
      mSifCirc = new JMenu(Messages.getString("MENU_CIRC_CODERS"));
      mSifCirc.add(getMiUserCategs());
      mSifCirc.add(getMiMmbrTypes());
      mSifCirc.add(getMiMmbrship());
      mSifCirc.add(getMiEduLvl());
      mSifCirc.add(getMiLanguages());
      mSifCirc.add(getMiOrganization());
      mSifCirc.add(getMiLocation());
      mSifCirc.add(getMiPlaces());
      mSifCirc.add(getMiWarnCounters());
    }
    return mSifCirc;
  }

  private JMenuItem getMiUserCategs() {
    if (miUserCategs == null) {
      miUserCategs = new JMenuItem(Messages.getString("MENU_USER_CATEGS"));
      miUserCategs.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          getUserCategsFrame().setVisible(true);
        }
      });
    }
    return miUserCategs;
  }
  
  private JMenuItem getMiMmbrTypes() {
    if (miMmbrTypes == null) {
      miMmbrTypes = new JMenuItem(Messages.getString("MENU_MMBR_TYPES"));
      miMmbrTypes.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          getMmbrTypesFrame().setVisible(true);
        }
      });
    }
    return miMmbrTypes;
  }
  
  private JMenuItem getMiEduLvl() {
    if (miEduLvl == null) {
      miEduLvl = new JMenuItem(Messages.getString("MENU_EDU_LVL"));
      miEduLvl.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          getEduLvlFrame().setVisible(true);
        }
      });
    }
    return miEduLvl;
  }
  
  private JMenuItem getMiLanguages() {
    if (miLanguages == null) {
      miLanguages = new JMenuItem(Messages.getString("MENU_LANG"));
      miLanguages.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          getLanguagesFrame().setVisible(true);
        }
      });
    }
    return miLanguages;
  }
  
  private JMenuItem getMiOrganization() {
    if (miOrganization == null) {
      miOrganization = new JMenuItem(Messages.getString("MENU_ORGANIZATION"));
      miOrganization.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          getOrganizationFrame().setVisible(true);
        }
      });
    }
    return miOrganization;
  }
  
  private JMenuItem getMiLocation() {
    if (miLocation == null) {
      miLocation = new JMenuItem(Messages.getString("MENU_LOCATIONS"));
      miLocation.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          getLocationFrame().setVisible(true);
        }
      });
    }
    return miLocation;
  }
  
  private JMenuItem getMiPlaces() {
    if (miPlaces == null) {
      miPlaces = new JMenuItem(Messages.getString("MENU_PLACES"));
      miPlaces.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          getPlacesFrame().setVisible(true);
        }
      });
    }
    return miPlaces;
  }
  
  private JMenuItem getMiWarnCounters() {
    if (miWarnCounters == null) {
    	miWarnCounters = new JMenuItem(Messages.getString("MENU_WARN_COUNTERS"));
    	miWarnCounters.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          getWarnCountersFrame().setVisible(true);
        }
      });
    }
    return miWarnCounters;
  }
  
  private JMenuItem getMiMmbrship() {
    if (miMmbrship == null) {
      miMmbrship = new JMenuItem(Messages.getString("MENU_FEE"));
      miMmbrship.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          getMmbrshipFrame().setVisible(true);
        }
      });
    }
    return miMmbrship;
  }
  
  private JMenuItem getMiWarnings() {
    if (miWarnings == null) {
      miWarnings = new JMenuItem(Messages.getString("MENU_NOTES"));
      miWarnings.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          getWarningsFrame().setVisible(true);
        }
      });
    }
    return miWarnings;
  }

  public CoderFrame getUserCategsFrame(){
    if (userCategsFrame == null){
      userCategsFrame = new CoderFrame(TableCatalog.getTable("user_categs"));
      BisisApp.getMainFrame().insertFrame(userCategsFrame);
    }
    return userCategsFrame;
  }
  
  public CoderFrame getMmbrTypesFrame(){
    if (mmbrTypesFrame == null){
      mmbrTypesFrame = new CoderFrame(TableCatalog.getTable("mmbr_types"));
      BisisApp.getMainFrame().insertFrame(mmbrTypesFrame);
    }
    return mmbrTypesFrame;
  }
  
  public CoderFrame getEduLvlFrame(){
    if (eduLvlFrame == null){
      eduLvlFrame = new CoderFrame(TableCatalog.getTable("edu_lvl"));
      BisisApp.getMainFrame().insertFrame(eduLvlFrame);
    }
    return eduLvlFrame;
  }
  
  public CoderFrame getLanguagesFrame(){
    if (languagesFrame == null){
      languagesFrame = new CoderFrame(TableCatalog.getTable("languages"));
      BisisApp.getMainFrame().insertFrame(languagesFrame);
    }
    return languagesFrame;
  }
  
  public CoderFrame getOrganizationFrame(){
    if (organizationFrame == null){
      organizationFrame = new CoderFrame(TableCatalog.getTable("organization"));
      BisisApp.getMainFrame().insertFrame(organizationFrame);
    }
    return organizationFrame;
  }
  
  public CoderFrame getLocationFrame(){
    if (locationFrame == null){
      locationFrame = new CoderFrame(TableCatalog.getTable("location"));
      BisisApp.getMainFrame().insertFrame(locationFrame);
    }
    return locationFrame;
  }
  
  public CoderFrame getPlacesFrame(){
    if (placesFrame == null){
      placesFrame = new CoderFrame(TableCatalog.getTable("places"));
      BisisApp.getMainFrame().insertFrame(placesFrame);
    }
    return placesFrame;
  }
  
  public CoderFrame getWarnCountersFrame(){
    if (warnCountersFrame == null){
    	warnCountersFrame = new CoderFrame(TableCatalog.getTable("warn_counters"));
      BisisApp.getMainFrame().insertFrame(warnCountersFrame);
    }
    return warnCountersFrame;
  }

  public MmbrshipCoder getMmbrshipFrame(){
    if (mmbrshipFrame == null){
      mmbrshipFrame = new MmbrshipCoder();
      BisisApp.getMainFrame().insertFrame(mmbrshipFrame);
    }
    return mmbrshipFrame;
  }

  public WarningsFrame getWarningsFrame(){
    if (warningsFrame == null){
      warningsFrame = new WarningsFrame();
      BisisApp.getMainFrame().insertFrame(warningsFrame);
    }
    return warningsFrame;
  }

  public OptionsMainFrame getOptionsFrame(){
    if (optionsFrame == null){
      optionsFrame = new OptionsMainFrame();
      BisisApp.getMainFrame().insertFrame(optionsFrame);
    }
    return optionsFrame;
  }

  public SearchAdvancedFrame getSearchAdvancedFrame(){
	    if (searchAdvancedFrame == null){
	    	searchAdvancedFrame = new SearchAdvancedFrame();
	      BisisApp.getMainFrame().insertFrame(searchAdvancedFrame);
	    }
	    return searchAdvancedFrame;
	  }

	  /*
  public OnlineReportFrame getOnlineReportFrame(){
	    if (onlineReportFrame == null){
	    	onlineReportFrame = new OnlineReportFrame();
	      BisisApp.getMainFrame().insertFrame(onlineReportFrame);
	    }
	    return onlineReportFrame;
	  }*/
}