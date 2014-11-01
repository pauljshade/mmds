package com.paulshade.generatepairs;

public class BucketEntry {

	private int _id ;				public int getId() { return _id ; }
	private int _length ;			public int getLength() { return _length ; }
	private int _uniqueLength ;		public int getUniqueLength() { return _uniqueLength ; }
	
	public BucketEntry(Integer theId, Integer theLength, Integer uniqueLength) {
		_id=theId ;
		_length=theLength ;
		_uniqueLength=uniqueLength ;
	}

}
