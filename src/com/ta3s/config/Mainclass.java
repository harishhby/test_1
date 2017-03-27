package com.ta3s.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class Mainclass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> file= new ArrayList<String>();
		String path =GetPath.getBasePath()+"/testNg.xml";
		System.out.println("Path Is:"+path);
		System.out.println("Execution Started");
		file.add(File.separator+path);
		
		TestNG testNG=new TestNG();
		testNG.setTestSuites(file);
		testNG.run();
	}
}
