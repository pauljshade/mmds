package com.paulshade.matrix;

import java.util.HashMap;
import java.util.Map;

public abstract class SparseMatrix<T extends Number> implements Matrix<T> {

	private int _length ; @Override public int length() { return _length ; }
	private int _width ;  @Override public int width() { return _width ; }
	
	private Map<Integer,Vector<T>> _matrix = new HashMap<Integer, Vector<T>>() ; 
	
	/* Constructors */
	public SparseMatrix() { 
		_length=0 ;
		_width=0 ;		
	}
	
	public SparseMatrix(Matrix<T> matrix) {	
		_length=matrix.length() ;
		_width=matrix.width() ;
		clear() ;		
		/* copy the vector */
		for(int i=0;i<_width;i++) set(i, matrix.get(i)) ;  		
	}

	public SparseMatrix(int length, int width) {	
		_length=length ;
		_width=width ;
	}

	public SparseMatrix(int length) {
		_length=length ;
		_width=0 ;
	}	
	
	@Override
	public void clear() { _matrix.clear() ; }

	@Override
	public boolean set(int i, int j, T value) {
		
		if(!checkVectorPosition(i)||!checkColumnPosition(j)) return false ;	 /* out of bounds */
		if(!_matrix.containsKey(j)&&nullifzero(value)==null) return true ;	 /* does not exist and setting to 0 */
		
		if(!_matrix.containsKey(j)) _matrix.put(j, getNewVector(_length)) ;
		
		return _matrix.get(j).set(i, nullifzero(value)) ;
	}

	@Override public boolean initialiseRow(int i) { return initialiseRow(i,null); }
	@Override public boolean initialiseColumn(int j) { return initialiseColumn(j,null); }

	@Override
	public boolean initialise(T value) {
		boolean bFlag=true ;
		for(int j=0;j<_width;j++) if(!initialiseColumn(j,value)) bFlag=false ;
		return bFlag ;
	}

	@Override
	public boolean initialiseColumn(int j, T value) {
		if(!checkColumnPosition(j)) return false; /* out of bounds */
		if(!_matrix.containsKey(j)&&nullifzero(value)==null) return true ;	 /* does not exist and setting to 0 */
		
		if(!_matrix.containsKey(j)) _matrix.put(j, getNewVector(_length)) ;
		_matrix.get(j).initialise(nullifzero(value)) ;
		
		return true ;
	}

	@Override
	public boolean initialiseRow(int i, T value) {
		if(!checkVectorPosition(i)) return false ; /* out of bounds */
		
		for(int j=0;j<_width;j++) {
			if(_matrix.containsKey(j)) _matrix.get(j).set(i, nullifzero(value)) ;
			else if(nullifzero(value)!=null) {
				_matrix.put(j, getNewVector(_length)) ;			
				_matrix.get(j).set(i, nullifzero(value)) ;
			}
		}
		
		return true ;
	}


	@Override
	public boolean set(int j, Vector<T> v) {
		if(!checkColumnPosition(j)) return false; /* out of bounds */
		if(!this.sameLength(v)) return false ;

		if(_matrix.containsKey(j)) _matrix.get(j).clear();
		_matrix.put(j, getNewVector(v)) ;
				
		return true ;
	}

	@Override
	public T get(int i, int j) {
		if(!checkVectorPosition(i)||!checkColumnPosition(j)) return null ; /* out of bounds */

		if(!_matrix.containsKey(j)) return zeroifnull(null) ;
		
		return zeroifnull(_matrix.get(j).get(i)) ;
	}

	@Override
	public Vector<T> get(int j) {
		if(!checkColumnPosition(j)) return null ; /* out of bounds */
		
		if(!_matrix.containsKey(j)) return getNewVector(_length) ; /* need a blank vector */
		
		return _matrix.get(j) ;
	}
	
	@Override
	public Vector<T> getRowAsVector(int i) {
		if(!checkVectorPosition(i)) return null ; /* out of bounds */
		
		Vector<T> v=getNewVector(_width) ;
		
		for(int j=0;j<_width;j++) v.set(j, get(i,j)) ;
		
		return v ;	
	}
	

	@Override
	public boolean add(Vector<T> v) {
		
		if(!this.sameLength(v)) return false ;

		_width++ ;
		_matrix.put(_width-1, getNewVector(v)) ; 
		
		return true ;
	}

	@Override
	public Matrix<T> transpose() {
		Matrix<T> m=getNewMatrix(_width, _length) ;
		for(int i=0;i<_length;i++)
			for(int j=0;j<_width;j++) {
				if(!_matrix.containsKey(j)) m.set(j, i, null) ;
				else m.set(j, i, _matrix.get(j).get(i)) ;
			}
		return m;
	}

	@Override
	public Matrix<T> summ(Matrix<T> m) {
		if(m.length()!=_length||m.width()!=_width) return null ;
		
		Matrix<T> result=getNewMatrix(_length, _width) ;

		for(int j=0;j<_width;j++) result.set(j, getNewVector(get(j).summ(m.get(j)))) ;
		
		return result ;
	}
	
	@Override
	public Matrix<T> multiply(Matrix<T> m) {
		if(_width!=m.length()) return null ;
		
		Matrix<T> result=getNewMatrix(_length, m.width()) ;
		
		for(int i=0;i<_length;i++) {
			Vector<T> v=getRowAsVector(i) ;
			for(int j=0;j<_width;j++) result.set(i, j, v.product(m.get(j))) ;
		}
		
		return result ;
	}

	@Override
	public Vector<T> multiply(Vector<T> v) {
		if(_width!=v.length()) return null ;
		
		Vector<T> result=getNewVector(_length) ;
		
		for(int i=0;i<_length;i++) {
			Vector<T> vt=getRowAsVector(i) ;
			result.set(i, vt.product(v)) ;
		}
		
		return result ;
	}
	
	@Override
	public Matrix<T> multiply(T value) {

		Matrix<T> result=getNewMatrix(_length,_width) ;
		
		for(int i=0;i<_width;i++) result.set(i, _matrix.get(i).multiply(value)) ;
		
		return result ;
	}

	
	@Override
	public String print() {
		String output="" ;
		for(int i=0;i<_length;i++) {

			for(int j=0;j<_width;j++) output+=zeroifnull(get(i,j)).toString()+" " ;

			output+="\n" ;
		}
		return output ;
	}

	@Override 
	public EigenSet getEigenPairs(boolean verbose) {
		PowerIteration<T> pi= new PowerIteration<T>(this, verbose) ;
		
		return pi.process() ;
	}
	
	@Override
	public SVD performSVD() {
		SVD result = new SVD() ;

		Matrix<T> mtm=(this.transpose()).multiply(this) ;
		EigenSet vSig = mtm.getEigenPairs(false) ;
		Matrix<T> mmt=this.multiply(this.transpose()) ;
		EigenSet uSig = mmt.getEigenPairs(false) ;

		result.sig = vSig.eigenValues.root().diagonal() ;
		result.v = vSig.eigenVectors ;
		result.u = uSig.eigenVectors ;
		result.dimensionReduction();
		
		return result ;
	}
	
	/* value based delegated issues */
	protected abstract Matrix<T> getNewMatrix(int l, int w) ;
	protected abstract Vector<T> getNewVector(int l) ;
	protected abstract Vector<T> getNewVector(Vector<T> v) ;
	protected abstract T nullifzero(T value) ;
	protected abstract T zeroifnull(T value) ;

	/* helpers */
	private boolean sameLength(Vector<T> v) { return (v.length()==_length) ? true : false ; }
	private boolean checkVectorPosition(int i) { return (i>=0&&i<_length) ? true : false ; }
	private boolean checkColumnPosition(int i) { return (i>=0&&i<_width) ? true : false ; }
	
}
