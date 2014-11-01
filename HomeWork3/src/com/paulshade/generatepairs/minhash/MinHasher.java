package com.paulshade.generatepairs.minhash;

import com.paulshade.matrix.Vector;

public interface MinHasher {

	public Vector<Integer> minHash(Vector<Integer> tokens) ;
	
}
