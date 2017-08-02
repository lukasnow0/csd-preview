package com.csdDataPreview.app.main;
	
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;
import com.csdDataPreview.PaintControl.DrawImage;
import com.csdDataPreview.PaintControl.RandomGenerator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Main extends Application {
	Stage window;
	TextField textPath, textX, textY, textSize;
	Label labelX, labelY, labelSize;
	Button buttonOpen, buttonPreview, buttonSave;
	Canvas canvas;
	GraphicsContext gc;
	String filePath;
	Mother[][] net;
	SortedMap<Integer, Color> colorPool;
	int imagePositionX = 0;
	int imagePositionY = 0;
	int size = 100;
	int cursorPositionX, cursorPositionY;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			window = primaryStage;
			window.setMaximized(true);
			textPath = new TextField();
			textPath.setPromptText("Open a CSD Simulation file");
			textPath.setPrefWidth(300);
			
			buttonOpen = new Button("Open");
			buttonOpen.setOnAction( e -> openFile());
			
			labelX = new Label("X:");
			
			textX = new TextField();
			textX.setText("0");
			
			labelY = new Label("Y:");
			
			textY = new TextField();
			textY.setText("0");
			
			labelSize = new Label();
			labelSize.setText("Net size:");
			
			textSize = new TextField();
			textSize.setText("100");
			
			buttonPreview = new Button("Preview");
			buttonPreview.setOnAction( e -> {
				if(net != null){
					showImage();
				} else {
					MessageWindow mw = new MessageWindow();
					mw.display("No file selected", "Open a file to preview.");
				}
			});
			
			buttonSave = new Button("Save data");
			buttonSave.setOnAction( e -> {
				if(net != null){
					buttonSave.setDisable(true);
					saveImageData();
					buttonSave.setDisable(false);
				} else {
					MessageWindow mw = new MessageWindow();
					mw.display("No file selected", "Open a file to preview befor saving data.");
				}
			});
			
			HBox hBox1 = new HBox();
			hBox1.setPadding(new Insets(10, 10, 10, 10));
			hBox1.setSpacing(10);
			hBox1.setAlignment(Pos.CENTER_LEFT);
			hBox1.getChildren().addAll(textPath, buttonOpen);
			
			HBox hBox2 = new HBox();
			hBox2.setPadding(new Insets(10, 10, 10, 10));
			hBox2.setSpacing(10);
			hBox2.setAlignment(Pos.CENTER_LEFT);
			hBox2.getChildren().addAll(labelX, textX, labelY, textY, labelSize, textSize, buttonPreview, buttonSave);
			
			canvas = new Canvas(600, 600);
			gc = canvas.getGraphicsContext2D();
			
			RandomGenerator rand = new RandomGenerator(1000);
			colorPool = rand.getColorPool();
			
			VBox root = new VBox();
			root.getChildren().addAll(hBox1, hBox2, canvas);
			root.setPadding(new Insets(10, 10, 10, 10));
			Scene scene = new Scene(root);
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void openFile() {
		FileChooser fc = new FileChooser();
		File file = null;
		try{
			file = fc.showOpenDialog(window);
			filePath = file.getAbsolutePath();
			textPath.setText(filePath);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		DataLoader dataLoader = new DataLoader(file);
		net = dataLoader.load();
	}
	
	private void showImage(){
		try {
			imagePositionX = Integer.parseInt(textX.getText());
			imagePositionY = Integer.parseInt(textY.getText());
			size = Integer.parseInt(textSize.getText());
		} catch (NumberFormatException e) {
			MessageWindow mw = new MessageWindow();
			mw.display("Wrong input", "X, Y and Net size need integer values.");
		}
		if(imagePositionX >= 0 && imagePositionY >= 0 && size > 9 && size < 101){
			setColors();
			DrawImage dimg = new DrawImage(net, gc, setRangeToDisplay(imagePositionX, imagePositionY, size));
			dimg.run();
		} else {
			MessageWindow mw = new MessageWindow();
			mw.display("Input our of range", "X and Y need positive values.\r\nNet size range is fram 10 to 100.");
		}
	};

	private void setColors() {
		int id;
		for(int i = 0; i < net.length; i++) {
			for(int j = 0; j < net.length; j++) {
				id = (int) net[i][j].getAllelOne().getAllelId();
				net[i][j].getAllelOne().setColor(colorPool.get(id));
				id = (int) net[i][j].getAllelTwo().getAllelId();
				net[i][j].getAllelTwo().setColor(colorPool.get(id));
			}
		}
		
	}
	
	private int[] setRangeToDisplay(int x, int y, int size) {
		int[] range = new int[4];
		range[0] = x;
		range[1] = y;
		range[2] = x + size;
		range[3] = y + size;
		
		if(size > net.length) {
			range[0] = 0;
			range[1] = 0;
			range[2] = net.length;
			range[3] = net.length;
			textSize.setText(String.valueOf(net.length));
			return range;
		}
		if(range[2] > net.length) {
			range[2] = net.length;
			range[0] = range[2] - size;
			textX.setText(String.valueOf(range[0]));
		}
		if(range[3] > net.length) {
			range[3] = net.length;
			range[1] = range[3] - size;
			textY.setText(String.valueOf(range[1]));
		}
		
		return range;
	}
	
	private void saveImageData() {
		String[] parts = filePath.split("\\\\");
		String s;
		
		StringBuilder sb = new StringBuilder();
		int k = 0;
		do {
			s = parts[k];
			sb.append(s);
			sb.append("\\");
			k++;
		} while (! s.contains("Output"));
		sb.append("\\Previews");
		String outputPath = sb.toString();
		
		s = parts[parts.length - 1];
		int beginIndex = s.indexOf('_');
		int endIndex = s.indexOf('.');
		int generation = Integer.parseInt(s.substring(beginIndex + 1, endIndex));
		
		int[] range = setRangeToDisplay(imagePositionX, imagePositionY, size);
		ArrayList<Mother> tempNet = new ArrayList<>();
		for(int i = range[0]; i < range[2]; i++) {
			for(int j = range[1]; j < range[3]; j++) {
				tempNet.add(net[i][j]);
			}
		}
		
		String data = Arrays.toString(tempNet.toArray());
		data = formatOutputLine(data, generation);
		
		String fileName = "X=" + imagePositionX + "_Y=" + imagePositionY + "_Generation";
		FileManager fm = new FileManager(outputPath, fileName, "txt", generation);
		fm.saveFile(data);
	}
	
	private String formatOutputLine(String str, int generation){
		str = str.replace("[", "");
		str = str.replace("]", "");
		str = str.replace(", ", "");
		String out = "Preview of generation: " + generation + "\r\nStart position:\r\n\tx = "
				+ imagePositionX + "\r\n\ty = " + imagePositionY + "\r\nNet:\r\n" + str;
		return out;
	}
}
