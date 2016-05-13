package main;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class ScreenSaverMenuBar extends MenuBar {
	ScreenSaver screenSaver;
	private Menu file;
	private Menu view;
	private Menu help;
	Stage window;

	public ScreenSaverMenuBar() {
		super();
		createMenuBar();
		screenSaver = new ScreenSaver();
		super.getMenus().addAll(file, view, help);
	}
	
	public ScreenSaverMenuBar(ScreenSaver ss, Stage win) {
		super();
		createMenuBar();
		screenSaver = ss;
		window = win;
		super.getMenus().addAll(file, view, help);
	}
	
	private Menu createDebug() {
		Menu temp = new Menu("Debug");
		
		MenuItem allVariables = new MenuItem("All Variables");
		allVariables.setOnAction(e -> AlertBox.display("All Variables", screenSaver.toString()));
		
		temp.getItems().addAll(allVariables);
		return temp;
	}

	private Menu createFile() {
		Menu temp = new Menu("File");
		
		MenuItem save = new MenuItem("Save");
		save.setOnAction(e -> screenSaver.save());
		save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		
		MenuItem load = new MenuItem("Load");
		load.setOnAction(e -> screenSaver.load());
		load.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		
		MenuItem quickClose = new MenuItem("Quick Close");
		quickClose.setOnAction(e -> window.close());
		quickClose.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
		quickClose.setVisible(false);
		
		temp.getItems().addAll(save, load, quickClose);
		return temp;
	}

	private Menu createHelp() {
		Menu temp = new Menu("Help");
		
		MenuItem helpBox = new MenuItem("Help");
		helpBox.setOnAction(e -> AlertBox.display("Help", "This is a Screen Saver Demo.\nThis Program is nothing more than a test of its author's JavaFX abilities."));
		
		Menu debug = createDebug();
		
		
		temp.getItems().addAll(helpBox, debug);
		return temp;
	}
	
	private void createMenuBar() {
		file = createFile();
		view = createView();
		help = createHelp();
	}
	
	private Menu createView() {
		Menu temp = new Menu("View");
		
		MenuItem pausePlay = new MenuItem("Toggle Pause/Play");
		pausePlay.setOnAction(e -> screenSaver.toggleAnimation());
		pausePlay.setAccelerator(new KeyCodeCombination(KeyCode.K, KeyCombination.CONTROL_DOWN));
		
		MenuItem nextFrame = new MenuItem("Next Frame");
		nextFrame.setOnAction(e -> screenSaver.playOneFrame());
		nextFrame.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
		
		MenuItem reset = new MenuItem("Reset");
		reset.setOnAction(e -> screenSaver.reset((int) (window.getWidth()), (int) (window.getHeight())));
		reset.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
		
		MenuItem fullScreen = new MenuItem("Fullscreen");
		fullScreen.setOnAction(e -> window.setFullScreen(!window.isFullScreen()));
		fullScreen.setAccelerator(new KeyCodeCombination(KeyCode.F11));
		fullScreen.setVisible(false);
		
		temp.getItems().addAll(pausePlay, nextFrame, reset, fullScreen);
		return temp;
	}
}
