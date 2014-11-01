package com.paulshade.matrix ;

public interface Matrix<T extends Number> {
	
	public boolean set(int i, int j, T value) ;
	public boolean set(int j, Vector<T> v) ;
	public T get(int i, int j) ;
	public Vector<T> get(int j) ;
	public Vector<T> getRowAsVector(int i) ;
	
	public boolean add(Vector<T> vector) ;
	
	public void clear() ;
	public boolean initialise(T value) ;
	public boolean initialiseRow(int i) ;
	public boolean initialiseRow(int i, T value) ;
	public boolean initialiseColumn(int j) ;
	public boolean initialiseColumn(int j, T value) ;
	
	public int length() ;
	public int width() ;

	/* manipulations */
	public Matrix<T> transpose() ; 
	public Matrix<T> summ(Matrix<T> m) ;
	public Matrix<T> multiply(Matrix<T> m) ;
	public Vector<T> multiply(Vector<T> v) ;
	public Matrix<T> multiply(T value) ;
	public EigenSet getEigenPairs(boolean verbose) ;
	public SVD performSVD() ;

	public String print() ;
	
	
}
