package com.paulshade.generatepairs.lshash;

import com.paulshade.matrix.IntegerVector;
import com.paulshade.matrix.Vector;

public class IntegerLSHasher implements LSHasher {

	private int _b ;
	private int _r ;
	
	public IntegerLSHasher(int b, int r) { _b=b ; _r=r ; } 
	
	public Vector<Integer> LSHash(Vector<Integer> signature) {
		
		Vector<Integer> v = new IntegerVector(_b) ;
			
		/* for each signature gather r values together and hash */
		int bc=0 ;
		for(int i=0;i<signature.length();i+=_r) {
			String bValue="" ;
			for(int m=0;m<_r;m++) bValue+=signature.get(i+m).toString() ;
			int hashValue=bValue.hashCode() ;
			v.set(bc,hashValue) ;
			bc++ ;
			//System.out.println("value:"+bValue+" hash:"+hashValue) ;
		}

		return v ;
	}

}
