package com.csdDataPreview.PaintControl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Point{
	
	private int x, y;
	private Color color;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(int x, int y, Color color){
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public void drawPoint(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(x, y, 3, 6);
	}	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "x: " + x + ", y: " + y;
	}

}
