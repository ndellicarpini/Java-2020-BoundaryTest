package annotation.controller;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.junit.Test;

import annotation.boundary.ImagePanel;
import annotation.model.AnnotationModel;
import annotation.model.AppModel;
import junit.framework.TestCase;

public class TestMouseControl extends TestCase {

	@Test
	public void testCreate() {
		AppModel model = new AppModel();
		ImagePanel panel = new ImagePanel(model);
		JButton butt = new JButton();
		JTextArea ta = new JTextArea();
		
		MouseController mouse = new MouseController(model, panel, butt, ta);
		
		assertEquals(model, mouse.model);
		assertEquals(panel, mouse.imgPanel);
		assertEquals(butt, mouse.delAnno);
		assertEquals(ta, mouse.fieldAnno);
		
		assertEquals(false, mouse.mouseBool);
		assertEquals(false, mouse.mouseDrag);
		assertEquals(model, mouse.mc.model);
	}
	
	@Test
	public void testSet() {
		AppModel model = new AppModel();
		ImagePanel panel = new ImagePanel(model);
		JButton butt = new JButton();
		JTextArea ta = new JTextArea();
		
		MouseController mouse = new MouseController(model, panel, butt, ta);
		
		assertEquals(false, mouse.mouseBool);
		
		mouse.setMouseBool(true);
		
		assertEquals(true, mouse.mouseBool);
	}
	
	@Test
	public void testClick() {
		AppModel model = new AppModel();
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		AnnotationModel anno1 = new AnnotationModel(1, new Point(0, 0));
		anno1.setSize(new Point(5, 5));
		AnnotationModel anno2 = new AnnotationModel(2, new Point(10, 10));
		anno2.setSize(new Point(20, 20));
		
		annoList.add(anno1);
		annoList.add(anno2);
		
		model.setAnnotations(annoList);
		
		ImagePanel ip = new ImagePanel(model);
		JButton butt = new JButton();
		JTextArea ta = new JTextArea();
		JLabel jl = new JLabel();
		
		ip.setSize(100, 100);
		
		BoundController bc = new BoundController(model, butt);
		bc.loadImgButt(ip, jl, butt, ta, new File("./test/annotation/test_img.jpg"));
		
		MouseController mouse = new MouseController(model, ip, butt, ta);
		mouse.setMouseBool(true);
		mouse.clickFunc(new Point(1, 1));
		
		assertEquals(Color.red, mouse.model.getSelected().getColor());
		assertEquals(anno1.getID(), mouse.model.getSelected().getID());
		
		mouse.clickFunc(new Point(50, 50));
		
		assertEquals(null, mouse.model.getSelected());
	}
	
	@Test
	public void testDrag() {
		AppModel model = new AppModel();
		
		ImagePanel ip = new ImagePanel(model);
		JButton butt = new JButton();
		JTextArea ta = new JTextArea();
		JLabel jl = new JLabel();
		
		ip.setSize(100, 100);
		
		BoundController bc = new BoundController(model, butt);
		bc.loadImgButt(ip, jl, butt, ta, new File("./test/annotation/test_img.jpg"));
		
		MouseController mouse = new MouseController(model, ip, butt, ta);
		mouse.setMouseBool(true);
		
		mouse.dragFunc(new Point(1, 1));
		mouse.dragFunc(new Point(5, 5));
		
		assertEquals(true, mouse.mouseDrag);
		assertEquals(1, mouse.initialPos.x);
		assertEquals(1, mouse.initialPos.y);
		assertEquals(5, mouse.finalPos.x);
		assertEquals(5, mouse.finalPos.y);
		
		assertEquals(1, mouse.mc.numAnnotations());
		assertEquals(1, mouse.mc.getAnnotation(0).getX());
		assertEquals(1, mouse.mc.getAnnotation(0).getY());
		assertEquals(5, mouse.mc.getAnnotation(0).getX2());
		assertEquals(5, mouse.mc.getAnnotation(0).getY2());
		assertEquals(Color.gray, mouse.mc.getAnnotation(0).getColor());
	}
	
	@Test
	public void testRelease() {
		AppModel model = new AppModel();
		
		ImagePanel ip = new ImagePanel(model);
		JButton butt = new JButton();
		JTextArea ta = new JTextArea();
		JLabel jl = new JLabel();
		
		ip.setSize(100, 100);
		
		BoundController bc = new BoundController(model, butt);
		bc.loadImgButt(ip, jl, butt, ta, new File("./test/annotation/test_img.jpg"));
		
		MouseController mouse = new MouseController(model, ip, butt, ta);
		mouse.setMouseBool(true);
		
		mouse.dragFunc(new Point(1, 1));
		mouse.dragFunc(new Point(5, 5));
		
		assertEquals(true, mouse.mouseDrag);
		
		mouse.releaseFunc();
		
		assertEquals(false, mouse.mouseDrag);
		
		assertEquals(1, mouse.mc.numAnnotations());
		assertEquals(Color.red, mouse.mc.getAnnotation(0).getColor());
		assertEquals(mouse.mc.getAnnotation(0).getID(), mouse.model.getSelected().getID());
	}
}
