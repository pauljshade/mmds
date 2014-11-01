package com.paulshade.matrix;

public class SVD {

	public Matrix<Double> u ;
	public Matrix<Double> v ;
	public Matrix<Double> sig ;
	public int dimRed ;
	
	
	public Matrix<Double> check() {
		return u.multiply(sig).multiply(v.transpose()) ;
	}
	
	public void dimensionReduction() {
		double eigTot=0 ;
		for(int i=0;i<sig.length();i++) eigTot+=sig.get(i, i).doubleValue() ;
		
		eigTot*=0.8d ;

		dimRed=0 ; 
		double eig=0 ;
		for(int i=0;i<sig.length();i++) {
			if(eig<=eigTot) dimRed++ ;
			eig+=sig.get(i, i).doubleValue() ;
		}	
	}
	
	public String print() {

		System.out.println("************* U ***************") ;
		System.out.println(u.print()) ;
		System.out.println("****** Sigma - Reduction to "+dimRed+" from "+sig.length()+" *******") ;
		System.out.println(sig.print())  ;
		System.out.println("************* VT ***************") ;
		System.out.println(v.transpose().print()) ;
		System.out.println("************* USigVT ***************") ;
		System.out.println(check().print()) ;
		
		return null ;
	}
	
}
