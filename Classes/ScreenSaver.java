package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ScreenSaver extends Pane {
	private int x = y = 0, y, xI = yI = 1, yI, xLim[] = {1900, 1900}, yLim[] = {999, 999},  coords[][] = new int[xLim[0]+1][yLim[0]+1];
	private Color[] colors = {new Color(0.0f,0.0f,1.0f,1.0f),
			new Color(0.0f,0.5f,0.5f,1.0f),
			new Color(0.0f,1.0f,0.0f,1.0f),
			new Color(0.0f,0.0f,0.0f,1.0f),
			new Color(0.5f,0.5f,1.0f,1.0f),
			new Color(0.55f,0.33f,0.1f,1.0f),
			new Color(0.9f,0.45f,0.9f,1.0f),
			new Color(1.0f,0.0f,0.0f,1.0f),
			new Color(1.0f,0.5f,0.0f,1.0f),
			new Color(1.0f,1.0f,1.0f,1.0f)};
	Timeline time;
	private Canvas canvas;
	private GraphicsContext context;
	private PixelWriter g;
	private long startTime = System.currentTimeMillis();
	private boolean playing = false;
	
	public ScreenSaver() {
		super();
		setMinSize(xLim[1], yLim[1]);
		canvas = new Canvas(xLim[1],yLim[1]);
		context = canvas.getGraphicsContext2D();
		g = context.getPixelWriter();
		getChildren().add(canvas);
		createAnimation();
	}
	
	public ScreenSaver(int width, int height) {
		super();
		setMinSize(width, height);
		canvas = new Canvas(xLim[1],yLim[1]);
		context = canvas.getGraphicsContext2D();
		g = context.getPixelWriter();
		getChildren().add(canvas);
		createAnimation();
	}
	
	public ScreenSaver(int width, int height, Color temp) {
		super();
		setMinSize(width, height);
		canvas = new Canvas(xLim[1],yLim[1]);
		context = canvas.getGraphicsContext2D();
		context.setFill(temp);
		context.fillRect(0, 0, width, height);
		g = context.getPixelWriter();
		getChildren().add(canvas);
		createAnimation();
	}
	
	private void createAnimation() {
		time = new Timeline(60);
		time.setCycleCount(Animation.INDEFINITE);
		KeyFrame keyframe = new KeyFrame(Duration.millis(.5), e -> move());
		time.setRate(2);
		time.getKeyFrames().add(keyframe);
	}
	
	private void drawScreen() {
		for(int i = 0; i <= xLim[0]; i++) {
			for(int k = 0; k <= yLim[0]; k++) {
				g.setColor(i, k, colors[coords[i][k]%colors.length]);
			}
		}
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public GraphicsContext getGraphicsContext() {
		return context;
	}
	
	public PixelWriter getPixelWriter() {
		return g;	
	}
	
	public long getStartTime() {
		return startTime;
	}

	public Timeline getTimeline() {
		return time;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public void load() {
		try {
			x = (int) readField("x");
			y = (int) readField("y");
			xI = (int) readField("xI");
			yI = (int) readField("yI");
			xLim = (int[]) readField("xLim");
			yLim = (int[]) readField("yLim");
			coords = (int[][]) readField("coords");
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		drawScreen();
		playOneFrame();
	}

	private void move() {
		g.setColor(x, y, colors[coords[x][y]++%colors.length]);
		if(x != xLim[1] && y != yLim[1]) {
			x += xI;
			y += yI;
			move();
		}
		if(x == xLim[1]) {
			xI *= -1;
			xLim[1] = xLim[0] - xLim[1];
		}
		if(y == yLim[1]) {
			yI *= -1;
			yLim[1] = yLim[0] - yLim[1];
		}
	}
	
	public void playOneFrame() {
		time.stop();
		time.setCycleCount(1);
		time.play();
		playing = false;
		time.setCycleCount(Animation.INDEFINITE);
	}
	
	private Object readField(String fieldName) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(new File("saveData/ScreenSaver-" + fieldName + ".dat"));
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object value = ois.readObject();
		ois.close();
		return value;
	    }
	
	public void reset(int width, int height) {
		x = 0;
		y = 0;
		xI = 1;
		yI = 1;
		xLim[0] = xLim[1] = width;
		yLim[0] = yLim[1] = height;
		Arrays.fill(xLim, width);
		Arrays.fill(yLim, height);
		coords = new int[xLim[0]+1][yLim[0]+1];
		if(g != null)
			drawScreen();
	}
	
	public void save() {
		try {
			saveField("x", x);
			saveField("y", y);
			saveField("xI", xI);
			saveField("yI", yI);
			saveField("xLim", xLim);
			saveField("yLim", yLim);
			saveField("coords", coords);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveField(String fieldName, Object fieldValue) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File("saveData/ScreenSaver-" + fieldName + ".dat"));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(fieldValue);
		oos.close();
	}

	public void setMinSize(int width, int height) {
		reset(width, height);
		super.setMinSize(xLim[1], yLim[1]);
		super.setWidth(width);
		super.setHeight(height);
	}
	
	public void toggleAnimation() {
		if(playing)
			time.pause();
		else
			time.play();
		playing = !playing;
	}
	
	public String toString() {
		String temp = "";
		temp += "x:\t\t" + x;
		temp += "\ny:\t\t" + y;
		temp += "\nxI:\t\t" + xI;
		temp += "\nyI:\t\t" + yI;
		temp += "\nxLim:\t" + Arrays.toString(xLim);
		temp += "\nyLim:\t" + Arrays.toString(yLim);
		return temp;
	}
}
