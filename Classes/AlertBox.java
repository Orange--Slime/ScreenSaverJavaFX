package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertBox {
	static Stage window = new Stage();
	
	private static VBox createVBox(String message) {
		VBox layout = new VBox(10);
		Label label= new Label(message);
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> window.close());
		label.setWrapText(true);
		label.setAlignment(Pos.CENTER);
		
		layout.getChildren().addAll(label, closeButton);
		layout.setMinSize(layout.getWidth()+50, 100);
		layout.setAlignment(Pos.CENTER);
		return layout;
	}

	public static void display(String title, String message) {
		window.setTitle(title);
		window.setWidth(250);
		
		BorderPane root = new BorderPane(createVBox(message));
		BorderPane.setMargin(root.getChildren().get(0), new Insets(10,10,((Region) root.getChildren().get(0)).getWidth()+10, ((Region) root.getChildren().get(0)).getHeight()+10));
		
		Scene scene = new Scene(root);
		window.setScene(scene);
		window.setAlwaysOnTop(true);
		window.showAndWait();
	}
}
