package paulshade.com.hashfunctions;

import java.nio.ByteBuffer;

public class HashFunction {

	private Integer multiplier ;
	private Integer additive ;
	
	public HashFunction() { } ;
	
	public void setMultiplier(int value) { multiplier=value ; }
	public void setAdditive(int value) { additive=value ; }
	
	public String description() {
		if(multiplier!=null&&additive!=null) return multiplier.toString()+"x+"+additive.toString() ;
		
		return "Not set" ;
	}
	
	public int calculate(String value) {
		Integer fInt=value.hashCode() ;
		long fin=fInt.longValue() + 2147483647 + 1 ;

		return (int) (fin) ;

	}

	public int calculate(Integer value, int mod) {
		long multl=(long) multiplier ;
		long addl= (long) additive ;
		long modl= (long) mod ;
		
		/* make the value long and positive */
		// biggest int 2147483647  lowest -2147483648
		Long lValue= value.longValue() + 2147483647 + 1 ;
		
		Long fVal=(multl*lValue+addl) ;
		//String sfVal=(fVal).toString() ;
		//Integer fInt=fVal.hashCode() ;
		
		//Long fin= fInt.longValue() + 2147483647 + 1 ;
		//System.out.println(value+" "+fVal+" "+fInt+" "+fin+" "+fin%modl) ;

		return (int) (fVal % modl) ;
	}
	
	public long unsignedInt(int value) {

		ByteBuffer buf = ByteBuffer.allocate(Long.SIZE / 8);
		buf.putInt(Integer.SIZE / 8, value) ;
		return buf.getLong(0);
		
	}

}
