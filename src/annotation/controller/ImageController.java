package annotation.controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import annotation.boundary.ImagePanel;
import annotation.boundary.PopupWarn;
import annotation.model.AppModel;

public class ImageController {

	AppModel model;
	ImagePanel imgPanel;
	File image;

	public ImageController(AppModel model, ImagePanel imgPanel, File image) {
		this.model = model;
		this.imgPanel = imgPanel;
		this.image = image;
	}
	
	private Dimension scaleImageDim(int imgW, int imgH) {
		int newW;
		int newH;
		
		if (imgW > imgH) {
			newW = this.imgPanel.getWidth();
			
			double scale = (double) newW / (double) imgW;
			newH = (int)(imgH * scale);
		}
		else {
			newH = this.imgPanel.getHeight();
			
			double scale = (double) newH / (double) imgH;
			newW = (int)(imgW * scale);
		}
		
		return new Dimension(newW,  newH);
	}
	
	private String getFileExtension() {
		String fileName = this.image.getName();
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}
	
	public void setAppModelImage() {
		try {
			ArrayList<String> validTypes = new ArrayList<String>();
			validTypes.add("png");
			validTypes.add("jpg");
			validTypes.add("jpeg");
			validTypes.add("gif");
			
			if (validTypes.contains(this.getFileExtension())) {
				BufferedImage buffImg = ImageIO.read(this.image);
				
				Dimension newSize = this.scaleImageDim(buffImg.getWidth(), buffImg.getHeight());
				
				BufferedImage scaleBuffImg = new BufferedImage(newSize.width, newSize.height, BufferedImage.TYPE_INT_ARGB);
				
				Graphics2D g2 = scaleBuffImg.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2.drawImage(buffImg, 0, 0, newSize.width, newSize.height, null);
				g2.dispose();
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(scaleBuffImg, this.getFileExtension(), baos);
				this.model.setImage(this.image.getName(), baos.toByteArray());
			}
			else {
				new PopupWarn("ERROR: Invalid image type");
			}
		}
		catch (IOException e) {
			new PopupWarn("ERROR: IOException trying to load image");
		}
	}
}
