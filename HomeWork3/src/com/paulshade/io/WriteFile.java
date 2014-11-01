package com.paulshade.io;

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class WriteFile implements File {

	private String path ;
	
	private FileWriter fr ;
	private BufferedWriter textWriter ;
	
	public WriteFile(String file_path) {
		path=file_path ;
	}
	
	public boolean openFile() {
		try {
			fr = new FileWriter(path) ;
			textWriter = new BufferedWriter(fr) ;
		} catch(IOException e) { System.out.println("\n**************************\nError opening file\n**************************\n"); } ;
	    
	    return true ;
	}
	
	public void writeLine(String line)  {
		
		try {
			textWriter.write(line) ;
		} catch(IOException e) { System.out.println("\n**************************\nError writing file\n**************************\n"); } ;
			
	}
	
	public boolean closeFile()  {
		try {
			textWriter.close() ; 
			fr.close() ;
		} catch(IOException e) { System.out.println("\n**************************\nError closing files\n**************************\n"); } ;
		
		return true ;
	}
}
