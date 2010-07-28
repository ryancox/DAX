package com.asciiarmor.dax.examples;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import junit.framework.TestCase;

public class BindingTransformTest extends TestCase {
	public void testExecute() throws DocumentException {
		// create a dom4j doc
		SAXReader reader = new SAXReader();
		Document doc = reader.read(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream("com/asciiarmor/dax/examples/slashdot.xml"));

		// create a transformer
		BindingTransform t = new BindingTransform();

		// perform the transform
		t.execute(doc);

		assertEquals(t.items.size(), 10);

	}
}
