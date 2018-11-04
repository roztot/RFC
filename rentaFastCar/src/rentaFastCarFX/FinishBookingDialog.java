package rentaFastCarFX;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import rentaFastCar.Booking;
import rentaFastCar.Car;
import rentaFastCar.Customer;
import rentaFastCarDB.BookingDatabase;

public class FinishBookingDialog extends Dialog<ButtonType> {
	//Erzeugung der GUi Elemente
	Label lbAuto = new Label("Rückgabe Auto ");
	Label lbKm = new Label("Kilometerstand");
	Label lbTank = new Label("Auto vollgetankt");
	Label lbSchaden = new Label("Auto ist beschädigt");
	CheckBox cbTank = new CheckBox();
	CheckBox cbSchaden = new CheckBox();
	TextField txfKm = new TextField();
	Button mieteBeenden = new Button("Miete Beenden");
	GridPane gridEnd = new GridPane();
	VBox endView = new VBox();
	Customer cust = new Customer();
	Car c = new Car();
	FinishBookingDialog(BookingProperty bp, Booking b) {

		this.setTitle("MIETE BEENDEN");
		//Update der ausgewählen Mieten in der DB über das TextField und CheckBoxen
		mieteBeenden.setMinSize(100, 40);
		mieteBeenden.setOnAction(e -> {
			if(!(txfKm.getText() == null || txfKm.getText().length() == 0 )){
			try {
				
				try {
					BookingDatabase.finishBookingKm(bp.getAutoId(),Integer.parseInt(txfKm.getText()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (cbTank.isSelected()) {
					BookingDatabase.updateBookingTank(bp.getMietId(),true);
				} else {
					BookingDatabase.updateBookingTank(bp.getMietId(),false);
					bp.setgesamtPreis(bp.getgesamtPreis() * 1.2);
					BookingDatabase.updateBookingPreis(bp.getMietId(),bp.getgesamtPreis() * 1.2);
				}
				
				if (cbSchaden.isSelected()) {
					BookingDatabase.updateBookingSchaden(bp.getMietId(),true);
					bp.setgesamtPreis((bp.getgesamtPreis() * 1.2));
					BookingDatabase.updateBookingPreis(bp.getMietId(),bp.getgesamtPreis() * 1.2);
				} else {
					BookingDatabase.updateBookingSchaden(bp.getMietId(), false);
				}
				
				new InvoiceDialog(bp,b).showAndWait();
				
			} catch (NumberFormatException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("FEHLER!");
				alert.setContentText("Kilometer erwartet eine Zahl! Bitte kontrollieren Sie Ihre Eingabe");
				alert.showAndWait();
				System.out.println("Bitte eine Zahl eingeben"); 
			} 
			}		
		});

		//Anordnung der GUI Elemente für die Anzeige
		gridEnd.addRow(0, lbAuto);
		gridEnd.addRow(1, lbKm, txfKm);
		gridEnd.addRow(2, lbTank, cbTank);
		gridEnd.addRow(3, lbSchaden, cbSchaden);
		gridEnd.setPadding(new Insets(20, 10, 20, 20));
		gridEnd.setVgap(50);
		gridEnd.setHgap(50);
		endView.setSpacing(100);
		endView.getChildren().addAll(gridEnd, mieteBeenden);
		this.getDialogPane().setContent(endView);
		ButtonType cancel = ButtonType.CANCEL;
		this.getDialogPane().getButtonTypes().add(cancel);

	}

}
