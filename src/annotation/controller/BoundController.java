package annotation.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.JTextArea;

import annotation.boundary.ImagePanel;
import annotation.boundary.PopupWarn;
import annotation.model.AnnotationModel;
import annotation.model.AppModel;

public class BoundController {
	AppModel model;
	JComponent butt;
	
	public BoundController(AppModel model, JComponent butt) {
		this.model = model;
		this.butt = butt;
	}
	
	public void updateAnnoFromField(String text) {
		AnnotationModel selectedAnno = this.model.getSelected();
		if (selectedAnno != null) {
			selectedAnno.setAnnotation(text);
						
			this.model.select(selectedAnno);
			new ModelController(this.model).updateAnnotation(selectedAnno);
		}		
	}
	
	public void updateFieldFromAnno(String text) {
		((JTextArea) this.butt).setText(text);
	}
	
	public void deleteAnnotation(ImagePanel imgPanel, JTextArea fieldAnno) {
		ModelController mc = new ModelController(this.model);
		AnnotationModel selectedAnno = this.model.getSelected();
		ArrayList<AnnotationModel> mcAnnos = this.model.getAnnotations();
		mcAnnos.remove(mc.annotationFromID(selectedAnno.getID()));
		
		mc.deselectAnnotation((JButton) this.butt, fieldAnno, true);
		
		this.model.setAnnotations(mcAnnos);
		
		imgPanel.repaint();
	}
	

	public void loadImgButt(ImagePanel imgPanel, JLabel imageName, JButton delAnno, JTextArea fieldAnno, File image) {
		ModelController mc = new ModelController(this.model);
		
		mc.deselectAnnotation(delAnno, fieldAnno, true);
			
		if (!mc.isEmpty()) {
			mc.updateLoadedModels(this.model, true);
			this.model.resetModel();
		}
		
		ImageController imgControl = new ImageController(this.model, imgPanel, image);
		imgControl.setAppModelImage();
		mc.loadModel(this.model.getName());
		
		imgPanel.repaint(true);
		
		imageName.setText(this.model.getName());
	}
	
	public void saveFileButt(ImagePanel imgPanel, JButton delAnno, JTextArea fieldAnno, boolean popup) {
		ModelController mc = new ModelController(this.model);
		
		mc.deselectAnnotation(delAnno, fieldAnno, true);
		
		if (!mc.isEmpty()) {
			mc.saveModel(popup);
			
			imgPanel.repaint();
		} 
		else {
			new PopupWarn("ERROR: No data to save");
		}
	}
	
	public DocumentListener annoFieldListener() {
		BoundController self = this;
		return new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					self.updateAnnoFromField(e.getDocument().getText(0, e.getDocument().getLength()));
				
				} catch (BadLocationException ex) {
					new PopupWarn("ERROR: Lost JTextArea");
					System.out.println(ex);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					if (model.getCheat()) {
						model.setCheat(false);
					}
					else {
						self.updateAnnoFromField(e.getDocument().getText(0, e.getDocument().getLength()));
					}
					
				} catch (BadLocationException ex) {
					new PopupWarn("ERROR: Lost JTextArea");
					System.out.println(ex);
				}
			}
		};
	}
	
	public ActionListener annoDelListener(ImagePanel image_panel, JTextArea annotation_field) {
		BoundController self = this;
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				self.deleteAnnotation(image_panel, annotation_field);
			}
		};
	}
	
	public ActionListener loadImgListener(ImagePanel image_panel, JLabel img_name_txt, JButton del_annotation_butt, JTextArea annotation_field, File file) {
		BoundController self = this;
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (file == null) {
					JFileChooser fileBrowser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("IMAGE files", "png", "jpg", "jpeg", "gif");
					fileBrowser.setFileFilter(filter);
					int fileResult = fileBrowser.showOpenDialog(self.butt);
					
					if (fileResult == JFileChooser.APPROVE_OPTION) {
						self.loadImgButt(image_panel, img_name_txt, del_annotation_butt, annotation_field, fileBrowser.getSelectedFile());
					}
				}
				else {
					self.loadImgButt(image_panel, img_name_txt, del_annotation_butt, annotation_field, file);
				}
			}
		};
	}
	
	public ActionListener saveModelListener(ImagePanel image_panel, JButton del_annotation_butt, JTextArea annotation_field) {
		BoundController self = this;
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				self.saveFileButt(image_panel, del_annotation_butt, annotation_field, true);
			}
		};
	}
	
	public MouseAdapter mouseListener(ImagePanel image_panel, JButton del_annotation_butt, JTextArea annotation_field) {
		BoundController self = this;
		MouseController mouseControl = new MouseController(this.model, image_panel, del_annotation_butt, annotation_field);
		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseControl.setMouseBool(true);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				mouseControl.setMouseBool(false);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseControl.clickFunc(e.getPoint());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {		
				mouseControl.dragFunc(e.getPoint());
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				mouseControl.releaseFunc();
			}
		};
	}
	
//	public void loadFileButt(ImagePanel panel, JLabel imageName, JButton delAnno, JTextArea fieldAnno, File file) {
//		ModelController mc = new ModelController(this.model);
//		
//		mc.deselectAnnotation(delAnno, fieldAnno, true);
//		
//		if (!mc.isEmpty()) {
//			this.model.resetModel();
//		}
//		
//		mc.loadModel(file);
//		
//		panel.repaint(true);
//		
//		imageName.setText(this.model.getName());
//	}
	
}
