package annotation.boundary;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import annotation.controller.ModelController;
import annotation.model.AnnotationModel;
import annotation.model.AppModel;

public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	AppModel model;
	
	public ImagePanel(AppModel model) {
		this.model = model;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		BufferedImage buffImg;
		try {
			if (!new ModelController(this.model).isEmpty()) {
				buffImg = ImageIO.read(new ByteArrayInputStream(this.model.getImage()));
				
				int offsetX = (this.getWidth() - buffImg.getWidth()) / 2;
				int offsetY = (this.getHeight() - buffImg.getHeight()) / 2;
			
				g.drawImage(buffImg, offsetX, offsetY, null);
				
				ArrayList<AnnotationModel> annos = model.getAnnotations();
				
				for (int i = 0; i < annos.size(); i++) {
					annos.get(i).drawRect(g);
				}
			}
		
		} catch (IOException e) {
			new PopupWarn("ERROR: IOException trying to draw image");
		}
	}
	
	public void repaint(boolean reset) {
		if (reset) {
			this.removeAll();
		}
		
		super.repaint();
	}

}
