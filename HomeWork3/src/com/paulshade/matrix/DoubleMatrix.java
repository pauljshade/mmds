package com.paulshade.matrix;

public class DoubleMatrix extends SparseMatrix<Double>{

	/* constructors */
	public DoubleMatrix() { super() ; }
	public DoubleMatrix(int length, int width) { super(length, width) ; }
	public DoubleMatrix(int length) { super(length) ; }

	/* helpers */
	@Override
	protected Double zeroifnull(Double i) { 
		if(i==null) return 0d ;
		return i ; 
	}	
	@Override
	protected Double nullifzero(Double i) { 
		if(i==null) return null ;
		return (i.compareTo(0d)==0d) ? null : i ; 
	}

	@Override
	protected Matrix<Double> getNewMatrix(int l, int w) { return new DoubleMatrix(l,w) ; }
	@Override
	protected Vector<Double> getNewVector(int l) { return new DoubleVector(l) ; } 
	@Override
	protected Vector<Double> getNewVector(Vector<Double> v) { return new DoubleVector(v) ; } ;
	
}
