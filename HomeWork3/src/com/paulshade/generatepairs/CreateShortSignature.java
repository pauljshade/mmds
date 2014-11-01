package com.paulshade.generatepairs;

import com.paulshade.generatepairs.signature.*;
import com.paulshade.generatepairs.lshash.* ;
import com.paulshade.io.ReadFile;
import com.paulshade.io.WriteFile;
import com.paulshade.mmds.*;
import com.paulshade.sentence.SentenceProcessing;

public class CreateShortSignature implements SentenceProcessing {

	private Parameters _p ;
	public CreateShortSignature(Parameters parameters) {
		_p=parameters ;
	}
	
	public void process() {
		
		System.out.println("********** Create Signatures ") ;
			
		LSHasher lsh = new IntegerShortLSHasher(_p.buckets) ;
				
		ReadFile rf=new ReadFile(_p.path+_p.projectName+"Unique.txt") ;
		WriteFile lf=new WriteFile(_p.path+_p.projectName+"LSH.txt") ;
		rf.openFile() ;			
		lf.openFile();
		
		String line ;
		int i=0 ;
		while((line=rf.readLine())!=null) { 
			i++ ;
			Signature s = new WordSignature(line) ;
			
			s.LSHSash(lsh,0) ;						
			lf.writeLine(s.LSHString(true));
			s.LSHSash(lsh,1) ;						
			lf.writeLine(s.LSHString(true));
			s.LSHSash(lsh,2) ;						
			//lf.writeLine(s.LSHString(true));
			s.LSHSash(lsh,3) ;						
			//lf.writeLine(s.LSHString(true));
			
			if(i%1000000==0) System.out.println("@ "+i) ;
			
			s.clearAll(); 
			s=null ;
		}
		
		System.out.println("Total LSH = "+i) ;
		
		rf.closeFile() ;
		lf.closeFile();
		
	}
}

