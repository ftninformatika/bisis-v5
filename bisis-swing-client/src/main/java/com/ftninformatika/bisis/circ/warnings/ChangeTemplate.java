package com.ftninformatika.bisis.circ.warnings;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.StringWriter;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;


import com.ftninformatika.bisis.circ.manager.WarningsManager;
import com.ftninformatika.bisis.circ.WarningType;
import warning.RootDocument;


public class ChangeTemplate extends JPanel {

    private JSplitPane jSplitPane = null;
    private JTree jTree = null;
    private JPanel rightPanel = null;
    private JPanel cardPanel = null;
    private JPanel rightDownPanel = null;
    private CardLayout mCardLayout = null;
    private final String leaf0 = "naziv";
    private final String leaf1 = "zaglavlje";
    private final String leaf2 = "tekst iznad i ispod tabele";
    private final String leaf3 = "zaglavlje tabele";
    private final String leaf4 = "nadoknade i vrednost knjiga";
    private final String leaf5 = "podaci o korisniku";
    private final String leaf6 = "direktor";
    private String[] tree = {leaf0, leaf1, leaf2, leaf3, leaf4, leaf5, leaf6};
    private JLabel lAlphabet = null;
    private JRadioButton rbLatin = null;
    private JRadioButton rbCyrillic = null;
    private ButtonGroup rbGroup = null;
    private JPanel nazivPanel = null;
    private JTextField tfNazivSablona = null;
    private JPanel zaglavljePanel = null;
    private JTextField tfBiblioteka = null;
    private JTextField tfNaziv = null;
    private JTextField tfOgranak = null;
    private JTextField tfSifra = null;
    private JTextField tfAdresa = null;
    private JTextField tfMesto = null;
    private JTextField tfBropomenetext = null;
    private JTextField tfNaslov = null;
    private JTextField tfRok = null;
    private JPanel textPanel = null;
    private JTextArea taIznad = null;
    private JTextArea taIspod = null;
    private JPanel zaglavljeTabelePanel = null;
    private JTextField tfRbrT = null;
    private JTextField tfNaslovT = null;
    private JTextField tfAutorT = null;
    private JTextField tfCtlgNoT = null;
    private JTextField tfBrdanaT = null;
    private JPanel nadoknadePanel = null;
    private JTextField tfNadoknada1 = null;
    private JTextField tfCena1 = null;
    private JTextField tfDin = null;
    private JTextField tfNadoknada2 = null;
    private JTextField tfCena2 = null;
    private JTextField tfDodatuma = null;
    private JTextField tfPojedinacno = null;
    private JTextField tfTrostruko = null;
    private JPanel podaciPanel = null;
    private JTextField tfRoditelj = null;
    private JTextField tfUseridtext = null;
    private JTextField tfDocno = null;
    private JTextField tfDocmesto = null;
    private JTextField tfJmbg = null;
    private JPanel direktorPanel = null;
    private JTextField tfBibliotekaD = null;
    private JTextField tfDirektor = null;
    private JTextField tfImedirektora = null;
    private RootDocument doc = null;
    private boolean dirty = false;
    private WarningType wtype = null;
    private JTextField tfNapomena = null;
    private JTextField tfSigNoT = null;
    private WarningsManager manager = null;
    private WarningsFrame warningsFrame = null;

    public ChangeTemplate(WarningsManager manager, WarningsFrame warningsFrame) {
        super();
        initialize();
        this.manager = manager;
        this.warningsFrame = warningsFrame;
    }

    private void initialize() {
        this.setSize(750, 460);
        this.setPreferredSize(new java.awt.Dimension(750, 470));
        this.add(getJSplitPane(), null);
    }

    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setPreferredSize(new java.awt.Dimension(750, 460));
            jSplitPane.setRightComponent(getRightPanel());
            jSplitPane.setLeftComponent(getJTree());
        }
        return jSplitPane;
    }

    private JPanel getRightPanel() {
        if (rightPanel == null) {
            rightPanel = new JPanel();
            rightPanel.setLayout(new BorderLayout());
            rightPanel.setPreferredSize(new java.awt.Dimension(540, 490));
            rightPanel.add(getCardPanel(), java.awt.BorderLayout.CENTER);
            rightPanel.add(getRightDownPanel(), java.awt.BorderLayout.SOUTH);
        }
        return rightPanel;
    }

    private JPanel getCardPanel() {
        if (cardPanel == null) {
            cardPanel = new JPanel();
            mCardLayout = new CardLayout();
            cardPanel.setLayout(mCardLayout);
            cardPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray, 1));
            cardPanel.setPreferredSize(new java.awt.Dimension(500, 350));
            cardPanel.add(getNazivPanel(), getNazivPanel().getName());
            cardPanel.add(getZaglavljePanel(), getZaglavljePanel().getName());
            cardPanel.add(getTextPanel(), getTextPanel().getName());
            cardPanel.add(getZaglavljeTabelePanel(), getZaglavljeTabelePanel().getName());
            cardPanel.add(getNadoknadePanel(), getNadoknadePanel().getName());
            cardPanel.add(getPodaciPanel(), getPodaciPanel().getName());
            cardPanel.add(getDirektorPanel(), getDirektorPanel().getName());
        }
        return cardPanel;
    }

    private JPanel getRightDownPanel() {
        if (rightDownPanel == null) {
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.insets = new java.awt.Insets(0, 0, 10, 250);
            gridBagConstraints2.gridy = 2;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.insets = new java.awt.Insets(0, 0, 10, 250);
            gridBagConstraints1.gridy = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.insets = new java.awt.Insets(20, 10, 10, 200);
            gridBagConstraints.gridy = 0;
            lAlphabet = new JLabel();
            lAlphabet.setText("Podaci iz baze se prikazuju na:");
            rightDownPanel = new JPanel();
            rightDownPanel.setLayout(new GridBagLayout());
            rightDownPanel.setPreferredSize(new java.awt.Dimension(500, 150));
            rightDownPanel.add(lAlphabet, gridBagConstraints);
            rightDownPanel.add(getRbLatin(), gridBagConstraints1);
            rightDownPanel.add(getRbCyrillic(), gridBagConstraints2);
        }
        return rightDownPanel;
    }

    private JRadioButton getRbLatin() {
        if (rbLatin == null) {
            rbLatin = new JRadioButton();
            rbLatin.setText("latinici");
            rbLatin.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    handleKeyTyped();
                }
            });
            getRbGroup().add(rbLatin);
        }
        return rbLatin;
    }

    private JRadioButton getRbCyrillic() {
        if (rbCyrillic == null) {
            rbCyrillic = new JRadioButton();
            rbCyrillic.setText("\u0107irilici");
            rbCyrillic.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    handleKeyTyped();
                }
            });
            getRbGroup().add(rbCyrillic);
        }
        return rbCyrillic;
    }

    private ButtonGroup getRbGroup() {
        if (rbGroup == null) {
            rbGroup = new ButtonGroup();
        }
        return rbGroup;
    }

    private JPanel getNazivPanel() {
        if (nazivPanel == null) {
            GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
            gridBagConstraints39.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints39.gridy = 2;
            gridBagConstraints39.weightx = 1.0;
            gridBagConstraints39.insets = new java.awt.Insets(10, 10, 100, 10);
            gridBagConstraints39.gridx = 0;
            nazivPanel = new JPanel();
            nazivPanel.setLayout(new GridBagLayout());
            nazivPanel.setName("nazivPanel");
            nazivPanel.add(getTfNazivSablona(), gridBagConstraints39);
        }
        return nazivPanel;
    }

    private JTextField getTfNazivSablona() {
        if (tfNazivSablona == null) {
            tfNazivSablona = new JTextField();
            tfNazivSablona.setToolTipText("naziv sablona");
            tfNazivSablona.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfNazivSablona;
    }

    private JPanel getZaglavljePanel() {
        if (zaglavljePanel == null) {
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.gridy = 8;
            gridBagConstraints11.weightx = 1.0;
            gridBagConstraints11.insets = new java.awt.Insets(0, 10, 10, 10);
            gridBagConstraints11.gridx = 0;
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints10.gridy = 7;
            gridBagConstraints10.weightx = 1.0;
            gridBagConstraints10.insets = new java.awt.Insets(40, 10, 10, 10);
            gridBagConstraints10.gridx = 0;
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints9.gridy = 6;
            gridBagConstraints9.weightx = 1.0;
            gridBagConstraints9.insets = new java.awt.Insets(0, 10, 10, 10);
            gridBagConstraints9.gridx = 0;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints8.gridy = 5;
            gridBagConstraints8.weightx = 1.0;
            gridBagConstraints8.insets = new java.awt.Insets(0, 10, 10, 10);
            gridBagConstraints8.gridx = 0;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints7.gridy = 4;
            gridBagConstraints7.weightx = 1.0;
            gridBagConstraints7.insets = new java.awt.Insets(0, 10, 10, 10);
            gridBagConstraints7.gridx = 0;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.gridy = 3;
            gridBagConstraints5.weightx = 1.0;
            gridBagConstraints5.insets = new java.awt.Insets(0, 10, 10, 10);
            gridBagConstraints5.gridx = 0;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.gridy = 2;
            gridBagConstraints4.weightx = 1.0;
            gridBagConstraints4.insets = new java.awt.Insets(0, 10, 10, 10);
            gridBagConstraints4.gridx = 0;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridy = 1;
            gridBagConstraints3.weightx = 1.0;
            gridBagConstraints3.insets = new java.awt.Insets(0, 10, 10, 10);
            gridBagConstraints3.gridx = 0;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints6.gridy = 0;
            gridBagConstraints6.weightx = 1.0;
            gridBagConstraints6.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints6.gridx = 0;
            zaglavljePanel = new JPanel();
            zaglavljePanel.setLayout(new GridBagLayout());
            zaglavljePanel.setName("zaglavljePanel");
            zaglavljePanel.add(getTfBiblioteka(), gridBagConstraints6);
            zaglavljePanel.add(getTfNaziv(), gridBagConstraints3);
            zaglavljePanel.add(getTfOgranak(), gridBagConstraints4);
            zaglavljePanel.add(getTfSifra(), gridBagConstraints5);
            zaglavljePanel.add(getTfAdresa(), gridBagConstraints7);
            zaglavljePanel.add(getTfMesto(), gridBagConstraints8);
            zaglavljePanel.add(getTfBropomenetext(), gridBagConstraints9);
            zaglavljePanel.add(getTfNaslov(), gridBagConstraints10);
            zaglavljePanel.add(getTfRok(), gridBagConstraints11);
        }
        return zaglavljePanel;
    }

    private JTextField getTfBiblioteka() {
        if (tfBiblioteka == null) {
            tfBiblioteka = new JTextField();
            tfBiblioteka.setToolTipText("biblioteka");
            tfBiblioteka.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfBiblioteka;
    }

    private JTextField getTfNaziv() {
        if (tfNaziv == null) {
            tfNaziv = new JTextField();
            tfNaziv.setToolTipText("naziv biblioteke");
            tfNaziv.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfNaziv;
    }

    private JTextField getTfOgranak() {
        if (tfOgranak == null) {
            tfOgranak = new JTextField();
            tfOgranak.setToolTipText("ogranak");
            tfOgranak.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfOgranak;
    }

    private JTextField getTfSifra() {
        if (tfSifra == null) {
            tfSifra = new JTextField();
            tfSifra.setToolTipText("\u0161ifra biblioteke");
            tfSifra.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfSifra;
    }

    private JTextField getTfAdresa() {
        if (tfAdresa == null) {
            tfAdresa = new JTextField();
            tfAdresa.setToolTipText("adresa");
            tfAdresa.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfAdresa;
    }

    private JTextField getTfMesto() {
        if (tfMesto == null) {
            tfMesto = new JTextField();
            tfMesto.setToolTipText("mesto");
            tfMesto.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfMesto;
    }

    private JTextField getTfBropomenetext() {
        if (tfBropomenetext == null) {
            tfBropomenetext = new JTextField();
            tfBropomenetext.setToolTipText("br. opomene");
            tfBropomenetext.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfBropomenetext;
    }

    private JTextField getTfNaslov() {
        if (tfNaslov == null) {
            tfNaslov = new JTextField();
            tfNaslov.setToolTipText("naslov");
            tfNaslov.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfNaslov;
    }

    private JTextField getTfRok() {
        if (tfRok == null) {
            tfRok = new JTextField();
            tfRok.setToolTipText("rok vra\u0107anja");
            tfRok.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfRok;
    }

    private JPanel getTextPanel() {
        if (textPanel == null) {
            GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
            gridBagConstraints35.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints35.gridy = 2;
            gridBagConstraints35.weightx = 1.0;
            gridBagConstraints35.insets = new java.awt.Insets(10, 10, 50, 10);
            gridBagConstraints35.gridx = 0;
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints13.weighty = 1.0;
            gridBagConstraints13.gridx = 0;
            gridBagConstraints13.gridy = 1;
            gridBagConstraints13.insets = new java.awt.Insets(10, 10, 20, 10);
            gridBagConstraints13.weightx = 1.0;
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints12.gridwidth = 1;
            gridBagConstraints12.gridx = 0;
            gridBagConstraints12.gridy = 0;
            gridBagConstraints12.weightx = 1.0;
            gridBagConstraints12.weighty = 1.0;
            gridBagConstraints12.insets = new java.awt.Insets(50, 10, 10, 10);
            textPanel = new JPanel();
            textPanel.setLayout(new GridBagLayout());
            textPanel.setName("textPanel");
            textPanel.add(getTaIznad(), gridBagConstraints12);
            textPanel.add(getTaIspod(), gridBagConstraints13);
            textPanel.add(getTfNapomena(), gridBagConstraints35);
        }
        return textPanel;
    }

    private JTextArea getTaIznad() {
        if (taIznad == null) {
            taIznad = new JTextArea();
            taIznad.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray, 1));
            taIznad.setWrapStyleWord(true);
            taIznad.setLineWrap(true);
            taIznad.setToolTipText("tekst iznad tabele");
            taIznad.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return taIznad;
    }

    private JTextArea getTaIspod() {
        if (taIspod == null) {
            taIspod = new JTextArea();
            taIspod.setWrapStyleWord(true);
            taIspod.setToolTipText("tekst ispod tabele");
            taIspod.setLineWrap(true);
            taIspod.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray, 1));
            taIspod.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return taIspod;
    }

    public void showPanel(String name) {
        mCardLayout.show(getCardPanel(), name);
    }

    private JTree getJTree() {
        if (jTree == null) {
            jTree = new JTree(tree);
            jTree.setPreferredSize(new java.awt.Dimension(190, 490));
            jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            jTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
                public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
                    if (node == null) {
                        return;
                    } else if (node.toString().equals(leaf0)) {
                        showPanel("nazivPanel");
                    } else if (node.toString().equals(leaf1)) {
                        showPanel("zaglavljePanel");
                    } else if (node.toString().equals(leaf2)) {
                        showPanel("textPanel");
                    } else if (node.toString().equals(leaf3)) {
                        showPanel("zaglavljeTabelePanel");
                    } else if (node.toString().equals(leaf4)) {
                        showPanel("nadoknadePanel");
                    } else if (node.toString().equals(leaf5)) {
                        showPanel("podaciPanel");
                    } else if (node.toString().equals(leaf6)) {
                        showPanel("direktorPanel");
                    }
                }
            });
//			DefaultTreeCellRenderer renderer = 
//				new DefaultTreeCellRenderer();
//				    renderer.setLeafIcon(null);
//				    jTree.setCellRenderer(renderer);
        }
        return jTree;
    }

    private JPanel getZaglavljeTabelePanel() {
        if (zaglavljeTabelePanel == null) {
            GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
            gridBagConstraints36.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints36.gridy = 4;
            gridBagConstraints36.weightx = 1.0;
            gridBagConstraints36.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints36.gridx = 0;
            GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
            gridBagConstraints18.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints18.gridy = 5;
            gridBagConstraints18.weightx = 1.0;
            gridBagConstraints18.insets = new java.awt.Insets(10, 10, 50, 10);
            gridBagConstraints18.gridx = 0;
            GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
            gridBagConstraints17.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints17.gridy = 3;
            gridBagConstraints17.weightx = 1.0;
            gridBagConstraints17.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints17.gridx = 0;
            GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
            gridBagConstraints16.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints16.gridy = 2;
            gridBagConstraints16.weightx = 1.0;
            gridBagConstraints16.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints16.gridx = 0;
            GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
            gridBagConstraints15.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints15.gridy = 1;
            gridBagConstraints15.weightx = 1.0;
            gridBagConstraints15.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints15.gridx = 0;
            GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
            gridBagConstraints14.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints14.gridy = 0;
            gridBagConstraints14.weightx = 1.0;
            gridBagConstraints14.insets = new java.awt.Insets(50, 10, 10, 10);
            gridBagConstraints14.gridx = 0;
            zaglavljeTabelePanel = new JPanel();
            zaglavljeTabelePanel.setLayout(new GridBagLayout());
            zaglavljeTabelePanel.setName("zaglavljeTabelePanel");
            zaglavljeTabelePanel.add(getTfRbrT(), gridBagConstraints14);
            zaglavljeTabelePanel.add(getTfNaslovT(), gridBagConstraints15);
            zaglavljeTabelePanel.add(getTfAutorT(), gridBagConstraints16);
            zaglavljeTabelePanel.add(getTfCtlgNoT(), gridBagConstraints17);
            zaglavljeTabelePanel.add(getTfBrdanaT(), gridBagConstraints18);
            zaglavljeTabelePanel.add(getTfSigNoT(), gridBagConstraints36);
        }
        return zaglavljeTabelePanel;
    }

    private JTextField getTfRbrT() {
        if (tfRbrT == null) {
            tfRbrT = new JTextField();
            tfRbrT.setToolTipText("r.br.");
            tfRbrT.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfRbrT;
    }

    private JTextField getTfNaslovT() {
        if (tfNaslovT == null) {
            tfNaslovT = new JTextField();
            tfNaslovT.setToolTipText("naslov");
            tfNaslovT.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfNaslovT;
    }

    private JTextField getTfAutorT() {
        if (tfAutorT == null) {
            tfAutorT = new JTextField();
            tfAutorT.setToolTipText("autor");
            tfAutorT.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfAutorT;
    }

    private JTextField getTfCtlgNoT() {
        if (tfCtlgNoT == null) {
            tfCtlgNoT = new JTextField();
            tfCtlgNoT.setToolTipText("inv. broj");
            tfCtlgNoT.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfCtlgNoT;
    }

    private JTextField getTfBrdanaT() {
        if (tfBrdanaT == null) {
            tfBrdanaT = new JTextField();
            tfBrdanaT.setToolTipText("br. dana prekora\u010denja");
            tfBrdanaT.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfBrdanaT;
    }

    private JPanel getNadoknadePanel() {
        if (nadoknadePanel == null) {
            GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
            gridBagConstraints26.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints26.gridy = 6;
            gridBagConstraints26.weightx = 1.0;
            gridBagConstraints26.gridwidth = 2;
            gridBagConstraints26.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints26.gridx = 0;
            GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
            gridBagConstraints25.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints25.gridy = 5;
            gridBagConstraints25.weightx = 1.0;
            gridBagConstraints25.gridwidth = 2;
            gridBagConstraints25.insets = new java.awt.Insets(30, 10, 10, 10);
            gridBagConstraints25.gridx = 0;
            GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
            gridBagConstraints24.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints24.gridy = 4;
            gridBagConstraints24.weightx = 1.0;
            gridBagConstraints24.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints24.gridx = 0;
            GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
            gridBagConstraints23.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints23.gridy = 3;
            gridBagConstraints23.weightx = 1.0;
            gridBagConstraints23.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints23.gridx = 0;
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            gridBagConstraints22.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints22.gridy = 2;
            gridBagConstraints22.weightx = 1.0;
            gridBagConstraints22.gridwidth = 2;
            gridBagConstraints22.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints22.gridx = 0;
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints21.gridy = 1;
            gridBagConstraints21.weightx = 1.0;
            gridBagConstraints21.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints21.gridx = 1;
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            gridBagConstraints20.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints20.gridy = 1;
            gridBagConstraints20.weightx = 1.0;
            gridBagConstraints20.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints20.gridx = 0;
            GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
            gridBagConstraints19.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints19.gridy = 0;
            gridBagConstraints19.weightx = 1.0;
            gridBagConstraints19.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints19.gridwidth = 2;
            gridBagConstraints19.gridx = 0;
            nadoknadePanel = new JPanel();
            nadoknadePanel.setLayout(new GridBagLayout());
            nadoknadePanel.setName("nadoknadePanel");
            nadoknadePanel.add(getTfNadoknada1(), gridBagConstraints19);
            nadoknadePanel.add(getTfCena1(), gridBagConstraints20);
            nadoknadePanel.add(getTfDin(), gridBagConstraints21);
            nadoknadePanel.add(getTfNadoknada2(), gridBagConstraints22);
            nadoknadePanel.add(getTfCena2(), gridBagConstraints23);
            nadoknadePanel.add(getTfDodatuma(), gridBagConstraints24);
            nadoknadePanel.add(getTfPojedinacno(), gridBagConstraints25);
            nadoknadePanel.add(getTfTrostruko(), gridBagConstraints26);
        }
        return nadoknadePanel;
    }

    private JTextField getTfNadoknada1() {
        if (tfNadoknada1 == null) {
            tfNadoknada1 = new JTextField();
            tfNadoknada1.setToolTipText("nadoknada za prekora\u010denje roka dnevno po svakoj knjizi");
            tfNadoknada1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfNadoknada1;
    }

    private JTextField getTfCena1() {
        if (tfCena1 == null) {
            tfCena1 = new JTextField();
            tfCena1.setToolTipText("cena - dnevno po svakoj knjizi");
            tfCena1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfCena1;
    }

    private JTextField getTfDin() {
        if (tfDin == null) {
            tfDin = new JTextField();
            tfDin.setToolTipText("din.");
            tfDin.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfDin;
    }

    private JTextField getTfNadoknada2() {
        if (tfNadoknada2 == null) {
            tfNadoknada2 = new JTextField();
            tfNadoknada2.setToolTipText("nadoknada za tro\u0161kove opomene");
            tfNadoknada2.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfNadoknada2;
    }

    private JTextField getTfCena2() {
        if (tfCena2 == null) {
            tfCena2 = new JTextField();
            tfCena2.setToolTipText("cena  - tro\u0161kovi opomene");
            tfCena2.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfCena2;
    }

    private JTextField getTfDodatuma() {
        if (tfDodatuma == null) {
            tfDodatuma = new JTextField();
            tfDodatuma.setToolTipText("ukupno do");
            tfDodatuma.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfDodatuma;
    }

    private JTextField getTfPojedinacno() {
        if (tfPojedinacno == null) {
            tfPojedinacno = new JTextField();
            tfPojedinacno.setToolTipText("pojedina\u010dna vrednost knjiga");
            tfPojedinacno.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfPojedinacno;
    }

    private JTextField getTfTrostruko() {
        if (tfTrostruko == null) {
            tfTrostruko = new JTextField();
            tfTrostruko.setToolTipText("trostruka vrednost knjiga");
            tfTrostruko.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfTrostruko;
    }

    private JPanel getPodaciPanel() {
        if (podaciPanel == null) {
            GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints31.gridy = 4;
            gridBagConstraints31.weightx = 1.0;
            gridBagConstraints31.insets = new java.awt.Insets(10, 10, 50, 10);
            gridBagConstraints31.gridx = 0;
            GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
            gridBagConstraints30.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints30.gridy = 3;
            gridBagConstraints30.weightx = 1.0;
            gridBagConstraints30.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints30.gridx = 0;
            GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
            gridBagConstraints29.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints29.gridy = 2;
            gridBagConstraints29.weightx = 1.0;
            gridBagConstraints29.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints29.gridx = 0;
            GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
            gridBagConstraints28.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints28.gridy = 1;
            gridBagConstraints28.weightx = 1.0;
            gridBagConstraints28.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints28.gridx = 0;
            GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
            gridBagConstraints27.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints27.gridy = 0;
            gridBagConstraints27.weightx = 1.0;
            gridBagConstraints27.insets = new java.awt.Insets(50, 10, 10, 10);
            gridBagConstraints27.gridx = 0;
            podaciPanel = new JPanel();
            podaciPanel.setLayout(new GridBagLayout());
            podaciPanel.setName("podaciPanel");
            podaciPanel.add(getTfRoditelj(), gridBagConstraints27);
            podaciPanel.add(getTfUseridtext(), gridBagConstraints28);
            podaciPanel.add(getTfDocno(), gridBagConstraints29);
            podaciPanel.add(getTfDocmesto(), gridBagConstraints30);
            podaciPanel.add(getTfJmbg(), gridBagConstraints31);
        }
        return podaciPanel;
    }

    private JTextField getTfRoditelj() {
        if (tfRoditelj == null) {
            tfRoditelj = new JTextField();
            tfRoditelj.setToolTipText("roditelj");
            tfRoditelj.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfRoditelj;
    }

    private JTextField getTfUseridtext() {
        if (tfUseridtext == null) {
            tfUseridtext = new JTextField();
            tfUseridtext.setToolTipText("br. \u010dlanske karte");
            tfUseridtext.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfUseridtext;
    }

    private JTextField getTfDocno() {
        if (tfDocno == null) {
            tfDocno = new JTextField();
            tfDocno.setToolTipText("br.li\u010dne karte");
            tfDocno.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfDocno;
    }

    private JTextField getTfDocmesto() {
        if (tfDocmesto == null) {
            tfDocmesto = new JTextField();
            tfDocmesto.setToolTipText("mesto izdavanja");
            tfDocmesto.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfDocmesto;
    }

    private JTextField getTfJmbg() {
        if (tfJmbg == null) {
            tfJmbg = new JTextField();
            tfJmbg.setToolTipText("jmbg");
            tfJmbg.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfJmbg;
    }

    private JPanel getDirektorPanel() {
        if (direktorPanel == null) {
            GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
            gridBagConstraints34.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints34.gridy = 2;
            gridBagConstraints34.weightx = 1.0;
            gridBagConstraints34.insets = new java.awt.Insets(10, 10, 100, 10);
            gridBagConstraints34.gridx = 0;
            GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
            gridBagConstraints33.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints33.gridy = 1;
            gridBagConstraints33.weightx = 1.0;
            gridBagConstraints33.insets = new java.awt.Insets(10, 10, 10, 10);
            gridBagConstraints33.gridx = 0;
            GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
            gridBagConstraints32.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints32.gridy = 0;
            gridBagConstraints32.weightx = 1.0;
            gridBagConstraints32.insets = new java.awt.Insets(100, 10, 10, 10);
            gridBagConstraints32.gridx = 0;
            direktorPanel = new JPanel();
            direktorPanel.setLayout(new GridBagLayout());
            direktorPanel.setName("direktorPanel");
            direktorPanel.add(getTfBibliotekaD(), gridBagConstraints32);
            direktorPanel.add(getTfDirektor(), gridBagConstraints33);
            direktorPanel.add(getTfImedirektora(), gridBagConstraints34);
        }
        return direktorPanel;
    }

    private JTextField getTfBibliotekaD() {
        if (tfBibliotekaD == null) {
            tfBibliotekaD = new JTextField();
            tfBibliotekaD.setToolTipText("biblioteka");
            tfBibliotekaD.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfBibliotekaD;
    }

    private JTextField getTfDirektor() {
        if (tfDirektor == null) {
            tfDirektor = new JTextField();
            tfDirektor.setToolTipText("direktor");
            tfDirektor.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfDirektor;
    }

    private JTextField getTfImedirektora() {
        if (tfImedirektora == null) {
            tfImedirektora = new JTextField();
            tfImedirektora.setToolTipText("ime direktora");
            tfImedirektora.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfImedirektora;
    }

    public void setContent(WarningType wtype) {
        try {
            this.wtype = wtype;
            System.out.println("wtype " + wtype.hashCode());
            getTfNazivSablona().setText(wtype.getDescription());
            doc = RootDocument.Factory.parse(wtype.getTemplate());
            getTfBiblioteka().setText(doc.getRoot().getOpomenaArray(0).getZaglavlje().getBiblioteka());
            getTfNaziv().setText(doc.getRoot().getOpomenaArray(0).getZaglavlje().getNaziv());
            getTfOgranak().setText(doc.getRoot().getOpomenaArray(0).getZaglavlje().getOgranak());
            getTfSifra().setText(doc.getRoot().getOpomenaArray(0).getZaglavlje().getSifra());
            getTfAdresa().setText(doc.getRoot().getOpomenaArray(0).getZaglavlje().getAdresa());
            getTfMesto().setText(doc.getRoot().getOpomenaArray(0).getZaglavlje().getMesto());
            getTfBropomenetext().setText(doc.getRoot().getOpomenaArray(0).getZaglavlje().getBropomenetext());
            getTfNaslov().setText(doc.getRoot().getOpomenaArray(0).getZaglavlje().getNaslov());
            getTfRok().setText(doc.getRoot().getOpomenaArray(0).getZaglavlje().getRoktext());
            getTaIznad().setText(doc.getRoot().getOpomenaArray(0).getBody().getTextiznad());
            getTaIspod().setText(doc.getRoot().getOpomenaArray(0).getBody().getTextispod());
            getTfNapomena().setText(doc.getRoot().getOpomenaArray(0).getBody().getNapomena());
            getTfRbrT().setText(doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().getRbr());
            getTfNaslovT().setText(doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().getNaslov());
            getTfAutorT().setText(doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().getAutor());
            getTfCtlgNoT().setText(doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().getInvbroj());
            getTfSigNoT().setText(doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().getSignatura());
            getTfBrdanaT().setText(doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().getBrdana());
            getTfNadoknada1().setText(doc.getRoot().getOpomenaArray(0).getBody().getNadoknada1());
            getTfNadoknada2().setText(doc.getRoot().getOpomenaArray(0).getBody().getNadoknada2());
            getTfCena1().setText(Double.toString(doc.getRoot().getOpomenaArray(0).getBody().getCena1()));
            getTfCena2().setText(Double.toString(doc.getRoot().getOpomenaArray(0).getBody().getCena2()));
            getTfDin().setText(doc.getRoot().getOpomenaArray(0).getBody().getDin());
            getTfDodatuma().setText(doc.getRoot().getOpomenaArray(0).getBody().getDodatuma());
            getTfPojedinacno().setText(doc.getRoot().getOpomenaArray(0).getFooter().getPojedinacno());
            getTfTrostruko().setText(doc.getRoot().getOpomenaArray(0).getFooter().getTrostrukotext());
            getTfRoditelj().setText(doc.getRoot().getOpomenaArray(0).getPodaci().getRoditelj());
            getTfUseridtext().setText(doc.getRoot().getOpomenaArray(0).getPodaci().getUseridtext());
            getTfDocno().setText(doc.getRoot().getOpomenaArray(0).getPodaci().getDocno());
            getTfDocmesto().setText(doc.getRoot().getOpomenaArray(0).getPodaci().getDocmesto());
            getTfJmbg().setText(doc.getRoot().getOpomenaArray(0).getPodaci().getJmbg());
            getTfBibliotekaD().setText(doc.getRoot().getOpomenaArray(0).getFooter().getBiblioteka());
            getTfDirektor().setText(doc.getRoot().getOpomenaArray(0).getFooter().getDirektor());
            getTfImedirektora().setText(doc.getRoot().getOpomenaArray(0).getFooter().getIme());
            if (doc.getRoot().getCirilica() == 1) {
                getRbCyrillic().setSelected(true);
            } else {
                getRbLatin().setSelected(true);
            }
        } catch (Exception e) {
            doc = RootDocument.Factory.newInstance();
            doc.addNewRoot().addNewOpomena().addNewZaglavlje();
            doc.getRoot().getOpomenaArray(0).addNewBody();
            doc.getRoot().getOpomenaArray()[0].getBody().addNewTabelazg();
            doc.getRoot().getOpomenaArray()[0].addNewPodaci();
            doc.getRoot().getOpomenaArray()[0].addNewFooter();
        }
        dirty = false;

    }

    public void save() {
        try {
            doc.getRoot().getOpomenaArray(0).getZaglavlje().setBiblioteka(getTfBiblioteka().getText());
            doc.getRoot().getOpomenaArray(0).getZaglavlje().setNaziv(getTfNaziv().getText());
            doc.getRoot().getOpomenaArray(0).getZaglavlje().setOgranak(getTfOgranak().getText());
            doc.getRoot().getOpomenaArray(0).getZaglavlje().setSifra(getTfSifra().getText());
            doc.getRoot().getOpomenaArray(0).getZaglavlje().setAdresa(getTfAdresa().getText());
            doc.getRoot().getOpomenaArray(0).getZaglavlje().setMesto(getTfMesto().getText());
            doc.getRoot().getOpomenaArray(0).getZaglavlje().setBropomenetext(getTfBropomenetext().getText());
            doc.getRoot().getOpomenaArray(0).getZaglavlje().setNaslov(getTfNaslov().getText());
            doc.getRoot().getOpomenaArray(0).getZaglavlje().setRoktext(getTfRok().getText());
            doc.getRoot().getOpomenaArray(0).getBody().setTextiznad(getTaIznad().getText());
            doc.getRoot().getOpomenaArray(0).getBody().setTextispod(getTaIspod().getText());
            doc.getRoot().getOpomenaArray(0).getBody().setNapomena(getTfNapomena().getText());
            doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().setRbr(getTfRbrT().getText());
            doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().setNaslov(getTfNaslovT().getText());
            doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().setAutor(getTfAutorT().getText());
            doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().setInvbroj(getTfCtlgNoT().getText());
            doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().setSignatura(getTfSigNoT().getText());
            doc.getRoot().getOpomenaArray(0).getBody().getTabelazg().setBrdana(getTfBrdanaT().getText());
            doc.getRoot().getOpomenaArray(0).getBody().setNadoknada1(getTfNadoknada1().getText());
            doc.getRoot().getOpomenaArray(0).getBody().setNadoknada2(getTfNadoknada2().getText());
            doc.getRoot().getOpomenaArray(0).getBody().setCena1(Double.parseDouble(getTfCena1().getText()));
            doc.getRoot().getOpomenaArray(0).getBody().setCena2(Double.parseDouble(getTfCena2().getText()));
            doc.getRoot().getOpomenaArray(0).getBody().setDin(getTfDin().getText());
            doc.getRoot().getOpomenaArray(0).getBody().setDodatuma(getTfDodatuma().getText());
            doc.getRoot().getOpomenaArray(0).getFooter().setPojedinacno(getTfPojedinacno().getText());
            doc.getRoot().getOpomenaArray(0).getFooter().setTrostrukotext(getTfTrostruko().getText());
            doc.getRoot().getOpomenaArray(0).getPodaci().setRoditelj(getTfRoditelj().getText());
            doc.getRoot().getOpomenaArray(0).getPodaci().setUseridtext(getTfUseridtext().getText());
            doc.getRoot().getOpomenaArray(0).getPodaci().setDocno(getTfDocno().getText());
            doc.getRoot().getOpomenaArray(0).getPodaci().setDocmesto(getTfDocmesto().getText());
            doc.getRoot().getOpomenaArray(0).getPodaci().setJmbg(getTfJmbg().getText());
            doc.getRoot().getOpomenaArray(0).getFooter().setBiblioteka(getTfBibliotekaD().getText());
            doc.getRoot().getOpomenaArray(0).getFooter().setDirektor(getTfDirektor().getText());
            doc.getRoot().getOpomenaArray(0).getFooter().setIme(getTfImedirektora().getText());
            if (getRbCyrillic().isSelected()) {
                doc.getRoot().setCirilica(1);
            } else {
                doc.getRoot().setCirilica(0);
            }

            StringWriter sw = new StringWriter();
            doc.save(sw);
            wtype.setTemplate(sw.toString());
            wtype.setDescription(getTfNazivSablona().getText());
            try {
                WarningType savedWarningType = manager.saveWarnTypes(wtype);
                if (savedWarningType != null) {
                    dirty = false;
                    wtype = savedWarningType;
                    warningsFrame.getCmbType().addItem(wtype);
                    JOptionPane.showMessageDialog(this, "Sa\u010duvano!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gre\u0161ka u komunikaciji sa bazom!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O\u010dekuje se broj!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDirty() {
        return dirty;
    }

    private JTextField getTfNapomena() {
        if (tfNapomena == null) {
            tfNapomena = new JTextField();
            tfNapomena.setToolTipText("napomena");
            tfNapomena.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfNapomena;
    }

    private void handleKeyTyped() {
        if (!dirty)
            dirty = true;
    }

    private JTextField getTfSigNoT() {
        if (tfSigNoT == null) {
            tfSigNoT = new JTextField();
            tfSigNoT.setToolTipText("signatura");
            tfSigNoT.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfSigNoT;
    }

}
