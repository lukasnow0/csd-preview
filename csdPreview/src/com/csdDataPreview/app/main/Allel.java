package com.csdDataPreview.app.main;

import javafx.scene.paint.Color;


public class Allel{
	
	private long allelId;
	private Color color;

	public Allel(long id){
		this.allelId = id;
	}
	
	public Allel(long id, Color c){
		this.allelId = id;
		this.color = c;
	}

	public long getAllelId() {
		return allelId;
	}

	public void setAllelId(int allelId) {
		this.allelId = allelId;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "" + allelId;
	}
}
