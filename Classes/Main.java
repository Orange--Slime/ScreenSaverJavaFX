package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	Stage window;
	ScreenSaver screenSaver = new ScreenSaver(1366, 707, new Color(1f, .5f, 0f, 1f));
	
	ScreenSaverMenuBar menuBar;
	
	private void closeProgram(Stage mainStage) {
		Boolean answer = false;
		for(int i = 0; i < 1; i++) {
			String really = "";
			for(int k = 0; k < i; k++) {
				really += " really";
			}
			answer = ConfirmBox.display("Popup", ("Are you" + really + " sure?"));
			if(answer == false) 
				break;
		}
		if(answer)
			mainStage.close();
	}
	
	private BorderPane createLayout() {
		BorderPane layout = new BorderPane();
		layout.setTop(menuBar);
		layout.setCenter(screenSaver);
		return layout;
	}
	
	private Stage createWindow(Stage mainStage) {
		mainStage.setTitle("Screen Saver");
		mainStage.setOnCloseRequest(e -> {
			e.consume();
			closeProgram(mainStage);
		});
		return mainStage;
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		window = createWindow(mainStage);
		menuBar = new ScreenSaverMenuBar(screenSaver, window);
		BorderPane layout = createLayout();
		Scene scene = new Scene(layout, screenSaver.getWidth()-2, screenSaver.getHeight()-100);
		window.setScene(scene);
		window.show();
		window.fullScreenProperty().addListener(e -> menuBar.setVisible(!window.isFullScreen()));
	}
}