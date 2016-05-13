package main;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmBox {
	static boolean choice = false;
	static Stage window;
	
	public static void close(Stage window) {
		window.close();
	}
	
	private static HBox createButtons() {
		HBox buttons = new HBox(20);
		Button yes = new Button("Yes");
		Button no = new Button("No");
		yes.setOnAction(e -> {
			choice = true;
			close(window);
		});
		no.setOnAction(e -> {
			choice = false;
			close(window);
		});
		buttons.getChildren().addAll(yes,no);
		buttons.setAlignment(Pos.CENTER);
		return buttons;
	}

	private static VBox createLayout(String message) {
		Label lb = new Label(message);
		VBox layout = new VBox(20, lb, createButtons());
		layout.setAlignment(Pos.CENTER);
		return layout;
	}

	private static Stage createWindow(String title) {
		Stage window = new Stage();
		window.setTitle(title);
		window.setWidth(250);
		window.setOnCloseRequest(e -> {
			e.consume();
			close(window);
		});
		window.setAlwaysOnTop(true);
		return window;
	}

	public static boolean display(String title, String message) {
		window = createWindow(title);
		Scene scene = new Scene(createLayout(message));
		window.setScene(scene);
		window.showAndWait();
		return choice;
	}
}
