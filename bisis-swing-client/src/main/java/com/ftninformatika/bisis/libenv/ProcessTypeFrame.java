package com.ftninformatika.bisis.libenv;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.TreePath;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.formattree.FormatUtils;
import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UFormat;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.bisis.librarian.ProcessTypeBuilder;

import net.miginfocom.swing.MigLayout;


/**
 * @author dimicb 
 */
public class ProcessTypeFrame extends JInternalFrame {	
	
	public ProcessTypeFrame(){
		super(Messages.getString("ProcessType.PROCESSING_TYPES"), false, true, true, true); //$NON-NLS-1$
		this.setSize(780,550);    
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		createProcessTypeTable();
		createPubTypeTree();
		createSubfieldsTable();
		cretePubTypeCmbBox();
		layoutPanels();
		ponistiButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				initializeForm();				
			}			
		});
		sacuvajButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleSaveProcessType();				
			}			
		});		
		pubTypeCmbBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				handleUpdatePubTypeTree();
			}			
		});
		strelicaButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleAddSubfields();
			}			
		});
		processTypeTable.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				handleKeys(processTypeTable,ke);
			}			
		});
		subfieldsTable.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				handleKeys(subfieldsTable,ke);
			}			
		});	
		tabbedPane.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {	
				handleLoadTabs();					
			}			
		});
		
	}

	public void initializeForm() {
		ProcessType pt = new ProcessType();
		loadProcessType(pt);
		processTypeTable.clearSelection();
		processTypeTableModel.initializeModel();
		sfTableModel.clearList();
		sourceTxtArea.setText(""); //$NON-NLS-1$
	}
	
	private void createProcessTypeTable(){
		processTypeTableModel = new ProcessTypeTableModel();
		processTypeTable = new JTable(processTypeTableModel);
		tableScrollPane = new JScrollPane(processTypeTable);
		processTypeTable.setRowSorter(new TableRowSorter<ProcessTypeTableModel>(processTypeTableModel));		
		listSelModel = processTypeTable.getSelectionModel();
		listSelModel.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
					handleListSelectionChanged();			
			}			
		});
	}	
	
	private void createPubTypeTree(){
		treeModel = new PubTypeTreeModel(1);
		pubTypeTree = new JTree(treeModel);
		pubTypeTree.putClientProperty("JTree.lineStyle", "None"); //$NON-NLS-1$ //$NON-NLS-2$
		pubTypeTree.setRootVisible(false);
		((BasicTreeUI)pubTypeTree.getUI()).setCollapsedIcon(null);
		((BasicTreeUI)pubTypeTree.getUI()).setExpandedIcon(null);
		treeCellRenderer = new PubTypeTreeCellRenderer();
		pubTypeTree.setCellRenderer(treeCellRenderer);
		treeScrollPane = new JScrollPane(pubTypeTree);		
	}
	
	private void createSubfieldsTable() {
		sfTableModel = new ProcessTypeSubfieldsTableModel();
		subfieldsTable = new JTable(sfTableModel);
		subfieldsTable.setAutoCreateRowSorter(true);
		sfTableScrollPane = new JScrollPane(subfieldsTable);		
		SfTableCellRenderer renderer = new SfTableCellRenderer();		
		USubfield usf = new USubfield();
		subfieldsTable.setDefaultRenderer(usf.getClass(), renderer);
		TableColumn  column = subfieldsTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(400);		
		
	}
	
	private void cretePubTypeCmbBox(){
		PubTypeCmbBoxCellRenderer renderer = new PubTypeCmbBoxCellRenderer();
		pubTypeCmbBox.setRenderer(renderer);
		for(int i = 0; i<= PubTypes.getPubTypeCount(); i++){
			UFormat uf = PubTypes.getPubType(i);
			if(uf!=null){
				pubTypeCmbBox.addItem(uf);
			}
		}
	}
	
	private void handleListSelectionChanged() {
		if(getSelectedProcessType()!=null){
			selectedProcessType = getSelectedProcessType();
			loadProcessType(selectedProcessType);
			sourceTxtArea.setText(selectedProcessType.toXML());
		}
	}
	
	private void loadProcessType(ProcessType pt){		
		sfTableModel.initializeModel();
		for(int i=0;i<pt.getInitialSubfields().size();i++)
			sfTableModel.addSubfield(pt.getInitialSubfields().get(i));
		for(int i=0;i<pt.getMandatorySubfields().size();i++)
			sfTableModel.addMandatorySubfield(pt.getMandatorySubfields().get(i));			
		if(pt.getPubType()!=null){
			UFormat uf = pt.getPubType();
			pubTypeCmbBox.setSelectedItem(uf);
			treeModel.setPubTypeId(uf.getPubType());
		}else if(pubTypeCmbBox.getItemCount()>0)
			pubTypeCmbBox.setSelectedIndex(0);
		if(pt.getName()!=null) processTypeNameTxtFld.setText(pt.getName());
		else processTypeNameTxtFld.setText("");		 //$NON-NLS-1$
	}
	
	private ProcessType getProcessTypeFromForm(){
		selectedProcessType = new ProcessType();
		if(!sourceTxtArea.getText().equals("")){ //$NON-NLS-1$
			ProcessType pt = ProcessTypeBuilder.getProcessType(sourceTxtArea.getText());
			if(pt!=null)
				selectedProcessType = pt; 
		}
		if(selectedProcessType!=null){
			selectedProcessType.setInitialSubfields(sfTableModel.getInitialSubfields());
			selectedProcessType.setMandatorySubfields(sfTableModel.getMandatorySubfields());
			selectedProcessType.setName(processTypeNameTxtFld.getText());
			selectedProcessType.setPubType((UFormat)pubTypeCmbBox.getSelectedItem());
		}
		return selectedProcessType;		
	}	
	
	private void handleSaveProcessType() {		
		boolean saved = processTypeTableModel.updateProcessType(getProcessTypeFromForm());
		if(!saved)
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Greska u komunikciji sa bazom podataka!","Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
	}
	
	private void handleUpdatePubTypeTree() {
		if(validatePubTypeChange().equals("")) //$NON-NLS-1$
			treeModel.setPubTypeId
				(((UFormat)pubTypeCmbBox.getSelectedItem()).getPubType());
		else{
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),validatePubTypeChange(),"Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			pubTypeCmbBox.setSelectedItem(PubTypes.getPubType(treeModel.getPubTypeId()));
		}
			
	}
	
	private void handleAddSubfields() {		
		TreePath[] selectedNodes = pubTypeTree.getSelectionPaths();
		if(selectedNodes!=null){
		for(int i=0;i<selectedNodes.length;i++){
			if(selectedNodes[i].getLastPathComponent() instanceof UField)
				sfTableModel.addField((UField)selectedNodes[i].getLastPathComponent());
			else if(selectedNodes[i].getLastPathComponent() instanceof USubfield)
				sfTableModel.addSubfield((USubfield)selectedNodes[i].getLastPathComponent());
		}			
		}		
	}
	
	private ProcessType getSelectedProcessType(){
		if(processTypeTable.getSelectedRow()>-1 &&
				processTypeTable.getSelectedRow()<processTypeTableModel.getRowCount())
			return processTypeTableModel.getRow(
					processTypeTable.convertRowIndexToModel(processTypeTable.getSelectedRow()));
		return null;			
	}
	
	private void handleKeys(Component comp, KeyEvent ke) {
		if(comp.equals(processTypeTable) && ke.getKeyCode()==KeyEvent.VK_DELETE)
			handleDeleteProcesstype();
		if(comp.equals(subfieldsTable) && ke.getKeyCode()==KeyEvent.VK_DELETE)
			handleDeleteSubfield();
		if(comp.equals(subfieldsTable) && ke.getKeyCode()==KeyEvent.VK_UP
				&& ke.getModifiers()== InputEvent.ALT_MASK){
			int index = subfieldsTable.getSelectedRow();
			if(index>0){
				sfTableModel.replaceWithPrevious(sfTableModel.getRow(index));
				sfTableModel.fireTableDataChanged();
				subfieldsTable.setRowSelectionInterval(index-1, index-1);
			}
		}
		if(comp.equals(subfieldsTable) && ke.getKeyCode()==KeyEvent.VK_DOWN
				&& ke.getModifiers()== InputEvent.ALT_MASK){
			int index = subfieldsTable.getSelectedRow();
			if(index<subfieldsTable.getRowCount()-1){
					sfTableModel.replaceWithNext(sfTableModel.getRow(index));
					sfTableModel.fireTableDataChanged();
					subfieldsTable.setRowSelectionInterval(index+1, index+1);
			}
		}
			
	}

	private void handleDeleteProcesstype(){	
			if(isConnectionOK()){			
				if(getSelectedProcessType()!=null){
				if(isProcessTypeFree(getSelectedProcessType())){			
					String message = "Bri\u0161emo tip obrade\n" + //$NON-NLS-1$
								getSelectedProcessType().getName();
						Object[] options = {"Da", "Odustani"}; //$NON-NLS-1$ //$NON-NLS-2$
						int ret = JOptionPane.showOptionDialog(BisisApp.getMainFrame(),
								message, "Brisanjetipa obrade", JOptionPane.DEFAULT_OPTION,  //$NON-NLS-1$
								JOptionPane.YES_NO_OPTION, null, options, options[1]);
						if(ret==0){
							processTypeTableModel.deleteProcessType(getSelectedProcessType());
							initializeForm();
						}
			}else{
				String message = "Tip obrade je vezan za bibliotekara\n i ne mo\u017ee biti obrisan."; //$NON-NLS-1$
				JOptionPane.showMessageDialog(BisisApp.getMainFrame(),message,"Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			}
				}
		}else{
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Greska u komunikciji sa bazom podataka!","Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}
			
		
	}
	
	private void handleDeleteSubfield() {
		if(getSelectedSubfield()!=null)		
			sfTableModel.deleteSubfield(getSelectedSubfield());
	}
	
	/*
	 * proverava da li postoji bibliotekar koji
	 * sadrzi ovaj tip obrade 
	 */
	private boolean isProcessTypeFree(ProcessType pt){
		List<Librarian> librarians = LibEnvProxy.getAllLibrarians();
		if(librarians!=null){
			for(Librarian lib:librarians){
				if(lib.getContext().getProcessTypes()!=null && lib.getContext().getProcessTypes().size()>0){					
							for(ProcessType ptLib:lib.getContext().getProcessTypes())
							if(ptLib!=null && pt.getName()!=null && ptLib.getName().equals(pt.getName()))
								return false;						
				}
			}
			return true;	
		}else{
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Greska u komunikciji sa bazom podataka!","Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			return true;
		}		
	}
	
	private boolean isConnectionOK(){
		List<Librarian> librarians = LibEnvProxy.getAllLibrarians();
		if(librarians==null)
			return false;
		else return true;
		
	}
	

	
	private USubfield getSelectedSubfield(){
		if(subfieldsTable.getSelectedRow()>-1 &&
				subfieldsTable.getSelectedColumn()<sfTableModel.getRowCount())
			return sfTableModel.getRow(subfieldsTable.convertRowIndexToModel
					(subfieldsTable.getSelectedRow()));		
		return null;
	}
	
	
	private String validatePubTypeChange(){
		String messagePref = "Slede\u0107a potpolja ne pripadaju izabranom tipu publikacije:\n"; //$NON-NLS-1$
		StringBuffer buff = new StringBuffer();
		for(USubfield usf:sfTableModel.getInitialSubfields())
			if(FormatUtils.pubTypeContainsSubfield
				(((UFormat)pubTypeCmbBox.getSelectedItem()).getPubType(),usf))
				buff.append(FormatUtils.returnSubfieldSpec(usf)+"\n"); //$NON-NLS-1$
		if(buff.toString().equals("")) //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		else{
			return messagePref+buff.toString();
		}
	}
	
	
	/*
	 * proverava da li sva izabrana potpolja pripadaju 
	 * izabranom tipu publikacije
	 */
	private String validateFormData(){
		
		StringBuffer message = new StringBuffer();		
		return message.toString();
	}
	
	private void handleLoadTabs(){		
		if(tabbedPane.getSelectedIndex()==0 && !sourceTxtArea.getText().equals("")){			 //$NON-NLS-1$
			ProcessType pt = ProcessTypeBuilder
				.getProcessType(sourceTxtArea.getText());
			if(pt!=null){
				selectedProcessType = pt;
				loadProcessType(selectedProcessType);				
			}else{			
				JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
						"Neispravna sintaksa XML-a!", //$NON-NLS-1$
						"Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
				selectedProcessType = getProcessTypeFromForm();
				tabbedPane.setSelectedIndex(1);
			}
		}else{
			sourceTxtArea.setText(getProcessTypeFromForm().toXML());
			tabbedPane.setSelectedIndex(1);
		}
	}
	

	private void layoutPanels() {		
		buttonsPanel.setLayout(new MigLayout("","[right]5[right]","")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sacuvajButton.setIcon(new ImageIcon(getClass().getResource(
    "/icons/ok.gif"))); //$NON-NLS-1$
		ponistiButton.setIcon(new ImageIcon(getClass().getResource(
    "/icons/remove.gif")));		 //$NON-NLS-1$
		buttonsPanel.add(sacuvajButton,"gapleft 500"); //$NON-NLS-1$
		buttonsPanel.add(ponistiButton,"grow"); //$NON-NLS-1$
		processTypePanel.setLayout(new GridBagLayout());		
		strelicaButton.setIcon(new ImageIcon(getClass().getResource(
    "/icons/4arrow4.gif"))); //$NON-NLS-1$
		JPanel pubTypePanel = new JPanel();		
	
		pubTypePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;		
		//c.weighty = 0.05;			
		c.insets = new Insets(5,5,5,5);
		c.anchor = GridBagConstraints.LINE_START;
		pubTypePanel.add(new JLabel(com.ftninformatika.bisis.libenv.Messages.getString("ProcessType.PUBLICATION_TYPE")),c); //$NON-NLS-1$
		c.gridy = 1;
		c.insets = new Insets(0,5,5,5);
		pubTypePanel.add(pubTypeCmbBox,c);
		c.gridy = 2;
		c.weighty = 0.9;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		pubTypePanel.add(treeScrollPane,c);	
		
				
		JPanel ptPanel = new JPanel();
		ptPanel.setLayout(new MigLayout("","[]","[]5[]10[]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		ptPanel.add(new JLabel(com.ftninformatika.bisis.libenv.Messages.getString("ProcessType.NAME_PROCESS_TYPE")),"wrap"); //$NON-NLS-1$ //$NON-NLS-2$
		ptPanel.add(processTypeNameTxtFld,"wrap, grow"); //$NON-NLS-1$
		ptPanel.add(sfTableScrollPane,"grow"); //$NON-NLS-1$
		tabbedPane.addTab("table", ptPanel); //$NON-NLS-1$
		
		JPanel sourcePanel = new JPanel();
		JScrollPane sourceScrollPane = new JScrollPane(sourceTxtArea);
		sourcePanel.add(sourceScrollPane);
		tabbedPane.addTab("source", sourceScrollPane); //$NON-NLS-1$
		
		
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.4;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		processTypePanel.add(pubTypePanel,c);
		c.gridx = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0;
		c.weighty = 0;		
		processTypePanel.add(strelicaButton,c);
		c.gridx = 2;
		c.weightx = 0.5;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		processTypePanel.add(tabbedPane,c);		
		
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.3;
		c.fill = GridBagConstraints.BOTH;
		add(tableScrollPane,c);		
		c.gridy = 1;
		c.weighty = 0.7;
		add(processTypePanel,c);	
		c.gridy = 2;	
  	c.weighty = 0.05;
		add(buttonsPanel,c);		
	}
	
	private ProcessType selectedProcessType = null;
	
	private JTable processTypeTable;
	private ProcessTypeTableModel processTypeTableModel;
	private JScrollPane tableScrollPane;
	private ListSelectionModel listSelModel;	
	
	private JTree pubTypeTree;
	private PubTypeTreeModel treeModel;
	private JScrollPane treeScrollPane;
	private PubTypeTreeCellRenderer treeCellRenderer;
	
	
	private JTable subfieldsTable;
	private ProcessTypeSubfieldsTableModel sfTableModel;
	private JScrollPane sfTableScrollPane; 
	
	private JPanel processTypePanel = new JPanel();
	private JComboBox pubTypeCmbBox = new JComboBox();
	private JTextField processTypeNameTxtFld = new JTextField(20);
	private JButton strelicaButton = new JButton();
	private JTextArea sourceTxtArea = new JTextArea(300,300);
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	private JPanel buttonsPanel = new JPanel();
	private JButton sacuvajButton = new JButton(com.ftninformatika.bisis.libenv.Messages.getString("ProcessType.SAVE")); //$NON-NLS-1$
	private JButton ponistiButton = new JButton(com.ftninformatika.bisis.libenv.Messages.getString("ProcessType.NEW")); //$NON-NLS-1$
	
	
	public class PubTypeCmbBoxCellRenderer extends JLabel 
		implements ListCellRenderer {
		
		public PubTypeCmbBoxCellRenderer(){
			super();
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList list, Object value, 
				int index, boolean isSelected, boolean cellHasFocus) {
			if(value instanceof UFormat){
				UFormat uf = (UFormat)value;
				setText(uf.getPubType()+"-"+uf.getName());					 //$NON-NLS-1$
			}
			if (isSelected) {
	      setForeground(list.getSelectionForeground());
	      setBackground(list.getSelectionBackground());
	    } else {
	      setForeground(list.getForeground());
	      setBackground(list.getBackground());
	    }  
			return this;			
		}
		
	}
	
	
}
