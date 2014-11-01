package com.paulshade.mmds;

import com.paulshade.sentence.*;
import com.paulshade.sentence.duplicates.*;
import com.paulshade.sentence.statistics.*;
import com.paulshade.generatepairs.* ;

import java.util.ArrayList;

import paulshade.com.hashfunctions.HashFunction;
import paulshade.com.hashfunctions.HashGenerator;

public class HomeWork3 {

	public static void main(String[] args) {

		System.out.println("Homework Week 3 Programming Exercise\n") ;
		
		Parameters _p=new Parameters() ;
		
		/* Generate hash functions */	
		//HashGenerator hg = new HashGenerator() ;
		//ArrayList<HashFunction> hfs = hg.generateNewSet(_p.minHash) ;
		
		/* get the duplicates out */
		//SentenceProcessing dsf=new FileBasedDuplicateSplit(_p.path,_p.projectName) ;
		//dsf.process() ;
		
		/* get the duplicates out */
		//SentenceProcessing ss=new SentenceStatistics(_p.path,_p.projectName, "Unique") ;
		//ss.process() ;
				

		/* get pad the sentences */
		//SentenceProcessing pad=new PadSentence(_p.path,_p.projectName, "Unique", 10) ;
		//pad.process() ;

		/* Local Hash */
		//SentenceProcessing gp=new CreateSignature(_p, hfs) ;
		//gp.process() ;

		/* Local Hash */
		//SentenceProcessing gp=new CreateShortSignature(_p) ;
		//gp.process() ;

		/* Create pairs */
		//SentenceProcessing cp=new GeneratePairs(_p) ;
		//cp.process() ; 
		
		/* Confirm pairs */
		//SentenceProcessing con=new ConfirmPairs(_p) ;
		//con.process() ; 

		/* Confirm pairs */
		SentenceProcessing fc=new FinalCountUp(_p) ;
		fc.process() ; 	
		
		//test
		/*ConfirmPairs cp=new ConfirmPairs(_p) ;
		cp.loadSentencesTest(1, 10000) ;
		cp.readPairsTest();
		cp.checkPairs() ;
		cp.storePairs() ;	
*/
		/* Confirm pairs */
	//	SentenceProcessing fc=new FinalCountUp(_p) ;
	//	fc.process() ; 	
		
	}

}
