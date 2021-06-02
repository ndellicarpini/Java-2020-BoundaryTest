package annotation.controller;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextArea;

import org.junit.Test;

import annotation.boundary.ImagePanel;
import annotation.model.AnnotationModel;
import annotation.model.AppModel;
import junit.framework.TestCase;

public class TestModelControl extends TestCase {

	@Test
	public void testInvalidAnnoID() {
		AppModel model = new AppModel();
		AnnotationModel anno = new AnnotationModel(1, new Point(2, 3));
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		annoList.add(anno);
		
		model.setAnnotations(annoList);
		
		AnnotationModel badAnno = new ModelController(model).annotationFromID(0);
		
		assertEquals(-1, badAnno.getID());
	}
	
	@Test
	public void testNumAnno() {
		AppModel model = new AppModel();
		AnnotationModel anno = new AnnotationModel(1, new Point(2, 3));
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		annoList.add(anno);
		
		model.setAnnotations(annoList);
		
		assertEquals(1, new ModelController(model).numAnnotations());
		
		annoList.add(new AnnotationModel(4, new Point(5, 6)));
		model.setAnnotations(annoList);
		
		assertEquals(2, new ModelController(model).numAnnotations());
	}
	
	@Test
	public void testNewAnnotationID() {
		AppModel model = new AppModel();
	
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		annoList.add(new AnnotationModel(1, new Point(2, 3)));
		annoList.add(new AnnotationModel(4, new Point(5, 6)));
		model.setAnnotations(annoList);
		
		assertEquals(5, new ModelController(model).newAnnotationID());
		
		annoList.add(new AnnotationModel(5, new Point(0, 0)));
		model.setAnnotations(annoList);
		
		assertEquals(6, new ModelController(model).newAnnotationID());
	}
	
	@Test
	public void testGetAnnotation() {
		AppModel model = new AppModel();
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		AnnotationModel anno1 = new AnnotationModel(1, new Point(2, 3));
		AnnotationModel anno2 = new AnnotationModel(4, new Point(5, 6));
		
		annoList.add(anno1);
		annoList.add(anno2);
		model.setAnnotations(annoList);
		
		ModelController mc = new ModelController(model);
		
		assertEquals(anno1, mc.getAnnotation(0));
		assertEquals(anno2, mc.getAnnotation(1));
	}
	
	@Test
	public void testAddAnnotation() {
		AppModel model = new AppModel();
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		AnnotationModel anno1 = new AnnotationModel(1, new Point(2, 3));
		AnnotationModel anno2 = new AnnotationModel(4, new Point(5, 6));
		
		annoList.add(anno1);
		model.setAnnotations(annoList);
		
		ModelController mc = new ModelController(model);
		
		assertEquals(1, mc.numAnnotations());
		assertEquals(anno1, mc.getAnnotation(0));
		
		mc.addAnnotation(anno2);
		
		assertEquals(2, mc.numAnnotations());
		assertEquals(anno2, mc.getAnnotation(1));
	}
	
	@Test
	public void testSelectAnno() {
		AppModel model = new AppModel();
		AnnotationModel anno = new AnnotationModel(1, new Point(2, 3));
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		annoList.add(anno);
		
		model.setAnnotations(annoList);
		model.select(anno);
		
		JButton butt = new JButton();
		JTextArea ta = new JTextArea();
		
		ModelController mc = new ModelController(model);
		mc.selectAnnotation(anno, -1, butt, ta);
		
		assertEquals(anno.getID(), mc.model.getSelected().getID());
		assertEquals(Color.red, mc.model.getSelected().getColor());
		
		assertEquals(anno.getID(), mc.getAnnotation(0).getID());
		assertEquals(Color.red, mc.getAnnotation(0).getColor());
	}
	
	@Test
	public void testUpdateLoadModel() {
		AppModel model = new AppModel();
		model.setName("test");
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		AnnotationModel anno1 = new AnnotationModel(1, new Point(2, 3));
		AnnotationModel anno2 = new AnnotationModel(4, new Point(5, 6));
		
		annoList.add(anno1);
		model.setAnnotations(annoList);
		
		ModelController mc = new ModelController(model);
		mc.updateLoadedModels(model, true);
		
		assertEquals(1,model.getLoadedModels().get(0).getAnnotations().get(0).getID());
		
		mc.addAnnotation(anno2);
		mc.updateLoadedModels(model, true);
		
		assertEquals(1,model.getLoadedModels().get(0).getAnnotations().get(0).getID());
		assertEquals(4,model.getLoadedModels().get(0).getAnnotations().get(1).getID());
	}
}
