package com.csdDataPreview.PaintControl;


import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.paint.Color;

public class RandomGenerator {
	
	private SortedMap<Integer, Color> colorPool = new TreeMap<>();
	
	public RandomGenerator(int allelNum){
		for(int i = 0; i < allelNum; i++){
			double r = ThreadLocalRandom.current().nextDouble(1);
			double b = ThreadLocalRandom.current().nextDouble(1);
			double g = ThreadLocalRandom.current().nextDouble(1);
			double o = ThreadLocalRandom.current().nextDouble(1);
			colorPool.put(i, new Color(r, g, b, o));
		}
		colorPool.put(-1, Color.BLACK);
	}
	
	public SortedMap<Integer, Color> getColorPool() {
		return colorPool;
	}
}
