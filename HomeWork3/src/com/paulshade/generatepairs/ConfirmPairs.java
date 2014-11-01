package com.paulshade.generatepairs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.paulshade.generatepairs.signature.Signature;
import com.paulshade.generatepairs.signature.WordSignature;
import com.paulshade.io.ReadFile;
import com.paulshade.io.WriteFile;
import com.paulshade.mmds.Parameters;
import com.paulshade.sentence.SentenceProcessing;

public class ConfirmPairs implements SentenceProcessing {

	private Parameters _p ;
	public Map<Integer,String> _hm=new HashMap<Integer,String>() ; 
	public SortedMap<Integer,List<Integer>> _pa=new TreeMap<Integer,List<Integer>>() ; 
	
	public ConfirmPairs(Parameters p) {
		_p=p ;
	}
	
	@Override
	public void process() {
		readPairs() ;
		loadSentences() ;
		checkPairs() ;
		storePairs() ;
	}
	
	private int sumDist(String first, String second) {

		String[] s1=first.split(" ") ;
		String[] s2=second.split(" ") ;

		if(s1.length!=s2.length) return 20 ;
		int dist=0 ;
		for(int i=0;i<s1.length;i++) 
			if(s1[i].compareTo(s2[i])!=0) dist++ ;
		
		return dist ;
	}
	
	private int longestSubstr(String first, String second) {

		String[] s1=first.split(" ") ;
		String[] s2=second.split(" ") ;

		int m=s1.length ;
		int n=s2.length ;
		
		int[][] c = new int[m+1][n+1] ;
		for(int i=0;i<m+1;i++) for(int j=0;j<n+1;j++) c[i][j]=0 ;

		for(int i=0;i<m+1;i++) c[i][0]=0 ;
		for(int j=0;j<n+1;j++) c[0][j]=0 ;

		for(int i=1;i<m+1;i++)  {
			for(int j=1;j<n+1;j++)  {
			//	System.out.println(m+" "+n+" "+i+" "+j) ;
				if(s1[i-1].compareTo(s2[j-1])==0) c[i][j]=c[i-1][j-1]+1 ;
				else {
					if(c[i][j-1]>c[i-1][j]) c[i][j]=c[i][j-1] ; else c[i][j]=c[i-1][j] ;
				}
			}
		}
	    return c[m][n] ;
	}
	
	
	private void readPairs() {
		
		ReadFile rf=new ReadFile(_p.path+_p.projectName+"Pairs"+".txt") ;
		rf.openFile() ;			

		String line ;
		int i=0 ;
		while((line=rf.readLine())!=null) {
			i++ ;
			
			String[] str=line.split("-") ;
			Integer s1=Integer.valueOf(str[0]) ;
			Integer s2=Integer.valueOf(str[1]) ;
			
			_hm.put(s1,null) ;
			_hm.put(s2,null) ;
			
			if(_pa.containsKey(s1)) {
				if(!_pa.get(s1).contains(s2)) _pa.get(s1).add(s2) ;
			} else {
				List<Integer> a = new ArrayList<Integer>() ;
				a.add(s2) ;
				_pa.put(s1,a) ;
			}
			
			if(_pa.containsKey(s2)) {
				if(!_pa.get(s2).contains(s1)) _pa.get(s2).add(s1) ;
			} else {
				ArrayList<Integer> a = new ArrayList<Integer>() ;
				a.add(s1) ;
				_pa.put(s2, a) ;
			}
				
		}
								
		System.out.println("Read "+i+" Hash "+_hm.size()+" List "+_pa.size()) ;
		
		rf.closeFile() ;
			
	}

	public void loadSentencesTest(int l1, int l2) {
		
		ReadFile rf=new ReadFile(_p.path+_p.projectName+"Unique.txt") ;
		rf.openFile() ;			

		String line ;
		int i=0 ;
		while((line=rf.readLine())!=null) {
			i++ ;
			
			Signature sig = new WordSignature(line) ;
			if((sig.getId()).compareTo(Integer.valueOf(l1))>=0&&(sig.getId()).compareTo(Integer.valueOf(l2))<=0) {
				_hm.put(sig.getId(),sig.getSentence()) ;				
			}
			
		}
							
		System.out.println("Read "+i+" Hash "+_hm.size()+" Sent "+_hm.size()+" List "+_pa.size()) ;
		
		rf.closeFile() ;
		
	}

	public void readPairsTest() {
		
		
		Iterator<Integer> key=_hm.keySet().iterator() ;
		int i=0 ;
		int k=0 ;
		while(key.hasNext()) {
			i++ ;
			if(i%1000==0) System.out.println("@"+i+" "+k) ; 
			Integer k1=key.next() ;
			
			int l1=_hm.get(k1).split(" ").length ;
			
			Iterator<Integer> key2=_hm.keySet().iterator() ;
			for(int j=0;j<i;j++) key2.next() ;
			
			while(key2.hasNext()) {
				Integer k2=key2.next() ;
				int l2=_hm.get(k2).split(" ").length ;
				
				if(Math.abs(l1-l2)<=1) {
					k++ ;
					if(_pa.containsKey(k1)) {
						if(!_pa.get(k1).contains(k2)) _pa.get(k1).add(k2) ;
					} else {
						List<Integer> a = new ArrayList<Integer>() ;
						a.add(k2) ;
						_pa.put(k1,a) ;
					}
					
					if(_pa.containsKey(k2)) {
						if(!_pa.get(k2).contains(k1)) _pa.get(k2).add(k1) ;
					} else {
						ArrayList<Integer> a = new ArrayList<Integer>() ;
						a.add(k1) ;
						_pa.put(k2,a) ;
					}
				}				
			}
			
			
				
		}
								
		System.out.println("Read "+i+" Hash "+_hm.size()+" List "+_pa.size()) ;
					
	}
	
	private void loadSentences() {
		
		ReadFile rf=new ReadFile(_p.path+_p.projectName+".txt") ;
		rf.openFile() ;			

		String line ;
		int i=0 ;
		while((line=rf.readLine())!=null) {
			i++ ;
			
			Signature sig = new WordSignature(line) ;
			if(_hm.containsKey(sig.getId())) _hm.put(sig.getId(),sig.getSentence()) ;
			
		}
							
		System.out.println("Read "+i+" Hash "+_hm.size()+" Sent "+_hm.size()+" List "+_pa.size()) ;
		
		rf.closeFile() ;
		
	}

	private boolean same(String[] w1, String w2[]) {
		
		if(Math.abs(w1.length-w2.length)>1) return false ;
		
		int diff=0 ;
		int max=(w1.length>w2.length ? w1.length : w2.length) ;
		int w1d=0 ; int w2d=0 ;
		for(int i=0;i<max;i++) {
			String w1s=(w1.length>i+w1d ? w1[i+w1d] : "***££***") ;
			String w2s=(w2.length>i+w2d ? w2[i+w2d] : "***%%***") ;
			String w1n=(w1.length>i+1+w1d ? w1[i+1+w1d] : "***^^***") ;
			String w2n=(w2.length>i+1+w2d ? w2[i+w2d+1] : "***((***") ;
					
				if(w1s.compareTo(w2s)!=0) {
					diff++ ;
					if(w1n.compareTo(w2s)==0) w2d=-1 ; 
					else if(w2n.compareTo(w1s)==0) w1d=-1 ; 
				}
		}
		
		if(diff>1) return false ;
		
		return true ;
	}
	
	public void checkPairs() {
		
		Iterator<Integer> it=_hm.keySet().iterator() ;

		int sz=_hm.size() ;
		
		int i=0 ; int m=0 ; int mm=0 ;
		while(it.hasNext()) {
			i++ ;
			Integer key=it.next() ;
			
			String s1 = _hm.get(key) ;
			//System.out.println(key+" "+s1) ;
			if(_pa.containsKey(key)) {
				List<Integer> delList = new ArrayList<Integer>() ;
				List<Integer> trial = _pa.get(key) ;
				
				for(int t=0;t<trial.size();t++) {
					String s2=_hm.get(trial.get(t)) ;
					//System.out.println(trial.get(t)+" "+s2) ;
					
					int lcs = longestSubstr(s1,s2) ;
					int x = s1.split(" ").length ;
					int y= s2.split(" ").length ;
					int edit = x+y-2*lcs ;
					int dist = sumDist(s1,s2) ;
					
					int match = 0 ;
					if(edit<2) match=1 ;
					if(edit==2&&dist==1) match=1 ;
					
					m+=match ;
			//		int mmm=0 ;
		//			if(same(s1.split(" "),s2.split(" "))) mmm=1 ;
	//				mm+=mmm ;
					
//					if(mmm!=match) System.out.println(match+" "+mmm+" "+key+":"+s1+" "+trial.get(t)+":"+s2) ;
					
					if(match==0) delList.add(trial.get(t)) ;
			//		if(mmm==0) delList.add(trial.get(t)) ;
					//System.out.println("s1="+s1+"\ns2="+s2+"\nmatch="+match+" lcs="+lcs+" edit="+(x+y-2*lcs)+" l="+x+","+y+" dist="+dist);
				}
				
				if(delList.size()>0) {
					for(int d=0;d<delList.size();d++) trial.remove(trial.indexOf(delList.get(d))) ;
				}
				if(delList.size()>0) {
					for(int d=0;d<delList.size();d++) _pa.get(delList.get(d)).remove(_pa.get(delList.get(d)).indexOf(key)) ;
				}

				if(trial.size()<1) _pa.remove(key) ;
			}
			
		}
		System.out.println("Overall : Total="+i+" Match="+m+" Matchs="+mm+" Pairs="+_pa.size()+" Orig="+sz) ;
		
		
		
		
	}

	public void storePairs() {
		
		WriteFile rf=new WriteFile(_p.path+_p.projectName+"PairF"+".txt") ;
		rf.openFile() ;			
		
		Set<Map.Entry<Integer, List<Integer>>> keys=_pa.entrySet() ;
		Iterator<Map.Entry<Integer, List<Integer>>> it=keys.iterator() ;
		
		while(it.hasNext()) {
			Integer key=it.next().getKey() ;
			
			List<Integer> dups = _pa.get(key) ;
			String list=key.toString() ;
			for(int i=0;i<dups.size();i++) {
				list+=" "+dups.get(i) ;
			}
			list+="\n";
					
				rf.writeLine(list) ;
		}
		
			rf.closeFile() ;
		
	}
}
