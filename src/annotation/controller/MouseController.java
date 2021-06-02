package annotation.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JTextArea;

import annotation.boundary.ImagePanel;
import annotation.model.AnnotationModel;
import annotation.model.AppModel;

public class MouseController {
	AppModel model;
	ModelController mc;
	AnnotationModel currAnno;
	
	ImagePanel imgPanel;
	JButton delAnno;
	JTextArea fieldAnno;
	
	Point initialPos;
	Point finalPos;
	
	boolean mouseBool = false;
	boolean mouseDrag = false;
	
	public MouseController(AppModel model, ImagePanel imgPanel, JButton delAnno, JTextArea fieldAnno) {
		this.model = model;
		this.imgPanel = imgPanel;
		this.delAnno = delAnno;
		this.fieldAnno = fieldAnno;
		
		this.mc = new ModelController(model);
	}
	
	public void clickFunc(Point mouseSpot) {	
		if (this.mouseBool && !this.mc.isEmpty()) {
			AnnotationModel checkAnno;
			
			for (int i = (this.mc.numAnnotations() - 1); i >= 0; i--) {
				checkAnno = this.mc.getAnnotation(i);
				
				if ((mouseSpot.x >= checkAnno.getX()) && (mouseSpot.y >= checkAnno.getY())
						&& (mouseSpot.x <= checkAnno.getX2()) && (mouseSpot.y <= checkAnno.getY2())) {
					
					this.mc.selectAnnotation(checkAnno, i, this.delAnno, this.fieldAnno);
					
					this.imgPanel.repaint();
					
					return;
				}
			}
			
			this.mc.deselectAnnotation(this.delAnno, this.fieldAnno, true);
			
			this.imgPanel.repaint();
		}
	}
	
	public void dragFunc(Point mouseSpot) {
		if (this.mouseBool && !this.mc.isEmpty()) {
			if (!this.mouseDrag) {
				this.mc.deselectAnnotation(this.delAnno, this.fieldAnno, true);
				
				this.mouseDrag = true;
				this.initialPos = mouseSpot;
				
				this.currAnno = new AnnotationModel(this.mc.newAnnotationID(), this.initialPos);
				this.mc.addAnnotation(this.currAnno);
				
			}
			
			this.finalPos = mouseSpot;
			this.currAnno.setSize(this.finalPos);
			this.mc.updateAnnotation(this.currAnno);
			
			this.imgPanel.repaint(); 
			
		}
	}
	
	public void releaseFunc() {
		if (this.mouseDrag) {
			this.mouseDrag = false;
			if (!this.mc.isEmpty()) {
				this.currAnno.setColor(Color.black);
				this.mc.updateAnnotation(this.currAnno);
				
				this.mc.selectAnnotation(this.currAnno, -1, this.delAnno, this.fieldAnno);
				
				this.imgPanel.repaint();
			}
		}
	}
	
	public void setMouseBool(boolean bool) {
		this.mouseBool = bool;
	}
	
}
