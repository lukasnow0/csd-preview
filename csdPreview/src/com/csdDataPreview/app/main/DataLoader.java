package com.csdDataPreview.app.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DataLoader {
	
	File file;
	
	public DataLoader(File file){
		this.file = file;
	}
	
	public Mother[][] load() {
		String line;
		ArrayList<Mother> m = new ArrayList<>();
		Mother[][] net;
		FileReader fr = null;
		BufferedReader br = null;
		try{
			if(file.exists()) {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				while((line = br.readLine()) != null){
					if(line.startsWith("G") || line.startsWith("N") || line.isEmpty()) {
						continue;
					}
					if(line.contains("null")) {
						String parts[] = line.split(";");
						m.add(new Mother(-1, new Allel(-1), new Allel(-1), -1, Long.parseLong(parts[4]), Long.parseLong(parts[5])));
					} else {
						long data[]  = splitLine(line);
						m.add(new Mother(data[0], new Allel(data[1]), new Allel(data[2]), data[3], data[4], data[5]));
					}
				}
				br.close();
				fr.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(br != null){					
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(! m.isEmpty()) {
			int size = (int) Math.sqrt((double) m.size());
			net = new Mother[size][size];
			Iterator<Mother> iterator = m.iterator();
			while(iterator.hasNext()) {
				Mother temp = iterator.next();
				net[(int) temp.getX()][(int) temp.getY()] = temp;
			}
			return net;
		}
		return null;
	}
	
	private long[] splitLine(String line) {
		String parts[] = line.split(";");
		long data[]  = new long[parts.length];
		for(int i = 0; i < parts.length; i++){
			if(parts[i] != "null"){
				data[i] = Long.parseLong(parts[i]);
			} else {
				data[i] = -1;
			}
		}
		return data;
	}

}
