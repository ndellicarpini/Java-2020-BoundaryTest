package annotation.boundary;

import javax.swing.JButton;
import javax.swing.JTextArea;

import org.junit.Test;

import annotation.model.AppModel;
import junit.framework.TestCase;

public class TestPopup extends TestCase {

	@Test
	public void testChoice() {
		AppModel model = new AppModel();
		App app = new App(model);
		ImagePanel ip = new ImagePanel(model);
		JButton jb = new JButton();
		JTextArea ta = new JTextArea();
		String text = "this is a test";
		
		PopupChoice test = new PopupChoice(model, app, ip, jb, ta, text);
		
		assertEquals(text, test.text);
	}
}
