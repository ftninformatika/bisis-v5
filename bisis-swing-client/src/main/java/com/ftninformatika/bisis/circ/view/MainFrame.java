package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.ecard.ElCardInfo;
import com.ftninformatika.bisis.ecard.ElCardReader;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.actions.*;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.validator.Validator;
import org.apache.log4j.Logger;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.Box;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class MainFrame extends JInternalFrame {

    private JPanel jContentPane = null;
    private JToolBar toolBar = null;
    private JPanel mPanel = null;
    private CardLayout mCardLayout = null;
    private JPanel blankPanel = null;
    private JButton btnNew = null;
    private JButton btnUser = null;
    private JButton btnSearchUser = null;
    private JButton btnSearchBook = null;
    private JButton btnReports = null;
    private JButton btnBack = null;
    private User userPanel = null;
    private Group groupPanel = null;
    private JPanel searchBooksPanel = null;
    private SearchBooks searchBooks = null;
    private JPanel searchBooksResultsPanel = null;
    private SearchBooksResults searchBooksResults = null;
    private SearchUsers searchUsers = null;
    private JPanel searchUsersPanel = null;
    private JPanel searchUsersResultsPanel = null;
    private SearchUsersResults searchUsersResults = null;
    private JPanel reportPanel = null;
    private Report report = null;
    private JPanel reportResultsPanel = null;
    private ReportResults reportResults = null;
    private Stack<String> panelStack = null;
    private UserID userIDPanel = null;
    private ActionListener userIDOK = null;
    private ActionListener userIDCancel = null;
    private ActionListener userIDSearch = null;
    private ActionListener userECARD = null;
    private int requestedPanel;
    private boolean blank = true;
    private HashMap<String, JPanel> panels = null;
    private static Logger log = Logger.getLogger(MainFrame.class);

    public MainFrame() {
        super(Messages.getString("circulation.circulation"), true, true, true, true); //$NON-NLS-1$
        initialize();
    }

    private void initialize() {
        this.setDefaultWindowSize();
        this.setName("mframe"); //$NON-NLS-1$
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                handleClose();
            }
        });
        this.setContentPane(getJContentPane());
        Dimension screen = getToolkit().getScreenSize();
        this.setLocation((screen.width - getSize().width) / 2,
                (screen.height - getSize().height) / 2);
        this.pack();
        panelStack = new Stack<String>();
        showPanel("blankPanel"); //$NON-NLS-1$
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getToolBar(), BorderLayout.NORTH);
            jContentPane.add(getMPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    public UserID getUserIDPanel() {
        if (userIDPanel == null) {
            userIDPanel = new UserID(BisisApp.getMainFrame());
            userIDPanel.setLocationRelativeTo(this);
            userIDPanel.addOKListener(getUserIDOK());
            userIDPanel.addCancelListener(getUserIDCancel());
            userIDPanel.addSearchListener(getUserIDSearch());
            userIDPanel.addEsearchListener(getSearchByEcard());
        }
        return userIDPanel;
    }

    public void setLendingsTabWindowSize() {
        this.setSize(new Dimension(1000, 615));
        this.setPreferredSize(new Dimension(1000, 615));
    }

    public void setDefaultWindowSize() {
        this.setSize(new Dimension(1000, 600));
        this.setPreferredSize(new Dimension(1000, 600));
    }

    private ActionListener getUserIDOK() {
        if (userIDOK == null) {
            userIDOK = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String userid = Validator.convertUserId2DB(getUserIDPanel().getValue());
                    log.info("Pretraga korisnika: " + userid);
                    if (!userid.equals("")) { //$NON-NLS-1$
                        int found = Cirkulacija.getApp().getUserManager().getUser(getUserPanel(), getGroupPanel(), userid);
                        if (found == 1) {
                            log.info("Pronadjen individualni korisnik: " + userid);
                            getUserIDPanel().clear();
                            getUserIDPanel().setVisible(false);
                            switch (requestedPanel) {
                                case 1:
                                    getUserPanel().showData();
                                    break;
                                case 2:
                                    getUserPanel().showMmbrship();
                                    break;
                                case 3:
                                    getUserPanel().showLending();
                                    break;
                                case 4:
                                    getUserPanel().showPicturebooks();
                                    break;
                                case 5: {
                                    // todo: prikaz samo za bgb biblioteku
                                    if (BisisApp.appConfig.getClientConfig().getLibraryName().equals("bgb")) {
                                        getUserPanel().showReservations();
                                    }
                                    break;
                                }
                            }
                            showPanel("userPanel"); //$NON-NLS-1$
                        } else if (found == 2) {
                            getUserIDPanel().clear();
                            getUserIDPanel().setVisible(false);
                            showPanel("groupPanel"); //$NON-NLS-1$
                        } else if (found == 3) {
                            JOptionPane.showMessageDialog(getUserIDPanel(), Messages.getString("circulation.userinuse"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                    new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                        } else {
                            JOptionPane.showMessageDialog(getUserIDPanel(), Messages.getString("circulation.userdontexists"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                    new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                        }
                    } else {
                        JOptionPane.showMessageDialog(getUserIDPanel(), Messages.getString("circulation.usernumberisnotvalid"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                    }
                }
            };
        }
        return userIDOK;
    }

    private ActionListener getSearchByEcard() {
        if (userECARD == null) {
            userECARD = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ElCardReader reader = ElCardReader.getInstance();
                    boolean circLocaleLatn = BisisApp.appConfig.getClientConfig().getCircLocaleLatn() == null ?
                            true : BisisApp.appConfig.getClientConfig().getCircLocaleLatn();
                    ElCardInfo elCardInfo = reader.getInfo(circLocaleLatn);
                    if (!elCardInfo.isSuccess()) {
                        JOptionPane.showMessageDialog(null, Messages.getString(elCardInfo.getMessageKey()), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE,
                                new ImageIcon(getClass().getResource("/circ-images/x32.png")));
                        return;
                    }
                    elCardInfo.set();
                    int found = Cirkulacija.getApp().getUserManager().getUserByECard(getUserPanel(), getGroupPanel(), elCardInfo);
                    if (found == 1) {
                        getUserIDPanel().clear();
                        getUserIDPanel().setVisible(false);
                        switch (requestedPanel) {
                            case 1:
                                getUserPanel().showData();
                                break;
                            case 2:
                                getUserPanel().showMmbrship();
                                break;
                            case 3:
                                getUserPanel().showLending();
                                break;
                            case 4:
                                getUserPanel().showPicturebooks();
                                break;
                            case 5:
                                getUserPanel().showReservations();
                                break;
                        }
                        showPanel("userPanel");
                    } else if (found == 2) {
                        getUserIDPanel().clear();
                        getUserIDPanel().setVisible(false);
                        showPanel("groupPanel");
                    } else if (found == 3) {
                        JOptionPane.showMessageDialog(getUserIDPanel(), Messages.getString("circulation.userinuse"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                new ImageIcon(getClass().getResource("/circ-images/x32.png")));
                    } else {
                        JOptionPane.showMessageDialog(getUserIDPanel(), Messages.getString("circulation.cantbefounde"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                new ImageIcon(getClass().getResource("/circ-images/x32.png")));
                    }
                }
            };
        }
        return userECARD;
    }

    private ActionListener getUserIDCancel() {
        if (userIDCancel == null) {
            userIDCancel = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getUserIDPanel().clear();
                    getUserIDPanel().setVisible(false);
//                    boolean released = Cirkulacija.getApp().getUserManager().releaseUser();
//                    if (released) {
//                        log.info("Otkljucan korisnik: " + getUserIDPanel().getValue());
//                        getUserIDPanel().clear();
//                        getUserIDPanel().setVisible(false);
//                    } else {
//                        log.info("Otkljucavanje nije uspelo: " + getUserIDPanel().getValue());
//                        JOptionPane.showMessageDialog(getUserIDPanel(), Messages.getString("circulation.releaseerror"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
//                                new ImageIcon(getClass().getResource("/circ-images/x32.png")));
//                    }

                }
            };
        }
        return userIDCancel;
    }

    public void setRequestedPanel(int panel) {
        requestedPanel = panel;
    }

    public boolean getBlank() {
        return blank;
    }

    public int getRequestedPanel() {
        return requestedPanel;
    }

    private ActionListener getUserIDSearch() {
        if (userIDSearch == null) {
            userIDSearch = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showPanel("searchUsersPanel"); //$NON-NLS-1$
                    getUserIDPanel().clear();
                    getUserIDPanel().setVisible(false);
                }
            };
        }
        return userIDSearch;
    }

    private JToolBar getToolBar() {
        if (toolBar == null) {
            toolBar = new JToolBar();
            //toolBar.addSeparator(new Dimension(20, 0));
            toolBar.add(getBtnNew());
            toolBar.add(getBtnUser());
            toolBar.add(getBtnSearchUser());
            toolBar.add(getBtnSearchBook());
            toolBar.add(getBtnReports());
            toolBar.add(Box.createGlue());
            toolBar.add(getBtnBack());
            toolBar.setFloatable(false);
            getBtnBack().setVisible(false);
        }
        return toolBar;
    }

    private JPanel getMPanel() {
        if (mPanel == null) {
            mPanel = new JPanel();
            mPanel.setPreferredSize(null);
            mCardLayout = new CardLayout();
            mPanel.setLayout(mCardLayout);
            mPanel.add(getBlankPanel(), getBlankPanel().getName());
            mPanel.add(getUserPanel(), getUserPanel().getName());
            mPanel.add(getSearchBooksPanel(), getSearchBooksPanel().getName());
            mPanel.add(getSearchBooksResultsPanel(), getSearchBooksResultsPanel().getName());
            mPanel.add(getSearchUsersPanel(), getSearchUsersPanel().getName());
            mPanel.add(getSearchUsersResultsPanel(), getSearchUsersResultsPanel().getName());
            mPanel.add(getReportPanel(), getReportPanel().getName());
            mPanel.add(getReportResultsPanel(), getReportResultsPanel().getName());
            mPanel.add(getGroupPanel(), getGroupPanel().getName());
            initHash();
        }
        return mPanel;
    }

    private void initHash() {
        panels = new HashMap<String, JPanel>();
        panels.put(getBlankPanel().getName(), getBlankPanel());
        panels.put(getUserPanel().getName(), getUserPanel());
        panels.put(getSearchBooksPanel().getName(), getSearchBooksPanel());
        panels.put(getSearchBooksResultsPanel().getName(), getSearchBooksResultsPanel());
        panels.put(getSearchUsersPanel().getName(), getSearchUsersPanel());
        panels.put(getSearchUsersResultsPanel().getName(), getSearchUsersResultsPanel());
        panels.put(getReportPanel().getName(), getReportPanel());
        panels.put(getReportResultsPanel().getName(), getReportResultsPanel());
        panels.put(getGroupPanel().getName(), getGroupPanel());
    }

    private JPanel getBlankPanel() {
        if (blankPanel == null) {
            blankPanel = new JPanel();
            blankPanel.setName("blankPanel"); //$NON-NLS-1$
        }
        return blankPanel;
    }

    private JButton getBtnNew() {
        if (btnNew == null) {
            btnNew = new JButton(new CircNewUserAction());
            btnNew.setFocusable(false);
            btnNew.setPreferredSize(new Dimension(30, 30));
            btnNew.setText(""); //$NON-NLS-1$
        }
        return btnNew;
    }

    private JButton getBtnUser() {
        if (btnUser == null) {
            btnUser = new JButton(new CircUserDataAction());
            btnUser.setFocusable(false);
            btnUser.setPreferredSize(new Dimension(30, 30));
            btnUser.setText(""); //$NON-NLS-1$
        }
        return btnUser;
    }

    public Group getGroupPanel() {
        if (groupPanel == null) {
            groupPanel = new Group();
            groupPanel.setName("groupPanel"); //$NON-NLS-1$
        }
        return groupPanel;
    }

    public User getUserPanel() {
        if (userPanel == null) {
            userPanel = new User();
            userPanel.setName("userPanel"); //$NON-NLS-1$
        }
        return userPanel;
    }

    public SearchBooks getSearchBooks() {
        if (searchBooks == null) {
            searchBooks = new SearchBooks();
        }
        return searchBooks;
    }

    public JPanel getSearchBooksPanel() {
        if (searchBooksPanel == null) {
            searchBooksPanel = getSearchBooks().getPanel();
            searchBooksPanel.setName("searchBooksPanel"); //$NON-NLS-1$
        }
        return searchBooksPanel;
    }

    public SearchBooksResults getSearchBooksResults() {
        if (searchBooksResults == null) {
            searchBooksResults = new SearchBooksResults();
        }
        return searchBooksResults;
    }

    private JPanel getSearchBooksResultsPanel() {
        if (searchBooksResultsPanel == null) {
            searchBooksResultsPanel = getSearchBooksResults();
            searchBooksResultsPanel.setName("searchBooksResultsPanel"); //$NON-NLS-1$
            searchBooksResultsPanel.setPreferredSize(new Dimension(676, 356));
        }
        return searchBooksResultsPanel;
    }

    private JButton getBtnSearchBook() {
        if (btnSearchBook == null) {
            btnSearchBook = new JButton(new CircSearchBooksAction());
            btnSearchBook.setFocusable(false);
            btnSearchBook.setPreferredSize(new Dimension(30, 30));
            btnSearchBook.setText(""); //$NON-NLS-1$
        }
        return btnSearchBook;
    }

    public SearchUsers getSearchUsers() {
        if (searchUsers == null) {
            searchUsers = new SearchUsers();
        }
        return searchUsers;
    }

    public JPanel getSearchUsersPanel() {
        if (searchUsersPanel == null) {
            searchUsersPanel = getSearchUsers().getPanel();
            searchUsersPanel.setName("searchUsersPanel"); //$NON-NLS-1$
        }
        return searchUsersPanel;
    }

    public SearchUsersResults getSearchUsersResults() {
        if (searchUsersResults == null) {
            searchUsersResults = new SearchUsersResults();
        }
        return searchUsersResults;
    }

    private JPanel getSearchUsersResultsPanel() {
        if (searchUsersResultsPanel == null) {
            searchUsersResultsPanel = getSearchUsersResults().getPanel();
            searchUsersResultsPanel.setName("searchUsersResultsPanel"); //$NON-NLS-1$
        }
        return searchUsersResultsPanel;
    }

    private JButton getBtnSearchUser() {
        if (btnSearchUser == null) {
            btnSearchUser = new JButton(new CircSearchUsersAction());
            btnSearchUser.setFocusable(false);
            btnSearchUser.setPreferredSize(new Dimension(30, 30));
            btnSearchUser.setText(""); //$NON-NLS-1$
        }
        return btnSearchUser;
    }

    public Report getReport() {
        if (report == null) {
            report = new Report();
        }
        return report;
    }

    private JPanel getReportPanel() {
        if (reportPanel == null) {
            reportPanel = getReport().getPanel();
            reportPanel.setName("reportPanel"); //$NON-NLS-1$
        }
        return reportPanel;
    }

    public ReportResults getReportResults() {
        if (reportResults == null) {
            reportResults = new ReportResults();
        }
        return reportResults;
    }

    private JPanel getReportResultsPanel() {
        if (reportResultsPanel == null) {
            reportResultsPanel = getReportResults();
            reportResultsPanel.setName("reportResultsPanel"); //$NON-NLS-1$
        }
        return reportResultsPanel;
    }

    private JButton getBtnReports() {
        if (btnReports == null) {
            btnReports = new JButton(new CircReportAction());
            btnReports.setFocusable(false);
            btnReports.setPreferredSize(new Dimension(30, 30));
            btnReports.setText(""); //$NON-NLS-1$
        }
        return btnReports;
    }

    private JButton getBtnBack() {
        if (btnBack == null) {
            btnBack = new JButton();
            btnBack.setIcon(new ImageIcon(getClass().getResource("/circ-images/back.png"))); //$NON-NLS-1$
            btnBack.setToolTipText(Messages.getString("circulation.back")); //$NON-NLS-1$
            btnBack.setFocusable(false);
            btnBack.setPreferredSize(new Dimension(30, 30));
            btnBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    previousPanel();
                    btnBack.setVisible(false);
                }
            });
        }
        return btnBack;
    }

    public void showPanel(String name) {
        mCardLayout.show(mPanel, name);
        panelStack.push(name);
        if (!name.equals("blankPanel")) { //$NON-NLS-1$
            //getBtnBack().setEnabled(true);
            blank = false;
        }
        if (name.equals("reportResultsPanel")) { //$NON-NLS-1$
            getBtnBack().setVisible(true);
        }
    }

    public void previousPanel() {
        if (!panelStack.empty()) {
            String name = panelStack.pop();
            if (name.equals("userPanel")) { //$NON-NLS-1$
                ((User) panels.get(name)).clearPanels();
            }
            Utils.clear(panels.get(name));

            if (!panelStack.empty()) {
                name = panelStack.peek();
                if (name.equals("blankPanel")) { //$NON-NLS-1$
                    //getBtnBack().setEnabled(false);
                    blank = true;
                }
                mCardLayout.show(mPanel, name);
            }
        }
    }

    public void previousTwoPanels() {
        if (!panelStack.empty())
            Utils.clear(panels.get(panelStack.pop()));
        if (!panelStack.empty())
            Utils.clear(panels.get(panelStack.pop()));
        if (!panelStack.empty()) {
            String name = panelStack.peek();
            if (name.equals("blankPanel")) { //$NON-NLS-1$
                //getBtnBack().setEnabled(false);
                blank = true;
            }
            mCardLayout.show(mPanel, name);
        }
    }

    public void handleClose() {
        boolean released = Cirkulacija.getApp().getUserManager().releaseUser();
        if (released) {
            while (!panelStack.empty()) {
                String name = panelStack.pop();
                if (name.equals("userPanel")) { //$NON-NLS-1$
                    User user = (User) panels.get(name);
                    user.cancel();
                    user.clearPanels();
                } else {
                    Utils.clear(panels.get(name));
                }
            }

            showPanel("blankPanel"); //$NON-NLS-1$
            //getBtnBack().setEnabled(false);
            blank = true;
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(Cirkulacija.getApp().getMainFrame(), Messages.getString("circulation.releaseerror"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                    new ImageIcon(getClass().getResource("/circ-images/x32.png")));
        }
    }

}
