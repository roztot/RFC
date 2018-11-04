package rentaFastCarFX;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import rentaFastCar.Booking;
import rentaFastCar.Customer;
import rentaFastCarDB.BookingDatabase;

public class ChooseCustomerDialog extends Dialog<ButtonType> {

	private final TableView<CustomerProperty> kundenTable = new TableView<>();
	private  ObservableList<CustomerProperty> customerList;
	private  FilteredList<CustomerProperty> filteredData;
	private  SortedList<CustomerProperty> sortedData;
	GridPane gridChoose = new GridPane();
	Label lblKdId = new Label("KundenId");
	Label lblKdName = new Label("Name");
	Label lbAusw = new Label("Auswahl Kunde");
	TextField txCustId = new TextField();
	TextField txCustName = new TextField();
	Button bAnzeigen = new Button("Anzeigen");
	Button mieteStarten = new Button("Miete Starten");
	VBox chooseView = new VBox();
	Customer c = new Customer();
	BorderPane bPane = new BorderPane();
	Booking b = new Booking();

	@SuppressWarnings("unchecked")
	ChooseCustomerDialog(CarProperty cp, DatePicker dpU, DatePicker dpR) {


		this.setTitle("MIETE STARTEN");

		TableView<CustomerProperty> kundenTable = new TableView<>();
		kundenTable.setEditable(true);
		TableColumn<CustomerProperty, Integer> kundenId = new TableColumn("KundenID");
		kundenId.setCellValueFactory(new PropertyValueFactory<>("kundenID"));
		kundenId.setMinWidth(100);
		TableColumn<CustomerProperty, String> name = new TableColumn("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		name.setMinWidth(300);
		TableColumn<CustomerProperty, String> adresse = new TableColumn("Adresse");
		adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
		adresse.setMinWidth(300);
		TableColumn<CustomerProperty, LocalDate> geburtsdatum = new TableColumn("Geburtsdatum");
		geburtsdatum.setCellValueFactory(new PropertyValueFactory<>("geburtsDatum"));
		geburtsdatum.setMinWidth(200);
		TableColumn<CustomerProperty, String> fuererschein = new TableColumn("Fuererscheinnummer");
		fuererschein.setCellValueFactory(new PropertyValueFactory<>("fuehrerscheinNummer"));
		fuererschein.setMinWidth(200);
		kundenTable.getColumns().addAll(kundenId, name, adresse, geburtsdatum, fuererschein);
		kundenTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		try {
			ArrayList<Customer> cusList = BookingDatabase.loadCustomerList();
			customerList= FXCollections.observableArrayList();
			for (Customer c : cusList) {
				customerList.add(new CustomerProperty(c.getKundenId(), c.getName(), c.getAdresse(),
						c.getGeburtsDatum(), c.getFuehrerscheinNummer()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		kundenTable.setItems(customerList);
		gridChoose.setPadding(new Insets(10, 10, 10, 10));
		gridChoose.setHgap(20);
		gridChoose.setVgap(20);
		gridChoose.add(lblKdId, 0, 0);
		gridChoose.add(txCustId, 0, 1);
		gridChoose.add(lblKdName, 0, 2);
		gridChoose.add(txCustName, 0, 3);
		bAnzeigen.setPadding(new Insets(5, 10, 5, 10));

		//TextField  Suche
		filteredData = new FilteredList<>(customerList, p -> true);
		txCustName.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(str -> {
			if (newValue == null || newValue.isEmpty())
				return true;

			if (str.getName().toLowerCase().contains(newValue.toLowerCase()))
				return true;
			return false;
		}));
		sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(kundenTable.comparatorProperty());
		kundenTable.setItems(sortedData);

		txCustId.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(id -> {
			if (newValue == null || newValue.isEmpty())
				return true;
			if(id.getKundenId().toString().contains(newValue))
				return true;
			return false;
		}));
		sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(kundenTable.comparatorProperty());
		kundenTable.setItems(sortedData);

		bAnzeigen.setOnAction(e -> {


		});


		//Konversion von LocalDate auf SQL Date
		LocalDate d1 = dpU.getValue();
		LocalDate d2 = dpR.getValue();

		Date dateU = java.sql.Date.valueOf(dpU.getValue());
		Date dateR = java.sql.Date.valueOf(dpR.getValue());
		
		//CustomerProperty cusp = kundenTable.getSelectionModel().getSelectedItem();
		double miettageGes = Period.between(d1, d2).getDays();
		double gP = miettageGes * cp.getpreisProTag().doubleValue();


		mieteStarten.setOnAction(e -> {
			System.out.println("Text");
			if(!(kundenTable.getSelectionModel().getSelectedItem()== null) ){

				b.setAutoId(cp.getAutoId());
				b.setKundenId(kundenTable.getSelectionModel().getSelectedItem().getKundenId());
				b.setName(kundenTable.getSelectionModel().getSelectedItem().getName());
				b.setKennzeichen(cp.getKennzeichen());
				b.setUebergabeDatum(dateU);
				b.setRuecknahmeDatum(dateR);
				b.setGesamtPreis(gP);
				b.setKilometer(cp.getKilometer());
				try {
					BookingDatabase.createBooking(b);
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				System.out.println("Miete gespeichert");
				// ChooseCustomerDialog.this.close();
			}else {
				System.out.println("Please select a row");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setContentText("Bitte wählen Sie eine Zeile aus");
				alert.showAndWait();
			}
		});

		mieteStarten.setPadding(new Insets(20, 20, 20, 20));
		chooseView.getChildren().addAll(lbAusw, gridChoose,mieteStarten);
		chooseView.setSpacing(20);
		bPane.setCenter(kundenTable);
		bPane.setLeft(chooseView);
		this.getDialogPane().setContent(bPane);
		ButtonType cancel = ButtonType.CANCEL;
		this.getDialogPane().getButtonTypes().add(cancel);

	}



}