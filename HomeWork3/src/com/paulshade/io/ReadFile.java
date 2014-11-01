package com.paulshade.io;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ReadFile implements File {

	private String path ;
	
	private FileReader fr ;
	private BufferedReader textReader ;
	
	public ReadFile(String file_path) {
		path=file_path ;
	}
	
	public boolean openFile()  {
		try {
			fr = new FileReader(path) ;
			textReader = new BufferedReader(fr) ;
		} catch(IOException e) { System.out.println("\n**************************\nError opening file\n**************************\n"); } ;
	    
	    return true ;
	}
	
	public String readLine() {
		
		String line ;
		try {
			if((line=textReader.readLine()) != null) return line ;
		} catch(IOException e) { System.out.println("\n**************************\nError reading file\n**************************\n"); } ;
			
		return null ;
	}
	
	public boolean closeFile() {
		try {
			textReader.close() ; 
			fr.close() ;
		} catch(IOException e) { System.out.println("\n**************************\nError closing files\n**************************\n"); } ;
		
		return true ;
	}
}
