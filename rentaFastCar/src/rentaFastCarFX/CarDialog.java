package rentaFastCarFX;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import rentaFastCar.Car;
import rentaFastCarDB.BookingDatabase;

public class CarDialog extends Dialog<ButtonType> {

	private final TableView<CarProperty> autosTable = new TableView<>();
	public ObservableList<CarProperty> carList;
	
	//Gui Elemente + Carobject
	Button neu = new Button("Neu");
	Button bearbeiten = new Button("Bearbeiten");
	HBox atButtons = new HBox();
	VBox autosView = new VBox();
	Car car = new Car();

	public CarDialog() {

		this.setTitle("FUHRPARK");

		// Aufruf der TableView
		CreateCarTableView();
		// Anzeige zum hinzufügen der Autos -> gibt ein neues Auto in die DB
		neu.setOnAction(e -> {
			Optional<Car> newCar = new CreateCarDialog(car).showAndWait();
			if (newCar.isPresent()) {
				try {
					BookingDatabase.insertAuto(newCar.get());
					carList.add(new CarProperty(newCar.get().getAutoId(), newCar.get().getKennzeichen(),
							newCar.get().getModel(), newCar.get().getKilometer(), newCar.get().getPreisProTag(),
							newCar.get().getKategorie()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

		// Anzeige zum Bearbeiten der Autos, gleiches Dialog nur befüllt-> ändert Objekt in der DB und die Table
		bearbeiten.setOnAction(e -> {
			if (autosTable.getSelectionModel().getSelectedItem() != null) {
				CarProperty carp = autosTable.getSelectionModel().getSelectedItem();
				Car car1 = new Car(carp.getAutoId(), carp.getKennzeichen(), carp.getModel(), carp.getKilometer(),
						carp.getpreisProTag(), carp.getKategorie());

				Optional<Car> newCars = new CreateCarDialog(car1).showAndWait();
				if (newCars.isPresent()) {
					try {
						BookingDatabase.updateAuto(newCars.get());
						int i = autosTable.getSelectionModel().getSelectedIndex();
						carList.remove(i);
						carList.add(i,
								new CarProperty(newCars.get().getAutoId(), newCars.get().getKennzeichen(),
										newCars.get().getModel(), newCars.get().getKilometer(),
										newCars.get().getPreisProTag(), newCars.get().getKategorie()));
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

		Button loeschen = new Button("Entfernen");
		loeschen.setOnAction(e -> {
			if (autosTable.getSelectionModel().getSelectedItem() != null) {
				try {
					BookingDatabase.deleteCar(autosTable.getSelectionModel().getSelectedItem().getAutoId());
					deleteButtonClicked();
				} catch (SQLException e1) {
					System.out.println("Please select an other car");
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fehler");
					alert.setContentText("Auto kann nicht gelöscht werden! Wählen Sie eine andere aktion aus");
					alert.showAndWait();
				}
			} else {
				System.out.println("Please select a row");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setContentText("Bitte wählen Sie eine Zeile aus");
				alert.showAndWait();
			}
		});

		atButtons.getChildren().addAll(neu, bearbeiten, loeschen);
		atButtons.setPadding(new Insets(20, 20, 20, 20));
		atButtons.setSpacing(30);

		// Anzeige

		autosView.getChildren().addAll(autosTable, atButtons);
		this.getDialogPane().setContent(autosView);
		ButtonType cancel = ButtonType.CANCEL;
		this.getDialogPane().getButtonTypes().addAll(cancel);

	}

	//TableView für die Anzeige der Autos aus DB
	@SuppressWarnings("unchecked")
	private void CreateCarTableView() {

		autosTable.setEditable(false);
		TableColumn<CarProperty, Integer> autoId = new TableColumn("AutoId");
		autoId.setCellValueFactory(new PropertyValueFactory<>("autoId"));
		autoId.setMinWidth(100);
		TableColumn<CarProperty, String> kennzeichen = new TableColumn("Kennzeichen");
		kennzeichen.setCellValueFactory(new PropertyValueFactory<>("kennzeichen"));
		kennzeichen.setMinWidth(100);
		TableColumn<CarProperty, String> model = new TableColumn("Model");
		model.setCellValueFactory(new PropertyValueFactory<>("model"));
		model.setMinWidth(300);
		TableColumn<CarProperty, Integer> kilometer = new TableColumn("Kilometer");
		kilometer.setCellValueFactory(new PropertyValueFactory<>("kilometer"));
		kilometer.setMinWidth(300);
		TableColumn<CarProperty, Double> preisProTag = new TableColumn("Preis pro Tag");
		preisProTag.setCellValueFactory(new PropertyValueFactory<>("preisProTag"));
		preisProTag.setMinWidth(200);
		TableColumn<CarProperty, String> kategorie = new TableColumn("Kategorie");
		kategorie.setCellValueFactory(new PropertyValueFactory<>("kategorie"));
		kategorie.setMinWidth(200);

		try {
			ArrayList<Car> carsList = BookingDatabase.loadCarsList();
			carList= FXCollections.observableArrayList();
			for (Car c : carsList) {
				carList.add(new CarProperty(c.getAutoId(), c.getKennzeichen(), c.getModel(), c.getKilometer(),
						c.getPreisProTag(), c.getKategorie()));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		autosTable.getColumns().addAll(autoId, kennzeichen, model, kilometer, preisProTag, kategorie);
		autosTable.setItems(carList);
		autosTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

	//Löschen eines Autos aus der DB und der TableView
	public void deleteButtonClicked() {
		ObservableList<CarProperty> carSelected, allCars;
		allCars = autosTable.getItems();
		carSelected = autosTable.getSelectionModel().getSelectedItems();
		carSelected.forEach(allCars::remove);
	}

	// public void modifyButtonClicked() {
	// ObservableList<CarProperty> carsSelected;
	// carsSelected = getAutosTable().getSelectionModel().getSelectedItems();
	// carsSelected.set(getAutosTable().getSelectionModel().getFocusedIndex(),
	// CreateCarDialog.updateCarTable());
	//
	// }

}
