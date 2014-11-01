package com.paulshade.matrix;

public class DoubleVector extends SparseVector<Double> {

	/* constructors */
	public DoubleVector() { super() ; }
	public DoubleVector(int length) { super(length) ; }
	public DoubleVector(Vector<Double> vector) { super(vector) ; }

	@Override
	protected Double zeroifnull(Double i) { 
		if(i==null) return 0d ;
		return i ; 
	}	
	protected Double nullifzero(Double i) { 
		if(i==null) return null ;
		return (i.compareTo(0d)==0d) ? null : i ; 
	}

	@Override
	protected Vector<Double> getNewVector(int length) { return new DoubleVector(length) ; } 
	@Override
	protected Vector<Double> getNewVector(Vector<Double> v) { return new DoubleVector(v) ; } ;
	@Override
	protected Matrix<Double> getNewMatrix(int l, int w) { return new DoubleMatrix(l,w) ; }
	@Override
	protected Double getSumm(Double v1, Double v2) { return v1+v2 ; } 
	@Override
	protected Double getProduct(Double v1, Double v2) { return v1*v2 ; } 

	
}
