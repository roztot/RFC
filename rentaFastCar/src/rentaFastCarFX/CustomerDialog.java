package rentaFastCarFX;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import rentaFastCar.Customer;
import rentaFastCarDB.BookingDatabase;

public class CustomerDialog extends Dialog<ButtonType> {

	private final TableView<CustomerProperty> kundenTable = new TableView<>();
	private ObservableList<CustomerProperty> customerList;
	//Erzeugung der GUI Elemente
	Button neu = new Button("Neu");
	Button bearbeiten = new Button("Bearbeiten");
	Button loeschen = new Button("Entfernen");
	HBox kdButtons = new HBox();
	VBox kundenView = new VBox();
	Customer customerN = new Customer();
	BorderPane bPane = new BorderPane();

	public CustomerDialog() {

		this.setTitle("KUNDEN VERWALTEN");

		//Aufruf der TableView für die Auflistung der Kunden aus der DB
		CreateCustomerTableView();

		//Dialog um neue Kunden hinzuzufügen
		neu.setOnAction(e -> {

			Optional<Customer> cust = new CreateCustomerDialog(customerN).showAndWait();
			if (cust.isPresent()) {
				try {
					BookingDatabase.insertKunde(cust.get());
					cust.get();
					// neues customer objekt zu customerproperty und zu der observablelist
					customerList.add(new CustomerProperty(cust.get().getKundenId(), cust.get().getName(),
							cust.get().getAdresse(), cust.get().getGeburtsDatum(),
							cust.get().getFuehrerscheinNummer()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// gleiches Dialog nur befüllt, für die Bearbeitung der einzelnen Kunden

		bearbeiten.setOnAction(e -> {
			if (kundenTable.getSelectionModel().getSelectedItem() != null) {
				CustomerProperty customerp = kundenTable.getSelectionModel().getSelectedItem();
				Customer customer = new Customer(customerp.getKundenId(), customerp.getName(), customerp.getAdresse(),
						customerp.getGeburtsDatum(), customerp.getFuehrerscheinNummer());

				Optional<Customer> cust = new CreateCustomerDialog(customer).showAndWait();
				if (cust.isPresent()) {
					try {
						BookingDatabase.updateKunde(cust.get());
						int i = kundenTable.getSelectionModel().getSelectedIndex();
						customerList.remove(i);
						customerList.add(i,
								new CustomerProperty(cust.get().getKundenId(), cust.get().getName(),
										cust.get().getAdresse(), cust.get().getGeburtsDatum(),
										cust.get().getFuehrerscheinNummer()));
						// modifyButtonClicked();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			} else {
				System.out.println("Please select a row");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setContentText("Bitte wählen Sie eine Zeile aus");
				alert.showAndWait();
			}
		});

		//Entfernen eines Kunden aus DB -> es kann nur entfernt werden wenn noch nichts gemietet-> Alert
		loeschen.setOnAction(e -> {
			if (kundenTable.getSelectionModel().getSelectedItem() != null) {
				try {
					BookingDatabase.deleteCustomer(kundenTable.getSelectionModel().getSelectedItem().getKundenId());
					deleteButtonClicked();
				} catch (SQLException e1) {
					System.out.println("Please select an other customer");
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fehler");
					alert.setContentText("Kunde kann nicht gelöscht werden! Wählen Sie eine andere aktion aus");
					alert.showAndWait();
				}
			}else {
				System.out.println("Please select a row");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setContentText("Bitte wählen Sie eine Zeile aus");
				alert.showAndWait();
			}
		});

		kdButtons.getChildren().addAll(neu, bearbeiten, loeschen);
		kdButtons.setPadding(new Insets(20, 20, 20, 20));
		kdButtons.setSpacing(30);
		kundenView.getChildren().addAll(kundenTable, kdButtons);
		bPane.setCenter(kundenView);
		Scene scene = new Scene(bPane, 650, 400, true);
		this.getDialogPane().setContent(bPane);
		ButtonType cancel = ButtonType.CANCEL;
		this.getDialogPane().getButtonTypes().add(cancel);

	}

	//TableView für die Kundenliste
	@SuppressWarnings("unchecked")
	private void CreateCustomerTableView() {

		kundenTable.setEditable(false);
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

		try {
			ArrayList<Customer> cusList = BookingDatabase.loadCustomerList();
			customerList = (FXCollections.observableArrayList());
			for (Customer c : cusList) {
				customerList.add(new CustomerProperty(c.getKundenId(), c.getName(), c.getAdresse(), c.getGeburtsDatum(),
						c.getFuehrerscheinNummer()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		kundenTable.getColumns().addAll(kundenId, name, adresse, geburtsdatum, fuererschein);
		kundenTable.setItems(customerList);
		kundenTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

	}

	//Löschen des Kunden aus der Table und DB
	public void deleteButtonClicked() {
		ObservableList<CustomerProperty> cutomerSelected, allCustomer;
		allCustomer = kundenTable.getItems();
		cutomerSelected = kundenTable.getSelectionModel().getSelectedItems();
		cutomerSelected.forEach(allCustomer::remove);
	}

	// public void modifyButtonClicked() {
	// ObservableList<CustomerProperty> cutomersSelected;
	// cutomersSelected = getKundenTable().getSelectionModel().getSelectedItems();
	// cutomersSelected.set(getKundenTable().getSelectionModel().getFocusedIndex(),
	// CreateCustomerDialog.updateCustomerTable());
	//
	// }

}
