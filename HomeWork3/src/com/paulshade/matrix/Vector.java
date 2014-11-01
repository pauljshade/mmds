package com.paulshade.matrix;

public interface Vector<T extends Number> {

	public boolean set(int i, T value) ;
	public T get(int i) ;
	
	public void clear() ;
	public boolean initialise(T value) ;
	
	public int length() ;
	
	public String print() ;
	
	/* operations */
	public double euclideanLength() ;
	public double dotProduct(Vector<T> v) ;
	public Vector<Double> root() ;
	public Vector<T> transpose() ; 
	public T product(Vector<T> v) ;
	public Matrix<T> multiply(Vector<T> v) ;
	public Vector<T> summ(Vector<T> v) ;
	public Vector<T> multiply(Matrix<T> m) ;
	public Vector<Double> normalise() ;
	public Vector<T> multiply(T value) ;
	public Matrix<T> diagonal() ;
	
}
