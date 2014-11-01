package com.paulshade.generatepairs;

import java.util.List;

import paulshade.com.hashfunctions.HashFunction;

import com.paulshade.generatepairs.signature.*;
import com.paulshade.generatepairs.tokenizer.* ;
import com.paulshade.generatepairs.minhash.* ;
import com.paulshade.generatepairs.lshash.* ;
import com.paulshade.io.ReadFile;
import com.paulshade.io.WriteFile;
import com.paulshade.mmds.*;
import com.paulshade.sentence.SentenceProcessing;

public class CreateSignature implements SentenceProcessing {

	private Parameters _p ;
	private List<HashFunction> _hfs ;
	public CreateSignature(Parameters parameters, List<HashFunction> hfs) {
		_p=parameters ;
		_hfs=hfs ;
	}
	
	public void process() {
		
		System.out.println("********** Create Signatures ") ;
			
		Tokenizer tz = new StringTokenizer() ;
		MinHasher mh = new IntegerMinHasher(_hfs,_p.minHash) ;
		LSHasher lsh = new IntegerLSHasher(_p.buckets, _p.rows) ;
				
		ReadFile rf=new ReadFile(_p.path+_p.projectName+"Pad.txt") ;
		WriteFile lf=new WriteFile(_p.path+_p.projectName+"LSH.txt") ;
		rf.openFile() ;			
		lf.openFile();
		
		String line ;
		int i=0 ;
		while((line=rf.readLine())!=null) { 
			i++ ;
			Signature s = new WordSignature(line) ;
			
			s.tokenize(tz) ;
			s.minHash(mh) ;
			s.LSHash(lsh) ;
						
			lf.writeLine(s.LSHString(false));
			if(i%100000==0) System.out.println("@ "+i) ;
			
			s.clearAll(); 
			s=null ;
		}
		
		System.out.println("Total LSH = "+i) ;
		
		rf.closeFile() ;
		lf.closeFile();
		
	}
}
