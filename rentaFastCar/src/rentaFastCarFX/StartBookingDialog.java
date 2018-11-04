package rentaFastCarFX;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import rentaFastCar.Car;
import rentaFastCarDB.BookingDatabase;

public class StartBookingDialog extends Dialog<ButtonType> {

	private final TableView<CarProperty> autosTable = new TableView<>();
	private ObservableList<CarProperty> carList;


	Button anzeigen = new Button("Anzeigen");
	Button auswahl = new Button("Auswählen");
	Label lbAuto = new Label("Auswahl Auto ");
	Label lbKat = new Label("Kategorie");
	Label lbDatV = new Label("Datum von");
	Label lbDatB = new Label("bis");
	DatePicker dpU = new DatePicker();
	DatePicker dpR = new DatePicker();
	ComboBox<String> cbKat = new ComboBox();
	GridPane gridStart = new GridPane();
	VBox startView = new VBox();

	@SuppressWarnings("unchecked")
	StartBookingDialog() {
		
		this.setTitle("MIETE STARTEN");
		TableView<CarProperty> autosTable = new TableView<>();
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
		autosTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		try {
			ArrayList<Car> carsList = BookingDatabase.loadCarsList();
			
			carList = FXCollections.observableArrayList();
			for (Car c : carsList) {
				carList.add(new CarProperty(c.getAutoId(), c.getKennzeichen(), c.getModel(), c.getKilometer(),
						c.getPreisProTag(), c.getKategorie()));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		autosTable.setItems(carList);
		autosTable.getColumns().addAll(autoId, kennzeichen, model, kilometer, preisProTag, kategorie);
		
		ArrayList<String> listKat;
		try {
			listKat = BookingDatabase.chooseCategories();
			//cbKat.getSelectionModel().getSelectedItem()
		} catch (SQLException e) {

			e.printStackTrace();
			return;
		}

		//Auswahl Kategorie und verfühgbare Autos
		cbKat.setItems(FXCollections.observableArrayList(listKat));
		anzeigen.setOnAction(e -> {
			try {
				ArrayList<Car> carsList = BookingDatabase.loadCarsList(cbKat.getSelectionModel().getSelectedIndex()>=0?
						cbKat.getSelectionModel().getSelectedItem():null, dpU.getValue(),dpR.getValue());
				carList.clear();
				for (Car c : carsList) {
					carList.add(new CarProperty(c.getAutoId(), c.getKennzeichen(), c.getModel(), c.getKilometer(),
							c.getPreisProTag(), c.getKategorie()));
				}

			} catch (SQLException e1) {

				System.out.println("Please select categorie first!");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setContentText("Bitte wählen Sie die Kategorie aus!");
				alert.showAndWait();
			}
			
			
		});
		
		//Auswahl Datum: Ende muss nach Start auswählbar sein
		dpU.setEditable(true);
		dpR.setEditable(true);
		dpU.setValue(LocalDate.now());

		    final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
		      @Override
		      public DateCell call(final DatePicker datePicker) {
		        return new DateCell() {
		          @Override
		          public void updateItem(LocalDate item, boolean empty) {
		            super.updateItem(item, empty);
		            if (item.isBefore(dpU.getValue().plusDays(1))) {
		              setDisable(true); 
		              if(item.equals(dpU.getValue()))
		            	  setDisable(true);
		            }
		          }};
		      }};
		    dpR.setDayCellFactory(dayCellFactory);
		    dpR.setValue(dpU.getValue().plusDays(1));

		auswahl.setOnAction(e -> {
			if(autosTable.getSelectionModel().getSelectedItem()!=null) {
			new ChooseCustomerDialog(autosTable.getSelectionModel().getSelectedItem(), dpU,
					dpR).showAndWait();
			}else {
				System.out.println("Please select a row");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setContentText("Bitte wählen Sie eine Zeile aus");
				alert.showAndWait();
			}
		});
		
		gridStart.setPadding(new Insets(20, 20, 20, 20));
		gridStart.setVgap(50);
		gridStart.setHgap(50);
		gridStart.addRow(0, lbAuto);
		gridStart.addRow(1, lbKat, cbKat);
		gridStart.addRow(2, anzeigen);
		gridStart.addRow(3, lbDatV, dpU, lbDatB, dpR);
		

		startView.getChildren().addAll(gridStart, autosTable, auswahl);
		startView.setPadding(new Insets(20, 20, 20, 20));
		startView.setSpacing(20);
		this.getDialogPane().setContent(startView);
		ButtonType cancel = ButtonType.CANCEL;
		this.getDialogPane().getButtonTypes().add(cancel);

	}

		
}
