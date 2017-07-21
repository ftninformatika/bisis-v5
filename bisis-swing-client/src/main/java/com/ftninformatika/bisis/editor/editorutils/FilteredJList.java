package com.ftninformatika.bisis.editor.editorutils;

	import java.awt.BorderLayout;
import java.awt.Dimension;
	import java.util.ArrayList;

	import javax.swing.AbstractListModel;
import javax.swing.JComponent;
	import javax.swing.JFrame;
	import javax.swing.JList;
	import javax.swing.JScrollPane;
	import javax.swing.JTextField;
	import javax.swing.ListModel;
	import javax.swing.ScrollPaneConstants;
	import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


	public class FilteredJList extends JComponent{
		
		private JList<String> filteredList;
		private FilterField filterField;
		private int DEFAULT_FIELD_WIDTH = 20;

		public FilteredJList() {
			super();
			filteredList = new JList<String>();
			setModel(new FilterModel());
			filterField = new FilterField(DEFAULT_FIELD_WIDTH);
			setLayout();
		}

		private void setLayout() {			
			this.setPreferredSize(new Dimension(300, 200));
			setLayout(new BorderLayout());
			JScrollPane pane = new JScrollPane(filteredList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);		
			add(pane, BorderLayout.CENTER);
			add(filterField, BorderLayout.NORTH);			
			
		}

		public void setModel(ListModel m) {
			if (!(m instanceof FilterModel))
				throw new IllegalArgumentException();
			filteredList.setModel(m);
		}

		public void addItem(Object o) {
			((FilterModel) filteredList.getModel()).addElement(o);
		}

		public JTextField getFilterField() {
			return filterField;
		}
		
		public JList getJList(){
			return filteredList;
		}

		// test filter list
		public static void main(String[] args) {
			String[] listItems = { "Chris", "Joshua", "Daniel", "Michael", "Don", "Kimi", "Kelly", "Keagan" };
			JFrame frame = new JFrame("FilteredJList");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(new BorderLayout());
			// populate list
			FilteredJList list = new FilteredJList();
			for (int i = 0; i < listItems.length; i++)
				list.addItem(listItems[i]);	
			frame.getContentPane().add(list);
			frame.pack();
			frame.setVisible(true);
		}

		// inner class to provide filtered model
		class FilterModel extends AbstractListModel {
			ArrayList items;
			ArrayList filterItems;

			public FilterModel() {
				super();
				items = new ArrayList();
				filterItems = new ArrayList();
			}

			public Object getElementAt(int index) {
				if (index < filterItems.size())
					return filterItems.get(index);
				else
					return null;
			}

			public int getSize() {
				return filterItems.size();
			}

			public void addElement(Object o) {
				items.add(o);
				refilter();
			}

			private void refilter() {
				filterItems.clear();
				String term = getFilterField().getText().toLowerCase();
				for (int i = 0; i < items.size(); i++)
					if (items.get(i).toString().toLowerCase().indexOf(term, 0) != -1)
						filterItems.add(items.get(i));
				fireContentsChanged(this, 0, getSize());
			}
		}

		// inner class provides filter-by-keystroke field
		class FilterField extends JTextField implements DocumentListener {
			public FilterField(int width) {
				super(width);
				getDocument().addDocumentListener(this);
			}

			public void changedUpdate(DocumentEvent e) {
				((FilterModel) filteredList.getModel()).refilter();
			}

			public void insertUpdate(DocumentEvent e) {
				((FilterModel) filteredList.getModel()).refilter();
			}

			public void removeUpdate(DocumentEvent e) {
				((FilterModel) filteredList.getModel()).refilter();
			}
		}
	}



