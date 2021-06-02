package annotation.model;

import java.awt.Color;
import java.awt.Point;

import org.junit.Test;

import junit.framework.TestCase;

public class TestAnnoModel extends TestCase {

	@Test
	public void testCreate() {
		AnnotationModel testModel = new AnnotationModel(1, new Point(2, 3));
		
		assertEquals(1, testModel.getID());
		assertEquals(Color.gray, testModel.getColor());
	}
	
	@Test
	public void testSet() {
		AnnotationModel testModel = new AnnotationModel(1, new Point(2, 3));
		
		testModel.setSize(new Point(0, 0));
		testModel.setAnnotation("test_string");
		testModel.setColor(Color.red);
		
		assertEquals(0, testModel.getX());
		assertEquals(0, testModel.getY());
		assertEquals(2, testModel.getX2());
		assertEquals(3, testModel.getY2());
		assertEquals("test_string", testModel.getAnnotation());
		assertEquals(Color.red, testModel.getColor());
	}
	
}
