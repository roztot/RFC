package rentaFastCarFX;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
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
import rentaFastCar.Customer;

public class CreateCustomerDialog extends Dialog<Customer> {

	//GUI Elemente
	GridPane gridKunde = new GridPane();
	static Label lbKdId = new Label();
	static DatePicker dp = new DatePicker();
	static TextField txKdId = new TextField();
	static TextField txName = new TextField();
	static TextField txAdr = new TextField();
	static TextField txGeb = new TextField();
	static TextField txFuer = new TextField();
	BorderPane bp = new BorderPane();

	CreateCustomerDialog(Customer customer) {

		int kundenId = customer.getKundenId();
		this.setTitle("KUNDEN ANLEGEN");
		
		gridKunde.setPadding(new Insets(5, 5, 5, 5));
		gridKunde.setVgap(10);
		gridKunde.setHgap(10);
		gridKunde.addRow(0, new Label("KundenID:"), lbKdId);
		gridKunde.addRow(1, new Label("Name:"), txName);
		gridKunde.addRow(2, new Label("Adresse:"), txAdr);
		gridKunde.addRow(3, new Label("Geburtsdatum:"), dp);
		gridKunde.addRow(4, new Label("Fuererscheinnummer:"), txFuer);

		//Befüllung der Felder wenn der Kunde schon angelegt wurde-> Änderung
		lbKdId.setText(customer.getKundenId() + "");
		txName.setText(customer.getName());
		txAdr.setText(customer.getAdresse());
		//dp.setUserData(customer.getGeburtsDatum()); -> Datum wird dadurch nicht angezeigt
		txFuer.setText(customer.getFuehrerscheinNummer());

		bp.setPrefSize(100, 50);
		bp.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		bp.setLeft(gridKunde);
		bp.setMaxWidth(400);
		dp.setEditable(true);

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

		//Gibt ein neues Kundenobjekt zurück für die speicherung in der DB
		this.setResultConverter(new Callback<ButtonType, Customer>() {

			@Override
			public Customer call(ButtonType arg0) {

				if (arg0 == speichern) {
					java.sql.Date sqlDate = null;
					sqlDate = java.sql.Date.valueOf(dp.getValue());
					return new Customer(kundenId, txName.getText(), txAdr.getText(), sqlDate, txFuer.getText());
				}

				return null;
			}
		});

	}

	//Alle Textfelder müssen befüllt seint, sonst kommt eine Warnung
	public boolean isInputValid() {

		Boolean b = false;
			if (!(txName.getText() == null || txName.getText().length() == 0) &&
				!(txAdr.getText() == null || txAdr.getText().length() == 0) &&
				!(dp.getValue() == null || dp.getValue().lengthOfYear() == 0) &&
				!(txFuer.getText() == null || txFuer.getText().length() == 0))
			{
				b= true;
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("FEHLERHAFTE EINGABE!");
				alert.setContentText("DATEN FEHLEN! Bitte kontrollieren Sie Ihre Eingabe");
				alert.showAndWait();
				
				
			}
				
		return b;
	}

	public CreateCustomerDialog(Optional<Customer> customer) {

	}

	// Erster versuch :
	// Textfelder befüllen aus der TableView
	// public void fillTextFields() {
	// CustomerProperty cusp =
	// CustomerDialog.getKundenTable().getSelectionModel().getSelectedItem();
	// lbKdId.setText(cusp.getKundenID().toString());
	// if (cusp != null) {
	// txName.setText(cusp.getName());
	// txAdr.setText(cusp.getAdresse());
	// dp.getUserData();
	// txFuer.setText(cusp.fuehrerscheinNummerProperty().toString());

	// }
	// }

	// TableView updaten mit den gänderten Daten
	// public static CustomerProperty updateCustomerTable() {
	// java.sql.Date sqlDate = null;
	// sqlDate = java.sql.Date.valueOf(dp.getValue());
	//
	// CustomerProperty cusp =
	// CustomerDialog.getKundenTable().getSelectionModel().getSelectedItem();
	// cusp.setName(txName.getText());
	// cusp.setAdresse(txAdr.getText());
	// cusp.setGeburtsDatum(sqlDate);
	// cusp.setFuehrerscheinNummer(txFuer.getText());
	// return cusp;
	// }

	/*
	 * public void neu() { speichern.setOnAction(e -> { Text error = new Text();
	 * String text = txGeb.getText(); boolean saveCustomer; java.sql.Date sqlDate =
	 * null; int kundenId = 0; BookingDatabase db = new BookingDatabase(); try {
	 * kundenId = Integer.parseInt(txKdId.getText()); } catch (NumberFormatException
	 * e1) { java.time.format.DateTimeFormatter geburtsDatumformat =
	 * java.time.format.DateTimeFormatter .ofPattern("dd/MM/yyyy");
	 * java.time.LocalDate txGebField = java.time.LocalDate.parse(text,
	 * geburtsDatumformat); sqlDate = java.sql.Date.valueOf(txGebField); } if
	 * (txKdId.getText().isEmpty()) error.setText("Gib ID ein"); else if
	 * (txName.getText().isEmpty()) error.setText("Gib Namen ein"); else if
	 * (txAdr.getText().isEmpty()) error.setText("Gib Adresse ein"); else if
	 * (txGeb.getText().isEmpty()) error.setText("Gib Datum ein: dd/MM/yyy"); else
	 * if (txFuer.getText().isEmpty())
	 * error.setText("Gib die Führerscheinnummer ein"); else { Customer cus = new
	 * Customer(kundenId, txName.getText(), txAdr.getText(), sqlDate,
	 * txFuer.getText()); try { saveCustomer = db.insertKunde(cus);
	 * 
	 * if(saveCustomer) { System.out.println("Kunde angelegt"); }else {
	 * txKdId.setText(""); txName.setText(""); txName.setText("");
	 * txGeb.setText(""); txFuer.setText(""); } } catch (SQLException e1) {
	 * 
	 * e1.printStackTrace(); } }}); }
	 * 
	 * void bearbeiten() {
	 * 
	 * speichern.setOnAction(ea -> { int kundenId = 0; String text =
	 * txGeb.getText(); java.sql.Date sqlDate = null; BookingDatabase db = new
	 * BookingDatabase(); try { kundenId = Integer.parseInt(txKdId.getText()); }
	 * catch (NumberFormatException e) { java.time.format.DateTimeFormatter
	 * geburtsDatumformat = java.time.format.DateTimeFormatter
	 * .ofPattern("dd/MM/yyyy"); java.time.LocalDate txGebField =
	 * java.time.LocalDate.parse(text, geburtsDatumformat); sqlDate =
	 * java.sql.Date.valueOf(txGebField); } Customer cus = new Customer(kundenId,
	 * txName.getText(), txAdr.getText(), sqlDate, txFuer.getText()); try {
	 * db.updateKunde(cus); } catch (SQLException e) {
	 * 
	 * e.printStackTrace(); db.notifyAll();
	 * 
	 * } });
	 * 
	 * speichern.setOnAction(new EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) {
	 * CreateCustomerDialog.this.close(); } });
	 */

}
