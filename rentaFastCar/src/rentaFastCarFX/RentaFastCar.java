 package rentaFastCarFX;

import java.net.URI;
import java.nio.file.Paths;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rentaFastCarDB.Database;

public class RentaFastCar extends Application{

	static {
//		try {
//			Database.dropTable();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		try {
//			Database.createTable();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			Database.insertCar();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
	//Hauptfenster
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10));
		
		//Bild
		URI uri = Paths.get("C:\\Users\\Zitta\\Pictures\\Camera Roll\\Cover.jpg").toUri();
		ImageView imageView = new ImageView(uri.toString());
		Label labelCover = new Label();
		labelCover.setGraphic(imageView);
		imageView.setFitWidth(900);
		imageView.setFitHeight(500);

		
		//Buttons
		Button btnKunde = new Button("KUNDEN VERWALTEN");
		btnKunde.setMinSize(300, 100);
		btnKunde.setCenterShape(true);
	
		btnKunde.setOnAction(custd ->{
			new CustomerDialog().showAndWait();
			});
		
		Button btnFuhrpark = new Button("FUHRPARK");
		btnFuhrpark.setMinSize(300, 100);
		btnFuhrpark.setCenterShape(true);
		
		btnFuhrpark.setOnAction(card ->{
			new CarDialog().showAndWait();
			});
		
		Button btnMiete = new Button("MIETE");
		btnMiete.setMinSize(300, 100);
		btnMiete.setCenterShape(true);
		
		btnMiete.setOnAction(bookd ->{
			new BookingDialog().showAndWait();
		});
		
		//Anzeige
		HBox hBoxButtons = new HBox();
		hBoxButtons.getChildren().addAll(btnKunde,btnFuhrpark,btnMiete);
		hBoxButtons.setAlignment(Pos.TOP_CENTER);
		
		VBox vBoxCover = new VBox();
		vBoxCover.getChildren().addAll(hBoxButtons,labelCover);
		vBoxCover.setFillWidth(true);
		borderPane.getChildren().addAll(vBoxCover);
		borderPane.setPrefSize(900, 600);
		borderPane.setTop(hBoxButtons);
		borderPane.setCenter(labelCover);
		
		
		primaryStage.setScene(new Scene(borderPane));
		primaryStage.setTitle("HAUPTMENUE RENTAFASTCAR");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	public static void main(String[] args) {
		launch(args);

	}

}
