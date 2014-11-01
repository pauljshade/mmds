package com.paulshade.sentence.duplicates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.paulshade.io.ReadFile;
import com.paulshade.io.WriteFile;
import com.paulshade.sentence.* ;

public class FileBasedDuplicateSplit implements SentenceProcessing {

	/* file and project details */
	private String _filepath ;
	private String _projectName ;
	
	/* intermediate lists */
	private Map<Integer, List<Integer>> _sentenceHash = new HashMap<Integer,List<Integer>>() ;
	private Map<String,List<Integer>> _sentenceList = new HashMap<String,List<Integer>>() ;
	
	/* final unique sets */
	private Set<Integer> _uniqueSet = new  HashSet<Integer>() ;
	private SortedMap<Integer, List<Integer>> _duplicateSet = new TreeMap<Integer,List<Integer>>() ;
		
	/* constructors - currently only file version is implemented */
	public FileBasedDuplicateSplit(String path, String projectName) {
		_filepath=path ;
		_projectName=projectName ;
	}

	public void storeDuplicates() {
		
		System.out.println("Store duplicates *******") ;
		
		WriteFile wf = new WriteFile(_filepath+_projectName+"Dups.txt") ;
		wf.openFile() ;
		int k=0 ;
		for (Integer key : _duplicateSet.keySet()) { 
			List<Integer> l=_duplicateSet.get(key) ;
			String dup=l.get(0).toString() ;
			k+=l.size()-1 ;
			for(int i=1;i<l.size();i++) dup+=" "+l.get(i).toString() ; 
			wf.writeLine(dup+"\n") ;
		}	
		
		System.out.println("Final Duplicate ids Written "+ k) ;		
		
		wf.closeFile() ;
	}
	
	public void storeUnique() {
		System.out.println("Store unique *******") ;
				
		ReadFile sentenceFile=new ReadFile(_filepath+_projectName+".txt") ;
		WriteFile wf = new WriteFile(_filepath+_projectName+"Unique.txt") ;
		sentenceFile.openFile() ;
		wf.openFile() ;
		
		int i=0 ; 
		String currS ;
		while((currS=getNextSentence(sentenceFile))!=null) {
		
			/* split the line */
			String[] split = currS.split(" ",2) ;
		
			Integer id=Integer.valueOf(split[0]) ;
			
			if(this._uniqueSet.contains(id)) {
				wf.writeLine(currS+"\n") ;
				i++ ;
			}
			
		}
		
		System.out.println("Final Unique Sentences Written "+ i) ;		
		
		sentenceFile.closeFile() ;
		wf.closeFile() ;

	}

		
	private String getNextSentence(ReadFile sentenceFile) {		
		return sentenceFile.readLine() ;
	}
	
	@Override
	public void process() {

		System.out.println("****** Duplicate Finder *******") ;
		
		/* early hash processing to id potential duplicates */
		System.out.println("Read Sentences *******") ;
		readSentences() ;
		System.out.println("Remove singles *******") ;
		removeSingles() ;
		
		/* check the duplicates exactly */
		System.out.println("Duplicate test *******") ;
		testDuplicates() ;	
		/* clean up */ _sentenceHash.clear() ; _sentenceHash=null ;

		System.out.println("Remove singles *******") ;
		removeExactSingles() ;
		/* clean up */ _sentenceList.clear() ; _sentenceList=null ;

		/* store the duplicates */
		storeDuplicates() ;
		storeUnique() ;
		
	}
	
	private void readSentences() {
		ReadFile sentenceFile=new ReadFile(_filepath+_projectName+".txt") ;
		sentenceFile.openFile() ;
		
		int i=0 ; int k=0 ;
		String currS ;
		while((currS=getNextSentence(sentenceFile))!=null) {
		
			i++ ;
		
			/* split the line */
			String[] split = currS.split(" ",2) ;
		
			Integer id=Integer.valueOf(split[0]) ;
			Integer hash=getHash(split[1]) ;
		
			/* store the hash */
			if(storeHash(hash,id)) k++ ;
			
		}
		
		System.out.println("Total Sentences Read : "+ i) ;
		System.out.println("Total Unique Keys Read "+k) ;		
		System.out.println("Total Hash Buckets created "+ _sentenceHash.size()) ;		
		
		sentenceFile.closeFile() ;
		
	}
	
	private void removeSingles() {
		
		Iterator<Integer> keys=_sentenceHash.keySet().iterator() ;
		
		while(keys.hasNext()) {
			Integer key=keys.next() ;
			if(_sentenceHash.get(key).size()==1) {
				_uniqueSet.add(_sentenceHash.get(key).get(0)) ;
				keys.remove() ;
			}
		}
		
		System.out.println("Non-single Hash Buckets "+ _sentenceHash.size()) ;		
		System.out.println("First Pass Singles "+_uniqueSet.size()) ;		
		
	}
	
	private void testDuplicates() {
		ReadFile sentenceFile=new ReadFile(_filepath+_projectName+".txt") ;
		sentenceFile.openFile() ;
					
		String currS ;
		while((currS=getNextSentence(sentenceFile))!=null) {
		
			/* split the line */
			String[] split = currS.split(" ",2) ;

			Integer id=Integer.valueOf(split[0]) ;
			String sent=split[1] ;
			Integer hash=getHash(sent) ;
		
			/* check if part of the dups list */
			if(_sentenceHash.containsKey(hash)) {
				/* check if already mapped */
				if(_sentenceList.containsKey(sent)) _sentenceList.get(sent).add(id) ;
				else {
					_sentenceList.put(sent, new ArrayList<Integer>()) ;
					_sentenceList.get(sent).add(id) ;
				}
			}
			
		}
		
		sentenceFile.closeFile() ;
	}

	private void removeExactSingles() {
		
		Iterator<String> keys=_sentenceList.keySet().iterator() ;
		
		while(keys.hasNext()) {
			String key=keys.next() ;
			_uniqueSet.add(_sentenceList.get(key).get(0)) ;
			if(_sentenceList.get(key).size()==1) keys.remove() ;
			else _duplicateSet.put(_sentenceList.get(key).get(0), _sentenceList.get(key)) ;
		}
		
		System.out.println("Final Unique Keys "+ _uniqueSet.size()) ;		
		System.out.println("Final Duplicate Sets "+ _duplicateSet.size()) ;		

	}

	private int getHash(String str) { return str.hashCode() ; }
	
	private boolean storeHash(Integer key, Integer value) {
		if(!_sentenceHash.containsKey(key)) _sentenceHash.put(key, new ArrayList<Integer>()) ;		
		if(!_sentenceHash.get(key).contains(value)) {
			_sentenceHash.get(key).add(value) ;
			return true ;
		}
		
		return false ;
	}

}
