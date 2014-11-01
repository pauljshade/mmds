package com.paulshade.matrix;

import java.util.HashMap;
import java.util.Map;

public abstract class SparseVector<T extends Number> implements Vector<T> {

	private int _length ; @Override public int length() { return _length ; }
	private Map<Integer,T> _vector = new HashMap<Integer, T>() ;
	
	/* constructors */
	public SparseVector() { _length=0 ; }
	public SparseVector(int length) { _length=length ; }
	public SparseVector(Vector<T> vector) {
		_length=vector.length() ;
		clear() ;		
		/* copy the vector */
		for(int i=0;i<_length;i++) set(i, vector.get(i)) ;  		
	}

	@Override
	public void clear() { _vector.clear(); }

	@Override
	public T get(int i) { 
		if(!checkVectorPosition(i)) return null ;
		return zeroifnull(_vector.get(Integer.valueOf(i))); }

	@Override
	public boolean set(int i, T value) {
		if(!checkVectorPosition(i)) return false ;
		
		value=nullifzero(value) ;
		
		if(value==null) _vector.remove(i) ;
		else _vector.put(Integer.valueOf(i), value) ;
		
		return true;
	}

	@Override
	public boolean initialise(T value) {
		value=nullifzero(value) ;
		
		if(value==null) clear() ;
		else for(int i=0;i<_length;i++) set(i,value) ;

		return true ;
	}

	@Override
	public String print() {
		String output="" ;
		for(int i=0;i<_length;i++) output+=zeroifnull(get(i)).toString()+" " ;
		output+="\n" ;
		
		return output ;
	}
	
	/* manipulations */
	@Override
	public Vector<T> transpose() { return getNewVector(this) ; }
	
	@Override
	public T product(Vector<T> v) {
		if(v.length()!=_length) return null ;

		T value=zeroifnull(null) ;
		
		for(int i=0;i<_length;i++) value=getSumm(value,getProduct(get(i),v.get(i))) ;
		return value ;
	}

	@Override
	public Matrix<T> multiply(Vector<T> v) {

		Matrix<T> result=getNewMatrix(_length, v.length()) ;
		
		for(int i=0;i<_length;i++) {
			for(int j=0;j<v.length();j++) result.set(i, j, getProduct(get(i),v.get(j))) ;
		}
		
		return result ;
	}

	@Override
	public Vector<T> summ(Vector<T> v) {
		if(v.length()!=_length) return null ;

		Vector<T> vector = getNewVector(v.length()) ;;
		for(int i=0;i<_length;i++) vector.set(i, getSumm(get(i),v.get(i))) ;
		return vector ;
	}
	
	
	@Override
	public double euclideanLength() {
		T value=zeroifnull(null) ;
		for(int i=0;i<_length;i++) value=getSumm(value,getProduct(get(i),get(i))) ;
		
		double len = value.doubleValue() ;
		
		return Math.sqrt(len) ;
	}

	@Override
	public double dotProduct(Vector<T> v) {
		if(v.length()!=_length) return 0 ;

		T value=product(v) ;
		
		return value.doubleValue()/(euclideanLength()*v.euclideanLength()) ;

	}

	@Override
	public Vector<T> multiply(Matrix<T> m) {
		if(_length!=m.length()) return null ;
		
		Vector<T> result=getNewVector(m.width()) ;
		
		for(int i=0;i<m.length();i++) result.set(i, product(m.get(i))) ;

		return result ;
	}

	@Override
	public Vector<T> multiply(T value) {

		Vector<T> result=getNewVector(_length) ;
		
		for(int i=0;i<_length;i++) result.set(i, getProduct(get(i),value)) ;

		return result ;

	}
	
	@Override
	public Vector<Double> normalise() {
		Vector<Double> result=new DoubleVector(_length) ;

		double d = euclideanLength() ;
		
		for(int i=0;i<_length;i++) result.set(i, get(i).doubleValue()/d) ;
		
		return result ;
	}
	
	@Override
	public Matrix<T> diagonal() {
		Matrix<T> result = getNewMatrix(_length,_length) ;
		
		for(int i=0; i<_length;i++) result.set(i, i,get(i)) ;
		
		return result ;
	}
	
	@Override
	public Vector<Double> root() {
		Vector<Double> result=new DoubleVector(_length) ;
		
		for(int i=0;i<_length;i++) result.set(i, Math.sqrt(get(i).doubleValue())) ;
 		
		return result ;
	}
	
	
	/* value based delegated issues */
	protected abstract T nullifzero(T value) ;
	protected abstract T zeroifnull(T value) ;
	protected abstract T getProduct(T v1, T v2) ;
	protected abstract T getSumm(T v1, T v2) ;
	protected abstract Vector<T> getNewVector(Vector<T> v) ;
	protected abstract Matrix<T> getNewMatrix(int l, int w) ;
	protected abstract Vector<T> getNewVector(int length) ;
	
	/* helpers */
	private boolean checkVectorPosition(int i) { return (i>=0&&i<_length) ? true : false ; }
	

}
