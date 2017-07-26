package com.ftninformatika.bisis.libenv;


import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.librarian.Librarian;

public class LibEnvironment {
	
	private static LibrarianFrame librariansFrame = new LibrarianFrame();
	private static ProcessTypeFrame processtypeFrame = new ProcessTypeFrame();
	
	
	public static void showLibrariansFrame(){
		try {
      if (!librariansFrame.isVisible())
      	librariansFrame.setVisible(true);
      if (librariansFrame.isIcon())
      	librariansFrame.setIcon(false);
      if (!librariansFrame.isSelected())
      	librariansFrame.setSelected(true);
      librariansFrame.initializeForm();
    } catch (Exception ex) {
    }		
	}
	
	public static void showProcessTypesFrame(){
		try {
      if (!processtypeFrame.isVisible())
      	processtypeFrame.setVisible(true);
      if (processtypeFrame.isIcon())
      	processtypeFrame.setIcon(false);
      if (!processtypeFrame.isSelected())
      	processtypeFrame.setSelected(true);
      processtypeFrame.initializeForm();
    } catch (Exception ex) {
    }		
	}
	
	public static void updateLibrarian(Librarian lib){
		LibEnvProxy.updateLibrarian(lib);		
	}
	
	static{
		BisisApp.getMainFrame().insertFrame(librariansFrame);
		BisisApp.getMainFrame().insertFrame(processtypeFrame);
		
	}

}
