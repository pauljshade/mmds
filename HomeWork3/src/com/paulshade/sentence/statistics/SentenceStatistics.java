package com.paulshade.sentence.statistics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.paulshade.io.ReadFile;
import com.paulshade.sentence.* ;

public class SentenceStatistics implements SentenceProcessing {

	/* file and project details */
	private String _filepath ;
	private String _fileName ;
	
	/* statistics */
	SortedMap<String,Integer> _stats = new TreeMap<String,Integer>() ;
	
	/* length count */
	SortedMap<Integer,List<Integer>> _size = new TreeMap<Integer,List<Integer>>() ;
	
	
	/* constructors - currently only file version is implemented */
	public SentenceStatistics(String path, String projectName, String fileExtension) {
		_filepath=path ;
		_fileName=projectName+fileExtension ;
		
		initialiseStats() ;
	}
	
	private void initialiseStats() {
		_stats.put("A RecordCount",0) ;
		_stats.put("B MinWordLength",0) ;
		_stats.put("C MinWordUniqueLength",0) ;
		_stats.put("D MaxWordLength",0) ;
		_stats.put("E MaxWordUniqueLength",0) ;
	}
	
	public void process() {
		System.out.println("Generate Statistics *******") ;		
		
		ReadFile sentenceFile=new ReadFile(_filepath+_fileName+".txt") ;
		
		int stA=0 ; int stB=0 ; int stC=0 ; int stD=0 ; int stE=0 ;  
		sentenceFile.openFile() ;		
		
		String currS ;
		while((currS=sentenceFile.readLine())!=null) {
			stA++ ;
			
			String[] str=currS.split(" ") ;
			int length=str.length-1 ;
			if(stB==0||stB>length) stB=length ; /* min length */
			if(stD==0||stD<length) stD=length ; /* max length */
			Set<String> words = new HashSet<String>() ;
			for(int i=1;i<str.length;i++) words.add(str[i]) ;
			int unique=words.size() ;
			if(stC==0||stC>unique) stC=unique ; /* min length */
			if(stE==0||stE<unique) stE=unique ; /* max length */
						
			if(_size.containsKey(unique)) _size.get(unique).add(Integer.valueOf(str[0])) ;
			else {
				List<Integer> a=new ArrayList<Integer>() ;
				a.add(Integer.valueOf(str[0])) ;
				_size.put(unique, a) ;
			}
			
		}
		
		sentenceFile.closeFile() ;				

		_stats.put("A RecordCount",stA) ;
		_stats.put("B MinWordLength",stB) ;
		_stats.put("C MinWordUniqueLength",stC) ;
		_stats.put("D MaxWordLength",stD) ;
		_stats.put("E MaxWordUniqueLength",stE) ;
		
		this.print() ;
	}

	private void print() {
		for(String key : _stats.keySet()) System.out.println(key+" "+_stats.get(key)) ;
		
		System.out.println("\n\n") ;
		
		for(Integer key : _size.keySet()) System.out.println(key + "-"+_size.get(key).size()) ;
		
	}
}
