package rentaFastCarFX;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import rentaFastCar.Car;

public class CreateCarDialog extends Dialog<Car> {

	//GUI Elemente
	GridPane gridAutos = new GridPane();
	static Label lbAutoId = new Label();
	static TextField txKenZ = new TextField();
	static TextField txModel = new TextField();
	static TextField txKm = new TextField();
	static TextField txPpT = new TextField();
	static TextField txKat = new TextField();
	BorderPane bp = new BorderPane();

	//Dialog für Anlegen und Bearbeiten der Autos
	CreateCarDialog(Car car) {

		int autoId = car.getAutoId();
		this.setTitle("AUTO ANLEGEN");

		gridAutos.setPadding(new Insets(5, 5, 5, 5));
		gridAutos.setVgap(10);
		gridAutos.setHgap(10);
		gridAutos.addRow(0, new Label("AutoId:"), lbAutoId);
		gridAutos.addRow(1, new Label("Kennzeichen:"), txKenZ);
		gridAutos.addRow(2, new Label("Model:"), txModel);
		gridAutos.addRow(3, new Label("Kilometer:"), txKm);
		gridAutos.addRow(4, new Label("Preis pro Tag:"), txPpT);
		gridAutos.addRow(5, new Label("Kategorie:"), txKat);

		//Befüllung der Felder wenn das Auto schon angelegt wurde->Änderung
		lbAutoId.setText(car.getAutoId() + "");
		txKenZ.setText(car.getKennzeichen());
		txModel.setText(car.getModel());
		txKm.setText(car.getKilometer() + "");
		txPpT.setText(car.getPreisProTag() + "");
		txKat.setText(car.getKategorie());

		bp.setPrefSize(100, 50);
		bp.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		bp.setLeft(gridAutos);
		bp.setMaxWidth(400);

		this.getDialogPane().setContent(bp);
		ButtonType speichern = ButtonType.OK;
		ButtonType cancel = ButtonType.CANCEL;
		this.getDialogPane().getButtonTypes().addAll(speichern, cancel);
		final Button btOk = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
		btOk.addEventFilter(ActionEvent.ACTION, event -> {
			if (!isInputValid()) {
				event.consume();
			}

		});

		//Gibt ein neues Ato für die speicherung in der DB zurück
		//Nur int oder double darf eingegeben werden -> Alert
		this.setResultConverter(new Callback<ButtonType, Car>() {
			int iD;
			double pPT;
			@Override
			public Car call(ButtonType arg0) {
				if (arg0 == speichern) {
					
					try {
						iD = Integer.parseInt(txKm.getText());
					} catch (NumberFormatException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("FEHLER!");
						alert.setContentText("Kilometer erwartet eine Zahl! Bitte kontrollieren Sie Ihre Eingabe");
						alert.showAndWait();
						System.out.println("Bitte eine Zahl eingeben");
					}
					try {
						pPT = Double.parseDouble(txPpT.getText());
					} catch (NumberFormatException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("FEHLER!");
						alert.setContentText("Preis pro Tag erwartet eine Zahl! Bitte kontrollieren Sie Ihre Eingabe");
						alert.showAndWait();
						System.out.println("Bitte eine Zahl eingeben");
					}
					return new Car(autoId, txKenZ.getText(), txModel.getText(), iD, pPT, txKat.getText());
				}
				
				return null;
			}
			
			});
	}
	//Alle Textfelder müssen befüllt seint, sonst kommt eine Warnung
	private boolean isInputValid() {
		Boolean b = false;
		
		if (!(txKenZ.getText() == null || txKenZ.getText().length() == 0)
				&& !(txModel.getText() == null || txModel.getText().length() == 0)
				&& !(txKm.getText() == null || txKm.getText().length() == 0 )
				&& !(txPpT.getText() == null || txPpT.getText().length() == 0 )
				&& !(txKat.getText() == null || txKat.getText().length() == 0)) {
			b = true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("FEHLERHAFTE EINGABE!");
			alert.setContentText("DATEN FEHLEN! Bitte kontrollieren Sie Ihre Eingabe");
			alert.showAndWait();

		}

		return b;
	}

	public CreateCarDialog(Optional<Car> car) {

	}
}




// Erster Versuch:
// Textfelder befüllen aus der TableView
// public static void fillTextFields() {
// CarProperty carp =
// CarDialog.getAutosTable().getSelectionModel().getSelectedItem();
// lbAutoId.setText(carp.getAutoId().toString());
// if (carp != null) {
// txKenZ.setText(carp.getKennzeichen());
// txModel.setText(carp.getModel());
// txKm.setText(carp.getKilometer().toString());
// txPpT.setText(carp.getpreisProTag().toString());
// txKat.setText(carp.getKategorie());
// }
// }
//
// public static CarProperty updateCarTable() {
//
// int iD = Integer.parseInt(txKm.getText());
// double pPT = Double.parseDouble(txPpT.getText());
// CarProperty carp =
// CarDialog.getAutosTable().getSelectionModel().getSelectedItem();
// carp.setKennzeichen(txKenZ.getText());
// carp.setModel(txModel.getText());
// carp.setKilometer(iD);
// carp.setPreisProTag(pPT);
// return carp;
// }
//
// }
