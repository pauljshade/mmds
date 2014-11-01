package com.paulshade.matrix;

import java.util.ArrayList;
import java.util.List;

public class PowerIteration<T extends Number> {

	public List<Double> eigenValue = new ArrayList<Double>() ;
	public Matrix<Double> eigenVector ;
	private boolean _verbose ;
	
	Matrix<Double> _m ;
	Vector<Double> _x ;
	
	public PowerIteration(Matrix<T> m, boolean verbose) {
		_verbose=verbose ;
		InputMatrix(m) ;
	} 

	public Matrix<Double> InputMatrix(Matrix<T> matrix) { 
		_m = new DoubleMatrix(matrix.length(), matrix.width()) ;
		for(int i=0;i<matrix.length();i++)
			for(int j=0;j<matrix.width();j++)
				_m.set(i, j, matrix.get(i, j).doubleValue()) ;	
		
		eigenVector = new DoubleMatrix(matrix.width()) ;

		if(_verbose) System.out.println(_m.print()) ;
		
		return _m ;
	}
	
	public Vector<Double> seedX(Matrix<Double> m) {
		_x = new DoubleVector(m.width()) ;
		for(int i=0;i<m.width();i++) _x.set(i, (Math.random()<0.5) ? 0.5d : 1.0d) ;
		
		if(_x.get(0).compareTo(0.5d)==0) _x.set(1, 1d) ; else _x.set(1, 0.5d) ; 

		if(_verbose) System.out.println(_x.print()) ;
		
		return _x ;

	}
	
	public Vector<Double> iterate(Matrix<Double> m, Vector<Double> v) {

		int i = 0 ;
		double err=0.0001d ;
		double chk=10d ;
		
		Vector<Double> xn=v ;
		double ev=0d ;
		
		while(err<chk&&i<100) {
			if(_verbose) System.out.println("\nIteration: "+i+"************************\n") ;
	
			/* step 1 : Mx */
			Vector<Double> x1 = m.multiply(xn) ;
			if(_verbose) System.out.println(m.print()) ;
			if(_verbose) System.out.println(xn.print()) ;
			if(_verbose) System.out.println(x1.print()) ;
			
			/* step 2 : get the norm */
			Vector<Double> x1n=x1.normalise() ;
			ev=x1.euclideanLength() ;
			if(_verbose) System.out.println("Ev: "+ev+" Vect: "+x1n.print()) ;
			
			/* step 3 : check the gap */
			chk=(xn.summ(x1n.multiply(-1d))).euclideanLength() ;
			if(_verbose) System.out.println(chk) ;
			
			xn=x1n ;
			i++ ;
		}
		
		double postChk=(_m.multiply(xn).multiply(-1d).summ(xn.multiply(ev))).euclideanLength() ;
		
		if(_verbose) System.out.println("Check EV: "+postChk) ;
		
		if(postChk<err*100d) {
			//System.out.println("wwew"+xn.length());
			eigenValue.add(ev) ;
			eigenVector.add(xn) ;
		}
		
		return xn ;
		
	}
	
	public Matrix<Double> prepNext(Matrix<Double> m) {
		
		Vector<Double> x=eigenVector.get(eigenVector.width()-1) ;
		double l=eigenValue.get(eigenValue.size()-1) ;
		
		Matrix<Double> m1= m.summ(x.multiply(x.multiply(-1d*l))) ;	
		if(_verbose) System.out.println("\nNew Matrix\n"+m1.print()) ;
		
		return m1 ;
	}
	
	public EigenSet process() {

		Matrix<Double> md = new DoubleMatrix(_m.length(), _m.width()) ;
		for(int i=0;i<_m.length();i++)
			for(int j=0;j<_m.width();j++)
				md.set(i, j, _m.get(i, j)) ;	

		int i=0 ;
		while(i==eigenValue.size()) {
			i++ ;
			Vector<Double> x1=seedX(md);
			iterate(md,x1);
			if(_verbose) System.out.println("Iter: i="+i+" ev="+eigenValue.size()) ;
			md=prepNext(md);
		}
		
		EigenSet es=new EigenSet() ;
		es.matrix=_m ;
		es.eigenValues=getEigenValues() ;
		es.eigenVectors=getEigenVectors() ;
		
		return es ;
	}
	
	public Vector<Double> getEigenValues() {
		Vector<Double> result = new DoubleVector(eigenValue.size()) ;
		for(int i=0;i<result.length();i++) result.set(i, eigenValue.get(i)) ;
		
		return result ;
	}

	public Matrix<Double> getEigenVectors() {
		return eigenVector ;
	}

	
}
