package rentaFastCarFX;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import rentaFastCar.Booking;
import rentaFastCarDB.BookingDatabase;

public class InvoiceDialog extends Dialog<ButtonType>{

	
	InvoiceDialog(BookingProperty bp, Booking b){
	
		try {
			b = BookingDatabase.getBooking(bp.getMietId(), bp.getAutoId(), bp.getKundenId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setTitle("Rechnung");
		Label lbId = new Label();
		Label lbName = new Label();
		Label lbAdr = new Label();
		Label lbAutoM = new Label();
		Label lbVon = new Label();
		Label lbBis = new Label();
		Label lbPreisProTag = new Label();
		Label lbTextTank = new Label();
		Label lbGesamtP = new Label();
		Label lbSchaden = new Label();
		
		lbId.setText(b.getKundenId()+"");
		lbName.setText(b.getName());		
		lbAdr.setText(b.getAdresse());
		lbAutoM.setText(" Gefahrenes Model: " + b.getModel());
		lbVon.setText(" am " + bp.getUebergabeDatum());
		lbBis.setText(" am " + bp.getRuecknahmeDatum());
	
		lbPreisProTag.setText("Preis pro Tag beträgt: " + b.getPreisProTag());
	   
		if(b.getTankstand()== false && b.getSchaeden() == false) {
		lbTextTank.setText(" Auto nicht vollgetankt! Tankgebühr: " + b.getGesamtPreis()*0.2 + " EUR");
		lbSchaden.setText("Bravo! Sie haben das Auto unbeschädigt returniert. Diesmal gibt es keinen Schadensgebühr!");
		lbGesamtP.setText("Zur Zahlen: " + b.getGesamtPreis()*1.2 + " EUR");
		System.out.println("Tankstand aktualisiert");
		}
		else  if(b.getTankstand() == true && b.getSchaeden() == true) {
			lbTextTank.setText("Tank ist voll! Danke das Sie das Auto vollgetankt haben!");
			lbSchaden.setText("Schadengebühr: " + b.getGesamtPreis()*0.2 + " EUR");
			lbGesamtP.setText("Zur Zahlen: " + b.getGesamtPreis()* 1.2 + " EUR");
			System.out.println("Tankstand aktualisiert");
		}
		else  if(b.getTankstand() == true && b.getSchaeden() == false ) {
			lbTextTank.setText("Tank ist voll! Danke das Sie das Auto vollgetankt haben!");
			lbSchaden.setText("Bravo! Sie haben das Auto unbeschädigt returniert. Diesmal gibt es keinen Schadensgebühr!");
			lbGesamtP.setText("Zur zahlen: " + b.getGesamtPreis() + " EUR");
			System.out.println("Tankstand aktualisiert");
		}
		else {if(b.getTankstand() == false && b.getSchaeden() == true){
			lbTextTank.setText("Auto nicht vollgetankt! Tankgebühr: " + bp.getgesamtPreis()*0.2 + " EUR");
			lbSchaden.setText("Schadengebühr: " + bp.getgesamtPreis()*0.2 + " EUR");
			lbGesamtP.setText("Zur Zahlen: " + bp.getgesamtPreis()* 1.4 + " EUR");
			System.out.println("Tankstand aktualisiert");
		}
		}
		
		TextArea txaRg = new TextArea("RentaFastCar GmbH Wien " + '\n'
		 + "" + '\n'
		 + "Kunde: " + lbName.getText() + '\n'
		 + "Adresse: " + lbAdr.getText() + '\n'
		 + "" + '\n'
		 + "Übergabe: " + lbVon.getText() + '\n'
		 + "Rücknahme: " + lbBis.getText() + '\n'
		 + "" + '\n'
		 + lbAutoM.getText() + '\n'
		 +"Alle Kilometer inklusive! " + '\n'
		 + "" + '\n'
		 + lbPreisProTag.getText() + '\n'
		 + "" + '\n'
		 + "Tankstand: " + lbTextTank.getText() + '\n'
		 + "" + '\n'
		 + "Schaden: " + lbSchaden.getText() + '\n'
		 + "" + '\n'
		 + "Endbetrag: " + lbGesamtP.getText() + '\n' 
		 + "" + '\n'
		 +"Im Endbetrag sind enthalten: "  + '\n' 
		 +"alle Kilometer, Strassenbenutzungsgeb./Zula. " + '\n'
		 +"Schutzpacket Full " +'\n'
		 +"Reifen- und Scheibenschutz. " + '\n'
		 + "" + '\n'
		 +"Vielen Dank, dass Sie RentaFastCar gewählt haben! "  + '\n'
		 + "" + '\n'
		 +"Die Leistung wurde im Zeitraum zwischen Übergabe und Rücknahme erbracht. "  + '\n'
		 +"Der Rechnungsbetrag wird von Ihrem Konto abgebucht. " + '\n');
		 
		BorderPane gP = new BorderPane();
		gP.setPadding(new Insets(10));
		gP.setCenter(txaRg);
		VBox txaView = new VBox();
		txaRg.setEditable(false);
		txaRg.setCenterShape(true);
		txaRg.minHeight(1500);
		txaRg.minWidth(700);
		txaRg.setPrefColumnCount(40);
		txaRg.setPrefRowCount(20);
		txaRg.setWrapText(true);
		txaView.getChildren().add(gP);
		this.getDialogPane().setContent(txaView);
		ButtonType ok = ButtonType.OK;
		this.getDialogPane().getButtonTypes().add(ok);
		
	
			
		
	}


}
