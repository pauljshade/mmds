package com.paulshade.generatepairs.minhash;

import java.util.List;

import paulshade.com.hashfunctions.HashFunction;

import com.paulshade.matrix.IntegerVector;
import com.paulshade.matrix.Vector;

public class IntegerMinHasher implements MinHasher {

	private List<HashFunction> _hfs ;
	private int _length ;
	
	public IntegerMinHasher(List<HashFunction> hfs, int length) { _hfs=hfs ; _length=length ; } 
	
	public Vector<Integer> minHash(Vector<Integer> tokens) {
		
		Vector<Integer> v = new IntegerVector(_length) ;
		v.initialise(2147483647) ;
			
		/* for each token cycle through each hash function and set min value */
		for(int i=0;i<tokens.length();i++) {
			for(int j=0;j<_length;j++) {
				int hashValue=_hfs.get(j).calculate(tokens.get(i), _length) ;
				//if(j==0) System.out.println("Hash : token:"+i+" value:"+tokens[i]+" hash:"+j+" "+Parameters.hfs.get(j).description()+" value:"+hashValue) ;
				if(v.get(j).compareTo(Integer.valueOf(hashValue))>0) v.set(j,hashValue) ;
			}
		}
			
		return v ;
	}
}
