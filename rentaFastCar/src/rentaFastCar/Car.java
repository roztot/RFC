package rentaFastCar;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Car {

	private  int autoId;
	private  String kennzeichen;
	private  String model;
	private  int kilometer;
	private  double preisProTag;
	private  String kategorie;
	
	
	public Car(int autoId, String kennzeichen, String model, int kilometer,double preisProTag, String kategorie) {
		super();
		this.autoId = autoId;
		this.kennzeichen = kennzeichen;
		this.model = model;
		this.kilometer = kilometer;
		this.preisProTag = preisProTag;
		this.kategorie = kategorie;

	}
	
	public  int getAutoId() {
		return autoId;
	}
	public void setAutoId(int autoId) {
		this.autoId = autoId;
	}
	public Car() {
		// TODO Auto-generated constructor stub
	}

	public  String getKennzeichen() {
		return kennzeichen;
	}

	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen = kennzeichen;
	}

	public  String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public  int getKilometer() {
		return kilometer;
	}
	public void setKilometer(int kilometer) {
		this.kilometer = kilometer;
	}
	
	public  double getPreisProTag() {
		return preisProTag;
	}

	public void setPreisProTag(double preisProTag) {
		this.preisProTag = preisProTag;
	}

	public  String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String status) {
		this.kategorie = status;
	}

	public static List<Car> getCarsList() {

		List<Car> list = new ArrayList<Car>();

		return list;
	}
	@Override
	public String toString() {
		return "Car [autoId=" + autoId + ", kennzeichen=" + kennzeichen + ", model=" + model + ", kilometer="
				+ kilometer + ", preisProTag=" + preisProTag + ", kategorie=" + kategorie + "]";
	
	}

	
}
