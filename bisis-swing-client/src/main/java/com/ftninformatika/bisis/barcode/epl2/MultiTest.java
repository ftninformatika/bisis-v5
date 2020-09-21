package com.ftninformatika.bisis.barcode.epl2;


import java.io.ByteArrayInputStream;


import javax.print.*;


public class MultiTest {

	    public static void main(String[] args) {
	        try {
             	
	           
	           
	         //   DocFlavor flavor =  DocFlavor.BYTE_ARRAY.AUTOSENSE;        
	            PrintService [] services = PrintServiceLookup.lookupPrintServices(null, null);
	            for (int i=0;i<services.length; i++){
	            System.out.println(services[i] );
	            }
	            Label label = new Label(456, 0, 203,3,2,140,"1250");
	            label.appendText("Danijela Boberic ",2);
	            label.appendBlankLine();
	            label.appendCode128("K00000001540"); 
	            byte[] by = label.getCommands().getBytes();

	           // ByteArrayInputStream is = new ByteArrayInputStream(label.getCommands().getBytes());
	           
	            DocPrintJob job = services[0].createPrintJob();

				DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
	 
				Doc doc = new SimpleDoc(by, flavor, null);
				job.print(doc, null);
	           
	            //job.print(doc, null);
	          //  PrintJobWatcher pjDone = new PrintJobWatcher(job);

	           // job.print(doc, null);



	          //  pjDone.waitForDone();
	        
	            // It is now safe to close the input stream
	           // is.close();


	  
	        } catch (Exception e) {
	            System.out.println("print exception");
	            e.printStackTrace();
	        } 

	    }

}
