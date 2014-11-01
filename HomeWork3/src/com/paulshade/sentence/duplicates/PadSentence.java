package com.paulshade.sentence.duplicates;

import java.util.HashSet;
import java.util.Set;

import com.paulshade.io.ReadFile;
import com.paulshade.io.WriteFile;
import com.paulshade.sentence.* ;

public class PadSentence implements SentenceProcessing{


	/* file and project details */
	private String _filepath ;
	private String _projectName ;
	private String _inputFile ;
	private int _padTo ;

	/* constructors - currently only file version is implemented */
	public PadSentence(String path, String projectName, String inputFile, int padTo) {
		_filepath=path ;
		_projectName=projectName ;
		_inputFile=inputFile ;
		_padTo=padTo ;
	}		
	
	
	@Override
	public void process() {
		System.out.println("Pad to "+_padTo+" *******") ;
				
		ReadFile sentenceFile=new ReadFile(_filepath+_projectName+_inputFile+".txt") ;
		WriteFile wf = new WriteFile(_filepath+_projectName+"Pad.txt") ;
		sentenceFile.openFile() ;
		wf.openFile() ;
		
		int i=0 ; 
		String currS ;
		while((currS=sentenceFile.readLine())!=null) {
			i++ ;
			
			/* split the line */
			String[] split = currS.split(" ") ;
			
			Set<String> str=new HashSet<String>() ;
			for(int k=1;k<split.length;k++) str.add(split[k]) ;
			
			int padding=_padTo-str.size() ;
			
			String pad = "" ;
			if(padding>0) for(int l=0;l<padding;l++) pad+=" __^^"+Integer.valueOf(l).toString()+"^^__" ;
			
			wf.writeLine((currS.trim()+pad).trim()+"\n") ;
			
		}
		
		System.out.println("Final Padded Sentences Written "+ i) ;		
		
		sentenceFile.closeFile() ;
		wf.closeFile() ;

	}
	
	
}
