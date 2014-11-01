package com.paulshade.matrix;

public class IntegerMatrix extends SparseMatrix<Integer>{

	/* constructors */
	public IntegerMatrix() { super() ; }
	public IntegerMatrix(int length, int width) { super(length, width) ; }

	/* helpers */
	@Override
	protected Integer zeroifnull(Integer i) { 
		if(i==null) return 0 ;
		return i ; 
	}	
	@Override
	protected Integer nullifzero(Integer i) { 
		if(i==null) return null ;
		return (i.compareTo(0)==0) ? null : i ; 
	}

	@Override
	protected Matrix<Integer> getNewMatrix(int l, int w) { return new IntegerMatrix(l,w) ; }
	@Override
	protected Vector<Integer> getNewVector(int l) { return new IntegerVector(l) ; } 
	@Override
	protected Vector<Integer> getNewVector(Vector<Integer> v) { return new IntegerVector(v) ; } ;
	
}
