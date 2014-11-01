package com.paulshade.generatepairs.signature;

import com.paulshade.generatepairs.lshash.LSHasher;
import com.paulshade.generatepairs.minhash.MinHasher;
import com.paulshade.generatepairs.tokenizer.Tokenizer;

public interface Signature {

	public Integer getId() ;
//	public String getFirstWord() ;
//	public String getLastWord() ;
//	public int getWordLength() ;
//	public int getUniqueWordLength() ;
	
	public void tokenize(Tokenizer tz) ;
	public void minHash(MinHasher mh) ;
	public void LSHash(LSHasher lsh) ;
	public void LSHSash(LSHasher lsh,int type) ;
	
	public String LSHString(boolean shortFlag) ;
	
	public void clearAll() ;
	
	public String getSentence() ;
	
}
