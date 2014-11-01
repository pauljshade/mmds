package paulshade.com.hashfunctions;

import java.util.ArrayList;

public class HashGenerator {
		
	private boolean checkIfPrime(int value) {
		if(value<=0) return false ;
		if(value==2) return false ; /* dont want 2's - i know its prime */
		if(value%5==0) return false ; /* some odd ones */
		if(value>7&&value%7==0) return false ; /* some odd ones */
		if(value==1) return true ;  /* dont mind 1's - i know its not prime */
		if(value==3) return true ;
		if((value*value-1)% 24 == 0) return true ;
		
		return false ;
	}

	private int generateRandomPrime(int max) {
		
		int trial=0 ;
		while(!checkIfPrime(trial))
			trial= (int) Math.floor(Math.random()*((double)max)) ;
		
		return trial ;
	}
	
	public HashGenerator() { }
	
	public ArrayList<HashFunction> generateNewSet(int noFunctions) {

		/* create an array list of hash functions */
		ArrayList<HashFunction> hashSet = new ArrayList<HashFunction>(noFunctions) ;
		
		int j=2 ; /* counter - start above 2 */
		int i=0 ;
		while(i<noFunctions) {
			
			//System.out.println("Prime : "+i+" complete "+j+" current test") ;
			
			/* dont want multiple of the list number */
			if(noFunctions % j!=0) {
				/* only want primes */
				if(this.checkIfPrime(j)) {
					HashFunction hf = new HashFunction() ;
					hf.setMultiplier(j) ;
					hf.setAdditive(generateRandomPrime(50));
					hashSet.add(hf) ;
					i++ ;
				}
			}
			j++ ;
		}
		
		return hashSet ;
				
	}
		
}
