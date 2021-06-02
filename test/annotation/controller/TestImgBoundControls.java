package annotation.controller;

import static org.junit.Assert.assertNotEquals;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.junit.Test;
import org.junit.Assert;

import annotation.boundary.ImagePanel;
import annotation.model.AnnotationModel;
import annotation.model.AppModel;
import junit.framework.TestCase;

public class TestImgBoundControls extends TestCase {
	
	@Test
	public void testCreateBound() {
		AppModel model = new AppModel();
		JButton butt = new JButton();
		
		BoundController bc = new BoundController(model, butt);
		
		assertEquals(model, bc.model);
		assertEquals(butt, bc.butt);
	}
	
	@Test
	public void testUAFF() {
		AppModel model = new AppModel();
		JButton butt = new JButton();
		AnnotationModel anno = new AnnotationModel(1, new Point(2, 3));
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		annoList.add(anno);
		
		model.setAnnotations(annoList);
		model.select(anno);
		
		BoundController bc = new BoundController(model, butt);
		
		String testString = "this is a test";
		bc.updateAnnoFromField(testString);
		
		assertEquals(testString, model.getSelected().getAnnotation());
		assertEquals(anno, annoList.get(0));
		assertEquals(testString, annoList.get(0).getAnnotation());
	}
	
	@Test
	public void testUFFA() {
		AppModel model = new AppModel();
		JTextArea butt = new JTextArea();
		
		BoundController bc = new BoundController(model, butt);
		String testString = "this is a test";
		bc.updateFieldFromAnno(testString);
		
		assertEquals(testString, ((JTextArea) bc.butt).getText());
	}
	
	@Test
	public void testDeleteAnno() {
		AppModel model = new AppModel();
		AnnotationModel anno = new AnnotationModel(1, new Point(2, 3));
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		annoList.add(anno);
		
		model.setAnnotations(annoList);
		model.select(anno);
		
		JButton butt = new JButton();
		JTextArea ta = new JTextArea();
		ImagePanel ip = new ImagePanel(model);
		
		BoundController bc = new BoundController(model, butt);
		bc.deleteAnnotation(ip, ta);
		
		assertEquals(null, bc.model.getSelected());
		assertEquals(0, bc.model.getAnnotations().size());
	}
	
	@Test
	public void testLoadImg() {
		AppModel model = new AppModel();
		AnnotationModel anno = new AnnotationModel(1, new Point(2, 3));
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		annoList.add(anno);
		
		model.setAnnotations(annoList);
		model.select(anno);
		
		JButton butt = new JButton();
		JTextArea ta = new JTextArea();
		JLabel jl = new JLabel();
		ImagePanel ip = new ImagePanel(model);
		ip.setSize(100, 100);
		
		byte[] emptyByte = new byte[0];
		
		BoundController bc = new BoundController(model, butt);
		bc.loadImgButt(ip, jl, butt, ta, new File("./test/annotation/test_img.jpg"));
		
		assertEquals(null, bc.model.getSelected());
		assertEquals("test_img.jpg", bc.model.getName());
		assertEquals("test_img.jpg", jl.getText());
		
		assertNotEquals(emptyByte, bc.model.getImage());
		
		bc.loadImgButt(ip, jl, butt, ta, new File("./test/annotation/test_img2.png"));
		
		assertEquals(null, bc.model.getSelected());
		assertEquals("test_img2.png", bc.model.getName());
		assertEquals("test_img2.png", jl.getText());
		
		assertNotEquals(emptyByte, bc.model.getImage());
	}
	
	@Test
	public void testSaveLoadFile() {
		AppModel model = new AppModel();
		AnnotationModel anno = new AnnotationModel(1, new Point(2, 3));
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		annoList.add(anno);
		
		model.setAnnotations(annoList);
		model.select(anno);
		
		JButton butt = new JButton();
		JTextArea ta = new JTextArea();
		JLabel jl = new JLabel();
		ImagePanel ip = new ImagePanel(model);
		ip.setSize(100, 100);
		
		File file = new File("./test/annotation/test_img.jpg");
		
		BoundController bc = new BoundController(model, butt);
		bc.loadImgButt(ip, jl, butt, ta, file);
		bc.saveFileButt(ip, butt, ta, true);
		
		bc.model.resetModel();
		
		ModelController mc = new ModelController(bc.model);
		mc.loadSavedModels();
		mc.loadModel("test_img.jpg");
		
		
		assertEquals(null, bc.model.getSelected());
		assertEquals("test_img.jpg", bc.model.getName());
		assertEquals("test_img.jpg", jl.getText());
		
		byte[] emptyByte = new byte[0];
		assertNotEquals(emptyByte, bc.model.getImage());
	}
	
	@Test
	public void testFieldListener() {
		AppModel model = new AppModel();
		AnnotationModel anno = new AnnotationModel(1, new Point(2, 3));
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		annoList.add(anno);
		
		model.setAnnotations(annoList);
		model.select(anno);
		
		JButton butt = new JButton();
		JButton butt2 = new JButton();
		JTextArea ta = new JTextArea();
		JLabel jl = new JLabel();
		ImagePanel ip = new ImagePanel(model);
		ip.setSize(100, 100);
		
		File file = new File("./test/annotation/test_img.jpg");
		
		BoundController bc = new BoundController(model, ta);
		ta.getDocument().addDocumentListener(bc.annoFieldListener());
		ta.setText("this is a test");
		ta.setText("this is a test");
		
		assertEquals("this is a test", bc.model.getSelected().getAnnotation());
		
	}
	
	@Test
	public void testLoadListener() {
		AppModel model = new AppModel();
		AnnotationModel anno = new AnnotationModel(1, new Point(2, 3));
		
		ArrayList<AnnotationModel> annoList = new ArrayList<AnnotationModel>();
		annoList.add(anno);
		
		model.setAnnotations(annoList);
		model.select(anno);
		
		JButton butt = new JButton();
		JButton butt2 = new JButton();
		JTextArea ta = new JTextArea();
		JLabel jl = new JLabel();
		ImagePanel ip = new ImagePanel(model);
		ip.setSize(100, 100);
		
		File file = new File("./test/annotation/test_img.jpg");
		
		BoundController bc = new BoundController(model, butt);
		butt.addActionListener(bc.loadImgListener(ip, jl, butt2, ta, file));
		butt.doClick();
		
		assertEquals("test_img.jpg", bc.model.getName());
	}
	
}
