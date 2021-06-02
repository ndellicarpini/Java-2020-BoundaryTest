package annotation.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;

public class AnnotationModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int id;
	int initialX;
	int initialY;
	int finalX;
	int finalY;
	Color color;
	String annotation;
	
	public AnnotationModel(int id, Point initialPos) {
		this.id = id;
		this.initialX = initialPos.x;
		this.initialY = initialPos.y;
		this.color = Color.gray;
	}
	
	public int getID() {
		return this.id;
	}
	
	public int getX() {
		return Math.min(this.initialX, this.finalX);
	}
	
	public int getY() {
		return Math.min(this.initialY, this.finalY);
	}
	
	public int getX2() {
		return Math.max(this.initialX, this.finalX);
	}
	
	public int getY2() {
		return Math.max(this.initialY, this.finalY);
	}
	
	public void setAnnotation(String text) {
		this.annotation = text;
	}
	
	public String getAnnotation() {
		return this.annotation;
	}
	
	public void setSize(Point finalPos) {
		this.finalX = finalPos.x;
		this.finalY = finalPos.y;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void drawRect(Graphics g) {
		int posX = Math.min(this.initialX, this.finalX);
		int posY = Math.min(this.initialY, this.finalY);
		int sizeX = Math.abs(this.initialX - this.finalX);
		int sizeY = Math.abs(this.initialY - this.finalY);
		
		Graphics2D g2d = (Graphics2D) g;
	
		g2d.setStroke(new BasicStroke(2));
		
		g2d.setColor(this.color);
		g2d.drawRect(posX, posY, sizeX, sizeY);
	}
}
