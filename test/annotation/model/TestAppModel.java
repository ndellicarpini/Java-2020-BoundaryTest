package annotation.model;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

import junit.framework.TestCase;

public class TestAppModel extends TestCase {
	
	@Test
	public void testCreate() {
		AppModel testModel = new AppModel();
		assertEquals(new ArrayList<AnnotationModel>().size(), testModel.getAnnotations().size());
		assertEquals(null, testModel.getSelected());
		assertEquals(false, testModel.getCheat());
		
	}
	
	@Test
	public void testSet() {
		AppModel testModel = new AppModel();
		
		String testString = "test_string";
		byte[] testBytes = testString.getBytes();
		ArrayList<AnnotationModel> testList = new ArrayList<AnnotationModel>();
		
		for (int i = 0; i < 3; i++) {
			testList.add(new AnnotationModel(i, new Point(i, i)));
		}
		
		testModel.setImage(testString, testBytes);
		testModel.setAnnotations(testList);
		testModel.select(testList.get(0));
		testModel.setCheat(true);
		
		assertEquals(testString, testModel.getName());
		assertEquals(testBytes, testModel.getImage());
		assertEquals(testList, testModel.getAnnotations());
		assertEquals(testList.get(0), testModel.getSelected());
		assertEquals(true, testModel.getCheat());
		
		testModel.deselect();
		
		assertEquals(null, testModel.getSelected());
		
	}
	
	@Test
	public void testSetModel() {
		AppModel testModel = new AppModel();
		AppModel testModel2 = new AppModel();
		
		String testString = "test_string";
		byte[] testBytes = testString.getBytes();
		ArrayList<AnnotationModel> testList = new ArrayList<AnnotationModel>();
		
		for (int i = 0; i < 3; i++) {
			testList.add(new AnnotationModel(i, new Point(i, i)));
		}
		
		testModel.setImage(testString, testBytes);
		testModel.setAnnotations(testList);
		
		testModel2.setModel(testModel);
		
		assertEquals(testString, testModel2.getName());
		assertEquals(testBytes, testModel2.getImage());
		assertEquals(testList, testModel2.getAnnotations());
		
		testModel2.resetModel();
		
		assertEquals(new ArrayList<AnnotationModel>().size(), testModel2.getAnnotations().size());
		assertEquals(null, testModel2.getSelected());
		assertEquals(false, testModel2.getCheat());
		
	}

}
