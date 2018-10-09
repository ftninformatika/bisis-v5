package com.ftninformatika.bisis.circ.view;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.*;

import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.cards.Report;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Sveska;
import com.ftninformatika.utils.string.Signature;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import com.ftninformatika.utils.Messages;
public class SearchBooksResults extends JPanel {

    private JSplitPane jSplitPane = null;
    private PanelBuilder leftPanel = null;
    private PanelBuilder rightPanel = null;
    private JLabel lDocidNum = null;
    private JLabel lCtlgnoNum = null;
    private JScrollPane leftScrollPane = null;
    private JTree tree = null;
    private JButton btnLend = null;
    private JButton btnReturn = null;
    private JButton btnPrev = null;
    private JButton btnNext = null;
    private JLabel lUser = null;
    private JScrollPane rightScrollPaneInfo = null;
    private JScrollPane rightScrollPaneList = null;
    private JEditorPane textPaneInfo = null;
    private JEditorPane textPaneList = null;
    private JTabbedPane tabPane = null;
    private JButton btnUser = null;
    private JButton btnCancel = null;
    private BooksTreeModel booksTreeModel = null;
    private List<String> hits;
    private int page = 0;
    private static final int PAGE_SIZE = 10;
    private Configuration cfg = null;
    private Template template = null;
    private String library;

    public SearchBooksResults() {
        super();
        initialize();
    }

    public void setFilter(String library) {
        this.library = library;
    }
    public void setHits(List<String> hits) {
        this.hits = hits;
        this.page = 0;
        updateAvailability();
        displayPage();
    }

    public void setCtlgnoNum(int ctlgnonum) {
        getLCtlgnoNum().setText("<html>" + Messages.getString("circulation.copies") + " <b>" + ctlgnonum + "</b></html>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setBounds(new java.awt.Rectangle(0, 0, 700, 400));
        this.add(getJSplitPane(), BorderLayout.CENTER);
        this.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                getTree().requestFocusInWindow();
            }
        });
    }

    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setLeftComponent(getLeftPanel());
            jSplitPane.setRightComponent(getRightPanel());
            jSplitPane.setOneTouchExpandable(true);
            jSplitPane.setDividerLocation(400);
        }
        return jSplitPane;
    }

    private JPanel getLeftPanel() {
        if (leftPanel == null) {
            FormLayout layout = new FormLayout(
                    "0dlu, 15dlu, 18dlu, 15dlu, 18dlu, 30dlu:grow, 10dlu, 5dlu, 10dlu, 5dlu",  //$NON-NLS-1$
                    "0dlu, pref, 2dlu, pref, 2dlu, 200dlu:grow, 2dlu, pref"); //$NON-NLS-1$
            CellConstraints cc = new CellConstraints();
            leftPanel = new PanelBuilder(layout);
            leftPanel.setDefaultDialogBorder();
            leftPanel.add(getLDocidNum(), cc.xyw(2, 2, 5));
            leftPanel.add(getLCtlgnoNum(), cc.xyw(2, 4, 5));
            leftPanel.add(getBtnPrev(), cc.xy(7, 4));
            leftPanel.add(getBtnNext(), cc.xy(9, 4));
            leftPanel.add(getLeftScrollPane(), cc.xyw(2, 6, 9, "fill, fill")); //$NON-NLS-1$
            leftPanel.add(getBtnLend(), cc.xy(3, 8, "fill, fill")); //$NON-NLS-1$
            leftPanel.add(getBtnReturn(), cc.xy(5, 8, "fill, fill")); //$NON-NLS-1$
        }
        return leftPanel.getPanel();
    }

    private JPanel getRightPanel() {
        if (rightPanel == null) {
            FormLayout layout = new FormLayout(
                    "0dlu, 50dlu:grow, pref",  //$NON-NLS-1$
                    "0dlu, pref, 2dlu, pref, 5dlu, pref, 5dlu, 200dlu:grow, 2dlu, pref, 2dlu, pref"); //$NON-NLS-1$
            CellConstraints cc = new CellConstraints();
            rightPanel = new PanelBuilder(layout);
            rightPanel.setDefaultDialogBorder();
            rightPanel.addLabel(Messages.getString("circulation.chargedto"), cc.xy(2, 2)); //$NON-NLS-1$
            rightPanel.add(getLUser(), cc.xy(2, 4));
            rightPanel.add(getBtnUser(), cc.xy(3, 4));
            rightPanel.addSeparator("", cc.xyw(2, 6, 2)); //$NON-NLS-1$
            rightPanel.add(getTabPane(), cc.xyw(2, 8, 2, "fill, fill")); //$NON-NLS-1$
            rightPanel.add(getBtnCancel(), cc.xy(3, 10));

        }
        return rightPanel.getPanel();
    }

    private JScrollPane getLeftScrollPane() {
        if (leftScrollPane == null) {
            leftScrollPane = new JScrollPane();
            leftScrollPane.setViewportView(getTree());
        }
        return leftScrollPane;
    }

    private JTree getTree() {
        if (tree == null) {
            tree = new JTree(getBooksTreeModel());
            tree.setRootVisible(false);
            tree.putClientProperty("JTree.lineStyle", "Horizontal"); //$NON-NLS-1$ //$NON-NLS-2$
            tree.setCellRenderer(new MyRenderer());
            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            tree.addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {
                    getLUser().setText(""); //$NON-NLS-1$
                    getBtnUser().setEnabled(false);
                    getBtnReturn().setEnabled(false);
                    getBtnLend().setEnabled(false);
                    Object node = tree.getLastSelectedPathComponent();
                    if (node == null) return;
                    if (node instanceof Record) {
                        if (getTabPane().getSelectedIndex() == 0) {
                            makeInfo((Record) node);
                        } else {
                            makeList((Record) node);
                        }
                    } else if (node instanceof Primerak) {
                        Primerak primerak = (Primerak) node;
                        if (getBooksTreeModel().isBorrowed(primerak.getInvBroj())) {
                            getLUser().setText("<html><b>" + Cirkulacija.getApp().getUserManager().getChargedUser(primerak.getInvBroj()) + "</b></html>"); //$NON-NLS-1$ //$NON-NLS-2$
                            if (!Cirkulacija.getApp().getUserManager().gotUser())
                                getBtnUser().setEnabled(true);
                            getBtnReturn().setEnabled(true);
                        } else {
                            if (primerak.getStatus() != null && !primerak.getStatus().equals("")) { //$NON-NLS-1$
                                boolean zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get(primerak.getStatus()).isLendable();
                                if (zaduziv) {
                                    getBtnLend().setEnabled(true);
                                }
                            } else {
                                getBtnLend().setEnabled(true);
                            }
                        }
                    } else if (node instanceof Sveska) {
                        Sveska sveska = (Sveska) node;
                        if (getBooksTreeModel().isBorrowed(sveska.getInvBroj())) {
                            getLUser().setText("<html><b>" + Cirkulacija.getApp().getUserManager().getChargedUser(sveska.getInvBroj()) + "</b></html>"); //$NON-NLS-1$ //$NON-NLS-2$
                            if (!Cirkulacija.getApp().getUserManager().gotUser())
                                getBtnUser().setEnabled(true);
                            getBtnReturn().setEnabled(true);
                        } else {
                            if (sveska.getStatus() != null && !sveska.getStatus().equals("")) { //$NON-NLS-1$
                                boolean zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get(sveska.getStatus()).isLendable();
                                if (zaduziv) {
                                    getBtnLend().setEnabled(true);
                                }
                            } else {
                                getBtnLend().setEnabled(true);
                            }
                        }
                    }
                }
            });
        }
        return tree;
    }

    private JButton getBtnLend() {
        if (btnLend == null) {
            btnLend = new JButton();
            btnLend.setToolTipText(Messages.getString("circulation.checkout")); //$NON-NLS-1$
            btnLend.setIcon(new ImageIcon(getClass().getResource("/circ-images/plus16.png"))); //$NON-NLS-1$
            btnLend.setFocusable(false);
            btnLend.setPreferredSize(new java.awt.Dimension(28, 28));
            btnLend.setEnabled(false);
            btnLend.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    if (getTree().getLastSelectedPathComponent() instanceof Primerak) {
                        Cirkulacija.getApp().getUserManager().chargeUser(((Primerak) getTree().getLastSelectedPathComponent()).getInvBroj());
                    } else if (getTree().getLastSelectedPathComponent() instanceof Sveska) {
                        Cirkulacija.getApp().getUserManager().chargeUser(((Sveska) getTree().getLastSelectedPathComponent()).getInvBroj());
                    }
                }
            });
        }
        return btnLend;
    }

    private JButton getBtnReturn() {
        if (btnReturn == null) {
            btnReturn = new JButton();
            btnReturn.setToolTipText(Messages.getString("circulation.discharge")); //$NON-NLS-1$
            btnReturn.setIcon(new ImageIcon(getClass().getResource("/circ-images/minus16.png"))); //$NON-NLS-1$
            btnReturn.setFocusable(false);
            btnReturn.setPreferredSize(new java.awt.Dimension(28, 28));
            btnReturn.setEnabled(false);
            btnReturn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    String[] options = {Messages.getString("circulation.yes"), Messages.getString("circulation.no")};  //$NON-NLS-1$ //$NON-NLS-2$
                    int op = JOptionPane.showOptionDialog(null, Messages.getString("circulation.dischargingwarning"), Messages.getString("circulation.warning"),  //$NON-NLS-1$ //$NON-NLS-2$
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/circ-images/critical32.png")), options, options[1]); //$NON-NLS-1$
                    if (op == JOptionPane.YES_OPTION) {
                        boolean success = false;
                        if (getTree().getLastSelectedPathComponent() instanceof Primerak) {
                            success = Cirkulacija.getApp().getUserManager().dischargeUser(((Primerak) getTree().getLastSelectedPathComponent()).getInvBroj());
                        } else if (getTree().getLastSelectedPathComponent() instanceof Sveska) {
                            success = Cirkulacija.getApp().getUserManager().dischargeUser(((Sveska) getTree().getLastSelectedPathComponent()).getInvBroj());
                        }
                        if (success) {
                            if (getTree().getLastSelectedPathComponent() instanceof Primerak) {
                                getBooksTreeModel().setBorrowed(((Primerak) getTree().getLastSelectedPathComponent()).getInvBroj(), false);
                                //((Primerak)getTree().getLastSelectedPathComponent()).setStanje(0);
                            } else if (getTree().getLastSelectedPathComponent() instanceof Sveska) {
                                getBooksTreeModel().setBorrowed(((Sveska) getTree().getLastSelectedPathComponent()).getInvBroj(), false);
                                //((Sveska)getTree().getLastSelectedPathComponent()).setStanje(0);
                            }
                            btnReturn.setEnabled(false);
                            getBtnLend().setEnabled(true);
                            getLUser().setText(""); //$NON-NLS-1$
                            getBtnUser().setEnabled(false);
                            JOptionPane.showMessageDialog(null, Messages.getString("circulation.ok"), Messages.getString("circulation.info"), JOptionPane.INFORMATION_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                    new ImageIcon(getClass().getResource("/circ-images/hand32.png"))); //$NON-NLS-1$
                        } else {
                            JOptionPane.showMessageDialog(null, Messages.getString("circulation.dischargingnotallowed"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                    new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                        }
                    }
                }
            });
        }
        return btnReturn;
    }

    private JButton getBtnPrev() {
        if (btnPrev == null) {
            btnPrev = new JButton();
            btnPrev.setToolTipText(Messages.getString("circulation.previous")); //$NON-NLS-1$
            btnPrev.setIcon(new ImageIcon(getClass().getResource("/circ-images/prev.gif"))); //$NON-NLS-1$
            btnPrev.setFocusable(false);
            btnPrev.setPreferredSize(new java.awt.Dimension(28, 28));
            btnPrev.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    if (page > 0) {
                        page--;
                        updateAvailability();
                        displayPage();
                    }
                }
            });
        }
        return btnPrev;
    }

    private JButton getBtnNext() {
        if (btnNext == null) {
            btnNext = new JButton();
            btnNext.setToolTipText(Messages.getString("circulation.next")); //$NON-NLS-1$
            btnNext.setIcon(new ImageIcon(getClass().getResource("/circ-images/next.gif"))); //$NON-NLS-1$
            btnNext.setFocusable(false);
            btnNext.setPreferredSize(new java.awt.Dimension(28, 28));
            btnNext.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    if (page < pageCount() - 1) {
                        page++;
                        updateAvailability();
                        displayPage();
                    }
                }
            });
        }
        return btnNext;
    }

    private JTabbedPane getTabPane() {
        if (tabPane == null) {
            tabPane = new JTabbedPane();
            tabPane.addTab(Messages.getString("circulation.info"), null, getRightScrollPaneInfo(), null); //$NON-NLS-1$
            tabPane.addTab(Messages.getString("circulation.catalogcard"), null, getRightScrollPaneList(), null); //$NON-NLS-1$
            tabPane.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    Object node = getTree().getLastSelectedPathComponent();
                    if (node == null) return;
                    if (node instanceof Record) {
                        if (tabPane.getSelectedIndex() == 0) {
                            makeInfo((Record) node);
                        } else {
                            makeList((Record) node);
                        }
                    }
                }
            });
        }
        return tabPane;
    }

    private JScrollPane getRightScrollPaneInfo() {
        if (rightScrollPaneInfo == null) {
            rightScrollPaneInfo = new JScrollPane();
            rightScrollPaneInfo.setViewportView(getTextPaneInfo());
        }
        return rightScrollPaneInfo;
    }

    private JEditorPane getTextPaneInfo() {
        if (textPaneInfo == null) {
            textPaneInfo = new JEditorPane();
            textPaneInfo.setEditable(false);
            textPaneInfo.setFocusable(false);
            textPaneInfo.setContentType("text/html; charset=UTF-8"); //$NON-NLS-1$
//			java.net.URL url = SearchBooksResults.class.getResource(
//            "/com/gint/app/bisis4/client/circ/docs/info.html");
//			try {
//				textPaneInfo.setPage(url);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

        }
        return textPaneInfo;
    }

    private JScrollPane getRightScrollPaneList() {
        if (rightScrollPaneList == null) {
            rightScrollPaneList = new JScrollPane();
            rightScrollPaneList.setViewportView(getTextPaneList());
        }
        return rightScrollPaneList;
    }

    private JEditorPane getTextPaneList() {
        if (textPaneList == null) {
            textPaneList = new JEditorPane();
            textPaneList.setEditable(false);
            textPaneInfo.setFocusable(false);
            textPaneList.setContentType("text/html"); //$NON-NLS-1$
        }
        return textPaneList;
    }

    private JButton getBtnUser() {
        if (btnUser == null) {
            btnUser = new JButton();
            btnUser.setFocusable(false);
            btnUser.setText(Messages.getString("circulation.show")); //$NON-NLS-1$
            btnUser.setIcon(new ImageIcon(getClass().getResource("/circ-images/user16.png"))); //$NON-NLS-1$
            btnUser.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int found = Cirkulacija.getApp().getUserManager().showChargedUser(Cirkulacija.getApp().getMainFrame().getUserPanel());
                    if (found == 1) {
                        Cirkulacija.getApp().getMainFrame().getUserPanel().showLending();
                        Cirkulacija.getApp().getMainFrame().showPanel("userPanel"); //$NON-NLS-1$
                    }
                }
            });
        }
        return btnUser;
    }

    private JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton();
            btnCancel.setFocusable(false);
            btnCancel.setText(Messages.getString("circulation.cancel")); //$NON-NLS-1$
            btnCancel.setIcon(new ImageIcon(getClass().getResource("/circ-images/Delete16.png"))); //$NON-NLS-1$
            btnCancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getLCtlgnoNum().setText(""); //$NON-NLS-1$
                    hits = null;
                    getBooksTreeModel().clear();
                    Cirkulacija.getApp().getMainFrame().previousPanel();
                }
            });
        }
        return btnCancel;
    }

    private JLabel getLCtlgnoNum() {
        if (lCtlgnoNum == null) {
            lCtlgnoNum = new JLabel();
            lCtlgnoNum.setText(Messages.getString("circulation.copies")); //$NON-NLS-1$
        }
        return lCtlgnoNum;
    }

    private JLabel getLDocidNum() {
        if (lDocidNum == null) {
            lDocidNum = new JLabel();
            lDocidNum.setText(Messages.getString("circulation.records")); //$NON-NLS-1$
        }
        return lDocidNum;
    }

    private JLabel getLUser() {
        if (lUser == null) {
            lUser = new JLabel();
            lUser.setText(Messages.getString("circulation.numberandname")); //$NON-NLS-1$
        }
        return lUser;
    }

    class MyRenderer extends DefaultTreeCellRenderer {

        public MyRenderer() {
        }

        public Component getTreeCellRendererComponent(
                JTree tree,
                Object value,
                boolean sel,
                boolean expanded,
                boolean leaf,
                int row,
                boolean hasFocus) {

            super.getTreeCellRendererComponent(
                    tree, value, sel,
                    expanded, leaf, row,
                    hasFocus);

            if (leaf) {
                setIcon(null);
                if (value instanceof Primerak) {
                    Primerak primerak = (Primerak) value;
                    if (primerak.getStatus() != null && !primerak.getStatus().equals("")) {
                        String statusOpis = BisisApp.appConfig.getCodersHelper().getItemStatuses().get(primerak.getStatus()).getDescription();
                        boolean zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get(primerak.getStatus()).isLendable();
                        String stanje = null;
                        if (zaduziv) {
                            if (getBooksTreeModel().isBorrowed(primerak.getInvBroj())) {
                                stanje = Messages.getString("circulation.charged"); //$NON-NLS-1$
                                setForeground(new Color(217, 19, 19));
                            } else {
                                stanje = Messages.getString("circulation.free"); //$NON-NLS-1$
                                setForeground(new Color(77, 138, 74));
                            }
                        } else {
                            if (getBooksTreeModel().isBorrowed(primerak.getInvBroj())) {
                                stanje = Messages.getString("circulation.charged"); //$NON-NLS-1$
                            } else {
                                stanje = Messages.getString("circulation.free"); //$NON-NLS-1$
                            }
                            setForeground(new Color(217, 19, 19));
                        }
                        setText(primerak.getInvBroj() + ", " + statusOpis + ", " + stanje + ", " + Signature.format(primerak)); //$NON-NLS-1$ //$NON-NLS-2$
                    } else {
                        String stanje = null;
                        if (getBooksTreeModel().isBorrowed(primerak.getInvBroj())) {
                            stanje = Messages.getString("circulation.charged"); //$NON-NLS-1$
                            setForeground(new Color(217, 19, 19));
                        } else {
                            stanje = Messages.getString("circulation.free"); //$NON-NLS-1$
                            setForeground(new Color(77, 138, 74));
                        }
                        setText(primerak.getInvBroj() + ", " + stanje); //$NON-NLS-1$
                    }
                } else if (value instanceof Sveska) {
                    Sveska sveska = (Sveska) value;
                    if (sveska.getStatus() != null && !sveska.getStatus().equals("")) { //$NON-NLS-1$
                        String statusOpis = BisisApp.appConfig.getCodersHelper().getItemStatuses().get(sveska.getStatus()).getDescription();
                        boolean zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get(sveska.getStatus()).isLendable();

                        String stanje = null;
                        if (zaduziv) {
                            if (getBooksTreeModel().isBorrowed(sveska.getInvBroj())) {
                                stanje = Messages.getString("circulation.charged"); //$NON-NLS-1$
                                setForeground(new Color(217, 19, 19));
                            } else {
                                stanje = Messages.getString("circulation.free"); //$NON-NLS-1$
                                setForeground(new Color(77, 138, 74));
                            }
                        } else {
                            if (getBooksTreeModel().isBorrowed(sveska.getInvBroj())) {
                                stanje = Messages.getString("circulation.charged"); //$NON-NLS-1$
                            } else {
                                stanje = Messages.getString("circulation.free"); //$NON-NLS-1$
                            }
                            setForeground(new Color(217, 19, 19));
                        }
                        setText(sveska.getInvBroj() + Messages.getString("circulation.partnumber") + sveska.getBrojSveske() + ", " + statusOpis + ", " + stanje); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    } else {
                        String stanje = null;
                        if (getBooksTreeModel().isBorrowed(sveska.getInvBroj())) {
                            stanje = Messages.getString("circulation.charged"); //$NON-NLS-1$
                            setForeground(new Color(217, 19, 19));
                        } else {
                            stanje = Messages.getString("circulation.free"); //$NON-NLS-1$
                            setForeground(new Color(77, 138, 74));
                        }
                        setText(sveska.getInvBroj() + Messages.getString("circulation.partnumber") + sveska.getBrojSveske() + ", " + stanje); //$NON-NLS-1$ //$NON-NLS-2$
                    }
                }

            } else {
                if (value instanceof Record) {
                    Record rec = (Record) value;
                    setIcon(new ImageIcon(getClass().getResource("/circ-images/book16.png"))); //$NON-NLS-1$
                    String text = "<html>"; //$NON-NLS-1$
                    RecordBean bean = new RecordBean(rec);
                    if (!bean.getAutor().equals("")) //$NON-NLS-1$
                        text = text + bean.getAutor() + ", "; //$NON-NLS-1$
                    if (!bean.getNaslov().equals("")) //$NON-NLS-1$
                        text = text + "<b>" + bean.getNaslov() + "</b>, "; //$NON-NLS-1$ //$NON-NLS-2$
                    if (!bean.getTom().equals("")) //$NON-NLS-1$
                        text = text + bean.getTom() + ", "; //$NON-NLS-1$
                    if (!bean.getIzdavac().equals("")) //$NON-NLS-1$
                        text = text + bean.getIzdavac() + ", "; //$NON-NLS-1$
                    if (!bean.getGodinaizdanja().equals("")) //$NON-NLS-1$
                        text = text + bean.getGodinaizdanja() + ", "; //$NON-NLS-1$
                    if (!bean.getMestoizdanja().equals("")) //$NON-NLS-1$
                        text = text + bean.getMestoizdanja() + ", "; //$NON-NLS-1$
                    if (!text.equals("<html>")) //$NON-NLS-1$
                        text = text.substring(0, text.length() - 2);
                    text = text + "</html>"; //$NON-NLS-1$
                    setText(text);
                } else if (value instanceof Godina) {
                    setIcon(null);
                    Godina godina = (Godina) value;
                    setText("<html><b>" + godina.getGodina() + "</b>, Vol " + godina.getGodiste() + "</html>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                }
            }

            return this;
        }
    }

    private BooksTreeModel getBooksTreeModel() {
        if (booksTreeModel == null) {
            booksTreeModel = new BooksTreeModel();
        }
        return booksTreeModel;
    }


    private void updateAvailability() {
        btnPrev.setEnabled(page != 0);
        btnNext.setEnabled(page != pageCount() - 1);
    }

    private void displayPage() {
        if (hits == null || hits.size() == 0)
            return;
        int count = PAGE_SIZE;
        if (page == pageCount() - 1) {
            if (hits.size() % PAGE_SIZE == 0) {
                count = PAGE_SIZE;
            } else {
                count = hits.size() % PAGE_SIZE;
            }
        }

        //int[] recIDs = new int[count];
        ArrayList<String> recIDs = new ArrayList<>();
        for (int i = 0; i < count; i++)
            recIDs.add(hits.get(page * PAGE_SIZE + i));
        getBooksTreeModel().setHits(recIDs, library);
        getLDocidNum().setText("<html>" + Messages.getString("circulation.records") + " <b>" + (page * PAGE_SIZE + 1) + "-" +  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                (page * PAGE_SIZE + count) + "/" + hits.size() + "</b></html>"); //$NON-NLS-1$ //$NON-NLS-2$
        getTree().setSelectionRow(0);
//    getTree().requestFocusInWindow();
//    System.out.println(getTree().isFocusOwner());

    }

    private int pageCount() {
        if (hits == null || hits.size() == 0)
            return 0;
        return hits.size() / PAGE_SIZE + (hits.size() % PAGE_SIZE > 0 ? 1 : 0);
    }

    private void makeInfo(Record rec) {
        Writer out = new StringWriter();
        try {
            getTemplate().process(new RecordBean(rec), out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        getTextPaneInfo().setText(out.toString());
        getTextPaneInfo().setCaretPosition(0);
    }

    private Configuration getConfiguration() {
        if (cfg == null) {
            cfg = new Configuration();
            cfg.setClassForTemplateLoading(Cirkulacija.class, "/cirkulacija/docs/"); //$NON-NLS-1$
        }
        return cfg;
    }

    private Template getTemplate() {
        if (template == null) {
            try {
                template = getConfiguration().getTemplate("info.ftl"); //$NON-NLS-1$
            } catch (IOException e) {
            }
        }
        return template;
    }

    private void makeList(Record rec) {
        getTextPaneList().setText(Report.makeOne(rec, true));
        getTextPaneList().setCaretPosition(0);
    }
}
