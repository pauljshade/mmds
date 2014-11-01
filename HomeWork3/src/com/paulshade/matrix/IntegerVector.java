package com.paulshade.matrix;

public class IntegerVector extends SparseVector<Integer> {

	/* constructors */
	public IntegerVector() { super() ; }
	public IntegerVector(int length) { super(length) ; }
	public IntegerVector(Vector<Integer> vector) { super(vector) ; }

	@Override
	protected Integer zeroifnull(Integer i) { 
		if(i==null) return 0 ;
		return i ; 
	}	
	protected Integer nullifzero(Integer i) { 
		if(i==null) return null ;
		return (i.compareTo(0)==0) ? null : i ; 
	}

	@Override
	protected Vector<Integer> getNewVector(int length) { return new IntegerVector(length) ; } 
	@Override
	protected Vector<Integer> getNewVector(Vector<Integer> v) { return new IntegerVector(v) ; } ;
	@Override
	protected Matrix<Integer> getNewMatrix(int l, int w) { return new IntegerMatrix(l,w) ; }
	@Override
	protected Integer getSumm(Integer v1, Integer v2) { return v1+v2 ; } 
	@Override
	protected Integer getProduct(Integer v1, Integer v2) { return v1*v2 ; } 

	
}
