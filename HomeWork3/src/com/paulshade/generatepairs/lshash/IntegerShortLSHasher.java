package com.paulshade.generatepairs.lshash;

import com.paulshade.matrix.IntegerVector;
import com.paulshade.matrix.Vector;

public class IntegerShortLSHasher implements LSHasher {

		private int _b ;
		
		public IntegerShortLSHasher(int b) { _b=b ; } 
		
		public Vector<Integer> LSHash(Vector<Integer> signature) {
			
			Vector<Integer> v = new IntegerVector(_b) ;
				
			v.set(0,(signature.get(0).toString()+signature.get(2)).hashCode()) ;
			v.set(1,(signature.get(0).toString()+signature.get(3)).hashCode()) ;
//			v.set(0,(signature.get(0).toString()+signature.get(1).toString()+signature.get(2)).hashCode()) ;
//			v.set(1,(signature.get(0).toString()+signature.get(1).toString()+signature.get(3)).hashCode()) ;
//			v.set(0,(signature.get(2)).hashCode()) ;
//			v.set(1,(signature.get(3)).hashCode()) ;

			return v ;
		}

}

