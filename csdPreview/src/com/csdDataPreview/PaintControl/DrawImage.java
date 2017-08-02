package com.csdDataPreview.PaintControl;

import java.util.ArrayList;
import java.util.Iterator;

import com.csdDataPreview.app.main.Mother;

import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawImage extends Task<Void>{
	
	private final Mother[][] net;
	private final GraphicsContext gc;
	private final int[] range;
	
	public DrawImage(Mother[][] net, GraphicsContext gc, int[] range) {
		this.net = net;
		this.gc = gc;
		this.range = range;
	}

	@Override
	protected Void call() throws Exception {
		// TODO Auto-generated method stub
		gc.clearRect(0, 0, gc.getCanvas().getWidth(),  gc.getCanvas().getHeight());
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		ArrayList<Point> pointList = new ArrayList<>();

		Color tempC1;
		Color tempC2;
		
		for(int i = range[0]; i < range[2]; i++) {
			for(int j = range[1]; j < range[3]; j++) {
				tempC1 = net[i][j].getAllelOne().getColor();
				tempC2 = net[i][j].getAllelTwo().getColor();
				
				if(i == 0 && j == 0) {
					pointList.add(new Point(0, 0, tempC1));
					pointList.add(new Point(3, 0, tempC2));
				} else {
					pointList.add(new Point((i - range[0]) * 6, (j - range[1]) * 6, tempC1));
					pointList.add(new Point(((i - range[0]) * 6) + 3, (j - range[1]) * 6, tempC2));
				}
			}
		}
		Iterator<Point> iterator = pointList.listIterator();
		while(iterator.hasNext()) {
			iterator.next().drawPoint(gc);
		}
		try {
            Thread.sleep(100);
        } catch (InterruptedException interrupted) {
            if (isCancelled()) {
                updateMessage("Cancelled");
                return null;
            }
        }
		return null;
	}

}
