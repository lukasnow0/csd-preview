package com.csdDataPreview.app.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class FileManager {
	private String projectPath;
	private String fileName;
	private String fileType;
	int generation;
	
	public FileManager(String projectPath, String fileName, String fileType, int generation){
		this.projectPath = projectPath;
		this.fileType = fileType;
		this.fileName = fileName;
		this.generation = generation;
		File directory = new File(projectPath);
		if (! directory.exists()){
			directory.mkdirs();
		}
	}
	
	@SuppressWarnings("null")
	public boolean saveFile(String str){
		String temp = projectPath + "\\" + fileName + "_" + generation + "." + fileType;
		File file = new File(temp);
		try{
			if(! file.exists()){
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if( file.length() > 0){
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				pw.close();
				return false;
			} finally {
				pw.close();
			}
		}
		Writer fw = null;
		BufferedWriter bw = null;
		try {
			if(file.canWrite()){
				fw = new FileWriter(file, true);
				bw = new BufferedWriter(fw);
				bw.append(str);
				bw.flush();
				fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
		    try {
		        if(bw != null)
		            bw.close();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		    try {
		        if(fw != null)
		            fw.close();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
		return true;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}
	
}
