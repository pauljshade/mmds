package com.paulshade.generatepairs.tokenizer;

import com.paulshade.matrix.IntegerVector;
import com.paulshade.matrix.Vector;

public class StringTokenizer implements Tokenizer {
	
	public StringTokenizer() { }

	@Override
	public Vector<Integer> tokenise(Object[] input) {
		Vector<Integer> v=new IntegerVector(input.length) ;
		for(int i=0;i<input.length;i++) v.set(i, ((String) input[i]).hashCode()) ;
		
		return v ;
	}
	
}