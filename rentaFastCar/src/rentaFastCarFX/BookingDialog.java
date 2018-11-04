package rentaFastCarFX;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import rentaFastCar.Booking;
import rentaFastCarDB.BookingDatabase;

public class BookingDialog extends Dialog<ButtonType> {

	private final TableView<BookingProperty> mieteTable = new TableView<>();
	public ObservableList<BookingProperty> bookingList;

	// erzeugen der GUI elemente für die Anzeige
	Button mieteStarten = new Button("Neue Miete Starten");
	Button mieteBeenden = new Button("Miete Beenden");
	Button mieteAnzeigen = new Button("Liste aller Mieten");
	HBox mtButtons = new HBox();
	VBox mieteView = new VBox();
	Booking b = new Booking();

	public BookingDialog() {

		// TableView für die Anzeige der Mieten aus DB
		// CreateBookingTableView();
		this.setTitle("MIETE");
		mieteTable.setEditable(false);
		TableColumn<BookingProperty, Integer> mietID = new TableColumn<BookingProperty, Integer>("MietID");
		mietID.setCellValueFactory(new PropertyValueFactory<>("mietID"));
		mietID.setMinWidth(100);
		TableColumn<BookingProperty, Integer> autoId = new TableColumn<BookingProperty, Integer>("AutoId");
		autoId.setCellValueFactory(new PropertyValueFactory<>("autoId"));
		autoId.setMinWidth(100);
		TableColumn<BookingProperty, Integer> kundenID = new TableColumn<BookingProperty, Integer>("KundenID");
		kundenID.setCellValueFactory(new PropertyValueFactory<>("kundenID"));
		kundenID.setMinWidth(100);
		TableColumn<BookingProperty, String> name = new TableColumn<BookingProperty, String>("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		name.setMinWidth(300);
		TableColumn<BookingProperty, String> kennzeichen = new TableColumn<BookingProperty, String>("Kennzeichen");
		kennzeichen.setCellValueFactory(new PropertyValueFactory<>("kennzeichen"));
		kennzeichen.setMinWidth(150);
		TableColumn<BookingProperty, LocalDate> uebergabeDatum = new TableColumn<BookingProperty, LocalDate>(
				"Uebergabe");
		uebergabeDatum.setCellValueFactory(new PropertyValueFactory<>("uebergabeDatum"));
		uebergabeDatum.setMinWidth(150);
		TableColumn<BookingProperty, LocalDate> ruecknahmeDatum = new TableColumn<BookingProperty, LocalDate>(
				"Ruecknahme");
		ruecknahmeDatum.setCellValueFactory(new PropertyValueFactory<>("ruecknahmeDatum"));
		ruecknahmeDatum.setMinWidth(150);
		TableColumn<BookingProperty, Double> gesamtPreis = new TableColumn<BookingProperty, Double>("Gesamtpreis");
		gesamtPreis.setCellValueFactory(new PropertyValueFactory<>("gesamtPreis"));
		gesamtPreis.setMinWidth(150);
		mieteTable.getColumns().addAll(mietID, autoId, kundenID, name, kennzeichen, uebergabeDatum, ruecknahmeDatum,
				gesamtPreis);
		mieteTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		//Buttons
		mtButtons.setSpacing(320);
		mtButtons.getChildren().addAll(mieteStarten, mieteBeenden, mieteAnzeigen);
		mtButtons.setPadding(new Insets(50, 100, 50, 100));
		mieteStarten.setMinSize(150, 70);
		mieteStarten.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		mieteAnzeigen.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		mieteAnzeigen.setMinSize(150, 70);
		mieteBeenden.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		mieteBeenden.setMinSize(150, 70);

		//Dialogaufruf für den Anfang des Mietprozesses
		mieteStarten.setOnAction(e -> {
			new StartBookingDialog().showAndWait();
		});

		//Dialogaufruf für Ende des Mietprozesses
		mieteBeenden.setOnAction(e -> {
			if (mieteTable.getSelectionModel().getSelectedItem() != null) {
				new FinishBookingDialog(mieteTable.getSelectionModel().getSelectedItem(), b).showAndWait();
			} else {
				System.out.println("Please select a row");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setContentText("Bitte wählen Sie eine Zeile aus");
				alert.showAndWait();
			}
		});

		//Anzeige aller Mieten im neuen Dialog
		mieteAnzeigen.setOnAction(ea -> {
			try {
				ArrayList<Booking> bookList = BookingDatabase.getBookingList();
				bookingList = (FXCollections.observableArrayList());
				for (Booking b : bookList) {
					bookingList.add(new BookingProperty(b.getMietId(), b.getAutoId(), b.getKundenId(), b.getName(),
							b.getKennzeichen(), b.getUebergabeDatum(), b.getRuecknahmeDatum(), b.getGesamtPreis()));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			mieteTable.setItems(bookingList);
		});
		
		//Layout für das Dialog, ordnen der GUI Elemente
		mieteView.getChildren().addAll(mieteTable, mtButtons);
		this.getDialogPane().setContent(mieteView);
		ButtonType cancel = ButtonType.CANCEL;
		this.getDialogPane().getButtonTypes().addAll(cancel);
	}

}
