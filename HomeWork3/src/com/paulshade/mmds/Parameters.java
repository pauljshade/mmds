package com.paulshade.mmds;

import java.io.File;

public class Parameters {


	public int minHash=200 ;
	public int buckets=20 ;
	public int rows=10 ;

	public String path=File.separator+"Users"+File.separator+"paulshade"+File.separator+"Desktop"+File.separator ;
	public String projectName="sentences" ;
	
	public String filenameLSH="LSH" ;
	public String filenameDuplicates="DUP" ;
	public String filenamePairs="Pair" ;
	public String filenamePairsF="sentencesPairF.txt" ;
	public String filenameFinal="sentencesFinal.txt" ;
			
}
