package com.asciiarmor.dax.examples;


import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;


public class AnalysisTransformTest extends TestCase {

	public void testExecute() throws DocumentException{
		// create a dom4j doc
		SAXReader reader = new SAXReader();
		Document doc = reader.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/asciiarmor/dax/examples/randj.xml"));
		
		// create a transformer
		AnalysisTransform t = new AnalysisTransform();
		
		// perform the transform
		t.execute(doc);

		assertEquals( t.lines.get("Rom."), new Integer(606));
		assertEquals( t.lines.get("Jul."), new Integer(542));
		
	}
}
