package com.paulshade.generatepairs.signature;

import java.util.HashSet;
import java.util.Set;

import com.paulshade.generatepairs.lshash.LSHasher;
import com.paulshade.generatepairs.minhash.MinHasher;
import com.paulshade.generatepairs.tokenizer.Tokenizer;
import com.paulshade.matrix.IntegerVector;
import com.paulshade.matrix.Vector;

public class WordSignature implements Signature {

	private Integer _id ; 
	@Override public Integer getId() { return _id ; }

	private String _sentence ; 
	@Override 
	public String getSentence() { return _sentence ; }
	
	private int _wordLength ;	
//	@Override 
//	public int getWordLength() { return _wordLength ; }

	private int _uniqueWordLength ;
//	@Override 
//	public int getUniqueWordLength() { return _uniqueWordLength ; }

	private String _firstWord ;
//	@Override
//	public String getFirstWord() { return _firstWord ; }	
	private String _lastWord ;
//	@Override
//	public String getLastWord() { return _lastWord ; }

	private String[] _words ;
	private Vector<Integer> _tokens ;
	private Vector<Integer> _minHash ;
	private Vector<Integer> _LSHash ;
	
	public WordSignature(String sentence) {
		_id=this.extractId(sentence) ;
		_sentence=this.extractSentance(sentence) ;
		_words=this.wordSplit(_sentence) ;
		_wordLength=this.wordLength(_words) ;
		_uniqueWordLength=this.uniqueWordLength(_words) ;
		_firstWord=_words[0]+_words[1]+_words[2]+_words[3]+_words[4] ;
		_lastWord=_words[_words.length-1]+_words[_words.length-2]+_words[_words.length-3]+_words[_words.length-4]+_words[_words.length-5] ;
 	}

	/* clear the space */
	public void clearAll() {
		_sentence=null ;
		_words=null ;
		if(_tokens!=null) _tokens.clear(); _tokens=null ;
		if(_minHash!=null) _minHash.clear(); _minHash=null ;
		if(_LSHash!=null) _LSHash.clear(); _LSHash=null ;
	}
	
	
	/* sentence processes */
	private String extractSentance(String line) {
		String[] tokens = line.split(" ",2) ;
		return tokens[1] ;
	}
	
	private Integer extractId(String line) {
		String[] tokens = line.split(" ",2) ;
		return Integer.valueOf(tokens[0]) ;
	}

	private String[] wordSplit(String line) {
		String[] tokens = line.split(" ") ;
		return tokens ;
	}
	
	private int wordLength(String[] words) {
		int w=0 ;
		for(int i=0;i<words.length;i++) 
			if(!defaultStr(words[i])) w++ ;

		return w ;
	}

	private int uniqueWordLength(String[] words) {
		Set<String> wds=new HashSet<String>() ;
		for(int i=0;i<words.length;i++) 
			if(!defaultStr(words[i])) wds.add(words[i]) ;

		return wds.size() ;
	}

	private boolean defaultStr(String word) {
		if(word.startsWith("__^^")&&word.endsWith("^^__")) return true ;
		
		return false ;
	}
	/* Tokenizer processes */
	@Override	
	public void tokenize(Tokenizer tz) { 
		_tokens=tz.tokenise(_words) ; 
	}
	
	/* Minhasher */
	@Override
	public void minHash(MinHasher mh) { 
		_minHash=mh.minHash(_tokens) ;
	}

	/* LSH */
	@Override
	public void LSHash(LSHasher lsh) { 
		_LSHash=lsh.LSHash(_minHash) ;
	}
	
	/* LSH */
	@Override
	public void LSHSash(LSHasher lsh, int type) { 
		Vector<Integer> v = new IntegerVector(4) ;
		int add1=0 ; int add2=0 ;
		if(type==1||type==3) add1=1 ; 
		if(type==2||type==3) add2=1 ;
		v.set(0, _wordLength+add1) ; v.set(1, _uniqueWordLength+add2) ;
		v.set(2, _firstWord.hashCode()) ; v.set(3, _lastWord.hashCode()) ;

		//if(_id==150||_id==207) System.out.println("id="+_id+" "+_firstWord+" "+_lastWord+" "+_wordLength+" "+_uniqueWordLength+" "+v.get(0)+" "+v.get(1)+" "+v.get(2)+" "+v.get(3)) ;
		_LSHash=lsh.LSHash(v) ;
	}	

	/* LSH string */
	@Override
	public String LSHString(boolean shortFlag) {
		return _id+" "+_wordLength+" "+_uniqueWordLength+" "+_LSHash.print() ;
	}

}
