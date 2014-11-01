package com.paulshade.generatepairs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.paulshade.io.ReadFile;
import com.paulshade.io.WriteFile;
import com.paulshade.mmds.Parameters;
import com.paulshade.sentence.SentenceProcessing;

public class FinalCountUp implements SentenceProcessing {
	
	private Parameters _p ;
	private Map<Integer,List<Integer>> fin = new HashMap<Integer,List<Integer>>() ;
	private Map<Integer,Integer> dupList = new HashMap<Integer,Integer>() ;
	
	public FinalCountUp(Parameters p) {
		_p=p ;
	}
	
	@Override
	public void process() {
		readDups() ;
		readPair();
		storeFinal();	
	}
	
	private void readDups() {
	

		ReadFile rf=new ReadFile(_p.path+_p.projectName+"Dups"+".txt") ;
			rf.openFile() ;			
	
		String line="" ;
		int i=0 ; int s=0 ; int load=0 ;
			while((line=rf.readLine())!=null) { 
				i++ ;
			
				String[] str=line.split(" ") ;				

				Integer key=Integer.valueOf(str[0]) ;
				Integer size=str.length ; /* includes the key in th elist */
				dupList.put(key,size) ; 
				load+=size*(size-1)/2 ;
				s+=size+1 ;
			}
		
		System.out.println("Dups "+i+" size="+s+" load="+load) ;
			rf.closeFile() ;
	}	

	public void readPair() {	

		ReadFile rf=new ReadFile(_p.path+_p.projectName+"PairF"+".txt") ;
			rf.openFile() ;			
	
		String line="" ;
			while((line=rf.readLine())!=null) { 
			
				String[] str=line.split(" ") ;				

				Integer key=Integer.valueOf(str[0]) ;
				
				Integer size=str.length-1 ;
				
				/*  get number of dups for key */
				Integer dups=0 ;
				if(dupList.containsKey(key)) dups+=dupList.get(key) ;
				else dups++ ;
				
				/* sum up the dups for all other pairs */
				Integer pairDups=0 ;
				for(int d=1;d<str.length;d++) {
					Integer pKey=Integer.valueOf(str[d]) ;
					if(dupList.containsKey(pKey)) pairDups+=dupList.get(pKey) ;
					else pairDups++ ;
				}

				ArrayList<Integer> a =new ArrayList<Integer>() ;
				a.add(size) ; a.add(dups) ; a.add(pairDups) ;
				fin.put(key,a) ;	
			}
			
			Set<Map.Entry<Integer, Integer>> keys=dupList.entrySet() ;
			Iterator<Map.Entry<Integer, Integer>> it=keys.iterator() ;
			
			int d=0;
			while(it.hasNext()) {
				Integer key=it.next().getKey() ;
				
				if(!fin.containsKey(key)) {
					ArrayList<Integer> a =new ArrayList<Integer>() ;
					a.add(0) ; a.add(dupList.get(key)) ; a.add(0) ;
					fin.put(key,a) ; d++ ;
				}
			}	

			System.out.println("Dups - "+d) ;
			rf.closeFile() ;
	}	

	public void finalCalc()  {
		
		Set<Map.Entry<Integer,List<Integer>>> keys=fin.entrySet() ;
		Iterator<Map.Entry<Integer, List<Integer>>> it=keys.iterator() ;
		
		while(it.hasNext()) {
		//	Integer key=it.next().getKey() ;
			
		//	int n=fin.get(key).get(0) ;
		//	int d=fin.get(key).get(1) ;
			
			//(d)*(d-1))
			
		}
			
			
	}

	public void storeFinal() {
		
		WriteFile rf=new WriteFile(_p.path+_p.projectName+"Final"+".txt") ;
			rf.openFile() ;			
		
		Set<Map.Entry<Integer, List<Integer>>> keys=fin.entrySet() ;
		Iterator<Map.Entry<Integer, List<Integer>>> it=keys.iterator() ;
		int k=0 ; int dupsc=0 ; int pairs=0 ; //int total=0 ;
		int d0=0 ;
		while(it.hasNext()) {
			Integer key=it.next().getKey() ;
			
			List<Integer> dups = fin.get(key) ;
			String list=key+" " ;
			if(dups.get(1)==0) d0++ ;
			for(int i=0;i<dups.size();i++) {
				if(i>0) list+=" " ;
				list+=dups.get(i) ;
				if(i==1&&dups.get(1)>0) {
					dupsc+=(dups.get(1)*(dups.get(1)-1)) ;
				}
				if(i==2) pairs+=(dups.get(1)*dups.get(2)) ;
			}
			list+="\n";
					
				k++ ;
				rf.writeLine(list) ;
		}
		
			rf.closeFile() ;
		
		
		long aa=((long)dupsc)/2 ;
		long bb=((long)pairs)/2 ;
		long cc=aa+bb ;

		System.out.println("Final " + k + " Pairs "+pairs+" Dups 0 "+d0) ;
		System.out.println("Total " + cc + " Pairs "+bb+" Dups "+aa) ;
	}

}
