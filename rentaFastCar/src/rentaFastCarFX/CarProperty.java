package rentaFastCarFX;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CarProperty {
	private SimpleIntegerProperty autoId;
	private SimpleStringProperty kennzeichen;
	private SimpleStringProperty model;
	private SimpleIntegerProperty kilometer;
	private SimpleDoubleProperty preisProTag;
	private SimpleStringProperty kategorie;

	public CarProperty(int autoId, String kennzeichen, String model, int kilometer, double preisProTag,
			String kategorie) {
		super();
		this.autoId = new SimpleIntegerProperty(autoId);
		this.kennzeichen = new SimpleStringProperty(kennzeichen);
		this.model = new SimpleStringProperty(model);
		this.kilometer = new SimpleIntegerProperty(kilometer);
		this.preisProTag = new SimpleDoubleProperty(preisProTag);
		this.kategorie = new SimpleStringProperty(kategorie);
	}

	public Integer getAutoId() {
		return autoId.get();
	}

	public void setautoId(int autoId) {
		this.autoId.set(autoId);
	}

	public SimpleIntegerProperty autoIdProperty() {
		return autoId;
	}

	public String getKennzeichen() {
		return kennzeichen.get();
	}

	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen.set(kennzeichen);
	}

	public SimpleStringProperty kennzeichenProperty() {
		return kennzeichen;
	}

	public String getModel() {
		return model.get();
	}

	public void setModel(String model) {
		this.model.set(model);
	}

	public SimpleStringProperty modelProperty() {
		return model;

	}

	public Integer getKilometer() {
		return kilometer.get();
	}

	public void setKilometer(int kilometer) {
		this.kilometer.set(kilometer);
	}

	public SimpleIntegerProperty kilometerProperty() {
		return kilometer;
	}

	public Double getpreisProTag() {
		return preisProTag.get();
	}

	public void setPreisProTag(Double preisProTag) {
		this.preisProTag.set(preisProTag);
	}

	public SimpleDoubleProperty preisProTagProperty() {
		return preisProTag;

	}

	public String getKategorie() {
		return kategorie.get();
	}

	public void setStatus(String status) {
		this.kategorie.set(status);
	}

	public SimpleStringProperty statusProperty() {
		return kategorie;

	}

	@Override
	public String toString() {
		return "CarProperty [autoId=" + autoId + ", kennzeichen=" + kennzeichen + ", model=" + model + ", kilometer="
				+ kilometer + ", preisProTag=" + preisProTag + ", kategorie=" + kategorie + "]";
	}

	

}
