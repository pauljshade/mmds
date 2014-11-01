package com.paulshade.generatepairs;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.paulshade.io.ReadFile;
import com.paulshade.io.WriteFile;
import com.paulshade.mmds.Parameters;
import com.paulshade.sentence.SentenceProcessing;

public class GeneratePairs implements SentenceProcessing {

	private Parameters _p ;
	
	private Map<Integer,Integer> _hashes = new HashMap<Integer,Integer>() ;
	private List<Map<Integer,List<BucketEntry>>> _matchIds = new ArrayList<Map<Integer,List<BucketEntry>>>() ;
	private SortedMap<String,Integer> pairIds=new TreeMap<String,Integer>() ;
	
	public GeneratePairs(Parameters p) {
		_p=p;
	}
	
	@Override 
	public void process() {
		for(int b=0 ; b<_p.buckets;b++) {
			preHash(b) ;
			readBucket(b) ;
			getPairs(b) ;
		}
		
		storePairs() ;
	}

	public void preHash(int bucket) {
		
		ReadFile rf=new ReadFile(_p.path+_p.projectName+"LSH.txt") ;
		rf.openFile() ;			

		_hashes.clear(); 
		
		String line ;
		int i=0 ; int b=0 ; int ee=0 ; 
		while((line=rf.readLine())!=null) {
			i++ ;
			/* split the line and grab the correct bucket */
			String[] str=line.split(" ") ;
			Integer hash=Integer.valueOf(str[bucket+3]) ;			

			if(_hashes.containsKey(hash)) {
				_hashes.put(hash, _hashes.get(hash)+1) ; 
				ee++ ;
			} else {
				_hashes.put(hash,1) ;
				ee++ ; b++ ;
			}
			
			//if(str[0].compareTo("150")==0||str[0].compareTo("207")==0) System.out.println("id="+str[0]+" "+hash) ;
			//if(hash==2072815748) System.out.println("hash id="+str[0]) ;
			if(i%1000000==0) System.out.println("@ "+i) ;
		}
			
		/* clean out the 1's */
		Iterator<Integer> it=_hashes.keySet().iterator() ;
		ee=0 ; 
		while(it.hasNext()) {
			Integer key = it.next() ;
			if(_hashes.get(key)>1) { ee+=_hashes.get(key) ; }
			if(_hashes.get(key)<=1) it.remove();
		}
				
		System.out.println("A Bucket "+bucket+" Read "+i+" Hashes "+b+" Entrie "+ee+" Final "+_hashes.size()) ;
			
		rf.closeFile() ;
		
	}
	
	public void readBucket(int bucket) {
		
		ReadFile rf=new ReadFile(_p.path+_p.projectName+"LSH.txt") ;
		rf.openFile() ;			

		Map<Integer,List<BucketEntry>> bes = new HashMap<Integer,List<BucketEntry>>() ;
		
		String line ;
		int i=0 ; int b=0 ; int ee=0 ; 
		while((line=rf.readLine())!=null) {
			i++ ;
			/* split the line and grab the correct bucket */
			String[] str=line.split(" ") ;
			Integer hash=Integer.valueOf(str[bucket+3]) ;

			if(_hashes.containsKey(hash)) {
				Integer id=Integer.valueOf(str[0]) ;
				Integer length=Integer.valueOf(str[1]) ;
				Integer uniqueLength=Integer.valueOf(str[1]) ;
				
				BucketEntry be=new BucketEntry(id,length,uniqueLength) ;
	
				if(bes.containsKey(hash)) {
					bes.get(hash).add(be) ;
					ee++ ; 
				} else {
					List<BucketEntry> a=new ArrayList<BucketEntry>() ;
					a.add(be) ;
					bes.put(hash, a) ;
					ee++ ; b++ ;
				}
			}
			
			if(i%1000000==0) System.out.println("@ "+i) ;
		}
			
		/* clean out the 1's */
		cleanUp(bes) ; 			
		_matchIds.add(bes) ;
				
		System.out.println("B Bucket "+bucket+" Read "+i+" Hashes "+b+" Entrie "+ee+" Final "+bes.size()) ;
			
		rf.closeFile() ;
		
	}
	
	private void cleanUp(Map<Integer,List<BucketEntry>> map) {
		
		Iterator<Integer> keys=map.keySet().iterator() ;
		
		while(keys.hasNext()) {
			Integer key=keys.next() ;
			if(map.get(key).size()==1) keys.remove() ;
		}
			
	}

	private void getPairs(int bucket) {

		Iterator<Integer> it=_matchIds.get(bucket).keySet().iterator() ;
		
		while(it.hasNext()) {
			List<BucketEntry> sg=_matchIds.get(bucket).get(it.next()) ;
			
			if(sg.size()>1) {
				for(int j=0;j<sg.size()-1;j++) {
					BucketEntry s1=sg.get(j) ;
					for(int k=j+1;k<sg.size();k++) {
						BucketEntry s2=sg.get(k) ;
						/* check length */
						//if(Math.abs(s1.getLength()-s2.getLength())<=1&&Math.abs(s1.getUniqueLength()-s2.getUniqueLength())<=1) {
							//System.out.println(s1.getId()+"-"+s2.getId()) ;
							if(Integer.valueOf(s1.getId()).compareTo(Integer.valueOf(s2.getId()))<0) {  
								String s =s1.getId()+"-"+s2.getId() ;
								if(!pairIds.containsKey(s))  pairIds.put(s, 1) ;
							} else {
								String s =s2.getId()+"-"+s1.getId() ;
								if(!pairIds.containsKey(s))  pairIds.put(s, 1) ;
							}
						//}
					}
				}
			
			}
		}
		
		System.out.println("Pairs Bucket "+bucket+" input "+_matchIds.get(bucket).size()+" size "+pairIds.size()) ;
		
		_matchIds.get(bucket).clear() ; _matchIds.set(bucket,null) ;
		
	}		
	
	private void storePairs() {
		
		WriteFile rf=new WriteFile(_p.path+_p.projectName+"Pairs.txt") ;
		rf.openFile() ;			
		int i=0 ;
		for(String key : pairIds.keySet()) {
			i++ ;
			rf.writeLine(key+"\n") ;
		}

		System.out.println("Total pairs - "+i) ;
		rf.closeFile() ;
	
	}

}
