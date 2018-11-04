package rentaFastCarFX;

import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerProperty {
	private  SimpleIntegerProperty kundenId;
	private SimpleStringProperty name;
	private SimpleStringProperty adresse;
	private SimpleObjectProperty<Date> geburtsDatum;
	private SimpleStringProperty fuehrerscheinNummer;

	public CustomerProperty(int kundenID, String name, String adresse,
			Date geburtsDatum, String fuehrerscheinNummer) {
		super();
		this.kundenId = new SimpleIntegerProperty(kundenID);
		this.name = new SimpleStringProperty(name);
		this.adresse = new SimpleStringProperty(adresse);
	
		this.geburtsDatum = new SimpleObjectProperty<Date>(geburtsDatum);
	
		this.fuehrerscheinNummer = new SimpleStringProperty(fuehrerscheinNummer);
	}
		public CustomerProperty() {
			
		}
		
		public Integer getKundenId() {
			return kundenId.get();
		}
		public void setKundenID(Integer kundenID) {
			this.kundenId.set(kundenID);
		}
		public SimpleIntegerProperty kundenIDProperty() {
			return kundenId;
	}
		public String getName() {
			return name.get();
		}
		public void setName(String name) {
			this.name.set(name);
		}
		public SimpleStringProperty nameProperty() {
			return name;
		}
		public String getAdresse() {
			return adresse.get();
		}
		public void setAdresse(String adresse) {
			this.adresse.set(adresse);
		}
		public SimpleStringProperty adresseProperty() {
			return adresse;
		}
		public Date getGeburtsDatum() {
			return (Date) geburtsDatum.get();
		}
		public void setGeburtsDatum(Date geburtsDatum) {
			this.geburtsDatum.set(geburtsDatum);
		}
		public SimpleObjectProperty geburtsDatumProperty() {
			return geburtsDatum;
		}
		public String getFuehrerscheinNummer() {
			return fuehrerscheinNummer.get();
		}
		public void setFuehrerscheinNummer(String fuehrerscheinNummer) {
			this.fuehrerscheinNummer.set(fuehrerscheinNummer);
		}
		public SimpleStringProperty fuehrerscheinNummerProperty() {
			return fuehrerscheinNummer;
			
		}


		@Override
		public String toString() {
			return "CustomerProperty [kundenId=" + kundenId + ", name=" + name + ", adresse=" + adresse
					+ ", geburtsDatum=" + geburtsDatum + ", fuehrerscheinNummer=" + fuehrerscheinNummer + "]";
		}
}
